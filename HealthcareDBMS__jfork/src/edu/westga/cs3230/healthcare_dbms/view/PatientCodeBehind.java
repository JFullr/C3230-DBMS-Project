package edu.westga.cs3230.healthcare_dbms.view;

import java.sql.Date;
import java.time.LocalDate;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import edu.westga.cs3230.healthcare_dbms.model.Address;
import edu.westga.cs3230.healthcare_dbms.model.Patient;
import edu.westga.cs3230.healthcare_dbms.model.PatientData;
import edu.westga.cs3230.healthcare_dbms.model.Person;
import edu.westga.cs3230.healthcare_dbms.utils.Genders;
import edu.westga.cs3230.healthcare_dbms.utils.States;
import edu.westga.cs3230.healthcare_dbms.viewmodel.PatientViewModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextFormatter.Change;
import javafx.stage.Stage;

public class PatientCodeBehind {
	
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
	
	/*
	private UnaryOperator<Change> filterDecimals = change -> {
	    Pattern pattern = Pattern.compile("\\d*|\\d+\\.\\d*");
	    return pattern.matcher(change.getControlNewText()).matches() ? change : null;
	};
	*/
	
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
	
	private static final Pattern emailValid = Pattern.compile("[a-zA-Z]+(\\d|[a-zA-Z])*@[a-zA-Z]+(\\d|[a-zA-Z])*[.](\\d|[a-zA-Z])+");
	private final BooleanProperty isEmailValid;
	
	private PatientViewModel viewModel;

	public PatientCodeBehind() {
		this.viewModel = new PatientViewModel();
		this.isEmailValid = new SimpleBooleanProperty(false);
	}
	
	/**
	 * Initializer for the fxml data
	 */
	@FXML
	public void initialize() {
		this.bindProperties();
		this.initializeTextFieldFormatters();
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

	public PatientViewModel getViewModel() {
		return this.viewModel;
	}
	
	public void setValidationAll(){
		this.actionButton.disableProperty().unbind();
		
		this.actionButton.disableProperty().bind(
				this.dobPicker.valueProperty().isNull()
				.or(this.contactEmailTextField.textProperty().isEmpty())
				.or(this.firstNameTextField.textProperty().isEmpty())
				.or(this.lastNameTextField.textProperty().isEmpty())
				.or(this.genderComboBox.getSelectionModel().selectedItemProperty().isNull())
				.or(this.streetAddress1TextField.textProperty().isEmpty())
				//.or(this.streetAddress2TextField.textProperty().isEmpty())
				.or(this.cityTextField.textProperty().isEmpty())
				.or(this.zipCodeTextField.textProperty().length().lessThan(5))
				.or(this.stateComboBox.getSelectionModel().selectedItemProperty().isNull())
				.or(this.middleInitialTextField.textProperty().isEmpty())
				.or(this.ssnTextField.textProperty().length().lessThan(9))
				.or(this.contactPhoneTextField.textProperty().length().lessThan(10))
				.or(this.isEmailValid.not())
				);
	}
	
	public void setValidationMinimum() {
		this.actionButton.disableProperty().unbind();
		
		this.actionButton.disableProperty().bind(
				this.dobPicker.valueProperty().isNull()
				.and(this.genderComboBox.getSelectionModel().selectedItemProperty().isNull())
				.and(this.genderComboBox.getSelectionModel().selectedItemProperty().isNull())
				.and(this.firstNameTextField.textProperty().isEmpty())
				.and(this.lastNameTextField.textProperty().isEmpty())
				.and(this.streetAddress1TextField.textProperty().isEmpty())
				//.and(this.contactEmailTextField.textProperty().)
				.and(this.middleInitialTextField.textProperty().isEmpty())
				.and(this.ssnTextField.textProperty().length().isNotEqualTo(9))
				.and(this.contactPhoneTextField.textProperty().length().isNotEqualTo(10))
				.and(this.zipCodeTextField.textProperty().length().isNotEqualTo(5))
				.and(this.isEmailValid.not())
				);
	}
	
	public void setValidationNone() {
		this.actionButton.disableProperty().unbind();
		
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
	
		this.contactEmailTextField.textProperty().addListener((change)->{
			this.isEmailValid.setValue(emailValid.matcher(this.contactEmailTextField.textProperty().getValue()).matches());
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
		
		this.viewModel.getValidationProperty().addListener((evt)->{
			String prop = this.viewModel.getValidationProperty().getValue();
			if(prop == null) {
				return;
			}
			
			if(prop.equalsIgnoreCase(PatientCodeBehind.ACTION_VALID_ALL)) {
				this.setValidationAll();
			}else if(prop.equalsIgnoreCase(PatientCodeBehind.ACTION_VALID_MINIMAL)) {
				this.setValidationMinimum();
			}else if(prop.equalsIgnoreCase(PatientCodeBehind.ACTION_VALID_NONE)) {
				this.setValidationNone();
			} 
		});
		
		this.setValidationAll();
	}
	
	private UnaryOperator<Change> maxLengthFormatter(int length) {
		return (change) -> {
			Pattern pattern = Pattern.compile(".{0,"+length+"}");
		    return pattern.matcher(change.getControlNewText()).matches() ? change : null;
		};
	}

}
