/**
 * @author yuanwq, date: 2017年7月22日
 */
package com.xwechat.api;

/**
 * @author yuanwq
 */
public interface Apis {
  String OAUTH2_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token";

  String SNS_USERINFO = "https://api.weixin.qq.com/sns/userinfo";

  String CLIENT_CREDENTIAL = "https://api.weixin.qq.com/cgi-bin/token";
  String GET_CALLBACK_IP = "https://api.weixin.qq.com/cgi-bin/getcallbackip";
  String MP_USERINFO = "https://api.weixin.qq.com/cgi-bin/user/info";
  String MP_USERINFO_BATCH = "https://api.weixin.qq.com/cgi-bin/user/info/batchget";
  String MP_OPENID_LIST = "https://api.weixin.qq.com/cgi-bin/user/get";

  String JSAPI_TICKET = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";

  String MESSAGE_SEND = "https://api.weixin.qq.com/cgi-bin/message/mass/send";
  String MESSAGE_SENDALL = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall";

  String WXAPP_TEMPLATE_MSG_SEND = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send";
}
