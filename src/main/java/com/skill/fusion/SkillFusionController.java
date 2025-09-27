package com.skill.fusion;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.random.RandomGenerator;
 
import jakarta.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import beans.BranchList;
import beans.GMailer;
import beans.RandomString;
import models.WorkingProfessionals;
import models.ApproveStudents;
import models.CheckUser;
 
import models.Documents;
 
import models.Forum;
import models.JavaFuns;
import models.Message;
import models.Pass;
import models.PasswordRecovery;
import models.Projects; 
import models.Recommendation;
import models.RegisterBranch;
import models.Requests;
import models.Reviews;
import models.Student;
import models.SuccessStory;
import models.Topics; 
import models.ViewStudent;
import models.ViewStudentList; 

@Controller
public class SkillFusionController implements ErrorController{
	@RequestMapping("/college")
	public String index()
	{
		return "index.jsp";
	}
    @RequestMapping("/likePost") 
    public String likePost(Projects pos,HttpServletRequest request, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) return "0";
        
        int updatedLikeCount = pos.toggleLike(pos.getPid(), username);
        return request.getParameter("page").toString()+".jsp?cnt="+String.valueOf(updatedLikeCount);
    }
    
 
	@RequestMapping("/home")
	public String home()
	{
		return "index.jsp";
	}
	@RequestMapping("/logout")
	public String logout(HttpSession ses)
	{
		ses.invalidate();
		return "index.jsp";
	}
	@RequestMapping("/UpdteAns")
	public String UpdteAns(HttpServletRequest request,HttpSession ses)
	{ 
		JavaFuns jf=new JavaFuns();
		System.out.println("qrrr="+"select answer from studexams where questionId="+request.getParameter("quesid").trim()+" and userid='"+ses.getAttribute("userid").toString().trim()+"'  and answer='"+request.getParameter("ans").trim()+"'");
		Vector v=jf.getValue("select answer from quesbank where ques='"+request.getParameter("ques").trim()+"'    and answer='"+request.getParameter("ans").trim()+"'", 1);
		if(v.size()>0)
		{
			System.out.println("qr="+"update studexams set answer='"+request.getParameter("ans").trim()+"',score=1 where  quesId="+request.getParameter("quesid").trim()+" and userid='"+ses.getAttribute("userid").toString().trim()+"'");
			if(jf.execute("update studexams set answer='"+request.getParameter("ans").trim()+"',score=1 where  quesId="+request.getParameter("quesid").trim()+" and userid='"+ses.getAttribute("userid").toString().trim()+"'")) {}
		}
		else
		{
			System.out.println("qr="+"update studexams set answer='"+request.getParameter("ans").trim()+"',score=0 where  quesId="+request.getParameter("quesid").trim()+" and userid='"+ses.getAttribute("userid").toString().trim()+"'");
		
			if(jf.execute("update studexams set answer='"+request.getParameter("ans").trim()+"',score=0 where  quesId="+request.getParameter("quesid").trim()+" and userid='"+ses.getAttribute("userid").toString().trim()+"'")) {}
		}
		return "result.jsp";
	}
	@RequestMapping("/admin")
	public String admin()
	{
		return "approvestudentlist";
	}
	
	@RequestMapping("/SendOTP")
	public String SendOTP(HttpServletRequest request,HttpSession ses)
	{
		String m="OTP sending Failed!!";
		 String filepath=request.getServletContext().getRealPath("/gmail_api/");
			try {
		GMailer mail=new GMailer(filepath);
		RandomString rnd=new RandomString();
		String otp=rnd.getAlphaNumericString(4);
		System.out.println("otp="+otp);
		ses.setAttribute("otp", otp);
		
		try
		{
			String msg="Dear student, your one time password is "+otp;
			if(mail.sendMail(msg, request.getParameter("email").trim(), "OTP")) 
			{
				m="OTP sent on specified email id...";
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}}
			catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		return "otpmsg.jsp?m="+m;
	}
	@RequestMapping("/registerstudent")
	public String registerstudent()
	{
		return "Register.jsp";
	}
	@RequestMapping("/studentHome")
	public String studentHome()
	{
		return "Student.jsp";
	}
	@RequestMapping("/student")
	public String student()
	{
		return "Student.jsp";
	}
	@RequestMapping("/WorkingProfessional")
	public String WorkingProfessional()
	{
		return "WorkingProfessional.jsp";
	}
	@RequestMapping("/regReview")
	@SessionScope
	public ModelAndView regReview(Reviews rev,HttpServletRequest request)
	{
		ModelAndView mv=new ModelAndView();
		String filepath=request.getServletContext().getRealPath("/");
		rev.registerReview(filepath); 	
		mv.setViewName("Success.jsp");
		mv.addObject("activity","DocReview");
		return mv;
	}
	@RequestMapping("/NewReview")
	@SessionScope
	public ModelAndView NewReview(HttpServletRequest request)
	{
		ModelAndView mv=new ModelAndView();
		Reviews review=new Reviews();
		
		review.setUserid1((request.getParameter("userid").trim()));
		review.getReviews();
		List<Reviews> lst=new ArrayList<Reviews>();
				lst=review.getLstreviews();
		//System.out.println("docid="+request.getParameter("docId").trim()+"lst size="+lst.size());
		mv.setViewName("NewReview.jsp");
		
		mv.addObject("lst",lst);
		mv.addObject("userid",request.getParameter("userid").trim());
		mv.addObject("username",request.getParameter("username").trim());
		return mv;
	}
	@RequestMapping("/RegCommunicate")
	@SessionScope
	public ModelAndView RegCommunicate(Message msg, HttpSession ses, HttpServletRequest request)
	{
		JavaFuns jf=new JavaFuns();
		 int mxid=jf.FetchMax("mid", "messages");
		 msg.setMid(mxid);
		ModelAndView mv=new ModelAndView();
		try
		 {MultipartFile file=msg.getFile();
		 String filepath=request.getServletContext().getRealPath("/")+"/Attachments/";
		  System.out.println("path="+filepath);
		 File f=new File(filepath);
		 f.mkdir();
		 
		 
		 String fileName=mxid+"."+ file.getOriginalFilename().split("\\.")[1];
		 file.transferTo(new File(filepath+"/"+fileName));
		 msg.setAttachment(fileName);
		  }
		catch (Exception e) {
			msg.setAttachment("NA");
		}
		String[] str=msg.getUserid1().split("\\,");
		msg.setUserid1(str[0].trim());
		msg.setUsername1(str[1].trim());
		Date d=new Date();
		msg.setDt((d.getDate()+"/"+(d.getMonth()+1)+"/"+(d.getYear()+1900)));
		msg.insertMessage();
		 mv.setViewName("Success.jsp");
		
		mv.addObject("activity","compose");
	 
		//mv.addObject("userid",request.getParameter("userid").trim());
		//mv.addObject("username",request.getParameter("username").trim());
		return mv;
	}
	@RequestMapping("/Communicate")
	@SessionScope
	public ModelAndView Communicate(Message msg, HttpSession ses, HttpServletRequest request)
	{
		 
		ModelAndView mv=new ModelAndView();
		 
		msg.getInbox(ses.getAttribute("userid").toString().trim());
		
		List<Message> lst=new ArrayList<Message>();
				lst=msg.getMessageList();
	   msg.getSentItems(ses.getAttribute("userid").toString().trim());
				
		List<Message> lst1=new ArrayList<Message>();
						lst1=msg.getMessageList();
		//System.out.println("docid="+request.getParameter("docId").trim()+"lst size="+lst.size());
		mv.setViewName("Communicate.jsp");
		
		mv.addObject("lst",lst);
		mv.addObject("lst1",lst1);
		
		mv.addObject("pid",request.getParameter("pid").trim());
		//mv.addObject("username",request.getParameter("username").trim());
		return mv;
	}
	@RequestMapping("/viewReviews")
	@SessionScope
	public ModelAndView viewReviews(HttpServletRequest request)
	{
		ModelAndView mv=new ModelAndView();
		Reviews review=new Reviews();
		
		review.setUserid1((request.getParameter("userid").trim()));
		review.getReviews();
		List<Reviews> lst=new ArrayList<Reviews>();
				lst=review.getLstreviews();
		//System.out.println("docid="+request.getParameter("docId").trim()+"lst size="+lst.size());
		mv.setViewName("viewReviews.jsp");
		
		mv.addObject("lst",lst);
		mv.addObject("userid",request.getParameter("userid").trim());
		return mv;
	}
	@SessionScope
	@RequestMapping("/acceptReq")
	public ModelAndView acceptReq(Requests req, HttpServletRequest request,HttpSession ses)
	{
		JavaFuns jf=new JavaFuns();
		String qr="update requests set sts='accepted' where reqid="+req.getReqid();
		if(jf.execute(qr)) {}  
		
		ModelAndView mv=new ModelAndView();
		mv.setViewName("Success.jsp");
		mv.addObject("activity","reqaccepted");
		String userid=request.getParameter("userid").toString().trim();
		Vector v=jf.getValue("select emailid,usernm from studentpersonal where userid='"+userid.trim()+"'", 2);
		String email=v.elementAt(0).toString().trim();
		String usernm=v.elementAt(1).toString().trim();
		String filepath=request.getServletContext().getRealPath("/");
		String msg="Dear user("+usernm+"), "+ses.getAttribute("username").toString().trim()+" accpted project joining request.. ";
		
		try {
		GMailer gmail=new GMailer(filepath);
		if(gmail.sendMail("Project Joining Request", msg, email))
		{}
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return mv;
	}
	@SessionScope
	@RequestMapping("/SendRequest")
	public ModelAndView SendRequest(Requests req, HttpServletRequest request,HttpSession ses)
	{
		String sts=req.insertRequest();
		   
		ModelAndView mv=new ModelAndView();
		mv.setViewName("Success.jsp");
		mv.addObject("activity","reqReg");
		String filepath=request.getServletContext().getRealPath("/");
		String msg="Dear user("+req.getUsername1()+"), "+req.getUsername()+" sent you project joining request.. Please check in your account";
		
		try {
		GMailer gmail=new GMailer(filepath);
		if(gmail.sendMail("Project Joining Request", msg, req.getEmail1()))
		{}
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return mv;
	}
	@SessionScope
	@RequestMapping("/RegExperience")
	public ModelAndView RegExperience(HttpServletRequest request,HttpSession ses)
	{
		 
		JavaFuns jf=new JavaFuns();
		String qr;
		//if(jf.execute("delete from experience where userid='"+ses.getAttribute("userid").toString().trim()+"'")) {}
		 
		qr="insert into experience(userid,designation,experience) values('"+ses.getAttribute("userid").toString().trim()+"','"+request.getParameter("designation").toString().trim()+"','"+request.getParameter("experience").toString().trim()+"')";
		if(jf.execute(qr)) {}
		 
		ModelAndView mv=new ModelAndView();
		mv.setViewName("Success.jsp");
		mv.addObject("activity","expReg");
		return mv;
	}
	@SessionScope
	@RequestMapping("/RegAchievements")
	public ModelAndView RegAchivements(HttpServletRequest request,HttpSession ses)
	{
		 
		JavaFuns jf=new JavaFuns();
		String qr;
		//if(jf.execute("delete from achievements where userid='"+ses.getAttribute("userid").toString().trim()+"'")) {}
		 
		qr="insert into achievements(userid,details) values('"+ses.getAttribute("userid").toString().trim()+"','"+request.getParameter("title").toString().trim()+"')";
		if(jf.execute(qr)) {}
		 
		ModelAndView mv=new ModelAndView();
		mv.setViewName("Success.jsp");
		mv.addObject("activity","AchievReg");
		return mv;
	}
	@RequestMapping("/submitExams")
	public ModelAndView submitExams(HttpServletRequest request,HttpSession ses)
	{
		String[] str=request.getParameterValues("selectedExams");
		JavaFuns jf=new JavaFuns();
		String qr;
		if(jf.execute("delete from stud_exams where userid='"+ses.getAttribute("userid").toString().trim()+"'")) {}
		for(int i=0;i<str.length;i++)
		{
		qr="insert into stud_exams(userid,exam,utype) values('"+ses.getAttribute("userid").toString().trim()+"','"+str[i].trim()+"','"+ses.getAttribute("usertype").toString().trim()+"')";
		if(jf.execute(qr)) {}
		}
		ModelAndView mv=new ModelAndView();
		mv.setViewName("Success.jsp");
		mv.addObject("activity","ExamsReg");
		return mv;
	}
	@RequestMapping("/RegSkills_proj")
	public ModelAndView RegSkills_proj(HttpServletRequest request,HttpSession ses)
	{
		String[] str=request.getParameterValues("selectedSkills");
		JavaFuns jf=new JavaFuns();
		String qr;
		if(jf.execute("delete from proj_skills where pid="+request.getParameter("pid").toString().trim())) {}
		for(int i=0;i<str.length;i++)
		{
		qr="insert into proj_skills(pid,skill) values("+request.getParameter("pid").toString().trim()+",'"+str[i].trim()+"')";
		if(jf.execute(qr)) {}
		}
		ModelAndView mv=new ModelAndView();
		mv.setViewName("Success.jsp");
		mv.addObject("activity","skillsReg");
		return mv;
	}
	@RequestMapping("/RegSkills")
	public ModelAndView RegSkills(HttpServletRequest request,HttpSession ses)
	{
		String[] str=request.getParameterValues("selectedSkills");
		JavaFuns jf=new JavaFuns();
		String qr;
		if(jf.execute("delete from stud_skills where userid='"+ses.getAttribute("userid").toString().trim()+"'")) {}
		for(int i=0;i<str.length;i++)
		{
		qr="insert into stud_skills(userid,skill,utype) values('"+ses.getAttribute("userid").toString().trim()+"','"+str[i].trim()+"','"+ses.getAttribute("usertype").toString().trim()+"')";
		if(jf.execute(qr)) {}
		}
		ModelAndView mv=new ModelAndView();
		mv.setViewName("Success.jsp");
		mv.addObject("activity","skillsReg");
		return mv;
	}
	@RequestMapping("/staff")
	public String staff1()
	{
		return "Staff.jsp";
	}
	@RequestMapping("/RegGrad")
	public String RegGrad(HttpServletRequest request,HttpSession ses)
	{
		String xschool=request.getParameter("grad-school");
	double xpercent=Double.parseDouble(request.getParameter("grad-percentage").trim());
		String xyear=request.getParameter("grad-year");
		String branch=request.getParameter("branch");
		JavaFuns jf=new JavaFuns();
		int mxid=jf.FetchMax("acid", "academics");
		String qr="insert into academics values("+mxid+",'"+ses.getAttribute("userid").toString().trim()+"',";
		qr+="'"+xschool+"',"+xpercent+",'"+xyear+"','"+request.getParameter("grad-degree").toString().trim()+"','"+request.getParameter("branch").toString().trim()+"',0,0)";
		System.out.println(qr);
		if(jf.execute(qr)) {}
		return "viewProfile.jsp";
	}
	@RequestMapping("/RegXIIDetails")
	public String RegXIIDetails(HttpServletRequest request,HttpSession ses)
	{
		String xschool=request.getParameter("xii-school");
	double xpercent=Double.parseDouble(request.getParameter("xii-percentage").trim());
		String xyear=request.getParameter("xii-year");
		JavaFuns jf=new JavaFuns();
		int mxid=jf.FetchMax("acid", "academics");
		String qr="insert into academics values("+mxid+",'"+ses.getAttribute("userid").toString().trim()+"',";
		qr+="'"+xschool+"',"+xpercent+",'"+xyear+"','HSC','NA',0,0)";
		System.out.println(qr);
		if(jf.execute(qr)) {}
		return "viewProfile.jsp";
	}
	@RequestMapping("/RegXDetails")
	public String RegXDetails(HttpServletRequest request,HttpSession ses)
	{
		String xschool=request.getParameter("x-school");
	double xpercent=Double.parseDouble(request.getParameter("x-percentage").trim());
		String xyear=request.getParameter("x-year");
		JavaFuns jf=new JavaFuns();
		int mxid=jf.FetchMax("acid", "academics");
		String qr="insert into academics values("+mxid+",'"+ses.getAttribute("userid").toString().trim()+"',";
		qr+="'"+xschool+"',"+xpercent+",'"+xyear+"','SSC','NA',0,0)";
		System.out.println(qr);
		if(jf.execute(qr)) {}
		return "viewProfile.jsp";
	}
	 
	@SessionScope
	@RequestMapping("/RegDocs")
	public ModelAndView RegDocs(Documents eobj,HttpServletRequest request,HttpSession ses)
	{
		ModelAndView mv=new ModelAndView();
		eobj.getId();
		 int mx=eobj.getDocId();
		 String filepath="NA",fileName="NA";
		 try
		 {MultipartFile file=eobj.getFile();
		  filepath=request.getServletContext().getRealPath("/")+"/Uploads/";
		  
		 System.out.println("path="+filepath);
		 File f=new File(filepath);
		 f.mkdir();
		 f=new File(filepath);
		 f.mkdir();
		
			 
		  fileName=mx+"."+ file.getOriginalFilename().split("\\.")[1];
			 file.transferTo(new File(filepath+"/"+fileName));
		 } catch (Exception e) {
			 
		 }
			 try {
				 if(eobj.getCategory().equals("Link"))
				 {
					 fileName=request.getParameter("link").trim();
				 }
			 eobj.setDocpath(fileName);
			 eobj.setUserid(ses.getAttribute("userid").toString().trim());
			 if(eobj.registration() )
			 { 
				mv.setViewName("Success.jsp");
				mv.addObject("activity","DocumentReg");
			 }
			 else
			 { 
				 mv.setViewName("Failure.jsp?type=DocumentReg");
				 mv.addObject("activity","DocumentReg");
			 }
			 
		 
			 
		// mv.setViewName("Success.jsp?type=DocumentReg");
		 }
		 catch (Exception e) {
			// TODO: handle exception
			 System.out.println("err="+e.getMessage());
			 mv.setViewName("Failure.jsp?type=DocumentReg");
		}
		 return mv;
	}
	
	@SessionScope
	@RequestMapping("/RegAns")
	public ModelAndView RegAns(Forum eobj,HttpServletRequest request,HttpSession ses)
	{
		ModelAndView mv=new ModelAndView();
		JavaFuns jf=new JavaFuns();
		 int mxid=jf.FetchMax("pid", "forum_ans");
		 eobj.setPid(mxid);
		  
		try
		 {MultipartFile file=eobj.getFile();
		 String filepath=request.getServletContext().getRealPath("/")+"/Answers/";
		  System.out.println("path="+filepath);
		 File f=new File(filepath);
		 f.mkdir();
		 
		 
		 String fileName=mxid+"."+ file.getOriginalFilename().split("\\.")[1];
		 file.transferTo(new File(filepath+"/"+fileName));
		 eobj.setAttachment(fileName);
		  }
		catch (Exception e) {
			eobj.setAttachment("NA");
		}
		 try { 
			 
			 System.out.println("title="+eobj.getQues());
			   String sts=eobj.RegAns(ses); 
			  
				mv.setViewName(sts);
				mv.addObject("activity","AnsReg");
				
			 
		 
			 
		// mv.setViewName("Success.jsp?type=DocumentReg");
		 }
		 catch (Exception e) {
			// TODO: handle exception
			 System.out.println("err="+e.getMessage());
			 mv.setViewName("Failure.jsp?type=PostReg");
		}
		 return mv;
	}
	@SessionScope
	@RequestMapping("/Regques")
	public ModelAndView Regques(Forum eobj,HttpServletRequest request,HttpSession ses)
	{
		ModelAndView mv=new ModelAndView();
		 try { 
			 
			 System.out.println("title="+eobj.getQues());
			   String sts=eobj.addQues(ses); 
			  
				mv.setViewName(sts);
				mv.addObject("activity","ForumReg");
				
			 
		 
			 
		// mv.setViewName("Success.jsp?type=DocumentReg");
		 }
		 catch (Exception e) {
			// TODO: handle exception
			 System.out.println("err="+e.getMessage());
			 mv.setViewName("Failure.jsp?type=PostReg");
		}
		 return mv;
	}
	 
	@SessionScope
	@RequestMapping("/RegProj")
	public ModelAndView RegProj(Projects eobj,HttpServletRequest request,HttpSession ses)
	{
		ModelAndView mv=new ModelAndView();
		 try { 
			 
			 System.out.println("title="+eobj.getTitle());
			   String sts=eobj.addProj(ses); 
			  
				mv.setViewName(sts);
				mv.addObject("activity","ProjReg");
				
			 
		 
			 
		// mv.setViewName("Success.jsp?type=DocumentReg");
		 }
		 catch (Exception e) {
			// TODO: handle exception
			 System.out.println("err="+e.getMessage());
			 mv.setViewName("Failure.jsp?type=PostReg");
		}
		 return mv;
	}
	 
	 
	 @RequestMapping("/error")
    public String handleError() {
        //do something like logging
		return "college";
    }
 
	 
	 @RequestMapping("/uploadResume")
		public ModelAndView uploadResume(HttpSession ses, Student stu,HttpServletRequest request)
		{
			ModelAndView mv=new ModelAndView();
			    
			 try
			 {  MultipartFile file1=stu.getFile1();
			 String filepath1=request.getServletContext().getRealPath("/")+"/Resume/";
			    
			 File f1=new File(filepath1);
			 f1.mkdir();
			 try { 
				 String fileName1=stu.getUserid()+"."+ file1.getOriginalFilename().split("\\.")[1];
				 file1.transferTo(new File(filepath1+"/"+fileName1));
				 ses.setAttribute("resume", fileName1);
				 JavaFuns jf=new JavaFuns();
				 if(jf.execute("update studentpersonal set stud_resume='"+fileName1.trim()+"' where userid='"+ses.getAttribute("userid").toString().trim()+"'")) {}
						mv.setViewName("Success.jsp");
					 
			 }
			 catch (Exception e) {
				// TODO: handle exception
				 e.printStackTrace();
				 mv.setViewName("Failure.jsp");
			}
			 
			 }
			 catch (Exception e) {
					// TODO: handle exception
				 mv.setViewName("Failure.jsp");
				}
			 mv.addObject("activity","ResumeReg");
			  
			 return mv;
			
		}	 
	@RequestMapping("/registeruser")
	public ModelAndView registeruser(HttpSession ses, Student stu,HttpServletRequest request)
	{
		ModelAndView mv=new ModelAndView();
		// String otp=request.getParameter("otp").toString().trim();
		// String otp1=request.getParameter("otp1").toString().trim();
		  
		// if(otp.trim().equals(otp1.trim()))
		// { 
	//	System.out.println(stu.getSelectedCategories());
		 try
		 {MultipartFile file=stu.getFile();
		 String filepath=request.getServletContext().getRealPath("/")+"/Uploads/";
		 MultipartFile file1=stu.getFile1();
		 String filepath1=request.getServletContext().getRealPath("/")+"/Resume/";
		 System.out.println("path="+filepath);
		 File f=new File(filepath);
		 f.mkdir();
		 File f1=new File(filepath1);
		 f1.mkdir();
		 try {
			  
			 String fileName=stu.getUserid()+"."+ file.getOriginalFilename().split("\\.")[1];
			 file.transferTo(new File(filepath+"/"+fileName));
			 stu.setPath(fileName);
			 String fileName1=stu.getUserid()+"."+ file1.getOriginalFilename().split("\\.")[1];
			 file1.transferTo(new File(filepath1+"/"+fileName1));
			 stu.setPath(fileName);
			 stu.setResume(fileName1);
			 String st=stu.addNewStudent();
				if(st.equals("success"))
					mv.setViewName("Success.jsp");
				else
					mv.setViewName("Failure.jsp");
		 }
		 catch (Exception e) {
			// TODO: handle exception
			 mv.setViewName("Failure.jsp");
		}
		 
		 }
		 catch (Exception e) {
				// TODO: handle exception
			 mv.setViewName("Failure.jsp");
			}
		 mv.addObject("activity","StudReg");
		// }
		// else
		// { mv.setViewName("Failure.jsp");
		//	 mv.addObject("activity","EmailVerificationFailed");
		// } 
		 return mv;
		
	}
	@RequestMapping("/registeruser1")
	public ModelAndView registeruser1( WorkingProfessionals stu,HttpServletRequest request)
	{
		ModelAndView mv=new ModelAndView();
		/// String otp=request.getParameter("otp").toString().trim();
		// String otp1=request.getParameter("otp1").toString().trim();
		  
		 //if(otp.trim().equals(otp1.trim()))
		// { 
		//System.out.println(stu.getSelectedCategories());
		 try
		 {MultipartFile file=stu.getFile();
		 String filepath=request.getServletContext().getRealPath("/")+"/Uploads/";
		 
		 
		 System.out.println("path="+filepath);
		 File f=new File(filepath);
		 f.mkdir();
		  
		 try {
			  
			 String fileName=stu.getUserid()+"."+ file.getOriginalFilename().split("\\.")[1];
			 file.transferTo(new File(filepath+"/"+fileName));
			 stu.setPath(fileName);
			 String st=stu.addNewStudent();
				if(st.equals("success"))
					mv.setViewName("Success.jsp");
				else
					mv.setViewName("Failure.jsp");
		 }
		 catch (Exception e) {
			// TODO: handle exception
			 mv.setViewName("Failure.jsp");
		}
		 
		 }
		 catch (Exception e) {
				// TODO: handle exception
			 mv.setViewName("Failure.jsp");
			}
		 mv.addObject("activity","StudReg");
		// }
		// else
		// { mv.setViewName("Failure.jsp");
		//	 mv.addObject("activity","EmailVerificationFailed");
		// } 
		 return mv;
		
	}
	@RequestMapping("/updateuser")
	public ModelAndView updateuser(Student stu,HttpServletRequest request,HttpSession ses)
	{String fileName="NA";
		
	ModelAndView mv=new ModelAndView();
	try
		 {
			 stu.setUserid(ses.getAttribute("userid").toString().trim());
			 
		  
		 try {
			 MultipartFile file=stu.getFile();
			 String filepath=request.getServletContext().getRealPath("/")+"/Uploads/";
			 
			 
			 System.out.println("path="+filepath);
			 File f=new File(filepath);
			 f.mkdir();
			  fileName=stu.getUserid()+"."+ file.getOriginalFilename().split("\\.")[1];
			 file.transferTo(new File(filepath+"/"+fileName));
			 
		 }
		 catch (Exception e) {
			// TODO: handle exception
			// return "UserRegFailure.jsp";
		}
		 if(!fileName.equals("NA"))
		 {
			 ses.setAttribute("photo", fileName);
		 }
		 stu.setPath(fileName);
		 String st=stu.updateStudent(stu.getUserid());
		 if(st.equals("success"))
				mv.setViewName("Success.jsp");
			else
				mv.setViewName("Failure.jsp");
		 }
		 catch (Exception e) {
			 System.out.println("in update="+e.getMessage());
				// TODO: handle exception
			 mv.setViewName("Failure.jsp");
			}
		 mv.addObject("activity","StudProfile");
		 return mv;
	}
	 
	@RequestMapping("/ChangePass")
	public String ChangePass()
	{
		return "ChangePass.jsp";
	}
	@RequestMapping("/ChangePassService")
	public ModelAndView ChangePassService(Pass eobj,HttpSession ses)
	{
		ModelAndView mv=new ModelAndView();
		 try
		 {
			 
			 eobj.setUserid(ses.getAttribute("userid").toString().trim());
			 if(eobj.changePassword())
			 { 
				 mv.setViewName("Success.jsp");
			 }
			 else
			 { 
				 mv.setViewName("Failure.jsp");
			 }
		 }
		 catch (Exception e) {
			// TODO: handle exception
			 System.out.println("err="+e.getMessage());
			 mv.setViewName("Failure.jsp");
		}
		 mv.addObject("activity","changePass");
		 return mv;
		 
	}

	 
	@RequestMapping("/registerbranch")
	@SessionScope
	public String registerbranch() {
		
		return("RegisterBranch.jsp");
		
	}
	@RequestMapping("/registerSuccessStories")
	@SessionScope
	public String registerSuccessStories() {
		
		return("RegSuccessStories.jsp");
		
	}
	@RequestMapping("/govtExamCategories")
	@SessionScope
	public String govtExamCategories() {
		
		return("RegGovtExamCategories.jsp");
		
	}
	@RequestMapping("/govtExams")
	@SessionScope
	public String govtExams() {
		
		return("RegGovtExams.jsp");
		
	}
	
	@RequestMapping("/registernewbranch")
	public ModelAndView registernewbranch(RegisterBranch rb) {
		
		String st=rb.registerBranch();
		ModelAndView mv=new ModelAndView();
		mv.setViewName(st);
		mv.addObject("activity","branchReg");
		return mv;
	}
	
	@RequestMapping("/RegSuccessStories1")
	public ModelAndView RegSuccessStories1(HttpServletRequest request, SuccessStory rb) {
		String fileName="NA"; 
		try
		 {System.out.println("in success Stories");
			MultipartFile file=rb.getFile();
		  String filepath=request.getServletContext().getRealPath("/")+"/SuccessStories/";
		  
		 System.out.println("path="+filepath);
		 File f=new File(filepath);
		 f.mkdir();
		 
		
			 
		  fileName=rb.getId()+"."+ file.getOriginalFilename().split("\\.")[1];
			 file.transferTo(new File(filepath+"/"+fileName));
		 } catch (Exception e) {
			 
		 }
		rb.setPhoto(fileName);
		String st=rb.registerSuccessStories();
		ModelAndView mv=new ModelAndView();
		mv.setViewName(st);
		mv.addObject("activity","SuccessStoriesReg");
		return mv;
	}
	 
	@RequestMapping("/deactivatestudent")
	public ModelAndView deactivatestudent(String uid) {
		ModelAndView mv=new ModelAndView();
		ApproveStudents ap=new ApproveStudents();
		String sts=ap.updateStudentStatus1(uid);
		if(sts.equals("success"))
			 mv.setViewName("Success.jsp");
		else
			 mv.setViewName("Failure.jsp");
		mv.addObject("activity","studDeActivation");
		 return mv;
	}
	
	@RequestMapping("/approveFollower")
	public ModelAndView approveFollower(HttpServletRequest request, ApproveStudents ap) {
		ModelAndView mv=new ModelAndView();
		 
		String filepath=request.getServletContext().getRealPath("/");
		ap.setPath(filepath);
		String sts=ap.approveFollower();
		if(sts.equals("success"))
			 mv.setViewName("Success.jsp");
		else
			 mv.setViewName("Failure.jsp");
		mv.addObject("activity","approveFollower");
		 return mv;
	}
	@RequestMapping("/declineFollower")
	public ModelAndView declineFollower(HttpServletRequest request, ApproveStudents ap) {
		ModelAndView mv=new ModelAndView();
		 
		String filepath=request.getServletContext().getRealPath("/");
		ap.setPath(filepath);
		String sts=ap.declineFollower();
		if(sts.equals("success"))
			 mv.setViewName("Success.jsp");
		else
			 mv.setViewName("Failure.jsp");
		mv.addObject("activity","declineFollower");
		 return mv;
	}
	@RequestMapping("/activatestudent")
	public ModelAndView activatestudent(HttpServletRequest request, ApproveStudents ap) {
		ModelAndView mv=new ModelAndView();
		 
		String filepath=request.getServletContext().getRealPath("/");
		ap.setPath(filepath);
		String sts=ap.updateStudentStatus();
		if(sts.equals("success"))
			 mv.setViewName("Success.jsp");
		else
			 mv.setViewName("Failure.jsp");
		mv.addObject("activity","studActivation");
		 return mv;
	}
	@RequestMapping("/viewstudent")
	@SessionScope
	public ModelAndView viewstudent() {
		
		List<Student> lst = new ArrayList<Student>();
		Student vs = new Student();
		lst=vs.getStudentReport();
		ModelAndView mv=new ModelAndView();
		mv.addObject("stal",lst);
		mv.setViewName("ViewStudentReport.jsp");
		return mv;
	}
	@RequestMapping("/viewAlumni")
	@SessionScope
	public ModelAndView viewalumni() {
		
		List<WorkingProfessionals> lst = new ArrayList<WorkingProfessionals>();
		WorkingProfessionals vs = new WorkingProfessionals();
		lst=vs.getAlumniReport();
		ModelAndView mv=new ModelAndView();
		mv.addObject("stal",lst);
		mv.setViewName("ViewAlumniReport.jsp");
		return mv;
	}
	@RequestMapping("/viewstudent1")
	@SessionScope
	public ModelAndView viewstudent1(String year) {
		
		List<ViewStudent> lst = new ArrayList<ViewStudent>();
		ViewStudent vs = new ViewStudent();
		lst=vs.getStudentReport1(year);
		ModelAndView mv=new ModelAndView();
		mv.addObject("std",lst);
		mv.setViewName("ViewStudentReport1.jsp");
		return mv;
	}
	@RequestMapping("/editProfile")
	@SessionScope
	public ModelAndView editProfile(HttpSession ses) {
		ModelAndView mv=new ModelAndView();
		try
		{
		List<Student> lst = new ArrayList<Student>();
		Student vs = new Student();
		lst=vs.getStudentReport(ses.getAttribute("userid").toString().trim());
		
		mv.addObject("std",lst);
		}
		catch (Exception e) {
		System.out.println("errr in edit="+e.getMessage());
		}
		mv.setViewName("EditProfileStud.jsp");
		return mv;
	}
	 
	@RequestMapping("/verifyEmailOTP")
	@SessionScope
	public ModelAndView verifyEmailOTP(HttpServletRequest request) {
		
		 String color="red"; 
		 String msg="Email Verification Failed!!";
		 String otp=request.getParameter("otp").toString().trim();
		 String otp1=request.getParameter("otp1").toString().trim();
		 String filepath=request.getServletContext().getRealPath("/");
		 try {
		 if(otp.trim().equals(otp1.trim()))
		 {
			 color="green";
			 msg="Email Verified Successfully..";
		 }
		 else {
		 color="red";
		 msg="Email Verification Failed!!";
		 }
		 }
		 catch (Exception e) {
			// TODO: handle exception
		}
		ModelAndView mv=new ModelAndView();
		mv.addObject("color",color);
		mv.addObject("msg",msg); 
		mv.setViewName("email_result1.jsp");
		return mv;
	
	}
	@RequestMapping("/VerifyEmail")
	@SessionScope
	public ModelAndView VerifyEmail(HttpServletRequest request) {
		
		 String color="red";
		 String otp="na";
		 String msg="Otp Sending Failed!!";
		 String email=request.getParameter("email").toString().trim();
		 String filepath=request.getServletContext().getRealPath("/");
		 try {
		 GMailer gmail=new GMailer(filepath);
		  otp=RandomString.getAlphaNumericString(4);
		 String msg1="Dear User, OTP for email verification is "+otp;
		 gmail.sendMail("Email Verification", msg1, email);
		 color="green";
		 msg="OTP sent on specified email id!!";
		 }
		 catch (Exception e) {
			// TODO: handle exception
		}
		ModelAndView mv=new ModelAndView();
		mv.addObject("color",color);
		mv.addObject("msg",msg);
		mv.addObject("otp",otp);
		mv.setViewName("email_result.jsp");
		return mv;
	
	}
	@RequestMapping("/approvestudentlist")
	@SessionScope
	public ModelAndView approvestudentlist() {
		
		List<Student> lst = new ArrayList<Student>();
		Student vls= new Student();
		
		lst=vls.getPendingRegistrations();
		System.out.println("lst="+lst.size());
		ModelAndView mv=new ModelAndView();
		mv.addObject("stal",lst);
		mv.setViewName("UpdateStudentStatus.jsp");
		return mv;
	
	}
	@RequestMapping("/followers")
	@SessionScope
	public ModelAndView followers(HttpSession ses) {
		
		List<Student> lst = new ArrayList<Student>();
		Student vls= new Student();
		vls.setUserid(ses.getAttribute("userid").toString().trim());
		lst=vls.getFollowers();
		System.out.println("lst="+lst.size());
		ModelAndView mv=new ModelAndView();
		mv.addObject("stal",lst);
		
		mv.setViewName("viewFollowers.jsp");
		return mv;
	
	}
	@RequestMapping("/followings")
	@SessionScope
	public ModelAndView followings(HttpSession ses) {
		
		List<Student> lst = new ArrayList<Student>();
		Student vls= new Student();
		vls.setUserid(ses.getAttribute("userid").toString().trim());
		lst=vls.getFollowings();
		System.out.println("lst="+lst.size());
		ModelAndView mv=new ModelAndView();
		mv.addObject("stal",lst);
		
		mv.setViewName("viewFollowings.jsp");
		return mv;
	
	}
	@RequestMapping("/pendingFollowers")
	@SessionScope
	public ModelAndView pendingFollowers(HttpSession ses) {
		
		List<Student> lst = new ArrayList<Student>();
		Student vls= new Student();
		vls.setUserid(ses.getAttribute("userid").toString().trim());
		lst=vls.getPendingFollowers();
		System.out.println("lst="+lst.size());
		ModelAndView mv=new ModelAndView();
		mv.addObject("stal",lst);
		
		mv.setViewName("viewPendingFollowers.jsp");
		return mv;
	
	}
	@RequestMapping("/approvestudentlist_al")
	@SessionScope
	public ModelAndView approvestudentlist_al() {
		
		List<WorkingProfessionals> lst = new ArrayList<WorkingProfessionals>();
		WorkingProfessionals vls= new WorkingProfessionals();
		
		lst=vls.getPendingRegistrations();
		System.out.println("lst="+lst.size());
		ModelAndView mv=new ModelAndView();
		mv.addObject("stal",lst);
		mv.setViewName("UpdateStudentStatus.jsp");
		return mv;
	
	}
	@RequestMapping("/check")
	public String check(CheckUser cu,HttpServletRequest request,HttpSession ses) {
		
		String st=cu.checkUser(request);
		if(st.equals("student") || st.equals("WorkingProfessional"))
		{

			JavaFuns jf=new JavaFuns();
			Vector v=jf.getValue("select currentsts,job_post,job_details from studentpersonal where userid='"+cu.getUserid().trim()+"'", 3);
		 	ses.setAttribute("currentsts", v.elementAt(0).toString().trim());
			ses.setAttribute("job_post", v.elementAt(1).toString().trim());
			ses.setAttribute("job_details", v.elementAt(2).toString().trim());
		}
		System.out.println(""+st);
		return st;
	}
	
	 
	@RequestMapping("/registerstaff")
	@SessionScope
	public String registerstaff()
	{
		return "RegisterStaff.jsp";
	}
	@RequestMapping("/regCourse")
	@SessionScope
	public String regCourse()
	{
		return "RegCourse.jsp";
	}
	
	   
	         
	@RequestMapping("/promoteStud")
	public ModelAndView promoteStud(HttpServletRequest request) {
		ModelAndView mv=new ModelAndView();
		String st="Failure.jsp";
		JavaFuns jf=new JavaFuns();
		if(request.getParameter("year").trim().equals("first"))
		{
			if(jf.execute("update studentpersonal set semester='second' where semester='first'")) {}
		}
		else if(request.getParameter("year").trim().equals("second"))
		{
			if(jf.execute("update studentpersonal set semester='third' where semester='second'")) {}
		}
		else
		{
			if(jf.execute("delete from users  where userid in (select userid from studentpersonal where semester='third')")) {}
			if(jf.execute("delete from studentpersonal  where semester='third'")) {}
			
		}
		st="Success.jsp?type=promote";
		mv.setViewName(st);
		mv.addObject("activity","promote");
		return mv;
	}
	 
	       
	@RequestMapping("/viewDocsStud")
	public ModelAndView viewDocsStud(HttpSession ses,HttpServletRequest request)
	{
		
		List<Documents> lst = new ArrayList<Documents>();
		List<Documents> lst1 = new ArrayList<Documents>();
		Documents obj=new Documents();
		obj.setCourse(request.getParameter("course").toString().trim());
		 obj.getDocs1(request.getParameter("course").toString().trim(),"Tutorial");
		 
		 lst=obj.getLstdocs();
		 obj.getDocs11(request.getParameter("course").toString().trim(),"Tutorial");
		 lst1=obj.getLstdocs();
System.out.println("lstsize="+lst.size());
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("viewDocsStud.jsp");
		mv.addObject("lst", lst); 
		mv.addObject("lst1", lst1);
		return mv;
		 
	}
	@RequestMapping("/viewDocs")
	public ModelAndView viewDocs(HttpSession ses)
	{
		
		List<Documents> lst = new ArrayList<Documents>();
		Documents obj=new Documents();
		obj.setUserid(ses.getAttribute("userid").toString().trim());
		 obj.getDocs();
		 lst=obj.getLstdocs();
System.out.println("lstsize="+lst.size());
		ModelAndView mv = new ModelAndView();

		mv.setViewName("viewDocs.jsp");
		mv.addObject("lst", lst); 
		return mv;
		 
	}
	 
	 
	@RequestMapping("/forgetpassword")
	public String forgetpassword() {
		
		return("ForgetPassword.jsp");
	}
	
	@RequestMapping("/recoverpassword")
	public String recoverpassword(PasswordRecovery pr,HttpServletRequest request) {
		 String filepath=request.getServletContext().getRealPath("/gmail_api/");
			
		String sts=pr.getNewPassword(filepath);
		
		return(sts);
	}
	
	 
}
