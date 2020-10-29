package edu.westga.cs3230.healthcare_dbms.view;

import edu.westga.cs3230.healthcare_dbms.view.embed.EmbedHandler;
import edu.westga.cs3230.healthcare_dbms.view.embed.TupleEmbed;
import edu.westga.cs3230.healthcare_dbms.viewmodel.MainPageViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * Code-behind file for the main page for the Healthcare DBMS
 * 
 * @author
 */
public class MainPageCodeBehind {

	private static final String DB_URL = "jdbc:mysql://160.10.25.16:3306/cs3230f20i?user=jfulle11&password=9j.3pwB@B4&serverTimezone=EST";
	
	@FXML
    private Button patientSearchButton;
	
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
    private Button appointSearchButton;
    
    @FXML
    private Button createAppointButton;

	@FXML
	private ListView<TupleEmbed> queryListView;

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
		this.patientSearchButton.disableProperty().bind(this.viewModel.getLoggedInProperty().not());
		this.appointSearchButton.disableProperty().bind(this.viewModel.getLoggedInProperty().not());
		this.createAppointButton.disableProperty().bind(this.viewModel.getLoggedInProperty().not());

		this.addListeners();
		
		this.setupTupleView();
		
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
		
		this.viewModel.handleLogOut();	
	}
	
	@FXML
    public void handlePatientSearch(ActionEvent event) {
		this.viewModel.showPatientSearch();
    }
	
	@FXML
    void handleCreateAppointment(ActionEvent event) {
		this.viewModel.showCreateAppointment();
    }
	
	@FXML
	public void handleOpenLoginView(ActionEvent event) {
		this.viewModel.showLogin();
	}
	
	@FXML
	public void handleAddPatient(ActionEvent event) {

		this.viewModel.showAddPatient();
	}
	
	public void updateLoginDisplay() {
		this.viewModel.updateLoginDisplay();
	}
	
	public void handleUpdateQueryListView() {
		this.embedHandler.updateQueryEmbeds();
	}
	
	private void addListeners() {
		this.queryListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			//TODO custom actions on selections
			if (oldValue != newValue && oldValue != null) {
				oldValue.setMouseTransparent(true);
			}
			if (newValue != null) {
				newValue.setMouseTransparent(false);
				//System.out.println("CHANGED");
			}
		});
		/*
		this.queryListView.addEventHandler(MouseEvent.MOUSE_PRESSED, (e)->{
			System.out.println("MOUSE "+e.getTarget());
			if(this.queryListView.getSelectionModel().getSelectedItem() != null) {
				
				this.queryListView.getScene().getRoot().fireEvent(
						new MouseEvent(MouseEvent.MOUSE_PRESSED, e.getX(),
	                e.getY(), e.getSceneX(), e.getSceneY(), MouseButton.PRIMARY, 1, true, true, true, true,
	                true, true, true, true, true, true, null)
				);
			}
		});
		*/
	}
	
	private void setupTupleView() {
		this.queryListView.setItems(this.viewModel.getTupleList());
		this.queryListView.setPadding(new Insets(0,0,0,0));
		this.queryListView.setFixedCellSize(100.0);
		this.queryListView.selectionModelProperty().addListener((evt)->{
			this.queryListView.refresh();
		});
	}

}
