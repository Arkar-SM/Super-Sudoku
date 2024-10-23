package pkgsuper.sudoku;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    private static Database instance;  // Singleton instance
    private Connection conn;

    // Private constructor to prevent direct instantiation
    private Database() {
        connect();  // Establish the connection when the instance is created
    }

    // Singleton method to get the Database instance
    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();  // Create the instance if it doesn't exist
        }
        return instance;
    }

    // Method to connect to the Derby database
    public void connect() {
        try {
            // Load the Derby driver
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");

            // Derby DB connection URL
            String url = "jdbc:derby:SuperSudokuDB;create=true";
            conn = DriverManager.getConnection(url);
            System.out.println("Connected to the database successfully.");
        } catch (ClassNotFoundException e) {
            System.out.println("Error: Embedded Derby driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get the current connection
    public Connection getConnection() {
        return conn;
    }

    // Method to create the necessary tables
    public void createTables() {
        try {
            if (!doesTableExist("PROFILE")) {
                createProfileTable();
            }
            if (!doesTableExist("EASY_STATS")) {
                createGameModeStatsTable("EASY_STATS");
            }
            if (!doesTableExist("NORMAL_STATS")) {
                createGameModeStatsTable("NORMAL_STATS");
            }
            if (!doesTableExist("HARD_STATS")) {
                createGameModeStatsTable("HARD_STATS");
            }
            if (!doesTableExist("TEST_STATS")) {
                createGameModeStatsTable("TEST_STATS");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Check if a table exists in the database
    private boolean doesTableExist(String tableName) throws SQLException {
        DatabaseMetaData dbMetaData = conn.getMetaData();
        ResultSet tables = dbMetaData.getTables(null, null, tableName.toUpperCase(), null);
        return tables.next();  // Returns true if table exists, false otherwise
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
            return false;  // Return false if the profile already exists
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

    // Create PROFILE table
    private void createProfileTable() throws SQLException {
        String createProfileTableSQL = "CREATE TABLE PROFILE (" +
                                       "ID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " +
                                       "PLAYER_NAME VARCHAR(100), " +
                                       "EASY_BESTTIME DECIMAL(10, 2), " +
                                       "NORMAL_BESTTIME DECIMAL(10, 2), " +
                                       "HARD_BESTTIME DECIMAL(10, 2), " +
                                       "TEST_BESTTIME DECIMAL(10, 2))";
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(createProfileTableSQL);
            System.out.println("PROFILE table created.");
        }
    }

    // Create EASY, NORMAL, HARD, TEST game mode stats tables
    private void createGameModeTables() throws SQLException {
        createGameModeStatsTable("EASY_STATS");
        createGameModeStatsTable("NORMAL_STATS");
        createGameModeStatsTable("HARD_STATS");
        createGameModeStatsTable("TEST_STATS");
    }

    // Helper method to create stats tables for each game mode
    private void createGameModeStatsTable(String tableName) throws SQLException {
        String createTableSQL = "CREATE TABLE " + tableName + " (" +
                                "PLAYER_ID INT, " +
                                "MATCHES_PLAYED INT, " +
                                "BEST_TIME DECIMAL(10, 2), " +
                                "FOREIGN KEY (PLAYER_ID) REFERENCES PROFILE(ID))";
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(createTableSQL);
            System.out.println(tableName + " table created.");
        }
    }

    // Drop tables (optional if you want to reset the DB)
    public void dropTables() {
        String[] tables = {"PROFILE", "EASY_STATS", "NORMAL_STATS", "HARD_STATS", "TEST_STATS"};
        try (Statement stmt = conn.createStatement()) {
            for (String table : tables) {
                stmt.executeUpdate("DROP TABLE " + table);
                System.out.println("Dropped table: " + table);
            }
        } catch (SQLException e) {
            System.out.println("Error dropping tables: " + e.getMessage());
        }
    }

    // Method to close the connection
    public void close() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
