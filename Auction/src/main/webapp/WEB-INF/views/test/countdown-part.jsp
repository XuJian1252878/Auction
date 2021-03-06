<%@page import="java.util.Date"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../../template/header.jsp"%>

<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Lobster">
<link rel='stylesheet' href='http://fonts.googleapis.com/css?family=Lato:400,700'>
<link rel="stylesheet" href="test/assets/css/style.css">
<script type="text/javascript" src="template/jquery.countdown/dist/jquery.countdown.min.js"></script>

<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->

<!-- Favicon and touch icons -->
<link rel="shortcut icon" href="test/assets/ico/favicon.ico">
<link rel="apple-touch-icon-precomposed" sizes="144x144" href="test/assets/ico/apple-touch-icon-144-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="114x114" href="test/assets/ico/apple-touch-icon-114-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="72x72" href="test/assets/ico/apple-touch-icon-72-precomposed.png">
<link rel="apple-touch-icon-precomposed" href="test/assets/ico/apple-touch-icon-57-precomposed.png">

        <!-- Header -->
        <div class="container">
            <div class="header row">
                <div class="logo span4">
                    <h1><a href="">Alissa</a> <span>.</span></h1>
                </div>
                <div class="call-us span8">
                    <p>Tel: <span>0039 123 45 789</span> | Skype: <span>info@domain.it</span></p>
                </div>
            </div>
        </div>

        <!-- Coming Soon -->
        <div class="coming-soon">
            <div class="inner-bg">
                <div class="container">
                    <div class="row">
                        <div class="span12">
                            <h2>We're Coming Soon</h2>
                            <p>We are working very hard on the new version of our site. It will bring a lot of new features. Stay tuned!</p>
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

        <!-- Content -->
        <div class="container">
            <div class="row">
                <div class="span12 subscribe">
                    <h3>Subscribe to our newsletter</h3>
                    <p>More Templates <a href="http://www.cssmoban.com/" target="_blank" title="模板之家">模板之家</a> Sign up now to our newsletter and you'll be one of the first to know when the site is ready:</p>
                    <form class="form-inline" action="assets/sendmail.php" method="post">
                        <input type="text" name="email" placeholder="Enter your email...">
                        <button type="submit" class="btn">Subscribe</button>
                    </form>
                    <div class="success-message"></div>
                    <div class="error-message"></div>
                </div>
            </div>
            <div class="row">
                <div class="span12 social">
                    <a href="" class="facebook" rel="tooltip" data-placement="top" data-original-title="Facebook"></a>
                    <a href="" class="twitter" rel="tooltip" data-placement="top" data-original-title="Twitter"></a>
                    <a href="" class="dribbble" rel="tooltip" data-placement="top" data-original-title="Dribbble"></a>
                    <a href="" class="googleplus" rel="tooltip" data-placement="top" data-original-title="Google Plus"></a>
                    <a href="" class="pinterest" rel="tooltip" data-placement="top" data-original-title="Pinterest"></a>
                    <a href="" class="flickr" rel="tooltip" data-placement="top" data-original-title="Flickr"></a>
                </div>
            </div>
        </div>

<!-- Javascript -->
<script src="test/assets/js/jquery.backstretch.min.js"></script>
<script src="test/assets/js/scripts.js"></script>

<%@ include file="../../../template/footer.jsp"%>
