<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
  String path = request.getContextPath();
  String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品类别管理</title>
</head>
<body>
  需要添加商品类别信息？点击<a href="admin/category/add?pageNo=${pageNo}">添加</a>商品类别<br/>
  <label>商品类别信息如下：</label><br/>
  <table>
    <tr>
      <td>商品类别id：</td>
      <td>商品类别名称:</td>
      <td>商品类别描述：</td>
    </tr>
    <c:forEach var="category" items="${categories}">
    <tr>
      <td>${category.id}</td>
      <td>${category.name}</td>
      <td>${category.cdesc}</td>
      <td><img src="${category.imgPath }" alt="商品类别图片" width="240" height="240"></td>
      <td><a href="admin/category/edit/0_${category.id }?pageNo=${pageNo}">查看详情</a></td>
      <td><a href="admin/category/edit/1_${category.id }?pageNo=${pageNo}">编辑</a></td>
      <td><a href="admin/category/delete_${category.id }?pageNo=${pageNo}">删除</a></td>
    </tr>
    </c:forEach>
  </table>
</body>
</html>