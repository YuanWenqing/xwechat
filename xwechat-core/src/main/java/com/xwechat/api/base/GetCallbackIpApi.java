/**
 * @author yuanwq, date: 2017年7月26日
 */
package com.xwechat.api.base;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Preconditions;
import com.xwechat.api.Apis;
import com.xwechat.api.base.GetCallbackIpApi.GetCallbackIpResponse;
import com.xwechat.core.AbstractWechatApi;
import com.xwechat.core.AbstractWechatResponse;

/**
 * @author yuanwq
 */
public class GetCallbackIpApi extends AbstractWechatApi<GetCallbackIpResponse> {

  public GetCallbackIpApi() {
    super(Apis.GET_CALLBACK_IP);
  }

  public GetCallbackIpApi setAccessToken(String accessToken) {
    Preconditions.checkArgument(StringUtils.isNotBlank(accessToken), "blank accessToken");
    this.urlBuilder.setQueryParameter("access_token", accessToken);
    return this;
  }

  @Override
  public Class<GetCallbackIpResponse> getResponseClass() {
    return GetCallbackIpResponse.class;
  }

  public static class GetCallbackIpResponse extends AbstractWechatResponse {
    private List<String> ipList;

    public List<String> getIpList() {
      return ipList == null ? Collections.emptyList() : Collections.unmodifiableList(ipList);
    }

    public void setIpList(List<String> ipList) {
      this.ipList = ipList;
    }
  }

}
