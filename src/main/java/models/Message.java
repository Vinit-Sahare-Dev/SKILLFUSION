package models;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import beans.*;

public class Message {
    
    private int mid;
    private String userid;
    private String username;
    private String userid1;
    private String username1;
    private String msg;
    private String sub;
    private String attachment;
    private String dt;
    private MultipartFile file;
    Connection con;
    PreparedStatement pst;
    CallableStatement csmt;
    ResultSet rs;
    List<Message> messageList;

    public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public Message() { }

    public Message(ResultSet rs) {
        try {
            mid = rs.getInt("mid");
            userid = rs.getString("userid").trim();
            username = rs.getString("username").trim();
            userid1 = rs.getString("userid1").trim();
            username1 = rs.getString("username1").trim();
            msg = rs.getString("msg").trim();
            sub = rs.getString("sub").trim();
            attachment = rs.getString("attachment").trim();
            dt = rs.getString("dt").trim();
        } catch (Exception e) {
            System.out.println("Error in Message Constructor: " + e.getMessage());
        }
    }

    // Getters and Setters
    public int getMid() { return mid; }
    public void setMid(int mid) { this.mid = mid; }

    public String getUserid() { return userid; }
    public void setUserid(String userid) { this.userid = userid; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getUserid1() { return userid1; }
    public void setUserid1(String userid1) { this.userid1 = userid1; }

    public String getUsername1() { return username1; }
    public void setUsername1(String username1) { this.username1 = username1; }

    public String getMsg() { return msg; }
    public void setMsg(String msg) { this.msg = msg; }

    public String getSub() { return sub; }
    public void setSub(String sub) { this.sub = sub; }

    public String getAttachment() { return attachment; }
    public void setAttachment(String attachment) { this.attachment = attachment; }

    public String getDt() { return dt; }
    public void setDt(String dt) { this.dt = dt; }

    // Function to insert a new message
    public String insertMessage() {
        GetConnection gc = new GetConnection();
        String status = "";

        try {
            con = gc.getConnection();
            pst = con.prepareStatement("INSERT INTO messages VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

            pst.setInt(1, mid);
            pst.setString(2, userid);
            pst.setString(3, username);
            pst.setString(4, userid1);
            pst.setString(5, username1);
            pst.setString(6, msg);
            pst.setString(7, sub);
            pst.setString(8, attachment);
            pst.setString(9, dt);

            int rows = pst.executeUpdate();
            if (rows > 0)
                status = "Success";
            else
                status = "Failure";
        } catch (Exception e) {
            System.out.println("Error in insertMessage: " + e.getMessage());
            e.printStackTrace();
            status = "Failure";
        }
        return status;
    }

    // Function to generate new message ID
    public int getNewMessageId() {
        int maxId = 1000;
        try {
            GetConnection gc = new GetConnection();
            con = gc.getConnection();
            csmt = con.prepareCall("{call getMaxIdMessages()}"); // Stored procedure for max ID
            csmt.execute();
            rs = csmt.getResultSet();

            if (rs.next()) {
                maxId = rs.getInt("maxId");
            }
        } catch (Exception e) {
            System.out.println("Error in getNewMessageId: " + e.getMessage());
        }
        return maxId + 1;
    }

    // Function to get messages by recipient (userid1)
    public void getInbox(String recipientId) {
        try {
            GetConnection gc = new GetConnection();
            con = gc.getConnection();
            csmt = con.prepareCall("{call getInbox(?)}"); // Stored procedure
            csmt.setString(1, recipientId);
            csmt.execute();
            rs = csmt.getResultSet();
            messageList = new ArrayList<>();

            while (rs.next()) {
                messageList.add(new Message(rs));
            }
        } catch (Exception e) {
            System.out.println("Error in getMessagesByRecipient: " + e.getMessage());
        }
    }
    public void getSentItems(String recipientId) {
        try {
            GetConnection gc = new GetConnection();
            con = gc.getConnection();
            csmt = con.prepareCall("{call getSentItems(?)}"); // Stored procedure
            csmt.setString(1, recipientId);
            csmt.execute();
            rs = csmt.getResultSet();
            messageList = new ArrayList<>();

            while (rs.next()) {
                messageList.add(new Message(rs));
            }
        } catch (Exception e) {
            System.out.println("Error in getMessagesByRecipient: " + e.getMessage());
        }
    }
    public List<Message> getMessageList() {
        return messageList;
    }
}
