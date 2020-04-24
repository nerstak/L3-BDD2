package Project;

import java.sql.*;

public class MariaDB<T> {
    //JDBC
    private static final String _driver = "org.mariadb.jdbc.Driver";
    private static final String _dbUrl = "jdbc:mariadb://127.0.0.1/rdvs";

    // Credentials
    private static final String _user = "root";
    private static final String _pass = "root";

    private static Connection _connection = null;

    private PreparedStatement _stmt;

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

    public MariaDB(String _request) {
        try {
            _stmt = _connection.prepareStatement(_request);
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }

    public <T> void setValue(Integer index, T obj) {
        try {
            _stmt.setObject(index, obj);
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }

    }

    public <T> void setValue(Integer index, T obj, int type) {
        try {
            _stmt.setObject(index, obj, type);
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }

    /**
     * Execute a query (as prepared)
     *
     * @return Result or null if error
     */
    public ResultSet executeQuery() {
        try {
            return _stmt.executeQuery();
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            return null;
        }
    }

    public void executeUpdate() {
        try {
            _stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }
}
