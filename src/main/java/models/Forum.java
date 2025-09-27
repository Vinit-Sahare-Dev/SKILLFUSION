package models; 

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import beans.GetConnection;
import jakarta.servlet.http.HttpSession;

public class Forum {
    private int qid,pid;
    private String userid,ques,ans;
    private String username;
    private String utype,vtype;
    private String title,details,tech;
    private String ptype,tm,dt,attachment;
    
    private MultipartFile file;
    Connection con;
    CallableStatement csmt;
    ResultSet rs;
    List<Forum> postList;
    
    public int getPid() {
		return pid;
	}


	public void setPid(int pid) {
		this.pid = pid;
	}


	public MultipartFile getFile() {
		return file;
	}

	 
	public String getAttachment() {
		return attachment;
	}


	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}


	public int getQid() {
		return qid;
	}


	public String getAns() {
		return ans;
	}


	public void setAns(String ans) {
		this.ans = ans;
	}


	public void setQid(int qid) {
		this.qid = qid;
	}


	public String getQues() {
		return ques;
	}


	public void setQues(String ques) {
		this.ques = ques;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getTech() {
		return tech;
	}

	public void setTech(String tech) {
		this.tech = tech;
	}

	public String getPtype() {
		return ptype;
	}

	public void setPtype(String ptype) {
		this.ptype = ptype;
	}

	public Connection getCon() {
		return con;
	}

	public void setCon(Connection con) {
		this.con = con;
	}

	public CallableStatement getCsmt() {
		return csmt;
	}

	public void setCsmt(CallableStatement csmt) {
		this.csmt = csmt;
	}

	public ResultSet getRs() {
		return rs;
	}

	public void setRs(ResultSet rs) {
		this.rs = rs;
	}

	public GetConnection getGc() {
		return gc;
	}

	public void setGc(GetConnection gc) {
		this.gc = gc;
	}

	public void setPostList(List<Forum> postList) {
		this.postList = postList;
	}

 

	public String getTm() {
		return tm;
	}

	public void setTm(String tm) {
		this.tm = tm;
	}

	public String getDt() {
		return dt;
	}

	public void setDt(String dt) {
		this.dt = dt;
	}
 

	public String getVtype() {
		return vtype;
	}

	public void setVtype(String vtype) {
		this.vtype = vtype;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public Forum() {
    }

    public Forum(ResultSet rs) {
        try {
            qid = rs.getInt("qid");
            userid = rs.getString("userid").trim();
            username = rs.getString("username").trim();
            utype = rs.getString("utype").trim();
            ques = rs.getString("ques").trim();
           
            dt = rs.getString("dt").trim();
            tm = rs.getString("tm").trim();
            
        } catch (Exception e) {
            System.out.println("Error in Post model: " + e.getMessage());
        }
    }

    // Getters and Setters
    
    private GetConnection gc = new GetConnection(); // Use existing connection utility

    // ✅ Function to like/unlike a post
    public int toggleLike(int postId, String username) {
        try (Connection con = gc.getConnection()) { // Use gc.getConnection()
            String checkQuery = "SELECT COUNT(*) FROM post_likes WHERE post_id = ? AND username = ?";
            try (PreparedStatement checkStmt = con.prepareStatement(checkQuery)) {
                checkStmt.setInt(1, postId);
                checkStmt.setString(2, username);
                ResultSet rs = checkStmt.executeQuery();
                rs.next();
                int count = rs.getInt(1);

                if (count == 0) {
                    String insertQuery = "INSERT INTO post_likes (post_id, username) VALUES (?, ?)";
                    try (PreparedStatement insertStmt = con.prepareStatement(insertQuery)) {
                        insertStmt.setInt(1, postId);
                        insertStmt.setString(2, username);
                        insertStmt.executeUpdate();
                    }
                } else {
                     
                }
            }

            return getLikeCount(postId);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    // ✅ Function to get the like count for a post
    public int getLikeCount(int postId) {
        try (Connection con = gc.getConnection();
             PreparedStatement stmt = con.prepareStatement("SELECT COUNT(*) FROM post_likes WHERE post_id = ?")) {
            stmt.setInt(1, postId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    
    public String RegAns(HttpSession ses) {
        PreparedStatement pst;
        GetConnection gc = new GetConnection();
        String status = "";
        userid=ses.getAttribute("userid").toString().trim();
        username=ses.getAttribute("username").toString().trim();
        utype=ses.getAttribute("usertype").toString().trim();
        try {
            con = gc.getConnection();
            pst = con.prepareStatement("INSERT INTO forum_ans(userid,username,utype,qid,ans,attachment,dt,tm) VALUES(?,?,?,?,?,?,?,?)");
             
            pst.setString(1, userid);
            pst.setString(2, username);
            pst.setString(3, utype);
            pst.setInt(4, qid);
            pst.setString(5, ans);
            pst.setString(6, attachment);
            Date dt=new Date();
            pst.setString(7, (dt.getDate()+"/"+(dt.getMonth()+1)+"/"+(dt.getYear()+1900)));
            pst.setString(8, (dt.getHours()+":"+dt.getMinutes()));
            int x = pst.executeUpdate();
            status = (x > 0) ? "ShowQues.jsp?quesid="+qid+"&ques="+ques : "Failure.jsp";
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
            status = "Failure.jsp";
        }  finally {
        	try{con.close();}catch (Exception e1) {
				// TODO: handle exception
			}}
        return status;
    }
    public String addQues(HttpSession ses) {
        PreparedStatement pst;
        GetConnection gc = new GetConnection();
        String status = "";
        userid=ses.getAttribute("userid").toString().trim();
        username=ses.getAttribute("username").toString().trim();
        utype=ses.getAttribute("usertype").toString().trim();
        try {
            con = gc.getConnection();
            pst = con.prepareStatement("INSERT INTO forum_ques(userid,username,utype,ques,sts,dt,tm) VALUES(?,?,?,?,?,?,?)");
             
            pst.setString(1, userid);
            pst.setString(2, username);
            pst.setString(3, utype);
            pst.setString(4, ques);
            pst.setString(5, "pending");
            pst.setString(6, dt);
            pst.setString(7, tm);
             
            
            Date dt=new Date();
            pst.setString(5, (dt.getDate()+"/"+(dt.getMonth()+1)+"/"+(dt.getYear()+1900)));
            pst.setString(6, (dt.getHours()+":"+dt.getMinutes()));
            int x = pst.executeUpdate();
            status = (x > 0) ? "Forum.jsp" : "Failure.jsp";
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
            status = "Failure.jsp";
        }  finally {
        	try{con.close();}catch (Exception e1) {
				// TODO: handle exception
			}}
        return status;
    }

     

    public void getAllProjects() {
        try {
            GetConnection gc = new GetConnection();
            con = gc.getConnection();
            csmt = con.prepareCall("{call getAllProjects()}");
            csmt.execute();
            rs = csmt.getResultSet();
            postList = new ArrayList<>();
            while (rs.next()) {
                postList.add(new Forum(rs));
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public List<Forum> getPostList() {
        return postList;
    }
}
