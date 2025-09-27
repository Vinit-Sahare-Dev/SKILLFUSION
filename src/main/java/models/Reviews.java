package models;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import  beans.*; 


public class Reviews {
	private int reviewId;
	private String reviewText,dt;
	private String userid,userid1,username,username1;
 
	Connection con;
	CallableStatement csmt;
	ResultSet rs;
	List<Reviews> lstreviews;
	 
	public String getUserid1() {
		return userid1;
	}
	public void setUserid1(String userid1) {
		this.userid1 = userid1;
	}
	public String getUsername1() {
		return username1;
	}
	public void setUsername1(String username1) {
		this.username1 = username1;
	}
	public List<Reviews> getLstreviews() {
		return lstreviews;
	}
	public void setLstreviews(List<Reviews> lstreviews) {
		this.lstreviews = lstreviews;
	}
	public int getReviewId() {
		return reviewId;
	}
	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}
	 
	public String getReviewText() {
		return reviewText;
	}
	public void setReviewText(String reviewText) {
		this.reviewText = reviewText;
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
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	public Reviews()
	{
		
	}
	public Reviews(ResultSet rs)
	{
		try
		{
		reviewId=rs.getInt("reviewId");
		System.out.println("review="+reviewId);
		reviewText=rs.getString("reviewText").toString().trim();
		userid1=rs.getString("userid1");
		username1=rs.getString("usename1");
		dt=rs.getString("dt").toString().trim();
		userid=rs.getString("userid").toString().trim();
		username=rs.getString("username").toString().trim();
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println("err"+e.getMessage());
		}
	}
	public boolean registerReview(String path) {
	
	Connection con;
	CallableStatement csmt;
	GetConnection gc = new GetConnection();
	String sts="";
	
	try {
		con=gc.getConnection();
		 java.util.Date dt1=new Date();
		 dt=dt1.getDate()+"/"+(dt1.getMonth()+1)+"/"+(dt1.getYear()+1900);
		 csmt=con.prepareCall("{call insertReviews(?,?,?,?,?,?,?,?)}");
	        csmt.setString(1, reviewText);
	        csmt.setString(2, userid);
	        csmt.setString(3, username);
	        csmt.setString(4, userid1);
	        csmt.setString(5, username1);
	        SentimentAnalyzer sobj=new SentimentAnalyzer();
	        String result=sobj.analyzeSentiment(reviewText);
	        csmt.setString(6, dt);
	        csmt.setString(7, result);
	        csmt.setDouble(8, sobj.getScore());
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
	public void getReviews()
 	{
 	    try
 	    {
 	    	GetConnection obj = new GetConnection();
 	         
 	        con=obj.getConnection() ;
 	        csmt=con.prepareCall("{call getReviews(?)}"); 
 	        csmt.setString(1, userid1);
 	         csmt.execute();
 	         rs=csmt.getResultSet();
 	           lstreviews=new ArrayList<Reviews>();          
 	        while(rs.next())
 	        { System.out.println("true");
 	       lstreviews.add(new Reviews(rs)); 
 	        }
 	    }
 	       
 	     
 	    catch(Exception ex)
 	    {
 	        System.out.println("err="+ex.getMessage());
 	         
 	    }
 	}
	 
}
