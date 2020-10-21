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
public class AddPatientViewModel {

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
	public AddPatientViewModel() {
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
	
	public Person getPatient() {
		String email = this.contactEmailProperty.getValue();
		String phone = this.contactPhoneProperty.getValue();
		String dob = this.dobProperty.getValue().toString();
		String fname = this.firstNameProperty.getValue();
		String lname = this.lastNameProperty.getValue();
		String address = ""; // TODO
		String middleInitial = this.middleInitialProperty.getValue();
		String ssn = this.ssnProperty.getValue();
		
		return new Person(email, phone, Date.valueOf(dob), fname, lname, 
				address, middleInitial, ssn);
		
	}
	
	public BooleanProperty getAddEventProperty() {
		return this.addEventProperty;
	}

	public StringProperty getActionTextProperty() {
		return actionTextProperty;
	}

	
}
