/**
 * @author yuanwq, date: 2017年8月16日
 */
package com.xwechat.schedule;

import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.concurrent.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.xwechat.api.base.ClientCredentialApi;
import com.xwechat.api.base.ClientCredentialApi.ClientCredentialResponse;
import com.xwechat.api.jssdk.JsapiTicketApi;
import com.xwechat.api.jssdk.JsapiTicketApi.JsapiTicketResponse;
import com.xwechat.core.Application;
import com.xwechat.core.ResponseWrapper;
import com.xwechat.core.Wechat;
import com.xwechat.enums.TicketType;

/**
 * @author yuanwq
 */
public class WechatScheduler {
  private static final Logger logger = LoggerFactory.getLogger(WechatScheduler.class);
  private static final ThreadFactory wechatThreadFactory =
      new ThreadFactoryBuilder().setDaemon(true).setNameFormat("wechat-schedule-%d")
          .setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
              logger.error("error in " + t.getName(), e);
            }
          }).build();

  private Repository<Application> appRepo;
  private Repository<TaskDef> taskRepo;
  private Repository<ExpirableValue> accessTokenRepo;
  private Repository<ExpirableValue> jsTicketRepo;

  private ExecutorService taskExecutor;
  private ScheduledExecutorService scheduledExecutor;

  private long gapMillis = TimeUnit.MINUTES.toMillis(1);
  private long durationMillis = TimeUnit.MINUTES.toMillis(100);
  private TaskLoop taskLoop;

  private volatile boolean started = false;
  private boolean debug = false;

  private WechatScheduler() {}

  public void setDebug(boolean debug) {
    this.debug = debug;
  }

  public synchronized void start() {
    Preconditions.checkState(!started, "already started");
    Preconditions.checkArgument(durationMillis > gapMillis);
    long size = durationMillis / gapMillis + 1; // +1是为了避免0和最大值落到同一个槽中
    taskLoop = new TaskLoop(size);
    scheduledExecutor.scheduleAtFixedRate(new LoopStepThread(), gapMillis, gapMillis,
        TimeUnit.MILLISECONDS);
    started = true;
  }

  public Repository<Application> getAppRepo() {
    return appRepo;
  }

  public Repository<TaskDef> getTaskRepo() {
    return taskRepo;
  }

  public Repository<ExpirableValue> getAccessTokenRepo() {
    return accessTokenRepo;
  }

  public Repository<ExpirableValue> getJsTicketRepo() {
    return jsTicketRepo;
  }

  public TaskLoop getTaskLoop() {
    return taskLoop;
  }

  public TaskDef scheduleAccessToken(String appId) {
    return scheduleTask(new TaskDef(appId));
  }

  public TaskDef scheduleJsTicket(String appId) {
    TaskDef task = new TaskDef(appId);
    task.addTicketType(TicketType.JSAPI);
    return scheduleTask(task);
  }

  private TaskDef scheduleTask(TaskDef task) {
    Preconditions.checkState(started, "not start yet");
    logger.info("schedule task: {}", task);
    boolean immediateExecute = false;
    final String appId = task.getAppId();
    Application app;
    app = appRepo.get(appId);
    if (app == null) {
      throw new NoSuchElementException("no app found, appId=" + task.getAppId());
    }
    TaskDef oldTask = taskRepo.get(appId);
    if (oldTask == null) {
      immediateExecute = true;
      oldTask = task;
    } else if (!oldTask.getTicketTypes().containsAll(task.getTicketTypes())) {
      immediateExecute = true;
      oldTask.addTicketTypes(task.getTicketTypes());
    } else if (oldTask.getExpireTime() < System.currentTimeMillis()) {
      immediateExecute = true;
    }
    taskRepo.update(appId, oldTask);
    if (debug) {
      logger.info("taskRepo: {}", taskRepo);
    }
    if (immediateExecute) {
      submit(oldTask);
    } else {
      scheduleNext(oldTask);
    }
    return oldTask;
  }

  private void submit(TaskDef taskDef) {
    if (taskDef == null) return;
    taskExecutor.submit(new ScheduleTask(taskDef));
  }

  private static final String DUMP_TEMPLATE =
      "===== appRepo =====\n%s\n" + "===== taskRepo =====\n%s\n" + "===== taskLoop =====\n%s\n"
          + "===== accessTokenRepo =====\n%s\n" + "===== jsTicketRepo =====\n%s\n";

  public String dump() {
    return String.format(DUMP_TEMPLATE, appRepo, taskRepo, taskLoop, accessTokenRepo, jsTicketRepo);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public static class Builder {
    private Repository<Application> appRepo;
    private Repository<TaskDef> taskRepo;
    private Repository<ExpirableValue> accessTokenRepo;
    private Repository<ExpirableValue> jsTicketRepo;

    private ExecutorService taskExecutor;
    private ScheduledExecutorService scheduledExecutor;

    private long gapMillis = TimeUnit.MINUTES.toMillis(1);
    private long durationMillis = TimeUnit.MINUTES.toMillis(100);

    private Builder() {}

    public Builder setAppRepo(Repository<Application> appRepo) {
      this.appRepo = appRepo;
      return this;
    }

    public Builder setTaskRepo(Repository<TaskDef> taskRepo) {
      this.taskRepo = taskRepo;
      return this;
    }

    public Builder setAccessTokenRepo(Repository<ExpirableValue> accessTokenRepo) {
      this.accessTokenRepo = accessTokenRepo;
      return this;
    }

    public Builder setJsTicketRepo(Repository<ExpirableValue> jsTicketRepo) {
      this.jsTicketRepo = jsTicketRepo;
      return this;
    }

    public Builder setDuration(long duration, TimeUnit unit) {
      this.durationMillis = unit.toMillis(duration);
      return this;
    }

    public Builder setGap(long gap, TimeUnit unit) {
      this.gapMillis = unit.toMillis(gap);
      return this;
    }

    public Builder setTaskExecutor(ExecutorService taskExecutor) {
      this.taskExecutor = taskExecutor;
      return this;
    }

    public Builder setScheduledExecutor(ScheduledExecutorService scheduledExecutor) {
      this.scheduledExecutor = scheduledExecutor;
      return this;
    }

    public WechatScheduler build() {
      Preconditions.checkNotNull(appRepo);
      WechatScheduler scheduler = new WechatScheduler();
      /* 默认使用内存方式，生产环境请自行实现并设置 */
      scheduler.appRepo = this.appRepo;
      scheduler.taskRepo = this.taskRepo != null ? this.taskRepo : new MapRepository<>();
      scheduler.accessTokenRepo =
          this.accessTokenRepo != null ? this.accessTokenRepo : new MapRepository<>();
      scheduler.jsTicketRepo =
          this.jsTicketRepo != null ? this.jsTicketRepo : new MapRepository<>();

      scheduler.taskExecutor = this.taskExecutor != null ? this.taskExecutor
          : Executors.newCachedThreadPool(wechatThreadFactory);
      scheduler.scheduledExecutor = this.scheduledExecutor != null ? this.scheduledExecutor
          : Executors.newSingleThreadScheduledExecutor(wechatThreadFactory);

      scheduler.durationMillis = this.durationMillis;
      scheduler.gapMillis = this.gapMillis;

      return scheduler;
    }
  }

  private void scheduleNext(TaskDef task) {
    long aheadMillis = Long.min(task.getExpireTime() - System.currentTimeMillis(), durationMillis);
    long ahead = aheadMillis / gapMillis;
    taskLoop.add(ahead, task.getAppId());
    if (debug) {
      logger.info("[after scheduleNext] appId={}, taskLoop: {}", task.getAppId(), taskLoop);
    }
  }

  private class LoopStepThread implements Runnable {
    @Override
    public void run() {
      Collection<String> appIds = taskLoop.current();
      taskLoop.moveOn();
      if (debug) {
        logger.info("[moveOn] toRun: {}", appIds);
      }
      for (String appId : appIds) {
        try {
          TaskDef task = taskRepo.get(appId);
          submit(task);
        } catch (Exception e) {
          logger.error("fail to get task in loop step: " + appId, e);
        }
      }
    }
  }

  private class ScheduleTask implements Runnable {
    private TaskDef taskDef;

    public ScheduleTask(TaskDef task) {
      this.taskDef = task;
    }

    @Override
    public void run() {
      logger.info("run {}", taskDef);
      try {
        long expireTime = doTask();
        taskDef.setExecuteTime(System.currentTimeMillis());
        taskDef.setExpireTime(expireTime);
        scheduleNext(taskDef);
        taskRepo.update(taskDef.getAppId(), taskDef);
      } catch (IOException e) {
        throw new RuntimeException("fail in task: " + taskDef, e);
      }
    }

    private long doTask() throws IOException {
      long expireTime;
      ExpirableValue accessToken = reqAccessToken();
      accessTokenRepo.update(taskDef.getAppId(), accessToken);
      expireTime = accessToken.getExpireTime();
      if (taskDef.getTicketTypes().contains(TicketType.JSAPI)) {
        ExpirableValue jsTicket = reqJsTicket(accessToken.getValue());
        jsTicketRepo.update(taskDef.getAppId(), jsTicket);
        expireTime = Long.min(expireTime, jsTicket.getExpireTime());
      }
      if (taskDef.getTicketTypes().contains(TicketType.WX_CARD)) {
        // TODO: request card ticket and update
      }
      if (debug) {
        logger.info("[done {}] task={}", taskDef.getAppId(), taskDef);
      }
      return expireTime;
    }

    private ExpirableValue reqAccessToken() throws IOException {
      ClientCredentialApi api = new ClientCredentialApi();
      Application app = appRepo.get(taskDef.getAppId());
      api.setAppId(taskDef.getAppId()).setAppSecret(app.getAppSecret());
      ResponseWrapper<ClientCredentialResponse> wrapper = Wechat.get().call(api);
      logger.info("[reqAccessToken] appId={}, resp={}", taskDef.getAppId(), wrapper.getBody());
      ClientCredentialResponse response = wrapper.getResponse();
      long expireTime =
          System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(response.getExpiresIn());
      ExpirableValue value = new ExpirableValue(response.getAccessToken(), expireTime);
      return value;
    }

    private ExpirableValue reqJsTicket(String accessToken) throws IOException {
      JsapiTicketApi api = new JsapiTicketApi();
      api.setAccessToken(accessToken);
      ResponseWrapper<JsapiTicketResponse> wrapper = Wechat.get().call(api);
      logger.info("[reqJsTicket] appId={}, resp={}", taskDef.getAppId(), wrapper.getBody());
      JsapiTicketResponse response = wrapper.getResponse();
      long expireTime =
          System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(response.getExpiresIn());
      ExpirableValue value = new ExpirableValue(response.getTicket(), expireTime);
      return value;
    }
  }
}
