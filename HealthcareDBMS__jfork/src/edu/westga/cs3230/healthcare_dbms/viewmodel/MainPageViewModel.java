package edu.westga.cs3230.healthcare_dbms.viewmodel;

import java.util.ArrayList;

import edu.westga.cs3230.healthcare_dbms.io.database.HealthcareDatabase;
import edu.westga.cs3230.healthcare_dbms.io.database.QueryResult;
import edu.westga.cs3230.healthcare_dbms.io.database.QueryResultStorage;
import edu.westga.cs3230.healthcare_dbms.model.Login;
import edu.westga.cs3230.healthcare_dbms.model.Person;
import edu.westga.cs3230.healthcare_dbms.sql.SqlTuple;
import edu.westga.cs3230.healthcare_dbms.utils.ExceptionText;
import edu.westga.cs3230.healthcare_dbms.view.AddPatientCodeBehind;
import edu.westga.cs3230.healthcare_dbms.view.LoginCodeBehind;
import edu.westga.cs3230.healthcare_dbms.view.MainPageCodeBehind;
import edu.westga.cs3230.healthcare_dbms.view.utils.FXMLAlert;
import edu.westga.cs3230.healthcare_dbms.view.utils.FXMLWindow;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert.AlertType;

/**
 * View-model for the MainPageCodeBehind.
 *
 * @author
 */
public class MainPageViewModel {
	
	private static final String ADD_GUI = "AddPatientGui.fxml";
	private static final String LOGIN_GUI = "LoginGui.fxml";
	
	private final BooleanProperty loggedInProperty;
	private final BooleanProperty attemptingLoginProperty;

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
		this.attemptingLoginProperty  = new SimpleBooleanProperty(false);
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
		
		this.loggedInProperty.setValue(true);
		
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
	
	public BooleanProperty getAttemptingLoginProperty() {
		return attemptingLoginProperty;
	}
	
	public void showLogin() {
		try {
			FXMLWindow window = new FXMLWindow(LoginCodeBehind.class.getResource(LOGIN_GUI), "Healthcare Login", true);
			LoginCodeBehind codeBehind = (LoginCodeBehind) window.getController();
			LoginViewModel viewModel = codeBehind.getViewModel();
			
			viewModel.getLoginButtonPressed().addListener((evt)->{
				if (viewModel.getLoginButtonPressed().getValue()) {
					if (!this.attemptLogin(viewModel.getLogin())) {
						FXMLAlert.statusAlert("Login Status", ExceptionText.FAILED_LOGIN, "Login Failed", AlertType.ERROR);
					} else {
						FXMLAlert.statusAlert("Login Successful", AlertType.INFORMATION);
						codeBehind.closeWindow(null);
						this.updateLoginDisplay();
					}
				}
			});
			
			window.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean attemptLogin(Login login) {
		
		QueryResult result = this.database.attemptLogin(login);
		if (result == null || result.getTuples().size() != 1) {
			return false;
		}
		
		this.queryResults.add(result);

		return true;
	}
	
	
	public void showAddPatient() {
		try {
			FXMLWindow window = new FXMLWindow(AddPatientCodeBehind.class.getResource(ADD_GUI), "Add Patient", true);
			AddPatientCodeBehind codeBehind = (AddPatientCodeBehind) window.getController();
			AddPatientViewModel viewModel = codeBehind.getViewModel();
			
			viewModel.getAddEventProperty().addListener((evt) -> {
				
				if (viewModel.getAddEventProperty().getValue()) {
					if (!this.attemptAddPatient(viewModel.getPatient())) {
						FXMLAlert.statusAlert("Add Patient Status", "Patient SSN already exists in the database.", "Add Patient Failed", AlertType.ERROR);
					} else {
						FXMLAlert.statusAlert("Add Patient Status", "Added patient Successfully", AlertType.INFORMATION);
						codeBehind.closeWindow(null);
						///TODO Later iteration: 
						//this.handleUpdateQueryListView();
					}
				}

			});
			
			window.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public boolean attemptAddPatient(Person patient) {
		
		QueryResult result = this.database.attemptAddPatient(patient);
		if (result == null || result.getTuples().size() == 0) {
			return true;
		}
		
		this.queryResults.add(result);

		return false;
	}

	public String getUserType(Person patient) {
		return this.getUserType(patient);
	}

}
