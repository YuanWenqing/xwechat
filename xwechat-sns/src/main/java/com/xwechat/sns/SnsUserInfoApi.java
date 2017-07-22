/**
 * @author yuanwq, date: 2017年7月22日
 */
package com.xwechat.sns;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Preconditions;
import com.xwechat.core.AbstractWechatApi;
import com.xwechat.core.AbstractWechatReponse;
import com.xwechat.sns.SnsUserInfoApi.SnsUserInfo;

/**
 * 开放平台第三方登陆后sns获取用户个人信息（unionId）
 * 
 * @Note 开发平台
 * @url https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID
 * @see https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419317853&token=&lang=zh_CN
 * @author yuanwq
 */
public class SnsUserInfoApi extends AbstractWechatApi<SnsUserInfo> {

  public SnsUserInfoApi() {
    super(SnsApis.SNS_USERINFO);
  }

  public SnsUserInfoApi setAccessToken(String accessToken) {
    Preconditions.checkArgument(StringUtils.isNotBlank(accessToken), "blank accessToken");
    this.urlBuilder.setQueryParameter("access_token", accessToken);
    return this;
  }

  public SnsUserInfoApi setOpenId(String openId) {
    Preconditions.checkArgument(StringUtils.isNotBlank(openId), "blank openId");
    this.urlBuilder.setQueryParameter("openid", openId);
    return this;
  }

  @Override
  public Class<SnsUserInfo> getResponseClass() {
    return SnsUserInfo.class;
  }

  public static class SnsUserInfo extends AbstractWechatReponse {
    private String openid;
    private String nickname;
    private int sex;
    private String language;
    private String province;
    private String city;
    private String country;
    private String headimgurl;
    private String[] privilege;
    private String unionid;

    public String getOpenid() {
      return openid;
    }

    public void setOpenid(String openid) {
      this.openid = openid;
    }

    public String getNickname() {
      return nickname;
    }

    public void setNickname(String nickname) {
      this.nickname = nickname;
    }

    public int getSex() {
      return sex;
    }

    public void setSex(int sex) {
      this.sex = sex;
    }

    public String getLanguage() {
      return language;
    }

    public void setLanguage(String language) {
      this.language = language;
    }

    public String getProvince() {
      return province;
    }

    public void setProvince(String province) {
      this.province = province;
    }

    public String getCity() {
      return city;
    }

    public void setCity(String city) {
      this.city = city;
    }

    public String getCountry() {
      return country;
    }

    public void setCountry(String country) {
      this.country = country;
    }

    public String getHeadimgurl() {
      return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
      this.headimgurl = headimgurl;
    }

    public String[] getPrivilege() {
      return privilege;
    }

    public void setPrivilege(String[] privilege) {
      this.privilege = privilege;
    }

    public String getUnionid() {
      return unionid;
    }

    public void setUnionid(String unionid) {
      this.unionid = unionid;
    }
  }

}
