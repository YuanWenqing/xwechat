/**
 * @author yuanwq, date: 2017年7月22日
 */
package com.xwechat.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.MoreObjects;

/**
 * 微信接口返回结果的包装：
 * <ul>
 * <li>原始文本</li>
 * <li>解析出来的结果</li>
 * </ul>
 * 
 * @author yuanwq
 */
public class ResponseWrapper<R extends IWechatResponse> {
  private static final ObjectMapper RESPONSE_OBJECT_MAPPER = new ObjectMapper();

  protected final String body;
  protected R response;

  public ResponseWrapper(String bodyText) {
    this.body = bodyText;
  }

  /** 接口返回的Response Body */
  public String getBody() {
    return body;
  }

  public void setResponse(R response) {
    this.response = response;
  }

  /** 解析的结果 */
  public R getResponse() {
    return response;
  }

  @Override
  public String toString() {
    String responseText = toString(response);
    return MoreObjects.toStringHelper(getClass()).add("response", responseText).add("body", body)
        .toString();
  }

  public static String toString(IWechatResponse response) {
    try {
      return RESPONSE_OBJECT_MAPPER.writeValueAsString(response);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("never here", e);
    }
  }
}
