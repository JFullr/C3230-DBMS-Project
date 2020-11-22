package edu.westga.cs3230.healthcare_dbms.io.database;

import java.math.BigDecimal;
import java.sql.Date;
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
	private LabTestOrderDAL labTestOrderDal;
	private UpdateDAL updateDal;
	private FinalDiagnosisDAL finalDiagnosisDal;
	private DiagnosisDAL diagnosisDal;
	private LabTestResultDAL labTestResultDal;
	private AdminDAL adminDal;
	
	private QueryResult lastResult;
	private DatabaseConnector connector;
	
	/**
	 * Instantiates a new healthcare database client.
	 *
	 * @param input/output the storage for queries
	 */
	public HealthcareDatabaseClient(String dbUrl, List<QueryResult> storageForReadQueries) {
		this.lastResult = null;
		this.connector = new DatabaseConnector(dbUrl);
		this.postDal = new PostDAL(this.connector);
		this.loginDal = new LoginDAL(this.connector);
		this.personDal = new PersonDAL(this.connector);
		this.userDal = new UserTypeDAL(this.connector);
		this.patientDal = new PatientDAL(this.connector);
		this.appointmentDal = new AppointmentDAL(this.connector);
		this.appointmentCheckupDal = new AppointmentCheckupDAL(this.connector);
		this.doctorDal = new DoctorDAL(this.connector);
		this.updateDal = new UpdateDAL(this.connector);
		this.finalDiagnosisDal = new FinalDiagnosisDAL(this.connector);
		this.labTestDal = new LabTestDAL(this.connector);
		this.labTestOrderDal = new LabTestOrderDAL(this.connector);
		this.diagnosisDal = new DiagnosisDAL(this.connector);
		this.labTestResultDal = new LabTestResultDAL(this.connector);
		this.adminDal = new AdminDAL(this.connector);
	}
	
	public QueryResult callAdminQuery(String rawSql) throws Exception {
		this.lastResult = this.adminDal.callQuery(rawSql);
		return this.lastResult;
	}

	public QueryResult callAdminDateQuery(Date start, Date end) throws Exception {
		this.lastResult = this.adminDal.searchByDates(start, end);
		return this.lastResult;
	}

	public QueryResult attemptLogin(Login login) throws SQLException {
		
		this.lastResult = this.loginDal.attemptLogin(login);
		this.lastResult.setAssociated(login);
		return this.lastResult;
	}
	
	public QueryResult attemptAdminLogin(Login login) throws SQLException {
		
		this.lastResult = this.loginDal.attemptAdminLogin(login);
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
		
		
		if(tups.size() > 0) {
			SqlTuple gen = new SqlTuple(new SqlAttribute(SqlTuple.SQL_GENERATED_ID, tups.get(0)));
			result = new QueryResult(gen);
		}else {
			result = new QueryResult((SqlTuple)null);
		}
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
	
	public QueryResult attemptGetFinalDiagnosisOf(Appointment appt) throws SQLException {
		this.lastResult = this.finalDiagnosisDal.getFinalDiagnosisOf(appt);
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

	public QueryResult attemptAddTestOrder(LabTestOrder order) throws SQLException {
		this.lastResult = this.labTestOrderDal.addLabTestOrder(order);
		return this.lastResult;
	}
	
	public QueryResult attemptGetTestOrdersOf(Appointment appointment) throws SQLException {
		this.lastResult = this.labTestOrderDal.getLabTestOrdersForAppointment(appointment.getAppointment_id());
		return this.lastResult;
	}
	
	public QueryResult attemptGetTestResultOf(LabTestOrder order) throws SQLException {
		this.lastResult = this.labTestResultDal.getLabTestOrderResultFor(order.getLab_test_order_id());
		return this.lastResult;
	}
	
	public QueryResult attemptGetTestResultsOf(Appointment appointment) throws SQLException {
		this.lastResult = this.labTestResultDal.getLabTestOrderResultsForAppointment(appointment.getAppointment_id());
		return this.lastResult;
	}

	public QueryResult attemptGetDiagnosisOf(Appointment appointment) throws SQLException {
		this.lastResult = this.diagnosisDal.getDiagnosisOf(appointment);
		return this.lastResult;
	}
}
