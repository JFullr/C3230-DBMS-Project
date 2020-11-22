package edu.westga.cs3230.healthcare_dbms.io.database;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.westga.cs3230.healthcare_dbms.model.Appointment;
import edu.westga.cs3230.healthcare_dbms.model.AppointmentCheckup;
import edu.westga.cs3230.healthcare_dbms.model.AppointmentData;
import edu.westga.cs3230.healthcare_dbms.model.LabTestOrder;
import edu.westga.cs3230.healthcare_dbms.model.Login;
import edu.westga.cs3230.healthcare_dbms.model.PatientData;
import edu.westga.cs3230.healthcare_dbms.model.Person;
import edu.westga.cs3230.healthcare_dbms.model.dal.AdminDAL;
import edu.westga.cs3230.healthcare_dbms.model.dal.AppointmentCheckupDAL;
import edu.westga.cs3230.healthcare_dbms.model.dal.AppointmentDAL;
import edu.westga.cs3230.healthcare_dbms.model.dal.DiagnosisDAL;
import edu.westga.cs3230.healthcare_dbms.model.dal.DoctorDAL;
import edu.westga.cs3230.healthcare_dbms.model.dal.FinalDiagnosisDAL;
import edu.westga.cs3230.healthcare_dbms.model.dal.LabTestDAL;
import edu.westga.cs3230.healthcare_dbms.model.dal.LabTestOrderDAL;
import edu.westga.cs3230.healthcare_dbms.model.dal.LabTestResultDAL;
import edu.westga.cs3230.healthcare_dbms.model.dal.LoginDAL;
import edu.westga.cs3230.healthcare_dbms.model.dal.PatientDAL;
import edu.westga.cs3230.healthcare_dbms.model.dal.PostDAL;
import edu.westga.cs3230.healthcare_dbms.model.dal.UpdateDAL;
import edu.westga.cs3230.healthcare_dbms.model.dal.UserTypeDAL;
import edu.westga.cs3230.healthcare_dbms.sql.SqlAttribute;
import edu.westga.cs3230.healthcare_dbms.sql.SqlTuple;

/**
 * Delegates all database calls to the per-object DALs.
 *
 * @author Joseph Fuller and Andrew Steinborn
 */
public class HealthcareDatabaseClient {
	
	/** The user dal. */
	private UserTypeDAL userDal;
	
	/** The post dal. */
	private PostDAL postDal;
	
	/** The login dal. */
	private LoginDAL loginDal;
	
	/** The patient dal. */
	private PatientDAL patientDal;
	
	/** The appointment dal. */
	private AppointmentDAL appointmentDal;
	
	/** The appointment checkup dal. */
	private AppointmentCheckupDAL appointmentCheckupDal;
	
	/** The doctor dal. */
	private DoctorDAL doctorDal;
	
	/** The lab test dal. */
	private LabTestDAL labTestDal;
	
	/** The lab test order dal. */
	private LabTestOrderDAL labTestOrderDal;
	
	/** The update dal. */
	private UpdateDAL updateDal;
	
	/** The final diagnosis dal. */
	private FinalDiagnosisDAL finalDiagnosisDal;
	
	/** The diagnosis dal. */
	private DiagnosisDAL diagnosisDal;
	
	/** The lab test result dal. */
	private LabTestResultDAL labTestResultDal;
	
	/** The admin dal. */
	private AdminDAL adminDal;
	
	/** The last result. */
	private QueryResult lastResult;
	
	/** The connector. */
	private DatabaseConnector connector;
	
	/**
	 * Instantiates a new healthcare database client.
	 *
	 * @param dbUrl the db url
	 */
	public HealthcareDatabaseClient(String dbUrl) {
		this.lastResult = null;
		this.connector = new DatabaseConnector(dbUrl);
		this.postDal = new PostDAL(this.connector);
		this.loginDal = new LoginDAL(this.connector);
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
	
	/**
	 * Call admin query.
	 *
	 * @param rawSql the raw sql
	 * @return the query result
	 * @throws Exception the exception
	 */
	public QueryResult callAdminQuery(String rawSql) throws Exception {
		this.lastResult = this.adminDal.callQuery(rawSql);
		return this.lastResult;
	}

	/**
	 * Call admin date query.
	 *
	 * @param start the start
	 * @param end the end
	 * @return the query result
	 * @throws Exception the exception
	 */
	public QueryResult callAdminDateQuery(Date start, Date end) throws Exception {
		this.lastResult = this.adminDal.searchByDates(start, end);
		return this.lastResult;
	}

	/**
	 * Attempt login.
	 *
	 * @param login the login
	 * @return the query result
	 * @throws SQLException the SQL exception
	 */
	public QueryResult attemptLogin(Login login) throws SQLException {
		
		this.lastResult = this.loginDal.attemptLogin(login);
		this.lastResult.setAssociated(login);
		return this.lastResult;
	}
	
	/**
	 * Attempt admin login.
	 *
	 * @param login the login
	 * @return the query result
	 * @throws SQLException the SQL exception
	 */
	public QueryResult attemptAdminLogin(Login login) throws SQLException {
		
		this.lastResult = this.loginDal.attemptAdminLogin(login);
		this.lastResult.setAssociated(login);
		return this.lastResult;
	}
	
	/**
	 * Gets the last query result.
	 *
	 * @return the last query result
	 */
	public QueryResult getLastQueryResult() {
		return this.lastResult;
	}
	
	/**
	 * Attempt post tuple.
	 *
	 * @param tuple the tuple
	 * @return the query result
	 * @throws SQLException the SQL exception
	 */
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
	
	/**
	 * Attempt update tuple.
	 *
	 * @param newTupleData the new tuple data
	 * @param oldTupleData the old tuple data
	 * @return the query result
	 * @throws SQLException the SQL exception
	 */
	public QueryResult attemptUpdateTuple(Object newTupleData, Object oldTupleData) throws SQLException {
		
		this.lastResult = this.updateDal.updateTuple(newTupleData, oldTupleData);
		return this.lastResult;
	}

	/**
	 * Attempt add patient.
	 *
	 * @param patientData the patient data
	 * @return the query result
	 * @throws SQLException the SQL exception
	 */
	public QueryResult attemptAddPatient(PatientData patientData) throws SQLException {
		
		this.lastResult = this.patientDal.attemptAddPatient(patientData);
		this.lastResult.setAssociated(patientData);
		return this.lastResult;
	}

	/**
	 * Gets the user type.
	 *
	 * @param patient the patient
	 * @return the user type
	 * @throws SQLException the SQL exception
	 */
	public String getUserType(Person patient) throws SQLException {
		
		return this.userDal.getUserType(patient);
	}

	/**
	 * Attempt search patient.
	 *
	 * @param patient the patient
	 * @return the query result
	 * @throws SQLException the SQL exception
	 */
	public QueryResult attemptSearchPatient(PatientData patient) throws SQLException {
		this.lastResult = this.patientDal.getPersonMatching(patient);
		return this.lastResult;
	}

	/**
	 * Gets the patient by SSN.
	 *
	 * @param patientData the patient data
	 * @return the patient by SSN
	 * @throws SQLException the SQL exception
	 */
	public QueryResult getPatientBySSN(PatientData patientData) throws SQLException {
		this.lastResult = this.patientDal.getPatientBySSN(patientData);
		return this.lastResult;
	}

	/**
	 * Update patient.
	 *
	 * @param updateData the update data
	 * @param existingData the existing data
	 * @return the query result
	 * @throws SQLException the SQL exception
	 */
	public QueryResult updatePatient(PatientData updateData, PatientData existingData) throws SQLException {
		this.lastResult = this.patientDal.attemptUpdatePatient(updateData, existingData);
		return this.lastResult;
	}

	/**
	 * Attempt add appointment.
	 *
	 * @param appointmentData the appointment data
	 * @return the query result
	 * @throws SQLException the SQL exception
	 */
	public QueryResult attemptAddAppointment(AppointmentData appointmentData) throws SQLException {
		this.lastResult = this.appointmentDal.attemptAddAppointment(appointmentData);
		return this.lastResult;
	}

	/**
	 * Gets the appointment by.
	 *
	 * @param appointmentData the appointment data
	 * @return the appointment by
	 * @throws SQLException the SQL exception
	 */
	public QueryResult getAppointmentBy(AppointmentData appointmentData) throws SQLException {
		this.lastResult = this.appointmentDal.getAppointmentsMatching(appointmentData);
		return this.lastResult;
	}

	/**
	 * Gets the appointments matching.
	 *
	 * @param patient the patient
	 * @return the appointments matching
	 * @throws SQLException the SQL exception
	 */
	public QueryResult getAppointmentsMatching(PatientData patient) throws SQLException {
		this.lastResult = this.appointmentDal.getAppointmentsMatching(patient);
		return this.lastResult;
	}
	
	/**
	 * Gets the valid appointments matching.
	 *
	 * @param patient the patient
	 * @return the valid appointments matching
	 * @throws SQLException the SQL exception
	 */
	public QueryResult getValidAppointmentsMatching(PatientData patient) throws SQLException {
		this.lastResult = this.appointmentDal.getValidAppointmentsMatching(patient);
		return this.lastResult;
	}
	
	/**
	 * Gets the invalid appointments matching.
	 *
	 * @param patient the patient
	 * @return the invalid appointments matching
	 * @throws SQLException the SQL exception
	 */
	public QueryResult getInvalidAppointmentsMatching(PatientData patient) throws SQLException {
		this.lastResult = this.appointmentDal.getInvalidAppointmentsMatching(patient);
		return this.lastResult;
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
		this.lastResult = this.appointmentDal.attemptUpdateAppointment(appointment, newAppointment);
		return this.lastResult;
	}

	/**
	 * Gets the appointment checkup for appointment.
	 *
	 * @param appointment the appointment
	 * @return the appointment checkup for appointment
	 * @throws SQLException the SQL exception
	 */
	public QueryResult getAppointmentCheckupForAppointment(Appointment appointment) throws SQLException {
		this.lastResult = this.appointmentCheckupDal.getAppointmentCheckupForAppointment(appointment);
		return this.lastResult;
	}

	/**
	 * Attempt add appointment checkup.
	 *
	 * @param appointmentData the appointment data
	 * @return the query result
	 * @throws SQLException the SQL exception
	 */
	public QueryResult attemptAddAppointmentCheckup(AppointmentCheckup appointmentData) throws SQLException {
		this.lastResult = this.appointmentCheckupDal.attemptAddAppointmentCheckup(appointmentData);
		return this.lastResult;
	}
	
	/**
	 * Attempt get final diagnosis of.
	 *
	 * @param appt the appt
	 * @return the query result
	 * @throws SQLException the SQL exception
	 */
	public QueryResult attemptGetFinalDiagnosisOf(Appointment appt) throws SQLException {
		this.lastResult = this.finalDiagnosisDal.getFinalDiagnosisOf(appt);
		return this.lastResult;
	}
	
	/**
	 * Attempt get doctors.
	 *
	 * @return the query result
	 * @throws SQLException the SQL exception
	 */
	public QueryResult attemptGetDoctors() throws SQLException {
		this.lastResult = this.doctorDal.getDoctors();
		return this.lastResult;
	}
	
	/**
	 * Attempt get lab tests.
	 *
	 * @return the query result
	 * @throws SQLException the SQL exception
	 */
	public QueryResult attemptGetLabTests() throws SQLException {
		this.lastResult = this.labTestDal.getLabTests();
		return this.lastResult;
	}

	/**
	 * Attempt add test order.
	 *
	 * @param order the order
	 * @return the query result
	 * @throws SQLException the SQL exception
	 */
	public QueryResult attemptAddTestOrder(LabTestOrder order) throws SQLException {
		this.lastResult = this.labTestOrderDal.addLabTestOrder(order);
		return this.lastResult;
	}
	
	/**
	 * Attempt get test orders of.
	 *
	 * @param appointment the appointment
	 * @return the query result
	 * @throws SQLException the SQL exception
	 */
	public QueryResult attemptGetTestOrdersOf(Appointment appointment) throws SQLException {
		this.lastResult = this.labTestOrderDal.getLabTestOrdersForAppointment(appointment.getAppointment_id());
		return this.lastResult;
	}
	
	/**
	 * Attempt get test result of.
	 *
	 * @param order the order
	 * @return the query result
	 * @throws SQLException the SQL exception
	 */
	public QueryResult attemptGetTestResultOf(LabTestOrder order) throws SQLException {
		this.lastResult = this.labTestResultDal.getLabTestOrderResultFor(order.getLab_test_order_id());
		return this.lastResult;
	}
	
	/**
	 * Attempt get test results of.
	 *
	 * @param appointment the appointment
	 * @return the query result
	 * @throws SQLException the SQL exception
	 */
	public QueryResult attemptGetTestResultsOf(Appointment appointment) throws SQLException {
		this.lastResult = this.labTestResultDal.getLabTestOrderResultsForAppointment(appointment.getAppointment_id());
		return this.lastResult;
	}

	/**
	 * Attempt get diagnosis of.
	 *
	 * @param appointment the appointment
	 * @return the query result
	 * @throws SQLException the SQL exception
	 */
	public QueryResult attemptGetDiagnosisOf(Appointment appointment) throws SQLException {
		this.lastResult = this.diagnosisDal.getDiagnosisOf(appointment);
		return this.lastResult;
	}
}
