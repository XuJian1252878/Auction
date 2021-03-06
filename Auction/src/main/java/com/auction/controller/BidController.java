package com.auction.controller;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.auction.model.Bid;
import com.auction.model.Product;
import com.auction.model.validator.BidValidator;
import com.auction.service.IBidService;
import com.auction.service.IMessageService;
import com.auction.service.IProductService;
import com.auction.util.WebConstantUtil;

@Controller
@RequestMapping("/bid")
public class BidController {

  @Resource(name = "bidService")
  IBidService bidService;

  @Resource(name = "productService")
  IProductService productService;

  @Resource(name = "messageService")
  IMessageService messageService;

  @InitBinder("bid")
  public void initBidBinder(DataBinder binder) {
    binder.addValidators(new BidValidator());
  }

  /**
   * 处理用户在提交竞价时的ajax回传信息。返回一个包含处理结果的map。
   * @param userBid
   * @param productId
   * @return
   */
  @RequestMapping(value = "/commit_{productId}", method = RequestMethod.POST)
  @ResponseBody
  public Map<String, Object> commitBidInfo(@Valid @ModelAttribute(WebConstantUtil.USERBID) Bid userBid,
      @PathVariable("productId") int productId, HttpSession httpSession) {
    // 记录用户提出竞价的时间。
    userBid.setBidDate(new Date());
    // 刚开始的时候竞价还没有成交。
    userBid.setIsSuccess(false);
    Map<String, Object> resMap = bidService.saveUserBid(userBid);
    return resMap;
  }

  @RequestMapping(value = "/modifyprice", method = RequestMethod.POST)
  public String modifyBidInfo(HttpServletRequest request) {
    // 获得需要竞价的bid 的 id信息以及修改之后的竞价。
    int bidId = Integer.parseInt(request.getParameter("goingOnBidId"));
    float price = Float.parseFloat(request.getParameter("goingOnBidPrice"));
    bidService.modifyBidPrice(bidId, price);
    return "redirect:/user/transaction";
  }

  @RequestMapping(value = "/deal", method = RequestMethod.POST)
  public ModelAndView bidDeal(@Valid @ModelAttribute("bid") Bid bid) {
    ModelAndView mv = new ModelAndView();
    // 设置竞价成交的时间
    // 更新bid的交易状态为true，并且记录竞价成交的时间，说明这笔交易已经成交。
    bidService.setBidDeal(bid);
    // 在对应的product对象中将isDeal标志设置为true，表示该商品已经拍卖成功。
    Product product = productService.getProductById(bid.getProduct().getId());
    product.setIsDeal(true);
    productService.updateProduct(product);
    // 向提出竞价的用户发送一条提示消息，告知其已竞价成功。
    messageService.saveBidSuccessNotification(bid.getId());
    mv.setViewName("redirect:/user/products");
    return mv;
  }
}
