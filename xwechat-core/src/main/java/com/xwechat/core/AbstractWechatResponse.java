/**
 * @author yuanwq, date: 2017年7月22日
 */
package com.xwechat.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author yuanwq
 */
public abstract class AbstractWechatResponse implements IWechatResponse {
  private static final ObjectMapper RESPONSE_OBJECT_MAPPER = new ObjectMapper();

  protected String bodyText;

  @Override
  public void setBodyText(String bodyText) {
    this.bodyText = bodyText;
  }

  @Override
  public String getBodyText() {
    return bodyText;
  }

  @Override
  public String toString() {
    try {
      return RESPONSE_OBJECT_MAPPER.writeValueAsString(this);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("never here", e);
    }
  }
}
