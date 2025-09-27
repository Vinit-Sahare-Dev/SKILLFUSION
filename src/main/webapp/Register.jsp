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
 
<title> </title>
</head>
<body><jsp:include page="DefaultTop.jsp"></jsp:include>
<div class="container"><div class="row"> <div class="col-md-6"> 
<%
	BranchList bl = new BranchList();
	List<BranchList> lst = bl.getBranchList();
%>

<h2>Student Registration Form</h2>
 
<hr>

<div class="form-group">
<form name="frm" method="post" action="registeruser" enctype="multipart/form-data"><table>
	<tr><td>Userid</td>
	<td><input type="text" name="userid" class="form-control" required></td>
	</tr>
	<tr><td>User Name</td>
	<td><input type="text" name="usernm" class="form-control" required></td>
	</tr>
	<tr><td>Password</td>
	<td><input type="password" name="pswd" class="form-control" required></td>
	</tr>
	 <tr>
                    <td>Usertype</td>
                    <td><select name="usertype" required class="form-control">                   
                    <option value="student">Student</option>
                    <option value="exstudent">Ex-student</option>

                        </select>          </td>
                </tr>
       <tr><td>Mobile Number</td>
       	<td><input type="text" name="mobileno"  pattern="^\d{10}$" class="form-control" required></td></tr>
       <tr>
		<td>Email Address</td>       
       <td><input type="text" name="emailid" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$"  class="form-control" required></td>
       </tr>
     
       <tr>
<td>Gender</td>
<td>
  <input type="text" name="userstatus" value="active" hidden="true">
<input type="radio" name="gender" value="Male"   checked="true" required >Male</input>
<input type="radio" name="gender" value="Female"  required>Female</input>
</td>
</tr>
        
       <tr>
       	<td>Branch</td>
       	<td><select name="branch" required class="form-control">
       	<%for(int i=0 ;i<lst.size();i++) {%>
       			<option value=<%=lst.get(i).getBranchname()%>><%=lst.get(i).getBranchname() %></option>
		<%} %>
       	</select></td>
       	
       </tr>
       <tr>
       	<td>Semester</td>
       	<td><select name="semester" required class="form-control">
       				<option value="na">NA</option>
       				<option value="first">First</option>                   
                    <option value="second">Second</option>
                    <option value="third">Third</option>
                    <option value="fourth">Fourth</option>
                    <option value="fifth">Fifth</option>
                    <option value="sixth">Sixth</option>
                    <option value="seventh">Seventh</option>
                    <option value="eighth">Eighth</option>
                    
                    
                        </select> </td>
       </tr>
       <tr>
       	<td>Date Of Birth</td>
       	<td><input type="date" name="dob" class="form-control"></td>
       </tr>
       <tr><td>Photo</td>
       <td>
       <input type="file" name="file" class="form-control"/>
       </td>
	<tr>
	<td><input type="submit" value="Submit" class="btn btn-primary" ></td>
	</tr>
	</table>
</form>
</div></div><div class="col-md-6"><br/><br/>
<img src="images/studReg.png" width="60%"/>
</div>
</div></div>
</body>
</html>