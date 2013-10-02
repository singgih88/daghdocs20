
$('.enableTooltip').tooltip({'placement':'bottom'});
$('.enablePopover').popover({'container':'body'});
$('.collapse').collapse();
$('.alert').alert();

setTimeout(function() {
	  $(".alert").fadeTo(500, 0);//.slideUp(500);
	}, 3000);

$('#addFiles').click(function() {
	  $('#addFilesUploader').animate({
	    height: 'toggle'
	  }, 500, function() {
	    // Animation complete.
	  });
	});


 $('#ctl').click(function(){
		$(".chk").each(function(){
			if ($('#ctl').attr('checked')=='checked'){
				$(this).attr('checked','checked');
			}else{
				$(this).attr('checked',false);
			}
		});
	});
 
 function movMultiple(id){
	   $("#step").val("move");
	   $("#father").val(id);
	   document.myform.submit()
 }
 function delMultiple(msg){
	 if(confirm(msg)){
	   $("#step").val("delete");
	   document.myform.submit()
	   }
 }
