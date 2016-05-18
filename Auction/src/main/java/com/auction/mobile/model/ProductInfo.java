package com.auction.mobile.model;

public class ProductInfo {
  
  private int id;
  private String name;
  private String describe;
  private String productOwner;
  private long endDate;
  private long bidDate;  // 用户拍卖时间。
  private int categoryId;
  private String categoryName;
  private float maxBid;  // 正在竞价，当前的最高竞价；竞价失败，要成交价。
  private float myBid;  // 正在竞价，我的出价。竞价成功，我的出价。
  private int bidId;
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getDesc() {
    return describe;
  }
  public void setDesc(String desc) {
    this.describe = desc;
  }
  public int getCategoryId() {
    return categoryId;
  }
  public void setCategoryId(int categoryId) {
    this.categoryId = categoryId;
  }
  public String getCategoryName() {
    return categoryName;
  }
  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }
  public float getMaxBid() {
    return maxBid;
  }
  public void setMaxBid(float maxBid) {
    this.maxBid = maxBid;
  }
  public float getMyBid() {
    return myBid;
  }
  public void setMyBid(float myBid) {
    this.myBid = myBid;
  }
  public int getBidId() {
    return bidId;
  }
  public void setBidId(int bidId) {
    this.bidId = bidId;
  }
  public String getProductOwner() {
    return productOwner;
  }
  public void setProductOwner(String productOwner) {
    this.productOwner = productOwner;
  }
  public long getEndDate() {
    return endDate;
  }
  public void setEndDate(long endDate) {
    this.endDate = endDate;
  }
  public long getBidDate() {
    return bidDate;
  }
  public void setBidDate(long bidDate) {
    this.bidDate = bidDate;
  }
}
