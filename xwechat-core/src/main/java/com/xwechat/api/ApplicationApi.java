/**
 * @author yuanwq, date: 2017年7月23日
 */
package com.xwechat.api;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Preconditions;
import com.xwechat.core.IWechatResponse;

/**
 * 应用api，需要appId和appSecret，主要用于授权初始阶段
 * 
 * @author yuanwq
 */
public abstract class ApplicationApi<R extends IWechatResponse> extends AbstractWechatApi<R> {

  public ApplicationApi(String endpoint) {
    super(endpoint);
  }

  public ApplicationApi<R> setAppId(String appId) {
    Preconditions.checkArgument(StringUtils.isNotBlank(appId), "blank appId");
    this.urlBuilder.setQueryParameter("appid", appId);
    return this;
  }

  public ApplicationApi<R> setAppSecret(String appSecret) {
    Preconditions.checkArgument(StringUtils.isNotBlank(appSecret), "blank appSecret");
    this.urlBuilder.setQueryParameter("secret", appSecret);
    return this;
  }

}
