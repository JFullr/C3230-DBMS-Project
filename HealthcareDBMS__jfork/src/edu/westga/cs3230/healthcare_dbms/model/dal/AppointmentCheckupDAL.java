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

// TODO: Auto-generated Javadoc
/**
 * The Class AppointmentCheckupDAL.
 *
 * @author Andrew Steinborn
 */
public class AppointmentCheckupDAL {
	
	/** The connector. */
	private DatabaseConnector connector;
	
	/** The post dal. */
	private PostDAL postDal;

	/**
	 * Instantiates a new appointment checkup DAL.
	 *
	 * @param connector the connector
	 */
	public AppointmentCheckupDAL(DatabaseConnector connector) {
		this.connector = connector;
		this.postDal = new PostDAL(connector);
	}
	
	/**
	 * Attempt add appointment checkup.
	 *
	 * @param checkup the checkup
	 * @return the query result
	 * @throws SQLException the SQL exception
	 */
	public QueryResult attemptAddAppointmentCheckup(AppointmentCheckup checkup) throws SQLException {
		return this.connector.getInTransaction(() -> {
			this.postDal.postTuple(checkup);
			return this.getAppointmentCheckupForAppointment(checkup.getAppointment_id());
		});
	}
	
	/**
	 * Gets the appointment checkup for appointment.
	 *
	 * @param appointment the appointment
	 * @return the appointment checkup for appointment
	 * @throws SQLException the SQL exception
	 */
	public QueryResult getAppointmentCheckupForAppointment(Appointment appointment) throws SQLException {
		return getAppointmentCheckupForAppointment(appointment.getAppointment_id());
	}

	/**
	 * Gets the appointment checkup for appointment.
	 *
	 * @param appointmentId the appointment id
	 * @return the appointment checkup for appointment
	 * @throws SQLException the SQL exception
	 */
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
