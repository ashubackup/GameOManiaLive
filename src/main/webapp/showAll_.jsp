
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@page import="gen.*"%>
<%@page import="java.sql.*"%>
<!DOCTYPE HTML>

<%
	String ani = (String) session.getAttribute("ani");
	String status = new DataCollector().getStatus(ani);
	if(ani== null){
		response.sendRedirect("/landing");
	}else if(status.equalsIgnoreCase("2")||status.equalsIgnoreCase("0")){
		response.sendRedirect("/Login?number="+ani+"");
	}
 %>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0 minimal-ui" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black">

<title>Celebrity Home</title>

<link rel="stylesheet" type="text/css" href="styles/style.css">
<link rel="stylesheet" type="text/css" href="styles/skin.css">
<link rel="stylesheet" type="text/css" href="styles/framework.css">
<link rel="stylesheet" type="text/css" href="styles/ionicons.min.css">

<link
	href="https://fonts.googleapis.com/css?family=Quicksand:300,400,500,700"
	rel="stylesheet">
<script type="text/javascript" src="scripts/jquery.js"></script>
<script type="text/javascript" src="scripts/plugins.js"></script>
<script type="text/javascript" src="scripts/custom.js"></script>
</head>
<%
	DataCollector coll = new DataCollector();
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


				<div class="staff-slider">
					<div class="cus-heading">Games</div>
					<div class="swiper-wrapper">
						<%
							ResultSet res = coll.getGamesByCatID(request.getParameter("_id"));
							if (res.next()) {
								res.beforeFirst();
								while (res.next()) {
									String gameid = res.getString("gameid");
									String cat_id = res.getString("cat_id");
						%>
						<div class="swiper-slide">
							<a href="<%= "/Logger?ani="+ani+"&url="+res.getString("gameurl")+"&type="+res.getString("status")+"&gameid="+gameid+"&cat_id="+cat_id+"" %>"
									class="column-center-image"> <img class="col-img-2 img"
									src="<%=res.getString("imgurl")%>" alt="img">
								</a>
							<p class="video_title"><%=res.getString("gamename")%></p>
						</div>
						<%
							}
							} else {
								System.out.println("Checked .........");
						%>
							<p> New Games Comming Soon...............</p>
						<%
							}
						%>

					</div>
				</div>


				<footer>
					<div class="container">
						<div class="row">
							<div class="col-md-12">
								<p>&copy; copyright All Right Reserved.</p>
							</div>
						</div>
					</div>
				</footer>

				<div class="clear"></div>


			</div>
		</div>

		<a href="#" class="back-to-top-badge"><i
			class="ion-android-arrow-dropup"></i>Back to Top</a>

	</div>
	<script>
		var swiper = new Swiper('.cusslides', {
			spaceBetween : 30,
			// autoplay: {
			//     delay: 2500,
			//     disableOnInteraction: false,
			// },
			pagination : {
				el : '.swiper-pagination',
				clickable : true,
			},
		});
	</script>
</body>