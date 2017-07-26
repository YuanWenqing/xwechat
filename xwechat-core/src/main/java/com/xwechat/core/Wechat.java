/**
 * @author yuanwq, date: 2017年7月22日
 */
package com.xwechat.core;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy;

import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * 微信api请求的执行器，包含httpClient、线程池等。所有微信请求都从这里发起
 * 
 * @author yuanwq
 */
public class Wechat {
  private static final Logger logger = LoggerFactory.getLogger(Wechat.class);

  private static class Holder {
    private static final Wechat instance = new Wechat();
  }

  public static Wechat get() {
    return Holder.instance;
  }

  private final OkHttpClient httpClient;
  private final ObjectMapper objectMapper;

  // private final ExecutorService executorService;

  private Wechat() {
    this.httpClient = new OkHttpClient();
    this.objectMapper = new ObjectMapper();
    // 变量的驼峰命名和json中的下划线命名映射
    this.objectMapper.setPropertyNamingStrategy(new LowerCaseWithUnderscoresStrategy());
    // 避免接口变动导致映射出错，所以忽略未知的字段
    this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    // ThreadFactory threadFactory = new ThreadFactoryBuilder().setDaemon(true)
    // .setNameFormat("wechat-%d").setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
    // @Override
    // public void uncaughtException(Thread t, Throwable e) {
    // logger.error("encouter error in wechat thread " + t.getName(), e);
    // }
    // }).build();
    // this.executorService = Executors.newCachedThreadPool(threadFactory);
  }

  public OkHttpClient getHttpClient() {
    return httpClient;
  }

  public Response rawCall(IWechatApi<?> request) throws IOException {
    return httpClient.newCall(request.toOkHttpRequest()).execute();
  }

  public <R extends IWechatResponse> ResponseWrapper<R> call(IWechatApi<R> request)
      throws IOException {
    Response rawResponse = rawCall(request);
    ResponseWrapper<R> wrapper = new ResponseWrapper<>(rawResponse.body().string());
    parseResponse(wrapper, request.getResponseClass());
    return wrapper;
  }

  private <R extends IWechatResponse> void parseResponse(ResponseWrapper<R> wrapper,
      Class<R> responseClass) throws IOException {
    try {
      JsonNode root = objectMapper.readTree(wrapper.getBody());
      if (root.has("errcode")) {
        wrapper.setErrcode(root.get("errcode").asInt());
        wrapper.setErrmsg(root.get("errmsg").asText());
        return;
      }
      R response = objectMapper.reader(responseClass).readValue(root);
      wrapper.setResponse(response);
    } catch (IOException e) {
      logger.warn("fail to parse json response, responseClass=" + responseClass + ", text="
          + wrapper.getBody(), e);
      throw e;
    }
  }
}
