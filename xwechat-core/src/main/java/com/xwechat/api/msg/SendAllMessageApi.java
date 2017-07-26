/**
 * @author yuanwq, date: 2017年7月26日
 */
package com.xwechat.api.msg;

import com.xwechat.api.Apis;
import com.xwechat.api.AuthorizedApi;

import okhttp3.RequestBody;

/**
 * @author yuanwq
 */
public class SendAllMessageApi extends AuthorizedApi<MessageSendResponse> {
  private Message message;

  public SendAllMessageApi() {
    super(Apis.MESSAGE_SENALL);
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
  public Class<MessageSendResponse> getResponseClass() {
    return MessageSendResponse.class;
  }
}
