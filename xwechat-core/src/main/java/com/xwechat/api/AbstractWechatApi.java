/**
 * @author yuanwq, date: 2017年7月22日
 */
package com.xwechat.api;

import java.io.IOException;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.xwechat.core.IWechatApi;
import com.xwechat.core.IWechatResponse;

import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Buffer;

/**
 * @author yuanwq
 */
public abstract class AbstractWechatApi<R extends IWechatResponse> implements IWechatApi<R> {
  protected static final ObjectMapper API_OBJECT_MAPPER = new ObjectMapper();

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
    Request request = toOkHttpRequest();
    if ("GET".equalsIgnoreCase(request.method())) {
      return request.toString();
    } else {
      return "Request{method=" + request.method() + ", url=" + request.url() + ", body="
          + toString(request.body()) + '}';
    }
  }

  protected String toString(RequestBody body) {
    if (body == null) return null;
    if ("text".equalsIgnoreCase(body.contentType().type())) {
      Buffer buffer = new Buffer();
      try {
        body.writeTo(buffer);
        IOUtils.toString(buffer.inputStream(), body.contentType().charset());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return "[<" + body.contentType().toString() + ">]";
  }

  protected static String writeJsonAsString(JsonNode node) {
    return writeJsonAsString(node, false);
  }

  protected static String writeJsonAsString(JsonNode node, boolean pretty) {
    try {
      ObjectWriter objectWriter;
      if (pretty) {
        objectWriter = API_OBJECT_MAPPER.writerWithDefaultPrettyPrinter();
      } else {
        objectWriter = API_OBJECT_MAPPER.writer();
      }
      return objectWriter.writeValueAsString(node);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("never here, node=" + node, e);
    }
  }
}
