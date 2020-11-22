package edu.westga.cs3230.healthcare_dbms.viewmodel;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import edu.westga.cs3230.healthcare_dbms.io.HealthcareIoConstants;
import edu.westga.cs3230.healthcare_dbms.io.database.HealthcareDatabase;
import edu.westga.cs3230.healthcare_dbms.io.database.QueryResult;
import edu.westga.cs3230.healthcare_dbms.io.database.QueryResultStorage;
import edu.westga.cs3230.healthcare_dbms.model.Address;
import edu.westga.cs3230.healthcare_dbms.model.Login;
import edu.westga.cs3230.healthcare_dbms.model.PatientData;
import edu.westga.cs3230.healthcare_dbms.model.Person;
import edu.westga.cs3230.healthcare_dbms.sql.SqlTuple;
import edu.westga.cs3230.healthcare_dbms.utils.ExceptionText;
import edu.westga.cs3230.healthcare_dbms.view.FullPatientCodeBehind;
import edu.westga.cs3230.healthcare_dbms.view.LoginCodeBehind;
import edu.westga.cs3230.healthcare_dbms.view.PatientCodeBehind;
import edu.westga.cs3230.healthcare_dbms.view.embed.TupleEmbed;
import edu.westga.cs3230.healthcare_dbms.view.utils.FXMLAlert;
import edu.westga.cs3230.healthcare_dbms.view.utils.FXMLWindow;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert.AlertType;

/**
 * View-model for the MainPageCodeBehind.
 *
 * @author
 */
public class MainPageViewModel {
	
	private MainPageViewModelSubMain viewModelMain;

	private MainPageViewModelSubAdmin viewModelAdmin;
	
	private final BooleanProperty loggedInProperty;
	private final BooleanProperty attemptingLoginProperty;
	private final BooleanProperty adminLoggedInProperty;

	private QueryResultStorage queryResults;
	private HealthcareDatabase database;
	
	
	
	public MainPageViewModel(String dbUrl) {
		this.queryResults = new QueryResultStorage();
		this.database = new HealthcareDatabase(dbUrl);

		this.loggedInProperty = new SimpleBooleanProperty(false);
		this.adminLoggedInProperty = new SimpleBooleanProperty(false);
		this.attemptingLoginProperty  = new SimpleBooleanProperty(false);
		
		this.viewModelMain = new MainPageViewModelSubMain();
		this.viewModelAdmin = new MainPageViewModelSubAdmin();
		
		this.viewModelAdmin.setDatabase(this.database);
		this.viewModelMain.setDatabase(this.database);
		
	}
	
	public MainPageViewModelSubAdmin getViewModelAdmin() {
		return viewModelAdmin;
	}
	
	public MainPageViewModelSubMain getViewModelMain() {
		return viewModelMain;
	}
	
	public ArrayList<QueryResult> getLastResults() {
		return this.queryResults.getLatestResults();
	}

	public QueryResultStorage getQueryStorage() {
		return this.queryResults;
	}
	
	public BooleanProperty getLoggedInProperty() {
		return this.loggedInProperty;
	}
	
	public BooleanProperty getAdminLoggedInProperty() {
		return this.adminLoggedInProperty;
	}
	
	public BooleanProperty getAttemptingLoginProperty() {
		return this.attemptingLoginProperty;
	}
	
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
	 * Updates the display with the login name, and user type
	 * 
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
