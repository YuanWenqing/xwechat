package com.xwechat.api.wxapp;

import okhttp3.RequestBody;

import com.xwechat.api.Apis;
import com.xwechat.api.AuthorizedApi;
import com.xwechat.api.Method;
import com.xwechat.util.JsonUtil;

/**
 * 列添模板
 *
 * @Note 小程序api
 * @url https://api.weixin.qq.com/cgi-bin/wxopen/template/add?access_token=ACCESS_TOKEN
 * @see https://mp.weixin.qq.com/debug/wxadoc/dev/api/notice.html#模版消息管理
 * @author zqs
 */
public class TemplateAddApi extends AuthorizedApi<WxappApiResp> {

    public TemplateAddApi() {
        super(Apis.WXAPP_TEMPLATE_ADD, Method.POST);
    }

    public TemplateAddApi setParams(TemplateReq req) {
        setRequestBody(RequestBody.create(JSON_MEDIA_TYPE,
                JsonUtil.writeAsString(JsonUtil.DEFAULT_OBJECT_MAPPER, req)));
        return this;
    }

    @Override
    public Class<WxappApiResp> getResponseClass() {
        return WxappApiResp.class;
    }

}
