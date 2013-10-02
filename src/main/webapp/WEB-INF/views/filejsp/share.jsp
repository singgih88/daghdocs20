<%@ include file="../include/lib.jsp"%>
<%@ include file="../include/include-head.jspf"%>
<legend><fmt:message key="share"/></legend>


	<form:form method="post" commandName="message" cssClass="form-horizontal">
		<form:hidden path="uuid" />
		<form:hidden path="feedUrl" />
		<div class="control-group">
			<form:label path="to" cssClass="control-label">
			<fmt:message key="to"/>
			</form:label>
			
			<div class="controls" style="position: relative;">
				<form:textarea path="to" cssClass="input-xxlarge" size="50" id="to"/> 
				<%-- <i class="icon-remove-sign enableTooltip" id="clear" title="<fmt:message key="reset"/>" style="cursor: pointer;"></i> --%>
				<a href="#"  id="addressbook"  class="btn btn-link enableTooltip" title="<fmt:message key="addressbook"/>"><i class="addressbook"></i></a>
				<select multiple="multiple" id="contact">
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><fmt:message key="copyTo"/></label>
			<div class="controls">
			 <input type="checkbox" name="copyTo" value="${userOs.mail}" checked="checked">
			</div>
		</div>
		<div class="control-group">
			<form:label cssClass="control-label" path="subj"><fmt:message key="subj"/></form:label>
			<div class="controls">
				<form:input path="subj" cssClass="input-xxlarge" />
			</div>
		</div>
		<div class="control-group">
			<form:label cssClass="control-label" path="body"><fmt:message key="body"/></form:label>
			<div class="controls">
				<form:textarea path="body" cssStyle="width:530px" rows="15" />
			</div>
		</div>
		<div class="form-actions">
			<button class="btn btn-primary"><fmt:message key="send"/></button>
			<a href="?" class="btn"><fmt:message key="exit"/></a>
		</div>
	</form:form>

<script src="../resources/js/scriptContacts.js"></script>
<%@ include file="../include/include-foot.jspf"%>

