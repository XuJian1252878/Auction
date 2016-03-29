package com.auction.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import com.auction.model.validator.MessageValidator;
import com.auction.service.IMessageService;

@Controller
@RequestMapping("/message")
public class MessageController {

  @Resource(name = "messageService")
  private IMessageService messageService;

  @InitBinder
  public void initMessageBinder(DataBinder binder) {
    binder.addValidators(new MessageValidator());
  }
}
