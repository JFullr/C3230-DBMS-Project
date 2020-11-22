package edu.westga.cs3230.healthcare_dbms.io.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

/**
 * Responsible for handling connections to the remote server and local database.
 *
 * @author Joseph Fuller and Andrew Steinborn
 */
public class DatabaseConnector {

    /** The db url. */
    private final String dbUrl;
    
    /** The connection. */
    private Connection connection;
    
    /** Whether the connector is being used in a transaction. */
    private boolean inTransaction;

    /**
     * Instantiates a new database connector.
     *
     * @param dbUrl the db url
     */
    public DatabaseConnector(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    /**
     * Gets the current connection.
     *
     * @return the current connection
     * @throws SQLException the SQL exception
     */
    public Connection getCurrentConnection() throws SQLException {
        if (connection != null && inTransaction) {
            return connection;
        }
        boolean valid = connection != null;
        if (valid) {
            try {
                valid = connection.isValid(1000);
            } catch (SQLException e) {
                valid = false;
            }
        }
        if (!valid) {
            connection = DriverManager.getConnection(dbUrl);
        }
        return connection;
    }

    /**
     * Sets the in transaction.
     *
     * @param inTransaction the new in transaction
     */
    public void setInTransaction(boolean inTransaction) {
        this.inTransaction = inTransaction;
    }

    /**
     * Gets the in transaction.
     *
     * @param <T> the generic type
     * @param callable the callable
     * @return the in transaction
     * @throws SQLException the SQL exception
     */
    public <T> T getInTransaction(Callable<T> callable) throws SQLException {
        if (this.inTransaction) {
            return doCallRunnableWithThrowingSqlException(callable);
        }

        Connection connection = this.getCurrentConnection();

        try {
            this.setInTransaction(true);
            connection.setAutoCommit(false);
            T result = doCallRunnableWithThrowingSqlException(callable);
            connection.commit();
            return result;
        } catch (Exception e) {
            connection.rollback();
            throw e;
        } finally {
            this.setInTransaction(false);
        }
    }

    /**
     * Do call runnable with throwing sql exception.
     *
     * @param <T> the generic type
     * @param callable the callable
     * @return the t
     * @throws SQLException the SQL exception
     */
    private <T> T doCallRunnableWithThrowingSqlException(Callable<T> callable) throws SQLException {
        try {
            return callable.call();
        } catch (SQLException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Do in transaction.
     *
     * @param runnable the runnable
     * @throws Exception the exception
     */
    public void doInTransaction(Runnable runnable) throws Exception {
        getInTransaction(Executors.callable(runnable));
    }
}