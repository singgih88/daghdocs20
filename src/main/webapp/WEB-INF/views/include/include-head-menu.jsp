<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
  
  <div class="navbar navbar-inverse navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <img src="../resources/img/logoDocs2.0.png" id="logoLink">
        </div>
        <div class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
           			   <li id="nv-file"><a href="file"><fmt:message key="file"/></a></li>
	                   <li id="nv-link"><a href="link"><fmt:message key="link"/></a></li>
	                   <li id="nv-tools"><a href="tools"><fmt:message key="tools"/></a></li>
	                   <li id="nv-contact"><a href="contact"><fmt:message key="contact"/></a></li> 
 <sec:authorize ifAllGranted="ROLE_ADMIN">
            <li class="dropdown">
              <a data-toggle="dropdown" class="dropdown-toggle" href="#"><fmt:message key="admin"/> <b class="caret"></b></a>
               <ul class="dropdown-menu">
                	<li><a href="user"><i class=" icon-user"></i> <fmt:message key="user"/></a></li>
                	<li><a href="property"><i class="icon-wrench"></i> <fmt:message key="property"/></a></li>
                	<li class="divider"></li>
                	<li><a href="info"><i class="icon-info-sign"></i> <fmt:message key="info"/></a></li>
              	</ul>
            </li>
 </sec:authorize> 
 			<li><%@ include file="./quota.jsp"%></li>
          </ul>
          
          <ul class="nav navbar-nav navbar-right">
				<li class="dropdown">
          			<a data-toggle="dropdown" class="dropdown-toggle" role="button"  href="#">
	                        <span class="glyphicon glyphicon-user glyphicon-white"></span>
						    ${userOs.username} <b class="caret"></b>
						    </a>
          			<ul class="dropdown-menu">
          			 		<li style="text-align: center;">
	                        	<a href="settings?avatar=active">
	                        		<img class="img-thumbnail" src="../secure/img${userOs.avatarPath}?width=70&height=70&type">
	                        	</a>
	                        </li>
	                        <li class="divider"></li>
	                        <li><a href="settings?settings=active"><span class="glyphicon glyphicon-wrench"></span> <fmt:message key="settings"/></a></li>
							<li><a href='<c:url value="/j_spring_security_logout" />'><span class="glyphicon glyphicon-off"></span> <fmt:message key="logout"/></a></li>
          			</ul>
          		</li>          		
          </ul>
        </div><!--/.navbar-collapse -->
      </div>
    </div>
  
  
  
  
    <%--  
    <div class="navbar navbar-static navbar-inverse navbar-fixed-top">
              <div class="navbar-inner">
                <div style="width: auto;" class="container">
                  

                  
                  <ul class="nav pull-right">
                    <li class="dropdown" >
	                        <a data-toggle="dropdown" class="dropdown-toggle" role="button"  href="#">
	                        <i class="icon-user icon-white"></i>
						    ${userOs.username}<b class="caret"></b>
						    </a>
	                      <ul role="menu" class="dropdown-menu">
	                        <li style="text-align: center;">
	                        	<a href="settings?avatar=active">
	                        		<img class="img-polaroid" src="../secure/img${userOs.avatarPath}?width=70&height=70&type">
	                        	</a>
	                        </li>
	                        <li class="divider"></li>
	                        <li><a href="settings?settings=active"><i class="icon-wrench"></i> <fmt:message key="settings"/></a></li>
							<li><a href='<c:url value="/j_spring_security_logout" />'><i class="icon-off"></i> <fmt:message key="logout"/></a></li>
	                      </ul>
                  </li>
                  </ul>
                  
                </div>
              </div> --%>
              
              
        <c:if test="${userOs.locked}">
			<c:set var="cssAlertType" value="error"/>
			<c:set var="alertType" value="quota.exceed" scope="session"/>
	  	</c:if>
				
        </div>
			<div class="container" style="visibility: hidden;" id="my-container-alert">
				<c:set var="cssAlertType" value="success"/>
				<c:if test="${alertType!='success'}">
					<c:set var="cssAlertType" value="error"/>
				</c:if>
	    	    <div class="alert alert-${cssAlertType}">
    				<button type="button" class="close" data-dismiss="alert">&times;</button>
    				<fmt:message key="response.${alertType}"/>
    			</div>
 			</div>
 			<c:if test="${!empty alertType}">
 				<script>
 				  $("#my-container-alert").attr("style","");
 				</script>
 			 	<c:set var="alertType" value="" scope="session"/>
 			</c:if>
 			
 			
    