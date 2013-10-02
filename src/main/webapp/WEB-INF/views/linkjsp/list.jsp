<!-- Link Jsp -->
<%@ include file="../include/lib.jsp"%>
<%@ include file="../include/include-head.jspf"%>



<c:if test="${empty groupId || groupId==0}">
	<c:set var="groupId" value="0"></c:set>
	<c:set var="groupTitle"><fmt:message key="home" /></c:set>
</c:if>

      <div class="row">
        <div class="col-md-3">
        <div class="btn-toolbar">
        	<div class="btn-group btn-group-sm">
		   	<a href="../secure/groupLink?create" class="btn btn-default enableTooltip" title="<fmt:message key="addLinkGroup.help"/>">
			<span class="glyphicon glyphicon-plus-sign"></span> <fmt:message key="addLinkGroup"/>
			</a></div>
        </div>
        
        <!-- Group List Menu -->
		<div class="well sidebar-nav">
			<ul class="nav nav-list">
				<li id="li-grp-0"><a href="../secure/groupLink?enterGroup&id=0"><span id="icon-grp-0" class="glyphicon glyphicon-folder-close"></span> <fmt:message key="home"/></a></li>
				<c:forEach items="${linkGroupList}" var="row">
					<li id="li-grp-${row.id}" >
						<a href="../secure/groupLink?enterGroup&id=${row.id}">
							<span id="icon-grp-${row.id}" class=" glyphicon glyphicon-folder-close"></span> ${row.title}
							<c:if test="${row.child>0}">
							<span class="badge">${row.child}</span>
							</c:if>
						</a> 
						<div class="action">
						<c:set var="prefix" value="groupLink"/>
						<%@ include file="../include/action.jspf"%>
						</div>
					</li>
				</c:forEach>
		   </ul>
		</div>
		<!--End  Group List Menu -->

		</div>
        <div class="col-md-9">
			<div class="btn-toolbar">
			    <div class="btn-group btn-group-sm">
			    <a href="?create" class="btn btn-default enableTooltip" title="<fmt:message key="addLink.help"/>">
			    	<span class=" glyphicon glyphicon-plus"></span>
					<fmt:message key="addLink"/></a>
					
					<a href="javascript:delMultiple('<fmt:message key="response.delete"/>');" id="delLink" class="btn btn-danger btn-small enableTooltip" title="<fmt:message key="delLink.help"/>">
			    	<span class="glyphicon glyphicon-trash glyphicon-white"></span>
					<fmt:message key="delLink"/></a>
				</div>
				<div class="btn-group" style="font-size: 11.9px;">
                  	<div class="dropdown" >
		                <a class="btn btn-sm btn-default dropdown-toggle enableTooltip" id="drop4" role="button" data-toggle="dropdown" href="#">
		                <span class="glyphicon glyphicon-move"></span> <fmt:message key="movRecord"/> <b class="caret"></b></a>
		                
		                <ul class="dropdown-menu" role="menu" aria-labelledby="drop4">
					   		 <li ><a href="javascript:movMultiple(0);"><fmt:message key="home"/></a></li>
							 <c:forEach items="${linkGroupList}" var="row">
							 <li><a href="javascript:movMultiple(${row.id});">${row.title}</a></li>
							 </c:forEach>
					  </ul>
		              </div>
				</div>
				
				<c:if test="${(!empty groupId && groupId!=0)}">
			 	<div class="btn-group btn-group-sm">
			    <a href="../feedLink?uuid=${groupUuid}" class="btn btn-warning enablePopover" data-placement="bottom" data-original-title="<fmt:message key="rss.help.title" />" data-trigger="hover" target="_blank" data-content='<fmt:message key="rss.help" />'>
					<span class="glyphicon glyphicon-bookmark glyphicon-white"></span> <fmt:message key="rss.view" />
				</a>
				</div>
				<div class="btn-group btn-group-sm">
				<a href="?share" class="btn btn-info enablePopover" data-placement="bottom" data-original-title="<fmt:message key="share.help.title" />" data-trigger="hover" data-content='<fmt:message key="share.help" />'>
					<span class="glyphicon glyphicon-envelope glyphicon-white"></span> <fmt:message key="share.view" />
				</a>
				</div>
				</c:if>
			 </div>
			 
			 				
			 				<form action="?multiple" method="post" name="myform">
			 				<input type="hidden" id="step" name="step" value="" >
			 				<input type="hidden" id="father" name="father" value="" >
							<table class="table table-condensed table-hover table-striped">
							 <thead>
									<tr>
										<th class="col-md-8"><input type="checkbox" name="ctl" id="ctl"></th>
										<th class="col-md-1"></th>
									</tr>
							 </thead>	
								<c:forEach items="${contentList.pageList}" var="row">
									<tr>
										<td class="col-md-8">
											<input type="checkbox" name="chk" id="chk${row.id}" class="chk" value="${row.id}">
											<span class="glyphicon glyphicon-bookmark"></span>
											
											
												<a href="#" data-toggle="collapse" data-target="#des${row.id}" class="enableTooltip" title='<fmt:message key="details" />'>
												
												<c:choose>
													<c:when test="${!empty row.title}">${row.title}</c:when>
													<c:otherwise>${row.link}</c:otherwise>
												</c:choose>
												<span class="glyphicon glyphicon-chevron-down"></span>
												</a>
												
												<div id="des${row.id}" class="collapse in">
													<blockquote>
													<small>
													<h5><a href="${row.link}" target="_blank">${row.link}</a><h5>
													<fmt:formatDate  value="${row.date}" />
													<c:if test="${! empty row.description}">
														<br>${row.description}
													</c:if>
													</small>
													</blockquote>
												</div>
										</td>
										<td class="col-md-1">
											<%@ include file="../include/action.jspf"%>
										</td>
									</tr>
								</c:forEach>
							</table>
        					</form>
			    <%@ include file="../include/include-pagination.jsp"%>
	</div>
</div>
  

		<script>
		$("#icon-grp-${groupId}").toggleClass( "glyphicon glyphicon-folder-open", true );
		$("#li-grp-${groupId}").toggleClass( "active", true );
		</script>


<%@ include file="../include/include-foot.jspf"%>

