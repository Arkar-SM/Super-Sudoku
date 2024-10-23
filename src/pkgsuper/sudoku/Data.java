/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkgsuper.sudoku;

/**
 *
 * @author Asus
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Data {

    private Connection conn;

    public Data() {
        conn = DBManager.getConnection();  // Get the database connection from DBManager
    }

    // Create a new player profile
    public void createProfile(String playerName) {
        String insertSQL = "INSERT INTO PROFILE (PLAYER_NAME) VALUES (?)";
        try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setString(1, playerName);
            pstmt.executeUpdate();
            System.out.println("Profile created for player: " + playerName);
        } catch (SQLException e) {
            System.out.println("Error creating profile: " + e.getMessage());
        }
    }

    // Update the best time for a player in a given game mode
    public void updateBestTime(String playerName, String mode, double bestTime) {
        String updateSQL = "UPDATE PROFILE SET " + mode.toUpperCase() + "_BESTTIME = ? WHERE PLAYER_NAME = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
            pstmt.setDouble(1, bestTime);
            pstmt.setString(2, playerName);
            pstmt.executeUpdate();
            System.out.println("Best time updated for " + playerName + " in mode: " + mode);
        } catch (SQLException e) {
            System.out.println("Error updating best time: " + e.getMessage());
        }
    }

    // Retrieve the best time for a player in a specific mode
    public double getBestTime(String playerName, String mode) {
        String selectSQL = "SELECT " + mode.toUpperCase() + "_BESTTIME FROM PROFILE WHERE PLAYER_NAME = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {
            pstmt.setString(1, playerName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching best time: " + e.getMessage());
        }
        return -1;  // Return a default value if not found
    }

    // Fetch profile details by player name
    public ResultSet getProfile(String playerName) {
        String selectSQL = "SELECT * FROM PROFILE WHERE PLAYER_NAME = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(selectSQL);
            pstmt.setString(1, playerName);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Error fetching profile: " + e.getMessage());
        }
        return null;
    }

    // Fetch all profiles (example method)
    public ResultSet getAllProfiles() {
        String selectSQL = "SELECT * FROM PROFILE";
        try {
            PreparedStatement pstmt = conn.prepareStatement(selectSQL);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Error fetching profiles: " + e.getMessage());
        }
        return null;
    }

    // Close the connection to avoid leaks
    public void close() {
        DBManager.closeConnection();
    }
}
