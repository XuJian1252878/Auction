<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../../template/header.jsp"%>

<link href="styles//usertransaction.css" rel="stylesheet">

<div class="container">
  <p>
    <label>我的交易记录</label>
  </p>
  <br /> <br />
  <div class="row">
    <div class="col-md-10 col-md-offset-1 col-sm-10 col-sm-offset-1 col-xs-10 col-xs-offset-1">
      <ul class="nav nav-pills nav-justified">
        <li class="active"><a href="#tab_a" data-toggle="pill">竞价记录</a></li>
        <li><a href="#tab_b" data-toggle="pill">正在进行</a></li>
        <li><a href="#tab_c" data-toggle="pill">已经完成</a></li>
      </ul>
      <div class="tab-content">
        <div id="tab_a" class="tab-pane fade in active">
          <h4>Pane A</h4>
          <p>Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.</p>
          <c:choose>
            <c:when test="${products == null || fn:length(products) == 0 }">
              <label>当前不存在竞价记录</label>
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
                <tr>
                  <td>${product.category.name }</td>
                  <td>${product.category.cdesc }</td>
                </tr>
              </c:forEach>
              </table>
            </c:otherwise>
          </c:choose>
        </div>
        <div id="tab_b" class="tab-pane fade">
          <h4>Pane B</h4>
          <p>Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.</p>
        </div>
        <div id="tab_c" class="tab-pane fade">
          <h4>Pane C</h4>
          <p>Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.</p>
        </div>
      </div>
    </div>
  </div>
</div>
<br />
<br />
<br />
<div class="contanner">
  <div class="row">
    <div class="col-md-10 col-md-offset-5 col-sm-10 col-sm-offset-5 col-xs-10 col-xs-offset-5">
      <a class="btn-success btn-lg" href="product/upload" role="button">上传我的旧物信息</a>
    </div>
  </div>
</div>

<%@ include file="../../../template/footer.jsp"%>