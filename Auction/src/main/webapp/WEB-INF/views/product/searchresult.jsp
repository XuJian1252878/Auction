<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../../template/header.jsp"%>

<link rel="stylesheet" href="styles/reset.css">
<link rel="stylesheet" href="styles/waterfall.css">
<script type="text/javascript" src="template/jquery.countdown/dist/jquery.countdown.min.js"></script>
<script type="text/javascript" src="template/bingdian-waterfall/libs/handlebars/handlebars.js"></script>
<script type="text/javascript" src="template/bingdian-waterfall/build/waterfall.min.js"></script>

<c:choose>
  <c:when test="${ productCount <= 0 }">
    <label>暂未找到 ${searchTags } 这些标签下的商品。</label>
  </c:when>
  <c:otherwise>
    <div class="container">
      <div class="row">
        <div class="col-md-12">
          <div id="waterfall-container"></div>
        </div>
        <div class="col-md-8 col-md-offset-3">
          <nav id="my-page-navigation" style="display: none;">
            <ul class="pagination">
              <li <c:if test="${pageNo == 1}"> class="disabled" </c:if> >
                <a href="product/search/${pageNo - 1}" data-target="page" data-page="${pageNo - 1}">
                  <span aria-hidden="true">&laquo;上一页</span>
                </a>
              </li>
              <c:forEach var="index" begin="1" end="${pageCount}">
                <c:choose>
                  <c:when test="${index == pageNo}">
                    <li class="active"><a href="product/search/${index}" data-target="page" data-page="${index}">${index} <span class="sr-only">(current)</span></a></li>
                  </c:when>
                  <c:otherwise>
                    <li><a href="product/search/${index}" data-target="page" data-page="${index}">${index}</a></li>
                  </c:otherwise>
                </c:choose>
              </c:forEach>
              <li <c:if test="${pageNo == pageCount}"> class="disabled" </c:if> >
                <a href="product/search/${pageNo + 1}" data-target="page" data-page="${pageNo + 1}"> 
                  <span aria-hidden="true">下一页&raquo;</span>
                </a>
              </li>
            </ul>
          </nav>
          <!-- #page-navigation -->
        </div>
      </div>
    </div>
    <script type="text/x-handlebars-template" id="waterfall-tpl">
      {{#result}}
        <div class="item">
          <img src="{{imgPath}}" width="300" height="300" />
          <br />
          <div class="row">
            <div class="col-sm-12 col-sm-offset-4">
              <a href="product/detail/{{id}}">查看商品链接</a>
              <br/>
              <div id="{{countdownId}}" data-countdown="{{endDate}}"></div>
              <div id="{{countdownAlertId}}"></div>
            </div>
          </div>
        </div>
      {{/result}}
    </script>
    <script>
      $('#waterfall-container').waterfall({
        itemCls: 'item',
        // 设置瀑布流中的每一列的最大宽度，这个最大宽度要大于item元素的宽度，否则会发生重叠。
        colWidth: 350,
        gutterWidth: 15,
        gutterHeight: 15,
        maxPage: '${maxWaterfallParts}', // 使用jstl从后台取出数据。
        // 是否图片加载完成后开始排列数据块。如果直接后台输出图片尺寸，可设置为false，强烈建议从后台输出图片尺寸，设置为false
        checkImagesLoaded: false,
        callbacks : {
          /*
           * ajax请求加载完成
           * @param {Object} loading $('#waterfall-loading')
           * @param {Boolean} isBeyondMaxPage
           */
          loadingFinished : function($loading, isBeyondMaxPage) {
            if (!isBeyondMaxPage) {
              $loading.fadeOut();
            } else {
              $loading.hide();
              //$('#my-page-navigation').style.visibility='visible';
              var myPageNavigation = document.getElementById("my-page-navigation");
              myPageNavigation.style.display = "block";
            }
            // 初始化 countdown 组件。
            $('[data-countdown]').each(function() {
              var $this = $(this), finalDate = $(this).data('countdown');
              $this.countdown(finalDate, function(event) {
                $this.html(event.strftime('%D days %H:%M:%S'));
              }).on('finish.countdown', function(event) {
                var colckId = $(this).attr('id');
                var expireId = "#productexpirealert" + colckId.replace(/[^0-9]/ig,"");
                $(expireId).html('该商品已停止竞价！');
              })
            })
          },
        /*
         * 处理ajax返回数方法
         * @param {String} data
         * @param {String} dataType , "json", "jsonp", "html"
         */
         renderData: function(data, dataType) {
           var tpl, template;
           if ( dataType === 'json' ||  dataType === 'jsonp'  ) { // json或jsonp格式
             tpl = $('#waterfall-tpl').html();
             template = Handlebars.compile(tpl);
             return template(data);
           } else { // html格式
             return data;
           }
         }
        },
        path : function(page) {
          /**
          瀑布流数据分页url，可以是数组如[“/popular/page/”, “/”] => “/popular/page/1/"，
          或者是根据分页返回一个url方法如：function(page) { return ‘/populr/page/’ + page; } => ”/popular/page/1/“
          */
          // 关于 get 参数的编码：http://stackoverflow.com/questions/332872/encode-url-in-javascript
          var tagsParam = "${searchTags}";
          return "product/search/" + ${pageNo} + "_" + page + "/" + encodeURIComponent(tagsParam);
        },
        // 加载提示进度条，html
        loadingMsg : '<div style="text-align:center;padding:10px 0; color:#999;"><img src="data:image/gif;base64,R0lGODlhEAALAPQAAP///zMzM+Li4tra2u7u7jk5OTMzM1hYWJubm4CAgMjIyE9PT29vb6KiooODg8vLy1JSUjc3N3Jycuvr6+Dg4Pb29mBgYOPj4/X19cXFxbOzs9XV1fHx8TMzMzMzMzMzMyH5BAkLAAAAIf4aQ3JlYXRlZCB3aXRoIGFqYXhsb2FkLmluZm8AIf8LTkVUU0NBUEUyLjADAQAAACwAAAAAEAALAAAFLSAgjmRpnqSgCuLKAq5AEIM4zDVw03ve27ifDgfkEYe04kDIDC5zrtYKRa2WQgAh+QQJCwAAACwAAAAAEAALAAAFJGBhGAVgnqhpHIeRvsDawqns0qeN5+y967tYLyicBYE7EYkYAgAh+QQJCwAAACwAAAAAEAALAAAFNiAgjothLOOIJAkiGgxjpGKiKMkbz7SN6zIawJcDwIK9W/HISxGBzdHTuBNOmcJVCyoUlk7CEAAh+QQJCwAAACwAAAAAEAALAAAFNSAgjqQIRRFUAo3jNGIkSdHqPI8Tz3V55zuaDacDyIQ+YrBH+hWPzJFzOQQaeavWi7oqnVIhACH5BAkLAAAALAAAAAAQAAsAAAUyICCOZGme1rJY5kRRk7hI0mJSVUXJtF3iOl7tltsBZsNfUegjAY3I5sgFY55KqdX1GgIAIfkECQsAAAAsAAAAABAACwAABTcgII5kaZ4kcV2EqLJipmnZhWGXaOOitm2aXQ4g7P2Ct2ER4AMul00kj5g0Al8tADY2y6C+4FIIACH5BAkLAAAALAAAAAAQAAsAAAUvICCOZGme5ERRk6iy7qpyHCVStA3gNa/7txxwlwv2isSacYUc+l4tADQGQ1mvpBAAIfkECQsAAAAsAAAAABAACwAABS8gII5kaZ7kRFGTqLLuqnIcJVK0DeA1r/u3HHCXC/aKxJpxhRz6Xi0ANAZDWa+kEAA7" alt=""><br />正在加载商品信息……</div>'
      });
    </script>
    <script type="text/javascript">
      var _gaq = _gaq || [];
      _gaq.push([ '_setAccount', 'UA-1245097-16' ]);
      _gaq.push([ '_trackPageview' ]);
      _gaq.push([ '_trackPageLoadTime' ]);
      (function() {
        var ga = document.createElement('script');
        ga.type = 'text/javascript';
        ga.async = true;
        ga.src = 'scripts/ga.js';
        var s = document.getElementsByTagName('script')[0];
        s.parentNode.insertBefore(ga, s);
      })();
    </script>
  </c:otherwise>
</c:choose>

<%@ include file="../../../template/footer.jsp"%>