/**
 * @author yuanwq, date: 2017年7月26日
 */
package com.xwechat.api.mp;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Preconditions;
import com.xwechat.api.Apis;
import com.xwechat.api.AuthorizedApi;
import com.xwechat.api.mp.UserInfoApi.UserInfoResponse;
import com.xwechat.core.IWechatResponse;

/**
 * 获取用户基本信息（UnionId），公众号和小程序使用
 * 
 * @Note 公众平台
 * @url https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
 * @see https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140839
 * @author yuanwq
 */
public class UserInfoApi extends AuthorizedApi<UserInfoResponse> {

  public UserInfoApi() {
    super(Apis.MP_USERINFO);
    this.urlBuilder.setQueryParameter("lang", "zh_CN");
  }

  public UserInfoApi setOpenid(String openid) {
    Preconditions.checkArgument(StringUtils.isNotBlank(openid), "blank openid");
    this.urlBuilder.setQueryParameter("openid", openid);
    return this;
  }

  @Override
  public Class<UserInfoResponse> getResponseClass() {
    return UserInfoResponse.class;
  }

  public static class UserInfoResponse extends UserInfo implements IWechatResponse {
    // see MpUserInfo
  }
}
