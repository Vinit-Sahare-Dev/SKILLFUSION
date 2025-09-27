<%@page import="java.util.Vector"%>
<%@page import="models.JavaFuns"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/cust.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <title>Success Stories</title>
</head>
<body>
    <jsp:include page="Top.jsp"></jsp:include>
    <% 
        try { 
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setDateHeader("Expires", -1);
            if (session.getAttribute("userid") == null) {
                response.sendRedirect("index.jsp");
            }

            String userid = String.valueOf(session.getAttribute("userid"));

            if (!userid.equalsIgnoreCase("null")) {    
                session.setMaxInactiveInterval(10 * 60);
                JavaFuns jf = new JavaFuns();
                Vector stories = jf.getValue("SELECT alumni_name, story, profession, photo, dt, passout_year FROM success_stories", 6);
    %>
    <div class="container">
        <ul class="nav nav-tabs" id="myTab" role="tablist">
            <li class="nav-item" role="presentation">
                <button class="nav-link active tabbg" id="register-tab" data-bs-toggle="tab" data-bs-target="#register" type="button" role="tab">Register Success Story</button>
            </li>
            <li class="nav-item" role="presentation">
                <button class="nav-link tabbg" id="report-tab" data-bs-toggle="tab" data-bs-target="#report" type="button" role="tab">Success Stories Report</button>
            </li>
        </ul>
        <div class="tab-content" id="myTabContent">
            <div class="tab-pane fade show active" id="register" role="tabpanel">
                <div class="col-md-6">
                    <h2>Register Success Story</h2>
                    <form name="frm" method="post" action="RegSuccessStories1" enctype="multipart/form-data">
                        <table class="tblform">
                            <tr>
                                <td>Alumni Name</td>
                                <td><input type="text" class="form-control" name="alumniName" required></td>
                            </tr>
                            <tr>
                                <td>Story</td>
                                <td><textarea class="form-control" name="story" required></textarea></td>
                            </tr>
                            <tr>
                                <td>Profession</td>
                                <td><input type="text" class="form-control" name="profession" required></td>
                            </tr>
                            <tr>
                                <td>Photo</td>
                                <td><input type="file" class="form-control" name="file" required></td>
                            </tr>
                            <tr>
                                <td>Date</td>
                                <td><input type="date" class="form-control" name="dt" required></td>
                            </tr>
                            <tr>
                                <td>Passout Year</td>
                                <td><select name="passoutYear" class="form-control">
    <option value="">--Select Year--</option>
    <%
        int currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        for (int i = currentYear - 1; i >= 2000; i--) {
    %>
        <option value="<%= i %>"><%= i %></option>
    <%
        }
    %>
</select></td>
                            </tr>
                            <tr>
                                <td><input type="submit" class="btn btn-primary" value="Submit"></td>
                            </tr>
                        </table>
                    </form>
                </div>
            </div>
            <div class="tab-pane fade" id="report" role="tabpanel">
                <div class="col-md-12">
                    <h2>Success Stories Report</h2>
                    <div class="row"> 
                        <% for(int i = 0; i < stories.size(); i += 6) { %>
                            <div class="col-md-6">
                                <table class="table table-bordered">
                                    <tr>
                                        <td colspan="3">
                                            <h2><%= stories.elementAt(i).toString().trim() %></h2>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td rowspan="2">
                                            <% if(stories.elementAt(i+3).toString().trim().contains(".")) { %>
                                                <img src="SuccessStories/<%= stories.elementAt(i+3).toString().trim() %>" width="100" height="100">
                                            <% } else { %>
                                                <i class="fa <%= stories.elementAt(i+3).toString().trim() %> fa-7x mb-4 text-primary"></i>
                                            <% } %>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="2"><b>Story:</b> <%= stories.elementAt(i+1).toString().trim() %></td>
                                    </tr>
                                    <tr>
                                        <td><b>Profession:</b> <%= stories.elementAt(i+2).toString().trim() %></td>
                                     
                                        <td><b>Date:</b> <%= stories.elementAt(i+4).toString().trim() %></td>
                                        <td><b>Passout Year:</b> <%= stories.elementAt(i+5).toString().trim() %></td>
                                    </tr>
                                </table>
                            </div>
                        <% } %>
                    </div>
                </div>
            </div>
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
    } catch(Exception ex) { ex.printStackTrace(); }
    %>
</body>
</html>
