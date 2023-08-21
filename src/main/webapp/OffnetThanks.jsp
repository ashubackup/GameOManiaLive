<!DOCTYPE html>
<%
    String ani = request.getParameter("ani");
    String ref = request.getParameter("ref");
    String flag = request.getParameter("flag");
%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Game-O-Mania | Thank you</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=DM+Sans:wght@400;500;700&family=Jost:wght@200;300;400;500;600;700;800;900&display=swap" rel="stylesheet">
    <style>
        body{
            font-family: 'Jost', sans-serif;
        }
        h1, h2, h3, h4, h5{
            font-family: 'DM Sans', sans-serif;
        }
        .inner_content_img h4 {
            margin-bottom: 20px;
            font-size: 18px;
            font-weight: normal;
        }
        .submit-btn .btn{
            font-size: 18px;
            letter-spacing: .5px;
            min-width: 150px;
            min-height: 45px;
            border-radius: 50px;
        }
        .body_wrapper{
            background-color: #eee;
        }
        @media screen and (max-width:767px){
            .inner_content_img h4 {
                font-size: 16px;
                text-align: center;
            }
        }
        @media screen and (max-width:575px){
            .submit-btn .btn {
                width: 100%;
                letter-spacing: 1px;
                font-weight: 500;
            }
        }
    </style>
</head>
<body>
    <main>
        <div class="body_wrapper">
            <div class="container">
                <div class="row py-md-5 min-vh-100 align-items-center justify-content-center">
                    <div class="col-md-7 col-lg-6 col-xl-6 py-3">
                        <div class="inner_content text-center px-3 py-4 p-md-5 bg-white rounded">
                            <div class="inner_content_img text-center mb-4">
                                <img src="images/CB.jpg" alt="cb" class="img-fluid" />
                            </div>
                            <%
                              if(flag.equalsIgnoreCase("TRUE")){
                            %>
                            <h4 class="text-primary">We have sent you an SMS - replay YES in the SMS to get started!</h4>
                            <h6>Haven't received SMS?</h6>
                            <%
                              }
                              else if (flag.equalsIgnoreCase("FALSE")){
                            %>
                            <h4 class="text-primary">Too many requests! Please try again later.</h4>
                            <!-- <h6>Haven't received SMS?</h6> -->
                            <%
                              }
                            %>
                            <div class="submit-btn mt-4">
                                <button onclick="window.location.href='Login?type=offnet&number=<%=ani %>&ref=<%=ref %>'" class="btn w-100 px-4 btn-block text-uppercase btn-primary submit-btn-cus">Try Again</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>

    <!-- <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js"></script> -->
</body>
</html>