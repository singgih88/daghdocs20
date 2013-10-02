var dataOut;

function PageContent(uuid){
	$.getJSON('./jgallery?uuid='+uuid, function(data) {
		dataOut = data;
		var txt="";
		var refId = dataOut[0].id;
		var arrId = 0
		$.each(data, function(i,item){		
			 var cls ="";
			 txt=txt+'<div class="item"><a href="javascript:SelectContent('+i+')"><img id="img'+item.id+'" class="'+cls+' imgmin" src="./img'+item.path+'?width=70" style="max-height:46px"></a></div>';
		});
		$('#items-bar').append(txt);
		SelectContent(arrId);
		SelectPrevNext(arrId);
		Select(refId);
	});
	
}

function SelectContentb(itemId){
	 var param = itemId;
	 if(param==0){
		 param=2;
	 }
	 $("#items-bar").animate({'left':"-"+((param*100)-200)+"px"},500);
	 SelectContent(itemId);
}
function SelectContent(itemId){
	var dbutton = '<a id="dd" href="" target="_blank" class="btn btn-mini"><i class="icon-download-alt"></i> <b>Download</b></a>';
	SelectPrevNext(itemId);
	id = dataOut[itemId].id;
	img = dataOut[itemId].path;
	des = dataOut[itemId].description;
	
	des =  dbutton+" "+(itemId+1)+"/ "+dataOut.length+" "+des;
	$("#des").html(des);
	$("#dd").attr("href","./file"+img);
	
	$("#img").attr("src","./img"+img+"?height=500");
	var wid= $("#img").width();
	Select(id);	
}

function Select(id){
	$(".selected").attr("class"," imgmin");
	$("#img"+id).attr("class","selected imgmin");
	
	$(".imgmin").fadeTo(0, 0.5);
	$(".selected").fadeTo(0, 1);
}

function SelectPrevNext(itemId){
	var prevId = 0;
	var nextId = dataOut.length;
	 if(itemId-1>0){
		prevId = itemId-1; 
	 }
	 if(itemId+1<dataOut.length){
		nextId = itemId+1; 
	 }
	
	 $("#prev").attr("href",'javascript:SelectContentb('+prevId+')');
	 $("#next").attr("href",'javascript:SelectContentb('+nextId+')');
}

function MoveItemsRight(){
	var pos = $("#items-bar").css('left').replace('px','');
	pos = parseInt(pos) - (100);
	var max = dataOut.length * 100;
	if (pos>-max){
		$("#items-bar").animate({'left':pos+"px"},500);
	}	
}
function MoveItemsLeft(){
	var pos = $("#items-bar").css('left').replace('px','');
	pos = parseInt(pos) + (100);
	if (pos<=0){
	    $("#items-bar").animate({'left':pos+"px"},500);
	}else{
		$("#items-bar").animate({'left':0+"px"},500);	
	}
}