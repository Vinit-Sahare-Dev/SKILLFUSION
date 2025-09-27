package models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import  beans.*;


public class Topics {
	private int courseId,topicId;
	private String courseName,topicName,dt,userid;
	private String branchname;
	Connection con;
	CallableStatement csmt;
	ResultSet rs;
	List<Topics> lstCourse;
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getBranchname() {
		return branchname;
	} 
	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}
	 
	public int getTopicId() {
		return topicId;
	}
	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}
	public String getTopicName() {
		return topicName;
	}
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}
	public String getDt() {
		return dt;
	}
	public void setDt(String dt) {
		this.dt = dt;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public List<Topics> getLstCourse() {
		return lstCourse;
	}
	public void setLstCourse(List<Topics> lstCourse) {
		this.lstCourse = lstCourse;
	}
	public Topics()
	{
		
	}
	public Topics(ResultSet rs)
	{
		try
		{
		courseId=rs.getInt("courseId");
		topicId=rs.getInt("topicId");
		topicName=rs.getString("topicName").toString().trim();
		courseName=rs.getString("courseName").toString().trim();
		userid=rs.getString("userid").toString().trim(); 
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}
	public boolean registerTopic() {
	
	Connection con;
	CallableStatement csmt;
	GetConnection gc = new GetConnection();
	String sts="";
	
	try {
		con=gc.getConnection();
		 java.util.Date d=new java.util.Date();
		 dt=d.getDate()+"/"+(d.getMonth()+1)+"/"+(d.getYear()+1900);
		 csmt=con.prepareCall("{call insertTopics(?,?,?,?)}");
		 csmt.setString(1, topicName);   
		 csmt.setInt(2, courseId);
	        csmt.setString(3, userid);
	        csmt.setString(4, dt);
	         
	         int n=csmt.executeUpdate(); 
	        if(n>0)
	        {
	            try{con.close();}catch(Exception ex){}
	            System.out.println("true");
	            return true;
	        }
	        else
	            return false; 
	}
	catch(Exception ex) {
		System.out.println("err="+ex.getMessage());
		ex.printStackTrace();
		return false; 
	}
	 
	}
	public void getTopics()
 	{
 	    try
 	    {
 	    	GetConnection obj = new GetConnection();
 	         
 	        con=obj.getConnection() ;
 	        csmt=con.prepareCall("{call getTopics(?,?)}"); 
 	        csmt.setInt(1, courseId);
 	        csmt.setString(2, userid);
 	         csmt.execute();
 	         rs=csmt.getResultSet();
 	           lstCourse=new ArrayList<Topics>();          
 	        while(rs.next())
 	        { System.out.println("true");
 	        	lstCourse.add(new Topics(rs)); 
 	        }
 	    }
 	       
 	     
 	    catch(Exception ex)
 	    {
 	        System.out.println("err="+ex.getMessage());
 	         
 	    }
 	}
	public void getTopics1()
 	{
 	    try
 	    {
 	    	GetConnection obj = new GetConnection();
 	         
 	        con=obj.getConnection() ;
 	        csmt=con.prepareCall("{call getTopics1(?)}"); 
 	        csmt.setString(1, courseName); 
 	         csmt.execute();
 	         rs=csmt.getResultSet();
 	           lstCourse=new ArrayList<Topics>();          
 	        while(rs.next())
 	        { System.out.println("true");
 	        	lstCourse.add(new Topics(rs)); 
 	        }
 	    }
 	       
 	     
 	    catch(Exception ex)
 	    {
 	        System.out.println("err="+ex.getMessage());
 	         
 	    }
 	}
}
