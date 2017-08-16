/**
 * @author yuanwq, date: 2017年8月16日
 */
package com.xwechat.schedule;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * @author yuanwq
 */
public class MapRepository<V> implements Repository<V> {
  private final Map<String, V> map = Maps.newLinkedHashMap();

  @Override
  public V get(String appId) {
    return map.get(appId);
  }

  @Override
  public void update(String appId, V value) {
    map.put(appId, value);
  }

  @Override
  public void delete(String appId) {
    map.remove(appId);
  }
}
