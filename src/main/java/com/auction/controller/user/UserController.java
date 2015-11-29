package com.auction.controller.user;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
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

import com.auction.controller.IPTimeStamp;
import com.auction.model.User;
import com.auction.model.validator.UserValidator;
import com.auction.service.IUserService;

@Controller
@RequestMapping("/user")
public class UserController {

  @Resource(name = "userService")
  private IUserService userService;

  @InitBinder
  public void initBinder(DataBinder binder) {
    binder.setValidator(new UserValidator());
  }

  private ServletContext getServletContext(HttpServletRequest request) {
    // WebApplicationContext webApplicationContext =
    // ContextLoader.getCurrentWebApplicationContext();
    // return webApplicationContext.getServletContext();
    return request.getSession().getServletContext();
  }

  // 获得上传图像文件的相对路径（相对于项目根目录）
  private String genAvatarFileName(HttpServletRequest request, String oriFileName) {
    StringBuilder stringBuilder = new StringBuilder();
    // 获得文件的后缀名称。
    String[] fileNameSplitInfo = oriFileName.split("\\.");
    String suffix = "jpg";
    if (fileNameSplitInfo.length >= 1) {
      suffix = fileNameSplitInfo[fileNameSplitInfo.length - 1];
    }
    // 获得格式化的文件名称
    stringBuilder.append(new IPTimeStamp(request.getRemoteAddr()).getIpTimeRand());
    stringBuilder.append("." + suffix);
    String avatarFilePath = "images" + File.separator + "avatar" + File.separator + stringBuilder.toString();
    return avatarFilePath;
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
    String avatarFilePath = this.genAvatarFileName(request, user.getAvatarFile().getOriginalFilename());
    // image 引用的时候要 / 的格式才能引用出来
    user.setAvatarPath(avatarFilePath);
    // 写文件的时候需要注意当前系统的文件路径分隔符。写入服务器中的对应目录下。
    String avatarCorePath = this.getServletContext(request).getRealPath("/") + avatarFilePath;
    // 注册信息符合要求，写入数据库
    if (userService.createUser(user)) {
      // 此时开始写入图片信息
      byte[] bytes;
      try {
        bytes = user.getAvatarFile().getBytes();
        // 创建新文件的输出流。
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(avatarCorePath)));
        stream.write(bytes);
        stream.close();
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
    httpSession.setAttribute("LOGINUSER", loginUser);
    return "/index";
  }
}
