package models;
import java.sql.*;
import java.util.List;
import java.util.Vector;

import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import beans.GetConnection;
 
public class CheckUser {

	private String userid,branch,year;
	private String pswd,resume;
		
	public String getResume() {
		return resume;
	}
	public void setResume(String resume) {
		this.resume = resume;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getPswd() {
		return pswd;
	}
	public void setPswd(String pswd) {
		this.pswd = pswd;
	}
	
	public String checkUser(HttpServletRequest request) {
		
		Connection con;
		ResultSet rs;
		String typ,st="";
		GetConnection gc = new GetConnection();
		
		
		try {
			
			con=gc.getConnection();
			PreparedStatement pst;
			pst=con.prepareStatement("select * from users where userid=? and pswd=? and userstatus='active' ");
			pst.setString(1,userid );
			pst.setString(2, pswd);
				
			rs=pst.executeQuery();
			
			if(rs.next()) {
								
				
				HttpSession sess=request.getSession(true);
				sess.setAttribute("userid",userid);
				sess.setAttribute("usertype", rs.getString("usertype"));
				sess.setAttribute("branch", rs.getString("branch"));
				sess.setAttribute("username", rs.getString("usernm"));
				
				typ=rs.getString("usertype");
				try {
				sess.setAttribute("photo", getPhoto(userid, typ));
				sess.setAttribute("year","NA");
				sess.setAttribute("resume",resume);
				System.out.println("type="+typ);
				LoginTracker lt=new LoginTracker();
				lt.recordLogin(userid, typ);
				}
				catch (Exception e) {
					// TODO: handle exception
					sess.setAttribute("photo", "NA");
				}
				if(typ.equals("WorkingProfessional"))
					st="WorkingProfessional";
				else if(typ.equals("student"))
					st="student";
				else if(typ.equals("admin"))
					st="admin";
				else
					st="LoginFailure.jsp";
			}
			else
				st="LoginFailure.jsp";
		}
		
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return(st);	
	}
public String getPhoto(String userid,String utype) {
		String photo="common.png";
		Connection con;
		ResultSet rs;
		String typ,st="";
		GetConnection gc = new GetConnection();
		
		
		try {
			
			
			con=gc.getConnection();
			PreparedStatement pst;
			String qr="";
			if(utype.equals("student")||utype.equals("WorkingProfessional"))
			{
				qr="select photo,semester,branch,stud_resume from studentpersonal where userid='"+userid+"'";
			 
			pst=con.prepareStatement(qr);
			 
			rs=pst.executeQuery();
			
			if(rs.next()) { 
				photo=rs.getString("photo");
				try {
					 resume=rs.getString("stud_resume");
					branch=rs.getString("branch");
					
					//System.out.println("year="+year);
				}
				catch (Exception e) {
				 e.printStackTrace();
					//year="NA";
				}
			}}else resume="NA";
			 
		}
		
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return(photo);	
	}
}
