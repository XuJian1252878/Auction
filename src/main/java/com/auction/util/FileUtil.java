package com.auction.util;

import org.springframework.web.multipart.MultipartFile;

public class FileUtil {

  /**
   * 检查文件是否为空，当传入文件为 null，或者文件的大小为0的时候，
   * 将返回true，否则返回false。
   * @param file
   * @return
   */
  public static boolean isEmptyFile(MultipartFile file) {
    if(file == null || file.getSize() <= 0L) {
      return true;
    }
    return false;
  }

  /**
   * 检查文件的大小是否满足要求，文件为null，或者大于restrictSize，返回false；
   * 不然返回true。
   * @param file
   * @param restrictSize
   * @return
   */
  public static boolean meetSizeRestrict(MultipartFile file, long restrictSize) {
    if (file == null || file.getSize() > restrictSize) {
      return false;
    }
    return true;
  }

  /**
   * 根据文件的文件名称获得文件后缀名，若fileName为null，或者未获得后缀名，那么返回null；
   * 否则返回文件后缀名称。
   * @param fileName
   * @return
   */
  public static String getFileSuffix(String fileName) {
    // 获得文件的后缀名称。
    String[] fileNameSplitInfo = fileName.split("\\.");
    String suffix = null;
    if (fileNameSplitInfo.length >= 1) {
      suffix = fileNameSplitInfo[fileNameSplitInfo.length - 1];
    }
    return suffix;
  }
}
