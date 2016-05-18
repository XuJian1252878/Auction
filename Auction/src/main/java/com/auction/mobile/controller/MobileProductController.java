package com.auction.mobile.controller;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.auction.model.Bid;
import com.auction.model.Category;
import com.auction.model.Product;
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
  public Map<String, Object> loadProductById(@PathVariable int productId){
    Map<String, Object> resMap = new HashMap<String, Object>();
    Product product = productService.getProductById(productId);
    resMap.put("product", product);
    return resMap;
  }

  /**
   * 获取用户已上传的商品信息。
   * @param request
   * @return
   */
  @RequestMapping(value = "/user_upload", method = RequestMethod.POST)
  @ResponseBody
  public Map<String, Object> loadUserUploadProducts(HttpServletRequest request){
    Map<String, Object> resMap = new HashMap<String, Object>();
    int userId = Integer.parseInt(request.getParameter(MobileConstantUtil.MOBILE_UPLOAD_PRODUCT_USER_ID));
    List<Product> bidingProducts = productService.getGoingOnProductsByUser(userId);
    resMap.put("bidingProducts", bidingProducts);
    return resMap;
  }

  /**
   * 用户上传商品动作，客户端上传。
   * @param request
   * @return
   */
  @RequestMapping(value = "/upload", method = RequestMethod.POST)
  @ResponseBody
  public Map<String, Object> uploadProduct(HttpServletRequest request) {
    String nowDateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()).split(" ")[1];
    int userId = Integer.parseInt(request.getParameter(request.getParameter(MobileConstantUtil.MOBILE_UPLOAD_PRODUCT_USER_ID)));
    int categoryId = Integer.parseInt(request.getParameter(MobileConstantUtil.MOBILE_UPLOAD_PRODUCT_CATEGORY_ID));
    String productName = request.getParameter(MobileConstantUtil.MOBILE_UPLOAD_PRODUCT_NAME);
    String productDesc = request.getParameter(MobileConstantUtil.MOBILE_UPLOAD_PRODUCT_DESC);
    String productTags = request.getParameter(MobileConstantUtil.MOBILE_UPLOAD_PRODUCT_TAGS);
    String endDateStr = request.getParameter(MobileConstantUtil.MOBILE_UPLOAD_PRODUCT_BID_END_DATE) + " " + nowDateStr;

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
    product.setOnSaleDate(DateTimeUtil.timeMillisToDate(curTimeMillis));
    product.setEndDate(DateTimeUtil.stringToDate(endDateStr, "yyyy-MM-dd HH:mm:ss"));
    product.setImgPath("images\\test\\ladybug_ant.jpg");
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
  public Map<String, Object> loadUserBindingProducts(HttpServletRequest request){
    Map<String, Object> resMap = new HashMap<String, Object>();
    int userId = Integer.parseInt(request.getParameter(MobileConstantUtil.MOBILE_TRANSACTION_USER_ID));
    List<Bid> bids = bidService.getGoingOnBids(userId);
    List<Product> products = new ArrayList<Product>();
    for (Bid bid : bids) {
      Product product = bid.getProduct();
      products.add(product);
    }
    resMap.put("products", products);
    return resMap;
  }

  @RequestMapping(value = "/deal_biding", method = RequestMethod.POST)
  @ResponseBody
  public Map<String, Object> loadUserDealProducts(HttpServletRequest request){
    Map<String, Object> resMap = new HashMap<String, Object>();
    int userId = Integer.parseInt(request.getParameter(MobileConstantUtil.MOBILE_TRANSACTION_USER_ID));
    List<Bid> bids = bidService.getDealBids(userId);
    List<Product> products = new ArrayList<Product>();
    for (Bid bid : bids) {
      Product product = bid.getProduct();
      products.add(product);
    }
    resMap.put("products", products);
    return resMap;
  }

  @RequestMapping(value = "/failed_biding", method = RequestMethod.POST)
  @ResponseBody
  public Map<String, Object> loadUserFailedBindingProduct(HttpServletRequest request){
    Map<String, Object> resMap = new HashMap<String, Object>();
    int userId = Integer.parseInt(request.getParameter(MobileConstantUtil.MOBILE_TRANSACTION_USER_ID));
    List<Bid> bids = bidService.getFailedBids(userId);
    List<Product> products = new ArrayList<Product>();
    for (Bid bid : bids) {
      Product product = bid.getProduct();
      products.add(product);
    }
    resMap.put("products", products);
    return resMap;
  }
}
