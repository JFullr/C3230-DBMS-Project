package edu.westga.cs3230.healthcare_dbms.io.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.westga.cs3230.healthcare_dbms.model.HealthcareQueryResult;
import query.utils.SQLData;

/**
 * Description
 * 
 */
public class HealthcareDatabaseClient {
	
	private HealthcareQueryResult lastResult;
	
	/**
	 * Instantiates a new healthcare database client.
	 *
	 * @param input/output the storage for queries
	 */
	public HealthcareDatabaseClient(List<HealthcareQueryResult> storageForReadQueries) {
		this.lastResult = null;
	}
	
	public boolean callQuery(String query) throws Exception {
		// TODO Auto-generated method stu
		return false;
	}

	public ArrayList<HashMap<String, SQLData>> getQueryTuples() {
		// TODO Auto-generated method stu
		return null;
	}

	public HealthcareQueryResult attemptLogin(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public HealthcareQueryResult getLastQueryResult() {
		return this.lastResult;
	}


}
