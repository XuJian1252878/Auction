<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../../template/header.jsp"%>

<script type="text/javascript" src="template/bootstrap-tagsinput/src/bootstrap-tagsinput.js"></script>
<script type="text/javascript" src="scripts/typeahead.bundle.js"></script>

<!-- Bootstrap DateTimePicker -->
<script src="template/bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js"></script>
<link rel="stylesheet" type="text/css"
  href="template/bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.min.css" />
<link href="styles/typeahead-input.css" rel="stylesheet" type="text/css" />

<!-- Bootstarp ImgSelect -->
<link rel="stylesheet" type="text/css" href="template/imgareaselect/css/imgareaselect-default.css" />
<script type="text/javascript" src="template/imgareaselect/scripts/jquery.imgareaselect.pack.js"></script>
<script type="text/javascript" src="scripts/cutimg.js"></script>

<!-- Bootstarp input tags -->
<link href="template/bootstrap-tagsinput/src/bootstrap-tagsinput.css" rel="stylesheet">

<!-- Bootstarp File Input -->
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

<div class="container">
  <div class="col-lg-5 col-lg-offset-5">
    <div class="title">
      <h2>
        <span>商品上传</span>
      </h2>
    </div>
  </div>
  <hr />

  <form:form modelAttribute="product" action="product/upload" enctype="multipart/form-data" method="post">
    <section>
      <div class="container">
        <div class="row">
          <div class="col-lg-12">
            <h2 class="section-heading">商品基本信息</h2>
            <br />
            <div class="form-group">
              <div class="row">
                <div class="col-md-2">
                  <form:label path="category.id">请选择商品类别：</form:label>
                </div>
                <div class="col-md-3">
                  <form:select path="category.id">
                    <c:choose>
                      <c:when test="${categories == null || fn:length(categories) == 0 }">
                        <form:option value="-1" label="暂无商品类别可选"></form:option>
                      </c:when>
                      <c:otherwise>
                        <form:option value="-1" label="请选择商品类别："></form:option>
                        <c:forEach var="mycategory" items="${categories }">
                          <form:option value="${mycategory.id }" label="${mycategory.name }"></form:option>
                        </c:forEach>
                      </c:otherwise>
                    </c:choose>
                  </form:select>
                  <form:errors path="category.id" />
                </div>
                <div class="col-md-2 col-md-offset-1">
                  <form:label path="name">商品名称：</form:label>
                </div>
                <div class="col-md-3">
                  <form:input path="name" />
                  <form:errors path="name" />
                </div>
              </div>
            </div>
            <form:input path="user.id" type="hidden" value="${loginUser.id }" />
            <br /> <br />
            <div class="form-group">
              <form:label path="describe">商品描述信息：</form:label>
              <form:textarea path="describe" />
              <form:errors path="describe" />
            </div>
            <br /> <br />
            <div class="form-group">
              <form:label path="basicPrice">拍卖基价：</form:label>
              <form:input path="basicPrice" />
              <form:errors path="basicPrice" />
            </div>
            <br /> <br />
            <div class="row">
              <div class="col-lg-2">
                <label>商品标签:</label>
              </div>
              <div class="col-lg-7">
                <input id="producttags" name="producttags" class="typeahead" type="text" data-role="tagsinput" />
              </div>
              <script type="text/javascript">
                var tagEngine = new Bloodhound({
                  datumTokenizer : function(datum) {
                    return Bloodhound.tokenizers.whitespace(datum.tag);
                  },
                  queryTokenizer : Bloodhound.tokenizers.whitespace,
                  prefetch : {
                    url : 'producttag/prefetch', // 获得预先缓存的json数据。
                    ttl : 0, // 不使用cache。
                    filter : function(tags) {
                      return $.map(tags.result, function(tagPair) {
                        return {
                          id : tagPair.id,
                          tag : tagPair.tag
                        };
                      });
                    }
                  }
                });
                tagEngine.clearPrefetchCache(); // 清除缓存信息，保证每一次的结果都能输出。
                // 初始化缓存的engine。
                tagEngine.initialize();
                $('#producttags').tagsinput({
                  tagClass : function(item) {
                    return 'label label-success';
                  },
                  typeaheadjs : {
                    name : 'tags',
                    displayKey : 'tag',
                    valueKey : 'tag',
                    source : tagEngine.ttAdapter()
                  }
                })
              </script>
            </div>
          </div>
        </div>
      </div>
    </section>
    <hr />

    <%-- 商品竞拍时间选择部分 --%>
    <section>
      <div class="container">
        <div class="row">
          <div class="col-lg-12">
            <h2 class="section-heading">竞拍结束时间</h2>
            <br /> <input id="endTimeMillis" name="endTimeMillis" type="hidden" />
            <form:errors path="endDate" />
            <div style="overflow: hidden;">
              <div class="form-group">
                <div class="row">
                  <div class="col-md-8 col-md-offset-2">
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
          </div>
        </div>
      </div>
    </section>
    <hr />

    <%-- 商品图片上传部分 --%>
    <section>
      <div class="container">
        <div class="row">
          <div class="col-lg-12">
            <h2 class="section-heading">商品图片上传</h2>
            <br />
            <div class="form-group">
              <div class="row">
                <div class="col-md-8 col-md-offset-5">
                  <form:input id="img" type="file" path="imgFile" class="btn-info" title="请选择类别图片"
                    onclick="displayUploadImgDiv()" />
                  <form:errors path="imgFile" />
                </div>
              </div>
            </div>
            <br /> <br />
            <%-- 裁剪商品图片部分 --%>
            <div id="productImgDiv" style="display: none;">
              <div class="container">
                <div class="row">
                  <div id="uploadImgDiv" class="col-md-5 col-md-offset-1">
                    <img id="uploadImg" src="" alt="测试预览图片"> <br /> <br />
                    <!-- 加上type="button" ，可以防止点击button的时候自动提交。 -->
                    <button id="cutImgBtn" class="btn-info btn-sm" style="display: none;" value="裁剪" onclick="cutImg()"
                      type="button">裁剪</button>
                    <input type="hidden" name="x1" value="-1"> <input type="hidden" name="y1" value="-1">
                    <input type="hidden" name="x2" value="-1"> <input type="hidden" name="y2" value="-1">
                    <input type="hidden" name="imgWidth" value="-1"> <input type="hidden" name="imgHeight"
                      value="-1">
                  </div>
                  <label for="previewImgDiv">图片预览</label>
                  <div id="previewImgDiv" class="col-md-5 col-md-offset-1">
                    <img id="previewImg" src="" style="position: relative;" />
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
    <hr />

    <%-- 商品信息提交部分 --%>
    <div class="row">
      <div class="col-md-4 col-md-offset-5">
        <input type="submit" class="btn-success btn-lg" value="上传新商品" />
      </div>
    </div>
  </form:form>

</div>

<%@ include file="../../../template/footer.jsp"%>