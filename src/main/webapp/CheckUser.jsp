
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>CheckUser</title>
</head>
<body>
<%
try{
    Enumeration headerNames = request.getHeaderNames();
    while(headerNames.hasMoreElements()) {
       String paramName = (String)headerNames.nextElement();
       out.print("<tr><td>" + paramName + "</td>\n");
       String paramValue = request.getHeader(paramName);
       out.println("<td> " + paramValue + "</td></tr>\n");
       
       String Data = paramName+"#"+paramValue;
       System.out.println(Data);
       
       }
	       
    }
    catch (Exception e1) {
	e1.printStackTrace();
	out.print("failed");
}		
%>
</body>
</html>