<%@page import="beans.SkillRecommendation"%>
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
    <title>  Dashboard</title>
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
            <h2><%=session.getAttribute("username").toString()%><br/>(<%=session.getAttribute("usertype").toString().trim()%>)</h2>
            	 
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
     	%>
        </div>
       
    </div>
</div><div class="col-md-9">
    <div class="main-content">
        <div class="section">
        
            <h2>Welcome to Skill Fusion</h2>
            <div style="background-image:url('img/about-1.jpg');background-position:center;background-size:contain; width:100%; color:transparent;min-height: 300px">dsf</div>
            
            <a href="Stud_Profile.jsp" class="button"><i class="fa fa-user-circle" ></i> Update Profile</a>
        </div>
        <div class="section">
            
              <h3 class="text-center mb-4">Recommended Users</h3>
    <div class="row"><table class="tblform"><tr>
    
    <%
    String uids="";
    int pid=Integer.parseInt(request.getParameter("pid").toString().trim());
    List<Map.Entry<String, Integer>> recommendedStudents = SkillRecommendation.recommendStudentsForProject(pid, 5);

    for (Map.Entry<String, Integer> student : recommendedStudents) {
        System.out.println("Student ID: " + student.getKey() + ", Match Score: " + student.getValue());
        if(uids=="")
        {
        	uids="'"+student.getKey()+"'";
        }
        else
        {
        	uids+=",'"+student.getKey()+"'";
        }
    }
    JavaFuns jf=new JavaFuns();
    String qr="select usernm,branch,mobileno,emailid,photo,currentsts,job_post,job_details,userid from  studentpersonal where userid in ("+uids+") order by positiveComments desc";
    System.out.println("qr="+qr);
    Vector v= jf.getValue(qr, 9);
      %>
        <% int cnt=1;
        for (int i=0;i<v.size();i=i+9) {
        Vector vsk=jf.getValue("(select GROUP_CONCAT(skill SEPARATOR ', ') AS skills  from stud_skills where userid='"+v.elementAt(8).toString().trim()+"')",1);	
        String skill=vsk.elementAt(0).toString().trim();
        if(cnt%4==0){
        	%></tr><tr><%
        }
        	%>
        
          <td>  <div class="col-md-12 mb-4">
                <div class="card alumni-card"> 
                 
                       <form method="post" action="SendRequest">
                      <input type="hidden" name="userid1" value="<%=v.elementAt(i+8).toString().trim() %>"/>
                      <input type="hidden" name="email1" value="<%=v.elementAt(i+3).toString().trim() %>"/>
                      <input type="hidden" name="username1" value="<%=v.elementAt(i).toString().trim() %>"/>
                      <input type="hidden" name="userid" value="<%=session.getAttribute("userid").toString().trim() %>"/>
                      <input type="hidden" name="username" value="<%=session.getAttribute("username").toString().trim() %>"/>
                      <input type="hidden" name="proj_id" value="<%=request.getParameter("pid").trim() %>"/>
                      <input type="hidden" name="proj_title" value="<%=request.getParameter("title").trim() %>"/>
                      <input type="submit" width="100%" value="Send Request to join Project" class="button follow"/>
                      </form>
                      
                    <img src="Uploads/<%=v.elementAt(i+4).toString().trim() %>" alt="Alumni Photo" class="alumni-img">
                    <div class="alumni-info">
                        <h5 class="text-primary"><%=v.elementAt(i+0).toString().trim() %></h5>
                         <p class="job-post"><b>Branch : <b> <%=v.elementAt(i+1).toString().trim() %></p>
                        <p class="job-post"><b>Mobile : <b><%=v.elementAt(i+2).toString().trim() %></p>
                         <p class="job-post"><b>Email : <b><%=v.elementAt(i+3).toString().trim() %></p>
	<p class="job-post"><b>Current Status : <b><%=v.elementAt(i+5).toString().trim() %></p>                     
                        <p class="job-post"><b>Designation : <b> <%=v.elementAt(i+6).toString().trim() %></p>
                        <p class="job-post"><b>Job Details : <b><%=v.elementAt(i+7).toString().trim() %></p>
                        <p class="job-post"><b>Skills: <b><%=skill %></p>
                      </div>
                </div>
            </div></td>
        <% cnt++;} %></tr></table>
        </div>
        
        <script>
    function equalizeHeights() {
        let maxHeight = 0;
        let items = document.querySelectorAll(".testimonial-item");

        // Reset height to avoid accumulation issues
        items.forEach(item => item.style.height = "auto");

        // Find the maximum height
        items.forEach(item => {
            if (item.offsetHeight > maxHeight) {
                maxHeight = item.offsetHeight;
            }
        });

        // Apply the maximum height to all items
        items.forEach(item => item.style.height = maxHeight + "px");
    }

    // Run after the page loads
    window.onload = equalizeHeights;
    window.onresize = equalizeHeights; // Adjust on resize
</script>
 
        
    </div>

   
    <!-- Popup Overlay -->
    <div id="popup-overlay" class="popup-overlay">
        <div id="post-popup" class="popup" style="display: none;">
        <button class="close" onclick="closePopup('post')">Close</button>
            <h3>Create a Post</h3>
            <form name="frm" method="post" action="RegPosts_Txt">
            <textarea name="title" required placeholder="Write your post..."></textarea>
            <input type="submit" value="Post" class="button"/>
            
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
