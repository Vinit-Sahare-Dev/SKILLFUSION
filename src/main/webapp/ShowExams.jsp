  
<%@page import="java.util.List"%>
  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="soham"%>
 
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<title>Register</title>
<link rel="stylesheet" href="css/bootstrap.min.css">
<title>Insert title here</title>
</head>
<body>
<%System.out.println("course page") ; %>
  
 <br/>

<table class="table table-bordered">
<tr >
<th>CourseName</th>
<th>Allotted To</th>
<th>Date</th>  
</tr>


<soham:forEach items="${stf}" var="rec">

<tr>
<td>${rec.courseName}</td>
<td>${rec.staffName}</td> 
 <td>${rec.adt}</td> 
</tr>
</soham:forEach>
</table>
</body>
</html>