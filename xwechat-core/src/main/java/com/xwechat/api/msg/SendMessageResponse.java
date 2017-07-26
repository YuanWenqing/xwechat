/**
 * @author yuanwq, date: 2017年7月26日
 */
package com.xwechat.api.msg;

import com.xwechat.core.IWechatResponse;

/**
 * @author yuanwq
 */
public class SendMessageResponse implements IWechatResponse {
  private int errcode;
  private int errmsg;

  private String type;
  private int msgId;
  private int msgDataId;

  public int getErrcode() {
    return errcode;
  }

  public void setErrcode(int errcode) {
    this.errcode = errcode;
  }

  public int getErrmsg() {
    return errmsg;
  }

  public void setErrmsg(int errmsg) {
    this.errmsg = errmsg;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public int getMsgId() {
    return msgId;
  }

  public void setMsgId(int msgId) {
    this.msgId = msgId;
  }

  public int getMsgDataId() {
    return msgDataId;
  }

  public void setMsgDataId(int msgDataId) {
    this.msgDataId = msgDataId;
  }

}
