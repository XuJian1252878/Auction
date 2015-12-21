<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../../template/header.jsp"%>


<!-- Bootstrap DateTimePicker -->
<script src="template/bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js"></script>
<link rel="stylesheet" type="text/css" href="template/bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.min.css"/>

<link rel="stylesheet" type="text/css" href="template/imgareaselect/css/imgareaselect-default.css" />
<script type="text/javascript" src="template/imgareaselect/scripts/jquery.imgareaselect.pack.js"></script>
<script type="text/javascript" src="scripts/cutimg.js"></script>
<script type="text/javascript" src="scripts/bootstrap.file-input.js"></script>

<script type="text/javascript">
  $(document).ready(function() {
    $('input[type=file]').bootstrapFileInput();
  });
  function displayUploadImgDiv() {
    var productImgDiv = document.getElementById("productImgDiv");
    if (productImgDiv.style.display == 'none') {
      productImgDiv.style.display = 'block';
    }
  }
</script>

<form:form modelAttribute="product" action="product/upload" enctype="multipart/form-data" method="post" >
  <div class="form-group">
  <form:label path="category.id">请选择商品类别：</form:label> <br />
  <form:select path="category.id">
    <c:choose>
      <c:when test="${categories == null || fn:length(categories) == 0 }">
        <form:option value="" label="暂无商品类别可选"></form:option> <br />
      </c:when>
      <c:otherwise>
        <form:option value="" label="请选择商品类别："></form:option> <br />
        <c:forEach var="mycategory" items="${categories }">
          <form:option value="${mycategory.id }" label="${mycategory.name }"></form:option>
        </c:forEach>
      </c:otherwise>
    </c:choose>
  </form:select>
  </div>
  <br />
  <form:input path="user.id" type="hidden" value="${loginUser.id }" />
  <div class="form-group">
  <form:label path="name" >商品名称：</form:label>
  <form:input path="name"/>
  <form:errors path="name" />
  </div>
  <br />
  <br />
  <div class="form-group">
  <form:label path="describe">商品描述信息：</form:label>
  <form:input path="describe"/>
  <form:errors path="describe" />
  </div>
  <br />
  <br />
  <div class="form-group">
  <form:label path="basicPrice">拍卖基价：</form:label>
  <form:input path="basicPrice"/>
  <form:errors path="basicPrice"/>
  </div>
  <br />
  <br />
  <label>竞拍结束时间：</label>
  <input id="endTimeMillis" name="endTimeMillis" type="hidden" />
  <form:errors path="endDate" />
  <div style="overflow: hidden;">
    <div class="form-group">
      <div class="row">
        <div class="col-md-8">
          <div id="datetimepicker"></div>
        </div>
      </div>
    </div>
    <script type="text/javascript">
      $(function() {
        $('#datetimepicker').datetimepicker({
          inline : true,
          sideBySide : true
        });
      });
      $("#datetimepicker").on("dp.change", function(e) {
        if (e.date == null || e.date == "") {
          $("#endTimeMillis").val(e.oldDate.valueOf());
        } else {
          $("#endTimeMillis").val(e.date.valueOf());
        }
      });
    </script>
  </div>
  <br />
  <br />
  <div class="form-group">
    <form:input id="img" type="file" path="imgFile" class="btn-info" title="请选择类别图片" onclick="displayUploadImgDiv()" />
    <form:errors path="imgFile"/>
  </div>
  <br />
  <br />
  <div id="productImgDiv" style="display: none;">
    <div class="container">
      <div class="row">
        <div id="uploadImgDiv" class="col-md-6">
          <img id="uploadImg" src="#" alt="测试预览图片"> <br /> <br />
          <!-- 加上type="button" ，可以防止点击button的时候自动提交。 -->
          <button id="cutImgBtn" class="btn-info btn-sm" style="display: none;" value="裁剪" onclick="cutImg()"
            type="button">裁剪</button>
          <input type="hidden" name="x1" value="-1"> <input type="hidden" name="y1" value="-1"> <input
            type="hidden" name="x2" value="-1"> <input type="hidden" name="y2" value="-1"> <input
            type="hidden" name="imgWidth" value="-1"> <input type="hidden" name="imgHeight" value="-1">
        </div>
        <label for="previewImgDiv">图片预览</label>
        <div id="previewImgDiv" class="col-md-6">
          <img id="previewImg" src="#" style="position: relative;" />
        </div>
      </div>
    </div>
  </div>
  <input type="submit" class="btn-success btn-lg" value="上传新商品" />
</form:form>

<%@ include file="../../../template/footer.jsp"%>