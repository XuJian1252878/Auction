package com.auction.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.auction.model.BidNotification;
import com.auction.model.User;
import com.auction.model.validator.BidNotificationValidator;
import com.auction.model.validator.MessageValidator;
import com.auction.service.IMessageService;
import com.auction.util.WebConstantUtil;

@Controller
@RequestMapping("/message")
public class MessageController {

  @Resource(name = "messageService")
  private IMessageService messageService;

  @InitBinder("message")
  public void initMessageBinder(DataBinder binder) {
    binder.addValidators(new MessageValidator());
  }

  @InitBinder("bidNotification")
  public void initBidNotificationBinder(DataBinder binder) {
    binder.addValidators(new BidNotificationValidator());
  }

  @RequestMapping(value = "/list")
  public ModelAndView userMessages(HttpSession httpSession) {
    User loginUser = (User)httpSession.getAttribute(WebConstantUtil.LOGINUSER);
    ModelAndView mv = new ModelAndView();
    // 获取全部未读的竞价消息。
    List<BidNotification> unreadBidNotifications = messageService.getUnreadBidNotifications(loginUser.getId());
    mv.addObject("unreadBidNotifications", unreadBidNotifications);
    // 获取全部的历史竞价消息。
    List<BidNotification> historyBidNotifications = messageService.getHistoryBidNotification(loginUser.getId());
    mv.addObject("historyBidNotifications", historyBidNotifications);
    mv.setViewName("user/message");
    return mv;
  }
  
  @RequestMapping(value = "/markread/{notificationId}")
  public ModelAndView markMessageAsRead(@PathVariable("notificationId") int notificationId) {
    ModelAndView mv = new ModelAndView();
    // 将该通知信息设置为已读。
    messageService.markBidNotificationAsRead(notificationId);
    mv.setViewName("redirect:/message/list");
    return mv;
  }
}
