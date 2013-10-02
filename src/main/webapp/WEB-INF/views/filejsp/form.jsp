<%@ include file="../include/include-head.jspf"%>
<%@ include file="../include/lib.jsp"%>
<legend><fmt:message key="addFile"/></legend>
<form:form method="post" commandName="fileBean" cssClass="form-horizontal">

			<form:hidden path="id" />
			<form:hidden path="date"/>
			<form:hidden path="uuid"/>
			<form:hidden path="path"/>
			<div class="form-group">
					<label for="file" class="col-md-2 control-label" ><fmt:message key="file"/></label>
					<div class="col-md-4">
					<a href="../file${fileBean.path}" target="_blank">
						<img src="../secure/img${fileBean.path}?height=500" class="img-thumbnail" style="max-width: 600px">
					</a>
					</div>
			</div> 
			<div class="form-group" style="${fatherStatus}">
				<form:label cssClass="col-md-2 control-label" path="father"><fmt:message key="father"/></form:label>
				<div class="col-md-4">
					<form:select items="${fileGroupList}" path="father" cssClass="form-control" />
				</div>
			</div>
			<div class="form-group">
				<form:label cssClass="col-md-2 control-label" path="description"><fmt:message key="description"/></form:label>
				<div class="col-md-4">
					<form:textarea path="description" cssClass="form-control"/>
					<form:errors path="description" cssClass="text-error" />
				</div>
			</div>
			<div class="form-group">
			<label for="<fmt:message key="count"/>" class="col-md-2 control-label" ><fmt:message key="count"/></label>
				<div class="col-md-4">
					${count} &nbsp; 
					<a data-toggle="collapse" data-target="#las10" style="cursor: pointer;" /><fmt:message key="last10"/></a>
					<div id="las10" class="collapse in">
					<c:forEach items="${listDownload}" var="row" begin="0" end="10">
						<br><fmt:formatDate value="${row.date}" pattern="yyyy-MM-dd"/> - ${row.ip}
					</c:forEach>
					</div>					
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