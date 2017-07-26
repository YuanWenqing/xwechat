/**
 * @author yuanwq, date: 2017年7月26日
 */
package com.xwechat.api.mp;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Preconditions;
import com.xwechat.api.AbstractWechatResponse;
import com.xwechat.api.Apis;
import com.xwechat.api.AuthorizedApi;
import com.xwechat.api.mp.MpUserInfoApi.MpUserInfoResponse;

/**
 * 公众平台获取用户基本信息（UnionId），公众号和小程序使用
 * 
 * @Note 公众平台
 * @url https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
 * @see https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140839
 * @author yuanwq
 */
public class MpUserInfoApi extends AuthorizedApi<MpUserInfoResponse> {

  public MpUserInfoApi() {
    super(Apis.MP_USERINFO);
    this.urlBuilder.setQueryParameter("lang", "zh_CN");
  }

  public MpUserInfoApi setOpenId(String openId) {
    Preconditions.checkArgument(StringUtils.isNotBlank(openId), "blank openid");
    this.urlBuilder.setQueryParameter("openid", openId);
    return this;
  }

  @Override
  public Class<MpUserInfoResponse> getResponseClass() {
    return MpUserInfoResponse.class;
  }

  public static class MpUserInfoResponse extends AbstractWechatResponse {
    private String subscribe;
    private String openid;
    private String nickname;
    private int sex;
    private String language;
    private String city;
    private String province;
    private String country;
    private String headimgurl;
    private long subscribeTime;
    private String unionid;
    private String remark;
    private int groupid;
    private List<Integer> tagidList;

    public String getSubscribe() {
      return subscribe;
    }

    public void setSubscribe(String subscribe) {
      this.subscribe = subscribe;
    }

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

    public String getCity() {
      return city;
    }

    public void setCity(String city) {
      this.city = city;
    }

    public String getProvince() {
      return province;
    }

    public void setProvince(String province) {
      this.province = province;
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

    public long getSubscribeTime() {
      return subscribeTime;
    }

    public void setSubscribeTime(long subscribeTime) {
      this.subscribeTime = subscribeTime;
    }

    public String getUnionid() {
      return unionid;
    }

    public void setUnionid(String unionid) {
      this.unionid = unionid;
    }

    public String getRemark() {
      return remark;
    }

    public void setRemark(String remark) {
      this.remark = remark;
    }

    public int getGroupid() {
      return groupid;
    }

    public void setGroupid(int groupid) {
      this.groupid = groupid;
    }

    public List<Integer> getTagidList() {
      return tagidList == null ? Collections.emptyList() : Collections.unmodifiableList(tagidList);
    }

    public void setTagidList(List<Integer> tagidList) {
      this.tagidList = tagidList;
    }
  }
}
