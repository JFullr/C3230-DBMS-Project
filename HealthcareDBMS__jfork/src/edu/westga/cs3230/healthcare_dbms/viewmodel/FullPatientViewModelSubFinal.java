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
 * Viewmodel class for the Login window.
 */
public class FullPatientViewModelSubFinal {
	
	private final StringProperty finalDiagnosisProperty;
	
	private final BooleanProperty submitEventProperty;
	
	private final ObjectProperty<AppointmentData> givenAppointmentProperty;
	private final ObjectProperty<FinalDiagnosis> givenFinalDiagnosisProperty;
	
	private HealthcareDatabase givenDB;
	
	public FullPatientViewModelSubFinal(ObjectProperty<AppointmentData> givenAppointmentProperty, ObjectProperty<FinalDiagnosis> givenFinalDiagnosisProperty) {
		this.givenAppointmentProperty = givenAppointmentProperty;
		this.givenFinalDiagnosisProperty = givenFinalDiagnosisProperty;
		
		this.finalDiagnosisProperty = new SimpleStringProperty();
		
		this.submitEventProperty = new SimpleBooleanProperty(false);
		
		this.addActionHandlers();
	}
	
	public BooleanProperty getSubmitEventProperty() {
		return submitEventProperty;
	}
	
	public StringProperty getFinalDiagnosisProperty() {
		return this.finalDiagnosisProperty;
	}
	
	public void setDatabase(HealthcareDatabase givenDB) {
		this.givenDB = givenDB;
	}
	
	public void loadFinalDiagnosis() {
		
		QueryResult result = this.givenDB.attemptGetFinalDiagnois(this.givenAppointmentProperty.getValue().getAppointment());
		
		FinalDiagnosis diagnosis = null;
		if(result != null && result.getTuple() != null) {
			diagnosis = new FinalDiagnosis();
			SqlSetter.fillWith(diagnosis, result.getTuple());
		}
		
		this.initFrom(diagnosis);
		
	}
	
	public void initFrom(FinalDiagnosis diagnosis) {
		
		if(diagnosis == null) {
			this.finalDiagnosisProperty.setValue("");
		}else {
			this.finalDiagnosisProperty.setValue(diagnosis.getDiagnosis_result());
		}
		this.givenFinalDiagnosisProperty.setValue(diagnosis);
	}

	public FinalDiagnosis getFinalDiagnosis() {
		
		FinalDiagnosis checkupData = new FinalDiagnosis(
				this.givenAppointmentProperty.getValue().getAppointment().getAppointment_id(),
				this.finalDiagnosisProperty.getValue());
		
		return checkupData;
	}
	
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
	
	private void addActionHandlers() {
		this.submitEventProperty.addListener((evt)->{
			if(this.submitEventProperty.getValue()) {
				this.submitFinalDiagnosis();
			}
		});
	}
	
	private boolean confirmSubmit() {
		
		Optional<ButtonType> result = FXMLAlert.statusAlert("Final Diagnosis Confirmation", 
										"Confirm Final Diagnosis", "Submitting a final diagnosis prevents further editing and must be removed by an administrator.", AlertType.CONFIRMATION);
		return result.get().getButtonData().equals(ButtonData.OK_DONE);
	}
	
	private void submitFinalDiagnosis() {
		
		if(!this.confirmSubmit()) {
			return;
		}
		
		FinalDiagnosis diagnosis = this.getFinalDiagnosis();
		
		if(diagnosis == null) {
			FXMLAlert.statusAlert("Submit Final Diagnosis Failed", "The fian diagnosis was malformed.", "Add Final Diagnosis failed", AlertType.ERROR);
			return;
		}
		
		if (!this.attemptPushFinalDiagnosis()) {
			FXMLAlert.statusAlert("Add Final Diagnosis Failed", "The appointment did not add successfully.", "Add Final Diagnosis failed", AlertType.ERROR);
		} else {
			FXMLAlert.statusAlert("Add Final Diagnosis Success", "The appointment was added Successfully.", "Add Final Diagnosis Success", AlertType.INFORMATION);
			//TODO activate anything needed -- properties bound should nullify anything already though
		}
	}

}
