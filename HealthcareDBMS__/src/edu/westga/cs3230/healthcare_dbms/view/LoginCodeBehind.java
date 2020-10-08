package edu.westga.cs3230.healthcare_dbms.view;

import edu.westga.cs3230.healthcare_dbms.viewmodel.LoginViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginCodeBehind {

    @FXML
    private Button loginButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField nameTextField;

    @FXML
    private PasswordField passwordField;
    
    private LoginViewModel viewModel;
    private boolean attemptLogin;
    
    public LoginCodeBehind() {
		this.viewModel = new LoginViewModel();
    }
    
    /**
	 * Initializer for the fxml data
	 */
	@FXML
	public void initialize() {
		this.bindProperties();
		this.attemptLogin = false;
	}
	
    @FXML
    void closeWindow(ActionEvent event) {
    	Stage stage = (Stage) this.cancelButton.getScene().getWindow();
	    stage.close();
    }

    @FXML
    void loginCloseWindow(ActionEvent event) {
    	
		this.attemptLogin = true;
		this.closeWindow(event);
    	
    }
    
    public boolean isAttemptingLogin() {
    	return this.attemptLogin;
    }
    
    public LoginViewModel getViewModel() {
    	return this.viewModel;
    }
    
    private void bindProperties() {
		this.viewModel.getNameProperty().bindBidirectional(this.nameTextField.textProperty());
		this.viewModel.getPasswordProperty().bindBidirectional(this.passwordField.textProperty());
	}

}
