/**
 * @author yuanwq, date: 2017年8月16日
 */
package com.xwechat.schedule;

import com.xwechat.core.Application;

import java.util.Map;

/**
 * @author yuanwq
 */
public interface AppRepository {
  public void saveApplication(Application application);

  public Application getApplication(String appId);

  public void delete(String appId);

  public Map<String, Application> all();
}
