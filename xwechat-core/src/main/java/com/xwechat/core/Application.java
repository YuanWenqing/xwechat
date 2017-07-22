/**
 * @author yuanwq, date: 2017年7月22日
 */
package com.xwechat.core;

/**
 * @author yuanwq
 */
public abstract class Application {
  private final String name;
  private final String appId;
  private final String appSecret;

  public Application(String name, String appId, String appSecret) {
    this.name = name;
    this.appId = appId;
    this.appSecret = appSecret;
  }

  public Application(String appId, String appSecret) {
    this("app-" + appId, appId, appSecret);
  }

  public String getName() {
    return name;
  }

  public String getAppId() {
    return appId;
  }

  public String getAppSecret() {
    return appSecret;
  }

}