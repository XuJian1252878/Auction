<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../../template/header.jsp"%>

<link rel="stylesheet" type="text/css" href="template/imgareaselect/css/imgareaselect-default.css" />
<script type="text/javascript" src="template/imgareaselect/scripts/jquery.min.js"></script>
<script type="text/javascript" src="template/imgareaselect/scripts/jquery.imgareaselect.pack.js"></script>
<script type="text/javascript" src="scripts/cutavatarimg.js"></script>
<link href="styles/userprofile.css" style="stylesheet">

<c:choose>
  <c:when test="${loginUser == null}">
    <h2>暂无登陆用户的个人信息</h2>
  </c:when>
  <c:otherwise>
    <div class="container">
      <div class="row">
        <form:form modelAttribute="loginUser" action="user/profile" enctype="multipart/form-data" method="post">
          <div class="well profile">
            <div class="col-xs-12 col-sm-8">
              <h2>个人信息</h2>
              <form:input path="id" type="hidden"/>
              <form:errors path="id" />
              <form:input path="birthday" type="hidden" />
              <form:input path="password" type="hidden" />
              <p>
                <strong>用户名: </strong> 
                <form:input path="userName" />
                <form:errors path="userName" />
              </p>
              <p>
                <strong>邮箱：</strong>
                <form:input path="email" />
                <form:errors path="email" />
              </p>
              <p>
                <strong>年龄: </strong>
                <form:input path="age" />
                <form:errors path="age" />
              </p>
              <p>
                  <strong>性别：</strong>
                  <form:select id="sex" path="sex" class="form-control">
                  <form:option id="Male" value="0" style="color: red">Male</form:option>
                  <form:option id="FeMale" value="1" style="color: green">Female</form:option>
                  <form:option id="NotInterested" value="-1" style="color: blue">Not interested</form:option>
                  </form:select>
              </p>
              <p>
                <strong>地址：</strong>
                <form:input path="address" />
                <form:errors path="address" />
              </p>
            </div>
            <div class="col-xs-12 col-sm-4 text-center">
              <figure>
                <label>${loginUser.avatarPath }</label>
                <img src="${loginUser.avatarPath }" alt="用户头像" class="img-circle img-responsive">
              </figure>
            </div>
            <div class="form-group">
              <p>更新头像</p>
              <form:label path="avatarFile">请选择头像图片：</form:label>
              <form:input id="img" type="file" path="avatarFile" accept="image/*"/>
              <form:errors path="avatarFile" />
            </div>
            <br />
            <br />
            <div class="form-group">
              <div class="container">
                <div class="row">
                  <div id="uploadImgDiv" class="col-md-6">
                    <img id="uploadImg" src="#" alt="头像图片" />
                    <br />
                    <button id="cutImgBtn" style="display: none;" value="裁剪" onclick="cutImg()" type="button">裁剪</button>
                    <input type="hidden" name="x1" value="-1" />
                    <input type="hidden" name="y1" value="-1" />
                    <input type="hidden" name="x2" value="-1" />
                    <input type="hidden" name="y2" value="-1" />
                    <input type="hidden" name="imgWidth" value="-1" />
                    <input type="hidden" name="imgHeight" value="-1" />
                  </div>
                  <label for="previewImgDiv">图片预览</label>
                  <div id="previewImgDiv" class="col-md-6">
                    <img id="previewImg" src="#" style="poition: relative;" />
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div>
            <input type="submit" class="btn-success btn-lg" value="更新用户信息" />
          </div>
        </form:form>
        <br />
        <br />
        <div class="col-xs-12 divider text-center">
          <div class="col-xs-12 col-sm-4 emphasis">
            <h2>
              <strong> 20,7K </strong>
            </h2>
            <p>
              <small>Followers</small>
            </p>
            <button class="btn btn-success btn-block">
              <span class="fa fa-plus-circle"></span> Follow
            </button>
          </div>
          <div class="col-xs-12 col-sm-4 emphasis">
            <h2>
              <strong>245</strong>
            </h2>
            <p>
              <small>Following</small>
            </p>
            <button class="btn btn-info btn-block">
              <span class="fa fa-user"></span> View Profile
            </button>
          </div>
          <div class="col-xs-12 col-sm-4 emphasis">
            <h2>
              <strong>43</strong>
            </h2>
            <p>
              <small>Snippets</small>
            </p>
            <div class="btn-group dropup btn-block">
              <button type="button" class="btn btn-primary">
                <span class="fa fa-gear"></span> Options
              </button>
              <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
                <span class="caret"></span> <span class="sr-only">Toggle Dropdown</span>
              </button>
              <ul class="dropdown-menu text-left" role="menu">
                <li><a href="#"><span class="fa fa-envelope pull-right"></span> Send an email </a></li>
                <li><a href="#"><span class="fa fa-list pull-right"></span> Add or remove from a list </a></li>
                <li class="divider"></li>
                <li><a href="#"><span class="fa fa-warning pull-right"></span>Report this user for spam</a></li>
                <li class="divider"></li>
                <li><a href="#" class="btn disabled" role="button"> Unfollow </a></li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>
  </c:otherwise>
</c:choose>

<%@ include file="../../../template/footer.jsp"%>