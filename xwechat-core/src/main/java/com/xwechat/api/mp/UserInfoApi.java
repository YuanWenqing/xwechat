/**
 * @author yuanwq, date: 2017年7月26日
 */
package com.xwechat.api.mp;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Preconditions;
import com.xwechat.api.Apis;
import com.xwechat.api.AuthorizedApi;
import com.xwechat.api.mp.UserInfoApi.MpUserInfoResponse;
import com.xwechat.core.IWechatResponse;

/**
 * 公众平台获取用户基本信息（UnionId），公众号和小程序使用
 * 
 * @Note 公众平台
 * @url https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
 * @see https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140839
 * @author yuanwq
 */
public class UserInfoApi extends AuthorizedApi<MpUserInfoResponse> {

  public UserInfoApi() {
    super(Apis.MP_USERINFO);
    this.urlBuilder.setQueryParameter("lang", "zh_CN");
  }

  public UserInfoApi setOpenId(String openId) {
    Preconditions.checkArgument(StringUtils.isNotBlank(openId), "blank openid");
    this.urlBuilder.setQueryParameter("openid", openId);
    return this;
  }

  @Override
  public Class<MpUserInfoResponse> getResponseClass() {
    return MpUserInfoResponse.class;
  }

  public static class MpUserInfoResponse extends MpUserInfo implements IWechatResponse {
    // see MpUserInfo
  }
}
