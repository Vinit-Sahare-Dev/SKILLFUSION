package models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import  beans.*;


public class RegisterBranch {
	
	private int branchid;
	private String branchname;
	Connection con;
	CallableStatement csmt;
	ResultSet rs;
	List<RegisterBranch> lstbranch;
	
	public List<RegisterBranch> getLstbranch() {
		return lstbranch;
	}



	public void setLstbranch(List<RegisterBranch> lstbranch) {
		this.lstbranch = lstbranch;
	}


	public RegisterBranch()
	{
		
	}
	public RegisterBranch(ResultSet rs)
	{
		try
		{
		branchid=rs.getInt("branchid");
		branchname=rs.getString("branchName").toString().trim();
		 
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println("errr in branch="+e.getMessage());
		}
	}
	
	
	  
	public String getBranchname() {
		return branchname;
	}
 
	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}




	public int getBranchid() {
		return branchid;
	}



	public void setBranchid(int branchid) {
		this.branchid = branchid;
	}



	public String registerBranch() {
	 
	PreparedStatement pst;
	GetConnection gc = new GetConnection();
	String sts="";
	
	try {
		con=gc.getConnection();
		
		pst=con.prepareStatement("insert into branches values(?,?);");
		pst.setInt(1, getId());
		pst.setString(2, branchname);
		
		int x= pst.executeUpdate();
		
		if(x>0)
			sts="Success.jsp";
		else
			sts="Failure.jsp";
	}
	catch(Exception ex) {
		System.out.println("err="+ex.getMessage());
		ex.printStackTrace();
		sts="Failure.jsp";
	}
	return(sts);
	}
 	public int getId()
    {
 		int mxid=1000;
        try
        {
    	PreparedStatement pst;
    	GetConnection gc = new GetConnection();
    	
             
            con=gc.getConnection();
          CallableStatement csmt=con.prepareCall("{call getMaxIdBranches()}");
           
             csmt.execute();
            ResultSet rs=csmt.getResultSet();
                        
            boolean auth=false;
            while(rs.next())
            { System.out.println("true");
                auth=true;
                
                mxid=rs.getInt("mxid");
                  
            }
        }
           
         
        catch(Exception ex)
        {
            System.out.println("err="+ex.getMessage());
             
        }
        return (mxid+1);
    } 
 	public void getBranches()
 	{
 	    try
 	    {
 	    	GetConnection obj = new GetConnection();
 	         
 	        con=obj.getConnection() ;
 	        csmt=con.prepareCall("{call getBranches()}"); 
 	         csmt.execute();
 	         rs=csmt.getResultSet();
 	                 lstbranch=new ArrayList<RegisterBranch>();    
 	        while(rs.next())
 	        { System.out.println("true");
 	        	lstbranch.add(new RegisterBranch(rs)); 
 	        }
 	    }
 	       
 	     
 	    catch(Exception ex)
 	    {
 	        System.out.println("err="+ex.getMessage());
 	         
 	    }
 	}
}
