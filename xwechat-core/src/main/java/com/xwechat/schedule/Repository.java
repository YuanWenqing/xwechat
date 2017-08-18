/**
 * @author yuanwq, date: 2017年8月16日
 */
package com.xwechat.schedule;

import java.util.Map;

/**
 * @author yuanwq
 */
public interface Repository<V> {
  public V get(String appId);

  public void update(String appId, V value);

  public void delete(String appId);

  public Map<String, V> all();
}
