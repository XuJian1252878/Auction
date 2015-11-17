<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8" isELIgnored="false"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户登录</title>
</head>
<body>
  <p>
    还没有账号？请先<a href="user/register">注册</a>！
  </p>
  <form:form action="login" modelAttribute="loginUser" method="post">
    <form:label for="userName" path="userName">用户名称/邮箱：</form:label>
    <form:input path="userName" type="text" />
    <form:errors path="userName" />
    <br />
    <form:label for="password" path="password">登陆密码：</form:label>
    <form:input path="password" type="password" />
    <form:errors path="password" />
    <br />
    <input type="submit" value="用户登录" />
  </form:form>
</body>
</html>