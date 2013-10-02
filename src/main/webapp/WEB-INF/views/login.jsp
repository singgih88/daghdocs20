<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="messages"/>
<html>
<head>
	<title>link</title>
</head>
<link href="./resources/css/bootstrap.css" rel="stylesheet">
<link href="./resources/css/bootstrap-responsive.css" rel="stylesheet">
<link href="./resources/css/screen.css" rel="stylesheet">
<script src="./resources/js/jquery.js"></script>
<script src="./resources/js/bootstrap.min.js"></script>
	<!-- <script src="./resources/css/tooltip.js"></script> -->
 <style type="text/css">
      body {
        padding-top: 40px;
        padding-bottom: 40px;
        background-color: #f5f5f5;
      }

      .form-signin {
        max-width: 300px;
        padding: 19px 29px 29px;
        margin: 0 auto 20px;
        background-color: #fff;
        border: 1px solid #e5e5e5;
        -webkit-border-radius: 5px;
           -moz-border-radius: 5px;
                border-radius: 5px;
        -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.05);
           -moz-box-shadow: 0 1px 2px rgba(0,0,0,.05);
                box-shadow: 0 1px 2px rgba(0,0,0,.05);
      }
      .form-signin .form-signin-heading,
      .form-signin .checkbox {
        margin-bottom: 10px;
      }
      .form-signin input[type="text"],
      .form-signin input[type="password"] {
        font-size: 16px;
        height: auto;
        margin-bottom: 15px;
        padding: 7px 9px;
      }
      .alert{
      	margin-bottom: 0px;
      }
     

    </style>
<body>

<!--  -->
 <div class="navbar navbar-static navbar-inverse navbar-fixed-top">
              <div class="navbar-inner">
                <div style="width: auto;" class="container">
                  <img src="./resources/img/logoDocs2.0.png" id="logoLink">
                </div>
              </div>
        </div>
    


<!--  -->

<br><br>
<div class="container form-signin">
 		<form action="<c:url value='j_spring_security_check' />" method='POST' id="loginForm">
        <input type="text" class="form-control" placeholder="<fmt:message key="username"/>" name='j_username' >
        <input type="password" class="form-control" placeholder="<fmt:message key="password"/>" name='j_password' >
        <label class="checkbox">
          <input type="checkbox" value="true" name='_spring_security_remember_me' id="_spring_security_remember_me" checked> <fmt:message key="remember.me"/>
        </label>
        <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="login" /></button>
        <a href="javascript:showRetrieve()">
        <fmt:message key="retrieve.password"/>
        </a>
      	</form>


		<div id="retrieve" style="display: none;">
			 <input type="text" class="form-control" placeholder="<fmt:message key="retrieve.help"/>" id='mail' >
			 <button class="btn btn-lg btn-primary btn-block" onclick="javascript:execRetrieve()"><fmt:message key="send" /></button>
			  <a href="javascript:showLogin()">
			  <fmt:message key="login"/>
			  </a>
		</div>
</div>

<div class="form-signin" id="answer" style="display: none;">
	<div class="alert alert-success">
		<fmt:message key="retrieve.send" />
	</div>
</div>


<br><br><br>
	<c:if test="${not empty error}">
			<fmt:message key="login.error"/>
			${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
	</c:if>

<script type="text/javascript">
	function showRetrieve(){
		$('#retrieve').show();
		$('#loginForm').hide();
	}
	function showLogin(){
		$('#loginForm').show();
		$('#retrieve').hide();
	}
	
	function execRetrieve(){
		$.get('retrieve?mail='+$("#mail").val());
		$('#answer').show();
	};
</script>
</body>
</html>