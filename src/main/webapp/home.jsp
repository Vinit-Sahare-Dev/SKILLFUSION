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
         .sidebar { width: 100%; padding: 20px; background: #f4f4f4; height: 100vh; }
        .main-content { width: 100%; padding: 20px; }
        .section { padding: 20px; margin: 10px 0; border: 1px solid #ddd; border-radius: 5px; }
        .profile { text-align: center; }
        .post-options { display: flex; gap: 10px; }
        .button { padding: 10px; border: none; background: #0073b1; color: white; border-radius: 5px; cursor: pointer; }
        .popup-overlay { display: none; position: fixed; top: 0; left: 0; width: 100%; height: 100%; background: rgba(0, 0, 0, 0.5); backdrop-filter: blur(5px); justify-content: center; align-items: center; }
        .popup { background: white; padding: 20px; border-radius: 10px; width: 400px; text-align: center; box-shadow: 0px 0px 20px rgba(0, 0, 0, 0.2); }
        .popup textarea, .popup input { width: 100%; margin-top: 10px; padding: 8px; border-radius: 5px; border: 1px solid #ddd; }
        .close { background: red; color: white; padding: 5px; border: none; cursor: pointer; border-radius: 5px; margin-top: 10px; align-content: right;float: right;}
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
    

    function likePost(st) {
       // st=document.frm.state.value;
       alert(st);
       document.getElementById("postid").value = ''+st;
       alert(document.getElementById("postid").value);
        http.open('get', 'likePost?pid=' + st);
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
   
    
</head>
<body><jsp:include page="Top1.jsp" />
<input type="hidden" name="postid" id="postid"/>
<div class="row">
<div class="col-md-3">

    <div class="sidebar">
        <div class="profile">
        <img src='Uploads/<%=session.getAttribute("photo").toString() %>' class="img-responsive img-thumbnail rounded-circle" width="100px"/>
            <h2><%=session.getAttribute("username").toString() %></h2>
            	 
	<%if(session.getAttribute("currentsts").toString().trim().equals("Placed")) {%>
	<p><b>Job Post:</b><%=session.getAttribute("job_post").toString().trim() %></p>
	<p><b>Job Details:</b><%=session.getAttribute("job_details").toString().trim() %></p>
	<p><b>Exams Given:</b><%=session.getAttribute("exams").toString().trim() %></p>
	<%}else{ %>
	 <p><b>Preparing for Exams:</b><%=session.getAttribute("exams").toString().trim() %></p>
     
	<%} %>
        </div>
       
    </div>
</div><div class="col-md-9">
    <div class="main-content">
        <div class="section">
        
            <h2>Welcome to Skill Fusion</h2>
            <div style="background-image:url('img/about-1.jpg');background-position:center;background-size:contain; width:100%; color:transparent;min-height: 300px">dsf</div>
            <p>Add your work experience and skills to get discovered by students.</p>
            <button class="button">Update Profile</button>
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
            <textarea name="title" placeholder="Write your post..."></textarea>
            <input type="submit" value="Post" class="button"/>
            
            </form>
            
        </div>
        <div id="live-popup" class="popup" style="display: none;">
            <h3>Share a Video</h3>
            <button class="close" onclick="closePopup('live')">Close</button>
            <form name="frm1" method="post" action="RegPosts_vlink">
            <input type="text" name="livefeed" placeholder="Enter YouTube URL">
            <textarea name="title" placeholder="Title"></textarea>
            <input type="submit" value="Post" class="button"/>
            
            </form>
        </div>
        <div id="photo-popup" class="popup" style="display: none;">
        <button class="close" onclick="closePopup('photo')">Close</button>
            <h3>Upload a Photo</h3>
            <form name="frm2" method="post" action="RegPosts_Media" enctype="multipart/form-data">
            <input type="file" name="file" accept="image/*">
            <textarea name="title" placeholder="Title"></textarea>
            <input type="submit" value="Post" class="button"/>
            
            </form>
        </div>
        <div id="video1-popup" class="popup" style="display: none;">
        <button class="close" onclick="closePopup('video1')">Close</button>
            <h3>Upload a Video</h3>
            <form name="frm2" method="post" action="RegPosts_Video" enctype="multipart/form-data">
            <input type="file" name="file" accept="video/*">
            <textarea name="title" placeholder="Title"></textarea>
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
          <textarea id="commentText" class="form-control" placeholder="Write a comment..." rows="3"></textarea>
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
