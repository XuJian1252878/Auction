<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../../../template/header.jsp"%>

<link rel="stylesheet" type="text/css" href="template/imgareaselect/css/imgareaselect-default.css" />
<script type="text/javascript" src="template/imgareaselect/scripts/jquery.min.js"></script>
<script type="text/javascript" src="template/imgareaselect/scripts/jquery.imgareaselect.pack.js"></script>
<script type="text/javascript" src="scripts/cutimg.js"></script>
<script type="text/javascript" src="scripts/bootstrap.file-input.js"></script>
<script type="text/javascript">
  $(document).ready(function() {
    $('input[type=file]').bootstrapFileInput();
  });
  function displayUploadImgDiv() {
    var categoryImgDiv = document.getElementById("categoryImgDiv");
    if (categoryImgDiv.style.display == 'none') {
      categoryImgDiv.style.display = 'block';
    }
  }
</script>

<form:form modelAttribute="category" action="admin/category/add?pageNo=${pageNo}" enctype="multipart/form-data"
  method="post">
  <form:label path="parentCategory.id">请选择所在的父类别：</form:label>
  <form:select path="parentCategory.id">
    <c:choose>
      <c:when test="${parentCategoryList == null || fn:length(parentCategoryList) == 0 }">
        <form:option value="-1" label="暂无父类别可选择"></form:option>
      </c:when>
      <c:otherwise>
        <form:option value="-1" label="请选择父类别："></form:option>
        <c:forEach var="parentCategory" items="${parentCategoryList }">
          <form:option value="${parentCategory.id }" label="${parentCategory.name }"></form:option>
        </c:forEach>
      </c:otherwise>
    </c:choose>
  </form:select>
  <form:errors path="parentCategory.id"/>
  <br />
  <form:label path="name">商品类别名称：</form:label>
  <form:input path="name" />
  <form:errors path="name" />
  <br />
  <form:label path="cdesc">商品类别描述：</form:label>
  <form:textarea path="cdesc" />
  <form:errors path="cdesc" />
  <br />
  <br />
  <div class="form-group">
    <form:input id="img" type="file" path="imgFile" class="btn-info" accept="image/*" title="请选择类别图片" onclick="displayUploadImgDiv()"/>
    <form:errors path="imgFile"/>
  </div>
  <br />
  <br />
  <div id="categoryImgDiv" style="display: none;">
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
  <input type="submit" class="btn-success btn-lg" value="创建新类别" />
</form:form>

<%@ include file="../../../../template/footer.jsp"%>