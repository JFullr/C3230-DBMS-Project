package edu.westga.cs3230.healthcare_dbms.model.dal;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import edu.westga.cs3230.healthcare_dbms.io.database.DatabaseConnector;
import edu.westga.cs3230.healthcare_dbms.io.database.QueryResult;
import edu.westga.cs3230.healthcare_dbms.model.Appointment;
import edu.westga.cs3230.healthcare_dbms.model.AppointmentData;
import edu.westga.cs3230.healthcare_dbms.model.PatientData;
import edu.westga.cs3230.healthcare_dbms.sql.SqlAttribute;
import edu.westga.cs3230.healthcare_dbms.sql.SqlGetter;
import edu.westga.cs3230.healthcare_dbms.sql.SqlManager;
import edu.westga.cs3230.healthcare_dbms.sql.SqlSetter;
import edu.westga.cs3230.healthcare_dbms.sql.SqlTuple;

/**
 * DAL object for {@link Appointment} objects.
 *
 * @author Joseph Fuller and Andrew Steinborn
 */
public class AppointmentDAL {

	/** The connector. */
	private DatabaseConnector connector;
	
	/** The post dal. */
	private PostDAL postDal;
	
	/** The update dal. */
	private UpdateDAL updateDal;

	/**
	 * Instantiates a new appointment DAL.
	 *
	 * @param connector the connector
	 */
	public AppointmentDAL(DatabaseConnector connector) {
		this.connector = connector;
		this.postDal = new PostDAL(connector);
		this.updateDal = new UpdateDAL(connector);
	}

	/**
	 * Attempt add appointment.
	 *
	 * @param appointment the appointment
	 * @return the query result
	 * @throws SQLException the SQL exception
	 */
	public QueryResult attemptAddAppointment(AppointmentData appointment) throws SQLException {
		return this.connector.getInTransaction(() -> {
			QueryResult result = null;
			Integer addressId = null;
			try {
				ArrayList<BigDecimal> generated = this.postDal.getGeneratedIds(this.postDal.postTuple(appointment.getAppointment()));
				if (generated.size() == 0) {
					System.out.println("ADDRESS FAILED GENERATED CHECK");
					return null;
				}
				addressId = generated.get(0).intValue();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}

			result = this.getAppointmentById(addressId);

			return result;
		});
	}
	
	/**
	 * Gets the appointment by id.
	 *
	 * @param addressId the address id
	 * @return the appointment by id
	 * @throws SQLException the SQL exception
	 */
	public QueryResult getAppointmentById(int addressId) throws SQLException {
        String prepared = "select * from Appointment where appointment_id = ?";

        SqlManager manager = new SqlManager();
		Connection con = this.connector.getCurrentConnection();
        try (PreparedStatement stmt = con.prepareStatement(prepared)) {
            stmt.setInt(1, addressId);
            ResultSet rs = stmt.executeQuery();
            manager.readTuples(rs);
        }

        return new QueryResult(manager.getTuples());
    }
	
	/**
	 * Gets the appointments matching.
	 *
	 * @param patient the patient
	 * @return the appointments matching
	 * @throws SQLException the SQL exception
	 */
	public QueryResult getAppointmentsMatching(PatientData patient) throws SQLException {
		Appointment appt = new Appointment(null,null,null,null);
		appt.setPerson_id(patient.getPerson().getPerson_id());
		return getAppointmentsMatching(new AppointmentData(appt));
	}
	
	/**
	 * Gets the valid appointments matching.
	 *
	 * @param patient the patient
	 * @return the valid appointments matching
	 * @throws SQLException the SQL exception
	 */
	public QueryResult getValidAppointmentsMatching(PatientData patient) throws SQLException {
		Appointment appt = new Appointment(null,null,null,null);
		appt.setPerson_id(patient.getPerson().getPerson_id());
		Timestamp time = Timestamp.valueOf(LocalDateTime.now());
		appt.setDate_time(time);
		
		QueryResult appointments = getAppointmentsAfter(appt);
		
		return this.combineAssociateResults(appointments);
	}
	
	/**
	 * Gets the invalid appointments matching.
	 *
	 * @param patient the patient
	 * @return the invalid appointments matching
	 * @throws SQLException the SQL exception
	 */
	public QueryResult getInvalidAppointmentsMatching(PatientData patient) throws SQLException {
		Appointment appt = new Appointment(null,null,null,null);
		appt.setPerson_id(patient.getPerson().getPerson_id());
		Timestamp time = Timestamp.valueOf(LocalDateTime.now());
		appt.setDate_time(time);
		
		QueryResult appointments = getAppointmentsBefore(appt);
		
		return this.combineAssociateResults(appointments);
	}

	/**
	 * Gets the appointments matching.
	 *
	 * @param appointmentData the appointment data
	 * @return the appointments matching
	 * @throws SQLException the SQL exception
	 */
	public QueryResult getAppointmentsMatching(AppointmentData appointmentData) throws SQLException{
		
		QueryResult appointments = this.getAppointmentsMatching(appointmentData.getAppointment());
		
		return this.combineAssociateResults(appointments);
	}
	
	/**
	 * Gets the appointments matching.
	 *
	 * @param appt the appt
	 * @return the appointments matching
	 * @throws SQLException the SQL exception
	 */
	private QueryResult getAppointmentsMatching(Appointment appt) throws SQLException {
		
		SqlTuple tuple = SqlGetter.getFrom(appt);
		StringBuilder query = new StringBuilder("SELECT * FROM Appointment WHERE ");
		for (SqlAttribute attribute : tuple) {
			if (attribute.getValue() == null) {
				continue;
			}
			query.append(attribute.getAttribute()).append(" = ? AND ");
		}
		
		if(!query.toString().contains("?")) {
			return null;
		}
		query.setLength(query.lastIndexOf("?")+1);

		SqlManager manager = new SqlManager();
		Connection con = this.connector.getCurrentConnection();
		try (PreparedStatement stmt = con.prepareStatement(query.toString())) {
			int j = 1;
			for(SqlAttribute attr : tuple) {
				if (attr.getValue() == null) {
					continue;
				}
				stmt.setObject(j, attr.getValue());
				j++;
			}

			ResultSet rs = stmt.executeQuery();
			manager.readTuples(rs);
		}

		return new QueryResult(manager.getTuples());
	}
	
	/**
	 * Gets the appointments before.
	 *
	 * @param appt the appt
	 * @return the appointments before
	 * @throws SQLException the SQL exception
	 */
	private QueryResult getAppointmentsBefore(Appointment appt) throws SQLException {
		
		String query ="SELECT * FROM Appointment WHERE person_id = ? AND date_time < ?";

		SqlManager manager = new SqlManager();
		Connection con = this.connector.getCurrentConnection();
		try (PreparedStatement stmt = con.prepareStatement(query)) {
			stmt.setObject(1, appt.getPerson_id());
			stmt.setObject(2, appt.getDate_time());
			ResultSet rs = stmt.executeQuery();
			manager.readTuples(rs);
		}

		return new QueryResult(manager.getTuples());
	}
	
	/**
	 * Gets the appointments after.
	 *
	 * @param appt the appt
	 * @return the appointments after
	 * @throws SQLException the SQL exception
	 */
	private QueryResult getAppointmentsAfter(Appointment appt) throws SQLException {
		
		String query ="SELECT * FROM Appointment WHERE person_id = ? AND date_time > ?";

		SqlManager manager = new SqlManager();
		Connection con = this.connector.getCurrentConnection();
		try (PreparedStatement stmt = con.prepareStatement(query)) {
			stmt.setObject(1, appt.getPerson_id());
			stmt.setObject(2, appt.getDate_time());
			ResultSet rs = stmt.executeQuery();
			manager.readTuples(rs);
		}

		return new QueryResult(manager.getTuples());
	}
	
	/**
	 * Combine associate results.
	 *
	 * @param results the results
	 * @return the query result
	 */
	private QueryResult combineAssociateResults(QueryResult results) {
		QueryResult combined = null;
		for(QueryResult appt : results.getBatch()) {
			
			Appointment found = new Appointment(null, null,null,null);
			
			if(appt.getTuple() == null) {
				continue;
			}
			
			SqlSetter.fillWith(found, appt.getTuple());
			
			QueryResult midCombine = new QueryResult(appt.getTuple());
			midCombine.setAssociated(found);
			
			if(combined == null) {
				combined = midCombine;
			} else {
				combined.combine(midCombine);
			}
		}
		
		return combined;
	}

	/**
	 * Attempt update appointment.
	 *
	 * @param appointment the appointment
	 * @param newAppointment the new appointment
	 * @return the query result
	 * @throws SQLException the SQL exception
	 */
	public QueryResult attemptUpdateAppointment(Appointment appointment, Appointment newAppointment) throws SQLException {
		return this.updateDal.updateTuple(newAppointment, appointment);
	}
	
}
