/**
 * @author yuanwq, date: 2017年7月23日
 */
package com.xwechat.enums;

import com.xwechat.core.IEnumParameter;

/**
 * 获取各类ticket接口的ticket参数
 * 
 * @author yuanwq
 */
public enum TicketType implements IEnumParameter {
  /** 公众号js sdk用的jsapi ticket */
  JSAPI,
  /** 微信卡券用的ticket类型 */
  WX_CARD;

}
