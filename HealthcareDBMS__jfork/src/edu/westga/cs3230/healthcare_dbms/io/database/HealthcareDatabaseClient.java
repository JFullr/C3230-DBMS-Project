package edu.westga.cs3230.healthcare_dbms.io.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.westga.cs3230.healthcare_dbms.model.Login;
import edu.westga.cs3230.healthcare_dbms.model.PatientData;
import edu.westga.cs3230.healthcare_dbms.model.Person;
import edu.westga.cs3230.healthcare_dbms.model.dal.*;
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

	private QueryResult lastResult;
	private ConnectionDAL connectionDal;
	
	/**
	 * Instantiates a new healthcare database client.
	 *
	 * @param input/output the storage for queries
	 */
	public HealthcareDatabaseClient(String dbUrl, List<QueryResult> storageForReadQueries) {
		this.lastResult = null;
		this.connectionDal = new ConnectionDAL(dbUrl);
		this.postDal = new PostDAL(connectionDal);
		this.loginDal = new LoginDAL(connectionDal);
		this.personDal = new PersonDAL(connectionDal);
		this.userDal = new UserTypeDAL(connectionDal);
		this.patientDal = new PatientDAL(connectionDal);
	}

	public void runInTransaction(Runnable runnable) throws SQLException {
		Connection connection = this.connectionDal.getConnection();
		try {
			this.connectionDal.setInTransaction(true);

			connection.setAutoCommit(false);
			runnable.run();
		} catch (Exception e) {
			connection.rollback();
			throw e;
		} finally {
			connection.commit();
			connection.setAutoCommit(true);
			this.connectionDal.setInTransaction(false);
		}
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
		return this.lastResult;
	}
	
	public QueryResult getLastQueryResult() {
		return this.lastResult;
	}

	public QueryResult attemptAddPatient(PatientData patientData) throws SQLException {
		
		this.lastResult = this.patientDal.attemptAddPatient(patientData);
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
		this.lastResult = this.patientDal.getPersonBySSN(patientData);
		return this.lastResult;
	}

	public QueryResult updatePatient(PatientData updateData, PatientData existingData) throws SQLException {
		//TODO fix
		this.lastResult = this.patientDal.attemptUpdatePatient(updateData, existingData);
		return this.lastResult;
	}
}
