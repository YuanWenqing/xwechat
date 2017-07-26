/**
 * @author yuanwq, date: 2017年7月26日
 */
package com.xwechat.api.mp;

import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Preconditions;
import com.xwechat.api.Apis;
import com.xwechat.api.AuthorizedApi;
import com.xwechat.api.mp.UserInfoBatchApi.MpUserInfoBatchResponse;
import com.xwechat.core.IWechatResponse;
import com.xwechat.util.CollectionUtil;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 批量获取用户基本信息，公众号和小程序使用
 * 
 * @Note 公众平台
 * @url https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=ACCESS_TOKEN
 * @see https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140839
 * @author yuanwq
 */
public class UserInfoBatchApi extends AuthorizedApi<MpUserInfoBatchResponse> {

  private final ObjectNode root;

  public UserInfoBatchApi() {
    super(Apis.MP_USERINFO_BATCH);
    root = API_OBJECT_MAPPER.createObjectNode();
    root.putArray("user_list");
  }

  public UserInfoBatchApi addAllOpenId(Collection<String> openIds) {
    Preconditions.checkArgument(openIds != null && !openIds.isEmpty(), "empty openIds");
    ArrayNode userList = root.withArray("user_list");
    for (String openId : openIds) {
      userList.addObject().put("openid", openId).put("lang", "zh_CN");
    }
    String content = writeJsonAsString(root);
    this.requestBuilder.post(RequestBody.create(MediaType.parse("text/json"), content));
    return this;
  }

  public String getPrettyBody() {
    return writeJsonAsString(root, true);
  }

  @Override
  public Class<MpUserInfoBatchResponse> getResponseClass() {
    return MpUserInfoBatchResponse.class;
  }

  public static class MpUserInfoBatchResponse implements IWechatResponse {
    private List<MpUserInfo> userInfoList;

    public void setUserInfoList(List<MpUserInfo> userInfoList) {
      this.userInfoList = userInfoList;
    }

    public List<MpUserInfo> getUserInfoList() {
      return CollectionUtil.unmodifiableList(userInfoList);
    }
  }
}
