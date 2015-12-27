package com.auction.model.test;

public class ImgTestEntity {

  public String imgFilePath;
  public int width;
  public int height;
  
  public ImgTestEntity(String imgFilePath, int width, int height) {
    this.imgFilePath = imgFilePath;
    this.width = width;
    this.height = height;
  }
  
  public String getImgFilePath() {
    return imgFilePath;
  }
  public void setImgFilePath(String imgFilePath) {
    this.imgFilePath = imgFilePath;
  }
  public int getWidth() {
    return width;
  }
  public void setWidth(int width) {
    this.width = width;
  }
  public int getHeight() {
    return height;
  }
  public void setHeight(int height) {
    this.height = height;
  }
}
