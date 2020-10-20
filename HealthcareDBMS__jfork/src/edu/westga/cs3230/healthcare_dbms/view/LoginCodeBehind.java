package edu.westga.cs3230.healthcare_dbms.view;

import edu.westga.cs3230.healthcare_dbms.viewmodel.LoginViewModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
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
    private BooleanProperty attemptLogin;
    
    public LoginCodeBehind() {
		this.viewModel = new LoginViewModel();
		this.attemptLogin = new SimpleBooleanProperty(false);
    }
    
    /**
	 * Initializer for the fxml data
	 */
	@FXML
	public void initialize() {
		this.bindProperties();
	}
	
    @FXML
    public void closeWindow(ActionEvent event) {
    	Stage stage = (Stage) this.cancelButton.getScene().getWindow();
	    stage.close();
    }

    @FXML
    public void loginCloseWindow(ActionEvent event) {
    	
		this.viewModel.getLoginButtonPressed().setValue(true);
		this.viewModel.getLoginButtonPressed().setValue(false);
    	
    }
    
    public LoginViewModel getViewModel() {
    	return this.viewModel;
    }
    
    private void bindProperties() {
		this.viewModel.getNameProperty().bindBidirectional(this.nameTextField.textProperty());
		this.viewModel.getPasswordProperty().bindBidirectional(this.passwordField.textProperty());
		/*
		this.attemptLogin.bind(this.loginButton.pressedProperty());
		this.viewModel.getLoginButtonPressed().bind(this.attemptLogin);
		/*/
			//this.viewModel.getLoginButtonPressed().bind(this.loginButton.pressedProperty());
		//*/
	}

}
