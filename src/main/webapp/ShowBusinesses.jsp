 
<%@page import="java.util.Vector"%>
<%@page import="models.JavaFuns"%>
<%@page import="java.util.List"%>
 
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
 <meta http-equiv='cache-control' content='no-cache'>
<meta http-equiv='expires' content='0'>
<meta http-equiv='pragma' content='no-cache'> 
  
 
</head>
<body>
   <select name="business"   class="form-control" required>
       <% JavaFuns jf=new JavaFuns();
                   Vector v=jf.getValue("select businessType from businesses where category='"+request.getParameter("category").trim()+"'", 1); 
                   for(int i=0;i<v.size();i=i+1)
                   {
                	   %>
                	   <option value="<%=v.elementAt(i).toString().trim() %>"><%=v.elementAt(i).toString().trim() %></option>
                	   <%} %>
                	   </select>
                	    
</body>
</html>