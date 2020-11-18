package edu.westga.cs3230.healthcare_dbms.model.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.westga.cs3230.healthcare_dbms.io.database.DatabaseConnector;
import edu.westga.cs3230.healthcare_dbms.io.database.QueryResult;
import edu.westga.cs3230.healthcare_dbms.model.Appointment;
import edu.westga.cs3230.healthcare_dbms.sql.SqlManager;

public class DiagnosisDAL {
	
	private DatabaseConnector connector;

	public DiagnosisDAL(DatabaseConnector connector) {
		this.connector = connector;
	}
	
	public QueryResult getDiagnosisOf(Appointment appt) throws SQLException {
		
		if(appt == null || appt.getAppointment_id() == null) {
			return null;
		}
		
		Integer id = appt.getAppointment_id();
		
		String query = "select * "
						+ "from Diagnosis "
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
