<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8" isELIgnored="false"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户注册</title>
</head>
<body>
  <!-- 用户注册表单 -->
  <form:form modelAttribute="registerUser" action="register"
    method="post">
    <form:label for="userName" path="userName">注册用户名：</form:label>
    <form:input path="userName" type="text" />
    <form:errors path="userName" />
    <br />
    <form:label for="email" path="email">注册邮箱:</form:label>
    <form:input path="email" type="text" />
    <form:errors path="email" />
    <br />
    <form:label for="password" path="password">密码：</form:label>
    <form:input path="password" type="password" />
    <form:errors path="password" />
    <br />
    <form:label for="confirmPassword" path="confirmPassword">确认密码：</form:label>
    <form:input path="confirmPassword" type="password" />
    <form:errors path="confirmPassword" />
    <br />
    <form:label for="age" path="age">年龄：</form:label>
    <form:input path="age" type="text" />
    <form:errors path="age" />
    <br />
    <form:label for="address" path="address">用户地址：</form:label>
    <form:input path="address" type="text" />
    <form:errors path="address" />
    <br />
    <input type="submit" value="注册新用户" />
  </form:form>
</body>
</html>