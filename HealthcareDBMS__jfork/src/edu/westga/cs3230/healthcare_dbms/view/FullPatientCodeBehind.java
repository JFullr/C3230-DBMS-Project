package edu.westga.cs3230.healthcare_dbms.view;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import edu.westga.cs3230.healthcare_dbms.model.PatientData;
import edu.westga.cs3230.healthcare_dbms.utils.Genders;
import edu.westga.cs3230.healthcare_dbms.utils.States;
import edu.westga.cs3230.healthcare_dbms.utils.TimeSelections;
import edu.westga.cs3230.healthcare_dbms.view.embed.TupleEmbed;
import edu.westga.cs3230.healthcare_dbms.viewmodel.FullPatientViewModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
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

public class FullPatientCodeBehind {

	public static final String ACTION_VALID_ALL = "all";
	public static final String ACTION_VALID_MINIMAL = "min";
	public static final String ACTION_VALID_NONE = "none";

	@FXML
	private Button actionButton;

	@FXML
	private Button cancelButton;

	@FXML
	private TextField firstNameTextField;

	@FXML
	private TextField lastNameTextField;

	@FXML
	private ComboBox<String> genderComboBox;

	@FXML
	private TextField contactPhoneTextField;

	@FXML
	private TextField contactEmailTextField;

	@FXML
	private TextField streetAddress1TextField;

	@FXML
	private TextField streetAddress2TextField;

	@FXML
	private TextField cityTextField;

	@FXML
	private ComboBox<String> stateComboBox;

	@FXML
	private TextField zipCodeTextField;

	@FXML
	private DatePicker dobPicker;

	@FXML
	private TextField middleInitialTextField;

	@FXML
	private TextField ssnTextField;

	private UnaryOperator<Change> filterDecimals = change -> {
		Pattern pattern = Pattern.compile("([.]\\d*)|(\\d*|\\d+\\.\\d*)");
		return pattern.matcher(change.getControlNewText()).matches() ? change : null;
	};

	private UnaryOperator<Change> filterInitial = change -> {
		Pattern pattern = Pattern.compile("[a-zA-Z]?");
		return pattern.matcher(change.getControlNewText()).matches() ? change : null;
	};

	private UnaryOperator<Change> filterSSN = change -> {
		Pattern pattern = Pattern.compile("\\d{0,9}");
		return pattern.matcher(change.getControlNewText()).matches() ? change : null;
	};

	private UnaryOperator<Change> filterPhone = change -> {
		Pattern pattern = Pattern.compile("\\d{0,10}");
		return pattern.matcher(change.getControlNewText()).matches() ? change : null;
	};

	private UnaryOperator<Change> filterZip = change -> {
		Pattern pattern = Pattern.compile("\\d{0,5}");
		return pattern.matcher(change.getControlNewText()).matches() ? change : null;
	};
	
	private UnaryOperator<Change> filterIntegral = change -> {
		Pattern pattern = Pattern.compile("\\d*");
		return pattern.matcher(change.getControlNewText()).matches() ? change : null;
	};

	private static final Pattern emailValid = Pattern
			.compile("[a-zA-Z]+(\\d|[a-zA-Z])*@[a-zA-Z]+(\\d|[a-zA-Z])*[.](\\d|[a-zA-Z])+");
	private final BooleanProperty isEmailValid;

	private FullPatientViewModel viewModel;
	
	
	public FullPatientCodeBehind() {
		this.viewModel = new FullPatientViewModel();
		this.isEmailValid = new SimpleBooleanProperty(false);
	}

	/**
	 * Initializer for the fxml data
	 */
	@FXML
	public void initialize() {
		this.bindProperties();
		this.initializeTextFieldFormatters();
		
		this.setupAvailableList();
		this.setupPastList();
		
		/*
		TODO uncomment after tests are complete
		only necessary when no appointment is selected
		
		this.subCheckupTab.disableProperty().bind(this.selectedPatient.isNull());
		this.subFinalTab.disableProperty().bind(this.selectedPatient.isNull());
		this.subTestTab.disableProperty().bind(this.selectedPatient.isNull());
		this.updateApptButton.disableProperty().bind(this.selectedPatient.isNull());
		//*/
		
		this.setupApptTab();
		this.setupSubAppt();
		this.setupSubCheckup();
		this.setupSubTest();
		this.setupSubFinal();
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

	public FullPatientViewModel getViewModel() {
		return this.viewModel;
	}

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

	private void bindProperties() {
		this.viewModel.getContactEmailProperty().bindBidirectional(this.contactEmailTextField.textProperty());
		this.viewModel.getContactPhoneProperty().bindBidirectional(this.contactPhoneTextField.textProperty());
		this.viewModel.getFirstNameProperty().bindBidirectional(this.firstNameTextField.textProperty());
		this.viewModel.getLastNameProperty().bindBidirectional(this.lastNameTextField.textProperty());
		this.viewModel.getStreetAddress1Property().bindBidirectional(this.streetAddress1TextField.textProperty());
		this.viewModel.getStreetAddress2Property().bindBidirectional(this.streetAddress2TextField.textProperty());
		this.viewModel.getCityProperty().bindBidirectional(this.cityTextField.textProperty());
		this.viewModel.getGenderProperty().bind(this.genderComboBox.selectionModelProperty());
		this.viewModel.getStateProperty().bind(this.stateComboBox.selectionModelProperty());
		this.viewModel.getZipCodePropertyy().bindBidirectional(this.zipCodeTextField.textProperty());
		this.viewModel.getMiddleInitialProperty().bindBidirectional(this.middleInitialTextField.textProperty());
		this.viewModel.getSsnProperty().bindBidirectional(this.ssnTextField.textProperty());
		this.viewModel.getDobProperty().bindBidirectional(this.dobPicker.valueProperty());
		this.viewModel.getActionTextProperty().bindBidirectional(this.actionButton.textProperty());

		this.viewModel.getCloseDisableProperty().bindBidirectional(this.cancelButton.disableProperty());

		this.actionButton.disableProperty()
			.bind(this.dobPicker.valueProperty().isNull().or(this.contactEmailTextField.textProperty().isEmpty())
					.or(this.firstNameTextField.textProperty().isEmpty())
					.or(this.lastNameTextField.textProperty().isEmpty())
					.or(this.genderComboBox.getSelectionModel().selectedItemProperty().isNull())
					.or(this.streetAddress1TextField.textProperty().isEmpty())
					// .or(this.streetAddress2TextField.textProperty().isEmpty())
					.or(this.cityTextField.textProperty().isEmpty())
					.or(this.zipCodeTextField.textProperty().length().lessThan(5))
					.or(this.stateComboBox.getSelectionModel().selectedItemProperty().isNull())
					.or(this.middleInitialTextField.textProperty().isEmpty())
					.or(this.ssnTextField.textProperty().length().lessThan(9))
					.or(this.contactPhoneTextField.textProperty().length().lessThan(10))
					.or(this.isEmailValid.not()));
	}

	private UnaryOperator<Change> maxLengthFormatter(int length) {
		return (change) -> {
			Pattern pattern = Pattern.compile(".{0," + length + "}");
			return pattern.matcher(change.getControlNewText()).matches() ? change : null;
		};
	}
	
	@FXML
	private Tab subApptTab;
	
	@FXML
	private Tab subCheckupTab;
	
	@FXML
	private Tab subTestTab;
	
	@FXML
	private Tab subFinalTab;




	
	@FXML
	private Tab apptTab;

	@FXML
	private Button newApptButton;

	@FXML
	private ListView<TupleEmbed> pastList;

	@FXML
	private ListView<TupleEmbed> availableList;

	@FXML
	void addAppointment(ActionEvent event) {
		this.viewModel.showCreateAppointment();
	}
	
	private void setupApptTab() {
		this.apptTab.selectedProperty().addListener((evt)->{
			this.viewModel.loadAssociatedData();
		});
		
	}
	
	private void setupAvailableList() {
		
		this.availableList.selectionModelProperty().addListener((evt)->{
			this.availableList.refresh();
		});
		this.availableList.setItems(this.viewModel.getViewModelControl().getAvailableList());
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
		this.pastList.setItems(this.viewModel.getViewModelControl().getPastList());
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
	
	
	

	@FXML
	private DatePicker apptDatePicker;

	@FXML
	private ComboBox<String> apptHourPicker;

	@FXML
	private ComboBox<String> apptMinutePicker;

	@FXML
	private ComboBox<String> apptDiuralPicker;

	@FXML
	private ComboBox<String> apptDoctorPicker;

	@FXML
	private TextField apptReasonField;

	@FXML
	private Button updateApptButton;
	
	private void setupSubAppt() {
		this.apptHourPicker.setItems(FXCollections.observableArrayList(TimeSelections.ALL_HOURS));
		this.apptMinutePicker.setItems(FXCollections.observableArrayList(TimeSelections.ALL_MINUTES));
		this.apptDiuralPicker.setItems(FXCollections.observableArrayList(TimeSelections.ALL_DIURNALS));
		//this.addCheckupDetailsButton.disableProperty().bind(this.viewModel.getIsUpdateProperty().not());
		
		this.viewModel.getViewModelAppt().getDoctorSelectionProperty().bindBidirectional(this.apptDoctorPicker.selectionModelProperty());
		this.viewModel.getViewModelAppt().getDateProperty().bindBidirectional(this.apptDatePicker.valueProperty());
		
		this.viewModel.getViewModelAppt().getHourProperty().bindBidirectional(this.apptHourPicker.selectionModelProperty());
		this.viewModel.getViewModelAppt().getMinuteProperty().bindBidirectional(this.apptMinutePicker.selectionModelProperty());
		this.viewModel.getViewModelAppt().getDiurnalProperty().bindBidirectional(this.apptDiuralPicker.selectionModelProperty());
		
		this.viewModel.getViewModelAppt().getReasonProperty().bindBidirectional(this.apptReasonField.textProperty());
		
		this.updateApptButton.disableProperty().bind(this.viewModel.getSelectedPatientProperty().isNull().or(this.viewModel.getFinalizedAppointment()));
		
	}

	@FXML
	public void updateAppointment(ActionEvent event) {
		//this.viewModel.app
	}

	@FXML
	private TextField systolicPressureField;

	@FXML
	private TextField diastolicPressureField;

	@FXML
	private TextField pulseField;

	@FXML
	private TextField weightField;

	@FXML
	private TextField temperatureField;

	@FXML
	private Button pushCheckupButton;
	
	private void setupSubCheckup() {
		
		this.systolicPressureField.disableProperty().bind(this.viewModel.getFinalizedAppointment().or(this.viewModel.getSelectedPatientProperty().isNull()));
		this.diastolicPressureField.disableProperty().bind(this.viewModel.getFinalizedAppointment().or(this.viewModel.getSelectedPatientProperty().isNull()));
		this.pulseField.disableProperty().bind(this.viewModel.getFinalizedAppointment().or(this.viewModel.getSelectedPatientProperty().isNull()));
		this.weightField.disableProperty().bind(this.viewModel.getFinalizedAppointment().or(this.viewModel.getSelectedPatientProperty().isNull()));
		this.temperatureField.disableProperty().bind(this.viewModel.getFinalizedAppointment().or(this.viewModel.getSelectedPatientProperty().isNull()));
		this.pushCheckupButton.disableProperty().bind(this.viewModel.getFinalizedAppointment().or(this.viewModel.getSelectedPatientProperty().isNull()));
		
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
	
	@FXML
	void pushCheckup(ActionEvent event) {

	}

	@FXML
	private ListView<TupleEmbed> testOrderViewList;

	@FXML
	private TextField testCostField;

	@FXML
	private TextField testDescField;

	@FXML
	private Button addTestButton;

	@FXML
	private DatePicker testDatePicker;

	@FXML
	private ComboBox<String> testPicker;

	@FXML
	private ListView<TupleEmbed> testOrderList;

	@FXML
	private Button testRemoveSelButton;

	@FXML
	private Button testRemoveAllButton;

	@FXML
	private Button testOrderButton;
	
	private void setupSubTest() {
		//*
		this.testOrderViewList.disableProperty().bind(this.viewModel.getFinalizedAppointment().or(this.viewModel.getSelectedPatientProperty().isNull()));
		this.testCostField.disableProperty().bind(this.viewModel.getFinalizedAppointment().or(this.viewModel.getSelectedPatientProperty().isNull()));
		this.testDescField.disableProperty().bind(this.viewModel.getFinalizedAppointment().or(this.viewModel.getSelectedPatientProperty().isNull()));
		this.testDatePicker.disableProperty().bind(this.viewModel.getFinalizedAppointment().or(this.viewModel.getSelectedPatientProperty().isNull()));
		this.testPicker.disableProperty().bind(this.viewModel.getFinalizedAppointment().or(this.viewModel.getSelectedPatientProperty().isNull()));
		this.testOrderList.disableProperty().bind(this.viewModel.getFinalizedAppointment().or(this.viewModel.getSelectedPatientProperty().isNull()));
		this.testRemoveSelButton.disableProperty().bind(this.viewModel.getFinalizedAppointment().or(this.viewModel.getSelectedPatientProperty().isNull()));
		this.testRemoveAllButton.disableProperty().bind(this.viewModel.getFinalizedAppointment().or(this.viewModel.getSelectedPatientProperty().isNull()));
		this.testOrderButton.disableProperty().bind(this.viewModel.getFinalizedAppointment().or(this.viewModel.getSelectedPatientProperty().isNull()));
		this.addTestButton.disableProperty().bind(this.viewModel.getFinalizedAppointment().or(this.viewModel.getSelectedPatientProperty().isNull()));
		//*/
		
		this.testPicker.setItems(this.viewModel.getViewModelTest().getTestsList());
		this.viewModel.getViewModelTest().getTestDropSelectionProperty().bindBidirectional(this.testPicker.selectionModelProperty());
		
		this.testOrderViewList.selectionModelProperty().bindBidirectional(this.viewModel.getViewModelTest().getTestListOrderSelectionProperty());
		this.testCostField.textProperty().bindBidirectional(this.viewModel.getViewModelTest().getTestCostProperty());
		this.testDescField.textProperty().bindBidirectional(this.viewModel.getViewModelTest().getTestDescProperty());
		this.testDatePicker.valueProperty().bindBidirectional(this.viewModel.getViewModelTest().getTestDateProperty());
		
		this.testOrderList.setItems(this.viewModel.getViewModelTest().getTestsOrderList());
		this.viewModel.getViewModelTest().getTestListOrderSelectionProperty().bindBidirectional(this.testOrderList.selectionModelProperty());
		
	}
	
	@FXML
	void testQueueAdd(ActionEvent event) {
		this.viewModel.getViewModelTest().getQueueTestEventProperty().setValue(true);
		this.viewModel.getViewModelTest().getQueueTestEventProperty().setValue(false);
	}
	
	@FXML
	void testRemoveSelected(ActionEvent event) {
		this.viewModel.getViewModelTest().getRemoveTestEventProperty().setValue(true);
		this.viewModel.getViewModelTest().getRemoveTestEventProperty().setValue(false);
	}
	
	@FXML
	void testRemoveAll(ActionEvent event) {
		this.viewModel.getViewModelTest().getRemoveAllTestsEventProperty().setValue(true);
		this.viewModel.getViewModelTest().getRemoveAllTestsEventProperty().setValue(false);
	}
	
	@FXML
	void addLabTests(ActionEvent event) {
		this.viewModel.getViewModelTest().getOrderTestsEventProperty().setValue(true);
		this.viewModel.getViewModelTest().getOrderTestsEventProperty().setValue(false);
	}

	@FXML
	private TextArea finalDiagnosisField;

	@FXML
	private Button submitFinalDiagnosisButton;
	
	private void setupSubFinal() {
		
		this.finalDiagnosisField.disableProperty().bind(this.viewModel.getFinalizedAppointment().or(this.viewModel.getSelectedPatientProperty().isNull()));
		this.submitFinalDiagnosisButton.disableProperty().bind(this.viewModel.getFinalizedAppointment().or(this.viewModel.getSelectedPatientProperty().isNull()));
		
		this.finalDiagnosisField.textProperty().bindBidirectional(this.viewModel.getViewModelFinal().getFinalDiagnosisProperty());
		
	}

	@FXML
	void submitFinalDiagnosis(ActionEvent event) {

	}

}
