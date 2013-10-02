<%@ include file="../include/include-head.jspf"%>

<section id="properties">
		<legend><fmt:message key="settings"/></legend>

	<form action="?create" method="post" class="form-inline"">
		<div class="form-group">
		<input type="text" value="" name="key" placeholder="<fmt:message key="placeHolderHey"/>" class="form-control">
		</div> 
		<div class="form-group">
		<input type="text" value="" name="value" placeholder="<fmt:message key="placeHolderValue"/>"  class="form-control">
		</div> 
		<button type="submit" class="btn btn-default btn-sm enableTooltip" title="<fmt:message key="addProperty.help"/>"><span class=" glyphicon glyphglyphicon glyphicon-plus"></span> <fmt:message key="addProperty"/></button>
	</form>
	
	
	<form action="?saveList" method="post" class="form-inline">
	<table class="table table-condensed table-striped">
		<c:forEach items="${list}" var="row">
			<tr>
				<td class="col-md-1">${row.key} <input type="hidden" value="${row.id}" name="id${row.id}"></td>
				<td class="col-md-8"><input type="text" value="${row.value}" name="value${row.id}" class="form-control">
				</td>
				<td class="col-md-1">
				<a href="javascript:if(confirm('<fmt:message key="response.delete"/>')){self.document.location.href=('?delete&id=${row.id}');}" class="enableTooltip" title='<fmt:message key="delete"/>'><i class="glyphicon glyphglyphicon glyphicon-remove"></i></a>
				</td>
			</tr>
		</c:forEach>
	</table>
	<div class="form-group">
   						<div class="col-md-offset-8 col-md-2">
						<button class="btn btn-primary"><fmt:message key="submit"/></button>
						</div>
	</div>
	</form>
</section>
<br><br>

<section id="install">
		
			<legend><fmt:message key="restore"/></legend>
		
			 <div class="btn-toolbar">
			    <div class="btn-group">
			      <a href="javascript:if(confirm('<fmt:message key="response.restore"/>')){self.document.location.href=('../secure/copyfiles');}" class="btn btn-default"><i class="glyphicon glyphicon-repeat"></i> <fmt:message key="restoreFiles"/></a>
				</div>
				<div class="btn-group">
			      <a href="javascript:if(confirm('<fmt:message key="response.restore"/>')){self.document.location.href=('?restore');}" class="btn btn-default"><i class="glyphicon glyphicon-repeat"></i> <fmt:message key="restoreSettings"/></a>
				</div>
			 </div>
</section>
<br><br>

<br><br>
<%@ include file="../include/include-foot.jspf"%>