package com.auction.mobile.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.auction.model.Bid;
import com.auction.model.Product;
import com.auction.model.User;
import com.auction.service.IBidService;
import com.auction.util.MobileConstantUtil;

@Controller
@RequestMapping("/mobile/bid")
public class MobileBidController {

  @Resource(name = "bidService")
  IBidService bidService;

  @RequestMapping(value = "/commit", method = RequestMethod.POST)
  @ResponseBody
  public Map<String, Object> userBid(HttpServletRequest request){
    int userId = Integer.parseInt(request.getParameter(MobileConstantUtil.MOBILE_BID_USER_ID));
    int productId = Integer.parseInt(request.getParameter(MobileConstantUtil.MOBILE_BID_PRODUCT_ID));
    float bidPrice = Float.parseFloat(request.getParameter(MobileConstantUtil.MOBILE_USER_BID_PRICE));

    User user = new User();
    user.setId(userId);
    Product product = new Product();
    product.setId(productId);

    Bid bid = new Bid();
    // 记录用户提出竞价的时间。
    bid.setBidDate(new Date());
    // 刚开始的时候竞价还没有成交。
    bid.setIsSuccess(false);
    bid.setPrice(bidPrice);
    bid.setProduct(product);
    bid.setUser(user);
    Map<String, Object> resMap = bidService.saveUserBid(bid);
    return resMap;
  }

  @RequestMapping(value = "/deal", method = RequestMethod.POST)
  @ResponseBody
  public boolean bidDeal(HttpServletRequest request) {
    int bidId = Integer.parseInt(request.getParameter(MobileConstantUtil.MOBILE_BID_ID));
    Bid bid = bidService.getBidById(bidId);
    boolean isSuccess = bidService.setBidDeal(bid);
    return isSuccess;
  }

}
