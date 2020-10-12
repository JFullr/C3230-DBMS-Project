package edu.westga.cs3230.healthcare_dbms.viewmodel;

import java.util.ArrayList;

import edu.westga.cs3230.healthcare_dbms.io.database.DatabaseConnector;
import edu.westga.cs3230.healthcare_dbms.model.RawQueryResult;
import edu.westga.cs3230.healthcare_dbms.model.QueryResultStorage;

/**
 * View-model for the MainPageCodeBehind.
 *
 * @author 
 */
public class MainPageViewModel {
	
	private QueryResultStorage queryResults;
	
	private DatabaseConnector database;

	/**
	 * Instantiates a new MainPageViewModel
	 * 
	 * @precondition none
	 * @postcondition 
	 * 
	 */
	public MainPageViewModel() {
		this.queryResults = new QueryResultStorage();
		this.database = new DatabaseConnector();
	}
	
	public ArrayList<RawQueryResult> getLastResult() {
		return this.queryResults.getLatestResults();
	}

	public QueryResultStorage getQueryStorage() {
		return this.queryResults;
	}

	public void attemptLogin(String username, String password) {

	}
}
