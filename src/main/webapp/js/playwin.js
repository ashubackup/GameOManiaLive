
var msisdn,gameencode,gameid,type;
function callme(msisdn,gameencode,gameid,type){
	console.log('test');
	window.msisdn = msisdn;
	window.gameencode = gameencode;
	window.gameid = gameid;
	window.type = type;
	var json = {ani:msisdn,type:type,action:"1"};
	$.ajax({
		type: "POST",	
		url: "/ApiServlet",
		data: json,
		success: function(result){	
		console.log("result:",result);
			var jsonD = JSON.parse(result);
			console.log(jsonD);
			var status = jsonD.status;
			if(status === "1"){
			console.log('status',status);
				window.location.href = "/Logger_PW?ani="+msisdn+"&nick_name="+jsonD.nick_name+"&url="+gameencode+"&type=html&id="+gameid+"&cli_type="+type ;
			}else if(status == "2"){
				$('#myModal').show();
				$('#myModal').removeClass("fade");
				$("#ani").val(msisdn);
			}else{
				alert("Something went wrong");
			}
			
		}
	});
}

function cancel(){
	$('#myModal').hide();
	$('#myModal').addClass("fade");
}

function submit(){
	var msisdn = $("#ani").val();
	var name = $("#name").val();
	if(name === "" || name === undefined){
		alert("Please Enter your Name");
		return;
	}
	var gameencode = window.gameencode;
	var gameid = window.gameid;
	var type = window.type;
	var json = {ani:msisdn,action:"2",type:type,nick_name:name};
	$.ajax({
		type: "POST",	
		url: "/ApiServlet",
		data: json,
		success: function(result){	
			var jsonD = JSON.parse(result);
			console.log(jsonD);
			var status = jsonD.status;
			if(status === "1"){
				window.location.href = "/Logger_PW?ani="+msisdn+"&nick_name="+jsonD.nick_name+"&url="+gameencode+"&type=html&id="+gameid+"&cli_type="+type ;
			}else{
				cancel();
				alert("Something went wrong");
			}
			
		}
	});
}