package com.auction.mobile.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.auction.service.IUserService;
import com.auction.util.MobileConstantUtil;

@Controller
@RequestMapping("/mobile/user")
public class MobileUserController {

  @Resource(name = "userService")
  IUserService userService;

  /**
   * 处理mobile用户登录请求的函数。
   * @param request
   * @return
   */
  @RequestMapping(value = "/login", method = RequestMethod.POST)
  @ResponseBody
  public Map<String, Object> mobileUserLogin(HttpServletRequest request) {
    String userNameOrEmail = request.getParameter(MobileConstantUtil.MOBILE_USER_LOGIN_NAME_OR_EMAIL);
    String userPwd = request.getParameter(MobileConstantUtil.MOBILE_USER_LOGIN_PASSWORD);
    Map<String, Object> loginResMap = userService.userLogin(userNameOrEmail, userPwd);
    return loginResMap;
  }
}
