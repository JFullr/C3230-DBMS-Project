package edu.westga.cs3230.healthcare_dbms.viewmodel;

import java.sql.Date;

import edu.westga.cs3230.healthcare_dbms.model.Address;
import edu.westga.cs3230.healthcare_dbms.model.Patient;
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
public class PatientViewModel {

	private final StringProperty firstNameProperty;
	private final StringProperty lastNameProperty;
	private final StringProperty contactPhoneProperty;
	private final StringProperty contactEmailProperty;
	private final StringProperty streetAddress1Property;
	private final StringProperty streetAddress2Property;
	private final StringProperty cityProperty;
	private final StringProperty stateProperty;
	private final StringProperty zipCodeProperty;
	private final ObjectProperty<Date> dobProperty;
	private final StringProperty middleInitialProperty;
	private final StringProperty ssnProperty;
	
	private final StringProperty actionTextProperty;
	
	private final BooleanProperty addEventProperty;

	/**
	 * Instantiates a new LoginViewModel.
	 */
	public PatientViewModel() {
		this.firstNameProperty = new SimpleStringProperty();
		this.lastNameProperty = new SimpleStringProperty();
		this.contactPhoneProperty = new SimpleStringProperty();
		this.contactEmailProperty = new SimpleStringProperty();
		this.streetAddress1Property = new SimpleStringProperty();
		this.streetAddress2Property = new SimpleStringProperty();
		this.cityProperty = new SimpleStringProperty();
		this.stateProperty = new SimpleStringProperty();
		this.zipCodeProperty = new SimpleStringProperty();
		this.dobProperty = new SimpleObjectProperty<Date>();
		this.middleInitialProperty = new SimpleStringProperty();
		this.ssnProperty = new SimpleStringProperty();
		this.addEventProperty = new SimpleBooleanProperty(false);
		this.actionTextProperty = new SimpleStringProperty();
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

	public StringProperty getStreetAddress1Property() {
		return streetAddress1Property;
	}

	public StringProperty getStreetAddress2Property() {
		return streetAddress2Property;
	}

	public StringProperty getCityProperty() {
		return cityProperty;
	}

	public StringProperty getStateProperty() {
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

	public ObjectProperty<Date> getDobProperty() {
		return dobProperty;
	}
	
	public PatientData getPatient() {
		String email = this.contactEmailProperty.getValue();
		String phone = this.contactPhoneProperty.getValue();
		String dob = this.dobProperty.getValue().toString();
		String fname = this.firstNameProperty.getValue();
		String lname = this.lastNameProperty.getValue();
		String middleInitial = this.middleInitialProperty.getValue();
		String ssn = this.ssnProperty.getValue();
		
		Person person = new Person(email, phone, Date.valueOf(dob), fname, lname, middleInitial, ssn);
		
		String street1 = this.streetAddress1Property.getValue();
		String street2 = this.streetAddress2Property.getValue();
		street2 = street2 == null ? "" : street2;
		String state = this.stateProperty.getValue();
		Integer zip = Integer.parseInt(this.zipCodeProperty.getValue());
		
		Address addr = new Address(street1, street2, state, zip);
		
		return new PatientData(person, addr);
		
	}
	
	public BooleanProperty getAddEventProperty() {
		return this.addEventProperty;
	}

	public StringProperty getActionTextProperty() {
		return actionTextProperty;
	}

	public void pull(PatientData data) {
		Person person = data.getPerson();
		this.contactEmailProperty.set(person.getContact_email());
		this.contactPhoneProperty.set(person.getContact_phone());
		this.dobProperty.set(person.getDOB());
		this.firstNameProperty.set(person.getFname());
		this.lastNameProperty.set(person.getLname());
		this.middleInitialProperty.set(person.getMiddle_initial());
		this.ssnProperty.set(String.format("%09d", person.getSSN()));

		Address addr = data.getAddress();
		this.streetAddress1Property.set(addr.getStreet_address1());
		this.streetAddress2Property.set(addr.getStreet_address2());
		this.stateProperty.set(addr.getState());
		this.zipCodeProperty.set(String.format("%05d", addr.getZip_code()));
	}
}
