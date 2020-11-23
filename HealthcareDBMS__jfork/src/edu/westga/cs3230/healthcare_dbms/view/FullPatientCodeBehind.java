package edu.westga.cs3230.healthcare_dbms.view;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import edu.westga.cs3230.healthcare_dbms.model.Appointment;
import edu.westga.cs3230.healthcare_dbms.utils.Genders;
import edu.westga.cs3230.healthcare_dbms.utils.States;
import edu.westga.cs3230.healthcare_dbms.utils.TimeSelections;
import edu.westga.cs3230.healthcare_dbms.view.embed.TupleEmbed;
import edu.westga.cs3230.healthcare_dbms.viewmodel.FullPatientViewModel;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.stage.Stage;

/**
 * Code behind for the patient window code.
 *
 * @author Joseph Fuller and Andrew Steinborn
 */
public class FullPatientCodeBehind {

	/** The Constant ACTION_VALID_ALL. */
	public static final String ACTION_VALID_ALL = "all";
	
	/** The Constant ACTION_VALID_MINIMAL. */
	public static final String ACTION_VALID_MINIMAL = "min";
	
	/** The Constant ACTION_VALID_NONE. */
	public static final String ACTION_VALID_NONE = "none";

	/** The action button. */
	@FXML
	private Button actionButton;

	/** The cancel button. */
	@FXML
	private Button cancelButton;

	/** The first name text field. */
	@FXML
	private TextField firstNameTextField;

	/** The last name text field. */
	@FXML
	private TextField lastNameTextField;

	/** The gender combo box. */
	@FXML
	private ComboBox<String> genderComboBox;

	/** The contact phone text field. */
	@FXML
	private TextField contactPhoneTextField;

	/** The contact email text field. */
	@FXML
	private TextField contactEmailTextField;

	/** The street address 1 text field. */
	@FXML
	private TextField streetAddress1TextField;

	/** The street address 2 text field. */
	@FXML
	private TextField streetAddress2TextField;

	/** The city text field. */
	@FXML
	private TextField cityTextField;

	/** The state combo box. */
	@FXML
	private ComboBox<String> stateComboBox;

	/** The zip code text field. */
	@FXML
	private TextField zipCodeTextField;

	/** The dob picker. */
	@FXML
	private DatePicker dobPicker;

	/** The middle initial text field. */
	@FXML
	private TextField middleInitialTextField;

	/** The ssn text field. */
	@FXML
	private TextField ssnTextField;

	/** The filter decimals. */
	private UnaryOperator<Change> filterDecimals = change -> {
		Pattern pattern = Pattern.compile("([.]\\d*)|(\\d*|\\d+\\.\\d*)");
		return pattern.matcher(change.getControlNewText()).matches() ? change : null;
	};

	/** The filter initial. */
	private UnaryOperator<Change> filterInitial = change -> {
		Pattern pattern = Pattern.compile("[a-zA-Z]?");
		return pattern.matcher(change.getControlNewText()).matches() ? change : null;
	};

	/** The filter SSN. */
	private UnaryOperator<Change> filterSSN = change -> {
		Pattern pattern = Pattern.compile("\\d{0,9}");
		return pattern.matcher(change.getControlNewText()).matches() ? change : null;
	};

	/** The filter phone. */
	private UnaryOperator<Change> filterPhone = change -> {
		Pattern pattern = Pattern.compile("\\d{0,10}");
		return pattern.matcher(change.getControlNewText()).matches() ? change : null;
	};

	/** The filter zip. */
	private UnaryOperator<Change> filterZip = change -> {
		Pattern pattern = Pattern.compile("\\d{0,5}");
		return pattern.matcher(change.getControlNewText()).matches() ? change : null;
	};
	
	/** The filter integral. */
	private UnaryOperator<Change> filterIntegral = change -> {
		Pattern pattern = Pattern.compile("\\d*");
		return pattern.matcher(change.getControlNewText()).matches() ? change : null;
	};

	/** The Constant emailValid. */
	private static final Pattern emailValid = Pattern
			.compile("[a-zA-Z]+(\\d|[a-zA-Z])*@[a-zA-Z]+(\\d|[a-zA-Z])*[.](\\d|[a-zA-Z])+");
	
	/** The is email valid. */
	private final BooleanProperty isEmailValid;

	/** The view model. */
	private FullPatientViewModel viewModel;
	
	
	/**
	 * Instantiates a new full patient code behind.
	 */
	public FullPatientCodeBehind() {
		this.viewModel = new FullPatientViewModel();
		this.isEmailValid = new SimpleBooleanProperty(false);
	}

	/**
	 * Initializer for the fxml data.
	 */
	@FXML
	public void initialize() {
		this.bindProperties();
		this.initializeTextFieldFormatters();
		
		this.setupAvailableList();
		this.setupPastList();
		
		this.setupApptTab();
		this.setupSubAppt();
		this.setupSubCheckup();
		this.setupSubTest();
		this.setupSubFinal();
		this.setupSubDiag();
	}

	/**
	 * Close window.
	 *
	 * @param event the event
	 */
	@FXML
	public void closeWindow(ActionEvent event) {
		Stage stage = (Stage) this.cancelButton.getScene().getWindow();
		stage.close();
	}

	/**
	 * Action button handler.
	 *
	 * @param event the event
	 */
	@FXML
	public void actionButtonHandler(ActionEvent event) {
		this.viewModel.getViewModelPatient().getActionPressedProperty().setValue(true);
		this.viewModel.getViewModelPatient().getActionPressedProperty().setValue(false);
	}

	/**
	 * Gets the view model.
	 *
	 * @return the view model
	 */
	public FullPatientViewModel getViewModel() {
		return this.viewModel;
	}

	/**
	 * Initialize text field formatters.
	 */
	private void initializeTextFieldFormatters() {
		this.ssnTextField.setTextFormatter(new TextFormatter<Change>(this.filterSSN));
		this.middleInitialTextField.setTextFormatter(new TextFormatter<Change>(this.filterInitial));
		this.contactPhoneTextField.setTextFormatter(new TextFormatter<Change>(this.filterPhone));
		this.zipCodeTextField.setTextFormatter(new TextFormatter<Change>(this.filterZip));

		this.stateComboBox.setItems(FXCollections.observableArrayList(States.ALL_STATES));
		this.genderComboBox.setItems(FXCollections.observableArrayList(Genders.ALL_GENDERS));

		this.firstNameTextField.setTextFormatter(new TextFormatter<Change>(this.maxLengthFormatter(50)));
		this.lastNameTextField.setTextFormatter(new TextFormatter<Change>(this.maxLengthFormatter(50)));
		this.contactEmailTextField.setTextFormatter(new TextFormatter<Change>(this.maxLengthFormatter(100)));
		this.streetAddress1TextField.setTextFormatter(new TextFormatter<Change>(this.maxLengthFormatter(100)));
		this.streetAddress2TextField.setTextFormatter(new TextFormatter<Change>(this.maxLengthFormatter(100)));
		this.cityTextField.setTextFormatter(new TextFormatter<Change>(this.maxLengthFormatter(100)));

		this.contactEmailTextField.textProperty().addListener((change) -> {
			this.isEmailValid
					.setValue(emailValid.matcher(this.contactEmailTextField.textProperty().getValue()).matches());
		});
	}

	/**
	 * Bind properties.
	 */
	private void bindProperties() {
		this.viewModel.getViewModelPatient().getContactEmailProperty().bindBidirectional(this.contactEmailTextField.textProperty());
		this.viewModel.getViewModelPatient().getContactPhoneProperty().bindBidirectional(this.contactPhoneTextField.textProperty());
		this.viewModel.getViewModelPatient().getFirstNameProperty().bindBidirectional(this.firstNameTextField.textProperty());
		this.viewModel.getViewModelPatient().getLastNameProperty().bindBidirectional(this.lastNameTextField.textProperty());
		this.viewModel.getViewModelPatient().getStreetAddress1Property().bindBidirectional(this.streetAddress1TextField.textProperty());
		this.viewModel.getViewModelPatient().getStreetAddress2Property().bindBidirectional(this.streetAddress2TextField.textProperty());
		this.viewModel.getViewModelPatient().getCityProperty().bindBidirectional(this.cityTextField.textProperty());
		this.viewModel.getViewModelPatient().getGenderProperty().bind(this.genderComboBox.selectionModelProperty());
		this.viewModel.getViewModelPatient().getStateProperty().bind(this.stateComboBox.selectionModelProperty());
		this.viewModel.getViewModelPatient().getZipCodePropertyy().bindBidirectional(this.zipCodeTextField.textProperty());
		this.viewModel.getViewModelPatient().getMiddleInitialProperty().bindBidirectional(this.middleInitialTextField.textProperty());
		this.viewModel.getViewModelPatient().getSsnProperty().bindBidirectional(this.ssnTextField.textProperty());
		this.viewModel.getViewModelPatient().getDobProperty().bindBidirectional(this.dobPicker.valueProperty());
		this.viewModel.getViewModelPatient().getActionTextProperty().bindBidirectional(this.actionButton.textProperty());

		this.viewModel.getViewModelPatient().getCloseDisableProperty().bindBidirectional(this.cancelButton.disableProperty());

		this.actionButton.disableProperty()
			.bind(this.dobPicker.valueProperty().isNull().or(this.contactEmailTextField.textProperty().isEmpty())
					.or(this.firstNameTextField.textProperty().isEmpty())
					.or(this.lastNameTextField.textProperty().isEmpty())
					.or(this.genderComboBox.getSelectionModel().selectedItemProperty().isNull())
					.or(this.streetAddress1TextField.textProperty().isEmpty())
					.or(this.cityTextField.textProperty().isEmpty())
					.or(this.zipCodeTextField.textProperty().length().lessThan(5))
					.or(this.stateComboBox.getSelectionModel().selectedItemProperty().isNull())
					.or(this.middleInitialTextField.textProperty().isEmpty())
					.or(this.ssnTextField.textProperty().length().lessThan(9))
					.or(this.contactPhoneTextField.textProperty().length().lessThan(10))
					.or(this.isEmailValid.not()));
	}

	/**
	 * Max length formatter.
	 *
	 * @param length the length
	 * @return the unary operator
	 */
	private UnaryOperator<Change> maxLengthFormatter(int length) {
		return (change) -> {
			Pattern pattern = Pattern.compile(".{0," + length + "}");
			return pattern.matcher(change.getControlNewText()).matches() ? change : null;
		};
	}
	
	/** The sub appt tab. */
	@FXML
	private Tab subApptTab;
	
	/** The sub checkup tab. */
	@FXML
	private Tab subCheckupTab;
	
	/** The sub test tab. */
	@FXML
	private Tab subTestTab;
	
	/** The sub final tab. */
	@FXML
	private Tab subFinalTab;




	
	/** The appt tab. */
	@FXML
	private Tab apptTab;

	/** The new appt button. */
	@FXML
	private Button newApptButton;

	/** The past list. */
	@FXML
	private ListView<TupleEmbed> pastList;

	/** The available list. */
	@FXML
	private ListView<TupleEmbed> availableList;
	
	/**
	 * Setup appt tab.
	 */
	private void setupApptTab() {
		this.apptTab.selectedProperty().addListener((evt)->{
			if(this.apptTab.isSelected()) {
				this.viewModel.loadData();
			}
		});
		
	}
	
	/**
	 * Setup available list.
	 */
	private void setupAvailableList() {
		
		this.availableList.selectionModelProperty().addListener((evt)->{
			this.availableList.refresh();
		});
		this.availableList.setItems(this.viewModel.getViewModelAppt().getAvailableList());
		this.availableList.setPadding(new Insets(0,0,0,0));
		this.availableList.setFixedCellSize(100.0);
		
		this.availableList.focusedProperty().addListener((e, oldVal, newVal)->{
			if(newVal) {
				TupleEmbed emb = (TupleEmbed)this.availableList.getSelectionModel().getSelectedItem();
				if(emb!= null){
					emb.setMouseTransparent(false);
					this.viewModel.setSelectedAppointment((Appointment)emb.getOperatedObject(), true);
				}
			} else {
				TupleEmbed emb = (TupleEmbed)this.availableList.getSelectionModel().getSelectedItem();
				if(emb != null) {
					emb.setMouseTransparent(true);
				}
			}
		});
		
		this.availableList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (oldValue != newValue && oldValue != null) {
				oldValue.setMouseTransparent(true);
			}
			if (newValue != null) {
				newValue.setMouseTransparent(false);
				this.viewModel.setSelectedAppointment((Appointment)newValue.getOperatedObject(), true);
			}
		});
	}
	
	/**
	 * Setup past list.
	 */
	private void setupPastList() {
		
		this.pastList.selectionModelProperty().addListener((evt)->{
			this.pastList.refresh();
		});
		this.pastList.setItems(this.viewModel.getViewModelAppt().getPastList());
		this.pastList.setPadding(new Insets(0,0,0,0));
		this.pastList.setFixedCellSize(100.0);
		
		this.pastList.focusedProperty().addListener((e, oldVal, newVal)->{
			if(newVal) {
				TupleEmbed emb = (TupleEmbed)this.pastList.getSelectionModel().getSelectedItem();
				if(emb!= null){
					emb.setMouseTransparent(false);
					this.viewModel.setSelectedAppointment((Appointment)emb.getOperatedObject(), false);
				}
			} else {
				TupleEmbed emb = (TupleEmbed)this.pastList.getSelectionModel().getSelectedItem();
				if(emb != null) {
					emb.setMouseTransparent(true);
				}
			}
		});
		
		this.pastList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (oldValue != newValue && oldValue != null) {
				oldValue.setMouseTransparent(true);
			}
			if (newValue != null) {
				newValue.setMouseTransparent(false);
				this.viewModel.setSelectedAppointment((Appointment)newValue.getOperatedObject(), false);
			}
		});
	}
	
	
	

	/** The appt date picker. */
	@FXML
	private DatePicker apptDatePicker;

	/** The appt hour picker. */
	@FXML
	private ComboBox<String> apptHourPicker;

	/** The appt minute picker. */
	@FXML
	private ComboBox<String> apptMinutePicker;

	/** The appt diural picker. */
	@FXML
	private ComboBox<String> apptDiuralPicker;

	/** The appt doctor picker. */
	@FXML
	private ComboBox<String> apptDoctorPicker;

	/** The appt reason field. */
	@FXML
	private TextField apptReasonField;

	/** The update appt button. */
	@FXML
	private Button updateApptButton;
	
	/**
	 * Setup sub appt.
	 */
	private void setupSubAppt() {
		this.apptHourPicker.setItems(FXCollections.observableArrayList(TimeSelections.ALL_HOURS));
		this.apptMinutePicker.setItems(FXCollections.observableArrayList(TimeSelections.ALL_MINUTES));
		this.apptDiuralPicker.setItems(FXCollections.observableArrayList(TimeSelections.ALL_DIURNALS));
		
		this.viewModel.getViewModelAppt().getDoctorSelectionProperty().bindBidirectional(this.apptDoctorPicker.selectionModelProperty());
		this.viewModel.getViewModelAppt().getDateProperty().bindBidirectional(this.apptDatePicker.valueProperty());
		
		this.viewModel.getViewModelAppt().getHourProperty().bindBidirectional(this.apptHourPicker.selectionModelProperty());
		this.viewModel.getViewModelAppt().getMinuteProperty().bindBidirectional(this.apptMinutePicker.selectionModelProperty());
		this.viewModel.getViewModelAppt().getDiurnalProperty().bindBidirectional(this.apptDiuralPicker.selectionModelProperty());
		
		this.viewModel.getViewModelAppt().getReasonProperty().bindBidirectional(this.apptReasonField.textProperty());
		
		this.apptDoctorPicker.setItems(this.viewModel.getViewModelAppt().getDoctorList());
		this.viewModel.getViewModelAppt().getDoctorSelectionProperty().bindBidirectional(this.apptDoctorPicker.selectionModelProperty());
		
		this.newApptButton.disableProperty().bind(
				this.apptDatePicker.valueProperty().isNull()
				.or(this.apptDiuralPicker.getSelectionModel().selectedItemProperty().isNull())
				.or(this.apptHourPicker.getSelectionModel().selectedItemProperty().isNull())
				.or(this.apptMinutePicker.getSelectionModel().selectedItemProperty().isNull())
				.or(this.apptDoctorPicker.getSelectionModel().selectedItemProperty().isNull())
				.or(this.apptReasonField.textProperty().isEmpty())
		);
		
		this.updateApptButton.disableProperty().bind(
				this.viewModel.getSelectedFinalDiagnosisProperty().isNotNull()
				.or(this.viewModel.getSelectedAppointmentProperty().isNull())
				.or(this.apptDatePicker.valueProperty().isNull())
				.or(this.apptDiuralPicker.getSelectionModel().selectedItemProperty().isNull())
				.or(this.apptHourPicker.getSelectionModel().selectedItemProperty().isNull())
				.or(this.apptMinutePicker.getSelectionModel().selectedItemProperty().isNull())
				.or(this.apptDoctorPicker.getSelectionModel().selectedItemProperty().isNull())
				.or(this.apptReasonField.textProperty().isEmpty())
				.or(Bindings.not(this.viewModel.getEditableAppointmentProperty()))
		);
		
	}

	/**
	 * Update appointment.
	 *
	 * @param event the event
	 */
	@FXML
	public void updateAppointment(ActionEvent event) {
		this.viewModel.getViewModelAppt().getUpdateEventProperty().setValue(true);
		this.viewModel.getViewModelAppt().getUpdateEventProperty().setValue(false);
	}
	
	/**
	 * Adds the appointment.
	 *
	 * @param event the event
	 */
	@FXML
	void addAppointment(ActionEvent event) {
		this.viewModel.getViewModelAppt().getCreateEventProperty().setValue(true);
		this.viewModel.getViewModelAppt().getCreateEventProperty().setValue(false);
	}

	/** The systolic pressure field. */
	@FXML
	private TextField systolicPressureField;

	/** The diastolic pressure field. */
	@FXML
	private TextField diastolicPressureField;

	/** The pulse field. */
	@FXML
	private TextField pulseField;

	/** The weight field. */
	@FXML
	private TextField weightField;

	/** The temperature field. */
	@FXML
	private TextField temperatureField;

	/** The add checkup button. */
	@FXML
	private Button addCheckupButton;
	
	/** The update checkup button. */
	@FXML
	private Button updateCheckupButton;
	
	@FXML
	private ComboBox<String> checkupNursePicker;
	
	
	/**
	 * Setup sub checkup.
	 */
	private void setupSubCheckup() {
		
		this.systolicPressureField.disableProperty().bind(this.viewModel.getSelectedFinalDiagnosisProperty().isNotNull().or(this.viewModel.getSelectedPatientProperty().isNull()));
		this.diastolicPressureField.disableProperty().bind(this.viewModel.getSelectedFinalDiagnosisProperty().isNotNull().or(this.viewModel.getSelectedPatientProperty().isNull()));
		this.pulseField.disableProperty().bind(this.viewModel.getSelectedFinalDiagnosisProperty().isNotNull().or(this.viewModel.getSelectedPatientProperty().isNull()));
		this.weightField.disableProperty().bind(this.viewModel.getSelectedFinalDiagnosisProperty().isNotNull().or(this.viewModel.getSelectedPatientProperty().isNull()));
		this.temperatureField.disableProperty().bind(this.viewModel.getSelectedFinalDiagnosisProperty().isNotNull().or(this.viewModel.getSelectedPatientProperty().isNull()));
		
		this.viewModel.getViewModelCheckup().getNurseSelectionProperty().bindBidirectional(this.checkupNursePicker.selectionModelProperty());
		this.checkupNursePicker.setItems(this.viewModel.getViewModelCheckup().getNurseList());
		
		this.addCheckupButton.disableProperty().bind(
				this.viewModel.getSelectedFinalDiagnosisProperty().isNotNull()
				.or(this.viewModel.getSelectedAppointmentProperty().isNull())
				.or(this.viewModel.getSelectedCheckupProperty().isNotNull())
				.or(this.temperatureField.textProperty().isEmpty())
				.or(this.systolicPressureField.textProperty().isEmpty())
				.or(this.diastolicPressureField.textProperty().isEmpty())
				.or(this.pulseField.textProperty().isEmpty())
				.or(this.weightField.textProperty().isEmpty())
				.or(this.checkupNursePicker.getSelectionModel().selectedItemProperty().isNull())
		);
		
		this.updateCheckupButton.disableProperty().bind(
				this.viewModel.getSelectedFinalDiagnosisProperty().isNotNull()
				.or(this.viewModel.getSelectedAppointmentProperty().isNull())
				.or(this.temperatureField.textProperty().isEmpty())
				.or(this.systolicPressureField.textProperty().isEmpty())
				.or(this.diastolicPressureField.textProperty().isEmpty())
				.or(this.pulseField.textProperty().isEmpty())
				.or(this.weightField.textProperty().isEmpty())
				.or(this.viewModel.getSelectedCheckupProperty().isNull())
				.or(this.checkupNursePicker.getSelectionModel().selectedItemProperty().isNull())
		);
		
		
		this.viewModel.getViewModelCheckup().getDiatolicPressureProperty().bindBidirectional(this.diastolicPressureField.textProperty());
		this.viewModel.getViewModelCheckup().getPulseProperty().bindBidirectional(this.pulseField.textProperty());
		this.viewModel.getViewModelCheckup().getSystolicPressureProperty().bindBidirectional(this.systolicPressureField.textProperty());
		this.viewModel.getViewModelCheckup().getTemperatureProperty().bindBidirectional(this.temperatureField.textProperty());
		this.viewModel.getViewModelCheckup().getWeightProperty().bindBidirectional(this.weightField.textProperty());
		
		this.diastolicPressureField.setTextFormatter(new TextFormatter<Change>(this.filterIntegral));
		this.pulseField.setTextFormatter(new TextFormatter<Change>(this.filterIntegral));
		this.systolicPressureField.setTextFormatter(new TextFormatter<Change>(this.filterIntegral));
		this.temperatureField.setTextFormatter(new TextFormatter<Change>(this.filterDecimals));
		this.weightField.setTextFormatter(new TextFormatter<Change>(this.filterDecimals));
	}
	
	/**
	 * Update checkup.
	 *
	 * @param event the event
	 */
	@FXML
	void updateCheckup(ActionEvent event) {
		this.viewModel.getViewModelCheckup().getUpdateEventProperty().setValue(true);
		this.viewModel.getViewModelCheckup().getUpdateEventProperty().setValue(false);
	}
	
	/**
	 * Adds the checkup.
	 *
	 * @param event the event
	 */
	@FXML
	void addCheckup(ActionEvent event) {
		this.viewModel.getViewModelCheckup().getCreateEventProperty().setValue(true);
		this.viewModel.getViewModelCheckup().getCreateEventProperty().setValue(false);
	}

	/** The test order status list. */
	@FXML
	private ListView<TupleEmbed> testOrderStatusList;

	/** The test cost field. */
	@FXML
	private TextField testCostField;

	/** The test desc field. */
	@FXML
	private TextField testDescField;

	/** The add test button. */
	@FXML
	private Button addTestButton;

	/** The test date picker. */
	@FXML
	private DatePicker testDatePicker;

	/** The test picker. */
	@FXML
	private ComboBox<String> testPicker;

	/** The test order list. */
	@FXML
	private ListView<TupleEmbed> testOrderList;

	/** The test remove sel button. */
	@FXML
	private Button testRemoveSelButton;

	/** The test remove all button. */
	@FXML
	private Button testRemoveAllButton;

	/** The test order button. */
	@FXML
	private Button testOrderButton;
	
	/**
	 * Setup sub test.
	 */
	private void setupSubTest() {
		this.testOrderStatusList.disableProperty().bind(this.viewModel.getSelectedFinalDiagnosisProperty().isNotNull().or(this.viewModel.getSelectedAppointmentProperty().isNull()));
		this.testCostField.disableProperty().bind(this.viewModel.getSelectedFinalDiagnosisProperty().isNotNull().or(this.viewModel.getSelectedAppointmentProperty().isNull()));
		this.testDescField.disableProperty().bind(this.viewModel.getSelectedFinalDiagnosisProperty().isNotNull().or(this.viewModel.getSelectedAppointmentProperty().isNull()));
		this.testDatePicker.disableProperty().bind(this.viewModel.getSelectedFinalDiagnosisProperty().isNotNull().or(this.viewModel.getSelectedAppointmentProperty().isNull()));
		this.testPicker.disableProperty().bind(this.viewModel.getSelectedFinalDiagnosisProperty().isNotNull().or(this.viewModel.getSelectedAppointmentProperty().isNull()));
		this.testRemoveSelButton.disableProperty().bind(this.viewModel.getSelectedFinalDiagnosisProperty().isNotNull().or(this.viewModel.getSelectedAppointmentProperty().isNull()));
		this.testRemoveAllButton.disableProperty().bind(this.viewModel.getSelectedFinalDiagnosisProperty().isNotNull().or(this.viewModel.getSelectedAppointmentProperty().isNull()));
		this.testOrderButton.disableProperty().bind(this.viewModel.getSelectedFinalDiagnosisProperty().isNotNull().or(this.viewModel.getSelectedAppointmentProperty().isNull()));
		
		this.testPicker.setItems(this.viewModel.getViewModelTest().getTestsList());
		this.viewModel.getViewModelTest().getTestDropSelectionProperty().bindBidirectional(this.testPicker.selectionModelProperty());
		
		this.testCostField.textProperty().bindBidirectional(this.viewModel.getViewModelTest().getTestCostProperty());
		this.testDescField.textProperty().bindBidirectional(this.viewModel.getViewModelTest().getTestDescProperty());
		this.testDatePicker.valueProperty().bindBidirectional(this.viewModel.getViewModelTest().getTestDateProperty());
		
		this.testOrderList.setItems(this.viewModel.getViewModelTest().getTestsOrderList());
		this.viewModel.getViewModelTest().getTestListOrderSelectionProperty().bindBidirectional(this.testOrderList.selectionModelProperty());
		
		this.testOrderStatusList.setItems(this.viewModel.getViewModelTest().getTestStatusList());
		this.viewModel.getViewModelTest().getTestListStatusSelectionProperty().bind(this.testOrderStatusList.selectionModelProperty());
		this.testOrderStatusList.selectionModelProperty().addListener((evt)->{
			this.testOrderStatusList.refresh();
		});
		this.testOrderStatusList.setPadding(new Insets(0,0,0,0));
		this.testOrderStatusList.setFixedCellSize(100.0);
		
		this.testOrderStatusList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (oldValue != newValue && oldValue != null) {
				oldValue.setMouseTransparent(true);
			}
			if (newValue != null) {
				newValue.setMouseTransparent(false);
			}
		});
		
		
		this.testOrderList.selectionModelProperty().addListener((evt)->{
			this.testOrderList.refresh();
		});
		this.testOrderList.setPadding(new Insets(0,0,0,0));
		this.testOrderList.setFixedCellSize(100.0);
		
		this.testOrderList.disableProperty().bind(
				this.viewModel.getSelectedFinalDiagnosisProperty().isNotNull()
				.or(this.viewModel.getSelectedAppointmentProperty().isNull())
		);
		
		this.addTestButton.disableProperty().bind(
				this.viewModel.getSelectedFinalDiagnosisProperty().isNotNull()
				.or(this.viewModel.getSelectedAppointmentProperty().isNull())
				.or(this.testDatePicker.valueProperty().isNull())
				.or(this.testPicker.getSelectionModel().selectedItemProperty().isNull())
		);
		
		this.testRemoveSelButton.disableProperty().bind(
				this.testOrderList.getSelectionModel().selectedItemProperty().isNull()
		);
		
		this.testCostField.setEditable(false);
		this.testDescField.setEditable(false);
		
	}
	
	/**
	 * Test queue add.
	 *
	 * @param event the event
	 */
	@FXML
	public void testQueueAdd(ActionEvent event) {
		this.viewModel.getViewModelTest().getQueueTestEventProperty().setValue(true);
		this.viewModel.getViewModelTest().getQueueTestEventProperty().setValue(false);
	}
	
	/**
	 * Test remove selected.
	 *
	 * @param event the event
	 */
	@FXML
	public void testRemoveSelected(ActionEvent event) {
		this.viewModel.getViewModelTest().getRemoveTestEventProperty().setValue(true);
		this.viewModel.getViewModelTest().getRemoveTestEventProperty().setValue(false);
	}
	
	/**
	 * Test remove all.
	 *
	 * @param event the event
	 */
	@FXML
	public void testRemoveAll(ActionEvent event) {
		this.viewModel.getViewModelTest().getRemoveAllTestsEventProperty().setValue(true);
		this.viewModel.getViewModelTest().getRemoveAllTestsEventProperty().setValue(false);
	}
	
	/**
	 * Adds the lab tests.
	 *
	 * @param event the event
	 */
	@FXML
	public void addLabTests(ActionEvent event) {
		this.viewModel.getViewModelTest().getOrderTestsEventProperty().setValue(true);
		this.viewModel.getViewModelTest().getOrderTestsEventProperty().setValue(false);
	}

	/** The final diagnosis field. */
	@FXML
	private TextArea finalDiagnosisField;

	/** The submit final diagnosis button. */
	@FXML
	private Button submitFinalDiagnosisButton;
	
	/**
	 * Setup sub final.
	 */
	private void setupSubFinal() {
		
		this.finalDiagnosisField.disableProperty().bind(
				this.viewModel.getSelectedFinalDiagnosisProperty().isNotNull()
				.or(this.viewModel.getSelectedPatientProperty().isNull()));
		this.submitFinalDiagnosisButton.disableProperty().bind(
				this.viewModel.getSelectedFinalDiagnosisProperty().isNotNull()
				.or(this.viewModel.getSelectedAppointmentProperty().isNull())
			);
		
		this.finalDiagnosisField.textProperty().bindBidirectional(
			this.viewModel.getViewModelFinal().getFinalDiagnosisProperty()
		);
		
	}

	/**
	 * Submit final diagnosis.
	 *
	 * @param event the event
	 */
	@FXML
	public void submitFinalDiagnosis(ActionEvent event) {
		this.viewModel.getViewModelFinal().getSubmitEventProperty().setValue(true);
		this.viewModel.getViewModelFinal().getSubmitEventProperty().setValue(false);
	}
	
	
	/** The diagnosis field. */
	@FXML
	private TextArea diagnosisField;

	/** The add diagnosis button. */
	@FXML
	private Button addDiagnosisButton;
	
	/** The update diagnosis button. */
	@FXML
	private Button updateDiagnosisButton;
	
	/**
	 * Setup sub diag.
	 */
	private void setupSubDiag() {
		
		this.diagnosisField.disableProperty().bind(
				this.viewModel.getSelectedFinalDiagnosisProperty().isNotNull()
				.or(this.viewModel.getSelectedPatientProperty().isNull())
				.or(this.viewModel.getSelectedAppointmentProperty().isNull())
		);
		this.addDiagnosisButton.disableProperty().bind(
				this.viewModel.getSelectedFinalDiagnosisProperty().isNotNull()
				.or(this.viewModel.getSelectedPatientProperty().isNull())
				.or(this.viewModel.getSelectedAppointmentProperty().isNull())
				.or(this.viewModel.getSelectedDiagnosisProperty().isNotNull())
		);
		this.updateDiagnosisButton.disableProperty().bind(
				this.viewModel.getSelectedFinalDiagnosisProperty().isNotNull()
				.or(this.viewModel.getSelectedPatientProperty().isNull())
				.or(this.viewModel.getSelectedAppointmentProperty().isNull())
				.or(this.viewModel.getSelectedDiagnosisProperty().isNull())
		);
		
		this.diagnosisField.textProperty().bindBidirectional(
			this.viewModel.getViewModelDiag().getDiagnosisProperty()
		);
		
	}
	
	/**
	 * Adds the diagnosis handler.
	 *
	 * @param event the event
	 */
	@FXML
	public void addDiagnosisHandler(ActionEvent event) {
		this.viewModel.getViewModelDiag().getAddEventProperty().setValue(true);
		this.viewModel.getViewModelDiag().getAddEventProperty().setValue(false);
	}
	
	/**
	 * Update diagnosis handler.
	 *
	 * @param event the event
	 */
	@FXML
	public void updateDiagnosisHandler(ActionEvent event) {
		this.viewModel.getViewModelDiag().getUpdateEventProperty().setValue(true);
		this.viewModel.getViewModelDiag().getUpdateEventProperty().setValue(false);
	}
	
}
