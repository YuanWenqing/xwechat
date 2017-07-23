/**
 * @author yuanwq, date: 2017年7月22日
 */
package com.xwechat.def;

/**
 * @author yuanwq
 */
public enum GrantType {
  /** oauth2使用code换access_token */
  AUTHORIZATION_CODE,
  /** oauth2使用refresh_token换新的access_token */
  REFRESH_TOKEN,
  /** 公众平台获取应用级通用access_token */
  CLIENT_CREDENTIAL;

  public String asParameter() {
    return name().toLowerCase();
  }
}
