package com.xwechat.api.wxapp;

import com.xwechat.BaseTest;
import com.xwechat.api.mp.UserPortraitApi;
import com.xwechat.core.Wechat;
import okhttp3.Response;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by shangqingzhe on 17/10/19.
 */
public class UserPortraitApiTest extends BaseTest {
  @Test
  public void testUserPortraitApi() throws IOException {
    UserPortraitApi api = new UserPortraitApi();
    Response response = Wechat.get().rawCall(api);
    System.out.println(response.body().toString());
  }
}
