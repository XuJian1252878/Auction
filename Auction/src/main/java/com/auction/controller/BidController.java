package com.auction.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.auction.model.Bid;
import com.auction.model.validator.BidValidator;
import com.auction.service.IBidService;
import com.auction.util.ConstantUtil;

@Controller
@RequestMapping("/bid")
public class BidController {

  @Resource(name = "bidService")
  IBidService bidService;
  
  @InitBinder("bid")
  public void initBidBinder(DataBinder binder) {
    binder.addValidators(new BidValidator());
  }

  @RequestMapping(value="/commit_{productId}", method = RequestMethod.POST)
  public ModelAndView commitBidInfo(@Valid @ModelAttribute(ConstantUtil.USERBID) Bid userBid, @PathVariable("productId") int productId) {
    ModelAndView mv = new ModelAndView();
    bidService.createBid(userBid);
    mv.setViewName("redirect:/product/detail/" + productId);
    return mv;
  }
}
