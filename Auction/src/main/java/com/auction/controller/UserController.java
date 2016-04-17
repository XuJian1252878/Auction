package com.auction.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

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
import com.auction.util.WebConstantUtil;
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

  // 这里的名称是同modelattribute的名称相对应的。如果不对应那么无法对实体进行检查。
  // 如果不指定名称，那么将会对所有的valid标签标注的对象进行检查，容易发生错误。
  @InitBinder("registerUser")
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
    String avatarFilePath = ImageUtil.genImgFileName(request, WebConstantUtil.AVATARFOLDER,
        user.getAvatarFile().getOriginalFilename());
    // image 引用的时候要 / 的格式才能引用出来
    user.setAvatarPath(avatarFilePath);
    // 注册信息符合要求，写入数据库
    int resFlag = userService.existsUser(user);
    if (resFlag == 0) {
      result.rejectValue("userName", "register.user.name.already.exist");
     // 注册的用户名称已经存在。
      return "/user/register";
    } else if (resFlag == 1) {
     // 注册的用户邮箱已经存在。
      result.rejectValue("email", "register.user.email.already.exist");
      return "/user/register";
    }
    // 此时开始写入图片信息
    ImageUtil.saveImgFile(request, user.getAvatarFile(), result, avatarFilePath);
    if (result.hasErrors()) {
      return "/user/register";// 如果注册过程中出现错误，那么返回原来的页面。
    }
    userService.saveUser(user); // 保存用户信息
    return "redirect:/index";
  }

  /**
   * 用户进入登陆页面的时候
   * 
   * @param model
   * @return
   */
  @RequestMapping(value = "/login", method = RequestMethod.GET)
  public String Login(Model model) {
    model.addAttribute(WebConstantUtil.LOGINUSER, new User());
    return "/user/login";
  }

  /**
   * 用户登录函数。
   * @param user
   * @param result
   * @param httpSession
   * @return
   */
  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public String Login(@ModelAttribute(WebConstantUtil.LOGINUSER) User user, BindingResult result,
      HttpSession httpSession) {
    if (result.hasErrors()) {
      System.out.println("raise error");
      return "/user/login";
    }
    // 因为前端的html代码中是按照userName传到后台的。
    String userNameOrEmail = user.getUserName();
    String userPwd = user.getPassword();
    Map<String, Object> loginResMap = userService.userLogin(userNameOrEmail, userPwd);
    int loginSuccessFlag = (Integer)loginResMap.get(WebConstantUtil.USER_LOGIN_SUCCESS_FLAG);
    if (loginSuccessFlag == 0) {
      // 用户名称（用户邮箱）不存在。
      result.rejectValue("userName", "login.user.name.notexist");
      return "/user/login";
    } else if (loginSuccessFlag == 1) {
      // 用户输入了错误的密码。
      result.rejectValue("password", "login.user.password.error");
      return "/user/login";
    }
    // 用户的用户名和密码都已输入正确。
    User loginUser = (User)loginResMap.get(WebConstantUtil.USER_LOGIN_OBJECT_FLAG);
 // 应该还要在Session中设置User http://www.tuicool.com/articles/m2iimaa
    httpSession.setAttribute(WebConstantUtil.LOGINUSER, loginUser);
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
    if (httpSession.getAttribute(WebConstantUtil.LOGINUSER) != null) {
      httpSession.setAttribute(WebConstantUtil.LOGINUSER, null);
    }
    return "redirect:/index";
  }

  /**
   * 这里采用 registerUser 来作为valid参数的名称，是为了与initBinder指定的名称对应，能对用户实体信息进行检查。
   * @param httpSession
   * @return
   */
  @RequestMapping(value = "/profile", method = RequestMethod.GET)
  public ModelAndView userProfile(HttpSession httpSession) {
    ModelAndView mv = new ModelAndView();
    mv.addObject("registerUser", httpSession.getAttribute(WebConstantUtil.LOGINUSER));
    mv.setViewName("/user/profile");
    return mv;
  }

  @RequestMapping(value = "/profile", method = RequestMethod.POST)
  public ModelAndView updateProfile(@Valid @ModelAttribute("registerUser") User user, BindingResult result,
      HttpSession httpSession, HttpServletRequest request) {
    // merge方式更新用户的信息，正常的域信息需要在表当中声明出来，比如input ...，外键的域不用在表单中声明出来，
    // 不会受merge更新的影响。
    ModelAndView mv = new ModelAndView();
    User oriUser = (User) httpSession.getAttribute(WebConstantUtil.LOGINUSER);
    if (result.hasErrors()) {
      mv.addObject("registerUser", oriUser);
      return mv;
    }

    // 开始更新用户的信息
    String avatarFilePath = ImageUtil.genImgFileName(request, WebConstantUtil.AVATARFOLDER,
        user.getAvatarFile().getOriginalFilename());  // 获取新的头像文件的名称，文件名称若无后缀，那么会返回null。
    if (user.getAvatarFile().getSize() != 0) { // 说明用户更改了头像信息
      // the size of the file, or 0 if empty 如果 MultipartFile 的size为0，那么说明用户根本没上传头像信息。
      user.setAvatarPath(avatarFilePath);
    }

    if (userService.updateUser(user) == false) {
      result.rejectValue("id", "profile.user.not.exists");
      mv.addObject("registerUser", oriUser);
      return mv;
    }
    if (user.getAvatarFile().getSize() != 0) {
      // 虽然没上传头像，但是 MultipartFile 对象还是不为null。
      // the size of the file, or 0 if empty 如果 MultipartFile 的size为0，那么说明用户根本没上传头像信息。
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
      mv.addObject("registerUser", oriUser);
      return mv;
    }
    httpSession.setAttribute(WebConstantUtil.LOGINUSER, newUser);
    mv.addObject("registerUser", newUser);
    mv.setViewName("/user/profile");
    return mv;
  }

  /**
   * 返回用户对他人商品进行竞价的相关信息，包括正在进行的交易，已经完成的交易等。
   * 
   * @param httpSession
   *          为了取出登陆用户的信息而使用。
   * @return
   */
  @RequestMapping(value = "/transaction", method = RequestMethod.GET)
  public ModelAndView userTransaction(HttpSession httpSession) {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("user/transaction");
    User loginUser = (User) httpSession.getAttribute(WebConstantUtil.LOGINUSER);
    if (loginUser == null) {
      return mv; // 不会走到这一步，用户登录之后才可以看自己相关的交易信息。
    }
    // 取出正在进行的竞价信息。
    List<Bid> goingOnBids = bidService.getGoingOnBids(loginUser.getId());
    // 取出竞价的历史信息。
    List<Bid> historyBids = bidService.getHistoryBids(loginUser.getId());
    // 取出已经成交的竞价信息。
    List<Bid> dealBids = bidService.getDealBids(loginUser.getId());
    // 用户可能要修改竞价信息，需要提供一个bid的实例供提交表单的使用。
    Bid bid = new Bid();
    mv.addObject(WebConstantUtil.USERBID, bid);
    mv.addObject("goingOnBids", goingOnBids);
    mv.addObject("historyBids", historyBids);
    mv.addObject("dealBids", dealBids);
    return mv;
  }

  /**
   * 获得用户上传的所有竞价商品信息，分为正在进行以及已经完结的两个商品list列表，列表以商品的上传时间降序排序。
   * 
   * @param httpSession
   * @return
   */
  @RequestMapping(value = "/products")
  public ModelAndView uploadProductsRecords(HttpSession httpSession) {
    ModelAndView mv = new ModelAndView();
    User loginUser = (User) httpSession.getAttribute(WebConstantUtil.LOGINUSER);
    // 获得用户上传的，并且正在被竞价的商品列表。
    List<Product> goingOnProducts = productService.getGoingOnProductsByUser(loginUser.getId());
    // 获得用户上传的，但是已经结束竞价的商品列表。
    Map<Product, Bid> historyProductsMap = productService.getHistoryProductsByUser(loginUser.getId());
    mv.addObject("goingOnProducts", goingOnProducts);
    mv.addObject("historyProductsMap", historyProductsMap);
    // 用户可能会同意某一个竞价，所以先设置一个modelAttribute。
    Bid dealBid = new Bid();
    mv.addObject("bid", dealBid);
    mv.setViewName("/user/product");
    return mv;
  }
}