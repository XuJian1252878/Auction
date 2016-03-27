package com.auction.model.test;

import java.util.Calendar;
import java.util.Date;

import com.auction.util.DateTimeUtil;

public class ImgTestEntity {

  public String imgFilePath;
  public int width;
  public int height;
  public String countdownId;
  public String countdownAlertId;
  public String expireTimeStamp;
  public Date myDate;
  // public String productUrl;  // 商品的实体页面信息，代写入。 
  public ImgTestEntity(String imgFilePath, int width, int height, String countdownId, String countdownAlertId, int expireTimeStamp) {
    this.imgFilePath = imgFilePath;
    this.width = width;
    this.height = height;
    this.countdownId = countdownId;
    this.countdownAlertId = countdownAlertId;
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    calendar.add(Calendar.MINUTE, 1);
    this.myDate = calendar.getTime();
    this.expireTimeStamp = DateTimeUtil.getTimeStamp("yyyy-MM-dd HH:mm:ss", calendar.getTime());
    // 由于页面需要显示倒计时，需要显示时间的html元素id信息，
    // 显示到时提醒的html元素id信息，
    // 以及商品拍卖的倒计时间。
    
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

  public String getCountdownId() {
    return countdownId;
  }

  public void setCountdownId(String countdownId) {
    this.countdownId = countdownId;
  }

  public String getCountdownAlertId() {
    return countdownAlertId;
  }

  public void setCountdownAlertId(String countdownAlertId) {
    this.countdownAlertId = countdownAlertId;
  }

  public String getExpireTimeStamp() {
    return expireTimeStamp;
  }

  public void setExpireTimeStamp(String expireTimeStamp) {
    this.expireTimeStamp = expireTimeStamp;
  }

  
}
