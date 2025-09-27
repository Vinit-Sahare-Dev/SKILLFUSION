 
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
    <title>Working Professional Dashboard</title>
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
        <img src='Uploads/<%=session.getAttribute("photo").toString() %>' class="img-responsive img-thumbnail rounded-circle" width="100px"/>
            <h2><%=session.getAttribute("username").toString() %><br/>(<%=session.getAttribute("usertype").toString().trim()%>)</h2>
            	 
	<%if(session.getAttribute("currentsts").toString().trim().equals("Placed")) {%>
	<p><b>Job Post:</b><%=session.getAttribute("job_post").toString().trim() %></p>
	<p><b>Job Details:</b><%=session.getAttribute("job_details").toString().trim() %></p>
	  
	<%}else{ %>
	  
	<%} %>
	<div class="section">
             <%
             String qr1= "select count(*) from projects where userid='"+session.getAttribute("userid").toString().trim() +"'";
            		  		 JavaFuns jf=new JavaFuns();
            				 Vector vf=jf.getValue(qr1,1);
            				 %>
            <div  >
            <table class="tblform">
            <tr>
            <td>
            <h4> Total Projects</h4>
 				<span class="btn btn-secondary rounded-circle dash">
 				<%=vf.elementAt(0) %>
 				</span>
            </td></tr> 
            </table>
           
            </div>
        </div>
        </div>
       
    </div>
</div><div class="col-md-9">
    <div class="main-content">
        <div class="section">
         
            <div style="background-image:url('img/about-1.jpg');background-position:center;background-size:contain; width:100%; color:transparent;min-height: 300px">dsf</div>
   </div>
        <div class="section">
          
          <a href="RegSkills.jsp" class="button smallbtn"  >Add Skills</a>
       <h4>Skills</h4>
       <%
       Vector vskills=jf.getValue("select skill from stud_skills where userid='"+session.getAttribute("userid").toString().trim()+"'", 1);
       
       %>
       <b><%=String.join(", ", vskills) %></b>
        </div> 
        <div class="section">
       
         <button class="button smallbtn" onclick="openPopup('live')">Add Experience</button>
                <h4>Experience</h4>
                 <%
       Vector vexp=jf.getValue("select designation,experience from experience where userid='"+session.getAttribute("userid").toString().trim()+"'", 2);
      
       %>
       <table class="table table-bordered">
       <tr><th>Designation<th>Experience in years</th></tr>
       <%for(int i=0;i<vexp.size();i=i+2){ %>
       <tr><td><%=vexp.elementAt(i).toString().trim() %></td>
       <td><%=vexp.elementAt(i+1).toString().trim() %></td>
       </tr>
       <%} %>
       </table>
        
        </div>
        <div class="section">
        
         <button class="button smallbtn" onclick="openPopup('photo')">Add Awards and Achivements</button>
                <h4>Awards and Achievements</h4>
                  <%
       Vector vach=jf.getValue("select details from achievements where userid='"+session.getAttribute("userid").toString().trim()+"'", 1);
      
       %>
       <ul  >
       <%for(int i=0;i<vach.size();i++){ %>
       <li> <%=vach.elementAt(i).toString().trim() %></li> 
       
       
       <%} %>
       </ul>
        </div>
        </div></div></div>
        
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
        <div id="post-popup" class="popup popup1" style="display: none;">
        <button class="close" onclick="closePopup('post')">Close</button>
           
            
        </div>
        <div id="live-popup" class="popup" style="display: none;">
            <h3>Experience Details</h3>
            <button class="close" onclick="closePopup('live')">Close</button>
            <form name="frm1" method="post" action="RegExperience">
            <input type="text" name="designation" required  placeholder="Enter Your Designation">
            <input type="text" name="experience" required placeholder="Experience in years"/>
            <input type="submit" value="Submit" class="button"/>
            
            </form>
        </div>
        <div id="photo-popup" class="popup" style="display: none;">
        <button class="close" onclick="closePopup('photo')">Close</button>
            <h3>Register Achievements</h3>
            <form name="frm2" method="post" action="RegAchievements" >
             
            <textarea name="title" required placeholder="details"></textarea>
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
        <h5 class="modal-title" id="commentModalLabel"> Comments</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        
        <!-- Comments Section -->
        <div id="commentList">
          <p>Loading comments...</p>
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
