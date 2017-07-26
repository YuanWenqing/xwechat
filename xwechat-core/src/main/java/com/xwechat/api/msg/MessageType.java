/**
 * @author yuanwq, date: 2017年7月26日
 */
package com.xwechat.api.msg;

import com.xwechat.core.IEnumParameter;

/**
 * 消息类型
 * 
 * @author yuanwq
 */
public enum MessageType implements IEnumParameter {
  /** 图文消息 */
  MPNEWS,
  /** 文本消息 */
  TEXT,
  /** 语音、音频消息 */
  VOICE,
  /** 图片消息 */
  IMAGE,
  /** 视频消息 */
  MPVIDEO,
  /** 卡券消息 */
  WXCARD;

}
