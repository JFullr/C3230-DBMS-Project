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
	
	private QueryResult lastResult;
	private String dbUrl;
	private Connection connection;

	private boolean inTransaction;
	
	/**
	 * Instantiates a new healthcare database client.
	 *
	 * @param input/output the storage for queries
	 */
	public HealthcareDatabaseClient(String dbUrl, List<QueryResult> storageForReadQueries) {
		this.lastResult = null;
		this.dbUrl = dbUrl;
		this.postDal = new PostDAL(this);
		this.loginDal = new LoginDAL(this);
		this.personDal = new PersonDAL(this);
		this.userDal = new UserTypeDAL(this);
		this.patientDal = new PatientDAL(this);
	}

	public Connection getConnection() throws SQLException {
		if (inTransaction && connection != null) {
			return connection;
		}
		boolean valid = connection != null;
		if (valid) {
			try {
				valid = connection.isValid(1000);
			} catch (SQLException e) {
				valid = false;
			}
		}
		if (!valid) {
			connection = DriverManager.getConnection(dbUrl);
		}
		return connection;
	}

	public void runInTransaction(Runnable runnable) throws SQLException {
		Connection connection = this.getConnection();
		try {
			inTransaction = true;

			connection.setAutoCommit(false);
			runnable.run();
		} catch (Exception e) {
			connection.rollback();
			throw e;
		} finally {
			connection.commit();
			connection.setAutoCommit(true);
			inTransaction = false;
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
