/**
 * @author yuanwq, date: 2017年8月18日
 */
package com.xwechat.schedule;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

import com.xwechat.BaseTest;
import com.xwechat.core.Application;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author yuanwq
 */
public class TestWechatSchedule extends BaseTest {
  // look服务号测试号
  private static final String appId = "wx215a9b70cdca4a60";
  private static final String appSecret = "dfac81d39846f09a5995f643c5df5813";

  private Repository<Application> appRepo;
  private WechatScheduler scheduler;

  @Before
  public void setup() {
    appRepo = new LoggableRepository<>("app");
    appRepo.update(appId, new Application(appId, appSecret));
    scheduler = WechatScheduler.newBuilder().setAppRepo(appRepo)
        .setAccessTokenRepo(new LoggableRepository<>("accessToken"))
        .setTaskRepo(new LoggableRepository<>("task")).setDuration(3, TimeUnit.SECONDS)
        .setGap(1, TimeUnit.SECONDS).build();
    scheduler.setDebug(true);
    scheduler.start();
    System.err.println("dump in setup:\n" + scheduler.dump());
  }

  @Test
  public void testSchedule() throws InterruptedException {
    scheduler.scheduleAccessToken(appId);
    Thread.sleep(2000); // wait for task run
    System.err.println("dump after schedule accessToken:\n" + scheduler.dump());
    assertEquals(1, scheduler.getAppRepo().all().size());
    assertEquals(1, scheduler.getTaskRepo().all().size());
    assertEquals(1, scheduler.getAccessTokenRepo().all().size());
    String accessToken = scheduler.getAccessTokenRepo().get(appId).getValue();
    TaskDef taskDef = scheduler.getTaskRepo().get(appId);
    long executeTime = taskDef.getExecuteTime();
    long expireTime = taskDef.getExpireTime();

    scheduler.scheduleJsTicket(appId);
    Thread.sleep(2000); // wait for task run
    System.err.println("dump after schedule jsTicket:\n" + scheduler.dump());
    assertEquals(1, scheduler.getAppRepo().all().size());
    assertEquals(1, scheduler.getTaskRepo().all().size());
    assertEquals(1, scheduler.getAccessTokenRepo().all().size());
    String accessToken2 = scheduler.getAccessTokenRepo().get(appId).getValue();
    taskDef = scheduler.getTaskRepo().get(appId);
    long executeTime2 = taskDef.getExecuteTime();
    long expireTime2 = taskDef.getExpireTime();
    assertNotEquals(accessToken, accessToken2);
    assertTrue(executeTime < executeTime2);
    assertTrue(expireTime < expireTime2);

    Thread.sleep(5000);
    String accessToken3 = scheduler.getAccessTokenRepo().get(appId).getValue();
    taskDef = scheduler.getTaskRepo().get(appId);
    long executeTime3 = taskDef.getExecuteTime();
    long expireTime3 = taskDef.getExpireTime();
    assertNotEquals(accessToken2, accessToken3);
    assertTrue(executeTime2 < executeTime3);
    assertTrue(expireTime2 < expireTime3);
  }
}
