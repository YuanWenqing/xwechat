/**
 * @author yuanwq, date: 2017年8月16日
 */
package com.xwechat.schedule;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.xwechat.api.base.ClientCredentialApi;
import com.xwechat.api.base.ClientCredentialApi.ClientCredentialResponse;
import com.xwechat.core.Application;
import com.xwechat.core.ExpirableValue;
import com.xwechat.core.ResponseWrapper;
import com.xwechat.core.Wechat;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

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

  private AppRepository appRepo;

  private ExecutorService taskExecutor;
  private ScheduledExecutorService scheduledExecutor;

  /**
   * 检查是否超时的间隔时间
   */
  private long gapMillis = TimeUnit.MINUTES.toMillis(10);
  /**
   * accessToken等的有效时间
   */
  private long durationMillis = TimeUnit.MINUTES.toMillis(60);

  private volatile boolean started = false;
  private boolean debug = false;

  private WechatScheduler() {
  }

  public void setDebug(boolean debug) {
    this.debug = debug;
  }

  public synchronized void start() {
    Preconditions.checkState(!started, "already started");
    Preconditions.checkArgument(durationMillis > gapMillis);
    scheduledExecutor.scheduleAtFixedRate(new CheckThread(), 0, gapMillis, TimeUnit.MILLISECONDS);
    started = true;
  }

  public boolean isStarted() {
    return started;
  }

  public AppRepository getAppRepo() {
    return appRepo;
  }

  public Application refresh(String appId) {
    return refresh(appId, false);
  }

  public Application refresh(String appId, boolean force) {
    Application application = appRepo.getApplication(appId);
    if (application == null) {
      return null;
    }
    if (force || needRefresh(application)) {
      taskExecutor.submit(new RefreshThread(application));
    }
    return application;
  }

  private boolean needRefresh(Application app) {
    if (app.getAccessToken() == null || StringUtils.isBlank(app.getAccessToken().getValue())) {
      return true;
    }
    if (app.getAccessToken().getExpireTime() < System.currentTimeMillis() + durationMillis) {
      return true;
    }
    logger.info("no need to refresh {}", app);
    return false;
  }

  private static final String DUMP_TEMPLATE = "===== appRepo =====\n%s\n";

  public String dump() {
    return String.format(DUMP_TEMPLATE, appRepo);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public static class Builder {
    private AppRepository appRepo = new MapRepository();

    private ExecutorService taskExecutor;
    private ScheduledExecutorService scheduledExecutor;

    private long gapMillis = TimeUnit.MINUTES.toMillis(10);
    private long durationMillis = TimeUnit.MINUTES.toMillis(100);

    private Builder() {
    }

    public Builder setAppRepo(AppRepository appRepo) {
      this.appRepo = appRepo;
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

      scheduler.scheduledExecutor = this.scheduledExecutor != null ? this.scheduledExecutor :
          Executors.newScheduledThreadPool(10, wechatThreadFactory);
      scheduler.taskExecutor =
          this.taskExecutor != null ? this.taskExecutor : scheduler.scheduledExecutor;

      scheduler.durationMillis = this.durationMillis;
      scheduler.gapMillis = this.gapMillis;

      return scheduler;
    }
  }

  private class CheckThread implements Runnable {
    @Override
    public void run() {
      Collection<Application> apps = appRepo.all().values();
      List<Application> toRefresh = Lists.newArrayList();
      for (Application app : apps) {
        if (needRefresh(app)) {
          toRefresh.add(app);
        }
      }
      logger.info("to refresh: {}", Lists.transform(toRefresh, Application::getBrief));
      for (Application app : toRefresh) {
        taskExecutor.submit(new RefreshThread(app));
      }
    }
  }

  private class RefreshThread implements Runnable {
    private Application application;

    public RefreshThread(Application application) {
      this.application = application;
    }

    @Override
    public void run() {
      logger.info("refresh {}", application);
      try {
        ExpirableValue accessToken = reqAccessToken();
        application.setAccessToken(accessToken);
        appRepo.saveApplication(application);
      } catch (IOException e) {
        throw new RuntimeException("fail in refresh: " + application, e);
      }
    }

    private ExpirableValue reqAccessToken() throws IOException {
      ClientCredentialApi api = new ClientCredentialApi();
      api.setAppId(application.getAppId()).setAppSecret(application.getAppSecret());
      ResponseWrapper<ClientCredentialResponse> wrapper = Wechat.get().call(api);
      logger.info("[reqAccessToken] app={}, resp={}", application.getBrief(), wrapper.getBody());
      ClientCredentialResponse response = wrapper.getResponse();
      long expireTime =
          System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(response.getExpiresIn());
      ExpirableValue value = new ExpirableValue(response.getAccessToken(), expireTime);
      return value;
    }

  }
}
