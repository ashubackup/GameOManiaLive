<%@page import="gen.DataCollector"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
 <%@page import="gen.*"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.*"%>
<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONObject"%>

<%
  Enumeration headerNames = request.getHeaderNames();
  String paramName = (String)headerNames.nextElement();
  String paramValue = request.getHeader(paramName);
  String Data = paramName+"#"+paramValue;
  System.out.println("Header data:::"+Data);
  
  String os = request.getHeader("user-agent");
  System.out.println("os::"+os);

response.setHeader("Cache-Control","no-cache");
response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", 0);
	String ani = request.getParameter("ani");
	System.out.println("ani on online:::"+ani);
  String status = new DataCollector().getStatus(ani);
  // If want to run in local comment if else block 
  // otherwise it will redirect to the live url
  
  /* if (ani == null) {
 	response.sendRedirect("http://sdp.smartcalltech.co.za/Traffic/df3846a6-8048-4a1c-a9f2-99cb2f8f99f2/");
 	return;
 } else if (status.equalsIgnoreCase("0")) {
 	response.sendRedirect("Login?number=" + ani + "&type=onnet");
 	return;
 } */   
  DataCollector obj = new DataCollector();
  obj.MobileLogs(ani,os,Loader.contentConn);
  obj.UserJourney(ani,"home",Loader.contentConn);
%>

<!DOCTYPE HTML>

<head>
<jsp:include page="head.jsp" />

</head>
<%
	DataCollector coll = new DataCollector();
	OnlineGames online = new OnlineGames();
List<String> catArray = new ArrayList<String>();
%>
<body>
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
			<div class="container">
			
				<div class="bigslides">
				
				<h3 class="cus-heading">Highlights</h3>
					<div class="swiper-container cusslides">
						<div class="swiper-wrapper">
							<%
							try{
								ResultSet res3 = coll.getHighlightGames();
							while (res3.next()) {
// 								String gameurl = res3.getString("gameurl");
// 								String type = res3.getString("status");
								String gameid = res3.getString("gameid");
// 								String cat_id = res3.getString("cat_id");
							%>
							<div class="swiper-slide">
								<%-- <a  href="<%="/OnlineLogger?ani=" + ani + "&url=" + gameurl + "&type=" + type + "&gameid=" + gameid
									+ "&cat_id=" + cat_id + ""%>"
									
									class="column-center-image"> <img class="col-img-2 img"
									src="<%=res3.getString("banner_url")%>" alt="img">
								</a> --%>
								
								<a  href="onlinePlay.jsp?gid=<%=gameid %>&ani=<%=ani %>"
									
									class="column-center-image"> <img class="col-img-2 img"
									src="<%=res3.getString("imgurl")%>" alt="img">
								</a>
								<p class="video_title"><%=res3.getString("gamename")%></p>
							</div>
							<%
								}
							res3.close();
							}catch(Exception e)
							{
								e.printStackTrace();
							}
							%>
						</div>
						<!-- Add Pagination -->
						<div class="swiper-pagination "></div>
					</div>
				</div>



		 <%
				try{
				ResultSet rs=coll.getCategories();
				
				while(rs.next()){
					
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
								href="onlinePlay.jsp?gid=<%=gameid %>&ani=<%=ani %>"
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

				
				<!-- <div class="staff-slider" >
					<div class="cus-heading">Cat Name</div>
					
					<div class="swiper-wrapper">

						<div class="swiper-slide">
							<a
								href="gameURl"
								class="column-center-image"> <img class="col-img-2 img"
								src="images/logo.png" alt="img">
							</a>
							<p class="video_title">GameName</p>
						</div>
						
						
						
					</div>
					
				</div> -->
				
				
				
				<jsp:include page="footer.jsp" />
			
			<script type="text/javascript">
			$(document).ready(
				    function()
				    {
				        var pagebytes = $('html').html().length;
				        var kbytes = pagebytes / 1024;
				        console.log(kbytes)
				    }
				);
			</script>	
</body>