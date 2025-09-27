<%@page import="java.util.Vector"%>
<%@page import="models.JavaFuns"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="css/bootstrap.min.css">
 <link rel="stylesheet" href="css/cust.css">

<title> </title>
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
JavaFuns jf=new JavaFuns();
Vector v=jf.getValue("select branchname from branches", 1);
%><div class="row">
<div class="col-md-6">

<h2>Register Branch</h2>
<form name="frm" method="post" action="registernewbranch">
<table class="tblform">
	<!-- <tr>
		<td>Branch ID</td>
		<td><input type="text" name="branchid" required></td>
	</tr> -->
		<tr>
		<td>Branch Name</td>
		<td><input type="text" class="form-control" name="branchname" required></td>
	</tr>
	<tr>
		<td>
		<input type="submit" class="btn btn-primary" value="Submit">
		</td>
	</tr>
</table>
</form></div> 
<div class="col-md-6">
<h2>Branches</h2>
 <table class="table table-bordered">
 <tr><th>Branch Name</th>
 </tr>
 <%for(int i=0;i<v.size();i++){ %>
 <tr><td><%=v.elementAt(i).toString().trim() %></td></tr>
 <%} %>
 </table>
</div>
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
	
}
%>
 
</body>
</html>