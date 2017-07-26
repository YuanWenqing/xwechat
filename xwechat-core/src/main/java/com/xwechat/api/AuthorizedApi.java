/**
 * @author yuanwq, date: 2017年7月26日
 */
package com.xwechat.api;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Preconditions;
import com.xwechat.core.IWechatResponse;

/**
 * 已授权的应用api，需要access_token。授权获取access_token（用户级或应用级）后的阶段
 * 
 * @author yuanwq
 */
public abstract class AuthorizedApi<R extends IWechatResponse> extends AbstractWechatApi<R> {

  public AuthorizedApi(String endpoint) {
    super(endpoint);
  }

  public AuthorizedApi<R> setAccessToken(String accessToken) {
    Preconditions.checkArgument(StringUtils.isNotBlank(accessToken), "blank accessToken");
    this.urlBuilder.setQueryParameter("access_token", accessToken);
    return this;
  }
}
