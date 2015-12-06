package com.auction.controller;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

public class ImageTool {

  private static ServletContext getServletContext(HttpServletRequest request) {
    // WebApplicationContext webApplicationContext =
    // ContextLoader.getCurrentWebApplicationContext();
    // return webApplicationContext.getServletContext();
    return request.getSession().getServletContext();
  }

  public static String getImgSuffix(String oriFileName) {
    // 获得文件的后缀名称。
    String[] fileNameSplitInfo = oriFileName.split("\\.");
    String suffix = "jpg";
    if (fileNameSplitInfo.length >= 1) {
      suffix = fileNameSplitInfo[fileNameSplitInfo.length - 1];
    }
    return suffix;
  }

  public static String genTempImgFileName(String oriFileName) {
    StringBuilder stringBuilder = new StringBuilder();
    String suffix = getImgSuffix(oriFileName);
    // 获得格式化的文件名称
    // 随机生成10个数字
    Random random = new Random();
    for (int index = 0; index < 10; index++) {
      stringBuilder.append(random.nextInt(10));
    }
    stringBuilder.append(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
    stringBuilder.append("." + suffix);
    String tmpImgFilePath = "images" + File.separator + "temp" + File.separator + stringBuilder.toString();
    return tmpImgFilePath;
  }

  // 获得上传图像文件的相对路径（相对于项目根目录）
  public static String genAvatarFileName(HttpServletRequest request, String oriFileName) {
    StringBuilder stringBuilder = new StringBuilder();
    // 获得文件的后缀名称。
    String suffix = getImgSuffix(oriFileName);
    // 获得格式化的文件名称
    stringBuilder.append(new IPTimeStamp(request.getRemoteAddr()).getIpTimeRand());
    stringBuilder.append("." + suffix);
    String avatarFilePath = "images" + File.separator + "avatar" + File.separator + stringBuilder.toString();
    return avatarFilePath;
  }

  private static BufferedImage cutImg(File oriImgFile, int x1, int y1, int imgWidth, int imgHeight) throws IOException {
    // 开始裁剪图片
    BufferedImage oriBufferImg = ImageIO.read(oriImgFile);
    BufferedImage subBufferedImage = oriBufferImg.getSubimage(x1, y1, imgWidth, imgHeight);
    // 获得头像文件最终的位置。
    return subBufferedImage;
  }

  private static void saveImg(BufferedImage bufferedImage, String imgType, String destPath) throws IOException {
    ImageIO.write(bufferedImage, imgType, new File(destPath));
    return;
  }

  private static void saveImg(MultipartFile imgFile, String destPath) throws IOException {
    byte[] bytes;
    bytes = imgFile.getBytes();
    // 创建新文件的输出流。
    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(destPath)));
    stream.write(bytes);
    stream.close();
  }

  private static boolean delImg(String path) {
    File tmpFile = new File(path);
    if (tmpFile.exists() && tmpFile.isFile()) {
      return tmpFile.delete();
    }
    return false;
  }

  public static String saveAvatarImgFile(HttpServletRequest request, MultipartFile oriImgFile, BindingResult result)
      throws IOException {
    // 首先检查关于图片裁剪的信息
    int x1 = Integer.parseInt(request.getParameter("x1"));
    int y1 = Integer.parseInt(request.getParameter("y1"));
    int x2 = Integer.parseInt(request.getParameter("x2"));
    int y2 = Integer.parseInt(request.getParameter("y2"));
    int imgWidth = Integer.parseInt(request.getParameter("imgWidth"));
    int imgHeight = Integer.parseInt(request.getParameter("imgHeight"));
    // 获取头像文件的存取路径。// 写文件的时候需要注意当前系统的文件路径分隔符。写入服务器中的对应目录下。
    String avatarCorePath = getServletContext(request).getRealPath("/")
        + genAvatarFileName(request, oriImgFile.getOriginalFilename());
    if (x1 == -1 || y1 == -1 || x2 == -1 || y2 == -1 || imgWidth == -1 || imgHeight == -1) {
      // 说明用户没有进行裁剪操作。
      // 此时开始写入图片信息
      try {
        saveImg(oriImgFile, avatarCorePath);
      } catch (IOException e) {
        result.rejectValue("avatarFile", "register.user.avatar.upload.failed");
        e.printStackTrace();
        return null;
      }
    } else { // 说明上传的图像被裁减过。
      // 首先将未存储的图片数据存储起来
      String tmpImgPath = getServletContext(request).getRealPath("/")
          + genTempImgFileName(oriImgFile.getOriginalFilename());
      File tmpImgFile = new File(tmpImgPath);
      try {
        oriImgFile.transferTo(tmpImgFile);
        BufferedImage avatarBufferImg = cutImg(tmpImgFile, x1, y1, imgWidth, imgHeight);
        // 裁剪完成，写入裁剪完成的头像信息。
        saveImg(avatarBufferImg, getImgSuffix(oriImgFile.getOriginalFilename()), avatarCorePath);
        // 删除临时文件。
        delImg(tmpImgPath);
      } catch (IllegalStateException e) {
        // TODO Auto-generated catch block
        result.rejectValue("avatarFile", "register.user.avatar.cut.failed");
        e.printStackTrace();
        return null;
      }
    }
    // 返回最终文件的存储路径。
    return avatarCorePath;
  }
}
