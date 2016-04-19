package com.auction.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.auction.model.Category;
import com.auction.model.User;
import com.auction.service.ICategoryService;
import com.auction.service.IMessageService;
import com.auction.util.WebConstantUtil;

@Controller
@RequestMapping(value="/")
public class IndexController {

  @Resource(name = "categoryService")
  ICategoryService categoryService;
  
  @Resource(name = "messageService")
  private IMessageService messageService;

  @RequestMapping(value="/index", method=RequestMethod.GET)
  public ModelAndView index(HttpSession httpSession) {
    User loginUser = (User)httpSession.getAttribute(WebConstantUtil.LOGINUSER);
    ModelAndView mv = new ModelAndView();
    // 获得当前商品类别的列表
    List<Category> categoryList = categoryService.loadCategory(-1, -1);
    mv.addObject("categoryList", categoryList);
    // 同时读取出用户未读消息的总数。
    if (loginUser != null) {
      int unreadMessageCount = messageService.getUnreadMessageCount(loginUser.getId());
      mv.addObject("unreadMessageCount", unreadMessageCount);
    }
    mv.setViewName("index");
    return mv;
  }

  /**
   * 跳转入项目关于界面。
   * @return
   */
  @RequestMapping("/about")
  public ModelAndView aboutProject() {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("about");
    return mv;
  }
}
