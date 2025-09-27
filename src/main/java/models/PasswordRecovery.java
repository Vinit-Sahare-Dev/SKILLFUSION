package models;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

import  beans.*;

public class PasswordRecovery {
	
	private String userid;
	private String username;
	private String emailid;
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmailid() {
		return emailid;
	}
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}
	
	
	public String getNewPassword(String path) {
		String password="",sts="UserRegFailure.jsp";
		try {
		GMailer ml = new GMailer(path);
		GetConnection gc = new GetConnection();
		Connection con;
		ResultSet rs;
		PreparedStatement pst;
		
		int x=0;
		
		Date date1 = Calendar.getInstance().getTime();  
	    DateFormat dateFormat = new SimpleDateFormat("hhmmss");  
	    String ins = dateFormat.format(date1);
	    
	    password=username.substring(0,2)+ins;
		
		try {
			
			con=gc.getConnection();
			
			pst=con.prepareStatement("update users SET pswd=? where userid=? and usernm=?");
			pst.setString(1, password);
			pst.setString(2, userid);
			pst.setString(3, username);
			
			x=pst.executeUpdate();
			
			if(x>0) {
				
			ml.passRecovery(username,password,emailid);
			sts="PassSuccess.jsp?msg=S";
			}
			else 
				sts="PassSuccess.jsp?msg=F";
			
			
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		
		return(sts);
	}

}
