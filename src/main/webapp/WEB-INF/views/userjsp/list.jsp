<%@ include file="../include/include-head.jspf"%>
<legend><fmt:message key="user"/></legend>
			<div class="btn-toolbar">
			    <div class="btn-group">
			    <a href="?create" class="btn btn-default btn-sm enableTooltip" title="<fmt:message key="addUser.help"/>">
			    	<span class="glyphicon glyphicon-plus"></span>
					<fmt:message key="addUser"/>
				</a>
			    </div>
			 </div>
			 
<div class="col-md-9">
<table class="table table-condensed table-hover table-striped">
	<c:forEach items="${userList}" var="row">
		<tr>
			<td class="col-md-4">${row.username} (${row.mail}) </td>
			<td class="col-md-4">
				<div style="float: left; width: 100px">
					<c:set var="usedBig" value="${row.used/1024}"/>
					<fmt:parseNumber var="num" type="number" integerOnly="true" pattern="#.###" value="${(usedBig*100)/row.quota}"/>
					<fmt:parseNumber var="quota" type="number" pattern="#.###" value="${row.quota/1000}"/>
					<fmt:parseNumber var="used" type="number" integerOnly="true" pattern="#.###" value="${usedBig/1000}"/>
					<div class="progress enablePopover" 
						 data-trigger="hover" 
						 data-placement="bottom" 
						 data-content="Quota : ${quota} Mb Used : ${used} Mb" 
						 data-original-title="Occupation summary">
					  <div class="progress-bar progress-bar-danger" style="width: ${num}%;"></div>
					  <div class="progress-bar progress-bar-success" style="width: ${100-num}%;">${100-num}%</div>
					</div>
				 </div>
			</td>
			<td class="col-md-2">
				<%@ include file="../include/action.jspf"%>
				<a href="?actas&userPrincipal=${row.username}" class="enableTooltip" title="<fmt:message key="actas.help"/>"><i class="icon-user animate"></i></a>
				<a href="?notify&id=${row.id}" class="enableTooltip" title="<fmt:message key="notify.help"/>"><i class="icon-envelope animate"></i></a>
			</td>
		</tr>
	</c:forEach>
</table>
</div>
<%@ include file="../include/include-foot.jspf"%>