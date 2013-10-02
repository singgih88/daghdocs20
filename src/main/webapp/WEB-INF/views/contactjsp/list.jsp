<%@ include file="../include/include-head.jspf"%>
<legend><fmt:message key="contact"/></legend>
<div class="col-md-9">

			<div class="btn-toolbar">
			    <div class="btn-group">
			    <a href="?create" class="btn btn-default btn-sm enableTooltip" title="<fmt:message key="addContact.help"/>">
			    	<span class="glyphicon glyphicon-plus"></span>
					<fmt:message key="addContact"/>
				</a>
			    </div>
			 </div>


	<table class="table table-condensed table-hover table-striped">
		<c:forEach items="${contentList.pageList}" var="row">
			<tr>
				<td class="col-md-8">${row.name} ${row.surname}  (${row.mail}) </td>
				<td class="col-md-2">
					<%@ include file="../include/action.jspf"%>
				</td>
			</tr>
		</c:forEach>
	</table>
<%@ include file="../include/include-pagination.jsp"%>
</div>
<%@ include file="../include/include-foot.jspf"%>