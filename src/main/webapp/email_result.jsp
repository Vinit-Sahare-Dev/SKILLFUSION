<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3 style="color:<%=request.getAttribute("color") %>"><%=request.getAttribute("msg") %></h3>
 
<table><tr><td>OTP</td><td>
<input type="password" name="otp" class="form-control"/>
<input type="hidden" name="otp1" value="<%=request.getAttribute("otp").toString().trim() %>" class="form-control"/> 
 
</table> 
</body>
</html>