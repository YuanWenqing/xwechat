/**
 * @author yuanwq, date: 2017年8月16日
 */
package com.xwechat.schedule;

/**
 * @author yuanwq
 */
public class ExpirableValue {
  private String value;
  private long createTime;
  private long expireTime;

  /** for deserialization */
  public ExpirableValue() {}

  public ExpirableValue(String value, long expireTime) {
    this.value = value;
    this.createTime = System.currentTimeMillis();
    this.expireTime = expireTime;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public long getCreateTime() {
    return createTime;
  }

  public void setCreateTime(long createTime) {
    this.createTime = createTime;
  }

  public long getExpireTime() {
    return expireTime;
  }

  public void setExpireTime(long expireTime) {
    this.expireTime = expireTime;
  }
}
