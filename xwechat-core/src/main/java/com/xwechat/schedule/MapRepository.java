/**
 * @author yuanwq, date: 2017年8月16日
 */
package com.xwechat.schedule;

import com.google.common.collect.Maps;
import com.xwechat.core.Application;

import java.util.Collections;
import java.util.Map;

/**
 * @author yuanwq
 */
public class MapRepository implements AppRepository {
  private final Map<String, Application> map = Maps.newLinkedHashMap();

  @Override
  public void saveApplication(Application application) {
    synchronized (map) {
      map.put(application.getAppId(), application);
    }
  }

  @Override
  public Application getApplication(String appId) {
    return map.get(appId);
  }

  @Override
  public void delete(String appId) {
    map.remove(appId);
  }

  @Override
  public Map<String, Application> all() {
    return Collections.unmodifiableMap(map);
  }

  @Override
  public String toString() {
    return map.toString();
  }

  @Override
  public int hashCode() {
    return map.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof MapRepository) {
      return this.map.equals(((MapRepository) obj).map);
    }
    return false;
  }
}
