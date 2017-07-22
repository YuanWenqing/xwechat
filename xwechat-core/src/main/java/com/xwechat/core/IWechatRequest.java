/**
 * @author yuanwq, date: 2017年7月22日
 */
package com.xwechat.core;

import okhttp3.Request;

/**
 * @author yuanwq
 */
public interface IWechatRequest<R extends IWechatResponse> {
  /**
   * 构造OkHttp3的请求体
   */
  public Request toOkHttpRequest();

  /**
   * @return US-ASCII representation of url, composing schema, host, path, query string, but except
   *         request body.
   */
  public String toASCIIUrl();

  /**
   * 请求结果的映射类，用于json形式的结果
   */
  public Class<R> getResponseClass();
}
