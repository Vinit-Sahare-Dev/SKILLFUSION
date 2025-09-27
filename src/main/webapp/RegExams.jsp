<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="beans.*" %>
<%@page import="models.*" %>
<%@page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="css/bootstrap.min.css">
 <link rel="stylesheet" href="css/cust.css">

<title>AlumniConnect</title>
  
<script language="Javascript" type="text/javascript">
 

function createRequestObject() {
    var tmpXmlHttpObject;
    if (window.XMLHttpRequest) {
            tmpXmlHttpObject = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        tmpXmlHttpObject = new ActiveXObject("Microsoft.XMLHTTP");
    }

    return tmpXmlHttpObject;
}


var http = createRequestObject();

function makeGetRequest(st) {
   // st=document.frm.state.value;
   
    http.open('get', 'ShowBusinesses.jsp?category=' + st);
    http.onreadystatechange = processResponse;
    http.send(null);
}

function processResponse() {
    if(http.readyState == 4){
        var response = http.responseText;
        document.getElementById('businesses').innerHTML = response;
    }
}
 
</script>
</head>
<%
	BranchList bl = new BranchList();
	List<BranchList> lst = bl.getBranchList();
%>
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
<div class="col-md-6">

<h2>Register Course</h2>
 

<div class="form-group">
<form name="frm" method="post" action="registerCourse" enctype="multipart/form-data"><table class="tblform">
	<tr><td>Course Name</td>
	<td><input type="text" name="courseName" class="form-control" required></td>
	</tr>
	 <tr><td>Fees</td>
	<td><input type="text" name="fees" class="form-control" required></td>
	</tr>
	<tr><td>Duration</td>
	<td><input type="text" name="duration" class="form-control" required>
	<select name="duration_unit" class="form-control" required>
	<option value="Months">Months</option>
	<option value="Years">Years</option>
	</select>
	
	</td>
	</tr>
	<tr><td>Details</td>
	<td><textarea name="details" class="form-control" required></textarea>
	 
	</td>
	</tr>
	 
       
       <tr>
       	<td>Branch</td>
       	<td><select name="branch" class="form-control" required>
       	<%for(int i=0 ;i<lst.size();i++) {%>
       			<option value=<%=lst.get(i).getBranchname()%>><%=lst.get(i).getBranchname() %></option>
		<%} %>
       	</select></td>
       	
       </tr>
       <tr>
       	<td>Category</td>
       	<td><select name="category" onchange="makeGetRequest(this.value)" class="form-control" required>
       	<option value=""><--select--></option>
       <% JavaFuns jf=new JavaFuns();
                   Vector v=jf.getValue("select category  from categories", 1); 
                   for(int i=0;i<v.size();i=i+1)
                   {
                	   %>
                	   <option value="<%=v.elementAt(i).toString().trim() %>"><%=v.elementAt(i).toString().trim() %></option>
                	   <%} %>
                	   </select>
                	   </td></tr>
       <tr><td>Businesses</td><td>
       <div id="businesses"></div>
       </td></tr>
        <tr><td>Logo</td>
     <td>
     <input type="file" name="file" class="form-input"/>
     </td></tr>
 
	<tr>
	<td><input type="submit" value="Submit" class="btn btn-primary" ></td>
	</tr>
	</table>
</form>
</div></div>
<div class="col-md-6">
<img src="img/course_registration.jpg" width="100%"/>
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