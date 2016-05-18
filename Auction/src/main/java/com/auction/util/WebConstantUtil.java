package com.auction.util;

public class WebConstantUtil {
  // session 存储的User变量索引（form表单提交user信息的时候）。
  public static final String LOGINUSER = "loginuser";
  // 存储用户的竞价信息的Bid变量索引。
  public static final String USERBID = "userbid";
  // 存储用户头像的文件夹名称
  public static final String AVATARFOLDER = "avatar";
  // 存储商品类别图片的文件夹名称
  public static final String CATEGORYFOLDER = "category";
  // 存储商品图片的文件夹名称
  public static final String PRODUCTFOLDER = "product";
  // 在类别商品列表中，每页显示的商品的数量。
  public static final int PRODUCT_COUNT_PER_PAGE = 40;
  // 在类别商品列表中，瀑布流每次加载的商品数量。
  public static final int PRODUCT_COUNT_PER_WATERFALL_PART = 20;
  // 在类别商品页面中，瀑布流总共需要加载的次数。
  public static final int PRODUCT_WATERFALL_PARTS_PER_PAGE = PRODUCT_COUNT_PER_PAGE / PRODUCT_COUNT_PER_WATERFALL_PART;
  
  // 用于用户登录时，作为返回map的key使用。
  public static final String USER_LOGIN_SUCCESS_FLAG = "loginresult";
  public static final String USER_LOGIN_OBJECT_FLAG = "loginuser";
  
  // 商品标签的默认分隔符。
  public static final String PRODUCT_TAG_DELIMETER = ",";
}
