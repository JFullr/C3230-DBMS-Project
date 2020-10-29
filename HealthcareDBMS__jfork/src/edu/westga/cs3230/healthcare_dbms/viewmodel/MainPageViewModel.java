package edu.westga.cs3230.healthcare_dbms.viewmodel;

import java.util.ArrayList;

import edu.westga.cs3230.healthcare_dbms.io.HealthcareIoConstants;
import edu.westga.cs3230.healthcare_dbms.io.database.HealthcareDatabase;
import edu.westga.cs3230.healthcare_dbms.io.database.QueryResult;
import edu.westga.cs3230.healthcare_dbms.io.database.QueryResultStorage;
import edu.westga.cs3230.healthcare_dbms.model.AppointmentData;
import edu.westga.cs3230.healthcare_dbms.model.Login;
import edu.westga.cs3230.healthcare_dbms.model.PatientData;
import edu.westga.cs3230.healthcare_dbms.model.Person;
import edu.westga.cs3230.healthcare_dbms.sql.SqlTuple;
import edu.westga.cs3230.healthcare_dbms.utils.ExceptionText;
import edu.westga.cs3230.healthcare_dbms.view.AppointmentCodeBehind;
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
	
	private final BooleanProperty loggedInProperty;
	private final BooleanProperty attemptingLoginProperty;

	private final StringProperty nameProperty;
	private final StringProperty userIdProperty;
	private final StringProperty userNameProperty;
	
	private final ObjectProperty<Object> selectedTupleObject;

	private QueryResultStorage queryResults;
	private HealthcareDatabase database;
	
	private ObservableList<TupleEmbed> tuples;
	private ObservableList<TupleEmbed> tuplesShadow;
	private QueryResult lastResults;

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
		
		this.selectedTupleObject = new SimpleObjectProperty<Object>(null);
		
		this.tuples = FXCollections.observableArrayList();
		this.tuplesShadow = FXCollections.observableArrayList();
		this.lastResults = null;
		
		this.addListeners();
	}
	
	private void addListeners() {
		this.selectedTupleObject.addListener((evt)->{
			
			Object obj = this.selectedTupleObject.getValue();
			if(obj == null) {
				return;
			}
			
			
			Class<?> mutateClass = obj.getClass();
			//TODO add more classes to editt
			if(mutateClass == PatientData.class) {
				this.showUpdatePatient((PatientData)obj);
			}
			
			else {
				FXMLAlert.statusAlert("Cannot Edit "+mutateClass.getSimpleName(), AlertType.WARNING);
			}
		});
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
		this.queryResults.clear();
		this.tuples.clear();
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
	
	public ObservableList<TupleEmbed> getTupleList() {
		return this.tuples;
	}
	
	public ObjectProperty<Object> getSelectedTupleObject() {
		return selectedTupleObject;
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
		
		QueryResult result = this.database.attemptLogin(login);
		if (result == null || result.getTuple() == null) {
			return false;
		}
		
		this.addResults(login, result);

		return true;
	}
	
	
	public void showAddPatient() {
		try {
			FXMLWindow window = new FXMLWindow(HealthcareIoConstants.PATIENT_GUI_URL, "Add Patient", true);
			PatientCodeBehind codeBehind = (PatientCodeBehind) window.getController();
			PatientViewModel viewModel = codeBehind.getViewModel();
			viewModel.setActionButtonText("Add Patient");
			
			viewModel.getActionPressedProperty().addListener((evt) -> {
				
				if (viewModel.getActionPressedProperty().getValue()) {
					if (!this.attemptAddPatient(viewModel.getPatient())) {
						FXMLAlert.statusAlert("Add Patient Status", "Patient SSN already exists in the database.", "Add Patient Failed", AlertType.ERROR);
						viewModel.getActionPressedProperty().setValue(false);
					} else {
						FXMLAlert.statusAlert("Add Patient Status", "Added patient Successfully", AlertType.INFORMATION);
						codeBehind.closeWindow(null);
					}
				}

			});
			
			window.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void showPatientSearch() {
		try {
			FXMLWindow window = new FXMLWindow(HealthcareIoConstants.PATIENT_GUI_URL, "Search Patient", true);
			PatientCodeBehind codeBehind = (PatientCodeBehind) window.getController();
			PatientViewModel viewModel = codeBehind.getViewModel();
			viewModel.setActionButtonText("Search Patient");
			viewModel.setActionButtonValidationMinimal();
			
			viewModel.getActionPressedProperty().addListener((evt) -> {
				
				if (viewModel.getActionPressedProperty().getValue()) {
					if (!this.attemptPatientSearch(viewModel.getPatient())) {
						FXMLAlert.statusAlert("Search Failed", "The patient search did not complete successfully.", "Patient Search failed", AlertType.ERROR);
						viewModel.getActionPressedProperty().setValue(false);
					} else {
						FXMLAlert.statusAlert("Search Success", "The patient search found one or more results.", "Patient Search Success", AlertType.INFORMATION);
						codeBehind.closeWindow(null);
					}
				}

			});
			window.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void showUpdatePatient(PatientData patient) {
		try {
			FXMLWindow window = new FXMLWindow(HealthcareIoConstants.PATIENT_GUI_URL, "Update Patient", true);
			PatientCodeBehind codeBehind = (PatientCodeBehind) window.getController();
			PatientViewModel viewModel = codeBehind.getViewModel();
			viewModel.initFrom(patient);
			viewModel.setActionButtonText("Update Patient");

			viewModel.getActionPressedProperty().addListener((evt) -> {
				
				if (viewModel.getActionPressedProperty().getValue()) {
					if (!this.attemptUpdatePatient(viewModel.getPatient(), patient)) {
						FXMLAlert.statusAlert("Update Failed", "The patient update did not complete successfully.", "Patient Update failed", AlertType.ERROR);
						viewModel.getActionPressedProperty().setValue(false);
					} else {
						FXMLAlert.statusAlert("Update Success", "The patient update completed Successfully.", "Patient Update Success", AlertType.INFORMATION);
						codeBehind.closeWindow(null);
					}
				}

			});
			window.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void showCreateAppointment() {
		// TODO Auto-generated method stub
		try {
			FXMLWindow window = new FXMLWindow(HealthcareIoConstants.APPOINTMENT_GUI_URL, "Create Appointment", true);
			AppointmentCodeBehind codeBehind = (AppointmentCodeBehind) window.getController();
			AppointmentViewModel viewModel = codeBehind.getViewModel();
			//viewModel.initFrom(patient);
			viewModel.populateFrom(this.getTuplesByAssociated(PatientData.class));
			viewModel.setActionButtonText("Create Appointment");

			viewModel.getActionPressedProperty().addListener((evt) -> {
				
				if (viewModel.getActionPressedProperty().getValue()) {
					if (!this.attemptAddAppointment(viewModel.getAppointment())) {
						FXMLAlert.statusAlert("Add Appointment Failed", "The appointment did not add successfully.", "Add Appointment failed", AlertType.ERROR);
						viewModel.getActionPressedProperty().setValue(false);
					} else {
						FXMLAlert.statusAlert("Add Appointment Success", "The appointment added Successfully.", "Add Appointment Success", AlertType.INFORMATION);
						codeBehind.closeWindow(null);
					}
				}

			});
			window.pack();
			window.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private boolean attemptUpdatePatient(PatientData patientData, PatientData existing) {

		QueryResult results = this.database.attemptUpdatePatient(patientData, existing);
		if (results == null) {
			return false;
		}
		
		results = this.database.getPatientBySSN(patientData);
		PatientData updatedPatient = (PatientData)results.getAssociated();
		this.addResults(updatedPatient, updatedPatient.getPerson(), results);

		return true;
	}

	public boolean attemptAddPatient(PatientData patientData) {
		
		QueryResult results = this.database.attemptAddPatient(patientData);
		if (results == null || results.getTuple()== null) {
			return false;
		}
		
		results = this.database.getPatientBySSN(patientData);
		
		this.addResults(patientData, patientData.getPerson(), results);
		return true;
	}
	
	public boolean attemptAddAppointment(AppointmentData appointmentData) {
		
		QueryResult results = this.database.attemptAddAppointment(appointmentData);
		if (results == null || results.getTuple()== null) {
			return false;
		}
		
		results = this.database.getAppointmentBy(appointmentData);
		
		//this.addResults(patientData, patientData.getPerson(), results);
		return true;
	}

	public String getUserType(Person patient) {
		return this.getUserType(patient);
	}

	private boolean attemptPatientSearch(PatientData patientData) {
		QueryResult result = this.database.attemptSearchPatient(patientData);
		if (result == null || result.getTuple() == null) {
			return false;
		}
		
		PatientData patientFound = (PatientData) result.getAssociated();
		this.addResults(patientFound, patientFound.getPerson(), result);
		return true;
	}
	
	private void addResults(Object operatedOn, QueryResult results) {
		this.addResults(operatedOn, operatedOn, results);
	}
	
	private void addResults(Object operatedOn, Object display, QueryResult results) {
		
		if(results == this.lastResults || results == null) {
			return;
		}

		this.queryResults.add(results);
		
		this.tuples.clear();
		this.tuplesShadow.clear();
		
		TupleEmbed embed = null;
		TupleEmbed shadow = null;
		for(QueryResult result : results) {
			SqlTuple tup = result.getTuple();
			if(result.getAssociated() == null) {
				embed =  this.createEmbed(operatedOn, display, tup);
				shadow =  this.createEmbed(operatedOn, display, tup);
			} else {
				embed = this.createEmbed(result.getAssociated(), result.getAssociated(), tup);
				shadow =  this.createEmbed(operatedOn, display, tup);
			}
			
			this.tuples.add(embed);
			this.tuplesShadow.add(shadow);
		}
		
	}
	
	private TupleEmbed createEmbed(Object operatesOn, Object display, SqlTuple attributes) {
		TupleEmbed embed = new TupleEmbed(operatesOn, display, attributes);
			
		final TupleEmbed xbed = embed;
		xbed.getPressedPropertyAction().addListener((evt)->{
			this.selectedTupleObject.setValue(xbed.getPressedPropertyAction().getValue());
		});
		
		return embed;
	}
	
	private ObservableList<TupleEmbed> getTuplesByAssociated(Class<?> classAssociated){
		ObservableList<TupleEmbed> found = FXCollections.observableArrayList();
		
		for(TupleEmbed embed : this.tuplesShadow) {
			Object obj = embed.getOperatedObject();
			if(obj != null && obj.getClass() == classAssociated) {
				embed.setMouseTransparent(true);
				found.add(embed);
			}
		}
		
		return found;
	}
	
}
