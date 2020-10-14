package edu.westga.cs3230.healthcare_dbms.io.database;

import java.time.chrono.Chronology;
import java.util.LinkedList;
import java.util.List;

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
	public QueryResult attemptLogin(String username, String password) {
		try {
			QueryResult result = this.client.attemptLogin(username, password);
			return result;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public QueryResult attemptAddPatient(String email, String phone, String dob, String fname, String lname, String address, String middleInitial, String ssn) {
		try {
			
			QueryResult result = this.client.attemptAddPatient(email, phone, dob, fname, lname, address, middleInitial, ssn);
			return result;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}