package edu.westga.cs3230.healthcare_dbms.view;

import edu.westga.cs3230.healthcare_dbms.io.HealthcareIoConstants;
import edu.westga.cs3230.healthcare_dbms.utils.Genders;
import edu.westga.cs3230.healthcare_dbms.utils.States;
import edu.westga.cs3230.healthcare_dbms.utils.TimeSelections;
import edu.westga.cs3230.healthcare_dbms.view.embed.TupleEmbed;
import edu.westga.cs3230.healthcare_dbms.view.utils.FXMLWindow;
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

public class AppointmentCodeBehind {

	@FXML
	private Button actionButton;

	@FXML
	private Button cancelButton;

	@FXML
	private DatePicker datePicker;

	@FXML
	private ComboBox<String> hourPicker;

	@FXML
	private ComboBox<String> minutePicker;

	@FXML
	private ComboBox<String> diurnalPicker;

	@FXML
	private ListView<TupleEmbed> tupleDisplay;

	@FXML
	private FlowPane searchHolder;

	private AppointmentViewModel viewModel;

	public AppointmentCodeBehind() {
		this.viewModel = new AppointmentViewModel();
	}

	/**
	 * Initializer for the fxml data
	 */
	@FXML
	public void initialize() {
		this.bindProperties();
		
		FXMLWindow window;
		try {
			window = new FXMLWindow(HealthcareIoConstants.PATIENT_GUI_URL, "Search Patient", true);
			PatientCodeBehind codeBehind = (PatientCodeBehind) window.getController();
			PatientViewModel viewModel = codeBehind.getViewModel();
			viewModel.setActionButtonText("Search Patient");
			viewModel.setActionButtonValidationMinimal();
			viewModel.setCloseButtonDisabled();
			this.searchHolder.getChildren().add(window.getNode());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.hourPicker.setItems(FXCollections.observableArrayList(TimeSelections.ALL_HOURS));
		this.minutePicker.setItems(FXCollections.observableArrayList(TimeSelections.ALL_MINUTES));
		this.diurnalPicker.setItems(FXCollections.observableArrayList(TimeSelections.ALL_DIURNALS));
		
		this.setupTupleView();
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

	public AppointmentViewModel getViewModel() {
		return this.viewModel;
	}

	private void bindProperties() {
		
		this.viewModel.getActionTextProperty().bindBidirectional(this.actionButton.textProperty());
		
		this.viewModel.getDateProperty().bindBidirectional(this.datePicker.valueProperty());
		
		this.viewModel.getHourProperty().bindBidirectional(this.hourPicker.selectionModelProperty());
		this.viewModel.getMinuteProperty().bindBidirectional(this.minutePicker.selectionModelProperty());
		this.viewModel.getDiurnalProperty().bindBidirectional(this.diurnalPicker.selectionModelProperty());
	
		this.actionButton.disableProperty().bind(
				this.datePicker.valueProperty().isNull()
				.or(this.hourPicker.getSelectionModel().selectedItemProperty().isNull())
				.or(this.minutePicker.getSelectionModel().selectedItemProperty().isNull())
				.or(this.diurnalPicker.getSelectionModel().selectedItemProperty().isNull())
				.or(this.tupleDisplay.getSelectionModel().selectedItemProperty().isNull())
				);
	}
	
	private void setupTupleView() {
		
		this.tupleDisplay.selectionModelProperty().addListener((evt)->{
			this.tupleDisplay.refresh();
		});
		this.tupleDisplay.setItems(this.viewModel.getTupleList());
		this.tupleDisplay.setPadding(new Insets(0,0,0,0));
		this.tupleDisplay.setFixedCellSize(100.0);
		this.tupleDisplay.selectionModelProperty().addListener((evt)->{
			this.tupleDisplay.refresh();
		});
		
		this.tupleDisplay.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			//TODO custom actions on selections
			if (oldValue != newValue && oldValue != null) {
				oldValue.setMouseTransparent(true);
			}
			if (newValue != null) {
				newValue.setMouseTransparent(false);
			}
		});
		//this.tupleDisplay.setFocusModel(new FocusModel());
	}

}
