/**
 * @author yuanwq, date: 2017年7月26日
 */
package com.xwechat.api.msg;

import com.xwechat.api.Apis;
import com.xwechat.api.AuthorizedApi;
import com.xwechat.api.Method;

import okhttp3.RequestBody;

/**
 * @author yuanwq
 */
public class SendAllMessageApi extends AuthorizedApi<SendMessageResponse> {
  private Message message;

  public SendAllMessageApi() {
    super(Apis.MESSAGE_SENDALL, Method.POST);
  }

  public SendAllMessageApi setMessage(Message msg) {
    this.message = msg;
    setRequestBody(RequestBody.create(JSON_MEDIA_TYPE, msg.toJson()));
    return this;
  }

  public Message getMessage() {
    return message;
  }

  @Override
  public Class<SendMessageResponse> getResponseClass() {
    return SendMessageResponse.class;
  }
}
