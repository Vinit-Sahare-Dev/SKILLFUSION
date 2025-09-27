<%@page import="java.util.Vector"%>
<%@page import="models.JavaFuns"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <title> </title>
        <meta content="width=device-width, initial-scale=1.0" name="viewport">
        <meta content="" name="keywords">
        <meta content="" name="description">

        <!-- Google Web Fonts -->
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600&family=Saira:wght@500;600;700&display=swap" rel="stylesheet"> 

        <!-- Icon Font Stylesheet -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">

        <!-- Libraries Stylesheet -->
        <link href="lib/animate/animate.min.css" rel="stylesheet">
        <link href="lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">

        <!-- Customized Bootstrap Stylesheet -->
        <link href="css/bootstrap.min.css" rel="stylesheet">

        <!-- Template Stylesheet -->
        <link href="css/style.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
        
    </head>

    <body>
        <!-- Spinner Start -->
        <div id="spinner" class="show position-fixed translate-middle w-100 vh-100 top-50 start-50 d-flex align-items-center justify-content-center">
            <div class="spinner-grow text-primary" role="status"></div>
        </div>
        <!-- Spinner End -->

         

        <!-- Navbar Start -->
        <div class="container-fluid bg-primary1">
            <div class="container">
                <nav class="navbar navbar-dark navbar-expand-lg py-0">
                   <img src="img/logo.png" width="200px"/>
                    <button type="button" class="navbar-toggler me-0" data-bs-toggle="collapse" data-bs-target="#navbarCollapse">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <div class="collapse navbar-collapse bg-transparent" id="navbarCollapse">
                        <div class="navbar-nav ms-auto mx-xl-auto p-0">
                            <a href="<%=session.getAttribute("usertype") %>" class="nav-item nav-link active text-secondary">Home</a>
                             <%if(session.getAttribute("usertype").toString().trim().equals("admin")){ %>
                             <a href="registerbranch" class="nav-item nav-link">Branches</a>
                             
                           <div class="nav-item dropdown">
                                <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown">Students</a>
                                <div class="dropdown-menu rounded"> 
                                    <a href="approvestudentlist" class="dropdown-item">Pending Registration</a>
                                    <a href="viewstudent" class="dropdown-item">Active Students</a>
                                   
                                </div>
                            </div>
                           
      						<div class="nav-item dropdown">
                                <a href="#" class="nav-link dropdown-toggle" data-bs-toggle="dropdown">Working Professionals</a>
                                <div class="dropdown-menu rounded"> 
                                    <a href="approvestudentlist_al" class="dropdown-item">Pending Registration</a>
                                    <a href="viewAlumni" class="dropdown-item"> Report</a>
                                   
                                </div>
                            </div>
                             
                            <%}else if(session.getAttribute("usertype").toString().trim().equals("staff")){ %>
                      <a href="viewDocs" class="nav-item nav-link">View Documents</a>
                      <a href="viewMyAllottedCourse" class="nav-item nav-link">My Allotted Courses</a>
                      <a href="uploadDocs" class="nav-item nav-link">Upload Content</a>
                            
                            <%}else if(session.getAttribute("usertype").toString().trim().equals("student")){ %>
                            <%} %>
                            <a href="ChangePass" class="nav-item nav-link">Change Password</a>
                            <a href="logout" class="nav-item nav-link">Logout</a>
                            
                        </div>
                    </div>
                    
                </nav>
            </div>
        </div>
        <!-- Navbar End -->

        
        <!-- Page Header Start -->
        <div class="container-fluid page-header py-5">
            <div class="container text-center py-5">
                <h1 class="display-2 text-white mb-4 animated slideInDown">Welcome, <%= session.getAttribute("username") %>!</h1>
                <nav aria-label="breadcrumb animated slideInDown">
                    <ol class="breadcrumb justify-content-center mb-0">
                        <li class="breadcrumb-item">Logged in as <%=session.getAttribute("userid").toString() %> (<%=session.getAttribute("usertype").toString() %>)</li>
                           <li>
                            <%if(!session.getAttribute("usertype").toString().equals("admin")){ %>
	  <img src='Uploads/<%=session.getAttribute("photo").toString() %>' class="img-responsive img-thumbnail rounded-circle" width="100px"/>
	    
	 <%} %>
                           </li>
                            </ol>
                </nav>
            </div>
        </div>
        <div class="container-fluid  position-relative p-5"> 
       <div class="container back1"> 
        <!-- Page Header End -->
          <!-- Back to Top -->
        <a href="#" class="btn btn-secondary btn-square rounded-circle back-to-top"><i class="fa fa-arrow-up text-white"></i></a>

        
        <!-- JavaScript Libraries -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="lib/wow/wow.min.js"></script>
        <script src="lib/easing/easing.min.js"></script>
        <script src="lib/waypoints/waypoints.min.js"></script>
        <script src="lib/owlcarousel/owl.carousel.min.js"></script>

        <!-- Template Javascript -->
        <script src="js/main.js"></script>
    </body>

</html> 



 