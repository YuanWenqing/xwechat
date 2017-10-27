package com.xwechat.api.mp;

import java.util.List;

/**
 * 用户画像
 * Created by shangqingzhe on 17/10/19.
 */
public class UserPortrait {
  private String ref_date;
  private VisitUvNew visit_uv_new;
  private VisitUv visit_uv;

  public String getRef_date() {
    return ref_date;
  }

  public void setRef_date(String ref_date) {
    this.ref_date = ref_date;
  }

  public VisitUvNew getVisit_uv_new() {
    return visit_uv_new;
  }

  public void setVisit_uv_new(VisitUvNew visit_uv_new) {
    this.visit_uv_new = visit_uv_new;
  }

  public VisitUv getVisit_uv() {
    return visit_uv;
  }

  public void setVisit_uv(VisitUv visit_uv) {
    this.visit_uv = visit_uv;
  }

  public static class VisitUvNew {
    private List<Property> province;
    private List<Property> city;
    private List<Property> genders;
    private List<Property> platforms;
    private List<Property> devices;
    private List<Property> ages;

    public List<Property> getProvince() {
      return province;
    }

    public void setProvince(List<Property> province) {
      this.province = province;
    }

    public List<Property> getCity() {
      return city;
    }

    public void setCity(List<Property> city) {
      this.city = city;
    }

    public List<Property> getGenders() {
      return genders;
    }

    public void setGenders(List<Property> genders) {
      this.genders = genders;
    }

    public List<Property> getPlatforms() {
      return platforms;
    }

    public void setPlatforms(List<Property> platforms) {
      this.platforms = platforms;
    }

    public List<Property> getDevices() {
      return devices;
    }

    public void setDevices(List<Property> devices) {
      this.devices = devices;
    }

    public List<Property> getAges() {
      return ages;
    }

    public void setAges(List<Property> ages) {
      this.ages = ages;
    }
  }
  public static class VisitUv {
    private List<Property> province;
    private List<Property> city;
    private List<Property> genders;
    private List<Property> platforms;
    private List<Property> devices;
    private List<Property> ages;

    public List<Property> getProvince() {
      return province;
    }

    public void setProvince(List<Property> province) {
      this.province = province;
    }

    public List<Property> getCity() {
      return city;
    }

    public void setCity(List<Property> city) {
      this.city = city;
    }

    public List<Property> getGenders() {
      return genders;
    }

    public void setGenders(List<Property> genders) {
      this.genders = genders;
    }

    public List<Property> getPlatforms() {
      return platforms;
    }

    public void setPlatforms(List<Property> platforms) {
      this.platforms = platforms;
    }

    public List<Property> getDevices() {
      return devices;
    }

    public void setDevices(List<Property> devices) {
      this.devices = devices;
    }

    public List<Property> getAges() {
      return ages;
    }

    public void setAges(List<Property> ages) {
      this.ages = ages;
    }
  }
  public static class Property {
    private Integer id;
    private String name;
    private String value;

    public Integer getId() {
      return id;
    }

    public void setId(Integer id) {
      this.id = id;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getValue() {
      return value;
    }

    public void setValue(String value) {
      this.value = value;
    }
  }
}

