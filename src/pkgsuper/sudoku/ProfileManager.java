package pkgsuper.sudoku;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileManager {

    private Connection conn;

    public ProfileManager() {
        // Get connection from the singleton Database class
        conn = Database.getInstance().getConnection();
    }

    // Check if a profile exists in the database
    public boolean doesProfileExist(String playerName) {
        String query = "SELECT COUNT(*) FROM PROFILE WHERE PLAYER_NAME = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, playerName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;  // If count > 0, profile exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Create a new player profile in the database
    public boolean createProfile(String playerName) {
        if (doesProfileExist(playerName)) {
            System.out.println("Profile for " + playerName + " already exists.");
            return false;  // If profile exists, return false
        }

        String insertSQL = "INSERT INTO PROFILE (PLAYER_NAME, EASY_BESTTIME, NORMAL_BESTTIME, HARD_BESTTIME, TEST_BESTTIME) " +
                "VALUES (?, NULL, NULL, NULL, NULL)";
        try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setString(1, playerName);
            pstmt.executeUpdate();
            System.out.println("Profile created successfully for " + playerName);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update the best time for a player based on difficulty
    public void updateBestTime(String playerName, String difficulty, String elapsedTime) {
        String query = "SELECT EASY_BESTTIME, NORMAL_BESTTIME, HARD_BESTTIME, TEST_BESTTIME FROM PROFILE WHERE PLAYER_NAME = ?";
        String updateSQL = "UPDATE PROFILE SET %s = ? WHERE PLAYER_NAME = ?";
        String column = null;

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, playerName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                switch (difficulty) {
                    case "Easy":
                        column = "EASY_BESTTIME";
                        break;
                    case "Normal":
                        column = "NORMAL_BESTTIME";
                        break;
                    case "Hard":
                        column = "HARD_BESTTIME";
                        break;
                    case "Test":
                        column = "TEST_BESTTIME";
                        break;
                }

                if (column != null && isBetterTime(elapsedTime, rs.getString(column))) {
                    try (PreparedStatement updatePstmt = conn.prepareStatement(String.format(updateSQL, column))) {
                        updatePstmt.setString(1, elapsedTime);
                        updatePstmt.setString(2, playerName);
                        updatePstmt.executeUpdate();
                        System.out.println("Best time updated for " + playerName + " in " + difficulty + " mode.");
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Helper method to compare times in MM:SS format
    private boolean isBetterTime(String newTime, String oldTime) {
        if (oldTime == null) return true;  // If no previous time, the new time is better
        String[] newParts = newTime.split(":");
        String[] oldParts = oldTime.split(":");
        int newMinutes = Integer.parseInt(newParts[0]);
        int newSeconds = Integer.parseInt(newParts[1]);
        int oldMinutes = Integer.parseInt(oldParts[0]);
        int oldSeconds = Integer.parseInt(oldParts[1]);

        return (newMinutes < oldMinutes) || (newMinutes == oldMinutes && newSeconds < oldSeconds);
    }
}
