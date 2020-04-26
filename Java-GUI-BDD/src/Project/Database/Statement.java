package Project.Database;

import java.sql.ResultSet;

/**
 * Statement interface
 */
public interface Statement {
    /**
     * Set value of a parameter
     *
     * @param index Index of parameter (from 1)
     * @param obj   Object to set value
     * @param <T>   Type of object (Java)
     */
    <T> void setValue(Integer index, T obj);

    /**
     * Set value of a parameter
     *
     * @param index Index of parameter (from 1)
     * @param obj   Object to set value
     * @param type  Type of object in SQL.Types
     * @param <T>   Type of object (Java)
     */
    <T> void setValue(Integer index, T obj, int type);

    /**
     * Execute an update
     */
    void executeUpdate();

    /**
     * Execute a query (as prepared)
     *
     * @return Result or null if error
     */
    ResultSet executeQuery();
}
