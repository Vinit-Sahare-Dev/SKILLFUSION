package models;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import  beans.GetConnection;
 
 

public class  Pass {
private String newpass,currentpass,rnewpass,userid;
 
Connection con;
CallableStatement csmt;
PreparedStatement psmt;
 
ResultSet rs;
 
 
public String getNewpass() {
	return newpass;
}
public void setNewpass(String newpass) {
	this.newpass = newpass;
}
public String getCurrentpass() {
	return currentpass;
}
public void setCurrentpass(String currentpass) {
	this.currentpass = currentpass;
}
public String getRnewpass() {
	return rnewpass;
}
public void setRnewpass(String rnewpass) {
	this.rnewpass = rnewpass;
}
public String getUserid() {
	return userid;
}
public void setUserid(String userid) {
	this.userid = userid;
}
 public Pass(){}
 
public boolean changePassword()
{
	boolean flag=false;
	GetConnection gc = new GetConnection();
    try
    {
       
    	con=gc.getConnection();
        csmt=con.prepareCall("{call chkPass(?,?)}");
        csmt.setString(1, userid);
        csmt.setString(2, currentpass);
         System.out.println("userid="+userid+" pass="+currentpass);
         csmt.execute();
         rs=csmt.getResultSet();
                     
        while(rs.next())
        { 
        	
            csmt=con.prepareCall("{call changePassword(?,?)}");
            csmt.setString(1, userid);
            csmt.setString(2, newpass);
            System.out.println("userid="+userid+" pass="+newpass);   
             
             int n=csmt.executeUpdate();
             if(n>0)
             {
            	 flag=true;
             }
        }
        
    }
       
     
    catch(Exception ex)
    {
        System.out.println("err="+ex.getMessage());
         
    }
    return flag;
}
 
}
