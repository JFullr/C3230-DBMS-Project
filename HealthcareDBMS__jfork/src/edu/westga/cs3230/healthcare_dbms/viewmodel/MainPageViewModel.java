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
	
	private final BooleanProperty loggedInProperty;
	private final BooleanProperty attemptingLoginProperty;
	
	private final BooleanProperty adminLoggedInProperty;

	private final StringProperty nameProperty;
	private final StringProperty userIdProperty;
	private final StringProperty userNameProperty;
	
	private final ObjectProperty<Object> selectedTupleObject;

	private QueryResultStorage queryResults;
	private HealthcareDatabase database;
	
	private ObservableList<TupleEmbed> tuples;
	private QueryResult lastResults;
	
	private final ObjectProperty<LocalDate> adminStartDateProperty;
	private final ObjectProperty<LocalDate> adminEndDateProperty;
	private final StringProperty adminQueryProperty;
	private final ObservableList<TupleEmbed> adminResultList;
	

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
		this.adminLoggedInProperty = new SimpleBooleanProperty(false);
		this.attemptingLoginProperty  = new SimpleBooleanProperty(false);
		
		this.selectedTupleObject = new SimpleObjectProperty<Object>(null);
		
		this.tuples = FXCollections.observableArrayList();
		this.lastResults = null;
		
		this.addListeners();
		
		this.adminStartDateProperty = new SimpleObjectProperty<LocalDate>();
		this.adminEndDateProperty = new SimpleObjectProperty<LocalDate>();
		this.adminQueryProperty = new SimpleStringProperty();
		this.adminResultList = FXCollections.observableArrayList();
	}
	
	private void addListeners() {
		this.selectedTupleObject.addListener((evt)->{
			
			Object obj = this.selectedTupleObject.getValue();
			if(obj == null) {
				return;
			}
			
			
			Class<?> mutateClass = obj.getClass();
			if(mutateClass == PatientData.class) {
				this.handlePatientMod((PatientData)obj);
			} else {
				FXMLAlert.statusAlert("Cannot Edit "+mutateClass.getSimpleName(), AlertType.WARNING);
			}
		});
	}
	
	public void handleLogOut() {
		this.loggedInProperty.setValue(false);
		this.adminLoggedInProperty.setValue(false);
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
		return this.nameProperty;
	}

	public StringProperty getUserIdProperty() {
		return this.userIdProperty;
	}

	public StringProperty getUserNameProperty() {
		return this.userNameProperty;
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
	
	public ObservableList<TupleEmbed> getTupleList() {
		return this.tuples;
	}
	
	public ObjectProperty<Object> getSelectedTupleObject() {
		return this.selectedTupleObject;
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
		
	public void handlePatientMod(PatientData patient) {
		try {
			
			Person person = patient.getPerson();
			if(person == null) {
				//TODO error message
				return;
			}
			
			String fullName = person.getFname()+" "+person.getLname();
			FXMLWindow window = new FXMLWindow(HealthcareIoConstants.FULL_PATIENT_URL, "Modify Patient: "+fullName, true);
			FullPatientCodeBehind codeBehind = (FullPatientCodeBehind) window.getController();
			FullPatientViewModel viewModel = codeBehind.getViewModel();
			viewModel.getViewModelPatient().initFrom(patient);
			viewModel.setDatabase(this.database);
			
			window.pack();
			window.show();
			
			//TODO refresh patients here

		} catch (Exception e) {
			e.printStackTrace();
		}
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

	private boolean attemptPatientSearch(PatientData patientData) {
		QueryResult result = this.database.attemptSearchPatient(patientData);
		if (result == null || result.getTuple() == null) {
			return false;
		}
		
		PatientData patientFound = (PatientData) result.getAssociated();
		this.addResults(patientFound, patientFound.getPerson(), result);
		return true;
	}
	
	private void addResults(Object operatedOn, Object display, QueryResult results) {
		
		if(results == this.lastResults || results == null) {
			return;
		}

		this.queryResults.add(results);
		
		this.tuples.clear();
		
		TupleEmbed embed = null;
		for(QueryResult result : results) {
			SqlTuple tup = result.getTuple();
			if(result.getAssociated() == null) {
				embed =  this.createEmbed(operatedOn, display, tup.hideBasedOn(display));
			} else {
				embed = this.createEmbed(result.getAssociated(), result.getAssociated(), tup.hideBasedOn(result.getAssociated()));
			}
			
			if(embed != null) {
				this.tuples.add(embed);
			}
		}
		
	}
	
	private TupleEmbed createEmbed(Object operatesOn, Object display, SqlTuple attributes) {
		if(attributes == null) {
			return null;
		}
		if(operatesOn != null && operatesOn.getClass() == PatientData.class) {
			return this.createPatientEmbed(operatesOn, display, attributes);
		}
		
		return this.createGenericEmbed(operatesOn, display, attributes);
	}
	
	private TupleEmbed createPatientEmbed(Object operatesOn, Object display, SqlTuple attributes) {
		
		PatientData patient = (PatientData)operatesOn;
		Person person = patient.getPerson();
		Address addr = patient.getAddress();
		
		SqlTuple made = new SqlTuple();
		
		made.add("FirstName", person.getFname());
		made.add("MidInit", person.getMiddle_initial());
		made.add("LastName", person.getLname());
		made.add("SSN", person.getSSN());
		made.add("DateOfBirth", person.getDOB());
		made.add("Gender", person.getGender());
		made.add("Contact Email", person.getContact_email());
		made.add("Contact Phone", person.getContact_phone());
		
		made.add("State", addr.getState());
		made.add("City", addr.getCity());
		made.add("ZipCode", addr.getZip_code());
		made.add("Address 1", addr.getStreet_address1());
		if(addr.getStreet_address2() != null) {
			made.add("Address 2", addr.getStreet_address2());
		}
		
		
		TupleEmbed embed = new TupleEmbed(operatesOn, display, made);
		
		final TupleEmbed xbed = embed;
		xbed.getPressedPropertyAction().addListener((evt)->{
			this.selectedTupleObject.setValue(xbed.getPressedPropertyAction().getValue());
		});
		
		return embed;
	}
	
	private TupleEmbed createGenericEmbed(Object operatesOn, Object display, SqlTuple attributes) {
		TupleEmbed embed = new TupleEmbed(operatesOn, display, attributes);
			
		final TupleEmbed xbed = embed;
		xbed.getPressedPropertyAction().addListener((evt)->{
			this.selectedTupleObject.setValue(xbed.getPressedPropertyAction().getValue());
		});
		
		return embed;
	}

	
	
	
	
	
	
	
	
	/**
	 * Performs a query on the operated database
	 *
	 * @param query the query
	 */
	public void handleAdminQuery() {
		String rawSql = this.adminQueryProperty.getValue();
		
		QueryResult results = null;
		try {
			results = this.database.callAdminQuery(rawSql);
		} catch (Exception e) {
			FXMLAlert.statusAlert(e.getMessage());
			return;
		}
		this.setAdminResults(results);
		this.adminQueryProperty.setValue("");
	}
	
	public void handleAdminDateSearch() {
		LocalDate start = this.adminStartDateProperty.getValue();
		LocalDate end = this.adminEndDateProperty.getValue();
		Date dStart = Date.valueOf(start);
		Date dEnd = Date.valueOf(end);
		
		QueryResult results = this.database.callAdminDateQuery(dStart, dEnd);
		this.setAdminResults(results);
	}

	public ObservableList<TupleEmbed> getAdminResultList() {
		return adminResultList;
	}

	public StringProperty getAdminQueryProperty() {
		return adminQueryProperty;
	}

	public ObjectProperty<LocalDate> getAdminEndDateProperty() {
		return adminEndDateProperty;
	}

	public ObjectProperty<LocalDate> getAdminStartDateProperty() {
		return adminStartDateProperty;
	}
	
	private void setAdminResults(QueryResult results) {
		this.adminResultList.clear();
		for(QueryResult result : results) {
			if(result.getTuple() != null) {
				TupleEmbed embed = new TupleEmbed(null, null, result.getTuple());
				this.adminResultList.add(embed);
			}
		}
	}
}
