<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="soham"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<soham:forEach items="${lst}" var="rec">
<div class="border-bottom p-2"><strong>${rec.username}</strong>:${rec.comment} <br> 
    			                <small>${rec.commentedAt}</small> 
    		              </div>  

 </soham:forEach>
</body>
</html>