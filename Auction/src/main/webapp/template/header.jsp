<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>

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
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<base href="<%=basePath%>">
<link rel="icon" href="images/icon/favicon.ico">

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="template/jquery/jquery-1.11.3.min.js"></script>

<!-- DateTimePicker plugin -->
<script src="template/moment/moment.js"></script>

<!-- Bootstrap plugins -->
<script src="template/bootstrap-3.3.6/js/transition.js"></script>
<script src="template/bootstrap-3.3.6/js/collapse.js"></script>

<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="template/bootstrap-3.3.6/js/bootstrap.min.js"></script>
<!-- Bootstrap core CSS -->
<link href="template/bootstrap-3.3.6/css/bootstrap.min.css" rel="stylesheet">

<!-- font-awesome 各种图标 -->
<link rel="stylesheet" href="template/font-awesome-4.5.0/css/font-awesome.min.css">



<%-- 
<!--Import materialize.css must after bootstrap-->
<link type="text/css" rel="stylesheet"
  href="template/materialize/css/materialize.min.css"
  media="screen,projection" />
<!--Let browser know website is optimized for mobile-->
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
--%>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${PAGETITLE}</title>
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

<link href="styles/sitenavbar.css" rel="stylesheet">
</head>
<body>
  <!-- 网页导航栏 -->
  <nav class="navbar navbar-inverse navbar-fixed-top">
  <div class="container">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
        aria-expanded="false" aria-controls="navbar">
        <span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span
          class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="#">Project name</a>
    </div>
    <div id="navbar" class="navbar-collapse collapse navbar-right">
      <ul class="nav navbar-nav">
        <li class="active"><a href="index#home">主页</a></li>
        <li><a href="index#home">关于</a></li>
        <li><a href="product/search">搜索商品</a></li>
        <c:choose>
          <c:when test="${sessionScope.loginuser != null }">
            <li><img src="images/avatar/001.jpg" wigth="50px" height="50px"
              alt="${sessionScope.loginuser.userName }" title="${sessionScope.loginuser.userName }" class="img-circle"></li>
            <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
              aria-haspopup="true" aria-expanded="false">信息中心 <span class="caret"></span>
            </a>
              <ul class="dropdown-menu">
                <li><a href="user/profile"><i class="glyphicon glyphicon-cog"></i> 个人信息</a></li>
                <li><a href="message/list"><i class="glyphicon glyphicon-circle-arrow-down"></i>我的消息 <span class="badge">${unreadMessageCount }</span></a></li>
                <li><a href="#">Something else here</a></li>
                <li role="separator" class="divider"></li>
                <li class="dropdown-header">Nav header</li>
                <li><a href="user/products"><i class="glyphicon glyphicon-shopping-cart"></i> 我的商品</a></li>
                <li><a href="user/transaction"><i class="fa fa-credit-card-alt"></i> 我的竞价记录</a></li>
                <li role="seperator" class="divider"></li>
                <li><a href="user/logout"><i class="glyphicon glyphicon-off"></i> 注销</a></li>
              </ul></li>
          </c:when>
          <c:otherwise>
            <li><a href="user/login" class="btn btn-link btn-sm active">登陆</a></li>
            <li><a href="user/register" class="btn btn-link btn-sm active">注册</a></li>
          </c:otherwise>
        </c:choose>
      </ul>
    </div>
  </div>
  </nav>