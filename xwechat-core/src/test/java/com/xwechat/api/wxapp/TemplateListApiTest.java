package com.xwechat.api.wxapp;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import okhttp3.Response;

import com.google.common.collect.Lists;
import com.xwechat.BaseTest;
import com.xwechat.core.Wechat;

/**
 * Created by zqs on 2017/8/21. 模板相关api测试，需要事先设置token
 */
public class TemplateListApiTest extends BaseTest {
  private String token;

  @Before
  public void init() {
    token =
        "GoJ8ZkOnk4HWFby6uEJoCHBGJANPxsibSetBa_XVjS9gGHdTTZTqlkyh-K62CWFp9holsaNpho9rJa7F6m1l5LCADQ8evNDd4nFGi71N1EKZwcdgmOKVCpUS-fDyFuu5JTGhAAAXHU";
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
    req.setId("AT0009");
    api.setParams(req);
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
    req.setId("AT0009");
    req.setKeyword_id_list(Lists.newArrayList(9, 11, 10));
    api.setParams(req);
    try {
      Response response = Wechat.get().rawCall(api);
      System.out.println(response.body().string());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
