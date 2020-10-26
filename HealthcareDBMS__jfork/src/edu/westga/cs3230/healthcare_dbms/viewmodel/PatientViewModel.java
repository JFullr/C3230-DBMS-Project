package edu.westga.cs3230.healthcare_dbms.viewmodel;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import edu.westga.cs3230.healthcare_dbms.model.Address;
import edu.westga.cs3230.healthcare_dbms.model.PatientData;
import edu.westga.cs3230.healthcare_dbms.model.Person;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.SingleSelectionModel;

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
	private final StringProperty zipCodeProperty;
	private final ObjectProperty<LocalDate> dobProperty;
	private final StringProperty middleInitialProperty;
	private final StringProperty ssnProperty;
	
	private final StringProperty actionTextProperty;
	
	private final BooleanProperty addEventProperty;
	
	private final ObjectProperty<SingleSelectionModel<String>> stateProperty;
	private final ObjectProperty<SingleSelectionModel<String>> genderProperty;
	
	

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
		this.zipCodeProperty = new SimpleStringProperty();
		this.dobProperty = new SimpleObjectProperty<LocalDate>();
		this.middleInitialProperty = new SimpleStringProperty();
		this.ssnProperty = new SimpleStringProperty();
		this.addEventProperty = new SimpleBooleanProperty(false);
		this.actionTextProperty = new SimpleStringProperty();
		this.stateProperty = new SimpleObjectProperty<SingleSelectionModel<String>>();
		this.genderProperty = new SimpleObjectProperty<SingleSelectionModel<String>>();
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
	
	public PatientData getPatient() {
		String email = this.contactEmailProperty.getValue();
		String phone = this.contactPhoneProperty.getValue();

		Date dob = java.util.Date.from(this.dobProperty.get().atStartOfDay(ZoneId.of("UTC")).toInstant());
		String fname = this.firstNameProperty.getValue();
		String lname = this.lastNameProperty.getValue();
		String middleInitial = this.middleInitialProperty.getValue();
		String ssn = this.ssnProperty.getValue();
		String gender = this.genderProperty.getValue().getSelectedItem();
		
		Person person = new Person(email, phone, new java.sql.Date(dob.getTime()), fname, lname, middleInitial, gender, ssn);
		
		String street1 = this.streetAddress1Property.getValue();
		String street2 = this.streetAddress2Property.getValue();
		street2 = street2 == null ? "" : street2;
		String state = this.stateProperty.getValue().getSelectedItem();
		String city = this.cityProperty.getValue();
		Integer zip = Integer.parseInt(this.zipCodeProperty.getValue());
		
		Address addr = new Address(street1, street2, city, state, zip);
		
		return new PatientData(person, addr);
		
	}
	
	public BooleanProperty getAddEventProperty() {
		return this.addEventProperty;
	}

	public StringProperty getActionTextProperty() {
		return actionTextProperty;
	}

	public void initFrom(PatientData data) {
		Person person = data.getPerson();
		this.contactEmailProperty.set(nullToEmpty(person.getContact_email()));
		this.contactPhoneProperty.set(nullToEmpty(person.getContact_phone()));
		this.dobProperty.set(new java.util.Date(person.getDOB().getTime()).toInstant().atZone(ZoneId.of("UTC")).toLocalDate());
		this.firstNameProperty.set(nullToEmpty(person.getFname()));
		this.lastNameProperty.set(nullToEmpty(person.getLname()));
		this.middleInitialProperty.set(nullToEmpty(person.getMiddle_initial()));
		this.ssnProperty.set(String.format("%09d", person.getSSN()));
		//this.genderProperty.set(person.getGender());

		Address addr = data.getAddress();
		this.streetAddress1Property.set(nullToEmpty(addr.getStreet_address1()));
		this.streetAddress2Property.set(nullToEmpty(addr.getStreet_address2()));
		//this.stateProperty.set(addr.getState());
		this.cityProperty.set(nullToEmpty(addr.getCity()));
		this.zipCodeProperty.set(String.format("%05d", addr.getZip_code()));
		
		this.genderProperty.getValue().select(person.getGender());
		this.stateProperty.getValue().select(addr.getState());
		
	}

	private String nullToEmpty(String str) {
		return str == null ? "" : str;
	}
}
