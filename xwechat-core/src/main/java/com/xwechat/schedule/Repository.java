/**
 * @author yuanwq, date: 2017年8月16日
 */
package com.xwechat.schedule;

import java.io.IOException;

/**
 * @author yuanwq
 */
public interface Repository<V> {
  public V get(String appId) throws IOException;

  public void update(String appId, V value) throws IOException;

  public void delete(String appId) throws IOException;
}
