<%@page import="gen.DataCollector"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<%
String ref="";
String tag="";

try{
	String agent=request.getHeader("User-Agent");
	System.out.println(agent);
		ref = request.getParameter("ref");
		tag = request.getParameter("tag");

		if (ref == null) {
			ref = "null";	
		}
		if (tag == null ) {
			tag = "null";
		}
// 		DataCollector.insertOffnetUser(tag, ref,agent);	


%>


<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Game-O-Mania</title>
    <link rel="stylesheet" href="styles/custom.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css">
</head>
<body>
    <div class="main-section">
    <div class="header">
        <div class="bg-img">
            <img src="images/land/game-o-mania.jpg" alt="banner" class="img-fluid">
            <img src="images/land/game-mania.jpg" alt="banner" class="img-fluid responsive">
        </div>
    </div>

    <div class="inner-part">
        <div class="container">
            <div class="row">
                <div class="col-md-7 text-center m-auto">
                    <h4 class="font-weight-bold">Yello. Enter your number below to subscribe to Game-O-Mania from Smartcall</h4>
                    <p>R5.00/day subscription service</p>
                    <div>
                        <div class="form-group">
                          <input type="number" class="form-control" id="ani" placeholder="Enter Your Number" maxlength="11">
                        </div>
                        <!-- <div class="form-group form-check">
                            <input type="checkbox" class="form-check-input" id="check">
                            <label class="form-check-label text-white" for="check">I agreed to the Terms and Conditions</label>
                          </div> -->
                        <button type="submit" onclick="login()" class="btn btnn">Continue</button>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="footer pb-2">
        <div class="container">
            <div class="row">
                <div class="col-md-4 col-3 text-right img-center">
                    <img src="images/land/logo.jpg" alt="logo" class="img-fluid">
                </div>

                <div class="col-md-6 col-8 footer-para">
                    <p class="mb-0">This service cost R5.00/day. To cancel dial *123#. Call 135 for help. Ts&Cs <a href="https://home.smartcalltech.co.za/terms-and-conditions/" target="_blank">https://home.smartcalltech.co.za/terms-and-conditions/</a></p>
                </div>
            </div>
        </div>
    </div>  
    
</div>
<%
}catch(Exception e)
{
	e.printStackTrace();
	
}


%>



<script type="text/javascript">
    function login(){
    	
                    let ani= document.getElementById("ani").value;
                	let url=new URL(window.location.href).searchParams.get("ref");
   						if (ani == "") {
							alert('Please put your Number !');
							return;
						}
						window.location.href = "Login?number=" + ani + "&type=offnet&ref="+url

					}
				</script>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.min.js"></script>
</body>
</html>