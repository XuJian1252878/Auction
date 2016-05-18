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
        <li class="active"><a href="#tab_a" data-toggle="pill">正在竞价</a></li>
        <li><a href="#tab_b" data-toggle="pill">竞价时限已到</a></li>
        <li><a href="#tab_c" data-toggle="pill">成交记录</a></li>
      </ul>
      <div class="tab-content">
        <div id="tab_a" class="tab-pane fade in active">
          <br />
          <c:choose>
            <c:when test="${goingOnBids == null || fn:length(goingOnBids) == 0 }">
              <label>当前不存在正在竞价的商品！</label>
            </c:when>
            <c:otherwise>
              <div class="row">
                <div class="col-lg-1">名称：</div>
                <div class="col-lg-2">描述：</div>
                <div class="col-lg-1">起价：</div>
                <div class="col-lg-1">我的竞拍价：</div>
                <div class="col-lg-3">竞拍日期：</div>
                <div class="col-lg-1">期限：</div>
                <div class="col-lg-2">图片：</div>
                <div class="col-lg-1">操作：</div>
              </div>
              <c:forEach var="goingOnBid" items="${goingOnBids }">
                <div class="row">
                  <div class="col-lg-1">${goingOnBid.product.name }</div>
                  <div class="col-lg-2">${goingOnBid.product.describe }</div>
                  <div class="col-lg-1">${goingOnBid.product.basicPrice }</div>
                  <div class="col-lg-1">${goingOnBid.price }</div>
                  <div class="col-lg-3">${goingOnBid.bidDate }</div>
                  <div class="col-lg-1">${goingOnBid.product.endDate }</div>
                  <div class="col-lg-2">
                    <img src="${goingOnBid.product.imgPath }" alt="${goingOnBid.product.name }"
                      title="${goingOnBid.product.name }" class="img-circle" width="100" height="100" />
                  </div>
                  <div class="col-lg-1">
                    <button type="button" class="btn btn-primary" data-toggle="modal"
                      data-target="#myModal${goingOnBid.id }">调整出价</button>
                  </div>
                  <form action="bid/modifyprice" method="post" onsubmit="return checkModifyPrice();">
                    <input type="hidden" id="goingOnBidId" name="goingOnBidId" value="${goingOnBid.id }" />
                    <div id="myModal${goingOnBid.id }" class="modal fade" tabindex="-1" role="dialog"
                      aria-labelledby="gridSystemModalLabel">
                      <div class="modal-dialog" role="document">
                        <div class="modal-content">
                          <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                              <span aria-hidden="true">&times;</span>
                            </button>
                            <h4 class="modal-title" id="gridSystemModalLabel">竞价提示</h4>
                          </div>
                          <div class="modal-body">
                            <div class="row">
                              <div class="col-md-12">
                                <h4>
                                  该商品的竞拍起价为：<span class="label label-danger">${goingOnBid.product.basicPrice }</span>，您的出价必须高于竞拍起价！
                                </h4>
                              </div>
                              <div class="col-md-12">
                                <h4>
                                  您之前的竞拍价为：<span class="label label-danger">${goingOnBid.price }</span>！
                                </h4>
                              </div>
                              <div class="row">
                                <div class="col-md-12">
                                  <div class="input-group input-group-lg">
                                    <span class="input-group-addon" id="sizing-addon1">我的竞拍价：</span> <input
                                      id="goingOnBidPrice" name="goingOnBidPrice" type="text" class="form-control"
                                      placeholder="请输入您的竞拍价" aria-describedby="sizing-addon1" />
                                  </div>
                                </div>
                              </div>
                              <br />
                              <div class="row">
                                <div class="col-md-12">
                                  <span id="modifyBidPriceInfoSpan" class="label label-danger"></span>
                                </div>
                              </div>
                            </div>
                          </div>
                          <div class="modal-footer">
                            <button type="button" class="btn btn-success btn-lg" data-dismiss="modal" onClick="cancleModifyPrice()">取消修改</button>
                            <input type="submit" class="btn btn-success btn-lg" class="btn btn-primary" value="修改竞价" />
                            <script type="text/javascript">
                              function cancleModifyPrice() {
                                $("#modifyBidPriceInfoSpan").html("");
                              }
                              function checkModifyPrice() {
                                var newBidPrice = $("#goingOnBidPrice").val();  // 获得新填的竞价信息。
                                var basePrice = ${goingOnBid.product.basicPrice };  // 商品竞价底价。
                                if (newBidPrice <= basePrice) {
                                  $("#modifyBidPriceInfoSpan").html("您的竞拍价少于商品的竞拍底价，请调整出价！！");
                                  return false;
                                }
                                return true;
                              }
                            </script>
                          </div>
                        </div>
                        <!-- /.modal-content -->
                      </div>
                      <!-- /.modal-dialog -->
                    </div>
                    <!-- /.modal -->
                  </form>
                </div>
              </c:forEach>
            </c:otherwise>
          </c:choose>
        </div>
        <div id="tab_b" class="tab-pane fade">
          <br />
          <c:choose>
            <c:when test="${historyBids == null || fn:length(historyBids) == 0}">
              <label>当前没有已经完成的竞价记录。</label>
            </c:when>
            <c:otherwise>
              <div class="row">
                <div class="col-md-1">名称</div>
                <div class="col-md-3">描述</div>
                <div class="col-md-1">起价</div>
                <div class="col-md-1">我的竞拍价</div>
                <div class="col-md-3">日期</div>
                <div class="col-md-1">成交与否</div>
                <div class="col-md-2">图片</div>
              </div>
              <c:forEach var="historyBid" items="${historyBids }">
                <div class="row">
                  <div class="col-md-1">${historyBid.product.name }</div>
                  <div class="col-md-3">${historyBid.product.name }</div>
                  <div class="col-md-1">${historyBid.product.basicPrice }</div>
                  <div class="col-md-1">${historyBid.price }</div>
                  <div class="col-md-3">${historyBid.bidDate }</div>
                  <div class="col-md-1">
                    <c:choose>
                      <c:when test="${historyBid.isSuccess eq false }">竞价失败</c:when>
                      <c:otherwise>已成交，${historyBid.dealDate }</c:otherwise>
                    </c:choose>
                  </div>
                  <div class="col-md-2">
                    <img src="${historyBid.product.imgPath }" alt="${historyBid.product.name }"
                      title="${historyBid.product.name }" class="img-circle" width="100" height="100" />
                  </div>
                </div>
              </c:forEach>
            </c:otherwise>
          </c:choose>
        </div>
        <div id="tab_c" class="tab-pane fade">
          <br />
          <c:choose>
            <c:when test="${dealBids == null || fn:length(dealBids) == 0 }">
              <label>当前尚未有成交的竞价记录。</label>
            </c:when>
            <c:otherwise>
              <div class="row">
                <div class="col-md-1">名称</div>
                <div class="col-md-3">描述</div>
                <div class="col-md-1">起价</div>
                <div class="col-md-1">我的竞拍价</div>
                <div class="col-md-2">竞拍日期</div>
                <div class="col-md-2">成交日期</div>
                <div class="col-md-2">图片</div>
              </div>
              <c:forEach var="dealBid" items="${dealBids }">
                <div class="row">
                  <div class="col-md-1">${dealBid.product.name }</div>
                  <div class="col-md-3">${dealBid.product.name }</div>
                  <div class="col-md-1">${dealBid.product.basicPrice }</div>
                  <div class="col-md-1">${dealBid.price }</div>
                  <div class="col-md-2">${dealBid.bidDate }</div>
                  <div class="col-md-2">${dealBid.dealDate }</div>
                  <div class="col-md-2">
                    <img src="${dealBid.product.imgPath }" alt="${dealBid.product.name }"
                      title="${dealBid.product.name }" class="img-circle" width="100" height="100" />
                  </div>
                </div>
              </c:forEach>
            </c:otherwise>
          </c:choose>
        </div>
      </div>
    </div>
  </div>
</div>
<br />

<%@ include file="../../../template/footer.jsp"%>