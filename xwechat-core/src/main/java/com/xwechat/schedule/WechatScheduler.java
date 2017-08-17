/**
 * @author yuanwq, date: 2017年8月16日
 */
package com.xwechat.schedule;

import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Collection;
import java.util.concurrent.*;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.xwechat.api.base.ClientCredentialApi;
import com.xwechat.api.base.ClientCredentialApi.ClientCredentialResponse;
import com.xwechat.api.jssdk.JsapiTicketApi;
import com.xwechat.api.jssdk.JsapiTicketApi.JsapiTicketResponse;
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

  private final ExecutorService taskExecutor;
  private final ScheduledExecutorService scheduledExecutor;

  private long gapMillis = TimeUnit.MINUTES.toMillis(1);
  private long durationMillis = TimeUnit.MINUTES.toMillis(100);
  private Repository<TaskDef> taskRepo = new MapRepository<>();
  private TaskLoop taskLoop;
  private volatile boolean started = false;
  private boolean debug = false;

  /* 默认使用内存方式，生产环境请自行实现并设置 */
  private Repository<ExpirableValue> accessTokenRepo = new MapRepository<>();
  private Repository<ExpirableValue> jsTicketRepo = new MapRepository<>();

  public WechatScheduler() {
    this(Executors.newCachedThreadPool(wechatThreadFactory),
        Executors.newScheduledThreadPool(5, wechatThreadFactory));
  }

  public WechatScheduler(ExecutorService taskExecutor, ScheduledExecutorService scheduledExecutor) {
    Preconditions.checkNotNull(taskExecutor);
    Preconditions.checkNotNull(scheduledExecutor);
    this.taskExecutor = taskExecutor;
    this.scheduledExecutor = scheduledExecutor;
  }

  public void setDuration(long duration, TimeUnit unit) {
    this.durationMillis = unit.toMillis(duration);
  }

  public void setGapMillis(long gap, TimeUnit unit) {
    this.gapMillis = unit.toMillis(gap);
  }

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

  public TaskLoop getTaskLoop() {
    return taskLoop;
  }

  public Repository<TaskDef> getTaskRepo() {
    return taskRepo;
  }

  public void setTaskRepo(Repository<TaskDef> taskRepo) {
    this.taskRepo = taskRepo;
  }

  public Repository<ExpirableValue> getAccessTokenRepo() {
    return accessTokenRepo;
  }

  /**
   * @param accessTokenRepo 至少需要实现更新接口
   */
  public void setAccessTokenRepo(Repository<ExpirableValue> accessTokenRepo) {
    this.accessTokenRepo = accessTokenRepo;
  }

  public Repository<ExpirableValue> getJsTicketRepo() {
    return jsTicketRepo;
  }

  /**
   * @param jsTicketRepo 至少需要实现更新接口
   */
  public void setJsTicketRepo(Repository<ExpirableValue> jsTicketRepo) {
    this.jsTicketRepo = jsTicketRepo;
  }

  public TaskDef scheduleAccessToken(String appId, String appSecret) {
    return scheduleTask(new TaskDef(appId, appSecret));
  }

  public TaskDef scheduleJsTicket(String appId, String appSecret) {
    TaskDef task = new TaskDef(appId, appSecret);
    task.addTicketType(TicketType.JSAPI);
    return scheduleTask(task);
  }

  private TaskDef scheduleTask(TaskDef task) {
    Preconditions.checkState(started, "not start yet");
    logger.info("schedule task: {}", task);
    boolean immediateExecute = false;
    final String appId = task.getAppId();
    TaskDef oldTask = null;
    try {
      oldTask = taskRepo.get(appId);
    } catch (IOException e) {
      throw new RuntimeException("fail to get task: " + task.getAppId(), e);
    }
    if (oldTask == null) {
      immediateExecute = true;
      oldTask = task;
    } else if (!StringUtils.equals(task.getAppSecret(), oldTask.getAppSecret())) {
      immediateExecute = true;
      oldTask.setAppSecret(task.getAppSecret());
      oldTask.addTicketTypes(task.getTicketTypes());
    } else if (!oldTask.getTicketTypes().containsAll(task.getTicketTypes())) {
      immediateExecute = true;
      oldTask.addTicketTypes(task.getTicketTypes());
    } else if (oldTask.getExpireTime() < System.currentTimeMillis()) {
      immediateExecute = true;
    }
    try {
      taskRepo.update(appId, oldTask);
      if (debug) {
        logger.info("taskRepo: {}", taskRepo);
      }
    } catch (IOException e) {
      throw new RuntimeException("fail to update task: " + oldTask, e);
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

  private void scheduleNext(TaskDef task) {
    long aheadMillis = Long.min(task.getExpireTime() - System.currentTimeMillis(), durationMillis);
    long ahead = aheadMillis / gapMillis;
    taskLoop.add(ahead, task.getAppId());
    if (debug) {
      logger.info("taskLoop: " + taskLoop);
    }
  }

  private class LoopStepThread implements Runnable {
    @Override
    public void run() {
      Collection<String> appIds = taskLoop.current();
      taskLoop.moveOn();
      for (String appId : appIds) {
        TaskDef task;
        try {
          task = taskRepo.get(appId);
          submit(task);
        } catch (IOException e) {
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
      if (debug) {
        logger.info("[done {}] accessTokenRepo={}", taskDef.getAppId(), accessTokenRepo);
      }
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
      api.setAppId(taskDef.getAppId()).setAppSecret(taskDef.getAppSecret());
      ResponseWrapper<ClientCredentialResponse> wrapper = Wechat.get().call(api);
      logger.info("accessToken, appId={}, resp={}", taskDef.getAppId(), wrapper.getBody());
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
      logger.info("jsTicket, appId={}, resp={}", taskDef.getAppId(), wrapper.getBody());
      JsapiTicketResponse response = wrapper.getResponse();
      long expireTime =
          System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(response.getExpiresIn());
      ExpirableValue value = new ExpirableValue(response.getTicket(), expireTime);
      return value;
    }
  }
}
