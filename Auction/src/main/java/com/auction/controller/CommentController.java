package com.auction.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.auction.model.Comment;
import com.auction.model.validator.CommentValidator;
import com.auction.service.ICommentService;

@Controller
@RequestMapping("/comment")
public class CommentController {

  @Resource(name = "commentService")
  ICommentService commentService;

  @InitBinder("comment")
  public void initCommentBinder(DataBinder binder) {
    binder.addValidators(new CommentValidator());
  }

  @RequestMapping(value = "/pub", method = RequestMethod.POST)
  public ModelAndView pubUserComment(@Valid @ModelAttribute("userComment") Comment userComment) {
    ModelAndView mv = new ModelAndView();
    // 设置用户的发帖日期。
    userComment.setPubDate(new Date());
    commentService.pubComment(userComment);
    // 存储评论信息后返回原来的商品详情列表。
    mv.setViewName("redirect:/product/detail/" + userComment.getProduct().getId());
    return mv;
  }
}
