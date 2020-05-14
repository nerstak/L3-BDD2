package Project.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * PreparedStatement wrapper
 */
public class Prepared extends MariaDB implements Statement {
    private PreparedStatement _stmt;

    public Prepared(String _request) {
        try {
            _stmt = _connection.prepareStatement(_request);
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
}
