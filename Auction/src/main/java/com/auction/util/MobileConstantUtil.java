package com.auction.util;

public class MobileConstantUtil {

  /**
   * 服务器和客户端约定的通信字段信息。
   */

  /**
   * mobile用户登陆时（post回一个map对象），以post方式返回用户名（用户邮箱）的key。
   */
  public static final String MOBILE_USER_LOGIN_NAME_OR_EMAIL= "userName";
  /**
   * mobile用户登录时（post回一个map对象），以post方式返回的用户密码的key。
   */
  public static final String MOBILE_USER_LOGIN_PASSWORD = "userPwd";
  
  public static final String MOBILE_CATEGORY_ID = "kindid";

  public static final String MOBILE_PRODUCT_ID = "productId";

  /**
   * mobile 提出竞价申请的用户id。
   */
  public static final String MOBILE_BID_USER_ID = "userID";

  /**
   * 被竞价的物品的id
   */
  public static final String MOBILE_BID_PRODUCT_ID = "itemID";

  /**
   * 用户进行竞价时提出的竞价。
   */
  public static final String MOBILE_USER_BID_PRICE = "bidPrice";

  /**
   * 表示上传商品用户的id。（用户上传的历史商品，用户上传商品 使用）
   */
  public static final String MOBILE_UPLOAD_PRODUCT_USER_ID = "userID";

  /**
   * 用户上传商品时，商品所属的类别。
   */
  public static final String MOBILE_UPLOAD_PRODUCT_CATEGORY_ID = "categoryId";

  /**
   * 用户上传商品的名称。
   */
  public static final String MOBILE_UPLOAD_PRODUCT_NAME = "productName";

  /**
   * 用户上传商品的描述。
   */
  public static final String MOBILE_UPLOAD_PRODUCT_DESC = "productDesc";

  /**
   * 用户上传商品的标签。
   */
  public static final String MOBILE_UPLOAD_PRODUCT_TAGS = "productTag";

  /**
   * 用户上传商品时的结束竞价日期。
   */
  public static final String MOBILE_UPLOAD_PRODUCT_BID_END_DATE = "endDate";

  /**
   * 用户上传商品的竞价起价。
   */
  public static final String MOBILE_UPLOAD_PRODUCT_BASE_BID_PRICE = "basePrice";

  /**
   * 用户交易的用户id。
   */
  public static final String MOBILE_TRANSACTION_USER_ID = "userID";
  
  public static final String MOBILE_UPLOAD_PRODUCT_BID_BASIC_PRICE = "basicPrice";

  public static final String MOBILE_BID_ID = "bidId";

}
