<%@ include file="../include/include-head.jspf"%>
<legend><fmt:message key="tools"/></legend>

<div class="row">
	<div class="col-md-4">
		<h2><fmt:message key="bookmark.title" /></h2>
		<p><fmt:message key="bookmark.description" /></p>
		<p>
			<a href="javascript:(function(){window.open('${url}?title='+document.title+'&url='+window.location,'','top=10, left=10, width=400, height=450, status=no, menubar=no, toolbar=no scrollbars=no');})();"
			class="btn btn-primary btn-sm"> 
			<span class="glyphicon glyphicon-bookmark glyphicon-white"></span> <fmt:message key="bookmark.tool" /></a>
		</p>
	</div>
</div>
<%@ include file="../include/include-foot.jspf"%>