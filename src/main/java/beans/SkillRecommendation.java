package beans;

import java.sql.*;
import java.util.*;

public class SkillRecommendation {

    

    // Fetch required skills for the given project ID
    private static Set<String> getProjectSkills(int projectId) {
        Set<String> projectSkills = new HashSet<>();
        String query = "SELECT skill FROM proj_skills WHERE pid = ?";
        GetConnection obj=new GetConnection();
        try (Connection conn = obj.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, projectId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                projectSkills.add(rs.getString("skill"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projectSkills;
    }

    // Recommend students based on project skills
    public static List<Map.Entry<String, Integer>> recommendStudentsForProject(int projectId, int topN) {
        Set<String> projectSkills = getProjectSkills(projectId);
        Map<String, Set<String>> studentSkills = new HashMap<>();
        Map<String, Integer> studentScores = new HashMap<>();

        String query = "SELECT userid, skill FROM stud_skills WHERE utype = 'student'";
        GetConnection obj1=new GetConnection();
        try ( 
        		Connection conn = obj1.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String userId = rs.getString("userid");
                String skill = rs.getString("skill");

                studentSkills.putIfAbsent(userId, new HashSet<>());
                studentSkills.get(userId).add(skill);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Calculate match score
        for (Map.Entry<String, Set<String>> entry : studentSkills.entrySet()) {
            int matchScore = 0;
            for (String skill : entry.getValue()) {
                if (projectSkills.contains(skill)) {
                    matchScore++;
                }
            }
            if (matchScore > 0) {
                studentScores.put(entry.getKey(), matchScore);
            }
        }

        // Sort students by match score (descending)
        List<Map.Entry<String, Integer>> sortedStudents = new ArrayList<>(studentScores.entrySet());
        sortedStudents.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        return sortedStudents.subList(0, Math.min(topN, sortedStudents.size()));
    }
}

