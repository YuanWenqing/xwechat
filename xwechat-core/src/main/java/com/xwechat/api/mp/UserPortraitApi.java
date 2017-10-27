package com.xwechat.api.mp;

import com.xwechat.api.Apis;
import com.xwechat.api.AuthorizedApi;
import com.xwechat.api.Method;
import com.xwechat.api.mp.UserPortraitApi.UserPortraitResponse;
import com.xwechat.core.IWechatResponse;
import com.xwechat.util.JsonUtil;
import okhttp3.RequestBody;

/**
 * 获取用户画像-小程序使用
 * Created by shangqingzhe on 17/10/19.
 */
public class UserPortraitApi extends AuthorizedApi<UserPortraitResponse> {

  public UserPortraitApi() {
    super(Apis.MP_USER_PORTRAIT, Method.POST);
  }

  public UserPortraitApi setMessage(UserPortraitReq req) {
    setRequestBody(RequestBody
        .create(JSON_MEDIA_TYPE, JsonUtil.writeAsString(JsonUtil.DEFAULT_OBJECT_MAPPER, req)));
    return this;
  }

  @Override
  public Class<UserPortraitResponse> getResponseClass() {
    return UserPortraitResponse.class;
  }

  public static class UserPortraitResponse extends UserPortrait implements IWechatResponse{

  }
}
