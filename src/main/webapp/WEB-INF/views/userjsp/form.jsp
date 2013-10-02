<%@ include file="../include/include-head.jspf"%>
<%@ include file="../include/lib.jsp"%>
<legend><fmt:message key="addUser"/></legend>
<form:form method="post" commandName="user" cssClass="form-horizontal">
	<form:hidden path="id" />
	<div class="form-group">
		<form:label path="name" cssClass="col-md-2 control-label"><fmt:message key="name"/></form:label>
		<div class="col-md-4">
			<form:input path="name" cssClass="form-control"/>
			<form:errors path="name" />
		</div>
	</div>
	<div class="form-group">
		<form:label path="surname" cssClass="col-md-2 control-label"><fmt:message key="surname"/></form:label>
		<div class="col-md-4">
			<form:input path="surname" cssClass="form-control"/>
			<form:errors path="surname" />
		</div>
	</div>
	<div class="form-group">
		<form:label path="username" cssClass="col-md-2 control-label"><fmt:message key="username"/></form:label>
		<div class="col-md-4">
			<form:input path="username" cssClass="form-control"/>
			<form:errors path="username" />
		</div>
	</div>
	<div class="form-group">
		<form:label path="password" cssClass="col-md-2 control-label"><fmt:message key="password"/></form:label>
		<div class="col-md-4">
			<form:password path="password" showPassword="true" cssClass="form-control"/>
			<form:errors path="password" />
		</div>
	</div>
	<div class="form-group">
		<form:label path="enabled" cssClass="col-md-2 control-label"><fmt:message key="enabled"/></form:label>
		<div class="col-md-4">
			<form:checkbox path="enabled" value="true" />
		</div>
	</div>
	
	<div class="form-group">
		<form:label path="auth" cssClass="col-md-2 control-label"><fmt:message key="role"/></form:label>
		<div class="col-md-4">
			<form:select path="auth" items="${authList}" multiple="true" cssClass="form-control"/>
		</div>
	</div>
	
	<div class="form-group">
		<form:label path="mail" cssClass="col-md-2 control-label"><fmt:message key="mail"/></form:label>
		<div class="col-md-4">
			<form:input path="mail" cssClass="form-control"/>
			<form:errors path="mail" />
		</div>
	</div>
	
	<div class="form-group">
		<form:label path="quota" cssClass="col-md-2 control-label"><fmt:message key="quota"/></form:label>
		<div class="col-md-4">
			<form:input path="quota" cssClass="form-control"/>
			<form:errors path="quota" />
		</div>
	</div>
	  <div class="form-group">
     	<div class="col-md-offset-2 col-md-10">
		<button class="btn btn-primary"><fmt:message key="submit"/></button>
		<a href="?" class="btn"><fmt:message key="exit"/></a>
		</div>
	</div>
</form:form>

<%@ include file="../include/include-foot.jspf"%>
