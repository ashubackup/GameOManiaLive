<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@page import="gen.*"%>
<%@page import="java.sql.ResultSet"%>
<!DOCTYPE HTML>
<%
	String error = (String) request.getAttribute("errorMessage");
    String ref = request.getParameter("ref"); 

String errorMessage = (String) request.getAttribute("errorMessage");
if(errorMessage == null){
	errorMessage = "";
}
String subMessage = (String) request.getAttribute("subMessage");
String subApplMessage = (String) request.getAttribute("subApplMessage");
String number = "", pubid = "", provider = "", svc = "", clickid = "";

System.out.println("error -- " + error + " -- subMessage -- " + subMessage + " -- subApplMessage -- " + subApplMessage);

if ((error == null || error == "") && (subMessage == null || subMessage == "")
		&& (subApplMessage == null || subApplMessage == "")) {

	if (error == null || error == "") {
		String ani = (String) session.getAttribute("ani");
		if (ani != null) {
	response.sendRedirect("/Login?number=" + ani + "");
		} else {
	ani = request.getHeader("x-jinny.cid");
	if (ani != null) {
		response.sendRedirect("/Login?number=" + ani + "");
	} else {
		clickid = request.getParameter("clickid");
		if (clickid != null && clickid != "") {
			number = request.getParameter("number");
			pubid = request.getParameter("pubid");
			provider = request.getParameter("V");
			svc = request.getParameter("svc");
		}
	}
		}
	}
}
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<meta name="viewport"
	content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0 minimal-ui" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black">

<title>Game-O-Mania</title>

<link rel="stylesheet" type="text/css" href="styles/style.css">
<link rel="stylesheet" type="text/css" href="styles/skin.css">
<link rel="stylesheet" type="text/css" href="styles/framework.css">
<link rel="stylesheet" type="text/css" href="styles/ionicons.min.css">

<link
	href="https://fonts.googleapis.com/css?family=Quicksand:300,400,500,700"
	rel="stylesheet">
<link href="styles/landing.css" rel="stylesheet">

</head>
<%
	DataCollector coll = new DataCollector();
%>
<body class="grey">


	<div class="page-preloader page-preloader-dark">
		<div class="spinner"></div>
	</div>
	<div id="page-content" class="header-clear-large top_all">
		<div id="page-content-scroll">
			<!--Enables this element to be scrolled -->
			<div class="joinus">
				<!-- <div class="logo-join">
					<img src="images/logo.png" alt="logo" class="img-fluid" />
				</div> -->
				<!-- <div class="joinus-slider">
					<div class="swiper-container">
						<div class="swiper-wrapper">
							<%
								ResultSet res3 = coll.getHighlightGames();
							while (res3.next()) {
							%>
							<div class="swiper-slide">
								<img src="<%=res3.getString("imgurl")%>" alt="logo"
									class="img-fluid" />
							</div>
							<%
								}
							res3.close();
							%>
						</div>
						Add Pagination
						<div class="swiper-pagination"></div>
						Add Arrows
						<div class="swiper-button-next"></div>
						<div class="swiper-button-prev"></div>
					</div>
				</div> -->

				<div class="bg-img">
					<img src="images/background0.jpg" alt="">
				</div>
				<%
					if (subMessage != null) {
				%>
				<div class="joinus-content">
					<p>${subMessage}</p>
					<form method="POST" action="/Subscribe">
						<input type="text" name="number" title="Please enter only number"
							maxlength="11" value="${msisdn}" pattern="\d*" readonly>
						<div class="center-cus">
							<input type="submit" value="Join"
								class="btn btn-default submit-btn-cus">
						</div>
					</form>
				</div>
				<%
					} else if (subApplMessage != null) {
				%>
				<div class="joinus-content">
					<p>${subApplMessage}</p>
				</div>
				<%
					}  else if (clickid != null && clickid != "" ) {
						%>	<div class="joinus-content">
						<h3>Welcome to Game-O-Mania Games Portal</h3>
						<h5>Play unlimited! Hundreds of cool games</h5>
						<p>ENTER YOUR NUMBER AND JOIN Games</p>
						<h5 style="color: red;"><%=errorMessage %></h5>
						<form method="GET" action="/Subscribe">
							<input type="text" name="number" title="Please enter only number"
								onkeypress="if ( isNaN(this.value + String.fromCharCode(event.keyCode) )) return false;"
								placeholder="Enter your phone number : eg. 812xxxxxx"
								maxlength="11" minlength="9" pattern="\d*" required="">
								<input type="text" name="svc" value="videos" style="display:none;">
								<input type="text" name="pubid" value="<%=pubid%>" style="display:none;">
								<input type="text" name="clickid" value="<%=clickid%>" style="display:none;">
								<input type="text" name="V" value="<%=provider%>" style="display:none;">
							<p>
							
								<input type="checkbox" checked="checked" required=""> I
								agreed to the <a href="term.html">Terms and Conditions </a>
							</p>
							<div class="center-cus">
								<input type="submit" value="Join"
									onclick="if(!this.form.checkbox.checked){alert('You must agree to the terms first.');return false}"
									class="btn btn-default submit-btn-cus">
							</div>
						</form>
					</div>
					<%}else{
				%>
				<div class="joinus-content">
					<h3>Welcome to Game-O-Mania Games Portal</h3>
					<h5>Play unlimited! Hundreds of cool games</h5>
					<p>ENTER YOUR NUMBER AND JOIN Games</p>
					<h5 style="color: red;"><%=errorMessage %></h5>
					<form method="POST" action="Login">
						<input type="text" name="number" title="Please enter only number"
							onkeypress="if ( isNaN(this.value + String.fromCharCode(event.keyCode) )) return false;"
							placeholder="Enter your phone number : eg. 812xxxxxx"
							maxlength="11" minlength="9" pattern="\d*" required="">
						<p>
							<input type="checkbox" checked="checked" required=""> I
							agreed to the <a href="term.html">Terms and Conditions </a>
						</p>
						<input type="text" name="type" value="offnet" style="display:none;">
						<input type="text" name="ref" value="<%=ref %>" style="display:none;">
						<div class="center-cus">
							<input type="submit" value="Join"
								onclick="if(!this.form.checkbox.checked){alert('You must agree to the terms first.');return false}"
								class="btn btn-default submit-btn-cus">
						</div>
					</form>
				</div>

				<%
					}
				%>
			</div>

			<jsp:include page="footer.jsp"></jsp:include>
		</div>

		<a href="#" class="back-to-top-badge"><i
			class="ion-android-arrow-dropup"></i>Back to Top</a>

	</div>
	<script type="text/javascript" src="scripts/jquery.js"></script>
	<script type="text/javascript" src="scripts/plugins.js"></script>
	<script type="text/javascript" src="scripts/custom.js"></script>
	<script>
		var swiper = new Swiper('.swiper-container', {
			spaceBetween : 30,
			centeredSlides : true,
			pagination : {
				el : '.swiper-pagination',
				clickable : true,
			},
			navigation : {
				nextEl : '.swiper-button-next',
				prevEl : '.swiper-button-prev',
			},
		});
	</script>
</body>