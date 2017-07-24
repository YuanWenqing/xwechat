/**
 * @author yuanwq, date: 2017年7月22日
 */
package com.xwechat.enums;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.xwechat.core.IEnumParameter;

/**
 * @author yuanwq
 */
public enum Scope implements IEnumParameter {

  SNSAPI_BASE(), SNSAPI_USERINFO("/sns/userinfo"), SNSAPI_LOGIN;

  private final List<String> BASE_APIS =
      Lists.newArrayList("/sns/oauth2/access_token", "/sns/oauth2/refresh_token", "/sns/auth");

  private final Set<String> supportApis;

  private Scope(String... apis) {
    supportApis = Sets.newLinkedHashSet(BASE_APIS);
    if (apis != null) {
      for (String api : apis) {
        supportApis.add(api);
      }
    }
  }

  public Set<String> getSupportApis() {
    return supportApis;
  }

}
