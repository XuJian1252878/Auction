package com.auction.mobile.controller;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.auction.mobile.model.BidInfo;
import com.auction.mobile.model.ProductDetail;
import com.auction.mobile.model.ProductInfo;
import com.auction.model.Bid;
import com.auction.model.Category;
import com.auction.model.Product;
import com.auction.model.ProductTag;
import com.auction.model.User;
import com.auction.service.IBidService;
import com.auction.service.IProductService;
import com.auction.service.IProductTagService;
import com.auction.util.DateTimeUtil;
import com.auction.util.MobileConstantUtil;

@Controller
@RequestMapping("/mobile/product")
public class MobileProductController {

  @Resource(name = "productService")
  IProductService productService;
  @Resource(name = "productTagService")
  IProductTagService productTagService;
  @Resource(name = "bidService")
  IBidService bidService;

  @RequestMapping(value = "/{productId}", method = RequestMethod.GET)
  @ResponseBody
  public ProductDetail loadProductById(@PathVariable int productId){
    Product product = productService.getProductById(productId);
    ProductDetail productDetail = new ProductDetail();
    productDetail.setId(product.getId());
    productDetail.setKindName(product.getCategory().getName());
    productDetail.setName(product.getName());
    productDetail.setDescribe(product.getDescribe());
    productDetail.setBasicPrice(product.getBasicPrice());
    productDetail.setMaxPrice(product.getMaxBidPrice());
    productDetail.setEndDate(product.getEndDate().getTime());
    List<ProductTag> productTags = product.getProductTags();
    List<String> myTags = new ArrayList<String>();
    for (ProductTag pTag : productTags) {
      myTags.add(pTag.getTag());
    }
    productDetail.setProductTags(myTags);
    return productDetail;
  }

  /**
   * 获取用户已上传的商品信息。
   * @param request
   * @return
   */
  @RequestMapping(value = "/user_upload", method = RequestMethod.POST)
  @ResponseBody
  public List<Object> loadUserUploadProducts(HttpServletRequest request){
    int userId = Integer.parseInt(request.getParameter(MobileConstantUtil.MOBILE_UPLOAD_PRODUCT_USER_ID));
    List<Product> bidingProducts = productService.getGoingOnProductsByUser(userId);
    List<Object> bidInfos = new ArrayList<Object>();
    for (Product product : bidingProducts) {
      BidInfo bidInfo = new BidInfo();
      bidInfo.setId(product.getId());
      bidInfo.setName(product.getName());
      bidInfo.setKindName(product.getCategory().getName());
      bidInfo.setMaxPrice(product.getMaxBidPrice());
      bidInfo.setEndDate(product.getEndDate().getTime());
      bidInfo.setBasicPrice(product.getBasicPrice());
      bidInfos.add(bidInfo);
    }
    return bidInfos;
  }

  /**
   * 用户上传商品动作，客户端上传。
   * @param request
   * @return
   * @throws UnsupportedEncodingException 
   */
  @RequestMapping(value = "/upload", method = RequestMethod.POST)
  @ResponseBody
  public Map<String, Object> uploadProduct(HttpServletRequest request) throws UnsupportedEncodingException {
    int userId = Integer.parseInt(request.getParameter(MobileConstantUtil.MOBILE_UPLOAD_PRODUCT_USER_ID));
    int categoryId = Integer.parseInt(request.getParameter(MobileConstantUtil.MOBILE_UPLOAD_PRODUCT_CATEGORY_ID));
    String productName = new String(request.getParameter(MobileConstantUtil.MOBILE_UPLOAD_PRODUCT_NAME).getBytes("iso-8859-1"), "UTF-8");
    String productDesc = new String(request.getParameter(MobileConstantUtil.MOBILE_UPLOAD_PRODUCT_DESC).getBytes("iso-8859-1"), "UTF-8");
    String productTags = new String(request.getParameter(MobileConstantUtil.MOBILE_UPLOAD_PRODUCT_TAGS).getBytes("iso-8859-1"), "UTF-8");
    int endDateInterval = Integer.parseInt(request.getParameter(MobileConstantUtil.MOBILE_UPLOAD_PRODUCT_BID_END_DATE));
    float basicPrice = Float.parseFloat(request.getParameter(MobileConstantUtil.MOBILE_UPLOAD_PRODUCT_BID_BASIC_PRICE));
    
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DAY_OF_MONTH, endDateInterval);
    Date endDate = new Date(calendar.getTime().getTime());

    // 开始构建商品的实体信息。
    long curTimeMillis = DateTimeUtil.getCurrentTimeMillis();
    Product product = new Product();
    Category category = new Category();
    category.setId(categoryId);
    User user = new User();
    user.setId(userId);
    product.setName(productName);
    product.setDescribe(productDesc);
    product.setCategory(category);
    product.setUser(user);
    product.setOnSaleDate(DateTimeUtil.timeMillisToDate(curTimeMillis));
    product.setEndDate(endDate);
    product.setImgPath("images\\test\\ladybug_ant.jpg");
    product.setIsDeal(false);
    product.setBasicPrice(basicPrice);
    Serializable serializable = productService.createProduct(product);
    boolean isSuccess = false;
    if (serializable != null) {
      int productId = ((Integer) serializable).intValue();
      productTagService.saveTags(productTags, productId);
      isSuccess = true;
    }
    Map<String, Object> resMap = new HashMap<String, Object>();
    resMap.put("result", isSuccess);
    return resMap;
  }

  @RequestMapping(value = "/goingon_biding", method = RequestMethod.POST)
  @ResponseBody
  public List<Object> loadUserBindingProducts(HttpServletRequest request) {
    int userId = Integer.parseInt(request.getParameter(MobileConstantUtil.MOBILE_TRANSACTION_USER_ID));
    List<Bid> bids = bidService.getGoingOnBids(userId);
    List<Object> productInfos = new ArrayList<Object>();
    for (Bid bid : bids) {
      Product product = bid.getProduct();
      ProductInfo pInfo = new ProductInfo();
      pInfo.setBidId(bid.getId());
      pInfo.setCategoryName(product.getCategory().getName());
      pInfo.setDesc(product.getDescribe());
      pInfo.setMyBid(bid.getPrice());
      pInfo.setName(product.getName());
      pInfo.setMaxBid(product.getMaxBidPrice());
      pInfo.setCategoryId(product.getCategory().getId());
      pInfo.setProductOwner(product.getUser().getUserName());
      pInfo.setEndDate(product.getEndDate().getTime());
      pInfo.setBidDate(bid.getBidDate().getTime());
      productInfos.add(pInfo);
    }
    return productInfos;
  }

  @RequestMapping(value = "/deal_biding", method = RequestMethod.POST)
  @ResponseBody
  public List<Object> loadUserDealProducts(HttpServletRequest request){
    int userId = Integer.parseInt(request.getParameter(MobileConstantUtil.MOBILE_TRANSACTION_USER_ID));
    List<Bid> bids = bidService.getDealBids(userId);
    List<Object> productInfos = new ArrayList<Object>();
    for (Bid bid : bids) {
      Product product = bid.getProduct();
      ProductInfo pInfo = new ProductInfo();
      pInfo.setBidId(bid.getId());
      pInfo.setCategoryName(product.getCategory().getName());
      pInfo.setDesc(product.getDescribe());
      pInfo.setMyBid(bid.getPrice());
      pInfo.setName(product.getName());
      pInfo.setMaxBid(product.getDealBidPrice());
      pInfo.setCategoryId(product.getCategory().getId());
      productInfos.add(pInfo);
    }
    return productInfos;
  }

  @RequestMapping(value = "/failed_biding", method = RequestMethod.POST)
  @ResponseBody
  public List<Object> loadUserFailedBindingProduct(HttpServletRequest request){
    int userId = Integer.parseInt(request.getParameter(MobileConstantUtil.MOBILE_TRANSACTION_USER_ID));
    List<Bid> bids = bidService.getFailedBids(userId);
    List<Object> productInfos = new ArrayList<Object>();
    for (Bid bid : bids) {
      Product product = bid.getProduct();
      ProductInfo pInfo = new ProductInfo();
      pInfo.setBidId(bid.getId());
      pInfo.setCategoryName(product.getCategory().getName());
      pInfo.setDesc(product.getDescribe());
      pInfo.setMyBid(bid.getPrice());
      pInfo.setName(product.getName());
      pInfo.setMaxBid(product.getDealBidPrice());
      pInfo.setCategoryId(product.getCategory().getId());
      productInfos.add(pInfo);
    }
    return productInfos;
  }
}
