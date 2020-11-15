package edu.westga.cs3230.healthcare_dbms.viewmodel;

import java.sql.SQLException;
import java.util.Optional;

import edu.westga.cs3230.healthcare_dbms.io.database.HealthcareDatabase;
import edu.westga.cs3230.healthcare_dbms.io.database.QueryResult;
import edu.westga.cs3230.healthcare_dbms.model.Appointment;
import edu.westga.cs3230.healthcare_dbms.model.AppointmentData;
import edu.westga.cs3230.healthcare_dbms.model.Diagnosis;
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
public class FullPatientViewModelSubDiag {
	
	private final StringProperty diagnosisProperty;
	
	private final BooleanProperty addEventProperty;
	private final BooleanProperty updateEventProperty;
	
	private final ObjectProperty<AppointmentData> givenAppointmentProperty;
	private final ObjectProperty<Diagnosis> givenDiagnosisProperty;
	
	private HealthcareDatabase givenDB;
	
	public FullPatientViewModelSubDiag(ObjectProperty<AppointmentData> givenAppointmentProperty, ObjectProperty<Diagnosis> givenDiagnosisProperty) {
		this.givenAppointmentProperty = givenAppointmentProperty;
		this.givenDiagnosisProperty = givenDiagnosisProperty;
		
		this.diagnosisProperty = new SimpleStringProperty();
		
		this.addEventProperty = new SimpleBooleanProperty(false);
		this.updateEventProperty = new SimpleBooleanProperty(false);
		
		this.addActionHandlers();
	}
	
	public BooleanProperty getAddEventProperty() {
		return addEventProperty;
	}
	
	public BooleanProperty getUpdateEventProperty() {
		return updateEventProperty;
	}
	
	public StringProperty getDiagnosisProperty() {
		return this.diagnosisProperty;
	}
	
	public void setDatabase(HealthcareDatabase givenDB) {
		this.givenDB = givenDB;
	}
	
	public void loadDiagnosis() {
		
		if(this.givenDB == null || this.givenAppointmentProperty.getValue() == null || this.givenAppointmentProperty.getValue().getAppointment() == null) {
			this.initFrom(null);
			return;
		}
		
		QueryResult result = this.givenDB.attemptGetDiagnois(this.givenAppointmentProperty.getValue().getAppointment());
		
		Diagnosis diagnosis = null;
		if(result != null && result.getTuple() != null) {
			diagnosis = new Diagnosis(null, null);
			SqlSetter.fillWith(diagnosis, result.getTuple());
		}
		
		this.initFrom(diagnosis);
		
	}
	
	public void initFrom(Diagnosis diagnosis) {
		
		if(diagnosis == null) {
			this.diagnosisProperty.setValue("");
		}else {
			this.diagnosisProperty.setValue(diagnosis.getDiagnosis_description());
		}
		
		this.givenDiagnosisProperty.setValue(diagnosis);
	}

	public Diagnosis getDiagnosis() {
		
		Diagnosis diagnosis = new Diagnosis(
				this.givenAppointmentProperty.getValue().getAppointment().getAppointment_id(),
				this.diagnosisProperty.getValue());
		
		return diagnosis;
	}
	
	public boolean attemptAddDiagnosis() {
		
		Diagnosis diagnosis = this.getDiagnosis();
		if(diagnosis == null) {
			return false;
		}
		
		
		QueryResult results = this.givenDB.attemptPostTuple(diagnosis);
		
		if (results == null) {
			return false;
		}
		
		return true;
	}
	
	private void addActionHandlers() {
		this.addEventProperty.addListener((evt)->{
			if(this.addEventProperty.getValue()) {
				this.addDiagnosis();
			}
		});
		this.updateEventProperty.addListener((evt)->{
			if(this.updateEventProperty.getValue()) {
				this.updateDiagnosis();
			}
		});
	}
	
	private void addDiagnosis() {
		
		Diagnosis diagnosis = this.getDiagnosis();
		
		if(diagnosis == null) {
			FXMLAlert.statusAlert("Submit Diagnosis Failed", "The diagnosis was malformed.", "Add Diagnosis failed", AlertType.ERROR);
			return;
		}
		
		if (!this.attemptAddDiagnosis()) {
			FXMLAlert.statusAlert("Add Diagnosis Failed", "The diagnosis did not add successfully.", "Add Diagnosis failed", AlertType.ERROR);
		} else {
			FXMLAlert.statusAlert("Add Diagnosis Success", "The diagnosis was added Successfully.", "Add Diagnosis Success", AlertType.INFORMATION);
			//TODO activate anything needed -- properties bound should nullify anything already though
		}
	}
	
	
	
	private void updateDiagnosis() {
		if (!this.attemptUpdateDiagnosis(this.givenDiagnosisProperty.getValue(), this.getDiagnosis())) {
			FXMLAlert.statusAlert("Update Diagnosis Failed", "The diagnosis did not update successfully.", "Update Diagnosis failed", AlertType.ERROR);
		} else {
			FXMLAlert.statusAlert("Update Diagnosis Success", "The diagnosis updated successfully.", "Update Diagnosis Success", AlertType.INFORMATION);
			///TODO update Appointments
		}
	}
	
	private boolean attemptUpdateDiagnosis(Diagnosis currentDiagnosis, Diagnosis newDiagnosis) {
		
		QueryResult results = this.givenDB.attemptUpdateTuple(newDiagnosis, currentDiagnosis);
		
		if (results == null || results.getTuple()== null) {
			return false;
		}
		///TODO
		return true;
	}

}
