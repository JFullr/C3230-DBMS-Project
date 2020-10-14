package edu.westga.cs3230.healthcare_dbms.view;

import java.util.ArrayList;

import edu.westga.cs3230.healthcare_dbms.io.database.QueryResult;
import edu.westga.cs3230.healthcare_dbms.utils.ExceptionText;
import edu.westga.cs3230.healthcare_dbms.view.embed.Embed;
import edu.westga.cs3230.healthcare_dbms.view.embed.EmbedHandler;
import edu.westga.cs3230.healthcare_dbms.view.utils.FXMLAlert;
import edu.westga.cs3230.healthcare_dbms.view.utils.FXMLWindow;
import edu.westga.cs3230.healthcare_dbms.viewmodel.AddPatientViewModel;
import edu.westga.cs3230.healthcare_dbms.viewmodel.LoginViewModel;
import edu.westga.cs3230.healthcare_dbms.viewmodel.MainPageViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
	private static final String ADD_GUI = "AddPatientGui.fxml";
	private static final String DB_URL = "";

	@FXML
    private Button loginButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label userIdLabel;

    @FXML
    private Button addPatientButton;

	@FXML
	private ListView<Embed> queryListView;

	private EmbedHandler embedHandler;
	private MainPageViewModel viewModel;

	/**
	 * Initializer for the fxml data
	 */
	@FXML
	public void initialize() {

		// TODO embed for tuples for future iterations
		//this.queryListView.itemsProperty().bindBidirectional(this.embedHandler.getDisplayedQueryEmbeds());
		this.usernameLabel.textProperty().bindBidirectional(this.viewModel.getUserNameProperty());
		this.nameLabel.textProperty().bindBidirectional(this.viewModel.getNameProperty());
		this.userIdLabel.textProperty().bindBidirectional(this.viewModel.getUserIdProperty());
		
		this.addPatientButton.disableProperty().bind(this.viewModel.getLoggedInProperty().not());
		this.logoutButton.disableProperty().bind(this.viewModel.getLoggedInProperty().not());

		this.addListeners();
	}

	/**
	 * Instantiates a new MainPageCodeBehind.
	 */
	public MainPageCodeBehind() {
		this.viewModel = new MainPageViewModel(DB_URL);
		this.viewModel.loadDataFromDatabase();
		this.embedHandler = new EmbedHandler(this.viewModel.getQueryStorage());
	}
	
	@FXML
    public void handleLogOut(ActionEvent event) {
		
		///TODO clear tuples later
		this.viewModel.handleLogOut();
		
    }
	
	public void updateLoginDisplay() {
		this.viewModel.updateLoginDisplay();
	}
	
	public void handleUpdateQueryListView() {
		// TODO update from future query buttons
		this.embedHandler.updateQueryEmbeds();
	}
	
	@FXML
	public void handleOpenLoginView(ActionEvent event) {

		try {
			FXMLWindow window = new FXMLWindow(getClass().getResource(LOGIN_GUI), "Healthcare Login", true);
			LoginCodeBehind codeBehind = (LoginCodeBehind) window.getController();
			window.setOnWindowClose((winEvent) -> {

				LoginViewModel loginData = codeBehind.getViewModel();

				if (codeBehind.isAttemptingLogin()) {
					if (!this.doLogin(loginData)) {
						FXMLAlert.statusAlert("Login Status", ExceptionText.FAILED_LOGIN, "Login Failed", AlertType.ERROR);
						
					} else {
						FXMLAlert.statusAlert("Login Successful", AlertType.INFORMATION);
						this.updateLoginDisplay();
					}
				}

			});
			window.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void handleAddPatient(ActionEvent event) {

		try {
			FXMLWindow window = new FXMLWindow(getClass().getResource(ADD_GUI), "Add Patient", true);
			AddPatientCodeBehind codeBehind = (AddPatientCodeBehind) window.getController();
			window.setOnWindowClose((winEvent) -> {
				
				//codeBehind.setShortDbConnection(this)
				
				AddPatientViewModel patientData = codeBehind.getViewModel();
				
				/*
				if (codeBehind.isAttemptingLogin()) {
					if (!this.addPatient(patientData)) {
						FXMLAlert.statusAlert("Login Status", ExceptionText.FAILED_LOGIN, "Login Failed", AlertType.ERROR);
					} else {
						FXMLAlert.statusAlert("Login Successful", AlertType.INFORMATION);
						this.updateLoginDisplay();
					}
				}
				/*/
				if (codeBehind.isAttemptingAdd()) {
					if (!this.attemptAddPatient(patientData)) {
						FXMLAlert.statusAlert("Login Status", "Patient SSN already exists in the database.", "Add Patient Failed", AlertType.ERROR);
					} else {
						FXMLAlert.statusAlert("Add Patient Status", "Added patient Successfully", AlertType.INFORMATION);
						///Eventually this.updateLoginDisplay();
						this.handleUpdateQueryListView();
					}
				}
				//*/

			});
			window.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private boolean doLogin(LoginViewModel data) {

		String username = data.getNameProperty().getValue();
		String password = data.getPasswordProperty().getValue();

		if(!this.viewModel.attemptLogin(username, password)) {
			return false;
		}
		
		ArrayList<QueryResult> results = this.viewModel.getLastResults();
		if (results == null || results.size() != 1) {
			return false;
		}

		return true;
	}
	
	private boolean attemptAddPatient(AddPatientViewModel data) {
		
		String email = data.getContactEmailProperty().getValue();
		String phone = data.getContactPhoneProperty().getValue();
		String dob = data.getDobProperty().getValue().toString();
		String fname = data.getFirstNameProperty().getValue();
		String lname = data.getLastNameProperty().getValue();
		String address = data.getMailingAddressProperty().getValue();
		String middleInitial = data.getMiddleInitialProperty().getValue();
		String ssn = data.getSsnProperty().getValue();
		
		if(!this.viewModel.attemptAddPatient(email, phone, dob, fname, lname, address, middleInitial, ssn)) {
			return false;
		}
		
		ArrayList<QueryResult> results = this.viewModel.getLastResults();
		if (results == null || results.size() > 0) {
			return false;
		}
		
		return false;
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

}
