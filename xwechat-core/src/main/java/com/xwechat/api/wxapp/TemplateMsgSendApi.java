package com.xwechat.api.wxapp;

import okhttp3.RequestBody;

import com.xwechat.api.Apis;
import com.xwechat.api.AuthorizedApi;
import com.xwechat.api.Method;
import com.xwechat.util.JsonUtil;

/**
 * 小程序发送模板消息
 *
 * @Note 小程序api
 * @url https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=ACCESS_TOKEN
 * @see https://mp.weixin.qq.com/debug/wxadoc/dev/api/notice.html#发送模板消息
 * @author zqs
 */
public class TemplateMsgSendApi extends AuthorizedApi<WxappApiResp> {

  protected TemplateMsg msg;

  public TemplateMsgSendApi() {
    super(Apis.WXAPP_TEMPLATE_MSG_SEND, Method.POST);
  }

  public TemplateMsgSendApi setMessage(TemplateMsg msg) {
    this.msg = msg;
    setRequestBody(RequestBody.create(JSON_MEDIA_TYPE, JsonUtil.writeAsString(JsonUtil.DEFAULT_OBJECT_MAPPER, msg)));
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
