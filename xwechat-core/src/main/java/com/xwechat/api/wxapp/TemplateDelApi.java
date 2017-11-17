package com.xwechat.api.wxapp;

/**
 * Created by zqs on 2017/11/16.
 */

import okhttp3.RequestBody;

import com.xwechat.api.Apis;
import com.xwechat.api.AuthorizedApi;
import com.xwechat.api.Method;
import com.xwechat.util.JsonUtil;

/**
 * 删除帐号下的某个模板
 *
 * @Note 小程序api
 * @url https://api.weixin.qq.com/cgi-bin/wxopen/template/del?access_token=ACCESS_TOKEN
 * @see https://mp.weixin.qq.com/debug/wxadoc/dev/api/notice.html#模版消息管理
 * @author zqs
 */
public class TemplateDelApi extends AuthorizedApi<WxappApiResp> {
    public TemplateDelApi() {
        super(Apis.WXAPP_TEMPLATE_DEL, Method.POST);
    }

    public TemplateDelApi setParams(TemplateReq req) {
        setRequestBody(RequestBody.create(JSON_MEDIA_TYPE,
                JsonUtil.writeAsString(JsonUtil.DEFAULT_OBJECT_MAPPER, req)));
        return this;
    }

    @Override
    public Class<WxappApiResp> getResponseClass() {
        return WxappApiResp.class;
    }
}

