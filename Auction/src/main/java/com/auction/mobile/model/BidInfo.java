package com.auction.mobile.model;

public class BidInfo {

  private int id;
  private String Name;
  private String kindName;
  private float maxPrice;
  private String describe;
  private long endDate;
  private float basicPrice;

  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public String getName() {
    return Name;
  }
  public void setName(String name) {
    Name = name;
  }
  public String getKindName() {
    return kindName;
  }
  public void setKindName(String kindName) {
    this.kindName = kindName;
  }
  public float getMaxPrice() {
    return maxPrice;
  }
  public void setMaxPrice(float maxPrice) {
    this.maxPrice = maxPrice;
  }
  public String getDescribe() {
    return describe;
  }
  public void setDescribe(String describe) {
    this.describe = describe;
  }
  public long getEndDate() {
    return endDate;
  }
  public void setEndDate(long endDate) {
    this.endDate = endDate;
  }
  public float getBasicPrice() {
    return basicPrice;
  }
  public void setBasicPrice(float basicPrice) {
    this.basicPrice = basicPrice;
  }

}
