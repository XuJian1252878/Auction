<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../../template/header.jsp"%>

<link href="styles/login.css" rel="stylesheet">
<script src="scripts/login.js"></script>

<div class="container">
  <div class="row">
    <div class="col-md-12">
      <div class="pr-wrap">
        <div class="pass-reset">
          <label> Enter the email you signed up with</label> <input
            type="email" placeholder="Email" /> <input type="submit"
            value="Submit"
            class="pass-reset-submit btn btn-success btn-sm" />
        </div>
      </div>
      <div class="wrap">
        <p class="form-title">Sign In</p>
        <form:form class="login" action="user/login"
          modelAttribute="loginUser" method="post">
          <form:input type="text" placeholder="Username" path="userName" />
          <form:errors path="userName" />
          <form:input type="password" placeholder="Password"
            path="password" />
          <form:errors path="password" />
          <input type="submit" value="Sign In"
            class="btn btn-success btn-sm" />
          <div class="remember-forgot">
            <div class="row">
              <div class="col-md-6 remember-me">
                <input type="checkbox" id="rememberMe" /> <label
                  for="rememberMe">Remember Me</label>
              </div>
              <div class="col-md-6 forgot-pass-content">
                <a href="javascription:void(0)" class="forgot-pass">Forgot
                  Password</a>
              </div>
            </div>
          </div>
        </form:form>
      </div>
    </div>
  </div>
  <div class="posted-by">
    Posted By: <a href="http://www.jquery2dotnet.com">Bhaumik Patel</a>
  </div>
</div>


<%@ include file="../../../template/footer.jsp"%>