package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import beans.APICaller;
import beans.GetConnection;

public class WorkingProfessionals {
	private String userid,otp,otp1,gcate,currentsts ;
	private String usernm;
	private String pswd;
	private String usertype;
	private String userstatus;
	private String emailid;
	private String mobileno;
	private String gender;
	private String semester;
	private String dob;
	private String branch,job_post,job_details;
	private MultipartFile file;
	private String path,selectedCategoriestxt;
	private List<String> selectedCategories;
	
	 
	public String getGcate() {
		return gcate;
	}
	public void setGcate(String gcate) {
		this.gcate = gcate;
	}
	public String getCurrentsts() {
		return currentsts;
	}
	public void setCurrentsts(String currentsts) {
		this.currentsts = currentsts;
	}
	public String getJob_post() {
		return job_post;
	}
	public void setJob_post(String job_post) {
		this.job_post = job_post;
	}
	public String getJob_details() {
		return job_details;
	}
	public void setJob_details(String job_details) {
		this.job_details = job_details;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public String getOtp1() {
		return otp1;
	}
	public void setOtp1(String otp1) {
		this.otp1 = otp1;
	}
	public String getSelectedCategoriestxt() {
		return selectedCategoriestxt;
	}
	public void setSelectedCategoriestxt(String selectedCategoriestxt) {
		this.selectedCategoriestxt = selectedCategoriestxt;
	}
	public List<String> getSelectedCategories() {
		return selectedCategories;
	}
	public void setSelectedCategories(List<String> selectedCategories) {
		this.selectedCategories = selectedCategories;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public String getGender() {
		return gender;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getUserid() {
		return userid;
	}
	public String getEmailid() {
		return emailid;
	}
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}
	public String getMobileno() {
		return mobileno;
	}
	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsernm() {
		return usernm;
	}
	public void setUsernm(String usernm) {
		this.usernm = usernm;
	}
	public String getPswd() {
		return pswd;
	}
	public void setPswd(String pswd) {
		this.pswd = pswd;
	}
	public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	public String getUserstatus() {
		return userstatus;
	}
	public void setUserstatus(String userstatus) {
		this.userstatus = userstatus;
	}
public List<Student> getAlumniReport(String userid1){
		
		GetConnection gc = new GetConnection();
		Connection con;
		List<Student> lst = new ArrayList<Student>();
		System.out.println("userid="+userid1);
		ResultSet rs;
		
		try {
		
		con= gc.getConnection();
		Statement st=con.createStatement();
		rs=st.executeQuery("select * from studentpersonal where usertype='alumni' and userid='"+userid1+"'");
		Student vs;
		 
		while(rs.next()) {
			
			vs= new Student();
			vs.setUserid(rs.getString("userid"));
			vs.setUsernm(rs.getString("usernm"));
			vs.setEmailid(rs.getString("emailid"));
			vs.setMobileno(rs.getString("mobileno"));
			vs.setGender(rs.getString("gender"));
			vs.setBranch(rs.getString("branch"));
			vs.setDob(rs.getString("dob"));
			vs.setSemester(rs.getString("semester"));
			vs.setUsertype(rs.getString("usertype"));
			vs.setSelectedCategoriestxt(rs.getString("categories"));
			lst.add(vs);
		}
		
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return(lst);
	}
	
	
	public String addNewStudent()
	{
		GetConnection gc = new GetConnection();
		int y=0;
		
		Connection con;
		String st="";
		try {
		con=gc.getConnection();
		PreparedStatement pst;
		
		pst=con.prepareStatement("insert into users values(?,?,?,?,?,?);");

		pst.setString(1,userid);
		pst.setString(2,usernm);
		pst.setString(3,pswd);
		pst.setString(4,usertype);
		pst.setString(5,"pending");
		pst.setString(6,branch);
		

		int x=pst.executeUpdate();
		
		if(x>0) {
			
			pst=con.prepareStatement("insert into studentpersonal values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			
			pst.setString(1,userid);
			pst.setString(2,usernm);
			pst.setString(3,usertype);
			pst.setString(4, branch);
			pst.setString(5, "NA");
			pst.setString(6,mobileno);
			pst.setString(7,emailid);
			pst.setString(8, dob);
			pst.setString(9, gender);
			pst.setString(10, "pending");
			pst.setString(11, path);
			
			//if(currentsts.equals("Placed"))
			//{ 
			 
			pst.setString(12, currentsts);
				pst.setString(13, job_post);
				pst.setString(14, job_details);
			//}
			//else {
			//	String categories = String.join(", ", selectedCategories);
			//	pst.setString(12, categories);
			//	pst.setString(13, currentsts);
			//pst.setString(14, "NA");
			//pst.setString(15, "NA");
			
			//}
				pst.setString(15, "NA");
				pst.setInt(16, 0);
				pst.setInt(17, 0);
				pst.setInt(18, 0);
				pst.setInt(19, 0);
			y=pst.executeUpdate();
		}
		else
			st="failure";
		
		if(y>0)
			st="success";
		else
			st="failure";
			
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return st;
		
		
	}
	public String updateStudent(String userid1)
	{
		GetConnection gc = new GetConnection();
		int y=0;
		
		Connection con;
		String st="";
		try {
		con=gc.getConnection();
		PreparedStatement pst;
		
		 if(!path.equals("NA"))
		 {
			pst=con.prepareStatement("update studentpersonal set mobileno=?,semester=?,emailid=?,photo=?,dob=? where userid=?");
			 
			pst.setString(1,mobileno);
			pst.setString(2,semester);
			pst.setString(3, emailid);
			pst.setString(4, path);
			pst.setString(5, dob);
			pst.setString(6, userid1);
		 }
		else
		{
			pst=con.prepareStatement("update studentpersonal set mobileno=?,semester=?,emailid=?,dob=?  where userid=?");
			 
			pst.setString(1,mobileno);
			pst.setString(2,semester);
			pst.setString(3, emailid); 
			pst.setString(4, dob);
			pst.setString(5, userid1);
		}
			y=pst.executeUpdate();
		 
		
		if(y>0)
			st="success";
		else
			st="failure";
			
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return st;
		
		
	}
	public List<WorkingProfessionals> getPendingRegistrations() {
	    List<WorkingProfessionals> pendingStudents = new ArrayList<>();
	    GetConnection gc = new GetConnection();
	    Connection con;
	    ResultSet rs;

	    try {
	        con = gc.getConnection();
	        String query = "SELECT * FROM studentpersonal WHERE  usertype='WorkingProfessional' and userstatus = 'pending'";
	        PreparedStatement pst = con.prepareStatement(query);
	        rs = pst.executeQuery();

	        while (rs.next()) {
	        	WorkingProfessionals student = new WorkingProfessionals();
	            student.setUserid(rs.getString("userid"));
	            student.setUsernm(rs.getString("usernm"));
	            student.setBranch(rs.getString("branch"));
	            student.setSemester(rs.getString("semester"));
	            student.setMobileno(rs.getString("mobileno"));
	            student.setEmailid(rs.getString("emailid"));
	            student.setDob(rs.getString("dob"));
	            student.setGender(rs.getString("gender"));
	            student.setUserstatus(rs.getString("userstatus"));
	            student.setPath(rs.getString("photo"));  // For displaying photo if available

	            pendingStudents.add(student);
	        }
	        con.close();
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	    return pendingStudents;
	}
	public List<WorkingProfessionals> getAlumniReport() {
	    List<WorkingProfessionals> pendingStudents = new ArrayList<>();
	    GetConnection gc = new GetConnection();
	    Connection con;
	    ResultSet rs;

	    try {
	        con = gc.getConnection();
	        String query = "SELECT * FROM studentpersonal WHERE  usertype='WorkingProfessional' and userstatus = 'active'";
	        PreparedStatement pst = con.prepareStatement(query);
	        rs = pst.executeQuery();

	        while (rs.next()) {
	        	WorkingProfessionals student = new WorkingProfessionals();
	            student.setUserid(rs.getString("userid"));
	            student.setUsernm(rs.getString("usernm"));
	            student.setBranch(rs.getString("branch"));
	            student.setSemester(rs.getString("semester"));
	            student.setMobileno(rs.getString("mobileno"));
	            student.setEmailid(rs.getString("emailid"));
	            student.setDob(rs.getString("dob"));
	            student.setGender(rs.getString("gender"));
	            student.setUserstatus(rs.getString("userstatus"));
	            student.setPath(rs.getString("photo"));  // For displaying photo if available

	            pendingStudents.add(student);
	        }
	        con.close();
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	    return pendingStudents;
	}
public List<Map<String, String>> getRecommendedAlumni(String uid)
{
	 Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    GetConnection getcon=new GetConnection();
	    List<Map<String, String>> alumniList = new ArrayList<>();

	    try {
	       
	        conn = getcon.getConnection();
	        List<String> lst=APICaller.main(uid);
	        String query =APICaller.generateSQLQuery(lst);
	        // Assuming you have a list of recommended alumni IDs (Pass them as a parameter)
	        //String recommendedAlumni = "'harish', 'ramesh', 'suresh'";  // Replace dynamically

	       // String query = "SELECT userid, usernm, photo, currentsts, job_post, job_details, categories FROM studentpersonal WHERE userid IN (" + recommendedAlumni + ")";
	        pstmt = conn.prepareStatement(query);
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            Map<String, String> alumni = new HashMap<>();
	            alumni.put("userid", rs.getString("userid"));
	            alumni.put("name", rs.getString("usernm"));
	            alumni.put("photo", rs.getString("photo") != null ? rs.getString("photo") : "default.jpg");
	            alumni.put("currentsts", rs.getString("currentsts"));
	            alumni.put("job_post", rs.getString("job_post"));
	            alumni.put("job_details", rs.getString("job_details"));
	            alumni.put("categories", rs.getString("categories"));
	            alumniList.add(alumni);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {try {
	        if (rs != null) rs.close();
	        if (pstmt != null) pstmt.close();
	        if (conn != null) conn.close();
	    }
	    catch (Exception e) {
			// TODO: handle exception
		}
	   
	    }return alumniList;
}
}
