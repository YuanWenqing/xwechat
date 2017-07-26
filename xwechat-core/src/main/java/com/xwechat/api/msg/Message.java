/**
 * @author yuanwq, date: 2017年7月26日
 */
package com.xwechat.api.msg;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import com.xwechat.util.JsonUtil;

/**
 * @author yuanwq
 */
public class Message {
  private final MessageType type;

  private boolean toAll = false;
  private Integer tagId = -1;
  private Set<String> openids = Sets.newLinkedHashSet();

  private String mediaId;
  private String textContent;
  private String cardId;

  private String title;
  private String description;
  private String thumbMediaId;
  private int sendIgnoreReprint = 0;

  public Message(MessageType type) {
    this.type = type;
  }

  public MessageType getType() {
    return type;
  }

  public boolean isToAll() {
    return toAll;
  }

  /**
   * 设为true将覆盖tagId和openids的设置: {@code tagId=null, openids.clear()}<br>
   * 
   * @default false
   */
  public void setToAll(boolean toAll) {
    this.toAll = toAll;
    if (toAll) {
      this.tagId = null;
      this.openids.clear();
    }
  }

  public Integer getTagId() {
    return tagId;
  }

  /** 将覆盖toAll和openids的设置：{@code toAll=false, openids.clear()} */
  public void setTagId(int tagId) {
    Preconditions.checkArgument(tagId > 0);
    this.tagId = tagId;
    this.toAll = false;
    this.openids.clear();
  }

  /** 将覆盖toAll和tagId设置：{@code toAll=false, tagId=null} */
  public void addOpenids(Collection<String> openids) {
    Preconditions.checkArgument(openids != null && !openids.isEmpty());
    this.openids.addAll(openids);
    this.toAll = false;
    this.tagId = null;
  }

  public Set<String> getOpenids() {
    return Collections.unmodifiableSet(openids);
  }

  public String getMediaId() {
    return mediaId;
  }

  public void setMediaId(String mediaId) {
    this.mediaId = mediaId;
  }

  public String getTextContent() {
    return textContent;
  }

  public void setTextContent(String textContent) {
    this.textContent = textContent;
  }

  public String getCardId() {
    return cardId;
  }

  public void setCardId(String cardId) {
    this.cardId = cardId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getThumbMediaId() {
    return thumbMediaId;
  }

  public void setThumbMediaId(String thumbMediaId) {
    this.thumbMediaId = thumbMediaId;
  }

  public int getSendIgnoreReprint() {
    return sendIgnoreReprint;
  }

  public void setSendIgnoreReprint(int sendIgnoreReprint) {
    this.sendIgnoreReprint = sendIgnoreReprint;
  }

  @Override
  public String toString() {
    return JsonUtil.writeAsString(JsonUtil.DEFAULT_OBJECT_MAPPER, this);
  }

  public String toJson() {
    return JsonUtil.writeAsString(JsonUtil.DEFAULT_OBJECT_MAPPER, toJsonNode());
  }

  private ObjectNode toJsonNode() {
    ObjectNode root = JsonUtil.DEFAULT_OBJECT_MAPPER.createObjectNode();
    if (toAll) {
      root.putObject("filter").put("is_to_all", true);
    } else if (tagId != null) {
      root.putObject("filter").put("is_to_all", false).put("tag_id", tagId.intValue());
    } else if (!openids.isEmpty()) {
      ArrayNode arr = root.putArray("touser");
      openids.forEach(o -> arr.add(o));
    } else {
      throw new IllegalStateException("no set target(all/tagId/openid)");
    }
    root.put("msgtype", type.asParameter());
    switch (type) {
      case TEXT:
        Preconditions.checkArgument(StringUtils.isNotBlank(textContent), "blank text content");
        root.putObject(type.asParameter()).put("content", textContent);
        break;
      case MPNEWS:
      case VOICE:
      case IMAGE:
      case MPVIDEO:
        Preconditions.checkArgument(StringUtils.isNotBlank(mediaId), "blank mediaId");
        root.putObject(type.asParameter()).put("media_id", mediaId);
        break;
      case WXCARD:
        Preconditions.checkArgument(StringUtils.isNotBlank(cardId), "blank cardId");
        root.putObject(type.asParameter()).put("card_id", cardId);
        break;
      default:
        break;
    }
    root.put("title", title).put("description", description).put("thumb_media_id", thumbMediaId)
        .put("send_ignore_reprint", sendIgnoreReprint);
    return root;
  }

}
