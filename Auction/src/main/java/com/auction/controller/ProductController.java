package com.auction.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.auction.model.Bid;
import com.auction.model.Category;
import com.auction.model.Comment;
import com.auction.model.Product;
import com.auction.model.User;
import com.auction.model.validator.ProductValidator;
import com.auction.service.IBidService;
import com.auction.service.ICategoryService;
import com.auction.service.ICommentService;
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

  @Resource(name = "bidService")
  IBidService bidService;
  
  @Resource(name = "commentService")
  ICommentService commentService;

  // http://stackoverflow.com/questions/14533488/addiing-multiple-validators-using-initbinder
  @InitBinder("product") // 注意这里 InitBinder 参数命令 需要为被检测对象的名称。
  public void initProductBinder(DataBinder binder) {
    binder.addValidators(new ProductValidator());
  }

  @RequestMapping(value = "/upload", method = RequestMethod.GET)
  public ModelAndView uploadProduct(HttpSession session) {
    ModelAndView mv = new ModelAndView();
    // 只有登陆了才能到达这个页面，所以不太可能存在loginUser为空的情况。
    User loginUser = (User) session.getAttribute(ConstantUtil.LOGINUSER);
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

  @RequestMapping(value = "/upload", method = RequestMethod.POST)
  public ModelAndView uploadProduct(@Valid @ModelAttribute("product") Product product, BindingResult result,
      HttpServletRequest request, HttpSession session) {
    User loginUser = (User) session.getAttribute(ConstantUtil.LOGINUSER);
    // 选出所有的商品类别信息
    List<Category> categories = CategoryService.loadCategory(-1, -1);
    ModelAndView mv = new ModelAndView();
    // 获得当前的时间，拍卖商品的拍卖截止时间不能早于当前时间。
    long curTimeMillis = DateTimeUtil.getCurrentTimeMillis();
    long endTimeMillis = Long.parseLong(request.getParameter("endTimeMillis"));
    if (endTimeMillis < curTimeMillis) {
      result.rejectValue("endDate", "product.bid.end.date.error");
      mv.addObject("loginUser", loginUser);
      mv.addObject("categories", categories);
      mv.setViewName("/product/upload");
      return mv;
    }
    // 设置商品拍卖相关时间
    product.setOnSaleDate(DateTimeUtil.timeMillisToDate(curTimeMillis));
    product.setEndDate(DateTimeUtil.timeMillisToDate(endTimeMillis));
    // 上传商品图片
    String imgPath = ImageUtil.genImgFileName(request, ConstantUtil.PRODUCTFOLDER,
        product.getImgFile().getOriginalFilename());
    product.setImgPath(imgPath);
    ImageUtil.saveImgFile(request, product.getImgFile(), result, imgPath);
    if (result.hasErrors()) {
      mv.setViewName("product/upload");
      mv.addObject("loginUser", loginUser);
      mv.addObject("categories", categories);
      return mv;
    }
    // 保存商品信息
    if (!productService.createProduct(product)) {
      mv.setViewName("product/upload");
      mv.addObject("loginUser", loginUser);
      mv.addObject("categories", categories);
      return mv;
    }
    mv.setViewName("redirect:/user/transaction");
    return mv;
  }

  @RequestMapping(value = "/detail/{productId}")
  public ModelAndView getProductDetail(@PathVariable("productId") int productId, HttpSession httpSession) {
    ModelAndView mv = new ModelAndView();
    User loginUser = (User) httpSession.getAttribute(ConstantUtil.LOGINUSER);
    Product product = productService.getProductById(productId);
    // 获得商品对应的分类信息。
    mv.addObject("product", product);
    // 用户可能需要竞价，提供竞价实体。
    Bid bid = new Bid();
    mv.addObject(ConstantUtil.USERBID, bid);
    if (loginUser != null) {
      if (!loginUser.getId().equals(product.getUser().getId())) {
        // 1. 用户之前可能对该商品进行过竞价，如有，那么显示竞价信息。
        // 2. 该用户不是上传该商品的用户。用户不能对自己上传的商品进行竞价。
        Bid oldBid = bidService.getBid(loginUser.getId(), productId);
        mv.addObject("oldBid", oldBid);
      } else if (loginUser.getId().equals(product.getUser().getId())) {
        // 登陆用户就是上传该商品的用户。那么该用户可以看到该商品的所有竞价信息。
        List<Bid> productBids = bidService.getAllBidsByProduct(productId);
        mv.addObject("productBids", productBids);
      }
      // 用户可能需要发表评论信息。
      Comment userComment = new Comment();
      mv.addObject("userComment", userComment);
    }
    // 加载该商品的相关评论列表。
    List<Comment> productComments = commentService.getProductComments(productId);
    mv.addObject("productComments", productComments);
    mv.setViewName("/product/detail");
    return mv;
  }
}