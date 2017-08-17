/**
 * @author yuanwq, date: 2017年7月23日
 */
package com.xwechat.api.base;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Preconditions;
import com.xwechat.api.Apis;
import com.xwechat.api.ApplicationApi;
import com.xwechat.api.base.ClientCredentialApi.ClientCredentialResponse;
import com.xwechat.core.IWechatResponse;
import com.xwechat.enums.GrantType;

/**
 * 获取全局接口调用凭据，应用级access_token，公众号接口和移动应用、网站应用的智能接口都需要
 * 
 * @Note 开发平台，公众平台
 * @url https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
 * @see https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419317910&token=&lang=zh_CN
 * @see https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419318113&token=&lang=zh_CN
 * @see https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1478575250_DCOPM
 * @author yuanwq
 */
public class ClientCredentialApi extends ApplicationApi<ClientCredentialResponse> {

  public ClientCredentialApi() {
    super(Apis.CLIENT_CREDENTIAL);
    this.urlBuilder.setQueryParameter("grant_type", GrantType.CLIENT_CREDENTIAL.asParameter());
  }

  public ClientCredentialApi setAppId(String appId) {
    Preconditions.checkArgument(StringUtils.isNotBlank(appId), "blank appId");
    this.urlBuilder.setQueryParameter("appid", appId);
    return this;
  }

  public ClientCredentialApi setAppSecret(String appSecret) {
    Preconditions.checkArgument(StringUtils.isNotBlank(appSecret), "blank appSecret");
    this.urlBuilder.setQueryParameter("secret", appSecret);
    return this;
  }

  @Override
  public Class<ClientCredentialResponse> getResponseClass() {
    return ClientCredentialResponse.class;
  }

  public static class ClientCredentialResponse implements IWechatResponse {
    private String accessToken;
    private int expiresIn;

    public String getAccessToken() {
      return accessToken;
    }

    public void setAccessToken(String accessToken) {
      this.accessToken = accessToken;
    }

    /** seconds */
    public int getExpiresIn() {
      return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
      this.expiresIn = expiresIn;
    }
  }
}
