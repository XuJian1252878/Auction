<%@ page import="com.auction.model.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false" %>

<%
  String path = request.getContextPath();
  String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Auction首页</title>
</head>
<body>
  <!-- “/”代表的是 web 站点的根路径。因为超链接可以链接到任何需要的目标资源, 
  所以 / 代表的肯定不是当前 web 应用的根路径, 而是当前 web 站点的根路径。 -->
  <a href="user/register">注册</a><br/>
  <a href="user/login">登陆</a>
  <%
  User user = (User)session.getAttribute("LOGINUSER");
  if (user != null){
    %>
    登陆用户名：<%=user.getUserName() %><br/>
    登陆用户密码：<%=user.getPassword() %><br/>
    <%
  }
  %>
</body>
</html>