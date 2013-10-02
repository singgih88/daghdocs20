	<%@ include file="./lib.jsp"%>
		<div style="float: left; width: 100px;margin-top: 8px;">
						
			<c:set var="usedBig" value="${userOs.used/1024}"/>
			<fmt:parseNumber var="num" type="number" integerOnly="true" pattern="#.###" value="${(usedBig*100)/userOs.quota}"/>
			<fmt:parseNumber var="quota" type="number" pattern="#.###" value="${userOs.quota/1000}"/>
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
		
