/**
 * @author yuanwq, date: 2017年7月23日
 */
package com.xwechat.api;

import com.xwechat.core.IWechatApi;

/**
 * 应用api，需要appId和appSecret
 * 
 * @author yuanwq
 */
public interface ApplicationApi {

  public IWechatApi<?> setAppId(String appId);

  public IWechatApi<?> setAppSecret(String appSecret);

}
