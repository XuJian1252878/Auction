package com.auction.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.auction.model.Category;
import com.auction.service.ICategoryService;

@Controller
@RequestMapping(value="/")
public class IndexController {

  @Resource(name = "categoryService")
  ICategoryService categoryService;

  @RequestMapping(value="/index", method=RequestMethod.GET)
  public ModelAndView index() {
    ModelAndView mv = new ModelAndView();
    // 获得当前商品类别的列表
    List<Category> categoryList = categoryService.loadCategory(-1, -1);
    mv.addObject("categoryList", categoryList);
    mv.setViewName("index");
    return mv;
  }
}
