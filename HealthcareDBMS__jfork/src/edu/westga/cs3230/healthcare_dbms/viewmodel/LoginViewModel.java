package edu.westga.cs3230.healthcare_dbms.viewmodel;

import edu.westga.cs3230.healthcare_dbms.model.Login;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Viewmodel class for the Login window.
 *
 * @author Joseph Fuller and Andrew Steinborn
 */
public class LoginViewModel {
	
	/** The name property. */
	private final StringProperty nameProperty;
	
	/** The password property. */
	private final StringProperty passwordProperty;
	
	/** The login button pressed. */
	private final BooleanProperty loginButtonPressed;

	/**
	 * Instantiates a new LoginViewModel.
	 */
	public LoginViewModel() {		
		this.nameProperty = new SimpleStringProperty();
		this.passwordProperty = new SimpleStringProperty();
		
		this.loginButtonPressed = new SimpleBooleanProperty(false);
	}
	
	/**
	 * Gets the name property.
	 *
	 * @return the name property
	 * @precondition none
	 * @postcondition none
	 */
	public StringProperty getNameProperty() {
		return this.nameProperty;
	}
	
	/**
	 * Gets the password property.
	 *
	 * @return the password property
	 * @precondition none
	 * @postcondition none
	 */
	public StringProperty getPasswordProperty() {
		return this.passwordProperty;
	}
	
	/**
	 * Gets the login.
	 *
	 * @return the login
	 */
	public Login getLogin() {
		String userName = this.nameProperty.getValue();
		String password = this.passwordProperty.getValue();
		return new Login(userName, password);
	}

	/**
	 * Gets the login button pressed.
	 *
	 * @return the login button pressed
	 */
	public BooleanProperty getLoginButtonPressed() {
		return this.loginButtonPressed;
	}

}
