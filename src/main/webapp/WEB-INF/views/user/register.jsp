<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../../template/header.jsp"%>

<link rel="stylesheet" type="text/css"
  href="template/imgareaselect/css/imgareaselect-default.css" />
<script type="text/javascript"
  src="template/imgareaselect/scripts/jquery.min.js"></script>
<script type="text/javascript"
  src="template/imgareaselect/scripts/jquery.imgareaselect.pack.js"></script>
<link href="/styles/register.css" rel="stylesheet">
<script type="text/javascript" src="scripts/register.js"></script>
<script
  src="https://maps.googleapis.com/maps/api/js?signed_in=true&libraries=places&callback=initAutocomplete"
  async defer></script>

<script type="text/javascript">
  var activeEl = 2;
  $(function() {
    var items = $('.btn-nav');
    $(items[activeEl]).addClass('active');
    $(".btn-nav").click(function() {
      $(items[activeEl]).removeClass('active');
      $(this).addClass('active');
      activeEl = $(".btn-nav").index(this);
    });
  });
</script>

<%-- 
  <!-- 用户注册表单 -->
  <form:form modelAttribute="registerUser" action="register"
    enctype="multipart/form-data" method="post">
    <form:label for="userName" path="userName">注册用户名：</form:label>
    <form:input path="userName" type="text" />
    <form:errors path="userName" />
    <br />
    <form:label for="email" path="email">注册邮箱:</form:label>
    <form:input path="email" type="text" />
    <form:errors path="email" />
    <br />
    <form:label for="password" path="password">密码：</form:label>
    <form:input path="password" type="password" />
    <form:errors path="password" />
    <br />
    <form:label for="confirmPassword" path="confirmPassword">确认密码：</form:label>
    <form:input path="confirmPassword" type="password" />
    <form:errors path="confirmPassword" />
    <br />
    <form:label for="age" path="age">年龄：</form:label>
    <form:input path="age" type="text" />
    <form:errors path="age" />
    <br />
    <form:label for="address" path="address">用户地址：</form:label>
    <form:input path="address" type="text" />
    <form:errors path="address" />
    <br />
    <form:label path="avatarFile">请选择头像图片：</form:label>
    <form:input type="file" path="avatarFile" />
    <form:errors path="avatarFile" />
    <br />
    <input type="submit" value="注册新用户" />
  </form:form>
 --%>
<br>
<br>

<div class="container-fluid">
  <div class="row">
    <div class="col-xs-2 col-md-2" style="padding-left: 40px">
      <br>
      <div style="text-align: left;">
        <div class="container">
          <div class="btn-group btn-group-vertical">
            <div class="btn-group">
              <button type="button" class="btn btn-nav">
                <span class="glyphicon glyphicon-user"></span>
                <p>Profile</p>
              </button>
            </div>
            <div class="btn-group">
              <button type="button" class="btn btn-nav">
                <span class="glyphicon glyphicon-calendar"></span>
                <p>Calendar</p>
              </button>
            </div>
            <div class="btn-group">
              <button type="button" class="btn btn-nav">
                <span class="glyphicon glyphicon-globe"></span>
                <p>Network</p>
              </button>
            </div>
            <div class="btn-group">
              <button type="button" class="btn btn-nav">
                <span class="glyphicon glyphicon-picture"></span>
                <p>Upload</p>
              </button>
            </div>
            <div class="btn-group">
              <button type="button" class="btn btn-nav">
                <span class="glyphicon glyphicon-time"></span>
                <p>Statistics</p>
              </button>
            </div>
            <div class="btn-group">
              <button type="button" class="btn btn-nav">
                <span class="glyphicon glyphicon-bell"></span>
                <p>Events</p>
              </button>
            </div>
            <div class="btn-group">
              <button type="button" class="btn btn-nav">
                <span class="glyphicon glyphicon-th"></span>
                <p>ALL</p>
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="col-xs-10 col-md-10">
      <form:form modelAttribute="registerUser" action="user/register"
        enctype="multipart/form-data" method="post" role="form"
        class="form-inline col-md-10 go-right"
        style="color: Green; background-color: #FAFAFF; border-radius: 0px 22px 22px 22px;">
        <h2>Profile</h2>
        <p>Please update your profile for more security.</p>
        <div class="form-group">
          <form:label for="userName" path="userName">用户名<span
              class="glyphicon glyphicon-user"> </span>
          </form:label>
          <form:input path="userName" id="userName" name="userName"
            type="text" class="form-control" />
          <form:errors path="userName"></form:errors>
        </div>
        <div class="form-group">
          <form:label for="email" path="email">注册邮箱<span
              class="glyphicon glyphicon-user"> </span>
          </form:label>
          <form:input path="email" id="email" name="email" type="text"
            class="form-control" />
          <form:errors path="email"></form:errors>
        </div>
        <br>
        <br>
        <div class="form-group">
          <form:select id="sex" path="sex" class="form-control">
            <form:option id="Male" value="0" style="color: red">Male</form:option>
            <form:option id="FeMale" value="1" style="color: green">Female</form:option>
            <form:option id="NotInterested" value="-1"
              style="color: blue">Not
              interested</form:option>
          </form:select>
        </div>
        <div class="form-group">
          <form:label for="age" path="age">年龄<span
              class="glyphicon glyphicon-user"> </span>
          </form:label>
          <form:input path="age" id="age" name="age" type="text"
            class="form-control" />
          <form:errors path="age"></form:errors>
        </div>
        <div class="form-group">
          <form:label for="birthday" path="birthday">DOB<span
              class="glyphicon glyphicon-calendar"></span>
          </form:label>
          <form:input id="birthday" name="birthday" path="birthday"
            type="date" class="form-control" />
        </div>
        <br />
        <br />
        <div class="form-group">
          <form:label for="password" path="password">密码：</form:label>
          <form:input path="password" type="password" />
          <form:errors path="password" />
        </div>
        <div class="form-group">
          <form:label for="confirmPassword" path="confirmPassword">确认密码：</form:label>
          <form:input path="confirmPassword" type="password" />
          <form:errors path="confirmPassword" />
        </div>
        <br />
        <br />
        <div class="form-group">
          <textarea id="message" name="phone" class="form-control"
            style="width: 400px; height: 100px"
            placeholder="Short Description"></textarea>
          <label for="message">Short Description <span
            class="glyphicon glyphicon-align-justify"></span></label>
        </div>
        <br>
        <br>
        <p3>(Enter Pincode/Area to pick your nearest location)<span
          class="glyphicon glyphicon-map-marker"></span></p3>
        <br>
        <br>
        <div class="form-group" style="width: 600px">
          <input id="autocomplete" name="address" type="tel"
            onFocus="geolocate()"
            style="moz-border-radius: 22px; border-radius: 7px;">
          <label for="address">Location Picker</label>
        </div>
        <br>
        <br>
        <div class="form-group">
          <input id="route" name="route" type="tel" class="form-control"
            required disabled="true"> <label for="route">Route/Locality</label>
        </div>
        <div class="form-group">
          <input id="locality" name="locality" type="tel"
            class="form-control" required disabled="true"> <label
            for="locality">City/Town</label>
        </div>
        <br />
        <br />
        <div class="form-group">
          <input id="administrative_area_level_2"
            name="administrative_area_level_2" type="tel"
            class="form-control" required disabled="true"> <label
            for="administrative_area_level_2">District</label>
        </div>
        <div class="form-group">
          <input id="administrative_area_level_1"
            name="administrative_area_level_1" type="tel"
            class="form-control" required disabled="true"> <label
            for="administrative_area_level_1">State</label>
        </div>
        <br />
        <br />
        <div class="form-group">
          <input id="country" name="country" type="text"
            class="form-control" required disabled="true"> <label
            for="country">Country</label>
        </div>
        <div class="form-group">
          <input id="postal_code" name="postal_code" type="tel"
            class="form-control" required disabled="true"> <label
            for="postal_code">Pin Code</label>
        </div>
        <br />
        <br />
        <div class="form-group">
          <form:label path="avatarFile">请选择头像图片：</form:label>
          <form:input id="img" type="file" path="avatarFile"
            accept="image/*" />
          <form:errors path="avatarFile" />
        </div>
        <br />
        <br />
        <div class="form-group">
          <div class="container">
            <div class="row">
              <div id="uploadImgDiv" class="col-md-6">
                <img id="uploadImg" src="#" alt="测试预览图片"> <br />
                <br />
                <!-- 加上type="button" ，可以防止点击button的时候自动提交。 -->
                <button id="cutImgBtn" style="display: none;" value="裁剪"
                  onclick="cutImg()" type="button">裁剪</button>
                <input type="hidden" name="x1" value="-1"> <input
                  type="hidden" name="y1" value="-1"> <input
                  type="hidden" name="x2" value="-1"> <input
                  type="hidden" name="y2" value="-1"> <input
                  type="hidden" name="imgWidth" value="-1"> <input
                  type="hidden" name="imgHeight" value="-1">
              </div>
              <label for="previewImgDiv">图片预览</label>
              <div id="previewImgDiv" class="col-md-6">
                <img id="previewImg" src="#" style="position: relative;" />
              </div>
            </div>
          </div>
        </div>
        <br />
        <br />
        <div class="container-fluid">
          <div class="row">
            <div
              class="col-xs-col-3 col-xs-offset-5 col-md-col-3 col-md-offset-5">
              <input type="submit" class="btn-success btn-lg"
                value="Register" />
            </div>
          </div>
        </div>
        <br>
        <br>
      </form:form>
    </div>
  </div>
</div>

<%@ include file="../../../template/footer.jsp"%>