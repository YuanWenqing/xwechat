package com.xwechat.api.wxapp;

import com.google.common.base.Preconditions;
import com.xwechat.api.Apis;
import com.xwechat.api.ApplicationApi;
import com.xwechat.core.IWechatResponse;
import com.xwechat.enums.GrantType;
import org.apache.commons.lang3.StringUtils;

/**
 * @author: yuanwq
 * @date: 2018/1/23
 */
public class JsCode2SessionApi extends ApplicationApi<JsCode2SessionApi.JsCode2SessionResp> {
  public JsCode2SessionApi() {
    super(Apis.WXAPP_JSCODE_2_SESSION);
    this.urlBuilder.setQueryParameter("grant_type", GrantType.AUTHORIZATION_CODE.asParameter());
  }

  public JsCode2SessionApi setJsCode(String jsCode) {
    Preconditions.checkArgument(StringUtils.isNotBlank(jsCode), "blank jsCode");
    this.urlBuilder.setQueryParameter("js_code", jsCode);
    return this;
  }

  @Override
  public Class<JsCode2SessionResp> getResponseClass() {
    return JsCode2SessionResp.class;
  }

  public static class JsCode2SessionResp implements IWechatResponse {

    private String openid;
    private String sessionKey;
    private String unionid;

    public String getOpenid() {
      return openid;
    }

    public void setOpenid(String openid) {
      this.openid = openid;
    }

    public String getSessionKey() {
      return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
      this.sessionKey = sessionKey;
    }

    public String getUnionid() {
      return unionid;
    }

    public void setUnionid(String unionid) {
      this.unionid = unionid;
    }
  }
}
