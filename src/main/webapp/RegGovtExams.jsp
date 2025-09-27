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
    <title>Government Exams</title>
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
                Vector exams = jf.getValue("SELECT examName, details, branch, logo, criteria, category FROM govtExams", 6);
                Vector branches = jf.getValue("SELECT branchname FROM branches", 1);
                Vector categories = jf.getValue("SELECT category FROM govtExamsCategories", 1);
    %>
    <div class="container">
        <ul class="nav nav-tabs" id="myTab" role="tablist">
            <li class="nav-item" role="presentation">
                <button class="nav-link active tabbg" id="register-tab" data-bs-toggle="tab" data-bs-target="#register" type="button" role="tab">Register Government Exam</button>
            </li>
            <li class="nav-item" role="presentation">
                <button class="nav-link tabbg"  id="report-tab" data-bs-toggle="tab" data-bs-target="#report" type="button" role="tab">Government Exams Report</button>
            </li>
        </ul>
        <div class="tab-content" id="myTabContent">
            <div class="tab-pane fade show active" id="register" role="tabpanel">
                <div class="col-md-6">
                    <h2>Register Government Exam</h2>
                    <form name="frm" method="post" action="registerExam" enctype="multipart/form-data">
                        <table class="tblform">
                            <tr>
                                <td>Exam Name</td>
                                <td><input type="text" class="form-control" name="examName" required></td>
                            </tr>
                            <tr>
                                <td>Details</td>
                                <td><textarea class="form-control" name="details" required></textarea></td>
                            </tr>
                            <tr>
                                <td>Branch</td>
                                <td>
                                    <select name="branch" class="form-control" required>
                                        <option value="">--select--</option>     
                                        <% for(int i = 0; i < branches.size(); i++) { %>
                                            <option value="<%= branches.elementAt(i).toString().trim() %>">
                                                <%= branches.elementAt(i).toString().trim() %>
                                            </option>
                                        <% } %> 
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td>Logo</td>
                                <td><input type="file" class="form-control" name="file" required></td>
                            </tr>
                            <tr>
                                <td>Criteria</td>
                                <td><input type="text" class="form-control" name="criteria" required></td>
                            </tr>
                            <tr>
                                <td>Category</td>
                                <td>
                                    <select name="category" class="form-control" required>
                                        <option value="">--select--</option>     
                                        <% for(int i = 0; i < categories.size(); i++) { %>
                                            <option value="<%= categories.elementAt(i).toString().trim() %>">
                                                <%= categories.elementAt(i).toString().trim() %>
                                            </option>
                                        <% } %> 
                                    </select>
                                </td>
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
                    <h2>Government Exams Report</h2><div class="row"> 
                    
                         <% for(int i = 0; i < exams.size(); i += 6) { %>
                           <div class="col-md-6"><table class="table table-bordered">
                        <tr><td colspan="3"><h2><%= exams.elementAt(i).toString().trim() %></h2></td></tr>
                     
                            <tr>
                            <td rowspan="5">
                            <%if(exams.elementAt(i+3).toString().trim().contains(".")){ %>
                            <img src="GovtExams/<%= exams.elementAt(i+3).toString().trim() %>" width="100" height="100">
                            <%}else{ %>
                            <i class="fa <%=exams.elementAt(i+3).toString().trim() %> fa-7x mb-4 text-primary"></i>
                            <%} %>
                            </td>
                                </tr><tr>
                               <th>Details</th> <td><%= exams.elementAt(i+1).toString().trim() %></td>
                              </tr> <tr><th>Branch</th> <td><%= exams.elementAt(i+2).toString().trim() %></td>
                              </tr><tr>  <th>Criteria</th> <td><%= exams.elementAt(i+4).toString().trim() %></td>
                             </tr> <tr><th>Category</th>  <td><%= exams.elementAt(i+5).toString().trim() %></td>
                            </tr> </table></div>
                        <% } %>
                   
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
    } catch(Exception ex) { }
    %>
</body>
</html>
