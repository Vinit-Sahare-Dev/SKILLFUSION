<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="beans.*" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="models.*" %>
<%@page import="java.util.*" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/cust.css">
<title>Review Management</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    $(document).ready(function(){
        $("#reviewTab").click(function(){
            $("#submitReviewSection").hide();
            $("#reviewReportSection").show();
        });
        $("#submitTab").click(function(){
            $("#reviewReportSection").hide();
            $("#submitReviewSection").show();
        });
    });
</script>
</head>

<body>
<jsp:include page="Top.jsp"></jsp:include>

<% 
try {
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setDateHeader("Expires", -1);

    if(session.getAttribute("userid") == null) {
        response.sendRedirect("index.jsp");
    }

    String userid = String.valueOf(session.getAttribute("userid"));

    if(!userid.equalsIgnoreCase("null")) {    
        session.setMaxInactiveInterval(10*60);
%>

<div class="container mt-4">
    <!-- Tabs -->
    <ul class="nav nav-tabs">
        <li class="nav-item">
            <a class="nav-link active" href="#" id="submitTab">Submit Review</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="#" id="reviewTab">Review Report</a>
        </li>
    </ul>

    <!-- Submit Review Section -->
    <div id="submitReviewSection">
        <div class="row mt-4">
            <div class="col-md-6">
                <h2>Submit Your Review</h2>
                <div class="form-group">
                    <form name="frm" method="post" action="regReview">
                        <table class="tblform">
                            <tr>
                                <td>Review For</td>
                                <td>
                                    <input type="text" class="form-control" name="username1" 
                                    value="<%=request.getAttribute("username").toString().trim() %>"/>
                                </td>
                            </tr>
                            <tr>
                                <td>Review</td>
                                <td>
                                    <input type="hidden" name="userid" value="<%=session.getAttribute("userid").toString().trim() %>"/>
                                    <input type="hidden" name="username" value="<%=session.getAttribute("username").toString().trim() %>"/>
                                    <input type="hidden" name="userid1" value="<%=request.getAttribute("userid").toString().trim() %>"/>
                                    <textarea name="reviewText" class="form-control" required></textarea>
                                </td>
                            </tr>
                            <tr>
                                <td><input type="submit" value="Submit" class="btn btn-primary"></td>
                            </tr>
                        </table>
                    </form>
                </div>
            </div>
            <div class="col-md-6">
                <img src="images/review.gif" width="50%"/>
            </div>
        </div>
    </div>

    <!-- Review Report Section (Initially Hidden) -->
    <div id="reviewReportSection" style="display: none;" class="mt-4">
        <h2>Review Report</h2>
        <table class="table table-bordered">
            <%
                List<Reviews> lst = (List) request.getAttribute("lst");
            %>
            <c:forEach var="userdsc" items="${lst}">
                <tr>
                    <td>Sender Name: ${userdsc.getUsername()}</td>
                    <td>Date: ${userdsc.getDt()}</td>
                </tr>
                <tr>
                    <td colspan="2"><h3>${userdsc.getReviewText()}</h3></td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>

<% 
    } else {
%>
    <h2>Invalid Session...Login again</h2>
    <br>
    <a href="index.jsp">Login</a>
<%
    }
} catch(Exception ex) {
    System.out.println("Error: " + ex.getMessage());
}
%>

</body>
</html>
