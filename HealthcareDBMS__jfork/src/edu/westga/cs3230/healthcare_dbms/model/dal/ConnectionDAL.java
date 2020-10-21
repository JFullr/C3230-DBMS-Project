package edu.westga.cs3230.healthcare_dbms.model.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDAL {

    private String dbUrl;
    private Connection connection;
    private boolean inTransaction;

    public ConnectionDAL(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public Connection getConnection() throws SQLException {
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
}
