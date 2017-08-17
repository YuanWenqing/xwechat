package com.xwechat.api.wxapp;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import okhttp3.RequestBody;
import okhttp3.Response;

import com.xwechat.api.Apis;
import com.xwechat.api.AuthorizedApi;
import com.xwechat.api.Method;
import com.xwechat.core.Wechat;
import com.xwechat.util.JsonUtil;

/**
 * 获取小程序二维码
 *
 * @Note 适用于需要的码数量较少的业务场景，每个appid最多10w次
 * @url https://api.weixin.qq.com/wxa/getwxacode?access_token=ACCESS_TOKEN
 * @see https://mp.weixin.qq.com/debug/wxadoc/dev/api/qrcode.html
 * @author zqs
 */
public class QRCodeApi extends AuthorizedApi<WxappApiResp> {

  public QRCodeApi() {
    super(Apis.WXAPP_QR_CODE_SEND, Method.POST);
  }

  public QRCodeApi setMessage(QRCodeReq req) {
    setRequestBody(RequestBody.create(JSON_MEDIA_TYPE,
        JsonUtil.writeAsString(JsonUtil.DEFAULT_OBJECT_MAPPER, req)));
    return this;
  }

  @Override
  public Class<WxappApiResp> getResponseClass() {
    return WxappApiResp.class;
  }

  public static void main(String[] args) {
    String token =
        "LXymL_c1KI8_AKv77CqhrBLPoOXbjByiPOOIO32tb8CFDx-bPKFKxQvkjTFhW-X7IifTtbViK2fgwJymHCYxphx1IXNQfANmk3RdpFjtAI2fkkYTImiLHKHhp4TP3SZ6XILhAIASDG";
    QRCodeApi api = new QRCodeApi();
    QRCodeReq msg = new QRCodeReq();
    msg.setScene("abcdef");
    msg.setPage("pages/kol-home/kol-home");
    api.setMessage(msg);
    api.setAccessToken(token);
    try {
      Response response = Wechat.get().rawCall(api);
      InputStream inputStream = response.body().byteStream();
      Files.copy(inputStream, Paths.get("~/Desktop/a.jpg"), StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
