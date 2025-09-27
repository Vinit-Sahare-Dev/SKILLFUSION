package beans;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class APICaller {
    public static List<String> main(String userid) {
        
        	 String studentId = userid; // Replace with actual student ID
             String apiUrl = "http://127.0.0.1:5000/recommend/" + studentId;
             List<String> alumniList = new ArrayList<>();
             
             try {
                 URL url = new URL(apiUrl);
                 HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                 conn.setRequestMethod("GET");
                 conn.setRequestProperty("Accept", "application/json");

                 int responseCode = conn.getResponseCode();
                 if (responseCode != 200) {
                     throw new RuntimeException("Failed: HTTP error code: " + responseCode);
                 }

                 BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                 StringBuilder response = new StringBuilder();
                 String output;

                 while ((output = br.readLine()) != null) {
                     response.append(output);
                 }

                 System.out.println("API Response: " + response.toString());
                 // Parse JSON response
                  JSONArray jsonArray = new JSONArray(response.toString());

                 for (int i = 0; i < jsonArray.length(); i++) {
                     JSONObject obj = jsonArray.getJSONObject(i);
                     String alumniId = obj.getString("alumni_id");
                     alumniList.add(alumniId);
                 }

                 // Print the list of alumni IDs
                 System.out.println("Alumni IDs: " + alumniList);

                 conn.disconnect();
             } catch (Exception e) {
                 e.printStackTrace();
             }
             return alumniList;
    }
 // Function to generate SQL query
    public static String generateSQLQuery(List<String> alumniIds) {
        // Format the user IDs for SQL query
        StringBuilder query = new StringBuilder("SELECT * FROM studentpersonal WHERE userid IN (");

        for (int i = 0; i < alumniIds.size(); i++) {
            query.append("'").append(alumniIds.get(i)).append("'");
            if (i < alumniIds.size() - 1) {
                query.append(", ");
            }
        }

        query.append(");");
        return query.toString();
    }
}
