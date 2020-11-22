package edu.westga.cs3230.healthcare_dbms.view;

import edu.westga.cs3230.healthcare_dbms.view.embed.TupleEmbed;
import edu.westga.cs3230.healthcare_dbms.viewmodel.MainPageViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;

// TODO: Auto-generated Javadoc
/**
 * Code-behind file for the main page for the Healthcare DBMS.
 *
 * @author 
 */
public class MainPageCodeBehind {

	/** The Constant DB_URL. */
	private static final String DB_URL = "jdbc:mysql://160.10.25.16:3306/cs3230f20i?user=jfulle11&password=9j.3pwB@B4&serverTimezone=EST&noAccessToProcedureBodies=true";
	
	/** The patient search button. */
	@FXML
    private Button patientSearchButton;
	
	/** The login button. */
	@FXML
    private Button loginButton;

    /** The logout button. */
    @FXML
    private Button logoutButton;

    /** The username label. */
    @FXML
    private Label usernameLabel;

    /** The name label. */
    @FXML
    private Label nameLabel;

    /** The user id label. */
    @FXML
    private Label userIdLabel;
    
    /** The add patient button. */
    @FXML
    private Button addPatientButton;

	/** The query list view. */
	@FXML
	private ListView<TupleEmbed> queryListView;
	
	/** The view model. */
	private MainPageViewModel viewModel;

	/**
	 * Initializer for the fxml data.
	 */
	@FXML
	public void initialize() {

		this.usernameLabel.textProperty().bindBidirectional(this.viewModel.getViewModelMain().getUserNameProperty());
		this.nameLabel.textProperty().bindBidirectional(this.viewModel.getViewModelMain().getNameProperty());
		this.userIdLabel.textProperty().bindBidirectional(this.viewModel.getViewModelMain().getUserIdProperty());
		
		this.logoutButton.disableProperty().bind(this.viewModel.getLoggedInProperty().not());
		this.patientSearchButton.disableProperty().bind(this.viewModel.getLoggedInProperty().not());
		
		this.addPatientButton.disableProperty().bind(this.viewModel.getLoggedInProperty().not());

		this.addListeners();
		
		this.setupTupleView();
		
		this.setupAdminTab();
		
	}

	/**
	 * Instantiates a new MainPageCodeBehind.
	 */
	public MainPageCodeBehind() {
		this.viewModel = new MainPageViewModel(DB_URL);
	}
	
	/**
	 * Handle log out.
	 *
	 * @param event the event
	 */
	@FXML
    public void handleLogOut(ActionEvent event) {
		
		this.viewModel.handleLogOut();	
	}
	
	/**
	 * Handle patient search.
	 *
	 * @param event the event
	 */
	@FXML
    public void handlePatientSearch(ActionEvent event) {
		this.viewModel.getViewModelMain().showPatientSearch();
    }
	
	/**
	 * Handle open login view.
	 *
	 * @param event the event
	 */
	@FXML
	public void handleOpenLoginView(ActionEvent event) {
		this.viewModel.showLogin();
	}
	
	/**
	 * Handle add patient.
	 *
	 * @param event the event
	 */
	@FXML
	public void handleAddPatient(ActionEvent event) {

		this.viewModel.getViewModelMain().showAddPatient();
	}
	
	/**
	 * Update login display.
	 */
	public void updateLoginDisplay() {
		this.viewModel.updateLoginDisplay();
	}
	
	
	/**
	 * Adds the listeners.
	 */
	private void addListeners() {
		this.queryListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (oldValue != newValue && oldValue != null) {
				oldValue.setMouseTransparent(true);
			}
			if (newValue != null) {
				newValue.setMouseTransparent(false);
			}
		});
	}
	
	/**
	 * Setup tuple view.
	 */
	private void setupTupleView() {
		this.queryListView.setItems(this.viewModel.getViewModelMain().getTupleList());
		this.queryListView.setPadding(new Insets(0,0,0,0));
		this.queryListView.setFixedCellSize(100.0);
		this.queryListView.selectionModelProperty().addListener((evt)->{
			this.queryListView.refresh();
		});
	}
	
	
	
	
	/** The admin tab. */
	@FXML
    private Tab adminTab;

	/** The default tab. */
	@FXML
    private Tab defaultTab;
	
	/** The user pane. */
	@FXML
    private TabPane userPane;
	
    /** The admin username label. */
    @FXML
    private Label adminUsernameLabel;

    /** The admin name lablel. */
    @FXML
    private Label adminNameLablel;

    /** The admin user id label. */
    @FXML
    private Label adminUserIdLabel;
    
    /** The admin call query button. */
    @FXML
    private Button adminCallQueryButton;
    
    /** The admin date search button. */
    @FXML
    private Button adminDateSearchButton;

    /** The admin start date. */
    @FXML
    private DatePicker adminStartDate;

    /** The admin end date. */
    @FXML
    private DatePicker adminEndDate;

    /** The admin result list. */
    @FXML
    private ListView<TupleEmbed> adminResultList;
    
    /** The admin query area. */
    @FXML
    private TextArea adminQueryArea;
    
    /**
     * Setup admin tab.
     */
    private void setupAdminTab() {
    	this.adminTab.disableProperty().bind(this.viewModel.getAdminLoggedInProperty().not());
    	this.adminUsernameLabel.textProperty().bindBidirectional(this.viewModel.getViewModelMain().getUserNameProperty());
		this.adminNameLablel.textProperty().bindBidirectional(this.viewModel.getViewModelMain().getNameProperty());
		this.adminUserIdLabel.textProperty().bindBidirectional(this.viewModel.getViewModelMain().getUserIdProperty());
		
		this.adminDateSearchButton.disableProperty().bind(
			this.adminStartDate.valueProperty().isNull()
			.or(this.adminEndDate.valueProperty().isNull())
		);
		
		this.adminCallQueryButton.disableProperty().bind(this.adminQueryArea.textProperty().isEmpty());
		
		this.adminQueryArea.textProperty().bindBidirectional(this.viewModel.getViewModelAdmin().getAdminQueryProperty());
		this.adminStartDate.valueProperty().bindBidirectional(this.viewModel.getViewModelAdmin().getAdminStartDateProperty());
		this.adminEndDate.valueProperty().bindBidirectional(this.viewModel.getViewModelAdmin().getAdminEndDateProperty());
		this.adminResultList.setItems(this.viewModel.getViewModelAdmin().getAdminResultList());
		
		this.adminResultList.setPadding(new Insets(0,0,0,0));
		this.adminResultList.setFixedCellSize(100.0);
		this.adminResultList.selectionModelProperty().addListener((evt)->{
			this.adminResultList.refresh();
		});
    }
    
    /**
     * Handle admin log out.
     *
     * @param event the event
     */
    @FXML
    public void handleAdminLogOut(ActionEvent event) {
		
    	this.userPane.getSelectionModel().select(0);
		this.viewModel.handleLogOut();	
	}
    
    /**
     * Handle call query.
     *
     * @param event the event
     */
    @FXML
    public void handleCallQuery(ActionEvent event) {
    	this.viewModel.getViewModelAdmin().handleAdminQuery();
    }

    /**
     * Handle date search.
     *
     * @param event the event
     */
    @FXML
    public void handleDateSearch(ActionEvent event) {
    	this.viewModel.getViewModelAdmin().handleAdminDateSearch();
    }


}
