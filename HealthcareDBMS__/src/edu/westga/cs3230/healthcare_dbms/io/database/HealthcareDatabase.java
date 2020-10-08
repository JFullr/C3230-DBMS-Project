package edu.westga.cs3230.healthcare_dbms.io.database;

import java.util.LinkedList;
import java.util.List;

import edu.westga.cs3230.healthcare_dbms.model.HealthcareQueryResult;

/**
 * Responsible for handling connections to the remote server and local database.
 */
public class HealthcareDatabase {
	
	private List<HealthcareQueryResult> loadedQueries;
	private HealthcareDatabaseClient client;
	
	/**
	 * Instantiates a new database.
	 */
	public HealthcareDatabase() {
		this.loadedQueries = new LinkedList<HealthcareQueryResult>();
		this.client = new HealthcareDatabaseClient(this.loadedQueries);
	}
	
	/**
	 * Logins to the database
	 *
	 * @return the result of the login
	 */
	public HealthcareQueryResult loginIntoDatabase(String username, String password) {
		try {
			return this.client.attemptLogin(username, password);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Gets the queries
	 *
	 * @return the queries
	 */
	public List<HealthcareQueryResult> getQueryResults(){
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
	
	public HealthcareQueryResult getResults() {
		return this.client.getLastQueryResult();
	}
	
}