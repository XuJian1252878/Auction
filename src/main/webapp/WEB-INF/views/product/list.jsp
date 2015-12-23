<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../../template/header.jsp"%>

<c:choose>
  <c:when test="${products == null || fn:length(products) == 0 }">
    <label>商品类别 ${category.name } 下暂无竞价商品信息</label>
  </c:when>
  <c:otherwise>
    <table>
      <c:forEach var="product" items="${products }">
        <tr>
          <td>${product.id }</td>
          <td>${product.name }</td>
          <td>${product.describe }</td>
          <td>${product.onSaleDate }</td>
          <td>${product.endDate }</td>
          <td><img src="${product.imgPath }" alt="商品图片"></td>
        </tr>
        <tr>
          <td>${product.user.userName }</td>
        </tr>
      </c:forEach>
    </table>
  </c:otherwise>
</c:choose>

<%@ include file="../../../template/footer.jsp"%>