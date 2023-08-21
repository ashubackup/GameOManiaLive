<%@page import="java.sql.ResultSet"%>
<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@page import="gen.*"%>
<%@page import="java.util.*"%>
<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONObject"%>
<%
	OnlineGames oG = new OnlineGames();
%>
<div class="sidebar-menu-scroll">	
	<a class="current-menu" href="/"><em>Home</em></a>
	
	<%
	ResultSet rs=new DataCollector().getCategories();
	
	while(rs.next())
	{
		%>
		
		 <a href="showAll.jsp?cid=<%=rs.getString("sr_no")%>">
	 	 <img src="images/icons/terms-and-conditions.png" />
		 <em><%=rs.getString("cat_name") %></em>
	    </a> 
		
		<%
		
	}
	
	%>
	
	
	
	 <a href="termsCondition.html">
	 	 <img src="images/icons/terms-and-conditions.png" />
		 <em>Terms & Conditions</em>
	</a> 
	
	<!-- <a href="/Logout">
		<img src="images/icons/terms-and-conditions.png" />
		<em>Logout</em>
	</a> -->

</div>