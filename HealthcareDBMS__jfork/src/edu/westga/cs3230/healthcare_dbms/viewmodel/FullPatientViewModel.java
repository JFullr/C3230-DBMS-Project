package edu.westga.cs3230.healthcare_dbms.viewmodel;

import java.sql.Date;
import java.time.LocalDate;

import edu.westga.cs3230.healthcare_dbms.io.database.HealthcareDatabase;
import edu.westga.cs3230.healthcare_dbms.io.database.QueryResult;
import edu.westga.cs3230.healthcare_dbms.model.Address;
import edu.westga.cs3230.healthcare_dbms.model.Appointment;
import edu.westga.cs3230.healthcare_dbms.model.AppointmentCheckup;
import edu.westga.cs3230.healthcare_dbms.model.AppointmentData;
import edu.westga.cs3230.healthcare_dbms.model.FinalDiagnosis;
import edu.westga.cs3230.healthcare_dbms.model.PatientData;
import edu.westga.cs3230.healthcare_dbms.model.Person;
import edu.westga.cs3230.healthcare_dbms.view.PatientCodeBehind;
import edu.westga.cs3230.healthcare_dbms.view.utils.FXMLAlert;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Alert.AlertType;

/**
 * Viewmodel class for the Login window.
 */
public class FullPatientViewModel {
	
	private FullPatientViewModelSubCheckup viewModelCheckup;
	private FullPatientViewModelSubAppt viewModelAppt;
	private FullPatientViewModelSubFinal viewModelFinal;
	private FullPatientViewModelSubTest viewModelTest;

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
	
	private final BooleanProperty editableAppointmentProperty;
	
	private final ObjectProperty<SingleSelectionModel<String>> stateProperty;
	private final ObjectProperty<SingleSelectionModel<String>> genderProperty;
	
	private final StringProperty validationProperty;
	private final BooleanProperty finalizedAppointment;
	private final ObjectProperty<PatientData> selectedDoctorProperty;
	private final ObjectProperty<AppointmentData> selectedAppointmentProperty;
	private final ObjectProperty<AppointmentCheckup> selectedCheckupProperty;
	private final ObjectProperty<FinalDiagnosis> selectedFinalDiagnosisProperty;
	private final ObjectProperty<SingleSelectionModel<?>> testOrderListProperty;
	private final ObjectProperty<PatientData> selectedPatientProperty;
	
	private HealthcareDatabase givenDB;
	private ObjectProperty<Object> givenStore;
	
	private boolean initialDataLoad;

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
		
		this.editableAppointmentProperty = new SimpleBooleanProperty(true);
		
		this.finalizedAppointment = new SimpleBooleanProperty(false);
		this.selectedCheckupProperty = new SimpleObjectProperty<AppointmentCheckup>();
		this.selectedFinalDiagnosisProperty = new SimpleObjectProperty<FinalDiagnosis>();
		this.testOrderListProperty = new SimpleObjectProperty<SingleSelectionModel<?>>();
		this.selectedAppointmentProperty = new SimpleObjectProperty<AppointmentData>();
		
		this.selectedPatientProperty = new SimpleObjectProperty<PatientData>();
		this.selectedDoctorProperty = new SimpleObjectProperty<PatientData>();
		
		this.initialDataLoad = false;
		
		this.viewModelCheckup = new FullPatientViewModelSubCheckup(this.selectedPatientProperty, this.selectedAppointmentProperty, this.selectedCheckupProperty);
		this.viewModelAppt = new FullPatientViewModelSubAppt(this.selectedPatientProperty, this.selectedAppointmentProperty);
		this.viewModelFinal = new FullPatientViewModelSubFinal(this.selectedAppointmentProperty, this.selectedFinalDiagnosisProperty);
		this.viewModelTest = new FullPatientViewModelSubTest(this.selectedPatientProperty, this.selectedAppointmentProperty);
	
		this.addActionHandlers();
	}
	
	public FullPatientViewModelSubCheckup getViewModelCheckup() {
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
	
	public ObjectProperty<AppointmentData> getSelectedAppointmentProperty() {
		return selectedAppointmentProperty;
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

	public BooleanProperty getEditableAppointmentProperty() {
		return editableAppointmentProperty;
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
	
	public void setDatabase(HealthcareDatabase givenDB) {
		this.givenDB = givenDB;
		this.viewModelAppt.setDatabase(givenDB);
		this.viewModelCheckup.setDatabase(givenDB);
		this.viewModelFinal.setDatabase(givenDB);
		this.viewModelTest.setDatabase(givenDB);
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
		
		this.selectedPatientProperty.setValue(data);
		
	}
	
	public void setSelectedAppointment(Appointment appt, boolean canEdit) {
		this.editableAppointmentProperty.setValue(canEdit);
		this.setSelectedAppointment(appt);
	}
	
	public void setSelectedAppointment(Appointment appt) {
		if(this.selectedAppointmentProperty.getValue() == null) {
			this.selectedAppointmentProperty.setValue(new AppointmentData(appt,null));
		} else {
			this.selectedAppointmentProperty.setValue(new AppointmentData(appt,
					this.selectedAppointmentProperty.getValue().getPatient()));
		}
		this.viewModelAppt.initFrom(appt);
		this.viewModelCheckup.loadCheckupData();
		this.viewModelFinal.loadFinalDiagnosis();
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
	
	//TODO query DB for necessary related data -- doctors, appointments, lab tests
	public void loadData() {
		if(!this.initialDataLoad) {
			this.viewModelAppt.updateAvailableAppointments();
			this.viewModelAppt.loadDoctors();
			this.viewModelTest.loadLabTests();
			this.initialDataLoad = true;
		}
	}
	
	
	private boolean attemptUpdatePatient(PatientData patientData, PatientData existing) {

		QueryResult results = this.givenDB.attemptUpdatePatient(patientData, existing);
		if (results == null) {
			return false;
		}

		return true;
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

	
	private void addActionHandlers() {
		this.actionPressedProperty.addListener((e)->{
			if(this.actionPressedProperty.getValue()) {
				this.updatePatient();
			}
		});
	}
	
	private void updatePatient() {
		PatientData newData = this.getPatient();
		PatientData cur = this.selectedPatientProperty.getValue();
		
		if(cur == null) {
			FXMLAlert.statusAlert("Update Patient Failed", "The patient was malformed.", "Update Patient failed", AlertType.ERROR);
			return;
		}
		
		if (!this.attemptUpdatePatient(newData, cur)) {
			FXMLAlert.statusAlert("Update Patient Failed", "The patient was not updated successfully.", "Update Patient failed", AlertType.ERROR);
		} else {
			FXMLAlert.statusAlert("Update Patient Success", "The patient was updated successfully.", "Update Patient Success", AlertType.INFORMATION);
			//TODO propagate main display on close of window instead of forced propagation, maybe use old search query
			//this.updateAvailableAppointments();
		}
	}
	
}
