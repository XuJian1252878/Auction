<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../../template/header.jsp"%>

<link href="styles/fade-tab-nav.css" rel="stylesheet">

<div class="container">
  <div class="col-lg-10 col-lg-offset-1">
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
        <li><a href="#tab_b" data-toggle="pill">竞价时限已到</a></li>
        <li><a href="#tab_c" data-toggle="pill">成交记录</a></li>
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
              <div class="row">
                <div class="col-lg-1">商品名称：</div>
                <div class="col-lg-2">商品描述：</div>
                <div class="col-lg-1">竞拍起价：</div>
                <div class="col-lg-1">我的竞拍价：</div>
                <div class="col-lg-3">竞拍日期：</div>
                <div class="col-lg-1">竞拍期限：</div>
                <div class="col-lg-2">商品图片：</div>
                <div class="col-lg-1">更多操作：</div>
              </div>
                <c:forEach var="goingOnBid" items="${goingOnBids }">
                  <div class="row">
                    <div class="col-lg-1">${goingOnBid.product.name }</div>
                    <div class="col-lg-2">${goingOnBid.product.describe }</div>
                    <div class="col-lg-1">${goingOnBid.product.basicPrice }</div>
                    <div class="col-lg-1">${goingOnBid.price }</div>
                    <div class="col-lg-3">${goingOnBid.bidDate }</div>
                    <div class="col-lg-1">${goingOnBid.product.endDate }</div>
                    <div class="col-lg-2"><img src="${goingOnBid.product.imgPath }" alt="${goingOnBid.product.name }"
                      title="${goingOnBid.product.name }" class="img-circle" width="100" height="100" /></div>
                    <div class="col-lg-1"><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal${goingOnBid.id }">调整出价</button></div>
                    <form action="bid/modifyprice"  method="post">
                      <input type="hidden" id="goingOnBidId" name="goingOnBidId" value="${goingOnBid.id }" />
                      <div id="myModal${goingOnBid.id }" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="gridSystemModalLabel">
                        <div class="modal-dialog" role="document">
                          <div class="modal-content">
                            <div class="modal-header">
                              <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                              <h4 class="modal-title" id="gridSystemModalLabel">竞价提示</h4>
                            </div>
                            <div class="modal-body">
                              <div class="row">
                                <div class="col-md-12">
                                  <h4>该商品的竞拍起价为：<span class="label label-danger">${goingOnBid.product.basicPrice }</span>，您的出价必须高于竞拍起价！</h4>
                                </div>
                                <div class="col-md-12">
                                  <h4>您之前的竞拍价为：<span class="label label-danger">${goingOnBid.price }</span>！</h4>
                                </div>
                                <div class="row">
                                  <div class="col-md-12">
                                    <div class="input-group input-group-lg">
                                      <span class="input-group-addon" id="sizing-addon1">我的竞拍价：</span>
                                      <input id="goingOnBidPrice" name="goingOnBidPrice" type="text" class="form-control" placeholder="请输入您的竞拍价" aria-describedby="sizing-addon1" />
                                    </div>
                                  </div>
                                </div>
                              </div>
                            </div>
                            <div class="modal-footer">
                              <button type="button" class="btn btn-success btn-lg" data-dismiss="modal">取消修改</button>
                              <input type="submit" class="btn btn-success btn-lg" class="btn btn-primary" value="修改竞价" />
                            </div>
                          </div><!-- /.modal-content -->
                        </div><!-- /.modal-dialog -->
                      </div><!-- /.modal -->
                    </form>
                  </div>
                </c:forEach>
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
                        <c:when test="${historyBid.isSuccess eq false }">竞价失败</c:when>
                        <c:otherwise>已成交，${historyBid.dealDate }</c:otherwise>
                      </c:choose>
                    </td>
                    <td><img src="${historyBid.product.imgPath }" alt="${historyBid.product.name }"
                      title="${historyBid.product.name }" class="img-circle" width="100" height="100" /></td>
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

<%@ include file="../../../template/footer.jsp"%>