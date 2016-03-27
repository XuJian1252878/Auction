package com.auction.controller.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.auction.model.User;
import com.auction.model.test.ImgTestEntity;
import com.auction.service.IUserService;

@Controller
public class TestController {

  private final int IMG_PAGE_COUNT = 20;
  private final int IMG_COUNT = 100;

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

  @RequestMapping(value = "json", method = RequestMethod.GET)
  public ModelAndView transferJson() {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("test/json");
    return mv;
  }

  @RequestMapping(value = "/jsondata")
  @ResponseBody
  public List<User> getUsers() {
    return userService.getAllUser();
  }

  @RequestMapping(value = "/imgdata/{page}")
  @ResponseBody
  public Map<String, Object> getImgTestEntity(@PathVariable("page") int page) {
    // 计算当前商品的下标与当前页数之间的关系。
    if (page < 1) {
      page = 1;
    }
    int startIndex = (page - 1) * IMG_PAGE_COUNT;
    Map<String, Object> map = new HashMap<String, Object>();
    List<ImgTestEntity> testImgs = new ArrayList<ImgTestEntity>();
    if (startIndex >= IMG_COUNT) {
      return null;
    }
    int endIndex = page * IMG_PAGE_COUNT;
    map.put("total", IMG_PAGE_COUNT);

    // map.put("curPage", page);
    // map.put("pageCount", Math.ceil(IMG_COUNT / (float)IMG_PAGE_COUNT));

    // 填充商品的图片实体信息。
    for (int index = startIndex; index < endIndex; index++) {
      int productId = index;
      ImgTestEntity ite = new ImgTestEntity("images/test/" + addZeroToNum(index, 3) + ".jpg", 300, 300,
          "productexpireclock" + productId, "productexpirealert" + productId, 10000);
      testImgs.add(ite);
    }
    map.put("result", testImgs);
    return map;
  }

  @RequestMapping(value = "/imgdatatxt/{page}", method = RequestMethod.GET)
  @ResponseBody
  public Map<String, Object> getImgTestTextEntity(@PathVariable("page") int page) {
    if (page < 1) {
      page = 1;
    }
    int startIndex = (page - 1) * IMG_PAGE_COUNT;
    Map<String, Object> map = new HashMap<String, Object>();
    List<ImgTestEntity> testImgs = new ArrayList<ImgTestEntity>();
    if (startIndex >= IMG_COUNT) {
      return null;
    }
    int endIndex = page * IMG_PAGE_COUNT;
    map.put("total", IMG_PAGE_COUNT);

    for (int index = startIndex; index < endIndex; index++) {
      int productId = index;
      ImgTestEntity ite = new ImgTestEntity("images/test/" + addZeroToNum(index, 3) + ".jpg", 300, 300,
          "productexpireclock" + productId, "productexpirealert" + productId, 10000);
      testImgs.add(ite);
    }
    map.put("result", testImgs);
    return map;
  }

  private String addZeroToNum(int num, int len) {
    if (num < 1) {
      return "001";
    }
    String strNum = num + "";
    if (strNum.length() >= 3) {
      return strNum;
    }
    StringBuilder sBuilder = new StringBuilder(strNum);
    while (sBuilder.length() < 3) {
      sBuilder.insert(0, '0');
    }
    return sBuilder.toString();
  }
}
