package edu.westga.cs3230.healthcare_dbms.viewmodel;

import java.util.ArrayList;

import edu.westga.cs3230.healthcare_dbms.io.HealthcareIoConstants;
import edu.westga.cs3230.healthcare_dbms.io.database.HealthcareDatabase;
import edu.westga.cs3230.healthcare_dbms.io.database.QueryResult;
import edu.westga.cs3230.healthcare_dbms.model.Login;
import edu.westga.cs3230.healthcare_dbms.sql.SqlTuple;
import edu.westga.cs3230.healthcare_dbms.utils.ExceptionText;
import edu.westga.cs3230.healthcare_dbms.view.LoginCodeBehind;
import edu.westga.cs3230.healthcare_dbms.view.utils.FXMLAlert;
import edu.westga.cs3230.healthcare_dbms.view.utils.FXMLWindow;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Alert.AlertType;

// TODO: Auto-generated Javadoc
/**
 * View-model for the MainPageCodeBehind.
 *
 * @author
 */
public class MainPageViewModel {
	
	/** The view model main. */
	private MainPageViewModelSubMain viewModelMain;

	/** The view model admin. */
	private MainPageViewModelSubAdmin viewModelAdmin;
	
	/** The logged in property. */
	private final BooleanProperty loggedInProperty;
	
	/** The attempting login property. */
	private final BooleanProperty attemptingLoginProperty;
	
	/** The admin logged in property. */
	private final BooleanProperty adminLoggedInProperty;
	
	/** The database. */
	private HealthcareDatabase database;
	
	/**
	 * Instantiates a new main page view model.
	 *
	 * @param dbUrl the db url
	 */
	public MainPageViewModel(String dbUrl) {
		this.database = new HealthcareDatabase(dbUrl);

		this.loggedInProperty = new SimpleBooleanProperty(false);
		this.adminLoggedInProperty = new SimpleBooleanProperty(false);
		this.attemptingLoginProperty  = new SimpleBooleanProperty(false);
		
		this.viewModelMain = new MainPageViewModelSubMain();
		this.viewModelAdmin = new MainPageViewModelSubAdmin();
		
		this.viewModelAdmin.setDatabase(this.database);
		this.viewModelMain.setDatabase(this.database);
		
	}
	
	/**
	 * Gets the view model admin.
	 *
	 * @return the view model admin
	 */
	public MainPageViewModelSubAdmin getViewModelAdmin() {
		return this.viewModelAdmin;
	}
	
	/**
	 * Gets the view model main.
	 *
	 * @return the view model main
	 */
	public MainPageViewModelSubMain getViewModelMain() {
		return this.viewModelMain;
	}
	
	/**
	 * Gets the last results.
	 *
	 * @return the last results
	 */
	public ArrayList<QueryResult> getLastResults() {
		return this.queryResults.getLatestResults();
	}

	/**
	 * Gets the query storage.
	 *
	 * @return the query storage
	 */
	public QueryResultStorage getQueryStorage() {
		return this.queryResults;
	}
	
	/**
	 * Gets the logged in property.
	 *
	 * @return the logged in property
	 */
	public BooleanProperty getLoggedInProperty() {
		return this.loggedInProperty;
	}
	
	/**
	 * Gets the admin logged in property.
	 *
	 * @return the admin logged in property
	 */
	public BooleanProperty getAdminLoggedInProperty() {
		return this.adminLoggedInProperty;
	}
	
	/**
	 * Gets the attempting login property.
	 *
	 * @return the attempting login property
	 */
	public BooleanProperty getAttemptingLoginProperty() {
		return this.attemptingLoginProperty;
	}
	
	/**
	 * Handle log out.
	 */
	public void handleLogOut() {
		this.loggedInProperty.setValue(false);
		this.adminLoggedInProperty.setValue(false);
		this.viewModelMain.getUserIdProperty().setValue("");
		this.viewModelMain.getUserNameProperty().setValue("");
		this.viewModelMain.getNameProperty().setValue("");
		this.queryResults.clear();
		this.viewModelMain.getTupleList().clear();
	}
	
	/**
	 * Updates the display with the login name, and user type.
	 */
	public void updateLoginDisplay() {
		
		this.loggedInProperty.setValue(true);
		
		ArrayList<QueryResult> results = this.getLastResults();
		SqlTuple loginData = results.get(0).getTuple();
		
		String user_name= loginData.get("user_name").getValue().toString();
		String user_id = loginData.get("user_id").getValue().toString();
		String name = loginData.get("fname").getValue().toString();
				name += " "+loginData.get("lname").getValue().toString();
		
		this.viewModelMain.getUserIdProperty().setValue(user_id);
		this.viewModelMain.getUserNameProperty().setValue(user_name);
		this.viewModelMain.getNameProperty().setValue(name);
	}
	
	/**
	 * Show login.
	 */
	public void showLogin() {
		try {
			FXMLWindow window = new FXMLWindow(HealthcareIoConstants.LOGIN_GUI_URL, "Healthcare Login", true);
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
	
	/**
	 * Attempt login.
	 *
	 * @param login the login
	 * @return true, if successful
	 */
	public boolean attemptLogin(Login login) {
		
		QueryResult result = this.database.attemptAdminLogin(login);
		if (result != null) {
			this.adminLoggedInProperty.setValue(true);
			this.queryResults.add(result);
			return true;
		}
		
		result = this.database.attemptLogin(login);
		if (result == null || result.getTuple() == null) {
			return false;
		}
		
		this.queryResults.add(result);

		return true;
	}
	
}
