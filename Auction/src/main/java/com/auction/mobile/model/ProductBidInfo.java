package com.auction.mobile.model;

public class ProductBidInfo {

  private int bidId;
  private String userInfo;
  private String name;
  private String kindName;
  private float maxPrice;
  private String describe;
  private long endDate;
  private float basicPrice;
  public int getBidId() {
    return bidId;
  }
  public void setBidId(int bidId) {
    this.bidId = bidId;
  }
  public String getUserInfo() {
    return userInfo;
  }
  public void setUserInfo(String userInfo) {
    this.userInfo = userInfo;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
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
