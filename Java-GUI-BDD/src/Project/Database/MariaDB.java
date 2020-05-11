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
    protected static boolean transactionError = false;


    static Connection _connection = null;

    /**
     * Open connection to database
     *
     * @return Boolean attesting the sequence of events
     */
    public static boolean openConnection(String user, String password) {
        try {
            Class.forName(_driver);
            System.out.println("Connecting to database...");
            _connection = DriverManager.getConnection(_dbUrl, user, password);
            System.out.println("Connected successfully!");
            _connection.setAutoCommit(false);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void commit() {
        try {
            _connection.commit();
            transactionError = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void rollback() {
        try {
            System.out.println("Rollback...");
            _connection.rollback();
            transactionError = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * End any query and rollback if error
     *
     * @return Validity of the query
     */
    public static boolean endQuery() {
        if (!transactionError) {
            try {
                MariaDB._connection.commit();
                transactionError = false;
                return true;
            } catch (SQLException e) {
                System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
                MariaDB.rollback();
                return false;
            }
        } else {
            MariaDB.rollback();
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
