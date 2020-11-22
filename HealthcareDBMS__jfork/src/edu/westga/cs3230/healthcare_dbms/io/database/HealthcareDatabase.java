package edu.westga.cs3230.healthcare_dbms.io.database;

import java.sql.Date;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import edu.westga.cs3230.healthcare_dbms.model.Appointment;
import edu.westga.cs3230.healthcare_dbms.model.AppointmentCheckup;
import edu.westga.cs3230.healthcare_dbms.model.AppointmentData;
import edu.westga.cs3230.healthcare_dbms.model.LabTestOrder;
import edu.westga.cs3230.healthcare_dbms.model.Login;
import edu.westga.cs3230.healthcare_dbms.model.PatientData;

/**
 * Responsible for handling connections to the remote server and local database.
 */
public class HealthcareDatabase {

	/** The loaded queries. */
	private List<QueryResult> loadedQueries;
	
	/** The client. */
	private HealthcareDatabaseClient client;

	/** The db url. */
	private String dbUrl;
	
	/**
	 * Instantiates a new database.
	 *
	 * @param dbUrl the db url
	 */
	public HealthcareDatabase(String dbUrl) {
		this.dbUrl = dbUrl;
		this.loadedQueries = new LinkedList<QueryResult>();
		this.client = new HealthcareDatabaseClient(this.dbUrl);
	}
	
	/**
	 * Gets the results.
	 *
	 * @return the results
	 */
	public QueryResult getResults() {
		return this.client.getLastQueryResult();
	}

	/**
	 * Gets the queries.
	 *
	 * @return the queries
	 */
	public List<QueryResult> getQueryResults(){
		return this.loadedQueries;
	}

	/**
	 * calls the specified query by the admin.
	 *
	 * @param rawSql the raw sql
	 * @return != null if successfully called
	 * @throws Exception the exception
	 */
	public QueryResult callAdminQuery(String rawSql)throws Exception {
		QueryResult results = this.client.callAdminQuery(rawSql);
		return results;
	}
	
	/**
	 * Call admin date query.
	 *
	 * @param start the start
	 * @param end the end
	 * @return the query result
	 */
	public QueryResult callAdminDateQuery(Date start, Date end) {
		try {
			QueryResult result = this.client.callAdminDateQuery(start, end);
			return result;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Logins to the database.
	 *
	 * @param login the login
	 * @return the result of the login
	 */
	public QueryResult attemptLogin(Login login) {
		try {
			QueryResult result = this.client.attemptLogin(login);
			return result;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Attempt admin login.
	 *
	 * @param login the login
	 * @return the query result
	 */
	public QueryResult attemptAdminLogin(Login login) {
		try {
			QueryResult result = this.client.attemptAdminLogin(login);
			return result;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Attempt post tuple.
	 *
	 * @param tuple the tuple
	 * @return the query result
	 */
	public QueryResult attemptPostTuple(Object tuple) {
		try {
			QueryResult result = this.client.attemptPostTuple(tuple);
			return result;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Attempt update tuple.
	 *
	 * @param newTupleData the new tuple data
	 * @param oldTupleData the old tuple data
	 * @return the query result
	 */
	public QueryResult attemptUpdateTuple(Object newTupleData, Object oldTupleData) {
		try {
			QueryResult result = this.client.attemptUpdateTuple(newTupleData, oldTupleData);
			return result;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Attempt add patient.
	 *
	 * @param patientData the patient data
	 * @return the query result
	 */
	public QueryResult attemptAddPatient(PatientData patientData) {
		try {
			
			QueryResult result = this.client.attemptAddPatient(patientData);
			return result;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Attempt search patient.
	 *
	 * @param patient the patient
	 * @return the query result
	 */
	public QueryResult attemptSearchPatient(PatientData patient) {
		try {

			QueryResult result = this.client.attemptSearchPatient(patient);
			return result;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets the patient by SSN.
	 *
	 * @param patientData the patient data
	 * @return the patient by SSN
	 */
	public QueryResult getPatientBySSN(PatientData patientData) {
		try {

			QueryResult result = this.client.getPatientBySSN(patientData);
			return result;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Attempt update patient.
	 *
	 * @param patientData the patient data
	 * @param existing the existing
	 * @return the query result
	 */
	public QueryResult attemptUpdatePatient(PatientData patientData, PatientData existing) {
		try {

			QueryResult result = this.client.updatePatient(patientData, existing);
			return result;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Attempt add appointment.
	 *
	 * @param appointmentData the appointment data
	 * @return the query result
	 */
	public QueryResult attemptAddAppointment(AppointmentData appointmentData) {
		try {

			QueryResult result = this.client.attemptAddAppointment(appointmentData);
			return result;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets the appointment by.
	 *
	 * @param appointmentData the appointment data
	 * @return the appointment by
	 */
	public QueryResult getAppointmentBy(AppointmentData appointmentData) {
		try {

			QueryResult result = this.client.getAppointmentBy(appointmentData);
			return result;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Gets the appointments by patient.
	 *
	 * @param patient the patient
	 * @return the appointments by patient
	 */
	public QueryResult getAppointmentsByPatient(PatientData patient) {
		try {

			QueryResult result = this.client.getAppointmentsMatching(patient);
			return result;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Gets the valid appointments by patient.
	 *
	 * @param patient the patient
	 * @return the valid appointments by patient
	 */
	public QueryResult getValidAppointmentsByPatient(PatientData patient) {
		try {

			QueryResult result = this.client.getValidAppointmentsMatching(patient);
			return result;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Gets the invalid appointments by patient.
	 *
	 * @param patient the patient
	 * @return the invalid appointments by patient
	 */
	public QueryResult getInvalidAppointmentsByPatient(PatientData patient) {
		try {

			QueryResult result = this.client.getInvalidAppointmentsMatching(patient);
			return result;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
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
		try {

			QueryResult result = this.client.attemptUpdateAppointment(appointment, newAppointment);
			return result;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets the appointment checkup for appointment.
	 *
	 * @param appointment the appointment
	 * @return the appointment checkup for appointment
	 */
	public QueryResult getAppointmentCheckupForAppointment(Appointment appointment) {
		try {
			QueryResult result = this.client.getAppointmentCheckupForAppointment(appointment);
			return result;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Attempt add appointment checkup.
	 *
	 * @param checkup the checkup
	 * @return the query result
	 */
	public QueryResult attemptAddAppointmentCheckup(AppointmentCheckup checkup) {
		try {
			QueryResult result = this.client.attemptAddAppointmentCheckup(checkup);
			return result;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Attempt get doctors.
	 *
	 * @return the query result
	 */
	public QueryResult attemptGetDoctors() {
		try {
			QueryResult result = this.client.attemptGetDoctors();
			return result;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Attempt get lab tests.
	 *
	 * @return the query result
	 */
	public QueryResult attemptGetLabTests() {
		try {
			QueryResult result = this.client.attemptGetLabTests();
			return result;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Attempt get test orders of.
	 *
	 * @param appointment the appointment
	 * @return the query result
	 */
	public QueryResult attemptGetTestOrdersOf(Appointment appointment) {
		try {
			QueryResult result = this.client.attemptGetTestOrdersOf(appointment);
			return result;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Attempt get test result of.
	 *
	 * @param order the order
	 * @return the query result
	 */
	public QueryResult attemptGetTestResultOf(LabTestOrder order) {
		try {
			QueryResult result = this.client.attemptGetTestResultOf(order);
			return result;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Attempt get test results of.
	 *
	 * @param appointment the appointment
	 * @return the query result
	 */
	public QueryResult attemptGetTestResultsOf(Appointment appointment) {
		try {
			QueryResult result = this.client.attemptGetTestResultsOf(appointment);
			return result;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Attempt get final diagnois.
	 *
	 * @param appt the appt
	 * @return the query result
	 */
	public QueryResult attemptGetFinalDiagnois(Appointment appt) {
		try {
			QueryResult result = this.client.attemptGetFinalDiagnosisOf(appt);
			return result;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Attempt add test order.
	 *
	 * @param order the order
	 * @return the query result
	 */
	public QueryResult attemptAddTestOrder(LabTestOrder order) {
		try {
			QueryResult result = this.client.attemptAddTestOrder(order);
			return result;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Attempt get diagnois.
	 *
	 * @param appointment the appointment
	 * @return the query result
	 */
	public QueryResult attemptGetDiagnois(Appointment appointment) {
		try {
			QueryResult result = this.client.attemptGetDiagnosisOf(appointment);
			return result;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}