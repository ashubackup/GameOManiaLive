
<%@ page language="java"  pageEncoding="ISO-8859-1"%>
<%@page import="gen.*"%>
<%@page import="java.sql.ResultSet"%>

<%
	String ani = (String) session.getAttribute("ani");
	String status = new DataCollector().getStatus(ani);
	if(ani== null){
		response.sendRedirect("/landing");
	}else if(status.equalsIgnoreCase("2")||status.equalsIgnoreCase("0")){
		response.sendRedirect("/Login?number="+ani+"");
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