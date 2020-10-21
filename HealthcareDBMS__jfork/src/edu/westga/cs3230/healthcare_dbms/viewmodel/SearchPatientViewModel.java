package edu.westga.cs3230.healthcare_dbms.viewmodel;

import java.sql.Date;

import edu.westga.cs3230.healthcare_dbms.model.Address;
import edu.westga.cs3230.healthcare_dbms.model.PatientData;
import edu.westga.cs3230.healthcare_dbms.model.Person;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * Viewmodel class for the Login window.
 */
public class SearchPatientViewModel {

	private final StringProperty firstNameProperty;
	private final StringProperty lastNameProperty;
	private final StringProperty genderProperty;
	private final StringProperty contactPhoneProperty;
	private final StringProperty contactEmailProperty;
	private final StringProperty mailingAddressProperty;
	private final ObjectProperty<Date> dobProperty;
	private final StringProperty middleInitialProperty;
	private final StringProperty ssnProperty;
	
	private final BooleanProperty searchEventProperty;

	/**
	 * Instantiates a new LoginViewModel.
	 */
	public SearchPatientViewModel() {
		this.firstNameProperty = new SimpleStringProperty();
		this.lastNameProperty = new SimpleStringProperty();
		this.contactPhoneProperty = new SimpleStringProperty();
		this.contactEmailProperty = new SimpleStringProperty();
		this.mailingAddressProperty = new SimpleStringProperty();
		this.dobProperty = new SimpleObjectProperty<Date>();
		this.middleInitialProperty = new SimpleStringProperty();
		this.ssnProperty = new SimpleStringProperty();
		this.searchEventProperty = new SimpleBooleanProperty(false);
		this.genderProperty = new SimpleStringProperty();
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

	public StringProperty getMailingAddressProperty() {
		return mailingAddressProperty;
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

	public ObjectProperty<Date> getDobProperty() {
		return dobProperty;
	}

	public StringProperty getGenderProperty() {
		return genderProperty;
	}
	
	public PatientData getPatient() {
		String email = this.nullString(this.contactEmailProperty.getValue());
		String phone =  this.nullString(this.contactPhoneProperty.getValue());
		String dob =  this.nullString(this.dobProperty.getValue() != null ? this.dobProperty.getValue().toString() : null);
		String fname =  this.nullString(this.firstNameProperty.getValue());
		String lname =  this.nullString(this.lastNameProperty.getValue());
		String middleInitial =  this.nullString(this.middleInitialProperty.getValue());
		String ssn =  this.nullString(this.ssnProperty.getValue());
		String gender =  this.nullString(this.genderProperty.getValue());
		
		Person person = new Person(email, phone, dob != null ? Date.valueOf(dob) : null, fname, lname, middleInitial, gender, ssn);
		person.setPerson_id(null);
		
		String street1 = null;//this.streetAddress1Property.getValue();
		String street2 = null;//this.nullString(this.streetAddress2Property.getValue());
		String state = null;//this.stateProperty.getValue();
		Integer zip = null;//Integer.parseInt(this.zipCodeProperty.getValue());
		
		Address addr = new Address(street1, street2, state, zip);
		
		return new PatientData(person, addr);
	}
	
	public BooleanProperty getSearchEventProperty() {
		return this.searchEventProperty;
	}
	
	private String nullString(String check) {
		if(check == null) {
			return null;
		}
		return check.isEmpty() ? null : check;
	}
	
}
