
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
<title>Communication</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    $(document).ready(function(){
        $("#reviewTab").click(function(){
            $("#submitReviewSection").hide();
            $("#reviewReportSection").show();
            $("#sentItems").hide();
        });
        $("#submitTab").click(function(){
            $("#reviewReportSection").hide();
            $("#submitReviewSection").show();
            $("#sentItems").hide();
        });
        $("#sentTab").click(function(){
            $("#sentItems").show();
            $("#reviewReportSection").hide();
            $("#submitReviewSection").hide();
        });
    });
</script>
</head>

<body>
<jsp:include page="Top.jsp"></jsp:include>

<% 
try {
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setDateHeader("Expires", -1);

    if(session.getAttribute("userid") == null) {
        response.sendRedirect("index.jsp");
    }

    String userid = String.valueOf(session.getAttribute("userid"));

    if(!userid.equalsIgnoreCase("null")) {    
        session.setMaxInactiveInterval(10*60);
        int pid=Integer.parseInt(request.getAttribute("pid").toString().trim());
        
        
        JavaFuns jf=new JavaFuns();
        String uid=session.getAttribute("userid").toString().trim();
        String qr="select userid,usernm from  studentpersonal where userid in (select userid1 from requests where proj_id="+pid+" and sts='accepted' and userid1<>'"+uid.trim()+"') union";
        qr+=" select userid,usernm from  studentpersonal where userid in (select userid from projects where pid=2 and userid<>'"+uid+"')";
        System.out.println("qr="+qr);
        Vector v= jf.getValue(qr, 2);
  
%>

<div class="container mt-4">
    <!-- Tabs -->
    <ul class="nav nav-tabs">
        <li class="nav-item">
            <a class="nav-link active" href="#" id="submitTab">Compose Message</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="#" id="reviewTab">Inbox</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="#" id="sentTab">Sent Items</a>
        </li>
    </ul>

    <!-- Submit Review Section -->
    <div id="submitReviewSection">
        <div class="row mt-4">
            <div class="col-md-12">
                <h2>Compose New Message</h2>
                <div class="form-group">
                    <form name="frm" method="post" action="RegCommunicate" enctype="multipart/form-data">
                        <table class="tblform">
                            <tr>
                                <td>Subject</td>
                                <td>
                                    <input type="text" class="form-control" name="sub"  required/>
                                </td>
                            </tr> 
                            <tr><td>Recipient</td>
                            <td>
                            <select name="userid1" class="form-control" required>
                            <option value=""><--select--></option>
                            <%for(int i=0;i<v.size();i=i+2){ %>
                            <option value="<%=v.elementAt(i).toString().trim() %>,<%=v.elementAt(i+1).toString().trim() %>"><%=v.elementAt(i+1).toString().trim() %></option>
                            <%} %>
                            
                            </select>
                            </td></tr>
                            <tr><td>Attachment</td><td>
                            <input type="file" name="file" class="form-control"/>
                            </td></tr>
                            <tr>
                                <td>Message</td>
                                <td>
                                    <input type="hidden" name="userid" value="<%=session.getAttribute("userid").toString().trim() %>"/>
                                    <input type="hidden" name="username" value="<%=session.getAttribute("username").toString().trim() %>"/>
                                     
                                    <textarea name="msg" required class="form-control" required cols="500"></textarea>
                                </td>
                            </tr>
                           
                            <tr>
                                <td><input type="submit" value="Submit" class="btn btn-primary"></td>
                            </tr>
                        </table>
                    </form>
                </div>
            </div>
            
        </div>
    </div>

    <!-- Review Report Section (Initially Hidden) -->
    <div id="reviewReportSection" style="display: none;" class="mt-4">
        <h2>Inbox</h2>
        <table class="table table-bordered">
        <tr>
        <th>Sender</th>
        <th>Subject</th>
        <th>Message</th>
        <th>Date</th>
        <th>Attachment</th>
        </tr>
            <%
                List<Message> lst = (List) request.getAttribute("lst");
            %>
            <c:forEach var="userdsc" items="${lst}">
                <tr>
                    <td> ${userdsc.getUsername()}</td>
                    <td> ${userdsc.getSub()}</td>
                    <td> ${userdsc.getMsg()}</td>
                    <td> ${userdsc.getDt()}</td>
                    <td> 
                    <c:choose>
    <c:when test="${userdsc.getAttachment() eq 'NA'}">
        NA
    </c:when>
    <c:otherwise>
        <a href="Attachments/${userdsc.getAttachment()}" target="_blank">Download Attachment</a>
    </c:otherwise>
</c:choose>
                    
                     </td>
                </tr>
                 
            </c:forEach></table>
         
    </div>
    <div id="sentItems" style="display: none;" class="mt-4">
        <h2>Sent Items</h2>
        <table class="table table-bordered">
        <tr>
        <th>Recipient</th>
        <th>Subject</th>
        <th>Message</th>
        <th>Date</th>
        <th>Attachment</th>
        </tr>
            <%
                List<Message> lst1 = (List) request.getAttribute("lst1");
            %>
            <c:forEach var="userdsc" items="${lst1}">
                <tr>
                    <td> ${userdsc.getUsername1()}</td>
                    <td> ${userdsc.getSub()}</td>
                    <td> ${userdsc.getMsg()}</td>
                    <td> ${userdsc.getDt()}</td>
                    <td> 
                    <c:choose>
    <c:when test="${userdsc.getAttachment() eq 'NA'}">
        NA
    </c:when>
    <c:otherwise>
        <a href="Attachments/${userdsc.getAttachment()}" target="_blank">Download Attachment</a>
    </c:otherwise>
</c:choose>
                    
                     </td>
                </tr>
                 
            </c:forEach></table>
    </div>
</div>

<% 
    } else {
%>
    <h2>Invalid Session...Login again</h2>
    <br>
    <a href="index.jsp">Login</a>
<%
    }
} catch(Exception ex) {
    System.out.println("Error: " + ex.getMessage());
}
%>

</body>
</html>
