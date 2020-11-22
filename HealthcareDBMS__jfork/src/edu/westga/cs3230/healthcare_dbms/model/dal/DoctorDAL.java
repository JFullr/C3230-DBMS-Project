package edu.westga.cs3230.healthcare_dbms.model.dal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import edu.westga.cs3230.healthcare_dbms.io.database.DatabaseConnector;
import edu.westga.cs3230.healthcare_dbms.io.database.QueryResult;
import edu.westga.cs3230.healthcare_dbms.sql.SqlManager;

/**
 * Provides a DAL for accessing doctors in the database.
 *
 * @author Joseph Fuller and Andrew Steinborn
 */
public class DoctorDAL {
	
	/** The connector. */
	private DatabaseConnector connector;

	/**
	 * Instantiates a new doctor DAL.
	 *
	 * @param connector the connector
	 */
	public DoctorDAL(DatabaseConnector connector) {
		this.connector = connector;
	}
	
	/**
	 * Gets the doctors.
	 *
	 * @return the doctors
	 * @throws SQLException the SQL exception
	 */
	public QueryResult getDoctors() throws SQLException {
		String query = "select p.*  "
						+ "from Person p, Doctor d "
						+ "where p.person_id = d.person_id";
		
		SqlManager manager = new SqlManager();
		Connection con = this.connector.getCurrentConnection();
		try (Statement stmt = con.createStatement()) {
			ResultSet rs = stmt.executeQuery(query);
			manager.readTuples(rs);
		}
		
		return new QueryResult(manager.getTuples());
	}

}
