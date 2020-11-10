package edu.westga.cs3230.healthcare_dbms.io.database;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.westga.cs3230.healthcare_dbms.model.*;
import edu.westga.cs3230.healthcare_dbms.model.dal.*;
import edu.westga.cs3230.healthcare_dbms.sql.SqlAttribute;
import edu.westga.cs3230.healthcare_dbms.sql.SqlTuple;

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
	private AppointmentCheckupDAL appointmentCheckupDal;
	private DoctorDAL doctorDal;
	private LabTestDAL labTestDal;
	private UpdateDAL updateDal;
	
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
		this.appointmentCheckupDal = new AppointmentCheckupDAL(dbUrl);
		this.doctorDal = new DoctorDAL(dbUrl);
		this.updateDal = new UpdateDAL(dbUrl);
		
		this.labTestDal = new LabTestDAL(dbUrl);
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
	
	public QueryResult attemptPostTuple(Object tuple) throws SQLException {
		
		QueryResult result = null; //this.postDal.getGeneratedIds();
		
		ArrayList<BigDecimal> tups = this.postDal.getGeneratedIds(this.postDal.postTuple(tuple));
		if(tups == null) {
			return null;
		}
		
		SqlTuple gen = new SqlTuple(new SqlAttribute(SqlTuple.SQL_GENERATED_ID, tups.get(0)));
		result = new QueryResult(gen);
		this.lastResult = result;
		return result;
	}
	
	public QueryResult attemptUpdateTuple(Object newTupleData, Object oldTupleData) throws SQLException {
		
		this.lastResult = this.updateDal.updateTuple(newTupleData, oldTupleData);
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
	
	public QueryResult getValidAppointmentsMatching(PatientData patient) throws SQLException {
		this.lastResult = this.appointmentDal.getValidAppointmentsMatching(patient);
		return this.lastResult;
	}
	
	public QueryResult getInvalidAppointmentsMatching(PatientData patient) throws SQLException {
		this.lastResult = this.appointmentDal.getInvalidAppointmentsMatching(patient);
		return this.lastResult;
	}

	public QueryResult attemptUpdateAppointment(Appointment appointment, Appointment newAppointment) throws SQLException {
		this.lastResult = this.appointmentDal.attemptUpdateAppointment(appointment, newAppointment);
		//this.lastResult.setAssociated(appointmentData);
		return this.lastResult;
	}

	public QueryResult getAppointmentCheckupForAppointment(Appointment appointment) throws SQLException {
		this.lastResult = this.appointmentCheckupDal.getAppointmentCheckupForAppointment(appointment);
		return this.lastResult;
	}

	public QueryResult attemptAddAppointmentCheckup(AppointmentCheckup appointmentData) throws SQLException {
		this.lastResult = this.appointmentCheckupDal.attemptAddAppointmentCheckup(appointmentData);
		//this.lastResult.setAssociated(appointmentData);
		return this.lastResult;
	}
	
	public QueryResult attemptGetDoctors() throws SQLException {
		this.lastResult = this.doctorDal.getDoctors();
		return this.lastResult;
	}
	
	public QueryResult attemptGetLabTests() throws SQLException {
		this.lastResult = this.labTestDal.getLabTests();
		return this.lastResult;
	}
}
