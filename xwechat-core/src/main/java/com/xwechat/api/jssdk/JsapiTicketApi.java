/**
 * @author yuanwq, date: 2017年7月23日
 */
package com.xwechat.api.jssdk;

import com.xwechat.api.AbstractWechatResponse;
import com.xwechat.api.Apis;
import com.xwechat.api.AuthorizedApi;
import com.xwechat.api.jssdk.JsapiTicketApi.JsapiTicketResponse;
import com.xwechat.enums.TicketType;

/**
 * 获取jsapi ticket接口，用于公众号的各种接口，比如分享等
 * 
 * @Note 公众平台
 * @url https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi
 * @see https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140183
 * @author yuanwq
 */
public class JsapiTicketApi extends AuthorizedApi<JsapiTicketResponse> {

  public JsapiTicketApi() {
    super(Apis.JSAPI_TICKET);
    this.urlBuilder.setQueryParameter("type", TicketType.JSAPI.asParameter());
  }

  @Override
  public Class<JsapiTicketResponse> getResponseClass() {
    return JsapiTicketResponse.class;
  }

  public static class JsapiTicketResponse extends AbstractWechatResponse {
    private int errcode;
    private String errmsg;
    private String ticket;
    private int expiresIn;

    public int getErrcode() {
      return errcode;
    }

    public void setErrcode(int errcode) {
      this.errcode = errcode;
    }

    public String getErrmsg() {
      return errmsg;
    }

    public void setErrmsg(String errmsg) {
      this.errmsg = errmsg;
    }

    public String getTicket() {
      return ticket;
    }

    public void setTicket(String ticket) {
      this.ticket = ticket;
    }

    public int getExpiresIn() {
      return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
      this.expiresIn = expiresIn;
    }

  }

}
