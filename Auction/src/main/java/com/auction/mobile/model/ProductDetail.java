package com.auction.mobile.model;

import java.util.List;

public class ProductDetail {

  private int id;
  private String kindName;
  private String name;
  private String describe;
  private long endDate;
  private float basicPrice;
  private float maxPrice;
  private List<String> productTags;
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public String getKindName() {
    return kindName;
  }
  public void setKindName(String kindName) {
    this.kindName = kindName;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
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
  public float getMaxPrice() {
    return maxPrice;
  }
  public void setMaxPrice(float maxPrice) {
    this.maxPrice = maxPrice;
  }
  public List<String> getProductTags() {
    return productTags;
  }
  public void setProductTags(List<String> productTags) {
    this.productTags = productTags;
  }

}
