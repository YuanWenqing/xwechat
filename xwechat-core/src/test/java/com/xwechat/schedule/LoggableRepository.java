/**
 * @author yuanwq, date: 2017年8月18日
 */
package com.xwechat.schedule;

import com.xwechat.core.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 每个操作都log，用于测试
 *
 * @author yuanwq
 */
public class LoggableRepository extends MapRepository implements AppRepository {
  private static final Logger logger = LoggerFactory.getLogger(LoggableRepository.class);

  public LoggableRepository() {
  }

  @Override
  public void saveApplication(Application application) {
    super.saveApplication(application);
    logger.info("[save application] {}", application);
  }

  @Override
  public Application getApplication(String appId) {
    Application value = super.getApplication(appId);
    logger.info("[get application] appId={}, application={}", appId, value);
    return value;
  }

  @Override
  public void delete(String appId) {
    super.delete(appId);
    logger.info("[delete application] appId={}", appId);
  }

  @Override
  public Map<String, Application> all() {
    return super.all();
  }

}
