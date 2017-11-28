/**
 * @author yuanwq, date: 2017年8月18日
 */
package com.xwechat.schedule;

import java.util.concurrent.TimeUnit;

import com.xwechat.core.ExpirableValue;
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

  private AppRepository appRepo;
  private WechatScheduler scheduler;

  @Before
  public void setup() {
    appRepo = new LoggableRepository();
    scheduler =
        WechatScheduler.newBuilder().setAppRepo(appRepo).setGap(5, TimeUnit.SECONDS).build();
    scheduler.setDebug(true);
    scheduler.start();
    System.err.println("dump in setup:\n" + scheduler.dump());
  }

  @Test
  public void testSchedule() throws InterruptedException {
    Application application = new Application("a", appId, appSecret);
    appRepo.saveApplication(application);
    Thread.sleep(2000); // wait for task run
    System.err.println("dump after schedule accessToken:\n" + scheduler.dump());
    assertEquals(1, scheduler.getAppRepo().all().size());
    ExpirableValue accessToken = scheduler.getAppRepo().getApplication(appId).getAccessToken();
    System.out.println(accessToken);
  }

  @Test
  public void testRefresh() throws InterruptedException {
    Application application = new Application("a", appId, appSecret);
    appRepo.saveApplication(application);
    scheduler.refresh(appId);
    Thread.sleep(2000); // wait for task run
    System.err.println("dump after refresh:\n" + scheduler.dump());
    assertEquals(1, scheduler.getAppRepo().all().size());
    ExpirableValue accessToken = scheduler.getAppRepo().getApplication(appId).getAccessToken();
    System.out.println(accessToken);

    scheduler.refresh(appId, true);
    Thread.sleep(2000); // wait for task run
    System.err.println("dump after force refresh:\n" + scheduler.dump());
    assertEquals(1, scheduler.getAppRepo().all().size());
    ExpirableValue accessToken2 = scheduler.getAppRepo().getApplication(appId).getAccessToken();
    System.out.println(accessToken2);
    assertNotEquals(accessToken.getValue(), accessToken2.getValue());
    assertTrue(accessToken.getExpireTime() < accessToken2.getExpireTime());
  }
}
