package edu.westga.cs3230.healthcare_dbms.viewmodel;

import edu.westga.cs3230.healthcare_dbms.io.database.HealthcareDatabase;
import edu.westga.cs3230.healthcare_dbms.io.database.QueryResult;
import edu.westga.cs3230.healthcare_dbms.model.AppointmentData;
import edu.westga.cs3230.healthcare_dbms.model.Diagnosis;
import edu.westga.cs3230.healthcare_dbms.sql.SqlSetter;
import edu.westga.cs3230.healthcare_dbms.view.utils.FXMLAlert;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert.AlertType;

// TODO: Auto-generated Javadoc
/**
 * Viewmodel class for the Login window.
 */
public class FullPatientViewModelSubDiag {
	
	/** The diagnosis property. */
	private final StringProperty diagnosisProperty;
	
	/** The add event property. */
	private final BooleanProperty addEventProperty;
	
	/** The update event property. */
	private final BooleanProperty updateEventProperty;
	
	/** The given appointment property. */
	private final ObjectProperty<AppointmentData> givenAppointmentProperty;
	
	/** The given diagnosis property. */
	private final ObjectProperty<Diagnosis> givenDiagnosisProperty;
	
	/** The given DB. */
	private HealthcareDatabase givenDB;
	
	/**
	 * Instantiates a new full patient view model sub diag.
	 *
	 * @param givenAppointmentProperty the given appointment property
	 * @param givenDiagnosisProperty the given diagnosis property
	 */
	public FullPatientViewModelSubDiag(ObjectProperty<AppointmentData> givenAppointmentProperty, ObjectProperty<Diagnosis> givenDiagnosisProperty) {
		this.givenAppointmentProperty = givenAppointmentProperty;
		this.givenDiagnosisProperty = givenDiagnosisProperty;
		
		this.diagnosisProperty = new SimpleStringProperty();
		
		this.addEventProperty = new SimpleBooleanProperty(false);
		this.updateEventProperty = new SimpleBooleanProperty(false);
		
		this.addActionHandlers();
	}
	
	/**
	 * Gets the adds the event property.
	 *
	 * @return the adds the event property
	 */
	public BooleanProperty getAddEventProperty() {
		return this.addEventProperty;
	}
	
	/**
	 * Gets the update event property.
	 *
	 * @return the update event property
	 */
	public BooleanProperty getUpdateEventProperty() {
		return this.updateEventProperty;
	}
	
	/**
	 * Gets the diagnosis property.
	 *
	 * @return the diagnosis property
	 */
	public StringProperty getDiagnosisProperty() {
		return this.diagnosisProperty;
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
	 * Load diagnosis.
	 */
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
	
	/**
	 * Inits the from.
	 *
	 * @param diagnosis the diagnosis
	 */
	public void initFrom(Diagnosis diagnosis) {
		
		if(diagnosis == null) {
			this.diagnosisProperty.setValue("");
		}else {
			this.diagnosisProperty.setValue(diagnosis.getDiagnosis_description());
		}
		
		this.givenDiagnosisProperty.setValue(diagnosis);
	}

	/**
	 * Gets the diagnosis.
	 *
	 * @return the diagnosis
	 */
	public Diagnosis getDiagnosis() {
		
		Diagnosis diagnosis = new Diagnosis(
				this.givenAppointmentProperty.getValue().getAppointment().getAppointment_id(),
				this.diagnosisProperty.getValue());
		
		return diagnosis;
	}
	
	/**
	 * Attempt add diagnosis.
	 *
	 * @return true, if successful
	 */
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
	
	/**
	 * Adds the action handlers.
	 */
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
	
	/**
	 * Adds the diagnosis.
	 */
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
			this.givenDiagnosisProperty.setValue(diagnosis);
		}
	}
	
	
	
	/**
	 * Update diagnosis.
	 */
	private void updateDiagnosis() {
		
		Diagnosis diag = this.getDiagnosis();
		
		if (!this.attemptUpdateDiagnosis(this.givenDiagnosisProperty.getValue(), diag)) {
			FXMLAlert.statusAlert("Update Diagnosis Failed", "The diagnosis did not update successfully.", "Update Diagnosis failed", AlertType.ERROR);
		} else {
			FXMLAlert.statusAlert("Update Diagnosis Success", "The diagnosis updated successfully.", "Update Diagnosis Success", AlertType.INFORMATION);
			this.givenDiagnosisProperty.setValue(diag);
		}
	}
	
	/**
	 * Attempt update diagnosis.
	 *
	 * @param currentDiagnosis the current diagnosis
	 * @param newDiagnosis the new diagnosis
	 * @return true, if successful
	 */
	private boolean attemptUpdateDiagnosis(Diagnosis currentDiagnosis, Diagnosis newDiagnosis) {
		
		QueryResult results = this.givenDB.attemptUpdateTuple(newDiagnosis, currentDiagnosis);
		
		if (results == null) {
			return false;
		}
		
		return true;
	}

}
