package com.auction.mobile.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.auction.model.Category;
import com.auction.model.Product;
import com.auction.service.ICategoryService;
import com.auction.util.MobileConstantUtil;

@Controller
@RequestMapping("/mobile/category")
public class MobileCategoryController {

  @Resource(name = "categoryService")
  ICategoryService categoryService;
  
  @RequestMapping(value = "/all", method = RequestMethod.GET)
  @ResponseBody
  public Map<String, Object> loadCategories(){
    Map<String, Object> resMap = new HashMap<String, Object>();
    List<Category> categories = categoryService.loadCategory(-1, -1);
    resMap.put("categories", categories);
    return resMap;
  }

  @RequestMapping(value = "/products/", method = RequestMethod.POST)
  @ResponseBody
  public Map<String, Object> loadProductsByCategory(HttpServletRequest request){
    int categoryId = Integer.parseInt(request.getParameter(MobileConstantUtil.MOBILE_CATEGORY_ID));
    Map<String, Object> resMap = new HashMap<String, Object>();
    List<Product> products = categoryService.loadProducts(categoryId, 1, 10, false);
    resMap.put("products", products);
    return resMap;
    
  }
}
