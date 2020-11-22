package edu.westga.cs3230.healthcare_dbms.model.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.westga.cs3230.healthcare_dbms.io.database.DatabaseConnector;
import edu.westga.cs3230.healthcare_dbms.model.Person;
import edu.westga.cs3230.healthcare_dbms.sql.SqlManager;

// TODO: Auto-generated Javadoc
/**
 * Obtains the user type from the DB.
 *
 * @author Joseph Fuller and Andrew Steinborn
 */
public class UserTypeDAL {

	/** The connector. */
	private DatabaseConnector connector;

	/**
	 * Instantiates a new user type DAL.
	 *
	 * @param connector the connector
	 */
	public UserTypeDAL(DatabaseConnector connector) {
		this.connector = connector;
	}

	/**
	 * Gets the user type.
	 *
	 * @param patient the patient
	 * @return the user type
	 * @throws SQLException the SQL exception
	 */
	public String getUserType(Person patient) throws SQLException {
		
		return this.determineUserType(patient.getPerson_id());
	}

	/**
	 * Determine user type.
	 *
	 * @param person_id the person id
	 * @return the string
	 * @throws SQLException the SQL exception
	 */
	private String determineUserType(int person_id) throws SQLException {
		
		String[] tables = {"Doctor", "Nurse", "Admin", "Patient"};
		for(String table : tables) {
			if(this.readType(table, person_id)) {
				return table;
			}
		}

		return null;
	}

	/**
	 * Read type.
	 *
	 * @param tableName the table name
	 * @param person_id the person id
	 * @return true, if successful
	 * @throws SQLException the SQL exception
	 */
	private boolean readType(String tableName, int person_id) throws SQLException {

		String prepared = "select ssn " + 
							"from ? " + 
							"where person_id = ?";

		SqlManager manager = new SqlManager();
		Connection con = this.connector.getCurrentConnection();
		try (PreparedStatement stmt = con.prepareStatement(prepared)) {
			stmt.setString(1, "" + tableName);
			stmt.setObject(2, person_id);
			ResultSet rs = stmt.executeQuery();
			manager.readTuples(rs);
		}

		return manager.getTuples().size() == 1;

	}

}
