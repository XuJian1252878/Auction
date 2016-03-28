<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../../template/header.jsp"%>

<link href="styles/fade-tab-nav.css" rel="stylesheet">

<div class="container">
  <div class="row">
    <div class="col-lg-10 col-lg-offset-1">
      <div class="title">
        <h2>
          <span>我上传的物品</span>
        </h2>
      </div>
    </div>
  </div>
  <div class="row">
    <div class="col-md-10 col-md-offset-1 col-sm-10 col-sm-offset-1 col-xs-10 col-xs-offset-1">
      <ul class="nav nav-pills nav-justified">
        <li class="active"><a href="#tab_a" data-toggle="pill">正在进行</a></li>
        <li><a href="#tab_b" data-toggle="pill">已经完成</a></li>
      </ul>
      <div class="tab-content">
        <div id="tab_a" class="tab-pane fade in active">
          <h4>我的正在竞价商品</h4>
          <p>Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.</p>
          <c:choose>
            <c:when test="${goingOnProducts == null || fn:length(goingOnProducts) == 0}">
              <label>您没有上传任何商品。</label>
            </c:when>
            <c:otherwise>
              <table>
                <tr>
                  <th>商品名称：</th>
                  <th>商品描述：</th>
                  <th>竞价起价：</th>
                  <th>上架时间：</th>
                  <th>竞价热度：</th>
                  <th>更多操作：</th>
                  <th>商品图片：</th>
                </tr>
              </table>
            </c:otherwise>
          </c:choose>
        </div>
        <div id="tab_b" class="tab-pane fade">
          <h4>我的已完成竞价商品</h4>
          <p>Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.</p>
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