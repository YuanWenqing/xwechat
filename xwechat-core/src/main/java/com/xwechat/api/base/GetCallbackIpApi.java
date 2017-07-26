/**
 * @author yuanwq, date: 2017年7月26日
 */
package com.xwechat.api.base;

import java.util.Collections;
import java.util.List;

import com.xwechat.api.AbstractWechatResponse;
import com.xwechat.api.Apis;
import com.xwechat.api.AuthorizedApi;
import com.xwechat.api.base.GetCallbackIpApi.GetCallbackIpResponse;

/**
 * 获取微信服务器IP地址
 * 
 * @Note 公众平台
 * @url https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token=ACCESS_TOKEN
 * @see https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140187
 * @author yuanwq
 */
public class GetCallbackIpApi extends AuthorizedApi<GetCallbackIpResponse> {

  public GetCallbackIpApi() {
    super(Apis.GET_CALLBACK_IP);
  }

  @Override
  public Class<GetCallbackIpResponse> getResponseClass() {
    return GetCallbackIpResponse.class;
  }

  public static class GetCallbackIpResponse extends AbstractWechatResponse {
    private List<String> ipList;

    public List<String> getIpList() {
      return ipList == null ? Collections.emptyList() : Collections.unmodifiableList(ipList);
    }

    public void setIpList(List<String> ipList) {
      this.ipList = ipList;
    }
  }

}
