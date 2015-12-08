package com.auction.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {

  @RequestMapping(value = "/imageselect")
  public String imgSelectTest() {
    return "test/imageselect";
  }

  @RequestMapping(value = "/countdown")
  public String countDown() {
    return "test/countdown";
  }

}
