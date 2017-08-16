package com.xwechat.api.wxapp;

import okhttp3.RequestBody;

import com.xwechat.api.Apis;
import com.xwechat.api.AuthorizedApi;
import com.xwechat.api.Method;

/**
 * 小程序发送模板消息
 *
 * @Note 小程序api
 * @url https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=ACCESS_TOKEN
 * @see https://mp.weixin.qq.com/debug/wxadoc/dev/api/notice.html#发送模板消息
 * @author zqs
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
