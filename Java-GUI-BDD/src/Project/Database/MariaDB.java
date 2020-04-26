package Project.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * MariaDB Connection wrapper
 */
public class MariaDB {
    //JDBC
    private static final String _driver = "org.mariadb.jdbc.Driver";
    private static final String _dbUrl = "jdbc:mariadb://127.0.0.1/rdvs";

    // Credentials
    private static final String _user = "root";
    private static final String _pass = "root";

    static Connection _connection = null;

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
}
