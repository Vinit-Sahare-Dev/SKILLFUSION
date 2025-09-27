<%@page import="models.JavaFuns"%>
<%@page import="java.util.Vector"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
 <%
 String currentsts=request.getParameter("currentsts").toString().trim();
 String branch=request.getParameter("branch").toString().trim();
 
JavaFuns jf=new JavaFuns();
Vector v2=jf.getValue("select distinct(category) from   govtExamsCategories where branch='"+request.getParameter("branch").trim()+"'" , 1);
if(currentsts.equals("Placed"))
{%><table>
		<tr><td>Government Exam Category</td>
		<td><select name="gcate" class="form-control">
		<option value=""><--Select--></option>
 
		<%
	for(int i=0;i<v2.size();i++)
	{
		%>
		<option value="<%=v2.elementAt(i).toString().trim() %>"><%=v2.elementAt(i).toString().trim() %></option>
		
		<%
	}%></select>
	</td></tr>
	<tr><td>Job Post</td>
	<td> <textarea class="form-control" name="job_post" required></textarea></td>
	</tr> 
	<tr><td>Job Details</td>
	<td> <textarea class="form-control" name="job_details" required></textarea></td>
	</tr>
	</table>
	<%}
else{
for(int i=0;i<v2.size();i++)
{
%>

<div class="col-sm-6">
<label>
  <input type="checkbox" class="checkbox" value="<%=v2.elementAt(i).toString().trim() %>" name="selectedCategories" />
   <%=v2.elementAt(i).toString().trim() %>
                </label>
               </div>
<%} }%>
</body>
</html>