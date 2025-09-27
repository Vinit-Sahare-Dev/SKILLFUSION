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
    <title>Government Exam Categories</title>
</head>
<body>
    <jsp:include page="Top.jsp"></jsp:include>
    <% try { 
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
            Vector categories = jf.getValue("select category, details, branch, logo from govtExamsCategories", 4);
            Vector v=jf.getValue("select branchname from branches", 1);
    %>
    <div class="container">
        <ul class="nav nav-tabs" id="myTab" role="tablist">
            <li class="nav-item" role="presentation">
                <button class="nav-link active tabbg" id="register-tab" data-bs-toggle="tab" data-bs-target="#register" type="button" role="tab">Register Government Exam Category</button>
            </li>
            <li class="nav-item" role="presentation">
                <button class="nav-link tabbg" id="report-tab" data-bs-toggle="tab" data-bs-target="#report" type="button" role="tab">Government Exam Categories Report</button>
            </li>
        </ul>
        <div class="tab-content" id="myTabContent">
            <div class="tab-pane fade show active" id="register" role="tabpanel">
                <div class="col-md-6">
                    <h2>Register Government Exam Category</h2>
                    <form name="frm" method="post" action="registerCategory" enctype="multipart/form-data">
                        <table class="tblform">
                            <tr>
                                <td>Category Name</td>
                                <td><input type="text" class="form-control" name="category" required></td>
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
                                        <%for(int i=0;i<v.size();i++){ %>
                                            <option value="<%=v.elementAt(i).toString().trim() %>"><%=v.elementAt(i).toString().trim() %></option>
                                        <%} %> 
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td>Logo</td>
                                <td><input type="file" class="form-control" name="file" required></td>
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
                    <h2>Government Exam Categories Report</h2>
                     <div class="row"> 
                        
                        <% for(int i = 0; i < categories.size(); i=i+ 4) { %>
                           <div class="col-md-6">
                    <table class="table table-bordered"> <tr><tr>
                                <td colspan="2"> <h2><%= categories.elementAt(i).toString().trim() %></h2></td>
                            </tr>
                            <td rowspan="3">
                            <%if(categories.elementAt(i+3).toString().trim().contains(".")){ %>
                            <img src="GovtCategories/<%= categories.elementAt(i+3).toString().trim() %>" width="100" height="100">
                            <%}else{ %>
                            <i class="fa <%=categories.elementAt(i+3).toString().trim() %> fa-7x mb-4 text-primary"></i>
                            <%} %>
                            </td></tr><tr>    <td><b>Details:</b><%= categories.elementAt(i+1).toString().trim() %></td>
                              </tr><tr>  <td><b>Branch:</b><%= categories.elementAt(i+2).toString().trim() %></td>
                                
                            </tr></table></div>
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
    } catch(Exception ex) {ex.printStackTrace(); }
    %>
</body>
</html>
