package Project.Database;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * CallableStatement wrapper
 */
public class Callable extends MariaDB implements Statement {
    private CallableStatement _stmt;

    public Callable(String _request) {
        try {
            _stmt = _connection.prepareCall(_request);
        } catch (SQLException e) {
            MariaDB.transactionError = true;
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }

    }

    public <T> void setValue(Integer index, T obj) {
        try {
            _stmt.setObject(index, obj);
        } catch (SQLException e) {
            MariaDB.transactionError = true;
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }

    }

    public <T> void setValue(Integer index, T obj, int type) {
        try {
            _stmt.setObject(index, obj, type);
        } catch (SQLException e) {
            MariaDB.transactionError = true;
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }

    public ResultSet executeQuery() {
        try {
            return _stmt.executeQuery();
        } catch (SQLException e) {
            MariaDB.transactionError = true;
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            return null;
        }
    }

    public void executeUpdate() {
        try {
            _stmt.executeUpdate();
        } catch (SQLException e) {
            MariaDB.transactionError = true;
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }

    public void registerOutParameter(Integer index, int type) {
        try {
            _stmt.registerOutParameter(index, type);
        } catch (SQLException e) {
            MariaDB.transactionError = true;
            System.err.format("SQL State (registerOutParameter): %s\n%s", e.getSQLState(), e.getMessage());
        }
    }

    public int getInt(Integer index) {
        try {
            return _stmt.getInt(index);
        } catch (SQLException e) {
            MariaDB.transactionError = true;
            System.err.format("SQL State (getInt): %s\n%s", e.getSQLState(), e.getMessage());
            return 0;
        }
    }

    public String getString(Integer index) {
        try {
            return _stmt.getString(index);
        } catch (SQLException e) {
            MariaDB.transactionError = true;
            System.err.format("SQL State (getString): %s\n%s", e.getSQLState(), e.getMessage());
            return "";
        }
    }

    public boolean getBoolean(Integer index) {
        try {
            return _stmt.getBoolean(index);
        } catch (SQLException e) {
            MariaDB.transactionError = true;
            System.err.format("SQL State (getBoolean): %s\n%s", e.getSQLState(), e.getMessage());
            return false;
        }
    }
}
