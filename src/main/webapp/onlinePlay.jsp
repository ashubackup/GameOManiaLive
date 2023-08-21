<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="gen.*"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONObject"%>
<!DOCTYPE html>

<%
	response.setHeader("Cache-Control", "no-cache");
response.setHeader("Cache-Control", "no-store");
response.setHeader("Pragma", "no-cache");
response.setDateHeader("Expires", 0);
String ani = request.getParameter("ani");
String status = new DataCollector().getStatus(ani);
// if (ani == null) {
// 	response.sendRedirect("/landing");
// } else if (status.equalsIgnoreCase("2") || status.equalsIgnoreCase("0")) {
// 	response.sendRedirect("/Login?number=" + ani + "");
// }


%>
<%
	OnlineGames online = new OnlineGames();
String gid = request.getParameter("gid");

String gameurl = "",cat_id="",category="",imgurl="";
DataCollector obj = new DataCollector();
ResultSet rs = obj.getGameByGameId(gid);
if(rs.next()){
	gameurl = rs.getString("gameurl");
	cat_id = rs.getString("cat_id");
	category = rs.getString("category");
	imgurl = rs.getString("imgurl");
}

obj.UserJourney(ani,"GamePage",Loader.contentConn);
obj.GamePlay(ani,gameurl,cat_id,category,imgurl,gid,Loader.contentConn);
%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Games</title>
</head>
<body>

	<iframe style="height: 100vh; width: 100%;"
		src="<%=gameurl%>" width="100%"
		height="100vh" frameborder="0" scrolling="no"></iframe>







</body>
</html>