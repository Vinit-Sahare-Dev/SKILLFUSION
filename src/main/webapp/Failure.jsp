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
<body><jsp:include page="DefaultTop.jsp"></jsp:include><div class="container"><center> 
<%

try{
String usertype="NA";
try{String userid="NA";
if(session.getAttribute("userid")!=null){
	userid=String.valueOf(session.getAttribute("userid"));
}
if(session.getAttribute("usertype")!=null){
usertype= String.valueOf(session.getAttribute("usertype"));
}
}
catch(Exception ex)
{
	
}

 
%>
<h2><%
if(request.getAttribute("activity").toString().trim().equals("branchReg"))
{
	%>
	Branch Registered Successfully...
	<%
}
else if(request.getAttribute("activity").toString().trim().equals("GovtCateReg"))
{
	%>
	Government Exam Category Registered Successfully...
	
	<%
}
else if(request.getAttribute("activity").toString().trim().equals("GovtExamReg"))
{
	%>
	Government Exam Registered Successfully...
	
	<%
}
else if(request.getAttribute("activity").toString().trim().equals("SuccessStoriesReg"))
{
	%>
	Success Stories Registered Successfully...
	
	<%
}
else if(request.getAttribute("activity").toString().trim().equals("StudReg"))
{
	%>
	You are Registered Successfully...
	
	<%
}
else if(request.getAttribute("activity").toString().trim().equals("regRating"))
{
	%>
	Document Rating Submitted Successfully...
	
	<%
}
else if(request.getAttribute("activity").toString().trim().equals("EmailVerificationFailed"))
{
	%>
	Email Verification Failed!!
	
	<%
}else if(request.getAttribute("activity").toString().trim().equals("DocReview"))
{
	%>
	Review Registered Successfully...
	
	<%
}
else if(request.getAttribute("activity").toString().trim().equals("TopicReg"))
{
	%>
	Topic Registered Successfully...
	
	<%
}
else if(request.getAttribute("activity").toString().trim().equals("CourseAllocation"))
{
	%>
	Course Allocated to Staff Successfully...
	
	<%
}else if(request.getAttribute("activity").toString().trim().equals("SessionReg"))
{
	%>
	Session Registered Successfully...
	
	<%
}
else if(request.getAttribute("activity").toString().trim().equals("StaffReg"))
{
	%>
	New Staff Member Registered Successfully...
	<%
}else if(request.getAttribute("activity").toString().trim().equals("QuesReg"))
{
	%>
	Question Registered Successfully...
	<%
}
else if(request.getAttribute("activity").toString().trim().equals("ForumAnsReg"))
{
	%>
	Answer Registered Successfully...
	<%
}else if(request.getAttribute("activity").toString().trim().equals("changePass"))
{
	%>
	Password Changed Successfully...
	<%
}else if(request.getAttribute("activity").toString().trim().equals("regBookMark"))
{
	%>
	Selected Document Added into the book marks list Successfully...
	<%
}

else if(request.getAttribute("activity").toString().trim().equals("newStaff"))
{
	%>
	New Staff Registered Successfully...
	<%
}
else if(request.getAttribute("activity").toString().trim().equals("newCourse"))
{
	%>
	New Course Registered Successfully...
	<%
}
else if(request.getAttribute("activity").toString().trim().equals("studActivation"))
{
	%>
	Student Activation Done Successfully...
	<%
}else if(request.getAttribute("activity").toString().trim().equals("StaffProfile"))
{
	%>
	Your Profile Updated Successfully...
	<%
}
else if(request.getAttribute("activity").toString().trim().equals("StudProfile"))
{
	%>
	Your Profile Updated Successfully...
	<%
}
%></h2>
<hr><br>
<%
if(request.getAttribute("activity").toString().trim().equals("StudReg"))
{%>
	<a href="index.jsp" class="btn btn-secondary">Home</a>
<%}
else
{  
	if(usertype.equals("admin")){	%>
		<a href="Admin.jsp" class="btn btn-secondary">Home</a>
		<%
	}
	else if(usertype.equals("student")){
		
		 if(request.getAttribute("activity").toString().trim().equals("regRating")||request.getAttribute("activity").toString().trim().equals("regBookMark"))
		 {%>
			 	<a href="viewDocs1?course=<%=request.getParameter("course").trim() %>&topic=<%=request.getParameter("topic").trim() %>">Home</a>
		 <%}else{
		%>
		<a href="Student.jsp" class="btn btn-secondary">Home</a>
		<%}
	}
	else if(usertype.equals("staff")){
		%>
		<a href="Staff.jsp" class="btn btn-secondary">Home</a>
		<%
	}
	else if(usertype.equals("exstudent")){
		%>
	<a href="ExStudent.jsp" class="btn btn-secondary">Home</a>
<%
	}
	else {%>
		<a href="index.jsp" class="btn btn-secondary">Home</a>
		<%
	}

}}
catch(Exception ex)
{
	System.out.println("errr="+ex.getMessage());
}%>
 
 <button onclick="history.back()" class="btn btn-primary">Go Back</button>
</body>
</html>