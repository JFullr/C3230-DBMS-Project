package edu.westga.cs3230.healthcare_dbms.model.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.westga.cs3230.healthcare_dbms.io.database.DatabaseConnector;
import edu.westga.cs3230.healthcare_dbms.io.database.QueryResult;
import edu.westga.cs3230.healthcare_dbms.model.Appointment;
import edu.westga.cs3230.healthcare_dbms.sql.SqlManager;

// TODO: Auto-generated Javadoc
/**
 * The Class FinalDiagnosisDAL.
 */
public class FinalDiagnosisDAL {
	
	/** The connector. */
	private DatabaseConnector connector;

	/**
	 * Instantiates a new final diagnosis DAL.
	 *
	 * @param connector the connector
	 */
	public FinalDiagnosisDAL(DatabaseConnector connector) {
		this.connector = connector;
	}
	
	/**
	 * Gets the final diagnosis of.
	 *
	 * @param appt the appt
	 * @return the final diagnosis of
	 * @throws SQLException the SQL exception
	 */
	public QueryResult getFinalDiagnosisOf(Appointment appt) throws SQLException {
		
		if(appt == null || appt.getAppointment_id() == null) {
			return null;
		}
		
		Integer id = appt.getAppointment_id();
		
		String query = "select * "
						+ "from FinalDiagnosis "
						+ "where appointment_id = ?";
		
		SqlManager manager = new SqlManager();
		Connection con = this.connector.getCurrentConnection();
		try (PreparedStatement stmt = con.prepareStatement(query)) {
			stmt.setObject(1, id);
			ResultSet rs = stmt.executeQuery();
			manager.readTuples(rs);
		}
		
		return new QueryResult(manager.getTuples());
	}

}
