<%@ include file="../include/lib.jsp"%>
<%@ include file="../include/include-head-html.jspf" %>
<body>
<legend><fmt:message key="bookmark.tool"/></legend>
	<div class="container" style="min-height: 500px;">
<form:form method="post" action="?create" commandName="link" cssClass="form-horizontal">
	<form:hidden path="id" />
	<form:hidden path="type" />
	<form:hidden path="date"/>
	<div class="form-group">
		<form:label cssClass="col-md-2 control-label" path="title"><fmt:message key="title"/></form:label>
		<div class="col-md-4">
			<form:input path="title"  cssClass="form-control" />
			<form:errors path="title" cssClass="text-error" />
		</div>
	</div>
	<div class="form-group" style="${urlStatus}">
		<form:label cssClass="col-md-2 control-label" path="link"><fmt:message key="link"/></form:label>
		<div class="col-md-4">
			<form:input path="link"  cssClass="form-control" />
			<form:errors path="link" cssClass="text-error" />
		</div>
	</div>
	<div class="form-group" style="${fatherStatus}">
		<form:label cssClass="col-md-2 control-label" path="father"><fmt:message key="father"/></form:label>
		<div class="col-md-4">
			<form:select items="${linkGroupList}" path="father"  cssClass="form-control" />
		</div>
	</div>
	<div class="form-group">
		<form:label cssClass="col-md-2 control-label" path="description"><fmt:message key="description"/></form:label>
		<div class="col-md-4">
			<form:textarea path="description"  cssClass="form-control"/>
			<form:errors path="description" cssClass="text-error" />
		</div>
	</div>
	  <div class="form-group">
     	<div class="col-md-offset-2 col-md-10">
		<button class="btn btn-primary"><fmt:message key="submit"/></button>
		<a href="javascript:window.close();" class="btn"><fmt:message key="exit"/></a>
			</div>
	</div>
</form:form>
</div>
	<c:if test="${close}">
		<script>
			window.close();
		</script>
	</c:if>