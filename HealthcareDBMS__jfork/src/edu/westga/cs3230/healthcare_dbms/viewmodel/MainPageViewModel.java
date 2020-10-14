package edu.westga.cs3230.healthcare_dbms.viewmodel;

import java.time.chrono.Chronology;
import java.util.ArrayList;

import edu.westga.cs3230.healthcare_dbms.io.database.HealthcareDatabase;
import edu.westga.cs3230.healthcare_dbms.io.database.QueryResult;
import edu.westga.cs3230.healthcare_dbms.io.database.QueryResultStorage;
import edu.westga.cs3230.healthcare_dbms.sql.SqlTuple;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * View-model for the MainPageCodeBehind.
 *
 * @author
 */
public class MainPageViewModel {
	
	private final BooleanProperty loggedInProperty;

	private final StringProperty nameProperty;
	private final StringProperty userIdProperty;
	private final StringProperty userNameProperty;

	private QueryResultStorage queryResults;
	private HealthcareDatabase database;

	/**
	 * Instantiates a new MainPageViewModel
	 * 
	 * @precondition none
	 * @postcondition
	 * 
	 */
	public MainPageViewModel(String dbUrl) {
		this.queryResults = new QueryResultStorage();
		this.database = new HealthcareDatabase(dbUrl);

		this.nameProperty = new SimpleStringProperty();
		this.userIdProperty = new SimpleStringProperty();
		this.userNameProperty = new SimpleStringProperty();
		this.loggedInProperty = new SimpleBooleanProperty(false);

	}

	/**
	 * Loads Query results from the database into the front panel.
	 * 
	 * @precondition none
	 * @postcondition none
	 */
	public void loadDataFromDatabase() {
		for (QueryResult result : this.database.getQueryResults()) {
			try {
				/// TODO
				// this.frontpanel.add(result);
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
	
	public void handleLogOut() {
		this.loggedInProperty.setValue(false);
		this.userIdProperty.setValue("");
		this.userNameProperty.setValue("");
		this.nameProperty.setValue("");
	}
	
	/**
	 * Updates the display with the login name, and user type
	 * 
	 */
	public void updateLoginDisplay() {
		
		ArrayList<QueryResult> results = this.getLastResults();
		ArrayList<SqlTuple> loginTuples = results.get(0).getTuples();
		SqlTuple loginData = loginTuples.get(0);
		
		String user_name= loginData.get("user_name").getValue().toString();
		String user_id = loginData.get("user_id").getValue().toString();
		String name = loginData.get("fname").getValue().toString();
				name += " "+loginData.get("lname").getValue().toString();
		
		this.userIdProperty.setValue(user_id);
		this.userNameProperty.setValue(user_name);
		this.nameProperty.setValue(name);
	}

	public ArrayList<QueryResult> getLastResults() {
		return this.queryResults.getLatestResults();
	}

	public QueryResultStorage getQueryStorage() {
		return this.queryResults;
	}

	public StringProperty getNameProperty() {
		return nameProperty;
	}

	public StringProperty getUserIdProperty() {
		return userIdProperty;
	}

	public StringProperty getUserNameProperty() {
		return userNameProperty;
	}

	public BooleanProperty getLoggedInProperty() {
		return loggedInProperty;
	}
	
	public boolean attemptLogin(String username, String password) {
		
		QueryResult result = this.database.attemptLogin(username, password);
		if (result == null || result.getTuples().size() != 1) {
			return false;
		}
		
		this.queryResults.add(result);

		return true;
	}

	public boolean attemptAddPatient(String email, String phone, String dob, String fname, String lname, String address, String middleInitial, String ssn) {
		
		QueryResult result = this.database.attemptAddPatient(email, phone, dob, fname, lname, address, middleInitial, ssn);
		if (result == null || result.getTuples().size() == 0) {
			return true;
		}
		
		this.queryResults.add(result);

		return false;
	}

}
