package edu.westga.cs3230.healthcare_dbms.model.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.westga.cs3230.healthcare_dbms.io.database.QueryResult;
import edu.westga.cs3230.healthcare_dbms.model.Appointment;
import edu.westga.cs3230.healthcare_dbms.model.AppointmentCheckup;
import edu.westga.cs3230.healthcare_dbms.sql.SqlManager;

public class AppointmentCheckupDAL {
	private String dbUrl;
	private PostDAL postDal;
	private UpdateDAL updateDal;

	public AppointmentCheckupDAL(String dbUrl) {
		this.dbUrl = dbUrl;
		this.postDal = new PostDAL(dbUrl);
		this.updateDal = new UpdateDAL(dbUrl);
	}
	
	public QueryResult attemptAddAppointmentCheckup(AppointmentCheckup checkup) throws SQLException {
		try {
			this.postDal.postTuple(checkup);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}

		return this.getAppointmentCheckupForAppointment(checkup.getAppointment_id());
	}
	
	public QueryResult getAppointmentCheckupForAppointment(Appointment appointment) throws SQLException {
		return getAppointmentCheckupForAppointment(appointment.getAppointment_id());
	}

	public QueryResult getAppointmentCheckupForAppointment(int appointmentId) throws SQLException {
		String prepared = "select * "
				+ "from AppointmentCheckup "
				+ "where appointment_id = ? "
				+ "LIMIT 1";

		SqlManager manager = new SqlManager();
		try (Connection con = DriverManager.getConnection(this.dbUrl);
			 PreparedStatement stmt = con.prepareStatement(prepared);
		) {
			stmt.setObject(1, appointmentId);
			//System.out.println(stmt);
			ResultSet rs = stmt.executeQuery();
			manager.readTuples(rs);
		}

		return new QueryResult(manager.getTuples());
	}
}
