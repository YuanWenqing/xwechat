/**
 * @author yuanwq, date: 2017年7月23日
 */
package com.xwechat.core;

/**
 * 微信api中作为参数的枚举值定义的基本接口
 * 
 * @author yuanwq
 */
public interface IEnumParameter {
  public String name();

  /** 作为url参数的值 */
  default public String asParameter() {
    return name().toLowerCase();
  }

}
