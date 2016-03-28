package com.auction.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.auction.model.Bid;
import com.auction.model.Product;
import com.auction.model.User;
import com.auction.model.validator.UserValidator;
import com.auction.service.IBidService;
import com.auction.service.IProductService;
import com.auction.service.IUserService;
import com.auction.util.ConstantUtil;
import com.auction.util.ImageUtil;

@Controller
@RequestMapping("/user")
public class UserController {

  @Resource(name = "userService")
  private IUserService userService;

  @Resource(name = "productService")
  private IProductService productService;

  @Resource(name = "bidService")
  private IBidService bidService;

  @InitBinder
  public void initBinder(DataBinder binder) {
    binder.setValidator(new UserValidator());
  }

  @RequestMapping(value = "/register", method = RequestMethod.GET)
  public String register(Model model) {
    // 用model的attribute存住，然后才能用@ModelAttribute标签取出。
    model.addAttribute("registerUser", new User());
    return "user/register";
  }

  /**
   * 注册表单上传时，进行处理的register函数
   * 
   * @param user
   * @param result
   * @return
   * @throws IOException
   */
  @RequestMapping(value = "/register", method = RequestMethod.POST)
  public String register(@Valid @ModelAttribute("registerUser") User user, BindingResult result,
      HttpServletRequest request) {
    // As a result of data binding there may be errors such as missing required
    // fields or type conversion errors.
    if (result.hasErrors()) {
      return "/user/register";// 如果注册过程中出现错误，那么返回原来的页面。
    }
    // 首先获得图片将要保存在的路径信息。
    String avatarFilePath = ImageUtil.genImgFileName(request, ConstantUtil.AVATARFOLDER,
        user.getAvatarFile().getOriginalFilename());
    // image 引用的时候要 / 的格式才能引用出来
    user.setAvatarPath(avatarFilePath);
    // 注册信息符合要求，写入数据库
    if (userService.existsUser(user, result)) {
      // 此时开始写入图片信息
      ImageUtil.saveImgFile(request, user.getAvatarFile(), result, avatarFilePath);
      if (result.hasErrors()) {
        return "/user/register";// 如果注册过程中出现错误，那么返回原来的页面。
      }
      userService.saveUser(user); // 保存用户信息
      return "redirect:/index";
    }
    // 注册的用户名称或者是用户信息已经存在。
    return "/user/register";
  }

  /**
   * 用户进入登陆页面的时候
   * 
   * @param model
   * @return
   */
  @RequestMapping(value = "/login", method = RequestMethod.GET)
  public String Login(Model model) {
    model.addAttribute(ConstantUtil.LOGINUSER, new User());
    return "/user/login";
  }

  /**
   * 用户登录函数
   * 
   * @param user
   * @param result
   * @param httpSession
   * @return
   */
  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public String Login(@ModelAttribute(ConstantUtil.LOGINUSER) User user, BindingResult result,
      HttpSession httpSession) {
    if (result.hasErrors()) {
      System.out.println("raise error");
      return "/user/login";
    }
    // 因为前端的html代码中是按照userName传到后台的。
    String userNameOrEmail = user.getUserName();
    User loginUser = userService.getUserByEmail(userNameOrEmail);
    if (loginUser == null) {
      loginUser = userService.getUserByName(userNameOrEmail);
      if (loginUser == null) {
        result.rejectValue("userName", "login.user.name.notexist");
        return "/user/login";
      }
    }
    // 查看登陆密码是否正确
    if (!user.getPassword().equals(loginUser.getPassword())) {
      result.rejectValue("password", "login.user.password.error");
      return "/user/login";
    }
    // 应该还要在Session中设置User http://www.tuicool.com/articles/m2iimaa
    httpSession.setAttribute(ConstantUtil.LOGINUSER, loginUser);
    // 使用redirect可以在当前的controller中跳转到另一个controller中去。
    return "redirect:/index";
  }

  /**
   * 
   * @param httpSession
   * @return
   */
  @RequestMapping(value = "/logout")
  public String logout(HttpSession httpSession) {
    if (httpSession.getAttribute(ConstantUtil.LOGINUSER) != null) {
      httpSession.setAttribute(ConstantUtil.LOGINUSER, null);
    }
    return "redirect:/index";
  }

  /**
   * 
   * @param httpSession
   * @return
   */
  @RequestMapping(value = "/profile", method = RequestMethod.GET)
  public ModelAndView userProfile(HttpSession httpSession) {
    ModelAndView mv = new ModelAndView();
    mv.addObject(ConstantUtil.LOGINUSER, httpSession.getAttribute(ConstantUtil.LOGINUSER));
    mv.setViewName("/user/profile");
    return mv;
  }

  @RequestMapping(value = "/profile", method = RequestMethod.POST)
  public ModelAndView updateProfile(@Valid @ModelAttribute(ConstantUtil.LOGINUSER) User user, BindingResult result,
      HttpSession httpSession, HttpServletRequest request) {
    ModelAndView mv = new ModelAndView();
    User oriUser = (User) httpSession.getAttribute(ConstantUtil.LOGINUSER);
    if (result.hasErrors()) {
      mv.addObject(ConstantUtil.LOGINUSER, oriUser);
      return mv;
    }

    // 开始更新用户的信息
    String avatarFilePath = ImageUtil.genImgFileName(request, ConstantUtil.AVATARFOLDER,
        user.getAvatarFile().getOriginalFilename());
    if (user.getAvatarFile() != null) { // 说明用户更改了头像信息
      user.setAvatarPath(avatarFilePath);
    }

    if (userService.updateUser(user) == false) {
      result.rejectValue("id", "profile.user.not.exists");
      mv.addObject(ConstantUtil.LOGINUSER, oriUser);
      return mv;
    }
    if (user.getAvatarFile() != null) {
      // 删除原来的头像文件
      String oriAvatarPath = request.getSession().getServletContext().getRealPath("/") + oriUser.getAvatarPath();
      File avatarFile = new File(oriAvatarPath);
      if (avatarFile.exists() && avatarFile.isFile()) {
        avatarFile.delete(); // 删除原来的头像文件。
      }
      ImageUtil.saveImgFile(request, user.getAvatarFile(), result, avatarFilePath);
    }
    // 更新session中的登陆用户信息
    User newUser = userService.findUserById(user.getId());
    if (newUser == null) {
      result.rejectValue("id", "profile.user.id.not.exist");
      mv.addObject(ConstantUtil.LOGINUSER, oriUser);
      return mv;
    }
    httpSession.setAttribute(ConstantUtil.LOGINUSER, newUser);
    mv.addObject(ConstantUtil.LOGINUSER, newUser);
    mv.setViewName("/user/profile");
    return mv;
  }

  /**
   * 返回用户对他人商品进行竞价的相关信息，包括正在进行的交易，已经完成的交易等。
   * @param httpSession  为了取出登陆用户的信息而使用。
   * @return
   */
  @RequestMapping(value = "/transaction", method = RequestMethod.GET)
  public ModelAndView userTransaction(HttpSession httpSession) {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("user/transaction");
    User loginUser = (User)httpSession.getAttribute(ConstantUtil.LOGINUSER);
    if (loginUser == null) {
      return mv;  // 不会走到这一步，用户登录之后才可以看自己相关的交易信息。
    }
    // 取出正在进行的竞价信息。
    List<Bid> goingOnBids = bidService.getGoingOnBids(loginUser.getId());
    // 取出竞价的历史信息。
    List<Bid> historyBids = bidService.getHistoryBids(loginUser.getId());
    // 取出已经成交的竞价信息。
    List<Bid> dealBids = bidService.getDealBids(loginUser.getId());
    mv.addObject("goingOnBids", goingOnBids);
    mv.addObject("historyBids", historyBids);
    mv.addObject("dealBids", dealBids);
    return mv;
  }

  /**
   * 获得用户上传的所有竞价商品信息，分为正在进行以及已经完结的两个商品list列表，列表以商品的上传时间降序排序。
   * @param httpSession
   * @return
   */
  @RequestMapping(value = "/products")
  public ModelAndView uploadProductsRecords(HttpSession httpSession) {
    ModelAndView mv = new ModelAndView();
    User loginUser = (User)httpSession.getAttribute(ConstantUtil.LOGINUSER);
    // 获得用户上传的，并且正在被竞价的商品列表。
    List<Product> goingOnProducts = productService.getGoingOnProductsByUser(loginUser.getId());
    // 获得用户上传的，但是已经结束竞价的商品列表。
    List<Product> historyProducts = productService.getHistoryProductsByUser(loginUser.getId());
    mv.addObject("goingOnProducts", goingOnProducts);
    mv.addObject("historyProducts", historyProducts);
    mv.setViewName("/user/product");
    return mv;
  }
}