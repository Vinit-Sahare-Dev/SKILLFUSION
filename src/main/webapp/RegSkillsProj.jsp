<%@page import="java.util.Vector"%>
<%@page import="models.JavaFuns"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="soham"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/cust.css">
    <title> Followers</title>
</head>
<body>
    <jsp:include page="Top2.jsp"></jsp:include>

    <%
        String userid = String.valueOf(session.getAttribute("userid"));
        if (!userid.equalsIgnoreCase("null")) {
            session.setMaxInactiveInterval(10 * 60);
    %>
 <div class="section">
    <div class="container mt-4">
          <h3>Register/Update Skills</h3>
          <h4>Project Title : <%=request.getParameter("title") %></h4>
            <form name="frm" method="post" action="RegSkills_proj">
            <input type="hidden" name="pid" value="<%=request.getParameter("pid").toString().trim() %>"/>
            <div class="row">
             <%
             JavaFuns jf=new JavaFuns();
             Vector v5=jf.getValue("select skill from proj_skills  where pid="+request.getParameter("pid").toString().trim(), 1);
Vector v4=jf.getValue("select distinct(skill) from   skills where branch like '%"+session.getAttribute("branch").toString().trim()+"%'" , 1);
for(int i=0;i<v4.size();i++)
{
	boolean isChecked=false;

    String skill = v4.elementAt(i).toString().trim();
    if(v5.size()>0){
    isChecked= v5.contains(skill); // Check if the skill is already selected
    }
	 
 %>

<div class="col-sm-3"> <label>
            <input type="checkbox" class="checkbox" value="<%= skill %>" name="selectedSkills"
                <%= isChecked ? "checked" : "" %> />
            <%= skill %>
        </label>
               </div>
<%} %></div>
            <input type="submit" value="Submit" class="button"/>
            
            </form>
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
