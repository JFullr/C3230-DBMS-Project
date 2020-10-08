package edu.westga.cs3230.healthcare_dbms.view;

import edu.westga.cs3230.healthcare_dbms.model.HealthcareQueryResult;
import edu.westga.cs3230.healthcare_dbms.utils.ExceptionText;
import edu.westga.cs3230.healthcare_dbms.view.embed.HealthcareEmbed;
import edu.westga.cs3230.healthcare_dbms.view.embed.HealthcareEmbedHandler;
import edu.westga.cs3230.healthcare_dbms.view.utils.FXMLWindow;
import edu.westga.cs3230.healthcare_dbms.viewmodel.LoginViewModel;
import edu.westga.cs3230.healthcare_dbms.viewmodel.MainPageViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

/**
 * Code-behind file for the main page for the Healthcare DBMS
 * 
 * @author
 */
public class MainPageCodeBehind {

	private static final String LOGIN_GUI = "LoginGui.fxml";

	@FXML
	private Button loginButton;

	@FXML
	private Label usernameLabel;

	@FXML
	private Label usertypeLabel;

	@FXML
	private ListView<HealthcareEmbed> queryListView;
	
	private HealthcareEmbedHandler embedHandler;
	private MainPageViewModel viewModel;

	/**
	 * Initializer for the fxml data
	 */
	@FXML
	public void initialize() {
		
		//FIXME
		///this.queryListView.itemsProperty().bindBidirectional(this.embedHandler.getDisplayedQueryEmbeds());

		this.addListeners();
	}

	/**
	 * Instantiates a new MainPageCodeBehind.
	 */
	public MainPageCodeBehind() {
		this.viewModel = new MainPageViewModel();
		this.viewModel.loadDataFromDatabase();
		this.embedHandler = new HealthcareEmbedHandler(this.viewModel.getQueryStorage());
	}

	@FXML
	void handleOpenLoginView(ActionEvent event) {

		try {
			FXMLWindow window = new FXMLWindow(getClass().getResource(LOGIN_GUI), "Healthcare Login", true);
			LoginCodeBehind codeBehind = (LoginCodeBehind) window.getController();
			window.setOnWindowClose((winEvent) -> {
				
				LoginViewModel loginData = codeBehind.getViewModel();
				
				if (codeBehind.isAttemptingLogin()) {
					///TODO do not close window if failed to login
			    	/*TODO do login check with embedHandler*/
					///embedHandler.doLogin()
			    	if(!this.doLogin(loginData)) {
				    	///TODO send and check database
				    	///if login failure
				    	Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Login Failed");
						alert.setContentText(ExceptionText.FAILED_LOGIN);
						alert.showAndWait();
			    	} else {
			    		this.addStatusAlert("Login Successful", AlertType.INFORMATION);
			    		// TODO update login field to display name and user type since the login
						// succeeded
						// TODO pull data from the query buffer due to query of user type
						// returned
						this.updateLoginDisplay(loginData);
			    	}
				}
				
			});
			window.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public void handleUpdateQueryListView() {
		//TODO update from future query buttons
		this.embedHandler.updateQueryEmbeds();
	}
	
	public boolean doLogin(LoginViewModel data) {
		
		///TODO dummy escape handler, waiting on implementation of database
		int a = 42;
		if(a == 42) {
			///change to true to see action success
			return false;
		}
		
		String username = data.getNameProperty().getValue();
		String password = data.getPasswordProperty().getValue();
		
		///TODO use parameterized query later on, currently plaintext, no encryption
		this.viewModel.callQuery(null);//login query with username passed as PK to find
		
		//more than one user gotten illegal, failure
		if(this.viewModel.getLastResult().size() != 1) {
			return false;
		}
		
		HealthcareQueryResult loginResult = this.viewModel.getLastResult().get(0);
		String resultPassword = (String)loginResult.getTuples().get(0).get("password").getValue();
		
		return resultPassword.equals(resultPassword);
	}
	

	/**
	 * Updates the display with the login name, and user type
	 * 
	 */
	public void updateLoginDisplay(LoginViewModel data) {
		
		String username = data.getNameProperty().getValue();
		
		///TODO query from database with given username's person_id
		String userType = null;
		
		this.usernameLabel.setText(username);
		this.usertypeLabel.setText(userType);
	}

	private void addListeners() {
		this.queryListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (oldValue != newValue && oldValue != null) {
				oldValue.hidePane();
			}
			if (newValue != null) {
				newValue.showPane();
			}
		});
	}

	private void addStatusAlert(String message, AlertType icon) {
		Alert alert = new Alert(icon);
		alert.setTitle("Query Status");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

}
