package com.auction.controller.user;

import java.io.File;
import java.io.IOException;
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

import com.auction.controller.ImageTool;
import com.auction.model.User;
import com.auction.model.validator.UserValidator;
import com.auction.service.IUserService;
import com.auction.service.impl.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

  private static final String LOGINUSER = "LOGINUSER";
  
  @Resource(name = "userService")
  private IUserService userService;

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
    String avatarFilePath = ImageTool.genAvatarFileName(request, user.getAvatarFile().getOriginalFilename());
    // image 引用的时候要 / 的格式才能引用出来
    user.setAvatarPath(avatarFilePath);
    // 注册信息符合要求，写入数据库
    if (userService.createUser(user)) {
      // 此时开始写入图片信息
      try {
        ImageTool.saveAvatarImgFile(request, user.getAvatarFile(), result, avatarFilePath);
      } catch (IOException e) {
        // TODO Auto-generated catch block
        result.rejectValue("avatarFile", "register.user.avatar.upload.failed");
        e.printStackTrace();
        return "/user/register";
      }
      return "redirect:/index";
    }
    result.rejectValue("userName", "register.user.name.already.exist");
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
    model.addAttribute("loginUser", new User());
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
  public String Login(@ModelAttribute("loginUser") User user, BindingResult result, HttpSession httpSession) {
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
    httpSession.setAttribute(LOGINUSER, loginUser);
    return "/index";
  }

  /**
   * 
   * @param httpSession
   * @return
   */
  @RequestMapping(value = "/logout")
  public String logout(HttpSession httpSession) {
    if (httpSession.getAttribute(LOGINUSER) != null) {
      httpSession.setAttribute(LOGINUSER, null);
    }
    return "redirect:/index";
  }
  
  /**
   * 
   * @param httpSession
   * @return
   */
  @RequestMapping(value="/profile", method=RequestMethod.GET)
  public ModelAndView userProfile(HttpSession httpSession) {
    ModelAndView mv = new ModelAndView();
    mv.addObject("loginUser", httpSession.getAttribute(LOGINUSER));
    mv.setViewName("/user/profile");
    return mv;
  }
  
  /**
   * 
   * @param user
   * @param result
   * @param httpSession
   * @param request
   * @return
   */
  @RequestMapping(value = "/profile", method = RequestMethod.POST)
  public ModelAndView updateProfile(@Valid @ModelAttribute("loginUser") User user, BindingResult result,
      HttpSession httpSession, HttpServletRequest request) {
    ModelAndView mv = new ModelAndView();
    User oriUser = (User)httpSession.getAttribute(LOGINUSER);
    if (result.hasErrors()) {
      mv.addObject("loginUser", oriUser);
      return mv;
    }

    // 开始更新用户的信息
    String avatarFilePath = ImageTool.genAvatarFileName(request, user.getAvatarFile().getOriginalFilename());
    if (user.getAvatarFile() != null) { // 说明用户更改了头像信息
      user.setAvatarPath(avatarFilePath);
    }

    if (userService.updateUser(user) == false) {
      result.rejectValue("id", "profile.user.not.exists");
      mv.addObject("loginUser", oriUser);
      return mv;
    }
    if (user.getAvatarFile() != null) {
      // 删除原来的头像文件
      String oriAvatarPath = request.getSession().getServletContext().getRealPath("/") + oriUser.getAvatarPath();
      File avatarFile = new File(oriAvatarPath);
      if (avatarFile.exists() && avatarFile.isFile()) {
        avatarFile.delete(); // 删除原来的头像文件。
      }
      try {
        ImageTool.saveAvatarImgFile(request, user.getAvatarFile(), result, avatarFilePath);
      } catch (IOException e) {
        result.rejectValue("avatarFile", "register.user.avatar.upload.failed");
        e.printStackTrace();
      }
    }
    // 更新session中的登陆用户信息
    User newUser = userService.findUserById(user.getId());
    if (newUser == null) {
      result.rejectValue("id", "profile.user.id.not.exist");
      mv.addObject("loginUser", oriUser);
      return mv;
    }
    httpSession.setAttribute(LOGINUSER, newUser);
    mv.setViewName("/user/profile");
    return mv;
  }
}
