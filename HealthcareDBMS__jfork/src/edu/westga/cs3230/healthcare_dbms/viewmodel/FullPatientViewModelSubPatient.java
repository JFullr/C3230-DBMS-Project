package edu.westga.cs3230.healthcare_dbms.viewmodel;

import java.sql.Date;
import java.time.LocalDate;

import edu.westga.cs3230.healthcare_dbms.io.database.HealthcareDatabase;
import edu.westga.cs3230.healthcare_dbms.io.database.QueryResult;
import edu.westga.cs3230.healthcare_dbms.model.Address;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.SingleSelectionModel;

/**
 * View model class for the patient data pane.
 *
 * @author Joseph Fuller and Andrew Steinborn
 */
public class FullPatientViewModelSubPatient {
	
	/** The first name property. */
	private final StringProperty firstNameProperty;
	
	/** The last name property. */
	private final StringProperty lastNameProperty;
	
	/** The contact phone property. */
	private final StringProperty contactPhoneProperty;
	
	/** The contact email property. */
	private final StringProperty contactEmailProperty;
	
	/** The street address 1 property. */
	private final StringProperty streetAddress1Property;
	
	/** The street address 2 property. */
	private final StringProperty streetAddress2Property;
	
	/** The city property. */
	private final StringProperty cityProperty;
	
	/** The zip code property. */
	private final StringProperty zipCodeProperty;
	
	/** The dob property. */
	private final ObjectProperty<LocalDate> dobProperty;
	
	/** The middle initial property. */
	private final StringProperty middleInitialProperty;
	
	/** The ssn property. */
	private final StringProperty ssnProperty;
	
	/** The action text property. */
	private final StringProperty actionTextProperty;
	
	/** The action pressed property. */
	private final BooleanProperty actionPressedProperty;
	
	/** The close disable property. */
	private final BooleanProperty closeDisableProperty;
	
	/** The state property. */
	private final ObjectProperty<SingleSelectionModel<String>> stateProperty;
	
	/** The gender property. */
	private final ObjectProperty<SingleSelectionModel<String>> genderProperty;
	
	/** The validation property. */
	private final StringProperty validationProperty;
	
	/** The given patient property. */
	private final ObjectProperty<PatientData> givenPatientProperty;
	
	/** The given DB. */
	private HealthcareDatabase givenDB;
	
	
	/**
	 * Instantiates a new full patient view model sub patient.
	 *
	 * @param givenPatientProperty the given patient property
	 */
	public FullPatientViewModelSubPatient(ObjectProperty<PatientData> givenPatientProperty) {
		
		this.givenPatientProperty = givenPatientProperty;
		
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
		
		this.addActionHandlers();
		
	}
	
	/**
	 * Sets the database.
	 *
	 * @param givenDB the new database
	 */
	public void setDatabase(HealthcareDatabase givenDB) {
		this.givenDB = givenDB;
	}
	
	/**
	 * Gets the first name property.
	 *
	 * @return the first name property
	 */
	public StringProperty getFirstNameProperty() {
		return this.firstNameProperty;
	}

	/**
	 * Gets the contact phone property.
	 *
	 * @return the contact phone property
	 */
	public StringProperty getContactPhoneProperty() {
		return this.contactPhoneProperty;
	}

	/**
	 * Gets the last name property.
	 *
	 * @return the last name property
	 */
	public StringProperty getLastNameProperty() {
		return this.lastNameProperty;
	}

	/**
	 * Gets the gender property.
	 *
	 * @return the gender property
	 */
	public ObjectProperty<SingleSelectionModel<String>> getGenderProperty() {
		return this.genderProperty;
	}

	/**
	 * Gets the street address 1 property.
	 *
	 * @return the street address 1 property
	 */
	public StringProperty getStreetAddress1Property() {
		return this.streetAddress1Property;
	}

	/**
	 * Gets the street address 2 property.
	 *
	 * @return the street address 2 property
	 */
	public StringProperty getStreetAddress2Property() {
		return this.streetAddress2Property;
	}

	/**
	 * Gets the city property.
	 *
	 * @return the city property
	 */
	public StringProperty getCityProperty() {
		return this.cityProperty;
	}

	/**
	 * Gets the state property.
	 *
	 * @return the state property
	 */
	public ObjectProperty<SingleSelectionModel<String>> getStateProperty() {
		return this.stateProperty;
	}

	/**
	 * Gets the zip code propertyy.
	 *
	 * @return the zip code propertyy
	 */
	public StringProperty getZipCodePropertyy() {
		return this.zipCodeProperty;
	}

	/**
	 * Gets the contact email property.
	 *
	 * @return the contact email property
	 */
	public StringProperty getContactEmailProperty() {
		return this.contactEmailProperty;
	}

	/**
	 * Gets the middle initial property.
	 *
	 * @return the middle initial property
	 */
	public StringProperty getMiddleInitialProperty() {
		return this.middleInitialProperty;
	}

	/**
	 * Gets the ssn property.
	 *
	 * @return the ssn property
	 */
	public StringProperty getSsnProperty() {
		return this.ssnProperty;
	}

	/**
	 * Gets the dob property.
	 *
	 * @return the dob property
	 */
	public ObjectProperty<LocalDate> getDobProperty() {
		return this.dobProperty;
	}
	
	/**
	 * Sets the close button disabled.
	 */
	public void setCloseButtonDisabled() {
		this.closeDisableProperty.setValue(true);
	}
	
	/**
	 * Sets the action button text.
	 *
	 * @param text the new action button text
	 */
	public void setActionButtonText(String text) {
		this.getActionTextProperty().setValue(text);
	}
	
	/**
	 * Sets the action button validation none.
	 */
	public void setActionButtonValidationNone() {
		this.validationProperty.setValue(PatientCodeBehind.ACTION_VALID_NONE);
	}
	
	/**
	 * Sets the action button validation minimal.
	 */
	public void setActionButtonValidationMinimal() {
		this.validationProperty.setValue(PatientCodeBehind.ACTION_VALID_MINIMAL);
	}
	
	/**
	 * Sets the action button validation all.
	 */
	public void setActionButtonValidationAll() {
		this.validationProperty.setValue(PatientCodeBehind.ACTION_VALID_ALL);
	}
	
	/**
	 * Gets the action pressed property.
	 *
	 * @return the action pressed property
	 */
	public BooleanProperty getActionPressedProperty() {
		return this.actionPressedProperty;
	}

	/**
	 * Gets the action text property.
	 *
	 * @return the action text property
	 */
	public StringProperty getActionTextProperty() {
		return this.actionTextProperty;
	}
	
	/**
	 * Gets the validation property.
	 *
	 * @return the validation property
	 */
	public StringProperty getValidationProperty() {
		return this.validationProperty;
	}
	
	/**
	 * Gets the close disable property.
	 *
	 * @return the close disable property
	 */
	public BooleanProperty getCloseDisableProperty() {
		return this.closeDisableProperty;
	}
	
	/**
	 * Inits the from.
	 *
	 * @param data the data
	 */
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
		
		this.givenPatientProperty.setValue(data);
		
	}
	
	/**
	 * Gets the patient.
	 *
	 * @return the patient
	 */
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
	
	/**
	 * Attempt update patient.
	 *
	 * @param patientData the patient data
	 * @param existing the existing
	 * @return true, if successful
	 */
	private boolean attemptUpdatePatient(PatientData patientData, PatientData existing) {

		QueryResult results = this.givenDB.attemptUpdatePatient(patientData, existing);
		if (results == null) {
			return false;
		}

		return true;
	}
	
	
	
	
	/**
	 * Null to empty.
	 *
	 * @param str the str
	 * @return the string
	 */
	private String nullToEmpty(String str) {
		return str == null ? "" : str;
	}
	
	/**
	 * Null string.
	 *
	 * @param check the check
	 * @return the string
	 */
	private String nullString(String check) {
		if(check == null) {
			return null;
		}
		return check.isEmpty() ? null : check;
	}

	
	/**
	 * Adds the action handlers.
	 */
	private void addActionHandlers() {
		this.actionPressedProperty.addListener((e)->{
			if(this.actionPressedProperty.getValue()) {
				this.updatePatient();
			}
		});
	}
	
	/**
	 * Update patient.
	 */
	private void updatePatient() {
		PatientData newData = this.getPatient();
		PatientData cur = this.givenPatientProperty.getValue();
		
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
