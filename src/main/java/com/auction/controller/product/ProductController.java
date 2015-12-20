package com.auction.controller.product;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.auction.model.Category;
import com.auction.model.Product;
import com.auction.model.User;
import com.auction.service.ICategoryService;
import com.auction.service.IProductService;
import com.auction.util.ConstantUtil;

@Controller
@RequestMapping("/product")
public class ProductController {

  @Resource(name = "productService")
  IProductService productService;

  @Resource(name = "categoryService")
  ICategoryService CategoryService;

  @RequestMapping(value = "/upload", method=RequestMethod.GET)
  public ModelAndView uploadProduct(HttpSession session) {
    ModelAndView mv = new ModelAndView();
    // 只有登陆了才能到达这个页面，所以不太可能存在loginUser为空的情况。
    User loginUser = (User)session.getAttribute(ConstantUtil.LOGINUSER);
    mv.addObject("loginUser", loginUser);
    // 创建一个新的产品实体，用于存储上传的产品信息
    Product product = new Product();
    product.setUser(loginUser);
    mv.addObject("product", product);
    // 选出所有的商品类别信息
    List<Category> categories = CategoryService.loadCategory(-1, -1);
    mv.addObject("categories", categories);
    mv.setViewName("/product/upload");
    return mv;
  }
  
  @RequestMapping(value = "/upload", method=RequestMethod.POST)
  public ModelAndView uploadProduct(@ModelAttribute("product") Product product, BindingResult result) {
    ModelAndView mv = new ModelAndView();
    return mv;
  }
}
