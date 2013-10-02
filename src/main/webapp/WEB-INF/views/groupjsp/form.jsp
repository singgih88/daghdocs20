<%@ include file="../include/include-head.jspf"%>
<%@ include file="../include/lib.jsp"%>

<legend><fmt:message key="addGroup"/></legend>

<form:form method="post" commandName="group" cssClass="form-horizontal">
	<form:hidden path="id" />
	<form:hidden path="type" />
	<form:hidden path="date"/>
	<div class="control-group">
		<form:label cssClass="control-label" path="title"><fmt:message key="title"/></form:label>
		<div class="controls">
			<form:input path="title" cssClass="input-xxlarge" size="50" />
			<form:errors path="title" cssClass="text-error" />
		</div>
	</div>
	<div class="control-group">
		<form:label cssClass="control-label" path="description"><fmt:message key="description"/></form:label>
		<div class="controls">
			<form:textarea path="description" />
			<form:errors path="description" cssClass="text-error" />
		</div>
	</div>
	<div class="control-group">
		<form:label cssClass="control-label" path="share"><fmt:message key="share"/></form:label>
		<div class="controls">
			<form:radiobuttons items="${shareList}" path="share" />
		</div>
	</div>
	<div class="form-actions">
		<button class="btn btn-primary"><fmt:message key="submit"/></button>
		<a href="../secure/${contentListType}?" class="btn"><fmt:message key="exit"/></a>
	</div>
</form:form>
<%@ include file="../include/include-foot.jspf"%>
