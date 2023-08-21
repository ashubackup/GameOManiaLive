
<%@ page language="java"  pageEncoding="ISO-8859-1"%>
<%@page import="gen.*"%>
<%@page import="java.sql.ResultSet"%>

<!-- 9805702978 -->
<%
	String ani = (String) session.getAttribute("ani");
	String status = new DataCollector().getStatus(ani);
	if(ani== null){
		response.sendRedirect("landing");
	}else if(status.equalsIgnoreCase("2")||status.equalsIgnoreCase("0")){
		response.sendRedirect("Login?number="+ani+"");
	}
 %>

<!DOCTYPE HTML>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0 minimal-ui" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black">

<title>Games</title>

<link rel="stylesheet" type="text/css" href="styles/style.css">
<link rel="stylesheet" type="text/css" href="styles/skin.css">
<link rel="stylesheet" type="text/css" href="styles/framework.css">
<link rel="stylesheet" type="text/css" href="styles/ionicons.min.css">
<script defer src="js/playwin.js"></script>
<link rel="shortcut icon" href="/images/logo.png" type="image/x-icon">

<link
	href="https://fonts.googleapis.com/css?family=Quicksand:300,400,500,700"
	rel="stylesheet">

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
				<div class="container">
					<div class="bigslides">
						<h3 class="cus-heading">Highlights</h3>
						<div class="swiper-container cusslides">
							<div class="swiper-wrapper">
								<%
									ResultSet res3 = coll.getHighlightGames();
									while (res3.next()) {
									String gameurl = res3.getString("gameurl");
									String type = res3.getString("status");
									String gameid = res3.getString("gameid");
									String cat_id = res3.getString("cat_id");
								%>
								<div class="swiper-slide">
									<a href="<%= "/Logger?ani="+ani+"&url="+gameurl+"&type="+type+"&gameid="+gameid+"&cat_id="+cat_id+"" %>"
										class="column-center-image"> <img class="col-img-2 img"
										src="<%=res3.getString("banner_url")%>" alt="img">
									</a>
									<p class="video_title"><%=res3.getString("gamename")%></p>
								</div>
								<%
									}
									res3.close();
								%>



							</div>
							<!-- Add Pagination -->
							<div class="swiper-pagination"></div>
						</div>
					</div>

					


					<div class="staff-slider">
						<div class="cus-heading">Trending now</div>
						<div class="swiper-wrapper">
							<%
								ResultSet res2 = coll.getTrendingGames();
								while (res2.next()) {
								String gameurl = res2.getString("gameurl");
								String type = res2.getString("status");
								String gameid = res2.getString("gameid");
								String cat_id = res2.getString("cat_id");
							%>
							<div class="swiper-slide">
								<a href="<%= "/Logger?ani="+ani+"&url="+gameurl+"&type="+type+"&gameid="+gameid+"&cat_id="+cat_id+"" %>"
									class="column-center-image"> <img class="col-img-2 img"
									src="<%=res2.getString("imgurl")%>" alt="img">
								</a>
								<p class="video_title"><%=res2.getString("gamename")%></p>
							</div>
							<%
								}
								res2.close();
							%>

						</div>
					</div>
					

					<%
						ResultSet res = coll.getCategories();
						while (res.next()) {
					%>

					<div class="staff-slider">
						<div class="cus-heading"><%=res.getString("cat_name")%>
							Games
						</div>

						<div class="swiper-wrapper">
							<%
								ResultSet res1 = coll.getGamesByCatID(res.getString("sr_no"));
									while (res1.next()) {
										
									String gameurl = res1.getString("gameurl");
									String type = res1.getString("status");
									String gameid = res1.getString("gameid");
									String cat_id = res1.getString("cat_id");
									
							%>
							<div class="swiper-slide">
								<a href="<%= "/Logger?ani="+ani+"&url="+gameurl+"&type="+type+"&gameid="+gameid+"&cat_id="+cat_id+"" %>"
									class="column-center-image"> <img class="col-img-2 img"
									src="<%=res1.getString("imgurl")%>" alt="img">
								</a>
								<p class="video_title"><%=res1.getString("gamename")%></p>
							</div>
							<%
								}
									res1.close();
							%>

						</div>

					</div>
					<%
						}
						res.close();
					%>
				</div>
				<div id="myModal" class="modal fade" role="dialog">
					<div class="modal-dialog">

						<!-- Modal content-->
						<div class="modal-content">
						<div class="modal-header">
							<h4 class="modal-title">Add Info</h4>
						</div>
						<div class="modal-body">
							<div class="form-group">
								<label for="name"  class="text-black">Name</label>
								<input type="text" class="form-control text-black" id="name" maxlength="7" placeholder="Enter Nickname">
								<input type="text" style="display:none;" class="form-control" id="ani" >
							</div>
						</div>
						<div class="modal-footer">
						<button type="button" onclick="submit()" class="btn btn-primary">Submit</button>
							<button type="button" onclick="cancel()" class="btn btn-default" data-dismiss="modal">Close</button>
						</div>
						</div>

					</div>
				</div>
				
				<jsp:include page="footer.jsp" />
				<div class="clear"></div>
				

			</div>
		</div>

		<a href="#" class="back-to-top-badge"><i
			class="ion-android-arrow-dropup"></i>Back to Top</a>

	</div>
	<script type="text/javascript" src="scripts/jquery.js"></script>
	<script type="text/javascript" src="scripts/plugins.js"></script>
	<script type="text/javascript" src="scripts/custom.js"></script>
	<script>
		var swiper = new Swiper('.cusslides', {
			spaceBetween : 30,
			autoplay:4000,
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
	<script>
		// var appendNumber = 4;
		// var prependNumber = 1;
		var swiper = new Swiper('.playwin .swiper-container', {
		  slidesPerView: 3,
		  centeredSlides: true,
		  spaceBetween: 5,
		  autoplay:10000,
		  pagination: {
			el: '.swiper-pagination',
			clickable: true,
		  },
		  navigation: {
			nextEl: '.swiper-button-next',
			prevEl: '.swiper-button-prev',
		  },
		});
	  </script>
</body>