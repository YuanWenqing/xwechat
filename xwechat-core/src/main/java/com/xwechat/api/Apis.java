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

  String JSAPI_TICKET = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";
}
