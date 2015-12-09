<%@page import="java.util.Date"%>
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

<br />

<span id="myclock"></span>
<script type="text/javascript">
  var date = new Date(new Date().valueOf() + 10 * 1000);
  $('#myclock').countdown(date, function(event) {
    $(this).html(event.strftime('%D days %H:%M:%S'));
  })
</script>

<br />
<div>
<label >超时后继续计时</label>
<br />
<span id="elapseclock"></span>
<br />
<span id="elapseinfo"></span>
</div>
<script type="text/javascript">
  var date = new Date(new Date().valueOf() + 10 * 1000);
  $('#elapseclock').countdown(date, { elapse : true })
    .on('update.countdown', function(event) {
      $(this).html(event.strftime('%D days %H:%M:%S'));
      // 计时提醒
      if (event.elapsed) { // Either true or false
        // Counting up...
        $('#elapseinfo').html('已超时');
      } else {
        // Countdown
        $('#elapseinfo').html('未到时');
      }
    });
</script>

<div>
<label>到时提醒</label>
<br />
<span id="finishclock"></span>
<br />
<span id="finishinfo">到时信息</span>
</div>
<script type="text/javascript">
  var date = new Date(new Date().getTime() + 10 * 1000);
  $('#finishclock').countdown(date, function(event) {
    $(this).html(event.strftime('%D days %H:%M:%S'));
  }).on('finish.countdown', function(event) {
    $('#finishinfo').html('已到达预定时间');
  });
</script>

<div>
  <label>Pluralization信息</label>
  <br />
  <div id="pluralizationclock"></div>
  <div id="pluralizationinfo"></div>
</div>

<script type="text/javascript">
  
  var date = new Date(new Date().getTime() + 10 * 1000);
  $('#pluralizationclock').countdown(date, function(event) {
    $(this).html(event.strftime('%D day%!D:s; %H:%M:%S'));
  });
</script>

<label>多计时器计时</label>
<br />
<div id="countdown1" data-countdown="<%=new Date().getTime() + 10 * 1000 %>"></div>
<div id="info1">未到时限</div>
<div id="countdown2" data-countdown="<%=new Date().getTime() + 20 * 1000 %>"></div>
<div id="info2">未到时限</div>
<div id="countdown3" data-countdown="<%=new Date().getTime() + 30 * 1000 %>"></div>
<div id="info3">未到时限</div>
<div id="countdown4" data-countdown="<%=new Date().getTime() + 40 * 1000 %>"></div>
<div id="info4">未到时限</div>
<script type="text/javascript">
  $('[data-countdown]').each(function() {
    var $this = $(this), finalDate = $(this).data('countdown');
    $this.countdown(finalDate, function(event) {
      $this.html(event.strftime('%D days %H:%M:%S'));
    }).on('finish.countdown', function(event) {
      var id = $(this).attr('id');
      if (id == "countdown1") {
        id = "#info1";
      } else if (id == "countdown2") {
        id = "#info2";
      } else if (id == "countdown3") {
        id = "#info3";
      } else if (id == "countdown4") {
        id = "#info4";
      }
      $(id).html('已到时限');
    });
  });
</script>

<%@ include file="../../../template/footer.jsp"%>