/**
 * @author yuanwq, date: 2017年7月26日
 */
package com.xwechat.api.mp;

import java.util.Collections;
import java.util.List;

/**
 * @author yuanwq
 */
public class UserInfo {
  private String openid;
  private String nickname;
  private int sex;
  private String language;
  private String city;
  private String province;
  private String country;
  private String headimgurl;
  private String unionid;

  private int subscribe;
  private long subscribeTime;
  private String remark;
  private int groupid;
  private List<Integer> tagidList;

  public int getSubscribe() {
    return subscribe;
  }

  public void setSubscribe(int subscribe) {
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
