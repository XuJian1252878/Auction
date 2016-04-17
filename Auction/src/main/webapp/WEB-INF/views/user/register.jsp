<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../../template/header.jsp"%>

<script type="text/javascript" src="scripts/cutimg.js"></script>
<script type="text/javascript" src="scripts/register.js"></script>
<script type="text/javascript" src="scripts/bootstrap.file-input.js"></script>
<script src="https://maps.googleapis.com/maps/api/js?signed_in=true&libraries=places&callback=initAutocomplete" async
  defer></script>
<link rel="stylesheet" type="text/css" href="template/imgareaselect/css/imgareaselect-default.css" />
<link rel="stylesheet" type="text/css" href="styles/register.css" />
<script type="text/javascript" src="template/imgareaselect/scripts/jquery.imgareaselect.pack.js"></script>

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
  
  $(document).ready(function() {
    $('input[type=file]').bootstrapFileInput();
  });
  
  function displayAvatarSelectDiv() {
    var updateAvatarDiv = document.getElementById("uploadAvatarDiv");
    if (updateAvatarDiv.style.display == 'none') {
      updateAvatarDiv.style.display = 'block';
    }
  }
</script>

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
      <form:form modelAttribute="registerUser" action="user/register" enctype="multipart/form-data" method="post"
        role="form" class="form-inline col-md-10 go-right"
        style="color: Green; background-color: #FAFAFF; border-radius: 0px 22px 22px 22px;">
        <h2>新用户注册</h2>
        <br />
        <div class="form-group">
          <form:input path="userName" id="userName" name="userName" type="text" class="form-control" placeholder="用户名" />
          <form:label for="userName" path="userName" class="my-form-label" >用户名<span class="glyphicon glyphicon-user"> </span></form:label>
          <form:errors path="userName"></form:errors>
        </div>
        <div class="form-group">
          <form:input path="email" id="email" name="email" type="text" class="form-control" placeholder="用户邮箱" />
          <form:label for="email" path="email" class="my-form-label" >注册邮箱<span class="glyphicon glyphicon-user"> </span>
          </form:label>
          <form:errors path="email"></form:errors>
        </div>
        <div class="form-group">
          <form:select id="sex" path="sex" class="form-control">
            <form:option id="Male" value="0" style="color: red">Male</form:option>
            <form:option id="FeMale" value="1" style="color: green">Female</form:option>
            <form:option id="NotInterested" value="-1" style="color: blue">Not
              interested</form:option>
          </form:select>
        </div>
        <br />
        <br />
        <div class="form-group">
          <form:input path="age" id="age" name="age" type="text" class="form-control" placeholder="年龄" />
          <form:label for="age" path="age" class="my-form-label" >年龄<span class="glyphicon glyphicon-user"> </span>
          </form:label>
          <form:errors path="age"></form:errors>
        </div>
        <div class="form-group">
          <form:input id="birthday" name="birthday" path="birthday" type="date" class="form-control" />
          <form:label for="birthday" path="birthday" class="my-form-label" >DOB<span class="glyphicon glyphicon-calendar"></span>
          </form:label>
        </div>
        <br />
        <br />
        <div class="form-group">
          <form:input path="password" type="password" placeholder="密码" />
          <form:label for="password" path="password" class="my-form-label" >密码：<span class="glyphicon glyphicon-fire"></span></form:label>
          <form:errors path="password" />
        </div>
        <div class="form-group">
          <form:input path="confirmPassword" type="password" placeholder="确认密码" />
          <form:label for="confirmPassword" path="confirmPassword" class="my-form-label" >确认密码：<span class="glyphicon glyphicon-fire"></span></form:label>
          <form:errors path="confirmPassword" />
        </div>
        <br />
        <br />
        <div class="form-group">
          <textarea id="message" name="phone" class="form-control" style="width: 400px; height: 100px"
            placeholder="Short Description"></textarea>
          <label for="message" class="my-form-label" >Short Description <span class="glyphicon glyphicon-align-justify"></span></label>
        </div>
        <br>
        <br>
        <p3>(Enter Pincode/Area to pick your nearest location)<span class="glyphicon glyphicon-map-marker"></span></p3>
        <br>
        <br>
        <div class="form-group" style="width: 600px">
          <input id="autocomplete" name="address" type="tel" onFocus="geolocate()"
            style="moz-border-radius: 22px; border-radius: 7px;"> <label for="address" class="my-form-label" >Location Picker</label>
        </div>
        <br>
        <br>
        <div class="form-group">
          <input id="route" name="route" type="tel" class="form-control" required disabled="true"> <label
            for="route" class="my-form-label" >Route/Locality</label>
        </div>
        <div class="form-group">
          <input id="locality" name="locality" type="tel" class="form-control" required disabled="true"> <label
            for="locality" class="my-form-label" >City/Town</label>
        </div>
        <br />
        <br />
        <div class="form-group">
          <input id="administrative_area_level_2" name="administrative_area_level_2" type="tel" class="form-control"
            required disabled="true"> <label for="administrative_area_level_2" class="my-form-label" >District</label>
        </div>
        <div class="form-group">
          <input id="administrative_area_level_1" name="administrative_area_level_1" type="tel" class="form-control"
            required disabled="true"> <label for="administrative_area_level_1" class="my-form-label" >State</label>
        </div>
        <br />
        <br />
        <div class="form-group">
          <input id="country" name="country" type="text" class="form-control" required disabled="true"> <label
            for="country" class="my-form-label" >Country</label>
        </div>
        <div class="form-group">
          <input id="postal_code" name="postal_code" type="tel" class="form-control" required disabled="true"> <label
            for="postal_code" class="my-form-label" >Pin Code</label>
        </div>
        <br />
        <br />
        <div class="form-group">
          <form:input id="img" type="file" class="btn-info" path="avatarFile" accept="image/*" title="请选择头像图片" onclick="displayAvatarSelectDiv()" />
          <form:errors path="avatarFile" />
        </div>
        <br />
        <br />
        <div id="uploadAvatarDiv" style="display: none;">
          <div class="form-group">
            <div class="container">
              <div class="row">
                <div id="uploadImgDiv" class="col-md-6">
                  <img id="uploadImg" src="" alt="测试预览图片"> <br /> <br />
                  <!-- 加上type="button" ，可以防止点击button的时候自动提交。 -->
                  <button id="cutImgBtn" class="btn-info btn-sm" style="display: none;" value="裁剪" onclick="cutImg()"
                    type="button">裁剪</button>
                  <input type="hidden" name="x1" value="-1"> <input type="hidden" name="y1" value="-1">
                  <input type="hidden" name="x2" value="-1"> <input type="hidden" name="y2" value="-1">
                  <input type="hidden" name="imgWidth" value="-1"> <input type="hidden" name="imgHeight"
                    value="-1">
                </div>
                <label for="previewImgDiv">图片预览</label>
                <div id="previewImgDiv" class="col-md-6">
                  <img id="previewImg" src="" style="position: relative;" />
                </div>
              </div>
            </div>
          </div>
        </div>
        <br />
        <br />
        <div class="container-fluid">
          <div class="row">
            <div class="col-xs-col-1 col-md-col-1">
              <input type="submit" style="color:black;" class="btn-success btn-lg" value="新用户注册" />
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