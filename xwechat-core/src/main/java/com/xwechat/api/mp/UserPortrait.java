package com.xwechat.api.mp;

import java.util.List;

/**
 * 用户画像
 * Created by shangqingzhe on 17/10/19.
 */
public class UserPortrait {
  private String refDate;
  private VisitUvNew visitUvNew;
  private VisitUv visitUv;

  public String getRefDate() {
    return refDate;
  }

  public void setRefDate(String refDate) {
    this.refDate = refDate;
  }

  public VisitUvNew getVisitUvNew() {
    return visitUvNew;
  }

  public void setVisitUvNew(VisitUvNew visitUvNew) {
    this.visitUvNew = visitUvNew;
  }

  public VisitUv getVisitUv() {
    return visitUv;
  }

  public void setVisitUv(VisitUv visitUv) {
    this.visitUv = visitUv;
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

