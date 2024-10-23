/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkgsuper.sudoku;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
    private static final String USER_NAME = "pdc";  // DB username
    private static final String PASSWORD = "pdc";   // DB password
    private static final String URL = "jdbc:derby:SuperSudokuDB_Ebd;create=true";  // Derby DB URL
    private static Connection conn = null;  // Singleton connection instance

    // Private constructor to prevent instantiation
    private DBManager() {
    }

    // Singleton pattern to ensure only one connection instance
    public static Connection getConnection() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
                System.out.println("Connected to SuperSudokuDB_Ebd");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }

    // Close connection
    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Connection to SuperSudokuDB_Ebd closed.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
