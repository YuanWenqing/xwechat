/**
 * @author yuanwq, date: 2017年7月22日
 */
package com.xwechat.api;

import java.io.IOException;

import org.apache.commons.io.IOUtils;

import com.xwechat.core.IWechatApi;
import com.xwechat.core.IWechatResponse;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Buffer;

/**
 * @author yuanwq
 */
public abstract class AbstractWechatApi<R extends IWechatResponse> implements IWechatApi<R> {
  protected static final MediaType JSON_MEDIA_TYPE = MediaType.parse("text/json");

  protected final Request.Builder requestBuilder = new Request.Builder();
  protected final HttpUrl.Builder urlBuilder;
  protected final Method method;
  protected RequestBody requestBody;

  /**
   * @param endpoint 基本的url，即queryString之前的部分
   */
  public AbstractWechatApi(String endpoint) {
    this(endpoint, Method.GET);
  }

  /**
   * @param endpoint 基本的url，即queryString之前的部分
   */
  public AbstractWechatApi(String endpoint, Method method) {
    this.urlBuilder = HttpUrl.parse(endpoint).newBuilder();
    this.method = method;
  }

  public void setRequestBody(RequestBody requestBody) {
    this.requestBody = requestBody;
  }

  @Override
  public HttpUrl toOkHttpUrl() {
    return urlBuilder.build();
  }

  @Override
  public Request toOkHttpRequest() {
    requestBuilder.url(toOkHttpUrl());
    switch (method) {
      case POST:
        requestBuilder.post(requestBody);
        break;
      case PUT:
        requestBuilder.put(requestBody);
        break;
      case GET:
      default:
        break;
    }
    return requestBuilder.build();
  }

  @Override
  public String toString() {
    Request request = toOkHttpRequest();
    if (Method.GET.name().equalsIgnoreCase(request.method())) {
      return request.toString();
    } else {
      return "Request{method=" + request.method() + ", url=" + request.url() + ", body="
          + toString(requestBody) + '}';
    }
  }

  protected String toString(RequestBody body) {
    if (body == null) return null;
    if ("text".equalsIgnoreCase(body.contentType().type())) {
      Buffer buffer = new Buffer();
      try {
        body.writeTo(buffer);
        return IOUtils.toString(buffer.inputStream(), body.contentType().charset());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return "[<" + body.contentType().toString() + ">]";
  }

}
