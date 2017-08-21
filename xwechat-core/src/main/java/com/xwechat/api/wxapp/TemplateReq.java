package com.xwechat.api.wxapp;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by zqs on 2017/8/21. 模板请求
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TemplateReq {
  /** offset和count用于分页，表示从offset开始，拉取count条记录，offset从0开始，count最大为20。最后一页的list长度可能小于请求的count */
  private Integer offset;
  private Integer count;

  /** 模板标题id，可通过接口获取，也可登录小程序后台查看获取 */
  private String id;

  /** 开发者自行组合好的模板关键词列表，关键词顺序可以自由搭配（例如[3,5,4]或[4,5,3]），最多支持10个关键词组合*/
  private List<Integer> keyword_id_list;

  public List<Integer> getKeyword_id_list() {
    return keyword_id_list;
  }

  public void setKeyword_id_list(List<Integer> keyword_id_list) {
    this.keyword_id_list = keyword_id_list;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Integer getOffset() {
    return offset;
  }

  public void setOffset(Integer offset) {
    this.offset = offset;
  }

  public Integer getCount() {
    return count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }
}
