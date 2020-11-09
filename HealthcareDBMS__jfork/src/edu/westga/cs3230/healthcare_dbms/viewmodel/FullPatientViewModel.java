package edu.westga.cs3230.healthcare_dbms.viewmodel;

import java.sql.Date;
import java.time.LocalDate;

import edu.westga.cs3230.healthcare_dbms.io.HealthcareIoConstants;
import edu.westga.cs3230.healthcare_dbms.io.database.HealthcareDatabase;
import edu.westga.cs3230.healthcare_dbms.io.database.QueryResult;
import edu.westga.cs3230.healthcare_dbms.model.Address;
import edu.westga.cs3230.healthcare_dbms.model.AppointmentCheckup;
import edu.westga.cs3230.healthcare_dbms.model.AppointmentData;
import edu.westga.cs3230.healthcare_dbms.model.FinalDiagnosis;
import edu.westga.cs3230.healthcare_dbms.model.PatientData;
import edu.westga.cs3230.healthcare_dbms.model.Person;
import edu.westga.cs3230.healthcare_dbms.sql.SqlTuple;
import edu.westga.cs3230.healthcare_dbms.view.AppointmentCodeBehind;
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
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SingleSelectionModel;

/**
 * Viewmodel class for the Login window.
 */
public class FullPatientViewModel {
	
	private FullPatientViewModelSubControl viewModelControl;
	private FullPatientViewModelSubCheck viewModelCheckup;
	private FullPatientViewModelSubAppt viewModelAppt;
	private FullPatientViewModelSubFinal viewModelFinal;
	private FullPatientViewModelSubTest viewModelTest;
	
	private PatientData usingPatient;

	private final StringProperty firstNameProperty;
	private final StringProperty lastNameProperty;
	private final StringProperty contactPhoneProperty;
	private final StringProperty contactEmailProperty;
	private final StringProperty streetAddress1Property;
	private final StringProperty streetAddress2Property;
	private final StringProperty cityProperty;
	private final StringProperty zipCodeProperty;
	private final ObjectProperty<LocalDate> dobProperty;
	private final StringProperty middleInitialProperty;
	private final StringProperty ssnProperty;
	
	private final StringProperty actionTextProperty;
	private final BooleanProperty actionPressedProperty;
	
	private final BooleanProperty closeDisableProperty;
	
	private final ObjectProperty<SingleSelectionModel<String>> stateProperty;
	private final ObjectProperty<SingleSelectionModel<String>> genderProperty;
	
	
	///TODO repurpose to multile object properties in codebehind
	private final StringProperty validationProperty;
	private final BooleanProperty finalizedAppointment;
	private final ObjectProperty<PatientData> selectedPatientProperty;
	private final ObjectProperty<AppointmentData> selectedAppointmentProperty;
	private final ObjectProperty<AppointmentCheckup> selectedCheckupProperty;
	private final ObjectProperty<FinalDiagnosis> selectedFinalDiagnosisProperty;
	private final ObjectProperty<SingleSelectionModel<?>> testOrderListProperty;
	private final ObjectProperty<PatientData> selectedPatient;
	
	private HealthcareDatabase givenDB;
	private ObjectProperty<Object> givenStore;

	public FullPatientViewModel() {
		this.firstNameProperty = new SimpleStringProperty();
		this.lastNameProperty = new SimpleStringProperty();
		this.contactPhoneProperty = new SimpleStringProperty();
		this.contactEmailProperty = new SimpleStringProperty();
		this.streetAddress1Property = new SimpleStringProperty();
		this.streetAddress2Property = new SimpleStringProperty();
		this.cityProperty = new SimpleStringProperty();
		this.zipCodeProperty = new SimpleStringProperty();
		this.dobProperty = new SimpleObjectProperty<LocalDate>();
		this.middleInitialProperty = new SimpleStringProperty();
		this.ssnProperty = new SimpleStringProperty();
		this.actionPressedProperty = new SimpleBooleanProperty(false);
		this.actionTextProperty = new SimpleStringProperty();
		this.stateProperty = new SimpleObjectProperty<SingleSelectionModel<String>>();
		this.genderProperty = new SimpleObjectProperty<SingleSelectionModel<String>>();
		this.validationProperty = new SimpleStringProperty(null);
		this.closeDisableProperty = new SimpleBooleanProperty(false);
		
		this.finalizedAppointment = new SimpleBooleanProperty(false);
		this.selectedPatientProperty = new SimpleObjectProperty<PatientData>();
		this.selectedCheckupProperty = new SimpleObjectProperty<AppointmentCheckup>();
		this.selectedFinalDiagnosisProperty = new SimpleObjectProperty<FinalDiagnosis>();
		this.testOrderListProperty = new SimpleObjectProperty<SingleSelectionModel<?>>();
		this.selectedAppointmentProperty = new SimpleObjectProperty<AppointmentData>();
		
		this.selectedPatient = new SimpleObjectProperty<PatientData>();
		
		this.viewModelControl = new FullPatientViewModelSubControl();
		this.viewModelCheckup = new FullPatientViewModelSubCheck();
		this.viewModelAppt = new FullPatientViewModelSubAppt();
		this.viewModelFinal = new FullPatientViewModelSubFinal();
		this.viewModelTest = new FullPatientViewModelSubTest(this.selectedPatient, this.selectedAppointmentProperty);
	}
	
	public FullPatientViewModelSubControl getViewModelControl() {
		return this.viewModelControl;
	}
	
	public FullPatientViewModelSubCheck getViewModelCheckup() {
		return this.viewModelCheckup;
	}
	
	public FullPatientViewModelSubAppt getViewModelAppt() {
		return this.viewModelAppt;
	}
	
	public FullPatientViewModelSubFinal getViewModelFinal() {
		return viewModelFinal;
	}
	
	public FullPatientViewModelSubTest getViewModelTest() {
		return viewModelTest;
	}
	
	public StringProperty getFirstNameProperty() {
		return firstNameProperty;
	}

	public StringProperty getContactPhoneProperty() {
		return contactPhoneProperty;
	}

	public StringProperty getLastNameProperty() {
		return lastNameProperty;
	}

	public ObjectProperty<SingleSelectionModel<String>> getGenderProperty() {
		return genderProperty;
	}

	public StringProperty getStreetAddress1Property() {
		return streetAddress1Property;
	}

	public StringProperty getStreetAddress2Property() {
		return streetAddress2Property;
	}

	public StringProperty getCityProperty() {
		return cityProperty;
	}

	public ObjectProperty<SingleSelectionModel<String>> getStateProperty() {
		return stateProperty;
	}

	public StringProperty getZipCodePropertyy() {
		return zipCodeProperty;
	}

	public StringProperty getContactEmailProperty() {
		return contactEmailProperty;
	}

	public StringProperty getMiddleInitialProperty() {
		return middleInitialProperty;
	}

	public StringProperty getSsnProperty() {
		return ssnProperty;
	}

	public ObjectProperty<LocalDate> getDobProperty() {
		return dobProperty;
	}
	
	public void setCloseButtonDisabled() {
		this.closeDisableProperty.setValue(true);
	}
	
	public void setActionButtonText(String text) {
		this.getActionTextProperty().setValue(text);
	}
	
	public void setActionButtonValidationNone() {
		this.validationProperty.setValue(PatientCodeBehind.ACTION_VALID_NONE);
	}
	
	public void setActionButtonValidationMinimal() {
		this.validationProperty.setValue(PatientCodeBehind.ACTION_VALID_MINIMAL);
	}
	
	public void setActionButtonValidationAll() {
		this.validationProperty.setValue(PatientCodeBehind.ACTION_VALID_ALL);
	}
	
	public BooleanProperty getActionPressedProperty() {
		return this.actionPressedProperty;
	}

	public StringProperty getActionTextProperty() {
		return actionTextProperty;
	}
	
	public StringProperty getValidationProperty() {
		return validationProperty;
	}
	
	public BooleanProperty getCloseDisableProperty() {
		return this.closeDisableProperty;
	}

	public void setDatabaseAccess(HealthcareDatabase givenDB, ObjectProperty<Object> selectedTupleObject) {
		this.givenDB = givenDB;
		this.givenStore = selectedTupleObject;
	}
	
	public void initFrom(PatientData data) {
		Person person = data.getPerson();
		this.contactEmailProperty.setValue(this.nullToEmpty(person.getContact_email()));
		this.contactPhoneProperty.setValue(this.nullToEmpty(person.getContact_phone()));
		this.dobProperty.setValue(person.getDOB().toLocalDate());
		this.firstNameProperty.setValue(this.nullToEmpty(person.getFname()));
		this.lastNameProperty.setValue(this.nullToEmpty(person.getLname()));
		this.middleInitialProperty.setValue(this.nullToEmpty(person.getMiddle_initial()));
		this.ssnProperty.setValue(String.format("%09d", person.getSSN()));

		Address addr = data.getAddress();
		this.streetAddress1Property.setValue(this.nullToEmpty(addr.getStreet_address1()));
		this.streetAddress2Property.setValue(this.nullToEmpty(addr.getStreet_address2()));
		this.cityProperty.setValue(this.nullToEmpty(addr.getCity()));
		this.zipCodeProperty.setValue(String.format("%05d", addr.getZip_code()));
		
		this.genderProperty.getValue().select(person.getGender());
		this.stateProperty.getValue().select(addr.getState());
		
		this.usingPatient = data;
		
	}

	public PatientData getPatient() {
		
		Date dob = null;
		LocalDate time = this.dobProperty.getValue();
		if(time != null) {
			dob = Date.valueOf(time);
		}
		
		String email = this.nullString(this.contactEmailProperty.getValue());
		String phone =  this.nullString(this.contactPhoneProperty.getValue());
		String fname =  this.nullString(this.firstNameProperty.getValue());
		String lname =  this.nullString(this.lastNameProperty.getValue());
		String middleInitial =  this.nullString(this.middleInitialProperty.getValue());
		String ssn =  this.nullString(this.ssnProperty.getValue());
		String gender =  this.nullString(this.genderProperty.getValue().getSelectedItem());
		
		String street1 = this.nullString(this.streetAddress1Property.getValue());
		String street2 = this.nullString(this.streetAddress2Property.getValue());
		String state = this.nullString(this.stateProperty.getValue().getSelectedItem());
		String city = this.nullString(this.cityProperty.getValue());
		
		Integer zip = null;
		try {
			zip = Integer.parseInt(this.zipCodeProperty.getValue());
		}catch(Exception e) {}
		
		Person person = new Person(email, phone, dob != null ? new java.sql.Date(dob.getTime()) : null, fname, lname, middleInitial, gender, ssn);
		person.setPerson_id(null);
		
		Address addr = new Address(street1, street2, city, state, zip);
		
		return new PatientData(person, addr);
	}
	
	
	
	
	
	
	
	
	public BooleanProperty getFinalizedAppointment() {
		return finalizedAppointment;
	}

	public ObjectProperty<PatientData> getSelectedPatientProperty() {
		return selectedPatientProperty;
	}

	public ObjectProperty<AppointmentCheckup> getSelectedCheckupProperty() {
		return selectedCheckupProperty;
	}

	public ObjectProperty<FinalDiagnosis> getSelectedFinalDiagnosisProperty() {
		return selectedFinalDiagnosisProperty;
	}

	public ObjectProperty<SingleSelectionModel<?>> getTestOrderListProperty() {
		return testOrderListProperty;
	}
	
	public ObjectProperty<PatientData> getSelectedPatient() {
		return selectedPatient;
	}
	
	
	
	
	
	
	
	
	
	private String nullToEmpty(String str) {
		return str == null ? "" : str;
	}
	
	private String nullString(String check) {
		if(check == null) {
			return null;
		}
		return check.isEmpty() ? null : check;
	}

	
	
	
	
	
	
	
	
	
	
	public boolean showCreateAppointment() {
		try {
			FXMLWindow window = new FXMLWindow(HealthcareIoConstants.APPOINTMENT_GUI_URL, "Create Appointment", true);
			AppointmentCodeBehind codeBehind = (AppointmentCodeBehind) window.getController();
			AppointmentViewModel viewModel = codeBehind.getViewModel();
			//viewModel.initFrom(patient);
			//viewModel.populateFrom(this.getTuplesByAssociated(PatientData.class));
			viewModel.setActionButtonText("Create Appointment");

			viewModel.getActionPressedProperty().addListener((evt) -> {
				
				if (viewModel.getActionPressedProperty().getValue()) {
					if (!this.viewModelControl.attemptAddAppointment(viewModel.getAppointment())) {
						FXMLAlert.statusAlert("Add Appointment Failed", "The appointment did not add successfully.", "Add Appointment failed", AlertType.ERROR);
						viewModel.getActionPressedProperty().setValue(false);
					} else {
						FXMLAlert.statusAlert("Add Appointment Success", "The appointment added Successfully.", "Add Appointment Success", AlertType.INFORMATION);
						//TODO REFRESH APPOINTMENTS
						codeBehind.closeWindow(null);
					}
				}

			});
			window.pack();
			window.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	
	
	
	
	
	
	
	
	public void loadAssociatedData() {
		//this.searchUpdate();
	}

	
}
