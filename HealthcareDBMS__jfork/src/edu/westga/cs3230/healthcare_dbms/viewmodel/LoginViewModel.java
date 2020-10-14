package edu.westga.cs3230.healthcare_dbms.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Viewmodel class for the Login window.
 */
public class LoginViewModel {
	private final StringProperty nameProperty;
	private final StringProperty passwordProperty;

	/**
	 * Instantiates a new LoginViewModel.
	 */
	public LoginViewModel() {		
		this.nameProperty = new SimpleStringProperty();
		this.passwordProperty = new SimpleStringProperty();
	}
	
	/**
	 * Gets the name property.
	 *
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return the name property
	 */
	public StringProperty getNameProperty() {
		return this.nameProperty;
	}
	
	/**
	 * Gets the password property.
	 *
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return the password property
	 */
	public StringProperty getPasswordProperty() {
		return this.passwordProperty;
	}

}
