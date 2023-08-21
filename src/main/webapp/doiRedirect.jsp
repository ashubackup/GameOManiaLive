<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@page import="gen.*"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Game Hub</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
</head>
<body>
	<%
	    String data = request.getQueryString();
	    System.out.println(" redirect data::::"+data);
	    
	    String type= request.getParameter("type");
	    String date = request.getParameter("date");
	    String action = request.getParameter("action");
	    String ani = request.getParameter("msisdn");
	    String network = request.getParameter("network");
	    String guid = request.getParameter("guid");
	    String sub_id = request.getParameter("subscriberid");
	    String ref = request.getParameter("ref");
	    String tag = request.getParameter("tag");
		
		if(ani != null && ani != ""){
			String countyCode = "27";
			if(ani.startsWith("0"))
				ani = ani.substring(1);
			if(ani.startsWith("+"))
				ani = ani.substring(1);
			int len = countyCode.length();
			if(ani.substring(0,len).equals(countyCode))
				ani = ani.substring(len);
			if(ani.contains(" "))
				ani=ani.replace(" ", "");
			System.out.println("Ani :"+ani);
			String status = new DataCollector().getStatus(ani);
			if (status.equalsIgnoreCase("1")) {
				String reDirectURL = "Login?number=" + ani + "";
				response.sendRedirect(reDirectURL);
			} else if(status.equalsIgnoreCase("2")||status.equalsIgnoreCase("0")){
				response.sendRedirect("/Login?number="+ani+"");
			}else{
				response.sendRedirect("landing");
			}
			
		}else{
			response.sendRedirect("landing");
		}
		
	%>

</body>
</html>