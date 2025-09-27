package models; 

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import beans.*;

public class Requests {

    private int reqid;
    private String userid,email1;
    private String userid1;
    private String username;
    private String username1;
    private int proj_id;
    private String proj_title;
    private String dt;
    private String sts;
    private String details;
    private String remark;

    Connection con;
    CallableStatement csmt;
    ResultSet rs;
    List<Requests> lstRequests;

    public List<Requests> getLstRequests() {
        return lstRequests;
    }

    public void setLstRequests(List<Requests> lstRequests) {
        this.lstRequests = lstRequests;
    }

    public String getEmail1() {
		return email1;
	}

	public void setEmail1(String email1) {
		this.email1 = email1;
	}

	public Requests() {}

    public Requests(ResultSet rs) {
        try {
            reqid = rs.getInt("reqid");
            userid = rs.getString("userid");
            userid1 = rs.getString("userid1");
            username = rs.getString("username");
            username1 = rs.getString("username1");
            proj_id = rs.getInt("proj_id");
            proj_title = rs.getString("proj_title");
            dt = rs.getString("dt");
            sts = rs.getString("sts");
            details = rs.getString("details");
            remark = rs.getString("remark");
        } catch (Exception e) {
            System.out.println("Error in Requests constructor: " + e.getMessage());
        }
    }
    public String insertRequest() {
        PreparedStatement pst;
        GetConnection gc = new GetConnection();
        String status = "";

        try {
            con = gc.getConnection();
            
            String query = "INSERT INTO requests (reqid, userid, userid1, username, username1, proj_id, proj_title, dt, sts, details, remark) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pst = con.prepareStatement(query);
            JavaFuns jf=new JavaFuns();
            int id=jf.FetchMax("reqid", "requests");
            pst.setInt(1, id);  // Get the next available request ID
            pst.setString(2, userid);
            pst.setString(3, userid1);
            pst.setString(4, username);
            pst.setString(5, username1);
            pst.setInt(6, proj_id);
            pst.setString(7, proj_title);
            java.util.Date d=new java.util.Date();
            dt=(d.getDate())+"/"+(d.getMonth()+1)+"/"+(d.getYear()+1900);
            pst.setString(8, dt);
            pst.setString(9, "pending");
            pst.setString(10, "NA");
            pst.setString(11, "NA");
            
            int rowsInserted = pst.executeUpdate();
            
            if (rowsInserted > 0)
                status = "Success";
            else
                status = "Failure";
        } catch (Exception ex) {
            System.out.println("Error in insertRequest: " + ex.getMessage());
            status = "Failure";
        }
        return status;
    }

    public void getRequests(String userid1) {
        try {
            GetConnection obj = new GetConnection();
            con = obj.getConnection();
            csmt = con.prepareCall("{call getRequestsByUserId1(?)}");
            csmt.setString(1, userid1);
            csmt.execute();
            rs = csmt.getResultSet();

            lstRequests = new ArrayList<>();
            while (rs.next()) {
                lstRequests.add(new Requests(rs));
            }
        } catch (Exception ex) {
            System.out.println("Error in getRequests: " + ex.getMessage());
        }
    }

    // Getters and Setters

    public int getReqid() {
        return reqid;
    }

    public void setReqid(int reqid) {
        this.reqid = reqid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserid1() {
        return userid1;
    }

    public void setUserid1(String userid1) {
        this.userid1 = userid1;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername1() {
        return username1;
    }

    public void setUsername1(String username1) {
        this.username1 = username1;
    }

    public int getProj_id() {
        return proj_id;
    }

    public void setProj_id(int proj_id) {
        this.proj_id = proj_id;
    }

    public String getProj_title() {
        return proj_title;
    }

    public void setProj_title(String proj_title) {
        this.proj_title = proj_title;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getSts() {
        return sts;
    }

    public void setSts(String sts) {
        this.sts = sts;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
