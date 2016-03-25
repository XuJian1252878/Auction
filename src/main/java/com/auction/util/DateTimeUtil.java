package com.auction.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

// http://blog.csdn.net/mmm333zzz/article/details/8569637 关于生成高清缩略图

public class DateTimeUtil {
  
  private static SimpleDateFormat sdf = null;
  
  /**
   * 根据请求端ip地址以及调用时间生成字符串，该字符串是 上传日期、ip地址、随机五位整数的组合。
   * @param ipStr
   * @return
   */
  public static String getIpTimeRand(String ipStr) {
    StringBuffer stringBuffer = new StringBuffer();
    if (ipStr != null) {
      String[] ipSplitInfo = ipStr.split("\\.");
      // 有些ip地址段不满足三位，需要进行补零操作。
      for (String ipPart : ipSplitInfo) {
        stringBuffer.append(addZeroToIpPart(ipPart, 3));
      }
    }
    // 加入时间戳信息
    stringBuffer.append(getCurrentTimeStamp("yyyyMMddHHmmssSSS"));
    // 加入五位的随机数信息
    Random random = new Random();
    for (int index = 0; index < 5; index ++) {
      stringBuffer.append(random.nextInt(10));
    }
    return stringBuffer.toString();
  }
  
  /**
   * 将不满3位的ip段进行补零操作。
   * @param ipPart ip段，3位数字为一段
   * @param len ip段每一个小部分的长度
   * @return
   */
  private static String addZeroToIpPart(String ipPart, int len) {
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(ipPart);
    while(stringBuffer.length() < len) {
      stringBuffer.insert(0, "0");
    }
    return stringBuffer.toString();
  }
  
  /**
   * 返回当前的时间戳信息
   * @param dateTimeFormat 需要的日期格式
   * @return
   */
  public static String getCurrentTimeStamp(String dateTimeFormat) {
    return DateTimeUtil.getTimeStamp(dateTimeFormat, new Date());
  }

  /**
   * 获得指定时间的时间戳信息。
   * @param dateTimeFormat dateTimeFormat 需要的日期格式
   * @param date 需要转化的日期
   * @return
   */
  public static String getTimeStamp(String dateTimeFormat, Date date) {
    sdf = new SimpleDateFormat(dateTimeFormat);
    return sdf.format(date);
  }

  /**
   * 获得当前距离1970年1月1日0点0分0秒的毫秒数
   * @return
   */
  public static long getCurrentTimeMillis() {
    return System.currentTimeMillis();
  }

  /**
   * 获得当前的 util Date 对象
   * @return
   */
  public static Date getCurrentDate() {
    return new Date();
  }
  
  /**
   * 将传入的unix时间戳转化为Date对象
   * @param timeMillis 传入的unix时间戳，毫秒。
   * @return
   */
  public static Date timeMillisToDate(long timeMillis) {
    return new Date(timeMillis);
  }
  
  /**
   * 将传入的Date对象转化为Unix时间戳，毫秒为单位。
   * @param date java.util.date 对象
   * @return
   */
  public static long dateToTimeMillis(Date date) {
    return date.getTime();
  }
}
