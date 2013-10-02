<%@ include file="../include/include-head.jspf"%>
<%@ include file="../include/lib.jsp"%>
<legend><fmt:message key="addUser"/></legend>
<form:form method="post" commandName="contact" cssClass="form-horizontal" id="contactForm">
	<div class="form-group">
		<form:label path="name" cssClass="col-md-2 control-labe"><fmt:message key="name"/></form:label>
		<div class="col-md-4">
			<form:input path="name" cssClass="form-control" />
			<form:errors path="name" />
		</div>
	</div>
	<div class="form-group">
		<form:label path="surname" cssClass="col-md-2 control-labe"><fmt:message key="surname"/></form:label>
		<div class="col-md-4">
			<form:input path="surname" cssClass="form-control" />
			<form:errors path="surname" />
		</div>
	</div>
		
	<div class="form-group">
		<form:label path="mail" cssClass="col-md-2 control-labe"><fmt:message key="mail"/></form:label>
		<div class="col-md-4">
			<form:input path="mail" cssClass="form-control"/>
			<form:errors path="mail" />
		</div>
	</div>
	<div class="form-actions">
		<button class="btn btn-primary"><fmt:message key="submit"/></button>
		<a href="?" class="btn"><fmt:message key="exit"/></a>
	</div>
</form:form>
<%@ include file="../include/include-foot.jspf"%>
