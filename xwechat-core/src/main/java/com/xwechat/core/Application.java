/**
 * @author yuanwq, date: 2017年7月22日
 */
package com.xwechat.core;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

/**
 * @author yuanwq
 */
public class Application {
  private final String name;
  private final String appId;
  private String appSecret;
  private ExpirableValue accessToken;

  public Application(String name, String appId, String appSecret) {
    Preconditions.checkArgument(StringUtils.isNotBlank(name));
    Preconditions.checkArgument(StringUtils.isNotBlank(appId));
    this.name = name;
    this.appId = appId;
    this.appSecret = appSecret;
  }

  public Application(String name, String appId) {
    this(name, appId, null);
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

  public void setAppSecret(String appSecret) {
    Preconditions.checkArgument(StringUtils.isNotBlank(appSecret));
    this.appSecret = appSecret;
  }

  public void setAccessToken(ExpirableValue accessToken) {
    this.accessToken = accessToken;
  }

  public ExpirableValue getAccessToken() {
    return accessToken;
  }

  @Override
  public String toString() {
    MoreObjects.ToStringHelper helper = MoreObjects.toStringHelper(getClass());
    helper.add("name", name);
    helper.add("appId", appId);
    helper.add("appSecret", appSecret);
    if (accessToken != null) {
      helper.add("accessToken", accessToken);
    }
    return helper.toString();
  }
}
