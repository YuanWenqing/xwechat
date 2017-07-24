/**
 * @author yuanwq, date: 2017年7月24日
 */
package com.xwechat.api.jssdk;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

/**
 * @author yuanwq
 */
public class JsapiSigner {
  private String jsapiTicket;

  public JsapiSigner(String jsapiTicket) {
    reloadTicket(jsapiTicket);
  }

  public void reloadTicket(String jsapiTicket) {
    Preconditions.checkArgument(StringUtils.isNotBlank(jsapiTicket), "blank jsapi ticket");
    this.jsapiTicket = jsapiTicket;
  }

  /**
   * @param url
   * @see #sign(String, String, long)
   */
  public Signature sign(String url) {
    return sign(url, 0);
  }

  /**
   * @param url
   * @param timestamp 时间戳，秒，若{@code <=0}，默认为当前时间戳
   * @see #sign(String, String, long)
   */
  public Signature sign(String url, long timestamp) {
    return sign(url, null, timestamp);
  }

  /**
   * @param url
   * @param nonceStr 随机字符串，若为空，默认为随机16位字符串
   * @param timestamp 时间戳，秒，若{@code <=0}，默认为当前时间戳
   * @see #sign(String, String, long)
   */
  public Signature sign(String url, String nonceStr) {
    return sign(url, nonceStr, 0);
  }

  /**
   * @param url
   * @param nonceStr 随机字符串，若为空，默认为随机16位字符串
   * @param timestamp 时间戳，秒，若{@code <=0}，默认为当前时间戳
   */
  public Signature sign(String url, String nonceStr, long timestamp) {
    Preconditions.checkArgument(StringUtils.isNotBlank(url), "blank url");
    if (StringUtils.isBlank(nonceStr)) {
      nonceStr = RandomStringUtils.randomAlphanumeric(16);
    }
    if (timestamp <= 0) {
      timestamp = System.currentTimeMillis() / 1000;
    }
    Signature signature = new Signature();
    signature.jsapiTicket = jsapiTicket;
    signature.nonceStr = nonceStr;
    signature.timestamp = timestamp;
    signature.url = url;

    signature.string1 = String.format("jsapi_ticket=%s&noncestr=%s&timestamp=%d&url=%s",
        signature.jsapiTicket, signature.nonceStr, signature.timestamp, signature.url);
    signature.signature = DigestUtils.sha1Hex(signature.string1);
    return signature;
  }

  public static class Signature {
    private String jsapiTicket;
    private String nonceStr;
    /** second */
    private long timestamp;
    private String url;

    private String string1;
    private String signature;

    private Signature() {}

    public String getJsapiTicket() {
      return jsapiTicket;
    }

    public String getNonceStr() {
      return nonceStr;
    }

    public long getTimestamp() {
      return timestamp;
    }

    public String getUrl() {
      return url;
    }

    public String getString1() {
      return string1;
    }

    public String getSignature() {
      return signature;
    }

    @Override
    public String toString() {
      return MoreObjects.toStringHelper(getClass()).add("jaspi_ticket", jsapiTicket)
          .add("noncestr", nonceStr).add("timestamp", timestamp).add("url", url)
          .add("string1", string1).add("signature", signature).toString();
    }
  }

}
