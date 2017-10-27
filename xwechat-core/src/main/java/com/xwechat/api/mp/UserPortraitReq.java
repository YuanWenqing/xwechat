package com.xwechat.api.mp;

/**
 * 查询用户画像请求
 * 结束日期，开始日期与结束日期相差的天数限定为0/6/29，分别表示查询最近1/7/30天数据，end_date允许设置的最大值为昨日。
 * 例: "begin_date" : "2017-06-11" , "end_date" : "2017-06-17"
 * Created by shangqingzhe on 17/10/19.
 */
public class UserPortraitReq {
  private String begin_date;
  private String end_date;

  public String getBegin_date() {
    return begin_date;
  }

  public void setBegin_date(String begin_date) {
    this.begin_date = begin_date;
  }

  public String getEnd_date() {
    return end_date;
  }

  public void setEnd_date(String end_date) {
    this.end_date = end_date;
  }
}
