<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8" isELIgnored="false"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%
  String path = request.getContextPath();
  String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path
      + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加商品类别</title>
</head>
<body>
  <form:form modelAttribute="category" action="admin/category/add?pageNo=${categoryPageNo}"
    method="post">
    <form:label path="name">商品类别名称：</form:label>
    <form:input path="name" />
    <form:errors path="name" />
    <br />
    <form:label path="cdesc">商品类别描述：</form:label>
    <form:input path="cdesc" />
    <form:errors path="cdesc" />
    <br />
    <input type="submit" value="创建新类别" />
  </form:form>
</body>
</html>