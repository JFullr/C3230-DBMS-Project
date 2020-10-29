package edu.westga.cs3230.healthcare_dbms.io.database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.westga.cs3230.healthcare_dbms.model.AppointmentData;
import edu.westga.cs3230.healthcare_dbms.model.Login;
import edu.westga.cs3230.healthcare_dbms.model.PatientData;
import edu.westga.cs3230.healthcare_dbms.model.Person;
import edu.westga.cs3230.healthcare_dbms.model.dal.AppointmentDAL;
import edu.westga.cs3230.healthcare_dbms.model.dal.LoginDAL;
import edu.westga.cs3230.healthcare_dbms.model.dal.PatientDAL;
import edu.westga.cs3230.healthcare_dbms.model.dal.PersonDAL;
import edu.westga.cs3230.healthcare_dbms.model.dal.PostDAL;
import edu.westga.cs3230.healthcare_dbms.model.dal.UserTypeDAL;
import edu.westga.cs3230.healthcare_dbms.sql.SqlAttribute;

/**
 * Description
 * 
 */
public class HealthcareDatabaseClient {
	
	private UserTypeDAL userDal;
	private PostDAL postDal;
	private LoginDAL loginDal;
	private PersonDAL personDal;
	private PatientDAL patientDal;
	private AppointmentDAL appointmentDal;
	
	private QueryResult lastResult;
	private String dbUrl;
	
	/**
	 * Instantiates a new healthcare database client.
	 *
	 * @param input/output the storage for queries
	 */
	public HealthcareDatabaseClient(String dbUrl, List<QueryResult> storageForReadQueries) {
		this.lastResult = null;
		this.dbUrl = dbUrl;
		this.postDal = new PostDAL(dbUrl);
		this.loginDal = new LoginDAL(dbUrl);
		this.personDal = new PersonDAL(dbUrl);
		this.userDal = new UserTypeDAL(dbUrl);
		this.patientDal = new PatientDAL(dbUrl);
		this.appointmentDal = new AppointmentDAL(dbUrl);
	}
	
	public boolean callQuery(String query) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	public ArrayList<HashMap<String, SqlAttribute>> getQueryTuples() {
		// TODO Auto-generated method stub
		return null;
	}

	public QueryResult attemptLogin(Login login) throws SQLException {
		
		this.lastResult = this.loginDal.attemptLogin(login);
		this.lastResult.setAssociated(login);
		return this.lastResult;
	}
	
	public QueryResult getLastQueryResult() {
		return this.lastResult;
	}

	public QueryResult attemptAddPatient(PatientData patientData) throws SQLException {
		
		this.lastResult = this.patientDal.attemptAddPatient(patientData);
		//TODO remove due to null error
		this.lastResult.setAssociated(patientData);
		return this.lastResult;
	}

	public String getUserType(Person patient) throws SQLException {
		
		return this.userDal.getUserType(patient);
	}

	public QueryResult attemptSearchPatient(PatientData patient) throws SQLException {
		this.lastResult = this.patientDal.getPersonMatching(patient);
		return this.lastResult;
	}

	public QueryResult getPatientBySSN(PatientData patientData) throws SQLException {
		this.lastResult = this.patientDal.getPatientBySSN(patientData);
		return this.lastResult;
	}

	public QueryResult updatePatient(PatientData updateData, PatientData existingData) throws SQLException {
		this.lastResult = this.patientDal.attemptUpdatePatient(updateData, existingData);
		return this.lastResult;
	}

	public QueryResult attemptAddAppointment(AppointmentData appointmentData) throws SQLException {
		this.lastResult = this.appointmentDal.attemptAddAppointment(appointmentData);
		//this.lastResult.setAssociated(appointmentData);
		return this.lastResult;
	}

	public QueryResult getAppointmentBy(AppointmentData appointmentData) throws SQLException {
		this.lastResult = this.appointmentDal.getAppointmentsMatching(appointmentData);
		return this.lastResult;
	}

	public QueryResult getAppointmentsMatching(PatientData patient) throws SQLException {
		this.lastResult = this.appointmentDal.getAppointmentsMatching(patient);
		return this.lastResult;
	}

}
