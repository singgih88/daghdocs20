<!-- File Jsp -->
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
		   	<a href="../secure/groupFile?create" class="btn btn-default enableTooltip" title="<fmt:message key="addFileGroup.help"/>">
			<span class="glyphicon glyphicon-plus-sign"></span> <fmt:message key="addFileGroup"/></a>
			</div>
        </div>
        
        <!-- Group List Menu -->
		<div class="well sidebar-nav">
			<ul class="nav nav-list">
				<li id="li-grp-0"><a href="?enterGroup&id=0"><span id="icon-grp-0" class="glyphicon glyphicon-folder-close"></span> <fmt:message key="home"/></a></li>
				<c:forEach items="${fileGroupList}" var="row">
					<li id="li-grp-${row.id}" >
						<a href="?enterGroup&id=${row.id}">
							<c:set var="icon" value="glyphicon glyphicon-folder-close" />
							<c:if test="${!empty row.share}">
								<c:set var="icon" value="glyphicon glyphicon-picture" />
							</c:if>
							<span id="icon-grp-${row.id}" class="${icon}"></span> ${row.title}
							<c:if test="${row.child>0}">
							<span class="badge">${row.child}</span>
							</c:if>
						</a> 
						<div class="action">
						<c:set var="prefix" value="groupFile"/>
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
			    	<c:if test="${!userOs.locked}">
					    <a href="#" id="addFiles" class="btn btn-default enableTooltip" title="<fmt:message key="addFiles.help"/>">
				    	<span class=" glyphicon glyphicon-plus"></span>
						<fmt:message key="addFiles"/></a>
					</c:if>
					
					<a href="javascript:delMultiple('<fmt:message key="response.delete"/>');" id="delFiles" class="btn btn-danger btn-sm enableTooltip" title="<fmt:message key="delFiles.help"/>">
			    	<span class="glyphicon glyphicon-trash glyphicon-white"></span>
					<fmt:message key="delFiles"/></a>
					
				</div>
				<div class="btn-group " style="font-size: 11.9px;">
                  	<div class="dropdown" >
		                <a class="btn btn-sm btn-default dropdown-toggle enableTooltip" id="drop4" role="button" data-toggle="dropdown" href="#">
		                <span class="glyphicon glyphicon-move"></span> <fmt:message key="movRecord"/> <b class="caret"></b></a>
		                
		                <ul role="menu1" class="dropdown-menu" role="menu" aria-labelledby="drop4">
					   		 <li ><a href="javascript:movMultiple(0);"><fmt:message key="home"/></a></li>
							 <c:forEach items="${fileGroupList}" var="row">
							 <li><a href="javascript:movMultiple(${row.id});">${row.title}</a></li>
							 </c:forEach>
					  </ul>
		              </div>
				</div>
				
				<c:if test="${(!empty groupId && groupId!=0)}">
			 	<div class="btn-group btn-group-sm">
			    <a href="../feedFile?uuid=${groupUuid}" class="btn btn-warning enablePopover" data-placement="bottom" data-original-title="<fmt:message key="rss.help.title" />" data-trigger="hover" target="_blank" data-content='<fmt:message key="rss.help" />'>
					<span class="glyphicon glyphicon-bookmark glyphicon-white"></span> <fmt:message key="rss.view" />
				</a>
				</div>
				<div class="btn-group btn-group-sm">
				<a href="?share" class="btn btn-info  enablePopover" data-placement="bottom" data-original-title="<fmt:message key="share.help.title" />" data-trigger="hover" data-content='<fmt:message key="share.help" />'>
					<span class="glyphicon glyphicon-envelope glyphicon-white"></span> <fmt:message key="share.view" />
				</a>
				</div>
				<c:if test="${!empty group.share}">
				<div class="btn-group btn-group-sm">
				<a href="../gallery?uuid=${groupUuid}" target="_blank" class="btn btn-default enablePopover" data-placement="bottom" data-original-title="<fmt:message key="share.gallery.title"/>" data-trigger="hover" data-content='<fmt:message key="share.gallery.help" />'>
					<span class="glyphicon glyphicon-picture"></span> <fmt:message key="share.gallery" />
				</a>
				</div>
				</c:if>
				</c:if>
			 </div>
			
			
					
				
						     		 
			 <%@ include file="../include/include-uploader.jspf"%>
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
											<span class="glyphicon glyphicon-file"></span>
											<c:if test="${!empty row.title}">
											${row.title}<br>
											</c:if>
											<a href="#" data-toggle="collapse" data-target="#des${row.id}" />${row.filename}</a>
												<a href="#" data-toggle="collapse" data-target="#des${row.id}" class="enableTooltip" data-placement="right" title='<fmt:message key="details" />'><span class="glyphicon glyphicon-chevron-down"></span></a>
												<div id="des${row.id}" class="collapse in">
													<blockquote>
													<small>
															<a href="${prefix}?update&id=${row.id}">
														    <img src="../secure/img${row.path}?width=70" class="img-thumbnail" align="left" hspace="4"/>
														   
														     <fmt:formatDate  value="${row.date}" /> 
														     </a><br>
														 	 ${row.size} Bytes<br>
															 ${row.description}
															  
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
		
				<script>
				$( ".tomail" ).click(function() {
					$('#mytext').val($('#mytext').attr('value')+","+$(this).attr('value'));
				});
				</script>


<%@ include file="../include/include-foot.jspf"%>

