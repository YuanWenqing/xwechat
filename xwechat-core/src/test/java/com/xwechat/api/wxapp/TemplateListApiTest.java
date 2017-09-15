package com.xwechat.api.wxapp;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import okhttp3.Response;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;
import com.xwechat.BaseTest;
import com.xwechat.core.Wechat;
import com.xwechat.util.JsonUtil;

/**
 * Created by zqs on 2017/8/21. 模板相关api测试，需要事先设置token
 */
public class TemplateListApiTest extends BaseTest {
  private String token;

  @Before
  public void init() {
    token =
            "47RyGUTvcYL7U1iMOv0BaR168mv92aW-n_Ol-dI9Y6CEZe6fKHpzIt_HBq6YLAZ0YIrm16a-xmFCmWMOgpGwve6kKonV0pteo92jqyAggecQSLcAAAEGZ";
  }

  @Test
  public void testTemplateList() {
    TemplateListApi api = new TemplateListApi();
    api.setAccessToken(token);
    TemplateReq req = new TemplateReq();
    req.setCount(20);
    req.setOffset(0);
    api.setParams(req);
    try {
      Response response = Wechat.get().rawCall(api);
      System.out.println(response.body().string());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testTemplateLibGet() {
    TemplateLibGetApi api = new TemplateLibGetApi();
    api.setAccessToken(token);
    TemplateReq req = new TemplateReq();
//    req.setId("AT0009"); paid
//    req.setId("AT0229");  buy suc
//    req.setId("AT0007"); // sent
//    api.setParams(req);
    try {
      Response response = Wechat.get().rawCall(api);
      System.out.println(response.body().string());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testTemplateAdd() {
    TemplateAddApi api = new TemplateAddApi();
    api.setAccessToken(token);
    TemplateReq req = new TemplateReq();
//    req.setId("AT0009");
//    req.setKeyword_id_list(Lists.newArrayList(9, 11));


//    req.setId("AT0229");
//    req.setKeyword_id_list(Lists.newArrayList(2, 16, 18));
//    buy suc


//    req.setId("AT0007");
//    req.setKeyword_id_list(Lists.newArrayList(7, 59, 80));


    api.setParams(req);
    try {
      Response response = Wechat.get().rawCall(api);




      System.out.println(response.body().string());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }





}

