<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../../template/header.jsp"%>

<script type="text/javascript" src="template/bootstrap-tagsinput/src/bootstrap-tagsinput.js"></script>
<script type="text/javascript" src="scripts/typeahead.bundle.js"></script>
<link href="styles/typeahead-input.css" rel="stylesheet" type="text/css" />
<link href="template/bootstrap-tagsinput/src/bootstrap-tagsinput.css" rel="stylesheet" type="text/css">

<div class="container">
  <div class="row">
    <div class="col-lg-10 col-lg-offset-1">
      <h3>商品搜索</h3>
    </div>
  </div>
  <br />
  <br />
  <div class="row">
    <form:form action="product/search/1" method="post">
      <div class="col-lg-2 col-lg-offset-1">
        <h4>输入商品标签搜索商品：</h4>
      </div>
      <div class="col-lg-6">
        <input id="producttags" name="producttags" class="typeahead" type="text" data-role="tagsinput" />
        <script type="text/javascript">
          var tagEngine = new Bloodhound({
            datumTokenizer: function(datum) {
              return Bloodhound.tokenizers.whitespace(datum.tag);
            },
            queryTokenizer: Bloodhound.tokenizers.whitespace,
            prefetch: {
              url: 'producttag/prefetch',  // 获得预先缓存的json数据。
              ttl: 0,  // 不使用cache。
              filter: function(tags) {
                return $.map(tags.result, function(tagPair) {
                  return {
                    id: tagPair.id,
                    tag: tagPair.tag
                  };
                });
              }
            }
          });
          tagEngine.clearPrefetchCache();  // 清除缓存信息，保证每一次的结果都能输出。
          // 初始化缓存的engine。
          tagEngine.initialize();
          $('#producttags').tagsinput({
            tagClass: function(item) {
              return 'label label-success';
            },
            typeaheadjs: {
              name: 'tags',
              displayKey: 'tag',
              valueKey: 'tag',
              source: tagEngine.ttAdapter()
            }
          })
        </script>
      </div>
      <div class="col-lg-2">
        <input type="submit" class="btn btn-lg btn-success" value="搜索商品"/>
      </div>
    </form:form>
  </div>
</div>

<%@ include file="../../../template/footer.jsp"%>