package models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import  beans.*;


public class LoginTracker {
	private String userid,username,utype;
	private List<LoginTracker> lstonline;
	  

	public List<LoginTracker> getLstonline() {
		return lstonline;
	}
	public void setLstonline(List<LoginTracker> lstonline) {
		this.lstonline = lstonline;
	}
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
	public String getUtype() {
		return utype;
	}
	public void setUtype(String utype) {
		this.utype = utype;
	}
	public String recordLogin(String userid1,String utype1 ) {
	
	Connection con;
	CallableStatement pst;
	GetConnection gc = new GetConnection();
	String sts="",dt,tm;
	
	
	try {
		con=gc.getConnection();
		java.util.Date d=new java.util.Date();
		dt=d.getDate()+"/"+(d.getMonth()+1)+(d.getYear()+1900);
		tm=d.getHours()+":"+d.getMinutes();
		pst=con.prepareCall("{call insertLoginLog(?,?,?,?)}");
		pst.setString(1, userid1);
		pst.setString(2, utype1);
		pst.setString(3, dt);
		pst.setString(4, tm);
		
		int x= pst.executeUpdate();
		
		if(x>0)
			sts="Success.jsp";
		else
			sts="Failure.jsp";
	}
	catch(Exception ex) {
		System.out.println("err="+ex.getMessage());
		ex.printStackTrace();
	}
	return(sts);
	}
	public String recordLogout(String userid1 ) {
		
		Connection con;
		CallableStatement pst;
		GetConnection gc = new GetConnection();
		String sts="",dt,tm;
		
		
		try {
			con=gc.getConnection();
			 
			pst=con.prepareCall("{call updateLoginSts(?)}");
			pst.setString(1, userid1); 
			
			int x= pst.executeUpdate();
			
			if(x>0)
				sts="Success.jsp";
			else
				sts="Failure.jsp";
		}
		catch(Exception ex) {
			System.out.println("err="+ex.getMessage());
			ex.printStackTrace();
		}
		return(sts);
		}
public String getLastSeen(String userid1 ) {
		
		Connection con;
		CallableStatement pst;
		GetConnection gc = new GetConnection();
		String sts="NA",dt,tm;
		
		
		try {
			con=gc.getConnection();
			 
			pst=con.prepareCall("{call getlastSeen(?)}");
			pst.setString(1, userid1); 
			
			ResultSet rs= pst.executeQuery();
			while(rs.next())
			{
				sts=rs.getString("dt").toString()+" ("+rs.getString("tm").toString()+")";
			}
			 
		}
		catch(Exception ex) {
			System.out.println("err="+ex.getMessage());
			ex.printStackTrace();
		}
		return(sts);
		}
 	public void getOnlineUsers()
    {
        try
        {Connection con;
    	PreparedStatement pst;
    	GetConnection gc = new GetConnection();
    	
             
            con=gc.getConnection();
          CallableStatement csmt=con.prepareCall("{call getOnlineUsers()}");
           
             csmt.execute();
            ResultSet rs=csmt.getResultSet();
                 lstonline=new ArrayList<LoginTracker>();       
            boolean auth=false;
            while(rs.next())
            { System.out.println("true");
                auth=true;
                LoginTracker lt1=new LoginTracker();
                lt1.setUserid(rs.getString("userid").toString().trim());
                lt1.setUtype(rs.getString("utype").toString().trim());
                lt1.setUsername(rs.getString("username").toString().trim());
                lstonline.add(lt1);
                  
            }
        }
           
         
        catch(Exception ex)
        {
            System.out.println("err="+ex.getMessage());
             
        }
    } 
}
