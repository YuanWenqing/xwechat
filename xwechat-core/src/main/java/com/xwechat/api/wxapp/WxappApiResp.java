package com.xwechat.api.wxapp;

import com.xwechat.core.IWechatResponse;

/**
 * Created by zqs on 2017/8/15. 小程序api返回结果通用
 */
public class WxappApiResp implements IWechatResponse {

  private int errcode;

  private String errmsg;

  public int getErrcode() {
    return errcode;
  }

  public void setErrcode(int errcode) {
    this.errcode = errcode;
  }

  public String getErrmsg() {
    return errmsg;
  }

  public void setErrmsg(String errmsg) {
    this.errmsg = errmsg;
  }
}
