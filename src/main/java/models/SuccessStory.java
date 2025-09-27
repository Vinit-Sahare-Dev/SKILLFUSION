package models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import beans.*;

public class SuccessStory {
    
    private int sid;
    private String alumniName;
    private String story;
    private String profession;
    private String photo;
    private String dt;
    private String passoutYear;
    private MultipartFile file;
    Connection con;
    CallableStatement csmt;
    ResultSet rs;
    List<SuccessStory> lstStories;
    
    public List<SuccessStory> getLstStories() {
        return lstStories;
    }

    public void setLstStories(List<SuccessStory> lstStories) {
        this.lstStories = lstStories;
    }

    public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public SuccessStory() {
    }

    public SuccessStory(ResultSet rs) {
        try {
            sid = rs.getInt("sid");
            alumniName = rs.getString("alumni_name").trim();
            story = rs.getString("story").trim();
            profession = rs.getString("profession").trim();
            photo = rs.getString("photo").trim();
            dt = rs.getString("dt").trim();
            passoutYear = rs.getString("passout_year").trim();
        } catch (Exception e) {
            System.out.println("Error in SuccessStories model: " + e.getMessage());
        }
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getAlumniName() {
        return alumniName;
    }

    public void setAlumniName(String alumniName) {
        this.alumniName = alumniName;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getPassoutYear() {
        return passoutYear;
    }

    public void setPassoutYear(String passoutYear) {
        this.passoutYear = passoutYear;
    }

    public String registerSuccessStories() {
        PreparedStatement pst;
        GetConnection gc = new GetConnection();
        String status = "";

        try {
            con = gc.getConnection();
            pst = con.prepareStatement("INSERT INTO success_stories VALUES (?, ?, ?, ?, ?, ?, ?);");
            pst.setInt(1, getId());
            pst.setString(2, alumniName);
            pst.setString(3, story);
            pst.setString(4, profession);
            pst.setString(5, photo);
            pst.setString(6, dt);
            pst.setString(7, passoutYear);
            
            int x = pst.executeUpdate();
            
            if (x > 0)
                status = "Success.jsp";
            else
                status = "Failure.jsp";
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
            status = "Failure.jsp";
        }
        return status;
    }

    public int getId() {
        int maxId = 1000;
        try {
            PreparedStatement pst;
            GetConnection gc = new GetConnection();
            con = gc.getConnection();
            CallableStatement csmt = con.prepareCall("{call getMaxIdSuccessStories()}");
            csmt.execute();
            ResultSet rs = csmt.getResultSet();
            
            while (rs.next()) {
                maxId = rs.getInt("maxId");
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return (maxId + 1);
    }

    public void getSuccessStories() {
        try {
            GetConnection obj = new GetConnection();
            con = obj.getConnection();
            csmt = con.prepareCall("{call getSuccessStories()}");
            csmt.execute();
            rs = csmt.getResultSet();
            lstStories = new ArrayList<>();
            
            while (rs.next()) {
                lstStories.add(new SuccessStory(rs));
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}
