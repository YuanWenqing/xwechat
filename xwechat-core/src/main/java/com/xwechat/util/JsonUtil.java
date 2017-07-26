/**
 * @author yuanwq, date: 2017年7月26日
 */
package com.xwechat.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

/**
 * @author yuanwq
 */
public abstract class JsonUtil {
  public static final ObjectMapper COMMON_OBJECT_MAPPER = new ObjectMapper();

  public static String writeAsString(ObjectMapper objectMapper, Object value) {
    try {
      ObjectWriter objectWriter = objectMapper.writer();
      return objectWriter.writeValueAsString(value);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("never here, value=" + value, e);
    }
  }

  public static String writeAsPrettyString(ObjectMapper objectMapper, Object value) {
    try {
      ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
      return objectWriter.writeValueAsString(value);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("never here, value=" + value, e);
    }
  }
}
