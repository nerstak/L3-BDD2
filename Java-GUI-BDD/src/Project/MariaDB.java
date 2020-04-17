package Project;

import java.sql.*;

public class MariaDB {
    //JDBC
    private static final String _driver = "org.mariadb.jdbc.Driver";
    private static final String _dbUrl = "jdbc:mariadb://127.0.0.1/rdvs";

    // Credentials
    private static final String _user = "root";
    private static final String _pass = "root";

    private static Connection _connection = null;

    /**
     * Open connection to database
     *
     * @return Boolean attesting the sequence of events
     */
    public static boolean openConnection() {
        try {
            Class.forName(_driver);
            System.out.println("Connecting to database...");
            _connection = DriverManager.getConnection(_dbUrl, _user, _pass);
            System.out.println("Connected successfully!");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Close the connection to the database
     */
    public static void closeConnection() {
        try {
            if (_connection != null) {
                _connection.close();
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    /**
     * Execute a query (as prepared)
     *
     * @param sqlStatement SQL Query
     * @return Result or null if error
     */
    public static ResultSet query(String sqlStatement) {
        try (PreparedStatement stmt = _connection.prepareStatement(sqlStatement)) {
            return stmt.executeQuery();
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
