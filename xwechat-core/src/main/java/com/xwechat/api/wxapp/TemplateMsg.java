package com.xwechat.api.wxapp;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Maps;
import com.xwechat.util.JsonUtil;

/**
 * Created by zqs on 2017/8/15. 模板消息
 */
public class TemplateMsg {
  // 接收者（用户）的 openid
  private String touser;
  // 所需下发的模板消息的id
  private String template_id;
  // 点击模板卡片后的跳转页面，仅限本小程序内的页面。支持带参数,（示例index?foo=bar）。该字段不填则模板无跳转。
  private String page;
  // 表单提交场景下，为 submit 事件带上的 formId；支付场景下，为本次支付的 prepay_id
  private String form_id;
  // 模板内容，不填则下发空模板
  private Map<String, Keyword> data = Maps.newHashMapWithExpectedSize(5);
  // 模板内容字体的颜色，不填默认黑色
  private String color;
  // 模板需要放大的关键词，不填则默认无放大
  private String emphasis_keyword;

  @JsonIgnore
  private int keywordIdxCount = 1;

  private String generateKey() {
    return "keyword" + keywordIdxCount++;
  }

  public TemplateMsg addKeyword(String value) {
    return addKeyword(new Keyword(value, StringUtils.EMPTY));
  }

  public TemplateMsg addKeyword(String value, String color) {
    return addKeyword(new Keyword(value, color));
  }

  private TemplateMsg addKeyword(Keyword keyword) {
    data.put(generateKey(), keyword);
    return this;
  }

  public static class Keyword {
    String value;
    String color;

    public Keyword(String value, String color) {
      this.value = value;
      this.color = color;
    }
  }

  public String getTouser() {
    return touser;
  }

  public void setTouser(String touser) {
    this.touser = touser;
  }

  public String getTemplate_id() {
    return template_id;
  }

  public void setTemplate_id(String template_id) {
    this.template_id = template_id;
  }

  public String getPage() {
    return page;
  }

  public void setPage(String page) {
    this.page = page;
  }

  public String getForm_id() {
    return form_id;
  }

  public void setForm_id(String form_id) {
    this.form_id = form_id;
  }

  public Map<String, Keyword> getData() {
    return data;
  }

  public void setData(Map<String, Keyword> data) {
    this.data = data;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public String getEmphasis_keyword() {
    return emphasis_keyword;
  }

  public void setEmphasis_keyword(String emphasis_keyword) {
    this.emphasis_keyword = emphasis_keyword;
  }
}
