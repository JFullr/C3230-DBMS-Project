package edu.westga.cs3230.healthcare_dbms.model.dal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import edu.westga.cs3230.healthcare_dbms.io.database.DatabaseConnector;
import edu.westga.cs3230.healthcare_dbms.io.database.QueryResult;
import edu.westga.cs3230.healthcare_dbms.sql.SqlManager;

/**
 * Provides a DAL for accessing available lab tests.
 *
 * @author Joseph Fuller and Andrew Steinborn
 */
public class LabTestDAL {

	/** The connector. */
	private DatabaseConnector connector;

	/**
	 * Instantiates a new lab test DAL.
	 *
	 * @param connector the connector
	 */
	public LabTestDAL(DatabaseConnector connector) {
		this.connector = connector;
	}
	
	/**
	 * Gets the lab tests.
	 *
	 * @return the lab tests
	 * @throws SQLException the SQL exception
	 */
	public QueryResult getLabTests() throws SQLException {
		String query = "select * from LabTest "
						+ "WHERE is_available = TRUE";
		
		SqlManager manager = new SqlManager();
		Connection con = this.connector.getCurrentConnection();
		try (Statement stmt = con.createStatement()) {
			ResultSet rs = stmt.executeQuery(query);
			manager.readTuples(rs);
		}
		
		return new QueryResult(manager.getTuples());
	}

}
