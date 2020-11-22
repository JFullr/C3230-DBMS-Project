package edu.westga.cs3230.healthcare_dbms.viewmodel;

import java.util.Optional;

import edu.westga.cs3230.healthcare_dbms.io.database.HealthcareDatabase;
import edu.westga.cs3230.healthcare_dbms.io.database.QueryResult;
import edu.westga.cs3230.healthcare_dbms.model.AppointmentData;
import edu.westga.cs3230.healthcare_dbms.model.FinalDiagnosis;
import edu.westga.cs3230.healthcare_dbms.sql.SqlSetter;
import edu.westga.cs3230.healthcare_dbms.view.utils.FXMLAlert;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;

/**
 * View model class for the final diagnosis pane.
 *
 * @author Joseph Fuller and Andrew Steinborn
 */
public class FullPatientViewModelSubFinal {
	
	/** The final diagnosis property. */
	private final StringProperty finalDiagnosisProperty;
	
	/** The submit event property. */
	private final BooleanProperty submitEventProperty;
	
	/** The given appointment property. */
	private final ObjectProperty<AppointmentData> givenAppointmentProperty;
	
	/** The given final diagnosis property. */
	private final ObjectProperty<FinalDiagnosis> givenFinalDiagnosisProperty;
	
	/** The given DB. */
	private HealthcareDatabase givenDB;
	
	/**
	 * Instantiates a new full patient view model sub final.
	 *
	 * @param givenAppointmentProperty the given appointment property
	 * @param givenFinalDiagnosisProperty the given final diagnosis property
	 */
	public FullPatientViewModelSubFinal(ObjectProperty<AppointmentData> givenAppointmentProperty, ObjectProperty<FinalDiagnosis> givenFinalDiagnosisProperty) {
		this.givenAppointmentProperty = givenAppointmentProperty;
		this.givenFinalDiagnosisProperty = givenFinalDiagnosisProperty;
		
		this.finalDiagnosisProperty = new SimpleStringProperty();
		
		this.submitEventProperty = new SimpleBooleanProperty(false);
		
		this.addActionHandlers();
	}
	
	/**
	 * Gets the submit event property.
	 *
	 * @return the submit event property
	 */
	public BooleanProperty getSubmitEventProperty() {
		return this.submitEventProperty;
	}
	
	/**
	 * Gets the final diagnosis property.
	 *
	 * @return the final diagnosis property
	 */
	public StringProperty getFinalDiagnosisProperty() {
		return this.finalDiagnosisProperty;
	}
	
	/**
	 * Sets the database.
	 *
	 * @param givenDB the new database
	 */
	public void setDatabase(HealthcareDatabase givenDB) {
		this.givenDB = givenDB;
	}
	
	/**
	 * Load final diagnosis.
	 */
	public void loadFinalDiagnosis() {
		
		QueryResult result = this.givenDB.attemptGetFinalDiagnois(this.givenAppointmentProperty.getValue().getAppointment());
		
		FinalDiagnosis diagnosis = null;
		if(result != null && result.getTuple() != null) {
			diagnosis = new FinalDiagnosis();
			SqlSetter.fillWith(diagnosis, result.getTuple());
		}
		
		this.initFrom(diagnosis);
		
	}
	
	/**
	 * Inits the from.
	 *
	 * @param diagnosis the diagnosis
	 */
	public void initFrom(FinalDiagnosis diagnosis) {
		
		if(diagnosis == null) {
			this.finalDiagnosisProperty.setValue("");
		}else {
			this.finalDiagnosisProperty.setValue(diagnosis.getDiagnosis_result());
		}
		this.givenFinalDiagnosisProperty.setValue(diagnosis);
	}

	/**
	 * Gets the final diagnosis.
	 *
	 * @return the final diagnosis
	 */
	public FinalDiagnosis getFinalDiagnosis() {
		
		FinalDiagnosis checkupData = new FinalDiagnosis(
				this.givenAppointmentProperty.getValue().getAppointment().getAppointment_id(),
				this.finalDiagnosisProperty.getValue());
		
		return checkupData;
	}
	
	/**
	 * Attempt push final diagnosis.
	 *
	 * @return true, if successful
	 */
	public boolean attemptPushFinalDiagnosis() {
		
		FinalDiagnosis finalDiagnosis = this.getFinalDiagnosis();
		if(finalDiagnosis == null) {
			return false;
		}
		
		QueryResult results = this.givenDB.attemptPostTuple(finalDiagnosis);
		
		if (results == null) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Adds the action handlers.
	 */
	private void addActionHandlers() {
		this.submitEventProperty.addListener((evt)->{
			if(this.submitEventProperty.getValue()) {
				this.submitFinalDiagnosis();
			}
		});
	}
	
	/**
	 * Confirm submit.
	 *
	 * @return true, if successful
	 */
	private boolean confirmSubmit() {
		
		Optional<ButtonType> result = FXMLAlert.statusAlert("Final Diagnosis Confirmation", 
										"Confirm Final Diagnosis", "Submitting a final diagnosis prevents further editing and must be removed by an administrator.", AlertType.CONFIRMATION);
		return result.get().getButtonData().equals(ButtonData.OK_DONE);
	}
	
	/**
	 * Submit final diagnosis.
	 */
	private void submitFinalDiagnosis() {
		
		if(!this.confirmSubmit()) {
			return;
		}
		
		FinalDiagnosis diagnosis = this.getFinalDiagnosis();
		
		if(diagnosis == null) {
			FXMLAlert.statusAlert("Submit Final Diagnosis Failed", "The final diagnosis was malformed.", "Add Final Diagnosis failed", AlertType.ERROR);
			return;
		}
		
		if (!this.attemptPushFinalDiagnosis()) {
			FXMLAlert.statusAlert("Add Final Diagnosis Failed", "The final diagnosis did not add successfully.", "Add Final Diagnosis failed", AlertType.ERROR);
		} else {
			FXMLAlert.statusAlert("Add Final Diagnosis Success", "The final diagnosis was added Successfully.", "Add Final Diagnosis Success", AlertType.INFORMATION);
			this.givenFinalDiagnosisProperty.setValue(diagnosis);
		}
	}

}
