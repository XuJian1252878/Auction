<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../../template/header.jsp"%>

<link rel="stylesheet" href="styles/loading.css">
<script type="text/javascript" src="template/jquery.countdown/dist/jquery.countdown.min.js"></script>

<c:choose>
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

    <!-- Button trigger modal -->
    <button type="button" class="btn btn-danger btn-lg" data-toggle="modal" data-target="#myModal">我要竞价</button>
    
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
            <div class="row">
              <div class="col-md-12">
                <div class="input-group input-group-lg">
                  <span class="input-group-addon" id="sizing-addon1">我的竞拍价：</span>
                  <input type="text" class="form-control" placeholder="请输入您的竞拍价" aria-describedby="sizing-addon1">
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-md-4">.col-md-4</div>
              <div class="col-md-4 col-md-offset-4">.col-md-4 .col-md-offset-4</div>
            </div>
            <div class="row">
              <div class="col-md-3 col-md-offset-3">.col-md-3 .col-md-offset-3</div>
              <div class="col-md-2 col-md-offset-4">.col-md-2 .col-md-offset-4</div>
            </div>
            <div class="row">
              <div class="col-md-6 col-md-offset-3">.col-md-6 .col-md-offset-3</div>
            </div>
            <div class="row">
              <div class="col-sm-9">
                Level 1: .col-sm-9
                <div class="row">
                  <div class="col-xs-8 col-sm-6">
                    Level 2: .col-xs-8 .col-sm-6
                  </div>
                  <div class="col-xs-4 col-sm-6">
                    Level 2: .col-xs-4 .col-sm-6
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消竞价</button>
            <button type="button" class="btn btn-primary" >提交竞价</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
  </c:otherwise>
</c:choose>

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