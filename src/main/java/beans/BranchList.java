package beans;

import java.sql.*;
import beans.GetConnection;
import java.util.*;

public class BranchList {
	
	private String branchid;
	private String branchname;
	
	
	public String getBranchid() {
		return branchid;
	}


	public void setBranchid(String branchid) {
		this.branchid = branchid;
	}


	public String getBranchname() {
		return branchname;
	}


	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}


	public List<BranchList> getBranchList(){
		
		Connection con;
		Statement st;
		GetConnection gc = new GetConnection();
		ResultSet rs;
		BranchList bl=new BranchList();
		
		List<BranchList> lst= new ArrayList<BranchList>();
		
		try {
			con=gc.getConnection();
			st=con.createStatement();
			
			rs=st.executeQuery("select * from branches");
			
			while(rs.next()) {
				
				bl=new BranchList();
				bl.setBranchname(rs.getString("branchname"));	
				lst.add(bl);
				
			}			
			 try{con.close();}catch(Exception ex){}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return (lst);
	}
	
	
}
