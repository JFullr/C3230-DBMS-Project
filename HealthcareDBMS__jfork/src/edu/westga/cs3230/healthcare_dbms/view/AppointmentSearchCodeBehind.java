package edu.westga.cs3230.healthcare_dbms.view;

import edu.westga.cs3230.healthcare_dbms.io.HealthcareIoConstants;
import edu.westga.cs3230.healthcare_dbms.utils.Genders;
import edu.westga.cs3230.healthcare_dbms.utils.States;
import edu.westga.cs3230.healthcare_dbms.utils.TimeSelections;
import edu.westga.cs3230.healthcare_dbms.view.embed.TupleEmbed;
import edu.westga.cs3230.healthcare_dbms.view.utils.FXMLWindow;
import edu.westga.cs3230.healthcare_dbms.viewmodel.AppointmentSearchViewModel;
import edu.westga.cs3230.healthcare_dbms.viewmodel.AppointmentViewModel;
import edu.westga.cs3230.healthcare_dbms.viewmodel.LoginViewModel;
import edu.westga.cs3230.healthcare_dbms.viewmodel.PatientViewModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.FocusModel;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class AppointmentSearchCodeBehind {

	@FXML
    private Button actionButton;

    @FXML
    private Button cancelButton;

    @FXML
    private ListView<TupleEmbed> patientList;

    @FXML
    private ListView<TupleEmbed> availableList;

    @FXML
    private ListView<TupleEmbed> pastList;

	private AppointmentSearchViewModel viewModel;

	public AppointmentSearchCodeBehind() {
		this.viewModel = new AppointmentSearchViewModel();
	}

	/**
	 * Initializer for the fxml data
	 */
	@FXML
	public void initialize() {
		this.bindProperties();
		
		this.setupPatientList();
		this.setupAvailableList();
		this.setupPastList();
	}

	@FXML
	public void closeWindow(ActionEvent event) {
		Stage stage = (Stage) this.cancelButton.getScene().getWindow();
		stage.close();
	}

	@FXML
	public void actionButtonHandler(ActionEvent event) {
		this.viewModel.getActionPressedProperty().setValue(true);
		this.viewModel.getActionPressedProperty().setValue(false);
	}

	public AppointmentSearchViewModel getViewModel() {
		return this.viewModel;
	}

	private void bindProperties() {
		
		this.viewModel.getPatientSelectionProperty().bindBidirectional(this.patientList.selectionModelProperty());
		this.viewModel.getAvailableSelectionProperty().bindBidirectional(this.availableList.selectionModelProperty());
		this.viewModel.getPastSelectionProperty().bindBidirectional(this.pastList.selectionModelProperty());
		
		this.viewModel.getActionTextProperty().bindBidirectional(this.actionButton.textProperty());
	}
	
	private void setupPatientList() {
		
		this.patientList.selectionModelProperty().addListener((evt)->{
			this.patientList.refresh();
		});
		this.patientList.setItems(this.viewModel.getPatientList());
		this.patientList.setPadding(new Insets(0,0,0,0));
		this.patientList.setFixedCellSize(100.0);
		
		this.patientList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (oldValue != newValue && oldValue != null) {
				oldValue.setMouseTransparent(true);
			}
			if (newValue != null) {
				newValue.setMouseTransparent(false);
			}
		});
	}
	
	private void setupAvailableList() {
		
		this.availableList.selectionModelProperty().addListener((evt)->{
			this.availableList.refresh();
		});
		this.availableList.setItems(this.viewModel.getAvailableList());
		this.availableList.setPadding(new Insets(0,0,0,0));
		this.availableList.setFixedCellSize(100.0);
		
		this.availableList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (oldValue != newValue && oldValue != null) {
				oldValue.setMouseTransparent(true);
			}
			if (newValue != null) {
				newValue.setMouseTransparent(false);
			}
		});
	}
	
	private void setupPastList() {
		
		this.pastList.selectionModelProperty().addListener((evt)->{
			this.pastList.refresh();
		});
		this.pastList.setItems(this.viewModel.getPastList());
		this.pastList.setPadding(new Insets(0,0,0,0));
		this.pastList.setFixedCellSize(100.0);
		
		this.pastList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (oldValue != newValue && oldValue != null) {
				oldValue.setMouseTransparent(true);
			}
			if (newValue != null) {
				newValue.setMouseTransparent(false);
			}
		});
	}
	
	

}
