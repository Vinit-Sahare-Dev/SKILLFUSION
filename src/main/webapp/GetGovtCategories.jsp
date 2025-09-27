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
JavaFuns jf=new JavaFuns();
Vector v2=jf.getValue("select distinct(category) from   govtExamsCategories where branch='"+request.getParameter("branch").trim()+"'" , 1);
for(int i=0;i<v2.size();i++)
{
%>

<div class="col-sm-6">
<label>
  <input type="checkbox" class="checkbox" value="<%=v2.elementAt(i).toString().trim() %>" name="selectedCategories" />
   <%=v2.elementAt(i).toString().trim() %>
                </label>
               </div>
<%} %>
</body>
</html>