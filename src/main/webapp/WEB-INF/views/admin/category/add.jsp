<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8" isELIgnored="false"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

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
  <form:form modelAttribute="category"
    action="admin/category/add?pageNo=${pageNo}" method="post">
    <form:label path="parentCategory.id">请选择所在的父类别：</form:label>
    <form:select path="parentCategory.id">
      <c:choose>
        <c:when
          test="${parentCategoryList == null || fn:length(parentCategoryList) == 0 }">
          <form:option value="-1" label="暂无父类别可选择"></form:option>
        </c:when>
        <c:otherwise>
          <form:option value="-1" label="请选择父类别："></form:option>
        </c:otherwise>
      </c:choose>
      <c:forEach var="parentCategory" items="${parentCategoryList }">
        <form:option value="${parentCategory.id }"
          label="${parentCategory.name }"></form:option>
      </c:forEach>
    </form:select>
    <br />
    <form:label path="name">商品类别名称：</form:label>
    <form:input path="name" />
    <form:errors path="name" />
    <br />
    <form:label path="cdesc">商品类别描述：</form:label>
    <form:textarea path="cdesc" />
    <form:errors path="cdesc" />
    <br />
    <input type="submit" value="创建新类别" />
  </form:form>
</body>
</html>