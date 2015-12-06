package com.auction.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {

  @RequestMapping(value="/imageselect")
  public String imgSelectTest() {
    return "test/imageselect";
  }
  
}
