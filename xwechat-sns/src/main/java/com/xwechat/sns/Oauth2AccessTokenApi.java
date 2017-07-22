/**
 * @author yuanwq, date: 2017年7月22日
 */
package com.xwechat.sns;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Preconditions;
import com.xwechat.core.AbstractWechatApi;
import com.xwechat.core.IWechatResponse;
import com.xwechat.def.GrantType;
import com.xwechat.sns.Oauth2AccessTokenApi.Oauth2AccessTokenResponse;

/**
 * 通过code换取access_token
 * 
 * @Note 开发平台
 * @see https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419317853&token=&lang=zh_CN
 * @author yuanwq
 */
public class Oauth2AccessTokenApi extends AbstractWechatApi<Oauth2AccessTokenResponse> {

  public Oauth2AccessTokenApi() {
    super(SnsApis.OAUTH2_ACCESS_TOKEN);
    this.urlBuilder.setQueryParameter("grant_type", GrantType.AUTHORIZATION_CODE.asParameter());
  }

  public Oauth2AccessTokenApi setAppId(String appId) {
    Preconditions.checkArgument(StringUtils.isNotBlank(appId), "blank appId");
    this.urlBuilder.setQueryParameter("appid", appId);
    return this;
  }

  public Oauth2AccessTokenApi setAppSecret(String appSecret) {
    Preconditions.checkArgument(StringUtils.isNotBlank(appSecret), "blank appSecret");
    this.urlBuilder.setQueryParameter("secret", appSecret);
    return this;
  }

  public Oauth2AccessTokenApi setCode(String code) {
    Preconditions.checkArgument(StringUtils.isNotBlank(code), "blank code");
    this.urlBuilder.setQueryParameter("code", code);
    return this;
  }

  @Override
  public Class<Oauth2AccessTokenResponse> getResponseClass() {
    return Oauth2AccessTokenResponse.class;
  }

  public static class Oauth2AccessTokenResponse implements IWechatResponse {
    private String accessToken;
    private int expiresIn;
    private String refreshToken;
    private String openid;
    private String scope;

    public String getAccessToken() {
      return accessToken;
    }

    public void setAccessToken(String accessToken) {
      this.accessToken = accessToken;
    }

    public int getExpiresIn() {
      return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
      this.expiresIn = expiresIn;
    }

    public String getRefreshToken() {
      return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
      this.refreshToken = refreshToken;
    }

    public String getOpenid() {
      return openid;
    }

    public void setOpenid(String openid) {
      this.openid = openid;
    }

    public String getScope() {
      return scope;
    }

    public void setScope(String scope) {
      this.scope = scope;
    }
  }
}
