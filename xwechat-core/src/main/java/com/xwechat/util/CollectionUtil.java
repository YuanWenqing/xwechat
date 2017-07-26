/**
 * @author yuanwq, date: 2017年7月26日
 */
package com.xwechat.util;

import java.util.Collections;
import java.util.List;

/**
 * @author yuanwq
 */
public class CollectionUtil {

  /**
   * @param list
   * @return {@link Collections#emptyList()} if null, otherwise
   *         {@link Collections#unmodifiableList(List)}
   */
  public static <E> List<E> unmodifiableList(List<E> list) {
    return list == null ? Collections.emptyList() : Collections.unmodifiableList(list);
  }

}
