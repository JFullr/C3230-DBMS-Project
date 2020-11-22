package edu.westga.cs3230.healthcare_dbms.view;

import edu.westga.cs3230.healthcare_dbms.viewmodel.LoginViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Code behind of the login window.
 *
 * @author Joseph Fuller and Andrew Steinborn
 */
public class LoginCodeBehind {

    /** The login button. */
    @FXML
    private Button loginButton;

    /** The cancel button. */
    @FXML
    private Button cancelButton;

    /** The name text field. */
    @FXML
    private TextField nameTextField;

    /** The password field. */
    @FXML
    private PasswordField passwordField;
    
    /** The view model. */
    private LoginViewModel viewModel;
    
    /**
     * Instantiates a new login code behind.
     */
    public LoginCodeBehind() {
		this.viewModel = new LoginViewModel();
    }
    
    /**
     * Initializer for the fxml data.
     */
	@FXML
	public void initialize() {
		this.bindProperties();
	}
	
    /**
     * Close window.
     *
     * @param event the event
     */
    @FXML
    public void closeWindow(ActionEvent event) {
    	Stage stage = (Stage) this.cancelButton.getScene().getWindow();
	    stage.close();
    }

    /**
     * Login close window.
     *
     * @param event the event
     */
    @FXML
    public void loginCloseWindow(ActionEvent event) {
    	
		this.viewModel.getLoginButtonPressed().setValue(true);
		this.viewModel.getLoginButtonPressed().setValue(false);
    	
    }
    
    /**
     * Gets the view model.
     *
     * @return the view model
     */
    public LoginViewModel getViewModel() {
    	return this.viewModel;
    }
    
    /**
     * Bind properties.
     */
    private void bindProperties() {
		this.viewModel.getNameProperty().bindBidirectional(this.nameTextField.textProperty());
		this.viewModel.getPasswordProperty().bindBidirectional(this.passwordField.textProperty());
	}

}
