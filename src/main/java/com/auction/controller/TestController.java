package com.auction.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.auction.model.User;
import com.auction.service.IUserService;

@Controller
public class TestController {

  @Resource(name = "userService")
  IUserService userService;
  
  @RequestMapping(value = "/imageselect")
  public String imgSelectTest() {
    return "test/imageselect";
  }

  @RequestMapping(value = "/countdown")
  public String countDown() {
    return "test/countdown";
  }

  @RequestMapping(value = "/json")
  @ResponseBody
  public List<User> getUsers() {
    return userService.getAllUser();
  }
}
