<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../../template/header.jsp"%>

<script type="text/javascript"
  src="template/jquery.countdown/dist/jquery.countdown.min.js"></script>

<span id="clock"></span>
<script type="text/javascript">
  var date = new Date(new Date().valueOf() + 15 * 24 * 60 * 60 * 1000);
  $('#clock').countdown(date, function(event) {
    $(this).html(event.strftime('%D days %H:%M:%S'));
  });
</script>

<div data-countdown="2016/01/01"></div>
<div data-countdown="2017/01/01"></div>
<div data-countdown="2018/01/01"></div>
<div data-countdown="2019/01/01"></div>
<script type="text/javascript">
  $('[data-countdown]').each(function() {
    var $this = $(this), finalDate = $(this).data('countdown');
    $this.countdown(finalDate, function(event) {
      $this.html(event.strftime('%D days %H:%M:%S'));
    });
  });
</script>

<%@ include file="../../../template/footer.jsp"%>