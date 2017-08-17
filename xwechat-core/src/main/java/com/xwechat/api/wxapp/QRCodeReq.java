package com.xwechat.api.wxapp;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by zqs on 2017/8/17. 二维码请求参数
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QRCodeReq {
  // // 最大32个可见字符，只支持数字，大小写英文以及部分特殊字符：!#$&'()*+,/:;=?@-._~，其它字符请自行编码为合法字符（因不支持%，中文无法使用 urlencode
  // // 处理，请使用其他编码方式）
  protected String scene;
  protected String page;
  // 二维码的宽度
  protected int width;
  // 自动配置线条颜色，如果颜色依然是黑色，则说明不建议配置主色调
  protected boolean auto_color;
  // {"r":"0","g":"0","b":"0"} auth_color 为 false 时生效，使用 rgb 设置颜色 例如 {"r":"xxx","g":"xxx","b":"xxx"}
  protected Map<String, String> line_color;

  public String getScene() {
    return scene;
  }

  public void setScene(String scene) {
    this.scene = scene;
  }

  public String getPage() {
    return page;
  }

  public void setPage(String page) {
    this.page = page;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public boolean isAuto_color() {
    return auto_color;
  }

  public void setAuto_color(boolean auto_color) {
    this.auto_color = auto_color;
  }

  public Map<String, String> getLine_color() {
    return line_color;
  }

  public void setLine_color(Map<String, String> line_color) {
    this.line_color = line_color;
  }
}
