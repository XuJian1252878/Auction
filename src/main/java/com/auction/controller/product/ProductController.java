package com.auction.controller.product;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
import com.auction.util.DateTimeUtil;
import com.auction.util.ImageUtil;

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
  public String uploadProduct(@ModelAttribute("product") Product product, BindingResult result, HttpServletRequest request) {
    // 获得当前的时间，拍卖商品的拍卖截止时间不能早于当前时间。
    long curTimeMillis = DateTimeUtil.getCurrentTimeMillis();
    long endTimeMillis = Long.parseLong(request.getParameter("endTimeMillis"));
    if (endTimeMillis < curTimeMillis) {
      result.rejectValue("endDate", "product.bid.end.date.error");
      return "redirect:/product/upload";
    }
    // 设置商品拍卖相关时间
    product.setOnSaleDate(DateTimeUtil.timeMillisToDate(curTimeMillis));
    product.setEndDate(DateTimeUtil.timeMillisToDate(endTimeMillis));
    // 上传商品图片
    String imgPath = ImageUtil.genImgFileName(request, ConstantUtil.PRODUCTFOLDER, product.getImgFile().getOriginalFilename());
    product.setImgPath(imgPath);
    ImageUtil.saveImgFile(request, product.getImgFile(), result, imgPath);
    if (result.hasErrors()) {
      return "redirect:/product/upload";
    }
    // 保存商品信息
    if (!productService.createProduct(product)) {
      return "redirect:/product/upload";
    }
    return "redirect:/user/transaction";
  }
}
