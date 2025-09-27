package models;

import java.sql.*;
import  beans.*;
import java.util.*;

public class ViewStudent {

	private String userid;
	private String usernm;
	private String emailid;
	private String mobileno;
	private String gender;
	private String dob;
	private String semester;
	private String branch;
	private String lastseen;
	
	
	
	public String getLastseen() {
		return lastseen;
	}
	public void setLastseen(String lastseen) {
		this.lastseen = lastseen;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsernm() {
		return usernm;
	}
	public void setUsernm(String usernm) {
		this.usernm = usernm;
	}
	public String getEmailid() {
		return emailid;
	}
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}
	public String getMobileno() {
		return mobileno;
	}
	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}
	
	public List<ViewStudent> getStudentReport(){
		
		GetConnection gc = new GetConnection();
		Connection con;
		List<ViewStudent> lst = new ArrayList<ViewStudent>();
		
		ResultSet rs;
		
		try {
		
		con= gc.getConnection();
		Statement st=con.createStatement();
		rs=st.executeQuery("select s.* from studentpersonal s inner join users u on s.userid=u.userid where s.usertype='student' and u.userstatus='active' order by s.semester");
		ViewStudent vs;
		LoginTracker login=new LoginTracker();
		while(rs.next()) {
			
			vs= new ViewStudent();
			vs.setUserid(rs.getString("userid"));
			vs.setUsernm(rs.getString("usernm"));
			vs.setEmailid(rs.getString("emailid"));
			vs.setMobileno(rs.getString("mobileno"));
			vs.setGender(rs.getString("gender"));
			vs.setBranch(rs.getString("branch"));
			vs.setDob(rs.getString("dob"));
			vs.setSemester(rs.getString("semester"));
			vs.setLastseen(login.getLastSeen(rs.getString("userid").toString().trim()));
			lst.add(vs);
		}
		
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return(lst);
	}
 public List<ViewStudent> getStudentReport1(String yr){
		
		GetConnection gc = new GetConnection();
		Connection con;
		List<ViewStudent> lst = new ArrayList<ViewStudent>();
		
		ResultSet rs;
		
		try {
		
		con= gc.getConnection();
		Statement st=con.createStatement();
		rs=st.executeQuery("select s.* from studentpersonal s inner join users u on s.userid=u.userid where s.usertype='student' and u.userstatus='active' and s.semester='"+yr+"' order by s.semester");
		ViewStudent vs;
		LoginTracker login=new LoginTracker();
		while(rs.next()) {
			
			vs= new ViewStudent();
			vs.setUserid(rs.getString("userid"));
			vs.setUsernm(rs.getString("usernm"));
			vs.setEmailid(rs.getString("emailid"));
			vs.setMobileno(rs.getString("mobileno"));
			vs.setGender(rs.getString("gender"));
			vs.setBranch(rs.getString("branch"));
			vs.setDob(rs.getString("dob"));
			vs.setSemester(rs.getString("semester"));
			vs.setLastseen(login.getLastSeen(rs.getString("userid").toString().trim()));
			lst.add(vs);
		}
		
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return(lst);
	}
	public List<ViewStudent> getExStudentReport(){
		
		GetConnection gc = new GetConnection();
		Connection con;
		List<ViewStudent> lst = new ArrayList<ViewStudent>();
		
		ResultSet rs;
		
		try {
		
		con= gc.getConnection();
		Statement st=con.createStatement();
		rs=st.executeQuery("select * from studentpersonal where usertype='exstudent'");
		ViewStudent vs;
		LoginTracker login=new LoginTracker();
		while(rs.next()) {
			
			vs= new ViewStudent();
			vs.setUserid(rs.getString("userid"));
			vs.setUsernm(rs.getString("usernm"));
			vs.setEmailid(rs.getString("emailid"));
			vs.setMobileno(rs.getString("mobileno"));
			vs.setLastseen(login.getLastSeen(rs.getString("userid").toString().trim()));
			lst.add(vs);
		}
		
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return(lst);
	}
	
}
