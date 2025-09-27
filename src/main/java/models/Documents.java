package models;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.springframework.web.multipart.MultipartFile;

import beans.GetConnection;  
public class Documents {
	Connection con;
    CallableStatement csmt;
    ResultSet rs;
private String docpath,course,topic,category,topicId,docDesc,doctitle,dt,tm,userid,docData;
private int docId;
private double weight,avgrating,searchcount,bookmarkcount,poscomm,negcomm,neucomm,accessDuration;
private List<Documents> lstdocs;
private MultipartFile file;


public String getCategory() {
	return category;
}

public void setCategory(String category) {
	this.category = category;
}

public double getWeight() {
	return weight;
}

public void setWeight(double weight) {
	this.weight = weight;
}

public double getAvgrating() {
	return avgrating;
}

public void setAvgrating(double avgrating) {
	this.avgrating = avgrating;
}

public double getSearchcount() {
	return searchcount;
}

public void setSearchcount(double searchcount) {
	this.searchcount = searchcount;
}

public double getBookmarkcount() {
	return bookmarkcount;
}

public void setBookmarkcount(double bookmarkcount) {
	this.bookmarkcount = bookmarkcount;
}

public double getPoscomm() {
	return poscomm;
}

public void setPoscomm(double poscomm) {
	this.poscomm = poscomm;
}

public double getNegcomm() {
	return negcomm;
}

public void setNegcomm(double negcomm) {
	this.negcomm = negcomm;
}

public double getNeucomm() {
	return neucomm;
}

public void setNeucomm(double neucomm) {
	this.neucomm = neucomm;
}

public double getAccessDuration() {
	return accessDuration;
}

public void setAccessDuration(double accessDuration) {
	this.accessDuration = accessDuration;
}

public String getTopicId() {
	return topicId;
}

public void setTopicId(String topicId) {
	this.topicId = topicId;
}

public ResultSet getRs() {
	return rs;
}

public void setRs(ResultSet rs) {
	this.rs = rs;
}

public String getDocDesc() {
	return docDesc;
}

public String getCourse() {
	return course;
}

public void setCourse(String course) {
	this.course = course;
}

public String getTopic() {
	return topic;
}

public void setTopic(String topic) {
	this.topic = topic;
}

public void setDocDesc(String docDesc) {
	this.docDesc = docDesc;
}

public String getDoctitle() {
	return doctitle;
}

public void setDoctitle(String doctitle) {
	this.doctitle = doctitle;
}

public String getDt() {
	return dt;
}

public void setDt(String dt) {
	this.dt = dt;
}

public String getTm() {
	return tm;
}

public void setTm(String tm) {
	this.tm = tm;
}

public String getDocData() {
	return docData;
}

public void setDocData(String docData) {
	this.docData = docData;
}

public String getUserid() {
	return userid;
}

public void setUserid(String userid) {
	this.userid = userid;
}

public MultipartFile getFile() {
	return file;
}

public void setFile(MultipartFile file) {
	this.file = file;
}
 
public String getDocpath() {
	return docpath;
}

public void setDocpath(String docpath) {
	this.docpath = docpath;
}

 
  
public int getDocId() {
	return docId;
}

public void setDocId(int docId) {
	this.docId = docId;
}

public List<Documents> getLstdocs() {
	return lstdocs;
}

public void setLstdocs(List<Documents> lstdocs) {
	this.lstdocs = lstdocs;
}

public void getId()
{
    try
    {
         GetConnection obj=new  GetConnection();
        con=obj.getConnection();
        csmt=con.prepareCall("{call getMaxIdDocuments()}");
       
         csmt.execute();
         rs=csmt.getResultSet();
                    
        boolean auth=false;
        while(rs.next())
        { System.out.println("true");
            auth=true;
            
            docId=rs.getInt("mxid");
            if(docId==0)
            	docId=1001;
            else
            	docId+=1;
              
        }
    }
       
     
    catch(Exception ex)
    {
        System.out.println("err="+ex.getMessage());
         
    }
}

public Documents()
{
	
}
public Documents(ResultSet rs)
{
	try
	{
		docpath=rs.getString("docPath").toString().trim();
		docId=rs.getInt("docId");
		docDesc=rs.getString("docDesc");
		doctitle=rs.getString("title");
		userid=rs.getString("userid");
		dt=rs.getString("dt");
		tm=rs.getString("tm");
		course=rs.getString("course");
		category=rs.getString("doctype");
		topic=rs.getString("topic");
		/*avgrating=rs.getDouble("avgRating");
		searchcount=rs.getDouble("searchCount");
		bookmarkcount=rs.getDouble("bookMarkCount");
		poscomm=rs.getDouble("positiveComments");
		negcomm=rs.getDouble("negativeComments");
		neucomm=rs.getDouble("neutralComments");
		accessDuration=rs.getDouble("accessDuration");
		weight=rs.getDouble("weight");*/
	}
	catch (Exception e) {
		// TODO: handle exception
		System.out.println("err="+e.getMessage());
	}
}
  /*
public void getImagesList(int hobbyId)
{
    try
    {
         DBConnector obj=new  DBConnector();
        con=obj.connect();
        csmt=con.prepareCall("{call getHobbyImages(?)}");
        lsthobby=new ArrayList<HobbyImages>();
        csmt.setInt(1, hobbyId);
         csmt.execute();
         rs=csmt.getResultSet();
                     
        while(rs.next())
        { System.out.println("true");
        lsthobby.add(new HobbyImages(rs));
              
        }
    }
       
     
    catch(Exception ex)
    {
        System.out.println("err="+ex.getMessage());
         
    }
}  */

public void getSharedDocuments(String uid)
{
    try
    {
    	GetConnection obj=new  GetConnection();
        con=obj.getConnection();
        csmt=con.prepareCall("{call getsharedDoc(?)}");
        lstdocs=new ArrayList<Documents>();
         csmt.setString(1, uid);
         csmt.execute();
         rs=csmt.getResultSet();
                     
        while(rs.next())
        { System.out.println("true");
        lstdocs.add(new Documents(rs)); 
        }
    } 
    catch(Exception ex)
    {
        System.out.println("err="+ex.getMessage());
         
    }
}

public void getRecommDocs()
{
    try
    {
    	GetConnection obj=new  GetConnection();
        con=obj.getConnection();
        csmt=con.prepareCall("{call getRecommDocs(?)}");
        lstdocs=new ArrayList<Documents>();
         csmt.setString(1, userid);
         csmt.execute();
         rs=csmt.getResultSet();
                     
        while(rs.next())
        { System.out.println("true");
        lstdocs.add(new Documents(rs)); 
        }
    } 
    catch(Exception ex)
    {
        System.out.println("err="+ex.getMessage());
         
    }
}
public void getDocs()
{
    try
    {
    	GetConnection obj=new  GetConnection();
        con=obj.getConnection();
        csmt=con.prepareCall("{call getDocs(?)}");
        lstdocs=new ArrayList<Documents>();
         csmt.setString(1, userid);
         csmt.execute();
         rs=csmt.getResultSet();
                     
        while(rs.next())
        { System.out.println("true");
        lstdocs.add(new Documents(rs)); 
        }
    } 
    catch(Exception ex)
    {
        System.out.println("err="+ex.getMessage());
         
    }
}
public void getDocs1(String course1,String type)
{
    try
    {
    	GetConnection obj=new  GetConnection();
        con=obj.getConnection();
        csmt=con.prepareCall("{call getDocs1(?,?)}");
        lstdocs=new ArrayList<Documents>();
          
         csmt.setString(1, course1);
         csmt.setString(2, type);
         csmt.execute();
         rs=csmt.getResultSet();
                     
        while(rs.next())
        { System.out.println("true");
        lstdocs.add(new Documents(rs)); 
        }
    } 
    catch(Exception ex)
    {
        System.out.println("err="+ex.getMessage());
         
    }
}
public void getDocs11(String course1,String type)
{
    try
    {
    	GetConnection obj=new  GetConnection();
        con=obj.getConnection();
        csmt=con.prepareCall("{call getDocs11(?,?)}");
        lstdocs=new ArrayList<Documents>();
          
         csmt.setString(1, course1);
         csmt.setString(2, type);
         csmt.execute();
         rs=csmt.getResultSet();
                     
        while(rs.next())
        { System.out.println("true");
        lstdocs.add(new Documents(rs)); 
        }
    } 
    catch(Exception ex)
    {
        System.out.println("err="+ex.getMessage());
         
    }
}
public void getDocsSearch(String branch,String course1,String topic1,String query)
{
    try
    {
    	GetConnection obj=new  GetConnection();
        con=obj.getConnection();
        csmt=con.prepareCall("{call getDocsSearch(?,?,?,?)}");
        lstdocs=new ArrayList<Documents>();
         csmt.setString(1, branch);
         csmt.setString(2, course1);
         csmt.setString(3, topic1);
         csmt.setString(4, query);
         csmt.execute();
         rs=csmt.getResultSet();
                     
        while(rs.next())
        { System.out.println("true");
        lstdocs.add(new Documents(rs)); 
        }
    } 
    catch(Exception ex)
    {
        System.out.println("err="+ex.getMessage());
         
    }
}
public void getPrefDocs1()
{
    try
    {
    	GetConnection obj=new  GetConnection();
        con=obj.getConnection();
        csmt=con.prepareCall("{call getPrefDocs1(?,?,?)}");
        lstdocs=new ArrayList<Documents>();
         csmt.setString(1, userid);
         csmt.setString(2, course);
         csmt.setString(3, topic);
         csmt.execute();
         rs=csmt.getResultSet();
                     
        while(rs.next())
        { System.out.println("true");
        lstdocs.add(new Documents(rs)); 
        }
    } 
    catch(Exception ex)
    {
        System.out.println("err="+ex.getMessage());
         
    }
}
public void getBookMarkDocs1()
{
    try
    {
    	GetConnection obj=new  GetConnection();
        con=obj.getConnection();
        csmt=con.prepareCall("{call getmybookmarks(?,?,?)}");
        lstdocs=new ArrayList<Documents>();
         csmt.setString(1, userid);
         csmt.setString(2, course);
         csmt.setString(3, topic);
         csmt.execute();
         rs=csmt.getResultSet();
                     
        while(rs.next())
        { System.out.println("true");
        lstdocs.add(new Documents(rs)); 
        }
    } 
    catch(Exception ex)
    {
        System.out.println("err="+ex.getMessage());
         
    }
}
public boolean registration()
{
    try
    { 
    	GetConnection obj=new  GetConnection();
        con=obj.getConnection();
        java.util.Date d=new java.util.Date();
        String dt1=(d.getDate()+"/"+(d.getMonth()+1)+"/"+(d.getYear()+1900));
        String tm1=d.getHours()+":"+d.getMinutes();
        csmt=con.prepareCall("{call insertDocuments(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
        csmt.setInt(1, docId);
        csmt.setString(2, doctitle);
        csmt.setString(3, docDesc);
        csmt.setString(4, userid);
        csmt.setString(5, docpath);
        csmt.setString(6, dt1);
        csmt.setString(7, tm1);
        csmt.setString(8, category);
        JavaFuns jf=new JavaFuns();
        Vector<String> v=jf.getValue("select courseName from courses where courseId="+course.trim(), 1);
       // Vector<String> v1=jf.getValue("select topicName from topics where topicId="+topicId.trim(), 1);
        
        csmt.setString(9,v.elementAt(0).trim() );
        csmt.setString(10, "NA");
        csmt.setInt(11, 0);
        csmt.setInt(12, 0);
        csmt.setInt(13, 0);
        csmt.setInt(14, 0);
        csmt.setInt(15, 0);
        csmt.setInt(16, 0);
        csmt.setInt(17, 0);
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
       
     
    catch(Exception ex)
    {
        System.out.println("err="+ex.getMessage());
        return false;
    }
}
}
