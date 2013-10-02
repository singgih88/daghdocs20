<%@ include file="../include/include-head.jspf"%>
<%@ include file="../include/lib.jsp"%>

<c:set var="param.settings" value="active" scope="request"/>
<ul class="nav nav-tabs" id="myTab">
  <li class="${param.settings}" ><a href="#settings"><fmt:message key="settings"/></a></li>
  <li class="${param.avatar}"><a href="#avatar"><fmt:message key="avatar"/></a></li>
  <li class="${param.share}"><a href="#share"><fmt:message key="share.agent"/></a></li>
  <li class="${param.imap}"><a href="#imap"><fmt:message key="imap.agent"/></a></li>
</ul>
<br>

<script>
  /* $(function () {
    $('#myTab a:first').tab('show');
  }) */
  
  $('#myTab a').click(function (e) {
  e.preventDefault();
  $(this).tab('show');
})
</script>

<div class="tab-content">
			<div class="tab-pane ${param.avatar}" id="avatar">
			<form action="?updateAvatar" method="post" enctype="multipart/form-data" class="form-horizontal" > 
				<div class="form-group">
					<label class="col-md-2 control-label"><fmt:message key="avatar"/></label>
					<div class="col-md-4">
					<img src="../secure/img${user.avatarPath}?width=140&type" class="img-thumbnail"><br>
					<input class="default" type="file" id="file4" name="file4" size="50"/><br>
					</div>
					<div class="form-group">
   						<div class="col-md-offset-2 col-md-10">
						<input type="submit" value="Upload" class="btn btn-primary"/>
						<a href="?deleteAvatar" class="btn btn-danger"><fmt:message key="delete"/></a>
						</div>
					</div>
				</div>
			</form>
			</div>
			
			<div class="tab-pane ${param.settings}" id="settings">
					<form:form action="?updateSettings" method="post" commandName="user" cssClass="form-horizontal" >
						<form:hidden path="id" />
						<form:hidden path="avatarPath" />
						<div class="form-group">
							<form:label path="name" cssClass="col-md-2 control-label"><fmt:message key="name"/></form:label>
							<div class="col-md-4">
								<form:input path="name" cssClass="form-control"/>
								<form:errors path="name" />
							</div>
						</div>
						<div class="form-group">
							<form:label path="surname" cssClass="col-md-2 control-label"><fmt:message key="surname"/></form:label>
							<div class="col-md-4">
								<form:input path="surname" cssClass="form-control"/>
								<form:errors path="surname" />
							</div>
						</div>
						<div class="form-group">
							<form:label path="username" cssClass="col-md-2 col-md-2 control-label"><fmt:message key="username"/></form:label>
							<div class="col-md-4">
								<form:input path="username" readonly="true" cssClass="form-control"/>
								<form:errors path="username" />
							</div>
						</div>
						<div class="form-group">
							<form:label path="password" cssClass="col-md-2 col-md-2 control-label"><fmt:message key="password"/></form:label>
							<div class="col-md-4">
								<form:password showPassword="true" path="password" cssClass="form-control"/>
								<form:errors path="password" />
							</div>
						</div>
						<div class="form-group">
							<form:label path="mail" cssClass="col-md-2 control-label"><fmt:message key="mail"/></form:label>
							<div class="col-md-4">
								<form:input path="mail" readonly="true" cssClass="form-control"/>
								<form:errors path="mail" />
							</div>
						</div>
						  <div class="form-group">
    						<div class="col-md-offset-2 col-md-10">
							<button class="btn btn-primary"><fmt:message key="submit"/></button>
						  </div>
						</div>
					</form:form>
			</div>
			
			<div class="tab-pane ${param.share}" id="share">
		<!-- 	<a name="share-agent"></a> --> 
			<form action="?updateShare" method="post" class="form-horizontal" > 
					<div class="form-group">
								<label class="col-md-2 control-label"><fmt:message key="subj"/></label>
								<div class="col-md-4">
									<input name="share-subj" type="text" value="${sharesubj}" class="form-control" />
								</div>
					</div>
					<div class="form-group">
								<label class="col-md-2 control-label"><fmt:message key="sign"/></label>
								<div class="col-md-4">
									<textarea rows="" cols="" name="share-sign" class="form-control">${sharesign}</textarea>
								</div>
					</div>
					<div class="form-group">
   						<div class="col-md-offset-2 col-md-10">
							<button class="btn btn-primary"><fmt:message key="submit"/></button>
						</div>
					</div>
			</form>
			</div>
			
			<c:if test="${!userOs.locked}">
			<div class="tab-pane ${param.imap}" id="imap">
			<a name="imap-agent"></a> 
			<form action="?updateImap" method="post" class="form-horizontal" > 
					<div class="form-group">
								<label class="col-md-2 control-label"><fmt:message key="imap.enable"/></label>
								<div class="col-md-4">
									<input name="imap-checkbox" type="checkbox" value="true" ${imapcheck} />
								</div>
					</div>
					<div class="form-group">
								<label class="col-md-2 control-label"><fmt:message key="host"/></label>
								<div class="col-md-4">
									<input name="imap-host" type="text" value="${imaphost}" class="form-control" />
								</div>
					</div>
					<div class="form-group">
								<label class="col-md-2 control-label"><fmt:message key="user"/></label>
								<div class="col-md-4">
									<input name="imap-user" type="text" value="${imapuser}" class="form-control" />
								</div>
					</div>
					<div class="form-group">
								<label class="col-md-2 control-label"><fmt:message key="password"/></label>
								<div class="col-md-4">
									<input name="imap-password" type="password" value="${imappass}" class="form-control" />
								</div>
					</div>
					<div class="form-group">
								<label class="col-md-2 control-label"><fmt:message key="folder"/></label>
								<div class="col-md-4">
									<input name="imap-folder" type="text" value="${imapfolder}" class="form-control" />
								</div>
					</div>
					<div class="form-group">
								<label class="col-md-2 control-label"><fmt:message key="imap.delete"/></label>
								<div class="col-md-4">
									<input name="imap-delete" type="checkbox" value="true" ${imapdelete} />
								</div><br>
								<fmt:message key="imap.delete.default"/>
					</div>
				<div class="form-group">
   						<div class="col-md-offset-2 col-md-10">
							<button class="btn btn-primary"><fmt:message key="submit"/></button>
						</div>
				</div>
				</form>
				</div>
			</c:if>

</div>
 

<%@ include file="../include/include-foot.jspf"%>
