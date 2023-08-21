
<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@page import="gen.*"%>
<%@page import="java.sql.ResultSet"%>


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

<style>
.quick-slider a img {
	height: 130px !important;
}
.lotto-slider a img{
	height: 120px !important;
}
</style>
</head>

<body>
	<div id="page-transitions">

		<div class="page-preloader page-preloader-dark">
			<div class="spinner"></div>
		</div>

		<jsp:include page="nav.jsp" />
		<!-- Main Small Icon Sidebar -->
		<div class="sidebar-menu sidebar-dark">
			<jsp:include page="sampleSide.jsp" />
		</div>


		<div id="page-content" class="header-clear-large">
			<div id="page-content-scroll">
				<!--Enables this element to be scrolled -->
				<div class="container">


					<div class="staff-slider quick-slider">
						<div class="cus-heading">Quick Play</div>
						<div class="swiper-wrapper">
							<div class="swiper-slide">
								<a
									href="https://www.gameninja.in/html/v1/CashQuiz/index.html?uid=1"
									class="column-center-image"> <img class="col-img-2 img"
									src="https://www.gameninja.in/html/v1/CashQuiz/cashquizI.PNG"
									alt="img">
								</a>
								<p class="video_title">CashQuiz</p>
							</div>

							<div class="swiper-slide">
								<a
									href="https://www.gameninja.in/html/v1/CashHub//index.html?uid=2"
									class="column-center-image"> <img class="col-img-2 img"
									src="https://www.gameninja.in/html/v1/CashHub/cashubI.PNG"
									alt="img">
								</a>
								<p class="video_title">CashHub</p>
							</div>



						</div>
					</div>


					<div class="staff-slider lotto-slider">
						<div class="cus-heading">Lotto</div>
						<div class="swiper-wrapper">
							<div class="swiper-slide">
								<a href="SamplePlay.jsp" class="column-center-image"> <img
									class="col-img-2 img"
									src="https://gameninja.in/html/v1/BigCash_obfc/tabmola.PNG"
									alt="img">
								</a>
								<p class="video_title">Tambola</p>
							</div>

							<div class="swiper-slide">
								<a
									href="https://gogames.run/h5games/mastergames/Untested/prize/index.html"
									class="column-center-image"> <img class="col-img-2 img"
									src="https://www.gameninja.in/html/v1/CashQuiz/spinner.png"
									alt="img">
								</a>
								<p class="video_title">Spin the Wheel</p>
							</div>


						</div>
					</div>


					<div class="staff-slider">
						<div class="cus-heading">Scoreboard Win</div>
						<div class="swiper-wrapper">


							<div class="swiper-slide">
								<a
									href="http://www.gameninja.in/html/Gameneeti/millionairev1.0/index.html?phone_no=7018694842&nick_name=Test&game_id=10&type=98"
									class="column-center-image"> <img class="col-img-2 img"
									src="http://www.gameninja.in/html/Gameneeti/millionairev1.0/Cashup3.1_500.png"
									alt="img">
								</a>
								<p class="video_title">Money Quiz</p>
							</div>

							<div class="swiper-slide">
								<a
									href="http://www.gameninja.in/html/Gentech//hextrisMatch3/index.html?phone_no=7018694842&nick_name=Test&game_id=12&type=98"
									class="column-center-image"> <img class="col-img-2 img"
									src="https://www.gameninja.in/html/v1/CashQuiz/puzzle.png"
									alt="img">
								</a>
								<p class="video_title">Winner Puzzle</p>
							</div>

						</div>
					</div>

					<div class="staff-slider">
						<div class="swiper-wrapper">

							<div class="swiper-slide">
								<a
									href="http://www.gameninja.in/html/Gameneeti/Extreme%20Sea%20Winner/index.html?phone_no=7018694842&nick_name=Test&game_id=11&type=98"
									class="column-center-image"> <img class="col-img-2 img"
									src="https://www.gameninja.in/html/v1/CashQuiz/swim.png"
									alt="img">
								</a>
								<p class="video_title">Cash Chaser</p>
							</div>
						</div>
					</div>


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
									<label for="name" class="text-black">Name</label> <input
										type="text" class="form-control text-black" id="name"
										maxlength="7" placeholder="Enter Nickname"> <input
										type="text" style="display: none;" class="form-control"
										id="ani">
								</div>
							</div>
							<div class="modal-footer">
								<button type="button" onclick="submit()" class="btn btn-primary">Submit</button>
								<button type="button" onclick="cancel()" class="btn btn-default"
									data-dismiss="modal">Close</button>
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
			autoplay : 4000,
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
			slidesPerView : 3,
			centeredSlides : true,
			spaceBetween : 5,
			autoplay : 10000,
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