<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../../template/header.jsp"%>

<link href="styles//usertransaction.css" rel="stylesheet">

<div class="container">
  <div class="row">
    <div class="col-md-10 col-md-offset-1 col-sm-10 col-sm-offset-1 col-xs-10 col-xs-offset-1">
      <ul class="nav nav-pills nav-justified">
        <li class="active"><a href="#tab_a" data-toggle="pill">Pill A</a></li>
        <li><a href="#tab_b" data-toggle="pill">Pill B</a></li>
        <li><a href="#tab_c" data-toggle="pill">Pill C</a></li>
        <li><a href="#tab_d" data-toggle="pill">Pill D</a></li>
      </ul>
      <div class="tab-content">
        <div id="tab_a" class="tab-pane fade in active">
          <h4>Pane A</h4>
          <p>Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.</p>
        </div>
        <div id="tab_b" class="tab-pane fade">
          <h4>Pane B</h4>
          <p>Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.</p>
        </div>
        <div id="tab_c" class="tab-pane fade">
          <h4>Pane C</h4>
          <p>Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.</p>
        </div>
        <div id="tab_d" class="tab-pane fade">
          <h4>Pane D</h4>
          <p>Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.</p>
        </div>
      </div>
    </div>
  </div>
</div>

<%@ include file="../../../template/footer.jsp"%>