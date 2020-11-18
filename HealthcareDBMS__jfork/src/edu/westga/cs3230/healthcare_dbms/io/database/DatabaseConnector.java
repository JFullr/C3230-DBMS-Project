package edu.westga.cs3230.healthcare_dbms.io.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

/**
 * Responsible for handling connections to the remote server and local database.
 */
public class DatabaseConnector {

    private final String dbUrl;
    private Connection connection;
    private boolean inTransaction;

    public DatabaseConnector(String dbUrl) {
        this.dbUrl = dbUrl;
    }

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

    public void setInTransaction(boolean inTransaction) {
        this.inTransaction = inTransaction;
    }

    public <T> T getInTransaction(Callable<T> callable) throws Exception {
        if (this.inTransaction) {
            return callable.call();
        }

        // make sure the connection is available
        Connection connection = this.getCurrentConnection();

        try {
            this.setInTransaction(true);
            connection.setAutoCommit(false);
            T result = callable.call();
            connection.commit();
            return result;
        } catch (Exception e) {
            connection.rollback();
            throw e;
        } finally {
            this.setInTransaction(false);
        }
    }

    public void doInTransaction(Runnable runnable) throws Exception {
        getInTransaction(Executors.callable(runnable));
    }
}