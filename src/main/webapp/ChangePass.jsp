
 
<%@page import="java.util.List"%>

<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>

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
   
    http.open('get', 'Cities?state=' + st);
    http.onreadystatechange = processResponse;
    http.send(null);
}

function processResponse() {
    if(http.readyState == 4){
        var response = http.responseText;
        document.getElementById('cities').innerHTML = response;
    }
}
 
</script>
 <script type="text/javascript">
    function Validate() {
        var password = document.getElementById("newpass").value;
        var confirmPassword = document.getElementById("rnewpass").value;
        document.frmChangePass.rnewPass.value='';
        if (password != confirmPassword) {
            alert("Passwords do not match.");
            return false;
        }
        return true;
    }
</script>
</head>
<body>
<jsp:include page="Top.jsp"></jsp:include>
<% try{ response.setHeader("Pragma", "No-cache");
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
response.setDateHeader("Expires", -1);
if(session.getAttribute("userid")==null)
{
	response.sendRedirect("index.jsp");
}
%>
 
  <div class="jumbotron"> 

     
<div class="row">

<div class="col-md-6"> <h2>Change Password</h2>
   <div class="form-group"> 
    <form method="post" name="frmChangePass" action="/ChangePassService">
<table class="tblform">
<tr>
<td>Current Password</td>
<td>
<input type="password"  name="currentpass" required class="form-control"></input>
</td>
</tr>
<tr>
<td>New Password</td>
<td>
<input type="password"  class="form-control"  name="newpass" required  >
</td>
</tr>
<tr>
<td>Retype New Password</td>
<td>
<input type="password"  class="form-control"  name="rnewpass" onchange="Validate();" required >
</td>
</tr>			
               
 <tr>
 <td colspan="2">
 <input type="submit" value="Submit" class="btn btn-primary"/>
 </td>
 </tr>
</table></form>
 
</div></div>
 <div class="col-md-6">
 <img src="assets/images/changePass.jpg" width="90%"/>
 </div>
</div>
</div>
<%}
catch(Exception ex)
{
	
} %>

</div>
</body>
</html>