/**
 * @author yuanwq, date: 2017年8月16日
 */
package com.xwechat.schedule;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import com.xwechat.enums.TicketType;
import com.xwechat.util.JsonUtil;

/**
 * @author yuanwq
 */
public class TaskDef {
  private String appId;
  private String appSecret;
  private long createTime;
  private final Set<TicketType> ticketTypes = Sets.newLinkedHashSet();
  private long executeTime = 0L;
  private long expireTime = 0L;

  /** for deserialization */
  public TaskDef() {}

  public TaskDef(String appId, String appSecret) {
    Preconditions.checkArgument(StringUtils.isNotBlank(appId), "blank appId");
    Preconditions.checkArgument(StringUtils.isNotBlank(appSecret), "blank appSecret");
    this.appId = appId;
    this.appSecret = appSecret;
    this.createTime = System.currentTimeMillis();
  }

  public String getAppId() {
    return appId;
  }

  public void setAppId(String appId) {
    this.appId = appId;
  }

  public String getAppSecret() {
    return appSecret;
  }

  public void setAppSecret(String appSecret) {
    this.appSecret = appSecret;
  }

  public long getCreateTime() {
    return createTime;
  }

  public void setCreateTime(long createTime) {
    this.createTime = createTime;
  }

  public void addTicketType(TicketType type) {
    ticketTypes.add(type);
  }

  public void addTicketTypes(Collection<TicketType> ticketTypes) {
    this.ticketTypes.addAll(ticketTypes);
  }

  public Collection<TicketType> getTicketTypes() {
    return Collections.unmodifiableCollection(ticketTypes);
  }

  public void setExecuteTime(long executeTime) {
    this.executeTime = executeTime;
  }

  public long getExecuteTime() {
    return executeTime;
  }

  public void setExpireTime(long expireTime) {
    this.expireTime = expireTime;
  }

  public long getExpireTime() {
    return expireTime;
  }

  @Override
  public String toString() {
    return JsonUtil.writeAsString(JsonUtil.DEFAULT_OBJECT_MAPPER, this);
  }
}
