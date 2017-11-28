/**
 * @author yuanwq, date: 2017年8月16日
 */
package com.xwechat.core;

import org.joda.time.DateTime;

/**
 * @author yuanwq
 */
public class ExpirableValue {
  private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

  private static String formatTime(long timeMillis) {
    return new DateTime(timeMillis).toString(DATETIME_FORMAT);
  }

  private String value;
  private long createTime;
  private long expireTime;

  /**
   * for deserialization
   */
  public ExpirableValue() {
  }

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

  @Override
  public String toString() {
    return String.format("%s[%s ~ %s]", value, formatTime(createTime), formatTime(expireTime));
  }

}
