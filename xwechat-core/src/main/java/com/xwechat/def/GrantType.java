/**
 * @author yuanwq, date: 2017年7月22日
 */
package com.xwechat.def;

/**
 * @author yuanwq
 */
public enum GrantType {
  AUTHORIZATION_CODE, REFRESH_TOKEN;

  public String asParameter() {
    return name().toLowerCase();
  }
}
