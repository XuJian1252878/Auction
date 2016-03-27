<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../../../template/header.jsp"%>

  <form:form modelAttribute="category"
    action="admin/category/edit?pageNo=${pageNo }" method="post">
    <form:label path="parentCategory.name">父类别名称：</form:label>
    <form:label path="parentCategory.name">${category.parentCategory.name }</form:label>
    <br />
    <form:label path="id" for="id">id:</form:label>
    <form:input path="id" value="${category.id }" readonly="true" />
    <br />
    <!-- 0 表示为查看，1表示为编辑 -->
    <c:choose>
      <c:when test="${viewOrEdit == 1 }">
        <form:label path="name" for="name">商品类别名称：</form:label>
        <form:input path="name" value="${category.name }" />
        <br />
        <form:label path="cdesc" for="cdesc">商品类别描述信息：</form:label>
        <form:textarea path="cdesc" value="${category.cdesc }" />
        <br />
        <input type="submit" value="更新商品信息" />
      </c:when>
      <c:when test="${viewOrEdit == 0 }">
        <form:label path="name" for="name">商品类别名称：</form:label>
        <form:input path="name" value="${category.name }"
          readonly="true" />
        <br />
        <form:label path="cdesc" for="cdesc">商品类别描述信息：</form:label>
        <form:textarea path="cdesc" value="${category.cdesc }"
          readonly="true" />
        <br />
        <a href="admin/category/list/${pageNo }">返回列表</a>
      </c:when>
    </c:choose>
    <br />
    <form:label path="categories" for="categories">子类别信息：</form:label>
    <br />
    <c:if
      test="${category.categories == null || fn:length(category.categories) == 0 }">
      <label>类别：${category.name } 无子类别</label>
      <br />
    </c:if>
    <c:forEach var="childCategory" items="${category.categories }">
      <label for="childCategory.id">id：</label>
      <label id="childCategory.id">${childCategory.id }</label>
      <br />
      <label for="childCategory.name">商品类别名称：</label>
      <label id="childCategory.name">${childCategory.name }</label>
      <br />
      <label for="childCategory.cdesc">商品描述信息：</label>
      <label id="childCategory.cdesc">${childCategory.cdesc }</label>
      <br />
    </c:forEach>
  </form:form>

<%@ include file="../../../../template/footer.jsp"%>