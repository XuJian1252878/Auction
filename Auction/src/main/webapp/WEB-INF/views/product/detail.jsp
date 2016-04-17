<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../../template/header.jsp"%>

<link rel="stylesheet" href="styles/loading.css">
<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Lobster">
<link rel='stylesheet' href='http://fonts.googleapis.com/css?family=Lato:400,700'>
<link rel="stylesheet" href="styles/product-detail-countdown.css">
<script type="text/javascript" src="template/jquery.countdown/dist/jquery.countdown.min.js"></script>
<script type="text/javascript" src="template/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="scripts/jquery.backstretch.min.js"></script>

<c:choose>
  <%-- 判断商品是否存在信息 --%>
  <c:when test="${product == null}">
    <h3>
      <span>商品信息不存在</span>
    </h3>
    <br />
  </c:when>
  <c:otherwise>
    <!-- Coming Soon -->
    <div class="coming-soon">
      <div class="inner-bg">
        <div class="container">
          <div class="row">
            <div class="span12">
              <h2>竞拍倒计时</h2>
              <p>We are working very hard on the new version of our site. It will bring a lot of new features. Stay
                tuned!</p>
              <div class="timer">
                <div class="days-wrapper">
                  <span class="days"></span> <br>days
                </div>
                <div class="hours-wrapper">
                  <span class="hours"></span> <br>hours
                </div>
                <div class="minutes-wrapper">
                  <span class="minutes"></span> <br>minutes
                </div>
                <div class="seconds-wrapper">
                  <span class="seconds"></span> <br>seconds
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <script type="text/javascript">
      jQuery(document).ready(
          function() {
            /*
                Background slideshow
             */
            $('.coming-soon')
                .backstretch(
                    [ "images/temp/backgrounds/1.jpg", "images/temp/backgrounds/2.jpg",
                        "images/temp/backgrounds/3.jpg" ], {
                      duration : 3000,
                      fade : 750
                    });
            /*
                Countdown initializer
             */
            $('.timer').countdown("${product.endDate}", function(event) {
              var $this = $(this);
              $this.find('span.days').html(event.offset.totalDays);
              $this.find('span.hours').html(event.offset.hours);
              $this.find('span.minutes').html(event.offset.minutes);
              $this.find('span.seconds').html(event.offset.seconds);
            });
          });
    </script>

    <section>
      <div class="container">
        <div class="row">
          <div class="col-lg-12">
            <h2 class="section-heading">商品详情</h2>
            <p class="lead section-lead">${product.name }</p>
            <p class="section-paragraph">${product.describe }</p>
            <p class="section-paragraph">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aliquid,
              suscipit, rerum quos facilis repellat architecto commodi officia atque nemo facere eum non illo voluptatem
              quae delectus odit vel itaque amet.</p>
            <label>商品类别：</label> <span>${product.category.name }</span> <br /> <label>商品标签：</label>
            <div class="row">
              <div class="col-lg-10 col-lg-offset-1">
                <c:forEach var="productTag" items="${productTags }">
                  <h3 style="display: inline">
                    <span class="label label-pill label-success">${productTag.tag }</span>
                  </h3>
                </c:forEach>
              </div>
            </div>
            <br /> <label>商品上传时间：</label> <span>${product.onSaleDate }</span> <br /> <label>结束竞拍时间：</label> <span>${product.endDate }</span>
            <br /> <br /> <label>竞拍倒计时：</label> <span id="expireClockId"></span> <br />
            <script type="text/javascript">
              $('#expireClockId').countdown("${product.endDate}", function(event) {
                $(this).html(event.strftime('%D days %H:%M:%S'));
                }).on('finish.countdown', function(event) {
                  $(this).html('该商品已停止竞拍！');
                })
            </script>
            <label>竞拍起价：</label> <span>${product.basicPrice }</span> <br /> <img src="${product.imgPath }" width="300"
              height="300" /> <br /> <br />
          </div>
        </div>
      </div>
    </section>

    <%-- 商品竞价部分 --%>
    <hr />
    <section>
      <div class="container">
        <div class="row">
          <div class="col-lg-12">
            <h1 class="section-heading">竞价信息</h1>

            <c:choose>
              <c:when test="${sessionScope.loginuser != null}">
                <!-- 只有上传该商品的用户才能看到所有的竞价信息。 -->
                <c:if test="${sessionScope.loginuser.id == product.user.id}">
                  <c:choose>
                    <c:when test="${productBids == null || fn:length(productBids) == 0}">
                      <p class="lead section-lead">当前还未有人对该商品进行竞价！</p>
                    </c:when>
                    <c:otherwise>
                      <c:forEach var="bid" items="${productBids }">
                        <p class="lead section-lead">${bid.price }</p>
                        <p class="section-paragraph">出价用户：${bid.user.userName }，出价时间：${bid.bidDate }</p>
                      </c:forEach>
                    </c:otherwise>
                  </c:choose>
                </c:if>
                <!-- 只有登陆用户（不包括该商品的上传用户）才能参与竞价。 -->
                <c:if test="${sessionScope.loginuser.id != product.user.id}">
                  <!-- Button trigger modal -->
                  <button type="button" class="btn btn-danger btn-lg" data-toggle="modal" data-target="#myModal">我要竞价</button>
                  <form:form action="bid/commit_${product.id }" modelAttribute="userbid" method="post">
                    <form:input type="hidden" path="product.id" value="${product.id }" />
                    <form:input type="hidden" path="user.id" value="${sessionScope.loginuser.id }" />
                    <div id="myModal" class="modal fade" tabindex="-1" role="dialog"
                      aria-labelledby="gridSystemModalLabel">
                      <div class="modal-dialog" role="document">
                        <div class="modal-content">
                          <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                              <span aria-hidden="true">&times;</span>
                            </button>
                            <h4 class="modal-title" id="gridSystemModalLabel">竞价提示</h4>
                          </div>
                          <div class="modal-body">
                            <div class="row">
                              <div class="col-md-12">
                                <h4>
                                  该商品的竞拍起价为：<span class="label label-danger">${product.basicPrice }</span>，您的出价必须高于竞拍起价！
                                </h4>
                              </div>
                            </div>
                            <c:if test="${oldBid != null}">
                              <div class="row">
                                <div class="col-md-12">
                                  <h4>
                                    您之前已对该商品进行出价，您的出价为：<span class="label label-danger">${oldBid.price }</span>！
                                  </h4>
                                </div>
                              </div>
                            </c:if>
                            <div class="row">
                              <div class="col-md-12">
                                <div class="input-group input-group-lg">
                                  <span class="input-group-addon" id="sizing-addon1">我的竞拍价：</span>
                                  <form:input type="text" path="price" class="form-control" placeholder="请输入您的竞拍价"
                                    aria-describedby="sizing-addon1" />
                                </div>
                              </div>
                            </div>
                          </div>
                          <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">取消竞价</button>
                            <button id="commitBidBtn" type="submit" class="btn btn-primary">提交竞价</button>
                          </div>
                        </div>
                        <!-- /.modal-content -->
                      </div>
                      <!-- /.modal-dialog -->
                    </div>
                    <!-- /.modal -->
                  </form:form>
                </c:if>
              </c:when>
              <c:otherwise>
                <h4>
                  想要参与竞价？赶快<a href="user/register"><span class="label label-success">注册</span></a>吧！
                </h4>
              </c:otherwise>
            </c:choose>
          </div>
        </div>
      </div>
    </section>
    <hr />

    <%-- 商品评价信息部分 --%>
    <section>
      <div class="container">
        <div class="row">
          <div class="col-lg-12">
            <h1 class="section-heading">商品评价</h1>
            <p class="section-paragraph">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aliquid,
              suscipit, rerum quos facilis repellat architecto commodi officia atque nemo facere eum non illo voluptatem
              quae delectus odit vel itaque amet.</p>
            <c:choose>
              <c:when test="${productComments == null || fn:length(productComments) == 0}">
                <p class="lead section-lead">暂无该商品的评价信息！</p>
              </c:when>
              <c:otherwise>
                <c:forEach var="productComment" items="${productComments }">
                  <p class="lead section-lead">${productComment.user.userName }发表于： ${productComment.pubDate }</p>
                  <p class="section-paragraph">${productComment.commentText }</p>
                </c:forEach>
              </c:otherwise>
            </c:choose>
          </div>
        </div>
      </div>
    </section>
    <hr />

    <%-- 用户发表评论区域 --%>
    <section>
      <div class="container">
        <div class="row">
          <div class="col-lg-12">
            <h1 class="section-heading">发表评论</h1>
            <c:choose>
              <c:when test="${sessionScope.loginuser == null }">
                <p class="lead section-lead">
                  未登陆用户不能发表评论信息！赶快<a href="user/login"><span class="label label-success">登陆</span></a>或者<a
                    href="user/register"><span class="label label-success">注册</span>吧！</a>
                </p>
              </c:when>
              <c:otherwise>
                <div class="row">
                  <div class="col-lg-8 col-lg-offset-2">
                    <form:form action="comment/pub" modelAttribute="userComment" method="post">
                      <form:input type="hidden" path="user.id" value="${sessionScope.loginuser.id }" />
                      <form:input type="hidden" path="product.id" value="${product.id }" />
                      <form:textarea id="commentEditor" name="commentEditor" path="commentText" rows="5" cols="80"></form:textarea>
                      <br />
                      <script type="text/javascript">
                                              // Replace the <textarea id="commentEditor"> with a CKEditor
                                              // instance, using default configuration.
                                              CKEDITOR.replace('commentEditor');
                                            </script>
                      <form:errors path="commentText"></form:errors>
                      <br />
                      <div class="row">
                        <div class="col-lg-2 col-lg-offset-10">
                          <input type="submit" class="btn btn-lg btn-success" value="提交评论" />
                        </div>
                      </div>
                    </form:form>
                  </div>
                </div>
              </c:otherwise>
            </c:choose>
          </div>
        </div>
      </div>
    </section>
  </c:otherwise>
</c:choose>


<button type="button" class="btn btn-danger btn-lg" data-toggle="modal" data-target="#pleaseWaitDialog">进度条测试</button>
<div class="modal fade" id="pleaseWaitDialog" data-backdrop="static" data-keyboard="false">
  <div class="modal-header">
    <h1>Processing...</h1>
  </div>
  <div class="modal-body">
    <div class="row">
      <div class="col-md-8 col-md-offset-2">
        <div class="progress progress-striped active">
          <div class="bar bar-success" style="width: 100%;"></div>
        </div>
      </div>
    </div>
  </div>
  <div class="modal-footer">
    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
    <button type="button" class="btn btn-primary" >Commit</button>
  </div>
</div>

<div id="contentDiv"><h2>我的测试文字</h2></div>
<button id="btn001" type="button">ChangeContent</button><br /><br />

<script type="text/javascript">
  $('#btn001').click(function(){
    htmlobj = $.ajax({url:"test/imgdatatxt/1", async: false});
    $('#contentDiv').html(htmlobj.responseText);
  })
</script>

<%@ include file="../../../template/footer.jsp"%>