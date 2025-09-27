<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="soham"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/cust.css">
    <title>Student Pending Registration Report</title>
</head>
<body>
    <jsp:include page="Top.jsp"></jsp:include>

    <%
        String userid = String.valueOf(session.getAttribute("userid"));
        if (!userid.equalsIgnoreCase("null")) {
            session.setMaxInactiveInterval(10 * 60);
    %>

    <div class="container mt-4">
        <h2 class="text-center">Students Report</h2>
        <hr>

        <table class="table table-bordered text-center">
            <thead class="bg-secondary text-white">
                <tr>
                    <th>User ID</th>
                    <th>Name</th>
                    <th>Branch</th>
                    <th>Semester</th>
                    <th>Mobile No</th>
                    <th>Email</th>
                    <th>DOB</th>
                    <th>Gender</th>
                    <th>User Status</th>
                    <th>Photo</th>
                    
                </tr>
            </thead>
            <tbody>

                <soham:forEach items="${stal}" var="rec">
                    <tr>
                        <td>${rec.userid}</td>
                        <td>${rec.usernm}</td>
                        <td>${rec.branch}</td>
                        <td>${rec.semester}</td>
                        <td>${rec.mobileno}</td>
                        <td>${rec.emailid}</td>
                        <td>${rec.dob}</td>
                        <td>${rec.gender}</td>
                        <td>
                            <span class="badge bg-warning">${rec.userstatus}</span>
                        </td>
                        <td>
                            <soham:choose>
                                <soham:when test="${not empty rec.path}">
                                     
                                    <img src="Uploads/${rec.path}" alt="Student Photo" 
             class="small-photo rounded-circle" width="50" height="50"
             data-bs-toggle="modal" data-bs-target="#photoModal"
             onclick="showPhoto('Uploads/${rec.path}')">
                                </soham:when>
                                <soham:otherwise>
                                    <span class="text-muted">No Photo</span>
                                </soham:otherwise>
                            </soham:choose>
                        </td>
                         
                    </tr>
                </soham:forEach>

            </tbody>
        </table>
    </div>

    <%
        } else {
    %>
        <h2 class="text-danger text-center">Invalid Session...Login again</h2>
        <div class="text-center">
            <a href="index.jsp" class="btn btn-primary">Login</a>
        </div>
    <%
        }
    %>
    <!-- Bootstrap Modal -->
<div class="modal fade" id="photoModal" tabindex="-1" aria-labelledby="photoModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="photoModalLabel">Student Photo</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body text-center">
        <img id="bigPhoto" src="" width="100%" class="img-fluid" alt="Big Student Photo">
      </div>
    </div>
  </div>
</div>
    
    <script>
function showPhoto(photoSrc) {
    document.getElementById("bigPhoto").src = photoSrc;
}
</script>
    
</body>
</html>
