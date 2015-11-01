<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="springui" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<c:url var="saveUrl" value="/user/save/${userAttribute.id}" />
	<springui:form modelAttribute="userAttribute" action="${saveUrl }" >
		<table>
			<tr>
				<td>ID:</td>
				<td><springui:input path="id" readonly="true"/></td>
			</tr>
			<tr>
				<td>Name:</td>
				<td><springui:input path="userName"/></td>
			</tr>
			<tr>
				<td>Age:</td>
				<td><springui:input path="age"/></td>
			</tr>
		</table>
		<input type="submit" value="save">
	</springui:form>
	
</body>
</html>