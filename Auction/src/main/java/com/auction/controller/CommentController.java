package com.auction.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import com.auction.model.validator.CommentValidator;

@Controller
@RequestMapping("/comment")
public class CommentController {
  
  @InitBinder("comment")
  public void initCommentBinder(DataBinder binder) {
    binder.addValidators(new CommentValidator());
  }

}
