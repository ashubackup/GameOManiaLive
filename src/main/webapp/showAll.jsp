<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@page import="gen.*"%>
<%@page import="java.sql.*"%>
<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONObject"%>
<!DOCTYPE HTML>

<%
response.setHeader("Cache-Control","no-cache");
response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);

%>

<head>
<jsp:include page="head.jsp" />
</head>
<%
	DataCollector coll = new DataCollector();
OnlineGames online = new OnlineGames();

String catid=request.getParameter("cid");
%>
<body>
	<div id="page-transitions">

		<div class="page-preloader page-preloader-dark">
			<div class="spinner"></div>
		</div>

		<jsp:include page="nav.jsp" />


		<!-- Main Small Icon Sidebar -->
		<div class="sidebar-menu sidebar-dark">
			<jsp:include page="siderbar.jsp" />
		</div>


		<div id="page-content" class="header-clear-large">
			<div id="page-content-scroll">
				<!--Enables this element to be scrolled -->


				<!-- <div class="staff-slider">
					<div class="cus-heading">Games</div>
					<div class="swiper-wrapper">
						<div class="swiper-slide">
							<a
								href="gameurl"
								class="column-center-image"> <img class="col-img-2 img"
								src="images/logo.png" alt="img">
							</a>
							<p class="video_title">GameName</p>
						</div>
					</div>
				</div> -->
				
					 <%
				try{
				ResultSet rs=coll.getCategorie(catid);
				
				if(rs.next()){
					
					String catname=rs.getString("cat_name");
					String id=rs.getString("sr_no");
					
					%>
					
					<div class="staff-slider" >
					<div class="cus-heading"><%=catname %></div>
					
					<div class="swiper-wrapper">
					
					 <% 
                         ResultSet rst=DataCollector.getGamesbyCatid(id);
                         while(rst.next())
                         {
                        	 String name=rst.getString("gamename");
                        	 String url=rst.getString("gameurl");
                        	 String imgurl=rst.getString("imgurl");
                        	 String gameid=rst.getString("gameid");
                        	 %>
                        	 <div class="swiper-slide">
							<a
								href="onlinePlay.jsp?gid=<%=gameid %>"
								class="column-center-image"> <img class="col-img-2 img"
								src="<%=imgurl %>" alt="img">
							</a>
							<p class="video_title"><%=name %></p>
						</div>
                        	 
                        	 <% 
                        
                         }
                       %>
                       
					</div>
					
				</div>
					
					<% 
					}
				
						}catch(Exception e)
				{
					e.printStackTrace();
				}
				
				
				%>


			<jsp:include page="footer.jsp" />
</body>