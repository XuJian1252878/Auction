<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../../template/header.jsp"%>

<link rel="stylesheet" href="styles/loading.css">
<script type="text/javascript" src="template/jquery.countdown/dist/jquery.countdown.min.js"></script>

<c:choose>
  <%-- 判断商品是否存在信息 --%>
  <c:when test="${product == null}">
    <h3><span>商品信息不存在</span></h3>
    <br />
  </c:when>
  <c:otherwise>
    <label>商品名称：</label><span>${product.name }</span><br />
    <label>商品类别：</label><span>${product.category.name }</span><br />
    <label>商品描述：</label><span>${product.describe }</span><br />
    <label>商品上传时间：</label><span>${product.onSaleDate }</span><br />
    <label>结束竞拍时间：</label><span>${product.endDate }</span><br />
    <label>竞拍倒计时：</label><span id="expireClockId"></span><br />
    <script type="text/javascript">
      $('#expireClockId').countdown("${product.endDate}", function(event) {
        $(this).html(event.strftime('%D days %H:%M:%S'));
      }).on('finish.countdown', function(event) {
        $(this).html('该商品已停止竞拍！');
      })
    </script>
    <label>竞拍起价：</label><span>${product.basicPrice }</span><br />
    <img src="${product.imgPath }" width="300" height="300"/><br /><br />

    <%-- 商品竞价部分 --%>
    <c:choose>
      <c:when test="${sessionScope.loginuser != null}">
        <!-- 只有上传该商品的用户才能看到所有的竞价信息。 -->
        <c:if test="${sessionScope.loginuser.id == product.user.id}">
          <c:choose>
            <c:when test="${productBids == null || fn:length(productBids) == 0}">
              <h4>当前还未有人对该商品进行竞价！</h4>
            </c:when>
            <c:otherwise>
              <c:forEach var="bid" items="${productBids }">
                <h4>用户名称：${bid.user.userName }，出价：${bid.price }，出价时间：${bid.bidDate }</h4>
              </c:forEach>
            </c:otherwise>
          </c:choose>
        </c:if>
        <!-- 只有登陆用户（不包括该商品的上传用户）才能参与竞价。 -->
        <c:if test="${sessionScope.loginuser.id != product.user.id}">
          <!-- Button trigger modal -->
          <button type="button" class="btn btn-danger btn-lg" data-toggle="modal" data-target="#myModal">我要竞价</button>
          <form:form action="bid/commit_${product.id }" modelAttribute="userbid" method="post">
            <form:input type="hidden" path="product.id" value="${product.id }"/>
            <form:input type="hidden" path="user.id" value="${sessionScope.loginuser.id }"/>
            <div id="myModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="gridSystemModalLabel">
              <div class="modal-dialog" role="document">
                <div class="modal-content">
                  <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="gridSystemModalLabel">竞价提示</h4>
                  </div>
                  <div class="modal-body">
                    <div class="row">
                      <div class="col-md-12">
                        <h4>该商品的竞拍起价为：<span class="label label-danger">${product.basicPrice }</span>，您的出价必须高于竞拍起价！</h4>
                      </div>
                    </div>
                    <c:if test="${oldBid != null}">
                      <div class="row">
                        <div class="col-md-12">
                          <h4>您之前已对该商品进行出价，您的出价为：<span class="label label-danger">${oldBid.price }</span>！</h4>
                        </div>
                      </div>
                    </c:if>
                    <div class="row">
                      <div class="col-md-12">
                        <div class="input-group input-group-lg">
                          <span class="input-group-addon" id="sizing-addon1">我的竞拍价：</span>
                          <form:input type="text" path="price" class="form-control" placeholder="请输入您的竞拍价" aria-describedby="sizing-addon1" />
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消竞价</button>
                    <button id="commitBidBtn" type="submit" class="btn btn-primary" >提交竞价</button>
                  </div>
                </div><!-- /.modal-content -->
              </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
          </form:form>
        </c:if>
      </c:when>
      <c:otherwise>
        <h4>想要参与竞价？赶快<a href="user/register"><span class="label label-success">注册</span></a>吧！</h4>
      </c:otherwise>
    </c:choose>

    <%-- 商品评价信息部分 --%>
    <h4>商品评价：</h4>
    <c:choose>
      <c:when test="${productComments == null || fn:length(productComments) == 0}">
        <label>暂无该商品的评价信息！</label>
      </c:when>
      <c:otherwise>
        <table>
          <tr>
            <th>评论者</th>
            <th>评论内容</th>
            <th>评论时间</th>
          </tr>
          <c:forEach var="productComment" items="${productComments }">
            <tr>
              <td>${productComment.user.userName }</td>
              <td>${productComment.commentText }</td>
              <td>${productComment.pubDate }</td>
            </tr>
          </c:forEach>
        </table>
      </c:otherwise>
    </c:choose>
  </c:otherwise>
</c:choose>


<button type="button" class="btn btn-danger btn-lg" data-toggle="modal" data-target="#pleaseWaitDialog">进度条测试</button>
<div class="modal fade" id="pleaseWaitDialog" data-backdrop="static" data-keyboard="false">
  <div class="modal-header">
    <h1>Processing...</h1>
  </div>
  <div class="modal-body">
    <div class="row">
      <div class="col-md-8 col-md-offset-2">
        <div class="progress progress-striped active">
          <div class="bar bar-success" style="width: 100%;"></div>
        </div>
      </div>
    </div>
  </div>
  <div class="modal-footer">
    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
    <button type="button" class="btn btn-primary" >Commit</button>
  </div>
</div>

<div id="contentDiv"><h2>我的测试文字</h2></div>
<button id="btn001" type="button">ChangeContent</button><br /><br />

<script type="text/javascript">
  $('#btn001').click(function(){
    htmlobj = $.ajax({url:"imgdatatxt/1", async: false});
    $('#contentDiv').html(htmlobj.responseText);
  })
</script>

<style type="text/css">
/*
    Original version: http://www.bootply.com/128062
    
    This version adds support for IE 10+ and Firefox.
*/

.glyphicon-refresh-animate {
    -animation: spin .7s infinite linear;
    -ms-animation: spin .7s infinite linear;
    -webkit-animation: spinw .7s infinite linear;
    -moz-animation: spinm .7s infinite linear;
}

@keyframes spin {
    from { transform: scale(1) rotate(0deg);}
    to { transform: scale(1) rotate(360deg);}
}
  
@-webkit-keyframes spinw {
    from { -webkit-transform: rotate(0deg);}
    to { -webkit-transform: rotate(360deg);}
}

@-moz-keyframes spinm {
    from { -moz-transform: rotate(0deg);}
    to { -moz-transform: rotate(360deg);}
}
</style>

<!-- Code snippet -->
<div class="form-group">
    <div class="col-md-12 text-center">
        <span class="glyphicon glyphicon-refresh glyphicon-refresh-animate"></span>
    </div>
</div>

<%@ include file="../../../template/footer.jsp"%>