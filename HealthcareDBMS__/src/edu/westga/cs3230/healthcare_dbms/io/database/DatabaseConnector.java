package edu.westga.cs3230.healthcare_dbms.io.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Responsible for handling connections to the remote server and local database.
 */
public class DatabaseConnector {

    private final String dbUri;

    public DatabaseConnector(String dbUri) {
        this.dbUri = dbUri;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUri);
    }
}