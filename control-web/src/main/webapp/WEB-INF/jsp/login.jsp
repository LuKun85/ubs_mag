<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>管理控台</title>
  <script src="${pageContext.request.contextPath}/resources/BJUI/js/jquery-1.11.3.min.js"></script>
  <script src="${pageContext.request.contextPath}/resources/BJUI/js/jquery.cookie.js"></script>
  <link href="${pageContext.request.contextPath}/resources/css/login.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/resources/BJUI/themes/css/bootstrap.min.css" rel="stylesheet">
  <style type="text/css">
    * {font-family: "Verdana", "Tahoma", "Lucida Grande", "Microsoft YaHei", "Hiragino Sans GB", sans-serif;}
    body {
      background: url(${pageContext.request.contextPath}/resources/assets/img/bg.jpg) no-repeat center center fixed;
      -webkit-background-size: cover;
      -moz-background-size: cover;
      -o-background-size: cover;
      background-size: cover;
    }
    a:link {color: #285e8e;}
    .main_box {
      position: absolute; top:50%; left:50%; margin-top:-260px; margin-left: -300px; padding: 30px; width:600px; height:460px;
      background: #FAFAFA; background: rgba(255,255,255,0.5); border: 1px #DDD solid;
      border-radius: 5px;
      -webkit-box-shadow: 1px 5px 8px #888888; -moz-box-shadow: 1px 5px 8px #888888; box-shadow: 1px 5px 8px #888888;
    }
    .main_box .setting {position: absolute; top: 5px; right: 10px; width: 10px; height: 10px;}
    .main_box .setting a {color: #FF6600;}
    .main_box .setting a:hover {color: #555;}
    .login_logo {margin-bottom: 20px; height: 45px; text-align: center;}
    .login_logo img {height: 45px;}
    .login_msg {text-align: center; font-size: 16px;}
    .login_form {padding-top: 20px; font-size: 16px;}
    .login_box .form-control {display: inline-block; *display: inline; zoom: 1; width: auto; font-size: 18px;}
    .login_box .form-control.x319 {width: 319px;}
    .login_box .form-control.x164 {width: 164px;}
    .login_box .form-group {margin-bottom: 20px;}
    .login_box .form-group label.t {width: 120px; text-align: right; cursor: pointer;}
    .login_box .form-group.space {padding-top: 15px; border-top: 1px #FFF dotted;}
    .login_box .form-group img {margin-top: 1px; height: 32px; vertical-align: top;}
    .login_box .m {cursor: pointer;}
    .bottom {text-align: center; font-size: 12px;}
  </style>


  <script type="text/javascript">
    var COOKIE_NAME = 'sys__username';
    $(function() {
      choose_bg();
      //changeCode();
      if ($.cookie(COOKIE_NAME)){
        $("#j_username").val($.cookie(COOKIE_NAME));
        $("#j_password").focus();
        $("#j_remember").attr('checked', true);
      } else {
        $("#j_username").focus();
      }
      $("#captcha_img").click(function(){
        changeCode();
      });
      $("#login_form").submit(function(){
        var issubmit = true;
        var i_index  = 0;
        $(this).find('.in').each(function(i){
          if ($.trim($(this).val()).length == 0) {
            $(this).css('border', '1px #ff0000 solid');
            issubmit = false;
            if (i_index == 0)
              i_index  = i;
          }
        });
        if (!issubmit) {
          $(this).find('.in').eq(i_index).focus();
          return false;
        }
        var $remember = $("#j_remember");
        if ($remember.attr('checked')) {
          $.cookie(COOKIE_NAME, $("#j_username").val(), { path: '/', expires: 15 });
        } else {
          $.cookie(COOKIE_NAME, null, { path: '/' });  //删除cookie
        }
        $("#login_ok").attr("disabled", true).val('登陆中..');

        return true;
      });
    });
    function genTimestamp(){
      var time = new Date();
      return time.getTime();
    }
    function changeCode(){
      $("#captcha_img").attr("src", "${pageContext.request.contextPath}/image/captcha-image?t="+genTimestamp());
    }
    function choose_bg() {
      var bg = Math.floor(Math.random() * 9 + 1);
      <%--$('body').css('background-image', 'url(${pageContext.request.contextPath}/resources/image/loginbg_0'+ bg +'.jpg)');--%>
      <%--$('body').css('background-image', 'url(${pageContext.request.contextPath}/resources/assets/img/bg' + bg + '.jpg)');--%>
      $('body').css('background-image', 'url(${pageContext.request.contextPath}/resources/assets/img/bg9.jpg)');
    }

    function getLoginName() {
      var loginName = $("#j_username").val();
      var hrefs = "${pageContext.request.contextPath}/email/send?loginName="+loginName;
      $("#findPwd").attr("href",hrefs);
    }

  </script>
</head>
<body>
<div class="main_box">
  <div class="setting"><a href="javascript:;" onclick="choose_bg();" title="更换背景"><span class="glyphicon glyphicon-th-large"></span></a></div>
  <div class="login_box">
    <div class="login_logo">
      <img src="${pageContext.request.contextPath}/resources/image/logo.png" >
    </div>

		<c:if test="${!empty message}">
			<div class="login_msg">
	      		<font color="red">${message }</font>
	    	</div>
	    </c:if>

    <div class="login_form">
      <input type="hidden" value="${randomKey }" id="j_randomKey" />
      <form action="${pageContext.request.contextPath}/login" id="login_form" method="post">
        <span style="color:red;margin: auto auto;">${error.message}</span>
        <input type="hidden" name="token" value="${token}" />
        <div class="form-group">
          <label for="j_username" class="t">用户名：</label> <input id="j_username" value="" name="username" onblur="getLoginName()" type="text" class="form-control x319 in" autocomplete="off">
        </div>
        <div class="form-group">
          <label for="j_password" class="t">密　码：</label> <input id="j_password" value="" name="password" type="password" class="form-control x319 in">
        </div>
        <div class="form-group">
          <label for="j_captcha" class="t">验证码：</label> <input id="j_captcha" name="captcha" type="text" class="form-control x164 in">
          <img id="captcha_img" alt="点击更换" title="点击更换" src="${pageContext.request.contextPath}/image/captcha-image" class="m">
        </div>
        <div class="form-group">
          <label class="t"></label>
          <label for="j_remember" class="m"><input id="j_remember" name="remember" type="checkbox" value="true">&nbsp;记住登陆账号!</label>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <a id="findPwd" href="${pageContext.request.contextPath}/email/send" title="点击找回密码"><label class="t">忘记密码？</label></a>
        </div>
        <div class="form-group space">
          <label class="t"></label>　　　
          <input type="submit" id="login_ok" value="&nbsp;登&nbsp;录&nbsp;" class="btn btn-primary btn-lg">&nbsp;&nbsp;&nbsp;&nbsp;
          <input type="reset" value="&nbsp;重&nbsp;置&nbsp;" class="btn btn-default btn-lg">
        </div>
      </form>
    </div>
  </div>
  <div class="bottom">Copyright &copy; 2012 - 2016　<a href="http://www.77pay.com.cn" target="_blank">上海申鑫电子支付股份有限公司</a></div>
</div>
</body>
</html>