/**
 * @author yuanwq, date: 2017年8月16日
 */
package com.xwechat.schedule;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * @author yuanwq
 */
public class TaskLoop {
  private static final Logger logger = LoggerFactory.getLogger(TaskLoop.class);

  private final long size;
  private long curIdx = 0;
  private final Map<Long, Set<String>> idxTaskMap = Maps.newLinkedHashMap();
  private final Map<String, Long> taskIdxMap = Maps.newHashMap();

  public TaskLoop(long size) {
    Preconditions.checkArgument(size > 1);
    this.size = size;
    for (long i = 0; i < size; i++) {
      idxTaskMap.put(i, Sets.newConcurrentHashSet());
    }
  }

  /**
   * 清空当前游标对应的appId列表，并游标前进一步
   * 
   * @return 当前游标（未前进）对应的appId列表
   */
  public synchronized Collection<String> moveOn() {
    Collection<String> appIds = idxTaskMap.replace(curIdx, Sets.newConcurrentHashSet());
    curIdx = (curIdx + 1) % size;
    return appIds;
  }

  /**
   * @param aheadSteps 领先当前游标的步数, <0就是根据当前游标反向计算
   * @param appId
   */
  public void add(long aheadSteps, String appId) {
    aheadSteps = (aheadSteps % size + size) % size;
    synchronized (appId.intern()) {
      long idx = (curIdx + aheadSteps) % size;
      long oldIdx = taskIdxMap.getOrDefault(appId, idx);
      if (idx != oldIdx) {
        idxTaskMap.get(oldIdx).remove(appId);
      }
      idxTaskMap.get(idx).add(appId);
      taskIdxMap.put(appId, idx);
      logger.info("[addTask] curIdx={}, appId={}, scheduleIdx={}, size={}", curIdx, appId, idx,
          size);
    }
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(getClass()).add("size", size).add("curIdx", curIdx)
        .add("idx2task", idxTaskMap).add("task2idx", taskIdxMap).toString();
  }
}
