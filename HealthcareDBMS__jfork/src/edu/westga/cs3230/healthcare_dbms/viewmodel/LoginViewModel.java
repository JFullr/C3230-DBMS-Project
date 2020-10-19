package edu.westga.cs3230.healthcare_dbms.viewmodel;

import edu.westga.cs3230.healthcare_dbms.model.Login;
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
public class LoginViewModel {
	private final StringProperty nameProperty;
	private final StringProperty passwordProperty;
	
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
	
	public Login getLogin() {
		String userName = this.nameProperty.getValue();
		String password = this.passwordProperty.getValue();
		return new Login(userName, password);
	}

	public BooleanProperty getLoginButtonPressed() {
		return loginButtonPressed;
	}

}
