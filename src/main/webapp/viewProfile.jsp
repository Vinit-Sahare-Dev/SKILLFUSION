<%@page import="java.util.List"%>
<%@page import="beans.BranchList"%>
<%@page import="java.util.Vector"%>
<%@page import="models.JavaFuns"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Enhanced Profile Page</title>
    <link rel="stylesheet" href="css/profile.css">
    <style>
        /* Add some basic styling for the popup forms */
        .popup-form {
            /* display: none; Initially hide popups */
            position: fixed;
            top: 60%;
            left: 50%;
            transform: translate(-50%, -50%);
            background-color: #fff;
            padding: 20px;
            border: 2px solid #333;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            z-index: 10;
        }

        .popup-form h4 {
            margin-bottom: 15px;
        }

        .popup-form input {
            display: block;
            width: 100%;
            padding: 10px;
            margin-bottom: 10px;
        }

        .popup-form button {
            padding: 10px 15px;
            margin-top: 10px;
        }

        /* Dim background when popup is active */
        .overlay {
            display: none;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.5);
            z-index: 9;
        }
    </style>
</head>
<body>
<jsp:include page="Top1.jsp"></jsp:include>
 <div class="row">
<div class="col-md-3">

    <div class="sidebar">
        <div class="profile">
        <img src='Uploads/<%=session.getAttribute("photo").toString() %>' class="img-responsive img-thumbnail rounded-circle" width="100px"/>
            <h2><%=session.getAttribute("username").toString() %>(<%=session.getAttribute("usertype").toString().trim()%>)</h2>
            	 
	<%if(session.getAttribute("currentsts").toString().trim().equals("Placed")) {%>
	<p><b>Job Post:</b><%=session.getAttribute("job_post").toString().trim() %></p>
	<p><b>Job Details:</b><%=session.getAttribute("job_details").toString().trim() %></p>
	 <%}else{ %>
	 
	<%} %>
	<p><b>Branch : <%=session.getAttribute("branch").toString().trim() %></b></p>
                    <p><b>User Type: <%=session.getAttribute("usertype").toString().trim() %></b></p>
                     <%JavaFuns jf=new JavaFuns();
                     Vector v=jf.getValue("select categories,mobileno,emailid from studentpersonal where userid='"+session.getAttribute("userid").toString().trim()+"'", 3);
                     
                     %>
                     <p><b>Mobile No: <%=v.elementAt(1).toString().trim() %></b></p>
                     <p><b>Email Id : <%=v.elementAt(2).toString().trim() %></b></p>
                   
	<div class="section">
             <%
             String qr1= "select count(*) from projects where userid='"+session.getAttribute("userid").toString().trim() +"'";
            		  		  
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
    <!-- Main Container -->
    <div class="main-container" id="maincontainer">
  


        <!-- Education Section -->
        <div class="info-section" id="education">
            <h4>Education</h4>
            <br>
            <!-- Clickable Education Links -->
          <!-- Graduation Details Section -->
<%Vector vgrd=jf.getValue("select college,percent,passing_yr,course from academics where course<>'HSC' and course<>'SSC' and userid='"+session.getAttribute("userid").toString().trim()+"'", 4); %>
<%if(vgrd.size()==0){ %>
<p><a href="#" class="edu-link" data-target="#graduationForm">Add Graduation Details</a></p>
<i><P class="graduation-info">Scored Percentage, Passing Year </P> </i></h6>
<br>
<%}else{ %>
<p><%=vgrd.elementAt(3).toString().trim() %> from <%=vgrd.elementAt(0).toString().trim() %></p>
        <p><%=vgrd.elementAt(1).toString().trim() %>%, <%=vgrd.elementAt(2).toString().trim() %></p>
        <% }%>
<br>
<!-- Class XII Details Section -->
<%Vector vhsc=jf.getValue("select college,percent,passing_yr from academics where course='HSC' and userid='"+session.getAttribute("userid").toString().trim()+"'", 3); %>
<%if(vhsc.size()==0){ %>
<p><a href="#" class="edu-link" data-target="#classXIIForm">Add Class XII Details</a></p>
<i> <p class="class-xii-info">Scored Percentage, Passing Year</i></h6></p>
<%}else{ %>
<p>HSC from <%=vhsc.elementAt(0).toString().trim() %></p>
        <p><%=vhsc.elementAt(1).toString().trim() %>%, <%=vhsc.elementAt(2).toString().trim() %></p>
        <% }%>
<br>

<!-- Class X Details Section -->
<%Vector vssc=jf.getValue("select college,percent,passing_yr from academics where course='SSC' and userid='"+session.getAttribute("userid").toString().trim()+"'", 3); %>
<%if(vssc.size()==0){ %>
<p><a href="#" class="edu-link" data-target="#classXForm">Add Class X Details</a></p>
<i><p class="class-x-info">Scored Percentage, Passing Year</i></h6></p>
<%}else{ %>
<p>SSC from <%=vssc.elementAt(0).toString().trim() %></p>
        <p><%=vssc.elementAt(1).toString().trim() %>%, <%=vssc.elementAt(2).toString().trim() %></p>
        <% }%>
        
            <!-- Hidden Popup Forms -->
            <!-- Graduation Form -->
            <div id="graduationForm" class="popup-form">
                <h4>Education: Graduation</h4>
                <form id="graduationForm" method="post" action="RegGrad">
                    <label for ="grad-school">College Name:</label>
                    <input type="text" required id="grad-school" name="grad-school"> 
                    <label for="grad-degree">Degree:</label>
                    <input type="text" required id="grad-degree" name="grad-degree"> 
                    <label for="grad-branch">Branch:</label>
                   <%BranchList bl1 = new BranchList();
     List<BranchList> lst1 = bl1.getBranchList();  %>
        <select name="branch" required class="form-control">
       <option value=""><--Select--></option>
       <%for(int i=0 ;i<lst1.size();i++) {%>
           <option value=<%=lst1.get(i).getBranchname()%>><%=lst1.get(i).getBranchname() %></option>
  <%} %>
       </select><br> 
                    <label for="grad-percentage">CGPA:</label>
                    <input type="text" required id="grad-percentage" name="grad-percentage"> 
                    <label for="grad-year">Year:</label>
                    <input type="text" required id="grad-year" name="grad-year"> 
                    <input type="submit" value="Submit">
                </form>
                <button class="close-popup">Close</button>
            </div>

            <!-- Class XII Form -->
            <div id="classXIIForm" class="popup-form">
                <h4>Education: Class XII</h4>
                <form name="frm" method="post" action="RegXIIDetails">
                    <label for="xii-school">College Name:</label>
                    <input type="text" id="xii-school" name="xii-school" placeholder="Add your school/college name" required>
                    <br>
                    <label for="xii-percentage">Percentage/:</label>
                    <input type="text" id="xii-percentage" name="xii-percentage" placeholder="e.g., 95" required>
                    <br>
                    <label for="xii-year">Passing Year:</label>
                    <input type="text" id="xii-year" name="xii-year" placeholder="YYYY" required>
                    <br>
                    <button type="submit" class="add-btn">Submit</button>
                </form>
                <button class="close-popup">Close</button>
            </div>

            <!-- Class X Form -->
            <div id="classXForm" class="popup-form">
                <h4>Education: Class X</h4>
                <form name="frm" method="post" action="RegXDetails">
                    <label for="x-school">School:</label>
                    <input type="text" id="x-school" name="x-school" placeholder="Add your school name" required>
                    <br>
                    <label for="x-percentage">Percentage/CGPA:</label>
                    <input type="text" id="x-percentage" name="x-percentage" placeholder="e.g., 95" required>
                    <br>
                    <label for="x-year">Passing Year:</label>
                    <input type="text" id="x-year" name="x-year" placeholder="YYYY" required>
                    <br>
                    <button type="submit" class="add-btn">Submit</button>
                </form>
                <button class="close-popup">Close</button>
            </div>
        </div>


    <!-- Overlay for background dimming -->
    <div id="overlay" class="overlay"></div>




    <!-- Hidden form for adding new exams -->
    <div class="info-section" id="skills">
     
        <h4>Exams you are preparing for</h4>
        <%
       Vector vskills=jf.getValue("select exam from stud_exams where userid='"+session.getAttribute("userid").toString().trim()+"'", 1);
       
       %>
       <b><%=String.join(", ", vskills) %></b>
        <h5> <i> Choose exam want to prepare for...</h5>
            <br>
            
        <div id="skillsList" class="skills"></div>
        <div style="display: flex; justify-content: flex-end; margin-bottom: 10px;">
            <button id="addSkillBtn" class="add-btn">Add Exam</button>
        </div>
    
        <!-- Hidden form for adding new exams -->
        <div id="examForm" style="display:none;">
            <label for="examSelection">Select Exam:</label>
            <%
            String cate="";
            String[] str=v.elementAt(0).toString().trim().split("\\,");
            for(int i=0;i<str.length;i++)
            {
            	if(i==0)
            	cate+="'"+str[i].trim()+"'";
            	else
            		cate+=",'"+str[i].trim()+"'";	
            }
            Vector v1=jf.getValue("select examName from govtExams where category in ("+cate+")" , 1);
            Vector v5=jf.getValue("select exam from stud_exams  where userid='"+session.getAttribute("userid").toString().trim()+"'", 1);
            %>
             <form method="post" action="submitExams">
                 <div class="row">
                <%for(int i=0;i<v1.size();i++)
                { 
                	boolean isChecked=false;

                    String exam = v1.elementAt(i).toString().trim();
                    if(v5.size()>0){
                    isChecked= v5.contains(exam); // Check if the skill is already selected
                    }
                %>
                 <div class="col-sm-3">
<label>
  <input type="checkbox" class="checkbox" value="<%=v1.elementAt(i).toString().trim() %>" name="selectedExams" <%= isChecked ? "checked" : "" %> />
   <%=v1.elementAt(i).toString().trim() %>
                </label>
               </div>
                <%} %></div>
             
            <input type="submit" value="Submit" id="submitExamBtn" class="add-btn"/>
            </form>
        </div>
    </div>

<!-- Accomplishments Section -->
<div class="info-section" id="accomplishments" style="visibility: hidden;">
    <h4><b>Accomplishments</b></h4>
    <h5><i>Add your achievements & details</i></h5>
    <br>
    <!-- Competitive Exams Subsection -->
    <div id="competitiveExams">
        <h4>Competitive Exams</h4>
        <h6><i>Talk about any competitive exam that you appeared for and the rank received</i></h6>
        <br>
        <textarea id="competitiveExamsDetails" rows="4" placeholder="Talk about any competitive exams you have taken or are preparing for..."></textarea>
    </div>
    <br><br>
    <!-- Other Achievements Subsection -->
    <div id="otherAchievements">
        <h4>Other Achievements</h4>
        <h6><i>Talk about any academic achievement whether in college or school that deserves a mention</i></h6>
        <br>
        <textarea id="otherAchievementsDetails" rows="4" placeholder="Mention any additional achievements..."></textarea>
    </div>
</div>
<div class="info-section" id="resume" style="visibility: hidden;">
    <h3>Resume</h3>
    
    <!-- Display the uploaded resume link -->
    <div id="resumeList">
        <p>No resume uploaded yet.</p>
    </div>
    
    <!-- Button to add resume -->
    <button id="addResumeBtn" class="add-btn" style="visibility: hidden;">Add Resume</button>

    <!-- Hidden form to upload resume -->
    <div id="resumeForm" style="display:none;visibility: hidden;">
        <form id="uploadResumeForm" enctype="multipart/form-data">
            <label for="resumeUpload">Upload Resume (PDF/DOC):</label>
            <input type="file" id="resumeUpload" name="resumeUpload" accept=".pdf,.doc,.docx" required>
            <br>
            <button type="submit" class="add-btn">Submit</button>
        </form>
    </div>
 


</div>

<!-- Centered Submit button -->
<div class="submit-container" style="visibility: hidden;">
    <button id="finalSubmitBtn" class="add-btn">Submit All Data</button>
</div>
    <script src="css/profile.js"></script>
</body>
</html>
