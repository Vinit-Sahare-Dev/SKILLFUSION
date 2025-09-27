package models;

import java.sql.*;
import beans.*;

public class ApproveStudents {
	
	private String userid;
	private String email;
	private String path;
	private String usernm;
	private int followId;
	
	public int getFollowId() {
		return followId;
	}
	public void setFollowId(int followId) {
		this.followId = followId;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getUsernm() {
		return usernm;
	}
	public void setUsernm(String usernm) {
		this.usernm = usernm;
	}
	public  String updateStudentStatus() {
		

		Connection con;
		PreparedStatement pst;
		GetConnection gc = new GetConnection();
		String sts="";
		try {
			con=gc.getConnection();
			
			pst=con.prepareStatement("update users SET userstatus='active' where userid=?;");
			pst.setString(1, userid);
			
			int x=pst.executeUpdate();
			
			if(x>0) {
				
				pst=con.prepareStatement("update studentpersonal SET userstatus='active' where userid=?");
				pst.setString(1, userid);
				
				x=pst.executeUpdate();
				try {
					GMailer gmail=new GMailer(path);
					String msg="<b>Dear "+usernm+"</b>,<br/><br/> your account has been approved by administrator. You can login into the system now.. <br/><br/><br/> <b>Alumni Connect</b>";
					
					gmail.sendMail("Account Approval", msg, email);
				}
				catch (Exception e) {
					// TODO: handle exception
				}
			}			
			else
				sts="failure";
			
			if(x>0)
				sts="success";
			else
				sts="failure";
			System.out.println("sts="+sts+" x="+x);
		}
		
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return(sts);
	}
public  String declineFollower() {
		

		Connection con;
		PreparedStatement pst;
		GetConnection gc = new GetConnection();
		String sts="";
		try {
			con=gc.getConnection();
			
			pst=con.prepareStatement("delete from follows where follow_id=?;");
			pst.setInt(1, followId);
			
			int x=pst.executeUpdate();
			
			if(x>0) {
				
				 
				try {
					GMailer gmail=new GMailer(path);
					String msg="<b>Dear "+usernm+"</b>,<br/><br/> your account has been approved by administrator. You can login into the system now.. <br/><br/><br/> <b>Alumni Connect</b>";
					
					gmail.sendMail("Account Approval", msg, email);
				}
				catch (Exception e) {
					// TODO: handle exception
				}
			}			
			else
				sts="failure";
			
			if(x>0)
				sts="success";
			else
				sts="failure";
			System.out.println("sts="+sts+" x="+x);
		}
		
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return(sts);
	}
public  String approveFollower() {
		

		Connection con;
		PreparedStatement pst;
		GetConnection gc = new GetConnection();
		String sts="";
		try {
			con=gc.getConnection();
			
			pst=con.prepareStatement("update follows SET sts='approved' where follow_id=?;");
			pst.setInt(1, followId);
			
			int x=pst.executeUpdate();
			
			if(x>0) {
				
				 
				try {
					GMailer gmail=new GMailer(path);
					String msg="<b>Dear "+usernm+"</b>,<br/><br/> your account has been approved by administrator. You can login into the system now.. <br/><br/><br/> <b>Alumni Connect</b>";
					
					gmail.sendMail("Account Approval", msg, email);
				}
				catch (Exception e) {
					// TODO: handle exception
				}
			}			
			else
				sts="failure";
			
			if(x>0)
				sts="success";
			else
				sts="failure";
			System.out.println("sts="+sts+" x="+x);
		}
		
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return(sts);
	}
public  String updateStudentStatus1(String userid) {
		

		Connection con;
		PreparedStatement pst;
		GetConnection gc = new GetConnection();
		String sts="";
		try {
			con=gc.getConnection();
			
			pst=con.prepareStatement("update users SET userstatus='deactive' where userid=?;");
			pst.setString(1, userid);
			
			int x=pst.executeUpdate();
			
			if(x>0) {
				
				pst=con.prepareStatement("update studentpersonal SET userstatus='active' where userid=?");
				pst.setString(1, userid);
				
				x=pst.executeUpdate();
			}			
			else
				sts="failure";
			
			if(x>0)
				sts="success";
			else
				sts="failure";
			System.out.println("sts="+sts+" x="+x);
		}
		
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return(sts);
	}
}
