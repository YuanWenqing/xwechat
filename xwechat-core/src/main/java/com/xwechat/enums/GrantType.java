/**
 * @author yuanwq, date: 2017年7月22日
 */
package com.xwechat.enums;

import com.xwechat.core.IEnumParameter;

/**
 * @author yuanwq
 */
public enum GrantType implements IEnumParameter {
  /** oauth2使用code换access_token */
  AUTHORIZATION_CODE,
  /** oauth2使用refresh_token换新的access_token */
  REFRESH_TOKEN,
  /** 公众平台获取应用级通用access_token */
  CLIENT_CREDENTIAL;
}
