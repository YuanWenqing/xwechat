/**
 * @author yuanwq, date: 2017年7月22日
 */
package com.xwechat.core;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * @author yuanwq
 */
public interface IWechatApi<R extends IWechatResponse> {
  /**
   * 构造OkHttp3的请求体
   */
  public Request toOkHttpRequest();

  /**
   * OkHttp3的HttpUrl
   */
  public HttpUrl toOkHttpUrl();

  /**
   * 请求结果的映射类，用于json形式的结果
   */
  public Class<R> getResponseClass();
}
