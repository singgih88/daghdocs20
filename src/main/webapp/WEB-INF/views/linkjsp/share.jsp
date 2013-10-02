<%@ include file="../include/lib.jsp"%>
<%@ include file="../include/include-head.jspf"%>
<legend><fmt:message key="share"/></legend>


	<form:form method="post" commandName="message" cssClass="form-horizontal">
		<form:hidden path="uuid" />
		<form:hidden path="feedUrl" />
		<div class="form-group" style="position: relative;">
			<form:label path="to" cssClass="col-md-2 control-label">
			<fmt:message key="to"/>
			</form:label>
			<div class="col-md-4">
				<form:textarea path="to" cssClass="form-control" size="50" id="to"/>
			</div>
			<div class="col-md-1" >
				<a href="#"  id="addressbook"  class="btn btn-link enableTooltip" title="<fmt:message key="addressbook"/>"><i class="addressbook"></i></a>
			</div>
			<select multiple="multiple" id="contact" class="form-control"></select>
		</div>
		<div class="form-group">
			<label class="col-md-2 control-label"><fmt:message key="copyTo"/></label>
			<div class="col-md-4">
			 <input type="checkbox" name="copyTo" value="${userOs.mail}" checked="checked">
			</div>
		</div>
		<div class="form-group">
			<form:label cssClass="col-md-2 control-label" path="subj"><fmt:message key="subj"/></form:label>
			<div class="col-md-4">
				<form:input path="subj" cssClass="form-control" />
			</div>
		</div>
		<div class="form-group">
			<form:label cssClass="col-md-2 control-label" path="body"><fmt:message key="body"/></form:label>
			<div class="col-md-4">
				<form:textarea path="body" cssStyle="width:530px" rows="15" cssClass="form-control"/>
			</div>
		</div>
	  <div class="form-group">
     	<div class="col-md-offset-2 col-md-10">
			<button class="btn btn-primary"><fmt:message key="send"/></button>
			<a href="?" class="btn"><fmt:message key="exit"/></a>
		</div>
		</div>
	</form:form>

<script src="../resources/js/scriptContacts.js"></script>
<%@ include file="../include/include-foot.jspf"%>


