package edu.westga.cs3230.healthcare_dbms.view;

import edu.westga.cs3230.healthcare_dbms.io.HealthcareIoConstants;
import edu.westga.cs3230.healthcare_dbms.io.database.HealthcareDatabase;
import edu.westga.cs3230.healthcare_dbms.io.database.QueryResult;
import edu.westga.cs3230.healthcare_dbms.utils.TimeSelections;
import edu.westga.cs3230.healthcare_dbms.view.embed.TupleEmbed;
import edu.westga.cs3230.healthcare_dbms.view.utils.FXMLAlert;
import edu.westga.cs3230.healthcare_dbms.view.utils.FXMLWindow;
import edu.westga.cs3230.healthcare_dbms.viewmodel.AppointmentCheckupViewModel;
import edu.westga.cs3230.healthcare_dbms.viewmodel.AppointmentViewModel;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
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
	private Button addCheckupDetailsButton;

	private AppointmentViewModel viewModel;
	private HealthcareDatabase database;

	public AppointmentCodeBehind() {
		this.viewModel = new AppointmentViewModel();
	}

	/**
	 * Initializer for the fxml data
	 */
	@FXML
	public void initialize() {
		this.bindProperties();
		
		this.hourPicker.setItems(FXCollections.observableArrayList(TimeSelections.ALL_HOURS));
		this.minutePicker.setItems(FXCollections.observableArrayList(TimeSelections.ALL_MINUTES));
		this.diurnalPicker.setItems(FXCollections.observableArrayList(TimeSelections.ALL_DIURNALS));
		this.addCheckupDetailsButton.disableProperty().bind(this.viewModel.getIsUpdateProperty().not());
		
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
		
		this.viewModel.getTupleSelectionProperty().bindBidirectional(this.tupleDisplay.selectionModelProperty());
		
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

	public void onAddCheckupDetails(ActionEvent actionEvent) {
		// TODO Auto-generated method stub
		try {
			FXMLWindow window = new FXMLWindow(HealthcareIoConstants.APPOINTMENT_CHECKUP_URL, "Add Checkup Details", true);
			AppointmentCheckupGuiCodeBehind codeBehind = (AppointmentCheckupGuiCodeBehind) window.getController();
			AppointmentCheckupViewModel viewModel = codeBehind.getViewModel();
			viewModel.setAppointment(this.viewModel.getExistingAppointmentProperty().get());

			viewModel.getActionPressedProperty().addListener((evt) -> {

				if (viewModel.getActionPressedProperty().getValue()) {
					QueryResult result = this.database.attemptAddAppointmentCheckup(viewModel.getObject());
					if (result != null && !result.getTuple().getAttributes().isEmpty()) {
						FXMLAlert.statusAlert("Added Checkup Status", "Checkup status added", "Added Checkup Status", Alert.AlertType.INFORMATION);
						viewModel.getActionPressedProperty().setValue(false);
					} else {
						FXMLAlert.statusAlert("Checkup Status Failed", "Checkup status could not be added", "Checkup Status Failed", Alert.AlertType.ERROR);
						codeBehind.closeWindow();
					}
				}

			});
			window.pack();
			window.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setDatabase(HealthcareDatabase database) {
		this.database = database;
	}
}
