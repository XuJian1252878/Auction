<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../../template/header.jsp"%>

<link rel="stylesheet" href="template/bingdian-waterfall/demos/css/reset.css">
<link rel="stylesheet" href="template/bingdian-waterfall/demos/css/waterfall.css">
<script type="text/javascript" src="template/bingdian-waterfall/libs/handlebars/handlebars.js"></script>
<script type="text/javascript" src="template/bingdian-waterfall/build/waterfall.min.js"></script>

<div class="container">
  <div class="row">
    <div class="col-md-12">
      <div id="waterfall-container"></div>
    </div>
    <div class="col-md-8 col-md-offset-3">
      <nav id="my-page-navigation" style="display: none;">
        <ul class="pagination">
          <li class="disabled">
            <a href="?&p=1" data-target="page" data-page="0">
              <span aria-hidden="true" >&laquo;上一页</span>
            </a>
          </li>
          <li class="active"><a href="?&p=1" data-target="page" data-page="1">1 <span class="sr-only">(current)</span></a></li>
          <li><a href="&p=2" data-target="page" data-page="2">2</a></li>
          <li><a href="&p=3" data-target="page" data-page="3">3</a></li>
          <li><a href="&p=4" data-target="page" data-page="4">4</a></li>
          <li><a href="&p=5" data-target="page" data-page="5">5</a></li>
          <li><a href="&p=6" data-target="page" data-page="6">6</a></li>
          <li><a href="&p=7" data-target="page" data-page="7">7</a></li>
          <li><a href="&p=8" data-target="page" data-page="8">8</a></li>
          <li><a href="&p=9" data-target="page" data-page="9">9</a></li>
          <li><a href="&p=10" data-target="page" data-page="10">10</a></li>
          <li>
            <a href="?&p=2" data-target="page" data-page="2">
             <span aria-hidden="true" >下一页&raquo;</span>
            </a>
          </li>
        </ul>
    </nav><!-- #page-navigation -->
    </div>
  </div>
</div>

<script type="text/x-handlebars-template" id="waterfall-tpl">
{{#result}}
    <div class="item">
        <img src="{{imgFilePath}}" width="{{width}}" height="{{height}}" />
    </div>
{{/result}}
</script>

<script>
  $('#waterfall-container').waterfall({
    itemCls: 'item',
    colWidth: 300,  
    gutterWidth: 15,
    gutterHeight: 15,
    maxPage: 1,
    checkImagesLoaded: false,
    callbacks : {
      loadingFinished : function($loading, isBeyondMaxPage) {
        if (!isBeyondMaxPage) {
          $loading.fadeOut();
        } else {
          $loading.hide();
          //$('#my-page-navigation').style.visibility='visible';
          var myPageNavigation = document.getElementById("my-page-navigation");
          myPageNavigation.style.display = "block";
        }
      }
    },
    path : function(page) {
      return 'imgdata/' + page;
    }
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
<%@ include file="../../../template/footer.jsp"%>