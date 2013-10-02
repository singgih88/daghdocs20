 //Contact selector
 $("select").change(function () {
	  var str = "";
	  $("#contact option:selected").each(function () {
	        str = str+ $(this).val()+"; ";
	      });
	  $("#to").val( $("#to").val()+str);
	}).trigger('change');

	$("#clear").click(
			function () {
				$("#to").val("");
			    });

	$("#addressbook").click(
			function () {
				if($("#contact").is(":visible")){
						$("#contact").hide();
					}else{
						$("#contact").show();
					}});

	$.get('../secure/jcontact', 
	function(data) {
		$.each(data, function(i,item){
				var cn="";
				if (item.name!=null){
					cn = item.name+" ";
				}
				if (item.surname!=null){
					cn = cn+item.surname+" ";
				}
				$('#contact').append($("<option />").val(item.mail).text(cn + item.mail));
		});
	}); 





	
