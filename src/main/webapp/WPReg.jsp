<!DOCTYPE html>
<%@page import="java.util.List"%>
<%@page import="beans.BranchList"%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Working Professional Registration</title>

     <script>
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
        

       
        function makeGetRequest1(st) {
             branch=document.frm.branch.value;
             if(branch=="")
            alert("Select Branch!!");
              
             else
            	 {
             http.open('get', 'GetGovtCategories1.jsp?branch=' + branch+'&currentsts='+st);
             http.onreadystatechange = processResponse11;
             http.send(null);
            	 }
         }

         function processResponse11() {
             if(http.readyState == 4){
                 var response = http.responseText;
                 document.getElementById('govtcategories').innerHTML = response;
             }
         }
         function emailVerification(email) {
             
        	//alert(email);
        	             
        	            if (email.trim() === "") {
        	                alert("Please enter email!!");
        	                return;
        	            }

        	            http.open('GET', 'VerifyEmail?email=' + email);
        	            http.onreadystatechange = processResponse;
        	            http.send(null);
        	        }

        	        function processResponse() {
        	            if(http.readyState === 4 && http.status === 200){
        	                document.getElementById('emailverification1').innerHTML = http.responseText;
        	            }
        	        }
    </script>
        
</head>
<body><jsp:include page="DefaultTop2.jsp"></jsp:include>
<h2>Working Professional Registration</h2>
   <form name="frm" method="post" action="registeruser1" enctype="multipart/form-data">
            <div class="jumbotron">
            <table class="tblform "> 
            
            <tr><td>Userid</td>
            <td>
             <input type="text" class="form-control" name="userid" required>
             </td>
             </tr>
             <tr><td>User Name</td>
<td><input type="text" name="usernm" class="form-control" required></td>
</tr>
<tr><td>Password</td>
<td><input type="password" name="pswd" class="form-control" required></td>
<input type="hidden" name="usertype" value="WorkingProfessional"/>
</tr>
  
     <tr><td>Mobile Number</td>
       <td><input type="text" name="mobileno"  pattern="^\d{10}$" class="form-control" required></td></tr>
     <tr>
  <td>Email Address</td>       
     <td><input type="text" name="emailid" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$"    class="form-control" required>
      
     </td>
     </tr>
   
     <tr>
<td>Gender</td>
<td>
<input type="hidden" name="userstatus" value="active"/>
<input type="radio" name="gender" value="Male"   checked="checked" required >Male 
<input type="radio" name="gender" value="Female"  required>Female 
</td>
</tr>
     <tr>
      <%BranchList bl1 = new BranchList();
     List<BranchList> lst1 = bl1.getBranchList();  %>
       <td>Branch</td>
       <td><select name="branch" required class="form-control">
       <option value=""><--Select--></option>
       <%for(int i=0 ;i<lst1.size();i++) {%>
           <option value=<%=lst1.get(i).getBranchname()%>><%=lst1.get(i).getBranchname() %></option>
  <%} %>
       </select></td>
       
     </tr>
     
     <tr><td>Job Post</td>
	<td> <textarea class="form-control" name="job_post" required></textarea>
	<input type="hidden" name="currentsts"  value="Placed"/>
	</td>
	</tr> 
	<tr><td>Job Details</td>
	<td> <textarea class="form-control" name="job_details" required></textarea></td>
	</tr>
     <tr>
       <td>Date Of Birth</td>
       <td><input type="date" name="dob" class="form-control"></td>
     </tr>
     <tr><td>Photo</td>
     <td>
     <input type="file" name="file" class="form-control"/>
     </td></tr>  
<tr>
<td><input type="submit" value="Submit" class="btn btn-secondary btn-theme2" ></td>
</tr>
</table>
</form>
</body>
</html>
