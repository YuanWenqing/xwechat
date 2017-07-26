/**
 * @author yuanwq, date: 2017年7月22日
 */
package com.xwechat.api;

import com.xwechat.core.IWechatApi;
import com.xwechat.core.IWechatResponse;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * @author yuanwq
 */
public abstract class AbstractWechatApi<R extends IWechatResponse>
                                           implements IWechatApi<R> {
  protected final Request.Builder requestBuilder = new Request.Builder();
  protected final HttpUrl.Builder urlBuilder;

  /**
   * @param endpoint 基本的url，即queryString之前的部分
   */
  public AbstractWechatApi(String endpoint) {
    urlBuilder = HttpUrl.parse(endpoint).newBuilder();
  }

  @Override
  public HttpUrl toOkHttpUrl() {
    return urlBuilder.build();
  }

  @Override
  public Request toOkHttpRequest() {
    return requestBuilder.url(toOkHttpUrl()).build();
  }

  @Override
  public String toString() {
    return toOkHttpUrl().toString();
  }
}
