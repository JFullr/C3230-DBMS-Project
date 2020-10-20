package edu.westga.cs3230.healthcare_dbms.viewmodel;

import java.sql.Date;

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
	
	public Person getPatient() {
		String email = this.nullString(this.contactEmailProperty.getValue());
		String phone =  this.nullString(this.contactPhoneProperty.getValue());
		String dob =  this.nullString(this.dobProperty.getValue() != null ? this.dobProperty.getValue().toString() : null);
		String fname =  this.nullString(this.firstNameProperty.getValue());
		String lname =  this.nullString(this.lastNameProperty.getValue());
		String address =  this.nullString(this.mailingAddressProperty.getValue());
		String middleInitial =  this.nullString(this.middleInitialProperty.getValue());
		String ssn =  this.nullString(this.ssnProperty.getValue());
		
		Person person = new Person(email, phone, dob != null ? Date.valueOf(dob) : null, fname, lname,
				address, middleInitial, ssn);
		
		person.setPerson_id(null);
		
		return person;
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
