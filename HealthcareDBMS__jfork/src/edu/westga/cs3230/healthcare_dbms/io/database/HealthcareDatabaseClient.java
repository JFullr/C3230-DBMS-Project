package edu.westga.cs3230.healthcare_dbms.io.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.westga.cs3230.healthcare_dbms.model.Login;
import edu.westga.cs3230.healthcare_dbms.model.Person;
import edu.westga.cs3230.healthcare_dbms.model.dal.LoginDAL;
import edu.westga.cs3230.healthcare_dbms.model.dal.PersonDAL;
import edu.westga.cs3230.healthcare_dbms.model.dal.PostDAL;
import edu.westga.cs3230.healthcare_dbms.sql.SqlAttribute;
import edu.westga.cs3230.healthcare_dbms.sql.SqlManager;

/**
 * Description
 * 
 */
public class HealthcareDatabaseClient {
	
	private PostDAL postDal;
	private LoginDAL loginDal;
	private PersonDAL personDal;
	
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

	public QueryResult attemptAddPatient(Person patient) throws SQLException {
		
		this.lastResult = this.personDal.attemptAddPatient(patient);
		return this.lastResult;
	}


}