<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../../template/header.jsp"%>

<link href="styles//usertransaction.css" rel="stylesheet">

<div class="container">
  <div class="col-lg-12 col-lg-offset-1">
    <div class="title">
      <h2>
        <span>我的交易记录</span>
      </h2>
    </div>
  </div>
  <br /> <br /> <br />
  <div class="row">
    <div class="col-md-10 col-md-offset-1 col-sm-10 col-sm-offset-1 col-xs-10 col-xs-offset-1">
      <ul class="nav nav-pills nav-justified">
        <li class="active"><a href="#tab_a" data-toggle="pill">正在进行</a></li>
        <li><a href="#tab_b" data-toggle="pill">已经完成</a></li>
        <li><a href="#tab_c" data-toggle="pill">竞价记录</a></li>
      </ul>
      <div class="tab-content">
        <div id="tab_a" class="tab-pane fade in active">
          <h4>正在进行的竞价信息</h4>
          <p>Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.</p>
          <c:choose>
            <c:when test="${goingOnBids == null || fn:length(goingOnBids) == 0 }">
              <label>当前不存在正在竞价的商品！</label>
            </c:when>
            <c:otherwise>
              <table>
                <tr>
                  <th>商品名称：</th>
                  <th>商品描述：</th>
                  <th>竞拍起价：</th>
                  <th>我的竞拍价：</th>
                  <th>竞拍日期：</th>
                  <th>成交与否：</th>
                  <th>商品图片：</th>
                </tr>
                <c:forEach var="goingOnBid" items="${goingOnBids }">
                  <tr>
                    <td>${goingOnBid.product.name }</td>
                    <td>${goingOnBid.product.name }</td>
                    <td>${goingOnBid.product.basicPrice }</td>
                    <td>${goingOnBid.price }</td>
                    <td>${goingOnBid.bidDate }</td>
                    <td>
                      <c:choose>
                        <c:when test="${goingOnBid.isSuccess eq false }">未成交</c:when>
                        <c:otherwise>成交</c:otherwise>
                      </c:choose>
                    </td>
                    <td><img src="${goingOnBid.product.imgPath }" alt="${goingOnBid.product.name }"
                      title="${goingOnBid.product.name }" class="img-circle" /></td>
                  </tr>
                </c:forEach>
              </table>
            </c:otherwise>
          </c:choose>
        </div>
        <div id="tab_b" class="tab-pane fade">
          <h4>已经完成的竞价信息</h4>
          <p>Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.</p>
          <c:choose>
            <c:when test="${historyBids == null || fn:length(historyBids) == 0}">
              <label>当前没有已经完成的竞价记录。</label>
            </c:when>
            <c:otherwise>
              <table>
                <tr>
                  <th>商品名称：</th>
                  <th>商品描述：</th>
                  <th>竞拍起价：</th>
                  <th>我的竞拍价：</th>
                  <th>竞拍日期：</th>
                  <th>成交与否：</th>
                  <th>商品图片：</th>
                </tr>
                <c:forEach var="historyBid" items="${historyBids }">
                  <tr>
                    <td>${historyBid.product.name }</td>
                    <td>${historyBid.product.name }</td>
                    <td>${historyBid.product.basicPrice }</td>
                    <td>${historyBid.price }</td>
                    <td>${historyBid.bidDate }</td>
                    <td>
                      <c:choose>
                        <c:when test="${historyBid.isSuccess eq false }">未成交</c:when>
                        <c:otherwise>已成交</c:otherwise>
                      </c:choose>
                    </td>
                    <td><img src="${historyBid.product.imgPath }" alt="${historyBid.product.name }"
                      title="${historyBid.product.name }" class="img-circle" /></td>
                  </tr>
                </c:forEach>
              </table>
            </c:otherwise>
          </c:choose>
        </div>
        <div id="tab_c" class="tab-pane fade">
          <h4>已成交的竞价信息</h4>
          <p>Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.</p>
          <c:choose>
            <c:when test="${dealBids == null || fn:length(dealBids) == 0 }">
              <label>当前尚未有成交的竞价记录。</label>
            </c:when>
            <c:otherwise>
              <table>
                <tr>
                  <th>商品名称：</th>
                  <th>商品描述：</th>
                  <th>竞拍起价：</th>
                  <th>我的竞拍价：</th>
                  <th>竞拍日期：</th>
                  <th>成交日期：</th>
                  <th>商品图片：</th>
                </tr>
                <c:forEach var="dealBid" items="${dealBids }">
                  <tr>
                    <td>${dealBid.product.name }</td>
                    <td>${dealBid.product.name }</td>
                    <td>${dealBid.product.basicPrice }</td>
                    <td>${dealBid.price }</td>
                    <td>${dealBid.bidDate }</td>
                    <td>${dealBid.dealDate }</td>
                    <td><img src="${dealBid.product.imgPath }" alt="${dealBid.product.name }"
                      title="${dealBid.product.name }" class="img-circle" /></td>
                  </tr>
                </c:forEach>
              </table>
            </c:otherwise>
          </c:choose>
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