
                <footer>
                    <div class="container">
                        <div class="row">
                            <div class="col-md-12">
                                <p><span id="year"></span> &copy; copyright All Right Reserved.</p>
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
	<script type="text/javascript" src="scripts/jquery.js"></script>
	<script type="text/javascript" src="scripts/plugins.js"></script>
	<script type="text/javascript" src="scripts/custom.js"></script>
	<script>
// 		var swiper = new Swiper('.swiper-container', {
// 			spaceBetween : 30
// 			,
// 			autoplay: {
// 			    delay:10000 ,
// 			    disableOnInteraction: false
// 			}
		
// // 	     	,			
// // 			pagination : {
// // 				el : '.swiper-pagination',
// // 				clickable : true,
// // 			}


// 		});
		
		
		const swiper = new Swiper('.swiper-container', {
			speed:2000,
			 autoplay: {
			   delay: 10000,
			   disableOnInteraction: true,
			 },
			});
		
		
	</script>
                <script>
                
               		 function date() {
                	  var d = new Date();
                	  var n = d.getFullYear();
                	  document.getElementById("year").innerHTML = n;
                	}
               		date();
                </script>
                
                