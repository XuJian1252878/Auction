<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../../template/header.jsp"%>

<link rel="stylesheet" href="template/bingdian-waterfall/demos/css/reset.css">
<link rel="stylesheet" href="template/bingdian-waterfall/demos/css/waterfall.css">

<div id="container"></div>
<div id="page-navigation" class="hide clear">
    <span class="disabled page-navigation-prev" title="上一页">«上一页</span>
    <a href="?&p=1" data-target="page" data-page="1" class="cur">1</a>
    <a href="?&p=2" data-target="page" data-page="2">2</a>
    <a href="?&p=3" data-target="page" data-page="3">3</a>
    <a href="?&p=4" data-target="page" data-page="4">4</a>
    <a href="?&p=5" data-target="page" data-page="5">5</a>
    <a href="?&p=6" data-target="page" data-page="6">6</a>
    <a href="?&p=7" data-target="page" data-page="7">7</a>
    <a href="?&p=8" data-target="page" data-page="8">8</a>
    <a href="?&p=9" data-target="page" data-page="9">9</a>
    <a href="?&p=10" data-target="page" data-page="10">10</a>
    <a href="?&p=2" class="page-navigation-next"  data-page="2" title="下一页">下一页»</a>
</div><!-- #page-navigation -->

<script type="text/x-handlebars-template" id="waterfall-tpl">
{{#result}}
    <div class="item">
        <img src="{{imgFilePath}}" width="{{width}}" height="{{height}}" />
    </div>
{{/result}}
</script>
<script type="text/javascript" src="template/bingdian-waterfall/libs/handlebars/handlebars.js"></script>
<script type="text/javascript" src="template/bingdian-waterfall/build/waterfall.min.js"></script>

<script>
  $('#container').waterfall({
    itemCls: 'item',
    colWidth: 222,  
    gutterWidth: 15,
    gutterHeight: 15,
    maxPage: 5,
    checkImagesLoaded: false,
    callbacks : {
      loadingFinished : function($loading, isBeyondMaxPage) {
        if (!isBeyondMaxPage) {
          $loading.fadeOut();
        } else {
          $loading.hide();
          $('#page-navigation').show();
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
    ga.src = 'https://ssl.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0];
    s.parentNode.insertBefore(ga, s);
  })();
</script>
<%@ include file="../../../template/footer.jsp"%>