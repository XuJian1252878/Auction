<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../../template/header.jsp"%>

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


<div class="M1">
  <div class="container-fluid">
    <br>
    <br>
    <div class="col-xs-1 col-md-1" style="padding-left: 10px">
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

    <div class="col-xs-4 col-md-6">

      <form role="form" class="form-inline col-md-9 go-right"
        style="color: Green; background-color: #FAFAFF; border-radius: 0px 22px 22px 22px;">
        <h2>Profile</h2>
        <p>Please update your profile for more security.</p>
        <div class="form-group">
          <input id="Firstname" name="Firstname" type="text"
            class="form-control" required> <label
            for="Firstname">First Name <span
            class="glyphicon glyphicon-user"> </span></label>
        </div>
        <div class="form-group">
          <input id="Lastname" name="Lastname" type="text"
            class="form-control" required> <label for="Lastname">Last
            Name <span class="glyphicon glyphicon-user">
          </label>
        </div>
        <div class="form-group">
          <input id="Middlename" name="Middlename" type="text"
            class="form-control" placeholder="Middle Name"> <label
            for="Middlename">Middle Name <span
            class="glyphicon glyphicon-user"></label>
        </div>
        <br> <br>
        <div class="form-group">
          <input id="phone" name="phone" type="tel" class="form-control"
            required> <label for="fphone">Primary Phone
            <span class="glyphicon glyphicon-phone">
          </label>
        </div>
        <div class="form-group">
          <input id="password" name="phone" type="tel"
            class="form-control" placeholder="secondary phone">
          <label for="sphone">Secondary Phone <span
            class="glyphicon glyphicon-phone"></label>
        </div>
        <br>
        <br>
        <div class="form-group">
          <select class="form-control">
            <option id="Male" Value="M" style="color: red" selected>Male</option>
            <option id="FeMale" Value="FM" style="color: green">Female</option>
            <option id="NotInterested" Value="NI" style="color: blue">Not
              interested</option>
          </select>
        </div>

        <div class="form-group">
          <input id="date" name="date" type="date" class="form-control">
          <label for="date">DOB<span
            class="glyphicon glyphicon-calendar"></label>
        </div>
        <br>
        <br>
        <div class="form-group">
          <textarea id="message" name="phone" class="form-control"
            style="width: 400px; height: 100px"
            placeholder="Short Description"></textarea>
          <label for="message">Short Description <span
            class="glyphicon glyphicon-align-justify"></label>
        </div>
        <br>
        <br>
        <div class="form-group">
          <input id="Email1" name="phone" class="form-control"
            style="width: 400px;" placeholder="Registered email">
          </textarea>
          <label for="Email1">Registered email <span
            class="glyphicon glyphicon-align-envelope"></label>
        </div>
        <br>
        <br>
        <div class="form-group">
          <input id="Email2" name="phone" class="form-control"
            style="width: 400px;" placeholder="Alternate email">
          </textarea>
          <label for="Email2">Alternate email <span
            class="glyphicon glyphicon-align-envelope"></label>
        </div>

        <br>
        <br>
        <div class="form-group">
          <input id="Vweb" name="phone" class="form-control"
            style="width: 400px;" placeholder="Website">
          </textarea>
          <label for="Vweb">WebSite <span
            class="glyphicon glyphicon-align-envelope"></label>
        </div>
        <br> <br>
        <p1>Address</p1>
        <br>
        <div class="form-group">
          <input id="Address" name="Address" type="tel"
            class="form-control" required> <label for="Address">Flat
            NO/House No</label>
        </div>
        <div class="form-group">
          <input id="LandMark" name="LandMark" type="text"
            class="form-control" placeHolder="Land Mark"> <label
            for="LandMark">Land Mark</label>
        </div>
        <br>
        <br>
        <p3>(Enter Pincode/Area to pick your nearest location)<span
          class="glyphicon glyphicon-map-marker"></p3>
        <br>
        <br>
        <div class="form-group" style="width: 600px">
          <input input id="autocomplete" name="LocationPicker"
            type="text" onFocus="geolocate()"
            style="moz-border-radius: 22px; border-radius: 7px;">
          <label for="LocationPicker">Location Picker</label>
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
        <br>
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
        <br>
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

        <br>
        <br>
        <button>Save</button>
        <br> <br>
      </form>


    </div>
    <div class="col-xs-1 col-md-1" id="Customer feed"></div>
  </div>
</div>

<%@ include file="../../../template/footer.jsp"%>