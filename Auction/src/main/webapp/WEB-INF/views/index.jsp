<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../template/header.jsp"%>

<!-- Custom styles for this template -->
<link href="styles/carousel.css" rel="stylesheet">
<!-- “/”代表的是 web 站点的根路径。因为超链接可以链接到任何需要的目标资源, 
  所以 / 代表的肯定不是当前 web 应用的根路径, 而是当前 web 站点的根路径。 -->

<!-- Carousel
    ================================================== -->
<div id="myCarousel" class="carousel slide" data-ride="carousel">
  <!-- Indicators -->
  <ol class="carousel-indicators">
    <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
    <li data-target="#myCarousel" data-slide-to="1"></li>
    <li data-target="#myCarousel" data-slide-to="2"></li>
  </ol>
  <div class="carousel-inner" role="listbox">
    <div class="item active">
      <img class="first-slide"
        src="images/index/bkgnd1.jpg"
        alt="First slide">
      <div class="container">
        <div class="carousel-caption">
          <h1>校园二手交易平台</h1>
          <p>为在校大学生提供便捷的闲置物品处理平台，在这里我们可以将闲置物品卖出最好的价钱，再也不用为处理闲置物品发愁。</p>
        </div>
      </div>
    </div>
    <div class="item">
      <img class="second-slide"
        src="images/index/bkgnd2.jpg"
        alt="Second slide">
      <div class="container">
        <div class="carousel-caption">
          <h1>校园二手交易平台</h1>
          <p>为在校大学生提供便捷的闲置物品处理平台，在这里我们可以将闲置物品卖出最好的价钱，再也不用为处理闲置物品发愁。</p>
        </div>
      </div>
    </div>
    <div class="item">
      <img class="third-slide"
        src="images/index/bkgnd3.jpg"
        alt="Third slide">
      <div class="container">
        <div class="carousel-caption">
          <h1>校园二手交易平台</h1>
          <p>为在校大学生提供便捷的闲置物品处理平台，在这里我们可以将闲置物品卖出最好的价钱，再也不用为处理闲置物品发愁。</p>
        </div>
      </div>
    </div>
  </div>
  <a class="left carousel-control" href="#myCarousel" role="button"
    data-slide="prev"> <span
    class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
    <span class="sr-only">Previous</span>
  </a> <a class="right carousel-control" href="#myCarousel" role="button"
    data-slide="next"> <span
    class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
    <span class="sr-only">Next</span>
  </a>
</div>
<!-- /.carousel -->


<!-- Marketing messaging and featurettes
    ================================================== -->
<!-- Wrap the rest of the page in another container to center all the content. -->

<div class="container marketing">

  <!-- Three columns of text below the carousel -->
  <div class="row">
    <div class="col-lg-4">
      <img class="img-circle"
        src="images/index/head1.jpg"
        alt="Generic placeholder image" width="140" height="140">
      <h2>放心</h2>
      <p>本二手交易平台仅限于校园，商品可靠性是很高的，同学们可以放心在本网站竞拍商品。</p>
    </div>
    <!-- /.col-lg-4 -->
    <div class="col-lg-4">
      <img class="img-circle"
        src="images/index/head2.jpg"
        alt="Generic placeholder image" width="140" height="140">
      <h2>便捷</h2>
      <p>毕业在即，手头的闲置物品不知如何处理？那么把您的闲置物品上传到本平台吧，让本平台帮助您找到需求者，免除您处理闲置物品的烦恼。</p>
    </div>
    <!-- /.col-lg-4 -->
    <div class="col-lg-4">
      <img class="img-circle"
        src="images/index/head3.jpg"
        alt="Generic placeholder image" width="140" height="140">
      <h2>高效</h2>
      <p>本二手交易平台仅限于校园，同时采取竞价机制对商品进行拍卖。可以将您的闲置物品卖出最好的价钱，大家赶快将手头的闲置物品上传到该平台吧。</p>
    </div>
    <!-- /.col-lg-4 -->
  </div>
  <!-- /.row -->

  <!-- START THE PRODUCT CATEGORIES -->
  <hr class="featurette-divider">

  <div class="row featurette">
    <!-- /.col-lg-12 -->
    <div class="col-lg-12">
      <div class="title">
        <h2>
          Aution<span> 商品类别</span>
        </h2>
      </div>
    </div>
    <c:choose>
      <c:when test="${categoryList == null || fn:length(categoryList) == 0}">
        <div class="col-lg-12 col-lg-offset-5">
          <div class="title">
            <h4>暂无商品类别信息</h4>
          </div>
        </div>
      </c:when>
      <c:otherwise>
        <!-- /.col-lg-4 -->
        <div class="gallery">
          <c:forEach var="category" items="${categoryList }">
            <div class="col-lg-4">
              <a href="#"><img src="images/test/image${category.id }.png" width="240" height="240" alt="image" /></a>
              <div class="overlay">
                <h4>${categoey.name }</h4>
                <p>
                  <span>${category.cdesc }</span>
                </p>
                <a href="category/list/${category.id }_1">查看商品列表</a>
              </div>
            </div>
          </c:forEach>
        </div>
        <!-- /.col-lg-4 -->
      </c:otherwise>
    </c:choose>
  </div>


  <!-- START THE FEATURETTES -->

  <hr class="featurette-divider">

  <div class="row featurette">
    <div class="col-md-7">
      <h2 class="featurette-heading">
        毕业在即，旧物难以处理？
      </h2>
      <p class="lead">毕业在即，我们难免会有一些旧物需要处理，或出售或送人甚至是丢弃。我们处理闲置物品时自我估价总会出现偏差，
      这可能导致物品难以出售或是所谓的“亏本”状况的出现。如果您遇到上述问题，那么请您加入该平台吧，让本平台帮您卖旧物。</p>
    </div>
    <div class="col-md-5">
      <img class="featurette-image img-responsive center-block"
        data-src="holder.js/500x500/auto"
        src="images/index/head1.jpg"
        alt="Generic placeholder image">
    </div>
  </div>

  <hr class="featurette-divider">

  <div class="row featurette">
    <div class="col-md-7 col-md-push-5">
      <h2 class="featurette-heading">
        无法找到需求者？
      </h2>
      <p class="lead">现今，在校园二手交易市场上，常见的售卖方式有以下几种：在学校里的宣传栏、广告栏上粘贴售卖二手物品的广告；
      定点摆摊出售；通过论坛、贴吧出售。但这些方式宣传力度有限，同时也十分耗费精力。如果您遇到上述问题，那么请您加入本平台吧，
      让本平台帮您找到隐藏需求者。</p>
    </div>
    <div class="col-md-5 col-md-pull-7">
      <img class="featurette-image img-responsive center-block"
        data-src="holder.js/500x500/auto"
        src="images/index/head2.jpg"
        alt="Generic placeholder image">
    </div>
  </div>

  <hr class="featurette-divider">

  <div class="row featurette">
    <div class="col-md-7">
      <h2 class="featurette-heading">
        闲置物品卖不出好价钱？
      </h2>
      <p class="lead">我们由于时间、精力等原因，往往最终会将我们的闲置物品底价卖给学校中的收购店铺。发愁旧物卖不出好价钱？
      那么加入本平台吧，本平台采用竞价的方式对上传物品进行拍卖，能让您的物品卖出合理的好价钱。</p>
    </div>
    <div class="col-md-5">
      <img class="featurette-image img-responsive center-block"
        data-src="holder.js/500x500/auto"
        src="images/index/head3.jpg"
        alt="Generic placeholder image">
    </div>
  </div>

  <hr class="featurette-divider">

  <!-- /END THE FEATURETTES -->


  <!-- FOOTER -->
  <footer>
    <p class="pull-right">
      <a href="index/#">回到顶部</a>
    </p>
    <p>
      &copy; 2015 Company, Inc. &middot; <a href="#">Privacy</a>
      &middot; <a href="#">Terms</a>
    </p>
  </footer>

</div>
<!-- /.container -->
<script>window.jQuery || document.write('<script src="scripts/jquery.min.js"><\/script>')</script>
<!-- Just to make our placeholder images work. Don't actually copy the next line! -->
<script src="scripts/holder.min.js"></script>
<%@ include file="../../template/footer.jsp"%>