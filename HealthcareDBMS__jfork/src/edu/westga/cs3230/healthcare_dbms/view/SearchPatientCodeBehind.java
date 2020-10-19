package edu.westga.cs3230.healthcare_dbms.view;

import java.sql.Date;
import java.time.LocalDate;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import edu.westga.cs3230.healthcare_dbms.viewmodel.AddPatientViewModel;
import edu.westga.cs3230.healthcare_dbms.viewmodel.SearchPatientViewModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.stage.Stage;

public class SearchPatientCodeBehind {
	
	@FXML
    private ComboBox<?> dobFilter;

	@FXML
	private Button searchButton;

	@FXML
	private Button cancelButton;

	@FXML
	private TextField firstNameTextField;

	@FXML
	private TextField lastNameTextField;

	@FXML
	private TextField contactPhoneTextField;

	@FXML
	private TextField contactEmailTextField;

	@FXML
	private TextField mailingAddressTextField;

	@FXML
	private DatePicker dobPicker;

	@FXML
	private TextField middleInitialTextField;

	@FXML
	private TextField ssnTextField;
	
	private ObjectProperty<Date> dateSelect;
	
	/*
	 * 
	private UnaryOperator<Change> filterIntegers = change -> {
	    Pattern pattern = Pattern.compile("\\d*");
	    return pattern.matcher(change.getControlNewText()).matches() ? change : null;
	};
	
	
	private UnaryOperator<Change> filterLetters = change -> {
	    Pattern pattern = Pattern.compile("[a-zA-Z]*");
	    return pattern.matcher(change.getControlNewText()).matches() ? change : null;
	};
	
	private UnaryOperator<Change> filterDecimals = change -> {
	    Pattern pattern = Pattern.compile("\\d*|\\d+\\.\\d*");
	    return pattern.matcher(change.getControlNewText()).matches() ? change : null;
	};
	
	private UnaryOperator<Change> filterPhone = change -> {
		Pattern pattern = Pattern.compile("(\\d{0,10})|((\\d{0,3}[-/]?){0,3}\\d{0,1})");
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
	
	private static final Pattern emailValid = Pattern.compile("[a-zA-Z]+(\\d|[a-zA-Z])*@[a-zA-Z]+(\\d|[a-zA-Z])*[.](\\d|[a-zA-Z])+");
	private final BooleanProperty isEmailValid;
	
	private SearchPatientViewModel viewModel;
	private boolean attemptAdd;

	public SearchPatientCodeBehind() {
		this.viewModel = new SearchPatientViewModel();
		this.attemptAdd = false;
		this.dateSelect = new SimpleObjectProperty<Date>();
		this.isEmailValid = new SimpleBooleanProperty(true);
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
	public void searchForPatient(ActionEvent event) {
		this.attemptAdd = true;
		///this.closeWindow(event);
	}
	
	@FXML
    public void getDateValue(ActionEvent event) {
		LocalDate time = this.dobPicker.getValue();
		this.dateSelect.setValue(Date.valueOf(time));
    }

	public boolean isAttemptingAdd() {
		return this.attemptAdd;
	}

	public SearchPatientViewModel getViewModel() {
		return this.viewModel;
	}
	
	private void initializeTextFieldFormatters() {
		this.ssnTextField.setTextFormatter(new TextFormatter<Change>(this.filterSSN));
		this.middleInitialTextField.setTextFormatter(new TextFormatter<Change>(this.filterInitial));
		this.contactPhoneTextField.setTextFormatter(new TextFormatter<Change>(this.filterPhone));
		
		///limits on database
		this.firstNameTextField.setTextFormatter(new TextFormatter<Change>(this.maxLengthFormatter(50)));
		this.lastNameTextField.setTextFormatter(new TextFormatter<Change>(this.maxLengthFormatter(50)));
		this.contactEmailTextField.setTextFormatter(new TextFormatter<Change>(this.maxLengthFormatter(100)));
		this.mailingAddressTextField.setTextFormatter(new TextFormatter<Change>(this.maxLengthFormatter(100)));
	}

	private void bindProperties() {
		this.viewModel.getContactEmailProperty().bindBidirectional(this.contactEmailTextField.textProperty());
		this.viewModel.getContactPhoneProperty().bindBidirectional(this.contactPhoneTextField.textProperty());
		this.viewModel.getFirstNameProperty().bindBidirectional(this.firstNameTextField.textProperty());
		this.viewModel.getLastNameProperty().bindBidirectional(this.lastNameTextField.textProperty());
		this.viewModel.getMailingAddressProperty().bindBidirectional(this.mailingAddressTextField.textProperty());
		this.viewModel.getMiddleInitialProperty().bindBidirectional(this.middleInitialTextField.textProperty());
		this.viewModel.getSsnProperty().bindBidirectional(this.ssnTextField.textProperty());
		this.viewModel.getDobProperty().bindBidirectional(this.dateSelect);
		this.viewModel.getSearchEventProperty().bind(this.searchButton.pressedProperty());
		
		this.searchButton.disableProperty().bind(this.dateSelect.isNull()
				.and(this.contactEmailTextField.textProperty().isEmpty())
				.and(this.contactPhoneTextField.textProperty().isEmpty())
				.and(this.firstNameTextField.textProperty().isEmpty())
				.and(this.lastNameTextField.textProperty().isEmpty())
				.and(this.mailingAddressTextField.textProperty().isEmpty())
				.and(this.middleInitialTextField.textProperty().isEmpty())
				.and(this.ssnTextField.textProperty().isEmpty())
				.and(this.ssnTextField.textProperty().length().lessThan(9))
				.and(this.contactPhoneTextField.textProperty().length().lessThan(10))
				.or(this.isEmailValid.not())
				);
		
		this.contactEmailTextField.textProperty().addListener((change)->{
			String value = this.contactEmailTextField.textProperty().getValue();
			if(value == null || value.length() == 0) {
				this.isEmailValid.setValue(true);;
				return;
			}
			this.isEmailValid.setValue(emailValid.matcher(value).matches());
		});
	}
	
	private UnaryOperator<Change> maxLengthFormatter(int length) {
		return (change) -> {
			Pattern pattern = Pattern.compile(".{0,"+length+"}");
		    return pattern.matcher(change.getControlNewText()).matches() ? change : null;
		};
	}

}
