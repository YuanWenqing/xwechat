/**
 * @author yuanwq, date: 2017年8月18日
 */
package com.xwechat.schedule;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 每个操作都log，用于测试
 * 
 * @author yuanwq
 */
public class LoggableRepository<V> extends MapRepository<V> implements Repository<V> {
  private static final Logger logger = LoggerFactory.getLogger(LoggableRepository.class);

  private final String name;

  public LoggableRepository(String name) {
    this.name = name;
  }

  @Override
  public V get(String appId) {
    V value = super.get(appId);
    logger.info("[get {}] appId={}, value={}", name, appId, value);
    return value;
  }

  @Override
  public void update(String appId, V value) {
    super.update(appId, value);
    logger.info("[update {}] appId={}, value={}", name, appId, value);
  }

  @Override
  public void delete(String appId) {
    super.delete(appId);
    logger.info("[delete {}] appId={}", name, appId);
  }

  @Override
  public Map<String, V> all() {
    return super.all();
  }

}
