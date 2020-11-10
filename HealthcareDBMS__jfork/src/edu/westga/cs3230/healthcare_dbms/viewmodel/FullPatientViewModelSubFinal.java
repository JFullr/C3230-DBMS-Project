package edu.westga.cs3230.healthcare_dbms.viewmodel;

import edu.westga.cs3230.healthcare_dbms.io.database.HealthcareDatabase;
import edu.westga.cs3230.healthcare_dbms.io.database.QueryResult;
import edu.westga.cs3230.healthcare_dbms.model.AppointmentData;
import edu.westga.cs3230.healthcare_dbms.model.FinalDiagnosis;
import edu.westga.cs3230.healthcare_dbms.view.utils.FXMLAlert;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert.AlertType;

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
	
	public void initFrom(FinalDiagnosis diagnosis) {
		this.finalDiagnosisProperty.setValue(diagnosis.getDiagnosis_result());
	}

	public FinalDiagnosis getFinalDiagnosis() {
		
		FinalDiagnosis checkupData = new FinalDiagnosis(this.finalDiagnosisProperty.getValue());
		
		return checkupData;
	}
	
	public boolean attemptPushFinalDiagnosis() {
		
		//TODO check if exists, if not add checkup, else update
		
		FinalDiagnosis finalDiagnosis = this.getFinalDiagnosis();
		if(finalDiagnosis == null) {
			return false;
		}
		
		QueryResult results = this.givenDB.attemptPostTuple(finalDiagnosis);
		
		if (results == null || results.getTuple() == null) {
			return false;
		}
		
		//TODO pull back appointment data mabye
		//results = this.givenDB.getAppointmentBy(appointmentData);
		
		return true;
	}
	
	private void addActionHandlers() {
		this.submitEventProperty.addListener((evt)->{
			this.submitFInalDiagnosis();
		});
	}
	
	private void submitFInalDiagnosis() {
		
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
