package edu.westga.cs3230.healthcare_dbms.model.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.westga.cs3230.healthcare_dbms.io.database.DatabaseConnector;
import edu.westga.cs3230.healthcare_dbms.io.database.QueryResult;
import edu.westga.cs3230.healthcare_dbms.model.Appointment;
import edu.westga.cs3230.healthcare_dbms.model.AppointmentCheckup;
import edu.westga.cs3230.healthcare_dbms.sql.SqlManager;

public class AppointmentCheckupDAL {
	private DatabaseConnector connector;
	private PostDAL postDal;
	private UpdateDAL updateDal;

	public AppointmentCheckupDAL(DatabaseConnector connector) {
		this.connector = connector;
		this.postDal = new PostDAL(connector);
		this.updateDal = new UpdateDAL(connector);
	}
	
	public QueryResult attemptAddAppointmentCheckup(AppointmentCheckup checkup) throws SQLException {
		return this.connector.getInTransaction(() -> {
			this.postDal.postTuple(checkup);
			return this.getAppointmentCheckupForAppointment(checkup.getAppointment_id());
		});
	}
	
	public QueryResult getAppointmentCheckupForAppointment(Appointment appointment) throws SQLException {
		return getAppointmentCheckupForAppointment(appointment.getAppointment_id());
	}

	public QueryResult getAppointmentCheckupForAppointment(int appointmentId) throws SQLException {
		String prepared = "select * "
				+ "from AppointmentCheckup "
				+ "where appointment_id = ? "
				+ "LIMIT 1";

		Connection con = this.connector.getCurrentConnection();
		SqlManager manager = new SqlManager();
		try (PreparedStatement stmt = con.prepareStatement(prepared)) {
			stmt.setObject(1, appointmentId);
			ResultSet rs = stmt.executeQuery();
			manager.readTuples(rs);
		}

		return new QueryResult(manager.getTuples());
	}
}
