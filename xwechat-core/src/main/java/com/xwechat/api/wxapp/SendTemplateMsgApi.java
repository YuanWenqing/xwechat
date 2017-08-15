package com.xwechat.api.wxapp;

import okhttp3.RequestBody;

import com.xwechat.api.Apis;
import com.xwechat.api.AuthorizedApi;
import com.xwechat.api.Method;

/**
 * Created by zqs on 2017/8/15. 小程序发送模板消息
 */
public class SendTemplateMsgApi extends AuthorizedApi<WxappApiResp> {

  protected TemplateMsg msg;

  public SendTemplateMsgApi() {
    super(Apis.MESSAGE_SEND, Method.POST);
  }

  public SendTemplateMsgApi setMessage(TemplateMsg msg) {
    this.msg = msg;
    setRequestBody(RequestBody.create(JSON_MEDIA_TYPE, msg.toJson()));
    return this;
  }

  public TemplateMsg getTemplateMsg() {
    return msg;
  }

  @Override
  public Class<WxappApiResp> getResponseClass() {
    return WxappApiResp.class;
  }
}
