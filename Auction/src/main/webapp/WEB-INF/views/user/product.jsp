<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../../template/header.jsp"%>

<link href="styles/fade-tab-nav.css" rel="stylesheet">
<link href="styles/collapsible.css" rel="stylesheet">

<div class="container">
  <div class="row">
    <div class="col-lg-12">
      <div class="title">
        <h2>
          <span>我上传的竞价商品</span>
        </h2>
      </div>
    </div>
  </div>
  <br />
  <div class="row">
    <div class="col-md-10 col-md-offset-1 col-sm-10 col-sm-offset-1 col-xs-10 col-xs-offset-1">
      <ul class="nav nav-pills nav-justified">
        <li class="active"><a href="#tab_a" data-toggle="pill">正在竞价</a></li>
        <li><a href="#tab_b" data-toggle="pill">竞价完成</a></li>
      </ul>
      <div class="tab-content">
        <div id="tab_a" class="tab-pane fade in active">
          <br />
          <c:choose>
            <c:when test="${goingOnProducts == null || fn:length(goingOnProducts) == 0}">
              <label>您没有上传任何商品。</label>
            </c:when>
            <c:otherwise>
              <div class="row">
                <div class="col-lg-1 col-lg-offset-1">名称</div>
                <div class="col-lg-1">底价</div>
                <div class="col-lg-2">期限</div>
                <div class="col-lg-1">热度</div>
                <div class="col-lg-2">图片</div>
                <div class="col-lg-2">操作</div>
                <div class="col-lg-1">详情</div>
              </div>
              <br />
              <div id="accordion" role="tablist" aria-multiselectable="true">
                <c:forEach var="goingOnProduct" items="${goingOnProducts }">
                  <div class="row">
                    <div class="col-lg-1 col-lg-offset-1">${goingOnProduct.name }</div>
                    <div class="col-lg-1">${goingOnProduct.basicPrice }</div>
                    <div class="col-lg-2">${goingOnProduct.endDate }</div>
                    <div class="col-lg-1">${goingOnProduct.bids.size() }</div>
                    <div class="col-lg-2">
                      <img src="${goingOnProduct.imgPath }" alt="${goingOnProduct.name }"
                        title="${goingOnProduct.name }" width="100" height="100" class="img-circle" />
                    </div>
                    <div class="col-lg-2">
                      <a href="product/detail/${goingOnProduct.id }">查看商品详情</a>
                    </div>
                    <div class="panel panel-default">
                      <div class="panel-heading col-lg-1" role="tab" id="heading${goingOnProduct.id }">
                        <h4 class="panel-title">
                          <a data-toggle="collapse" data-parent="#accordion" href="#collapse${goingOnProduct.id }"
                            aria-expanded="false" aria-controls="collapse${goingOnProduct.id }" >竞价详情</a>
                        </h4>
                      </div>
                      <div id="collapse${goingOnProduct.id }" class="panel-collapse collapse col-lg-10 col-lg-offset-1"
                        role="tabpanel" aria-labelledby="heading${goingOnProduct.id }">
                        <c:choose>
                          <c:when test="${goingOnProduct.bids == null || fn:length(goingOnProduct.bids) == 0}">
                            <div class="row">
                              <div class="col-lg-4 col-lg-offset-4">
                                <label>暂时没有该商品的竞价记录！</label>
                              </div>
                            </div>
                          </c:when>
                          <c:otherwise>
                            <div class="row">
                              <div class="col-lg-4 col-lg-offset-4">
                                <label>共有${fn:length(goingOnProduct.bids) }条竞价记录。</label>
                              </div>
                            </div>
                            <c:forEach var="bid" items="${goingOnProduct.bids }">
                              <div class="row">
                                <div class="col-lg-2 col-lg-offset-1">${bid.user.userName }</div>
                                <div class="col-lg-3">${bid.bidDate }</div>
                                <div class="col-lg-3">${bid.price }</div>
                                <div class="col-lg-2">
                                  <form:form action="bid/deal" modelAttribute="bid" method="post">
                                    <form:input path="id" value="${bid.id }" type="hidden"/>
                                    <form:input path="product.id" value="${bid.product.id }" type="hidden"/>
                                    <form:input path="user.id" value="${bid.user.id }" type="hidden"/>
                                    <form:input path="price" value="${bid.price }" type="hidden"/>
                                    <form:input path="bidDate" value="${bid.bidDate }" type="hidden"/>
                                    <form:input path="isSuccess" value="${bid.isSuccess }" type="hidden"/>
                                    <form:input path="dealDate" value="${bid.dealDate }" type="hidden"/>
                                    <input type="submit" value="成交" class="btn btn-sm btn-success"/>
                                  </form:form>
                                </div>
                              </div>
                            </c:forEach>
                          </c:otherwise>
                        </c:choose>
                      </div>
                    </div>
                  </div>
                </c:forEach>
              </div>
            </c:otherwise>
          </c:choose>
        </div>
        <div id="tab_b" class="tab-pane fade">
          <br />
          <c:choose>
            <c:when test="${historyProductsMap == null || fn:length(historyProductsMap) == 0}">
              <label>您还没有任何历史纪录！</label>
            </c:when>
            <c:otherwise>
              <div class="row">
                <div class="col-lg-1 col-lg-offset-1">名称</div>
                <div class="col-lg-2">描述</div>
                <div class="col-lg-1">底价</div>
                <div class="col-lg-2">期限</div>
                <div class="col-lg-1">热度</div>
                <div class="col-lg-1">成交价</div>
                <div class="col-lg-2">图片</div>
              </div>
              <hr />
              <c:forEach var="historyProductPair" items="${historyProductsMap }">
                <div class="row">
                  <div class="col-lg-1 col-lg-offset-1">${historyProductPair.key.name }</div>
                  <div class="col-lg-2">${historyProductPair.key.describe }</div>
                  <div class="col-lg-1">${historyProductPair.key.basicPrice }</div>
                  <div class="col-lg-2">${historyProductPair.key.endDate }</div>
                  <div class="col-lg-1">${historyProductPair.key.bids.size() }</div>
                  <div class="col-lg-1">
                    <c:choose>
                      <c:when test="${historyProductPair.value == null }">商品竞拍失败</c:when>
                      <c:otherwise>${historyProductPair.value.price }</c:otherwise>
                    </c:choose>
                  </div>
                  <div class="col-lg-2">
                    <img src="${historyProductPair.key.imgPath }" alt="${historyProductPair.key.name }" title="${historyProductPair.key.name }"
                      width="100" height="100" class="img-circle" />
                  </div>
                </div>
                <hr />
              </c:forEach>
            </c:otherwise>
          </c:choose>
        </div>
      </div>
    </div>
  </div>
</div>

<div class="contanner">
  <div class="row">
    <div class="col-md-10 col-md-offset-5 col-sm-10 col-sm-offset-5 col-xs-10 col-xs-offset-5">
      <a class="btn-success btn-lg" href="product/upload" role="button">上传我的旧物</a>
    </div>
  </div>
</div>
<%@ include file="../../../template/footer.jsp"%>