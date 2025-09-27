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

<title></title>
</head>
 
<body><jsp:include page="Top.jsp"></jsp:include>
<% try{ response.setHeader("Pragma", "No-cache");
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
response.setDateHeader("Expires", -1);
if(session.getAttribute("userid")==null)
{
	response.sendRedirect("index.jsp");
}
 
String userid=String.valueOf(session.getAttribute("userid"));

if(!userid.equalsIgnoreCase("null")){	
	
session.setMaxInactiveInterval(10*60);

%>  <div class="row">
<div class="col-md-12">

<h2>Submit Your Review
<input type="button" onclick="window.history.back()" value="Back" class="btn btn-primary"/>

</h2>
<br/>    

<table class="table table-bordered">
 
<%
List<Reviews> lst=(List)request.getAttribute("lst");
%>

 <c:forEach var="userdsc" items="${lst}">

<tr>
<th>User Name : ${userdsc.getUsername()}</th>
<th>Date: ${userdsc.getDt()}</th>
</tr><tr> 
<td colspan="2">${userdsc.getReviewText()}</td>
 
</tr>
</c:forEach>
</table>
</div>
<%
}
else{
	%>
	<h2>Invalid Session...Login again</h2>
	<br>
	<a href="index.jsp">Login</a>
	
	<%
}}
catch(Exception ex)
{
	System.out.println("err="+ex.getMessage());
}
%>
 
</body>
</html>