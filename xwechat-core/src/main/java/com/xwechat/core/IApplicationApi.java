/**
 * @author yuanwq, date: 2017年7月23日
 */
package com.xwechat.core;

/**
 * 应用api，需要appId和appSecret
 * 
 * @author yuanwq
 */
public interface IApplicationApi {

  public IWechatApi<?> setAppId(String appId);

  public IWechatApi<?> setAppSecret(String appSecret);

}
