<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="models.WorkingProfessionals"%>

<%@page import="models.Projects"%> 
<%@page import="java.util.Date"%>
<%@page import="models.JavaFuns"%>
<%@page import="java.util.Vector"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Alumni Dashboard</title>
     <style>
          </style>
    <script>
        function openPopup(type) {
            document.getElementById('popup-overlay').style.display = 'flex';
            document.getElementById(type + '-popup').style.display = 'block';
        }
        function closePopup(type) {
            document.getElementById('popup-overlay').style.display = 'none';
            document.getElementById(type + '-popup').style.display = 'none';
        }
    </script>
   
<script>
let currentPostId = 0;

function openComments(postId) {
    currentPostId = postId;  // Store post ID for later use
    $('#commentModal').modal('show');  // Open modal

    // Fetch comments from server
    $.ajax({
        url: "/getComments",
        type: "GET",
        data: { postId: postId },
        dataType: "html",  // Expect HTML, since JSP renders it
        success: function(response) {
            $("#commentList").html(response); // Directly update the comments section
        },
        error: function(xhr, status, error) {
            console.error("Error loading comments:", xhr.responseText);
            $("#commentList").html("<p>Error loading comments.</p>");
        }
    });
}

function submitComment() {
    let commentText = $("#commentText").val().trim();
    if (commentText === "") {
        alert("Please enter a comment.");
        return;
    }

    // Send comment to the server
    $.ajax({
        url: "/addComment",
        type: "POST",
        data: { postId: currentPostId, comment: commentText },
        dataType: "html",
        success: function(response) {
            if (response.error) {
                alert("Failed to post comment: " + response.error);
                return;
            }

            let formattedDate = new Date().toLocaleString();

            // Append new comment instead of reloading all
            $("#commentList").prepend(response);

            $("#commentText").val(""); // Clear input
        },
        error: function(xhr, status, error) {
            console.error("Error posting comment:", xhr.responseText);
            alert("Failed to post comment.");
        }
    });
}

</script>
   
     <script type="text/javascript">
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
    

    function likePost(st1) {
       // st=document.frm.state.value;
       alert(st1);
       document.getElementById("postid").value = ''+st1;
       alert(document.getElementById("postid").value);
        http.open('get', 'likePost?pid=' + st1);
        http.onreadystatechange = processResponse11;
        http.send(null);
    }

    function processResponse11() {
        if(http.readyState == 4){
            var response = http.responseText;
            st=document.getElementById("postid").value;
             alert(st);
            document.getElementById("like-count-" + st).innerHTML = response
        }
    } 
    function commentPost(postId) {
        let commentText = document.getElementById("comment-input-" + postId).value;
        if (!commentText.trim()) {
            alert("Comment cannot be empty!");
            return;
        }

         
</script>
</head>
<body><jsp:include page="Top1.jsp" />
<input type="hidden" name="postid" id="postid"/>
<div class="row">
<div class="col-md-3">

    <div class="sidebar">
        <div class="profile">
        <img src='Uploads/<%=session.getAttribute("photo").toString()%>' class="img-responsive img-thumbnail rounded-circle" width="100px"/>
            <h2><%=session.getAttribute("username").toString()%>(<%=session.getAttribute("usertype").toString().trim()%>)</h2>
            	 
	<%
            	 	if(session.getAttribute("currentsts").toString().trim().equals("Placed")) {
            	 	%>
	<p><b>Job Post:</b><%=session.getAttribute("job_post").toString().trim()%></p>
	<p><b>Job Details:</b><%=session.getAttribute("job_details").toString().trim()%></p>
	 <%
	}else{
	%>
	   
	<%
     	}
     	%><a href="Stud_Profile.jsp" class="button"><i class="fa fa-user-circle" ></i> Update Profile</a>
        </div>
       
    </div>
</div><div class="col-md-9">
    <div class="main-content">
        <div class="section">
        
            <h2>Welcome to Skill Fusion</h2>
            <div style="background-image:url('img/about-1.jpg');background-position:center;background-size:contain; width:100%; color:transparent;min-height: 300px">dsf</div>
             
            
        </div>
        <div class="section">
             
            <div class="post-options">
                 
                <h2>Pending Project Joining Requests</h2>
                <%JavaFuns jf1=new JavaFuns();
                Vector vreq=jf1.getValue("select * from requests where userid1='"+session.getAttribute("userid").toString().trim()+"' and sts='pending'", 11);
                
                %> </div><div class="post-options">
                <table class="table table-bordered">
                <tr><th>Sender</th><th>Project Title</th><th>Date</th><th></th>
               <th></th> </tr>
                <% for(int i=0;i<vreq.size();i=i+11){ %>
                <tr>
                <td><%=vreq.elementAt(i+3).toString() %></td>
                <td><%=vreq.elementAt(i+6).toString() %></td>
                <td><%=vreq.elementAt(i+7).toString() %></td>
                 
                <td><a href="acceptReq?reqid=<%=vreq.elementAt(i).toString() %>&userid=<%=vreq.elementAt(i+1).toString() %>">Accept</a> </td>
                </tr>
                <%} %>
                </table> 
            </div>
        </div>
         <div class="section">
             
            <div class="post-options">
                 
                <h2>Accepted Projects</h2>
                <% 
                Vector vreq1=jf1.getValue("select * from requests where userid1='"+session.getAttribute("userid").toString().trim()+"' and sts='accepted'", 11);
                
                %> </div><div class="post-options">
                <table class="table table-bordered">
                <tr><th>Sender</th><th>Project Title</th><th>Date</th><th></th>
               <th></th> </tr>
                <% for(int i=0;i<vreq1.size();i=i+11){ %>
                <tr>
                <td><%=vreq1.elementAt(i+3).toString() %></td>
                <td><%=vreq1.elementAt(i+6).toString() %></td>
                <td><%=vreq1.elementAt(i+7).toString() %></td>
                 
                <td><a href="TeamMembers.jsp?pid=<%=vreq1.elementAt(i+5).toString() %>&title=<%=vreq1.elementAt(i+6).toString() %>">Team Members</a> </td>
                <td><a href="Communicate?pid=<%=vreq1.elementAt(i+5).toString() %>&title=<%=vreq1.elementAt(i+6).toString() %>">Communicate</a> </td>
                </tr>
                <%} %>
                </table> 
            </div>
        </div>
        <div class="section">
            <h3>Projects</h3>
            <div class="post-options">
                <button class="button" onclick="openPopup('post')">Register Project</button>
                <br/>
                <h2>My Projects</h2>
                <% 
                Vector vp=jf1.getValue("select * from projects where userid='"+session.getAttribute("userid").toString().trim()+"'", 7);
                
                %> </div><div class="post-options">
<table class="table table-bordered">
                <tr><th>Title</th><th>Details</th><th>Technologies</th><th>Date</th>
               <th></th> <th></th><th></th><th></th></tr>
                <% for(int i=0;i<vp.size();i=i+7){ %>
                <tr>
                <td><%=vp.elementAt(i+2).toString() %></td>
                <td><%=vp.elementAt(i+3).toString() %></td>
                <td><%=vp.elementAt(i+4).toString() %></td>
                <td><%=vp.elementAt(i+5).toString() %> <%=vp.elementAt(i+6).toString() %></td>
                <td><a href="RegSkillsProj.jsp?pid=<%=vp.elementAt(i).toString() %>&title=<%=vp.elementAt(i+2).toString() %>">Add Required Skills</a> </td>
                <td><a href="RecommendedUsers.jsp?pid=<%=vp.elementAt(i).toString() %>&title=<%=vp.elementAt(i+2).toString() %>">Recommended Users</a> </td>
                <td><a href="TeamMembers.jsp?pid=<%=vp.elementAt(i).toString() %>&title=<%=vp.elementAt(i+2).toString() %>">Team Members</a> </td>
                <td><a href="Communicate?pid=<%=vp.elementAt(i).toString() %>&title=<%=vp.elementAt(i+2).toString() %>">Communicate</a> </td>
                </tr>
                <%} %>
                </table>
            </div>
        </div>
         
   
    <!-- Popup Overlay -->
    <div id="popup-overlay" class="popup-overlay">
        <div id="post-popup" class="popup" style="display: none;">
        <button class="close" onclick="closePopup('post')">Close</button>
            <h3>Register Project</h3>
            <form name="frm" method="post" action="RegProj">
            <textarea name="title" required placeholder="Write your title..."></textarea>
            <textarea name="tech" required placeholder="Required Technolgoies..."></textarea>
            <textarea name="details" required placeholder="Details..."></textarea>
            <input type="submit" value="Submit" class="button"/>
            
            </form>
            
        </div>
        <div id="live-popup" class="popup" style="display: none;">
            <h3>Share a Video</h3>
            <button class="close" onclick="closePopup('live')">Close</button>
            <form name="frm1" method="post" action="RegPosts_vlink">
            <input type="text" required name="livefeed" placeholder="Enter YouTube URL">
            <textarea name="title" required placeholder="Title"></textarea>
            <input type="submit" value="Post" class="button"/>
            
            </form>
        </div>
        <div id="photo-popup" class="popup" style="display: none;">
        <button class="close" onclick="closePopup('photo')">Close</button>
            <h3>Upload a Photo</h3>
            <form name="frm2" method="post" action="RegPosts_Media" enctype="multipart/form-data">
            <input type="file" required name="file" accept="image/*">
            <textarea name="title" required placeholder="Title"></textarea>
            <input type="submit" value="Post" class="button"/>
            
            </form>
        </div>
        <div id="video1-popup" class="popup" style="display: none;">
        <button class="close" onclick="closePopup('video1')">Close</button>
            <h3>Upload a Video</h3>
            <form name="frm2" method="post" action="RegPosts_Video" enctype="multipart/form-data">
            <input type="file" required name="file" accept="video/*">
            <textarea name="title" required placeholder="Title"></textarea>
            <input type="submit" value="Post" class="button"/>
            
            </form>
        </div>
    </div></div>
    <!-- Comment Modal -->
<div class="modal fade" id="commentModal" tabindex="-1" aria-labelledby="commentModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="commentModalLabel">Post Comments</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        
        <!-- Comments Section -->
        <div id="commentList">
          <p>Loading comments...</p>
        </div>

        <!-- Add Comment Form -->
        <div class="mt-3">
          <textarea id="commentText" required class="form-control" placeholder="Write a comment..." rows="3"></textarea>
          <button class="btn btn-success mt-2" onclick="submitComment()">Post Comment</button>
        </div>

      </div>
    </div>
  </div>
</div>
    
        <!-- Back to Top -->
        <a href="#" class="btn btn-secondary btn-square rounded-circle back-to-top"><i class="fa fa-arrow-up text-white"></i></a>

        
        <!-- JavaScript Libraries -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="lib/wow/wow.min.js"></script>
        <script src="lib/easing/easing.min.js"></script>
        <script src="lib/waypoints/waypoints.min.js"></script>
        <script src="lib/owlcarousel/owl.carousel.min.js"></script>

        <!-- Template Javascript -->
        <script src="js/main.js"></script>
</body>
</html>
