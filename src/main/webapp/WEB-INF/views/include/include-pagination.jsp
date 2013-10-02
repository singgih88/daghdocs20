				<c:if test="${contentList.pageCount>1}">
		        <div >
			     <ul class="pagination pagination-sm">
			     	<li><a href="?page&pageid=0">««</a></li>
			    	<li><a href="?prev">«</a></li>
			    		<c:forEach var="row" begin="0" end="${contentList.pageCount-1}">
			    			<li id="nav-${row}" class=""><a href="?page&pageid=${row}">${row+1}</a></li>
			    		</c:forEach>
			    	<li><a href="?next">»</a></li>
			    	<li><a href="?page&pageid=${contentList.pageCount}">»»</a></li>
			     </ul>
			    </div>
			    <script>
			    	$("#nav-${contentList.page}").toggleClass( "active", true );
			    </script>
			    </c:if>
			    <table class="table"><tr><td>
					<small>
						<fmt:message key="show"/> 
							<a href="javascript:itemInPage('5')">5</a> |
							<a href="javascript:itemInPage('10')">10</a> | 
							<a href="javascript:itemInPage('20')">20</a> |
						<fmt:message key="items.pages"/>
					</small>
					</td></tr> 
				</table>
				
				<script>
					function itemInPage(num){
						$.get('../secure/settings?itemInPage&num='+num,
						function(data) {
							//$('.result').html(data);
							//alert('Load was performed.');
							location.reload();
						});
					};
				</script>
				
			<!-- 	$.get('ajax/test.html', function(data) {
					$('.result').html(data);
					alert('Load was performed.');
				}); -->
					