package edu.westga.cs3230.healthcare_dbms.io.database;

import java.time.chrono.Chronology;
import java.util.LinkedList;
import java.util.List;

import edu.westga.cs3230.healthcare_dbms.model.AppointmentData;
import edu.westga.cs3230.healthcare_dbms.model.Login;
import edu.westga.cs3230.healthcare_dbms.model.PatientData;
import edu.westga.cs3230.healthcare_dbms.model.Person;

/**
 * Responsible for handling connections to the remote server and local database.
 */
public class HealthcareDatabase {
	
	private List<QueryResult> loadedQueries;
	private HealthcareDatabaseClient client;
	private String dbUrl;
	
	/**
	 * Instantiates a new database.
	 */
	public HealthcareDatabase(String dbUrl) {
		this.dbUrl = dbUrl;
		this.loadedQueries = new LinkedList<QueryResult>();
		this.client = new HealthcareDatabaseClient(this.dbUrl, this.loadedQueries);
	}
	
	public QueryResult getResults() {
		return this.client.getLastQueryResult();
	}
	
	/**
	 * Gets the queries
	 *
	 * @return the queries
	 */
	public List<QueryResult> getQueryResults(){
		return this.loadedQueries;
	}
	
	/**
	 * calls the specified query
	 *
	 * @param query the query to be executed
	 * 
	 * @return if successfully called
	 */
	public boolean callQuery(String query) {
		try {
			return this.client.callQuery(query);
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Logins to the database
	 *
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

	public QueryResult attemptAddPatient(PatientData patientData) {
		try {
			
			QueryResult result = this.client.attemptAddPatient(patientData);
			return result;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public QueryResult attemptSearchPatient(PatientData patient) {
		try {

			QueryResult result = this.client.attemptSearchPatient(patient);
			return result;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public QueryResult getPatientBySSN(PatientData patientData) {
		try {

			QueryResult result = this.client.getPatientBySSN(patientData);
			return result;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public QueryResult attemptUpdatePatient(PatientData patientData, PatientData existing) {
		try {

			QueryResult result = this.client.updatePatient(patientData, existing);
			return result;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public QueryResult attemptAddAppointment(AppointmentData appointmentData) {
		try {

			QueryResult result = this.client.attemptAddAppointment(appointmentData);
			return result;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public QueryResult getAppointmentBy(AppointmentData appointmentData) {
		try {

			QueryResult result = this.client.getAppointmentBy(appointmentData);
			return result;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}