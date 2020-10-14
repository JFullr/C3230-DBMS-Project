package edu.westga.cs3230.healthcare_dbms.view;

import java.util.ArrayList;

import edu.westga.cs3230.healthcare_dbms.io.database.QueryResult;
import edu.westga.cs3230.healthcare_dbms.model.Login;
import edu.westga.cs3230.healthcare_dbms.model.Person;
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
	private static final String DB_URL = "jdbc:mysql://160.10.25.16:3306/cs3230f20i?user=jfulle11&password=9j.3pwB@B4&serverTimezone=EST";

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
				
				AddPatientViewModel patientData = codeBehind.getViewModel();
				
				if (codeBehind.isAttemptingAdd()) {
					if (!this.attemptAddPatient(patientData)) {
						FXMLAlert.statusAlert("Add Patient Status", "Patient SSN already exists in the database.", "Add Patient Failed", AlertType.ERROR);
					} else {
						FXMLAlert.statusAlert("Add Patient Status", "Added patient Successfully", AlertType.INFORMATION);
						///Later iteration: this.updateLoginDisplay();
						this.handleUpdateQueryListView();
					}
				}

			});
			window.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private boolean doLogin(LoginViewModel data) {

		Login login = data.getLogin();

		if(!this.viewModel.attemptLogin(login)) {
			return false;
		}
		
		ArrayList<QueryResult> results = this.viewModel.getLastResults();
		if (results == null || results.size() != 1) {
			return false;
		}
		
		this.viewModel.getLoggedInProperty().setValue(true);

		return true;
	}
	
	private boolean attemptAddPatient(AddPatientViewModel data) {
		
		Person patient = data.getPatient();
		return this.viewModel.attemptAddPatient(patient);
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
