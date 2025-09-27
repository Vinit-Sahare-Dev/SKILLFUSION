package beans;

import java.sql.*;

public class GetConnection {

	private Connection dbconnection;
    public GetConnection()
    {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            //dbconnection=DriverManager.getConnection("jdbc:mysql://uzsikp4p9tkkcgox:6NiFLfLsV9yjZSgOqhpz@bkb0luggcqcvnzkgqhvf-mysql.services.clever-cloud.com:3306/bkb0luggcqcvnzkgqhvf");
            dbconnection=DriverManager.getConnection("jdbc:mysql://avnadmin:AVNS_M26eZjMcbC9FTmvHVup@mysql-17f23fd6-skill-6010.i.aivencloud.com:19442/defaultdb?ssl-mode=REQUIRED");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public Connection getConnection()
    {
        return(dbconnection);
    }
	
}
