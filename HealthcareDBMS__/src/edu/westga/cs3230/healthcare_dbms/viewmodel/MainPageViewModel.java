package edu.westga.cs3230.healthcare_dbms.viewmodel;

import java.util.ArrayList;

import edu.westga.cs3230.healthcare_dbms.io.database.HealthcareDatabase;
import edu.westga.cs3230.healthcare_dbms.model.HealthcareQueryResult;
import edu.westga.cs3230.healthcare_dbms.model.QueryResultStorage;

/**
 * View-model for the MainPageCodeBehind.
 *
 * @author 
 */
public class MainPageViewModel {
	
	private QueryResultStorage queryResults;
	
	private HealthcareDatabase database;

	/**
	 * Instantiates a new MainPageViewModel
	 * 
	 * @precondition none
	 * @postcondition 
	 * 
	 */
	public MainPageViewModel() {
		this.queryResults = new QueryResultStorage();
		this.database = new HealthcareDatabase();
	}


	/**
	 * Loads Query results from the database into the front panel.
	 * 
	 * @precondition none
	 * @postcondition none
	 */
	public void loadDataFromDatabase() {
		for (HealthcareQueryResult result : this.database.getQueryResults()) {
			try {
				///TODO
				//this.frontpanel.add(result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Performs a query on the operated database
	 *
	 * @param query the query
	 */
	public boolean callQuery(String query) {
		boolean success = false;
		try {
			success = this.database.callQuery(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return success;
	}
	
	public ArrayList<HealthcareQueryResult> getLastResult() {
		return this.queryResults.getLatestResults();
	}

	public QueryResultStorage getQueryStorage() {
		return this.queryResults;
	}

}
