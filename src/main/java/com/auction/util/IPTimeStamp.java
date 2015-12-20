package com.auction.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

// http://blog.csdn.net/mmm333zzz/article/details/8569637 关于生成高清缩略图

public class IPTimeStamp {
  
  private SimpleDateFormat sdf = null;
  private String ipStr = null;
  
  public IPTimeStamp(String ipStr) {
    this.ipStr = ipStr;
  }
  
  // 获得上传日期、ip地址、随机五位整数的组合。
  public String getIpTimeRand() {
    StringBuffer stringBuffer = new StringBuffer();
    if (ipStr != null) {
      String[] ipSplitInfo = this.ipStr.split("\\.");
      // 有些ip地址段不满足三位，需要进行补零操作。
      for (String ipPart : ipSplitInfo) {
        stringBuffer.append(this.addZeroToIpPart(ipPart, 3));
      }
    }
    // 加入时间戳信息
    stringBuffer.append(this.getCurrentTimeStamp());
    // 加入五位的随机数信息
    Random random = new Random();
    for (int index = 0; index < 5; index ++) {
      stringBuffer.append(random.nextInt(10));
    }
    return stringBuffer.toString();
  }
  
  // 对ip地址段进行补零操作
  private String addZeroToIpPart(String ipPart, int len) {
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(ipPart);
    while(stringBuffer.length() < len) {
      stringBuffer.insert(0, "0");
    }
    return stringBuffer.toString();
  }
  
  // 返回当前的时间戳信息
  private String getCurrentTimeStamp() {
    this.sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    return sdf.format(new Date());
  }

}
