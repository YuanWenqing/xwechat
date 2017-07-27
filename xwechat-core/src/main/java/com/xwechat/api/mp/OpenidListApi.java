/**
 * @author yuanwq, date: 2017年7月26日
 */
package com.xwechat.api.mp;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.xwechat.api.Apis;
import com.xwechat.api.AuthorizedApi;
import com.xwechat.api.mp.OpenidListApi.OpenidListResponse;
import com.xwechat.core.IWechatResponse;
import com.xwechat.util.CollectionUtil;

/**
 * 公众号获取关注者用户列表（openid列表），每次最多10000个
 * 
 * @Note 公众平台
 * @url https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID
 * @see https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140840
 * @author yuanwq
 */
public class OpenidListApi extends AuthorizedApi<OpenidListResponse> {

  public OpenidListApi() {
    super(Apis.MP_OPENID_LIST);
  }

  /** 不填或为空或为null，则默认从第一个拉取 */
  public OpenidListApi setNextOpenid(String nextOpenid) {
    if (StringUtils.isNotBlank(nextOpenid)) {
      this.urlBuilder.setQueryParameter("next_openid", nextOpenid);
    }
    return this;
  }

  @Override
  public Class<OpenidListResponse> getResponseClass() {
    return OpenidListResponse.class;
  }

  public static class OpenidListResponse implements IWechatResponse {
    public static class Data {
      List<String> openid;

      public List<String> getOpenid() {
        return CollectionUtil.unmodifiableList(openid);
      }

      public void setOpenid(List<String> openid) {
        this.openid = openid;
      }
    }

    private int total;
    private int count;
    private Data data;
    private String nextOpenid;

    public int getTotal() {
      return total;
    }

    public void setTotal(int total) {
      this.total = total;
    }

    public int getCount() {
      return count;
    }

    public void setCount(int count) {
      this.count = count;
    }

    public Data getData() {
      return data;
    }

    public void setData(Data data) {
      this.data = data;
    }

    public String getNextOpenid() {
      return nextOpenid;
    }

    public void setNextOpenid(String nextOpenid) {
      this.nextOpenid = nextOpenid;
    }
  }
}
