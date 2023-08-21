<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Promotion</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

</head>
<body>
<%
String user_agent = request.getHeader("user-agent");
String x_forwarded_for = request.getHeader("x-forwarded-for");
System.out.println("Header user agent:::"+user_agent);
System.out.println("Header x_forwarded_for:::"+x_forwarded_for);
%>
<script>
check();
	
	
 function getUrlParameter(sParam) {
	var sPageURL = window.location.search.substring(1), sURLVariables = sPageURL
			.split('&'), sParameterName, i;
	console.log(sPageURL);
	for (i = 0; i < sURLVariables.length; i++) {
		sParameterName = sURLVariables[i].split('=');

		if (sParameterName[0] === sParam) {
			return sParameterName[1] === undefined ? true
					: decodeURIComponent(sParameterName[1]);
		}
	}
}  
	
	function check(){
		
		console.log('here')
		
		var cidN = getUrlParameter('cid');
		var id = getUrlParameter('id');
		console.log(id)
		
		//var cidN = 'track_20201022070234_ea707c5a_150e_4351_807b_259822de7a8b';
		if(cidN === undefined || cidN === null){
			return;
		}
		var jsonR ={action:'1',cid:cidN,id:id};
		$.ajax({
							type: "POST",
					        url: "ApiServlet",
					        data: jsonR,
					       success:function(result){
					    	   console.log(result);
					    	   	var JsonR = JSON.parse(result);
					    	   	var status = JsonR.status;
					    	   	var guid = JsonR.guid;
					    	   	console.log(guid)
					    	   	if(status == '1'){
					    	   		window.location = "http://sdp.smartcalltech.co.za/Traffic/"+guid+"/?ref="+JsonR.newid;
					    	   	}
								
					       }
					});
				
			}
		
	
	
</script>
</body>
</html>