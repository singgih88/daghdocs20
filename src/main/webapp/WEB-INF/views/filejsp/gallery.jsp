<%@ include file="../include/lib.jsp"%>
<c:if test="${!empty share}">
<!DOCTYPE html>
<html lang="en">
<head>
	<title>${title}</title>
</head>
<script src="./resources/js/jquery-1.8.3.js"></script>
<script src="./resources/js/bootstrap.min.js"></script>
<script src="./resources/js/scriptGallery.js"></script>
<link href="./resources/css/bootstrap.css" rel="stylesheet">
<link href="./resources/css/bootstrap-responsive.css" rel="stylesheet">
<link href="./resources/css/screenGallery.css" rel="stylesheet">
<link href="./resources/css/screen.css" rel="stylesheet">
<body>

    	<div class="navbar navbar-static navbar-inverse navbar-fixed-top">
              <div class="navbar-inner">
                <div style="width: auto;" class="container">
                  <img src="./resources/img/logoLink.png" id="logoLink">
                  <ul class="nav" role="navigation">
	                  <li id="nv-file"><a style="color: white">${title}</a></li>
                  </ul>
                </div>
              </div>
        </div>
<br><br><br>
<table border="0" cellpadding="0" cellspacing="0" width="100%" height="550px">
	<tr>
		<td></td>
		<td align="center" valign="top">
			<div class="viewer">
				 	<div id="stage">
				 				<div style="width: 800px;overflow: hidden;">
				 				<img id="img" src="">
				 				</div>
				 	   		 	<div id="des" style="overflow: hidden;"></div>
				 	   		
				 	   		 <a href="" id="prev"><span class="btn btn-mini" id="prevImg"><i class="icon-step-backward"></i></span></a>
				 	   		 <a href="" id="next"><span class="btn btn-mini" id="nextImg"><i class="icon-step-forward"></i></span></a>						 	   
						 	   
						 	   <div style=" position: absolute; left: 90px;bottom: -90px;" >
						 	   		<div style="float:left; margin-top: 19px;margin-left: 5px;" >
									<a href="javascript:SelectContentb(0)" class="btn btn-mini"><i class="icon-fast-backward"></i></a>
									</div>
									<div style="float: left;margin-right: 5px; margin-top: 19px;" >
									<a href="javascript:MoveItemsLeft()" class="btn btn-mini"><i class=" icon-backward"></i></a>
									</div>
									<div id="items-mask">
										 <div  id="items-bar"></div>
									</div>
									<div style="float:left; margin-top: 19px;margin-left: 5px;" >
									<a href="javascript:MoveItemsRight()" class="btn btn-mini"><i class="icon-forward"></i></a>
									</div>
									<div style="float:left; margin-top: 19px;margin-left: 5px;" >
									<a href="javascript:SelectContentb(${total-1})" class="btn btn-mini"><i class="icon-fast-forward"></i></a>
									</div>
								</div>
				 	</div>
			</div></td>
		<td align="right">
		</td>
	</tr>
</table>

<script>
$(document).ready(function(){
	PageContent('${uuid}');
});
</script>	
</body>
</c:if>	