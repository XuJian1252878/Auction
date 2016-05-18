package com.auction.mobile.controller;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.auction.mobile.model.CategoryInfo;
import com.auction.mobile.model.ProductBriefInfo;
import com.auction.model.Category;
import com.auction.model.Product;
import com.auction.service.ICategoryService;

@Controller
@RequestMapping("/mobile/category")
public class MobileCategoryController {

  @Resource(name = "categoryService")
  ICategoryService categoryService;
  
  @RequestMapping(value = "/all", method = RequestMethod.GET)
  @ResponseBody
  public List<Object> loadCategories(){
    List<Category> categories = categoryService.loadCategory(-1, -1);
    List<Object> categoryInfos = new ArrayList<Object>();
    for (Category category : categories) {
      CategoryInfo cInfo = new CategoryInfo();
      cInfo.setId(category.getId());
      cInfo.setName(category.getName());
      cInfo.setCdesc(category.getCdesc());
      categoryInfos.add(cInfo);
    }
    return categoryInfos;
  }

  @RequestMapping(value = "/products/{categoryId}", method = RequestMethod.GET)
  @ResponseBody
  public List<Object> loadProductsByCategory(@PathVariable int categoryId){
    List<Product> products = categoryService.loadProducts(categoryId, -1, -1, false);
    List<Object> productBriefInfos = new ArrayList<Object>();
    for (Product product : products) {
      ProductBriefInfo pbInfo = new ProductBriefInfo();
      pbInfo.setId(product.getId());
      pbInfo.setName(product.getName());
      productBriefInfos.add(pbInfo);
    }
    return productBriefInfos;
    
  }
}
