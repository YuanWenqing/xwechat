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
  private String name;
  private final String appId;
  private final String appSecret;

  public Application(String name, String appId, String appSecret) {
    this(appId, appSecret);
    this.name = name;
  }

  public Application(String appId, String appSecret) {
    Preconditions.checkArgument(StringUtils.isNotBlank(appId));
    Preconditions.checkArgument(StringUtils.isNotBlank(appSecret));
    this.appId = appId;
    this.appSecret = appSecret;
  }

  public void setName(String name) {
    this.name = name;
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

  @Override
  public String toString() {
    MoreObjects.ToStringHelper helper = MoreObjects.toStringHelper(getClass());
    helper.add("appId", appId);
    helper.add("appSecret", appSecret);
    if (name != null) {
      helper.add("name", name);
    }
    return helper.toString();
  }
}
