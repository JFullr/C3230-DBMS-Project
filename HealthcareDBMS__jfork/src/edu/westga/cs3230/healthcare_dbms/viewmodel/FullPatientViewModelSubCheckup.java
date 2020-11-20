package edu.westga.cs3230.healthcare_dbms.viewmodel;

import java.sql.SQLException;

import edu.westga.cs3230.healthcare_dbms.io.database.HealthcareDatabase;
import edu.westga.cs3230.healthcare_dbms.io.database.QueryResult;
import edu.westga.cs3230.healthcare_dbms.model.Appointment;
import edu.westga.cs3230.healthcare_dbms.model.AppointmentCheckup;
import edu.westga.cs3230.healthcare_dbms.model.AppointmentData;
import edu.westga.cs3230.healthcare_dbms.model.PatientData;
import edu.westga.cs3230.healthcare_dbms.sql.SqlSetter;
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
public class FullPatientViewModelSubCheckup {
	
	private final StringProperty systolicPressureProperty;
	private final StringProperty diatolicPressureProperty;
	private final StringProperty pulseProperty;
	private final StringProperty weightProperty;
	private final StringProperty temperatureProperty;
	
	private final ObjectProperty<AppointmentCheckup> givenCheckupProperty;
	private final ObjectProperty<AppointmentData> givenAppointmentProperty;
	
	private final BooleanProperty createEventProperty;
	private final BooleanProperty updateEventProperty;
	
	private HealthcareDatabase givenDB;
	
	public FullPatientViewModelSubCheckup(ObjectProperty<AppointmentData> givenAppointmentProperty, ObjectProperty<AppointmentCheckup> givenCheckupProperty) {
		
		this.givenCheckupProperty = givenCheckupProperty;
		this.givenAppointmentProperty = givenAppointmentProperty;
		
		this.createEventProperty = new SimpleBooleanProperty(false);
		this.updateEventProperty = new SimpleBooleanProperty(false);
		
		this.systolicPressureProperty = new SimpleStringProperty();
		this.diatolicPressureProperty = new SimpleStringProperty();
		this.pulseProperty = new SimpleStringProperty();
		this.weightProperty = new SimpleStringProperty();
		this.temperatureProperty = new SimpleStringProperty();
		
		this.addActionHandlers();
	}
	
	public void setDatabase(HealthcareDatabase givenDB) {
		this.givenDB = givenDB;
	}
	
	public StringProperty getSystolicPressureProperty() {
		return systolicPressureProperty;
	}

	public StringProperty getDiatolicPressureProperty() {
		return diatolicPressureProperty;
	}

	public StringProperty getPulseProperty() {
		return pulseProperty;
	}

	public StringProperty getWeightProperty() {
		return weightProperty;
	}

	public StringProperty getTemperatureProperty() {
		return temperatureProperty;
	}
	
	public BooleanProperty getCreateEventProperty() {
		return createEventProperty;
	}

	public BooleanProperty getUpdateEventProperty() {
		return updateEventProperty;
	}
	
	public void initFrom(AppointmentCheckup checkupData) {
		this.systolicPressureProperty.setValue(""+checkupData.getSystolic_pressure());
		this.diatolicPressureProperty.setValue(""+checkupData.getDiastolic_pressure());
		this.pulseProperty.setValue(""+checkupData.getPulse());
		this.weightProperty.setValue(""+checkupData.getWeight());
		this.temperatureProperty.setValue(""+checkupData.getTemperature());
	}
	
	public void loadCheckupData() {
		
		AppointmentCheckup checkup = null;
		
		if(this.givenAppointmentProperty.getValue() != null) {
			Appointment appt = this.givenAppointmentProperty.getValue().getAppointment();
			if(appt != null){
				
				QueryResult result = this.givenDB.getAppointmentCheckupForAppointment(appt);
				if(result != null && result.getTuple() != null) {
					checkup = new AppointmentCheckup();
					SqlSetter.fillWith(checkup, result.getTuple());
				}
				
				
			}
		}
		
		this.initFromCheckup(checkup);
	}
	
	public AppointmentCheckup getCheckup() {
		
		AppointmentCheckup checkupData = null;
		try {
			
			checkupData = new AppointmentCheckup(
				""+this.givenAppointmentProperty.getValue().getAppointment().getAppointment_id(),
				this.systolicPressureProperty.getValue(),
				this.diatolicPressureProperty.getValue(),
				this.pulseProperty.getValue(),
				this.weightProperty.getValue(),
				this.temperatureProperty.getValue()
			);
		} catch (Exception e) {
		}
		
		return checkupData;
	}
	
	public boolean attemptAddCheckup() {
		
		AppointmentCheckup checkupData = this.getCheckup();
		if(checkupData == null) {
			return false;
		}
		
		QueryResult results = this.givenDB.attemptPostTuple(checkupData);
		
		if (results == null) {
			return false;
		}
		
		this.givenCheckupProperty.setValue(checkupData);
		
		return true;
	}
	
	private void initFromCheckup(AppointmentCheckup checkup) {
		
		if(checkup == null) {
			
			this.diatolicPressureProperty.setValue("");
			this.pulseProperty.setValue("");
			this.systolicPressureProperty.setValue("");
			this.temperatureProperty.setValue("");
			this.weightProperty.setValue("");
			
		} else {
			
			this.diatolicPressureProperty.setValue(""+checkup.getDiastolic_pressure());
			this.pulseProperty.setValue(""+checkup.getPulse());
			this.systolicPressureProperty.setValue(""+checkup.getSystolic_pressure());
			this.temperatureProperty.setValue(""+checkup.getTemperature());
			this.weightProperty.setValue(""+checkup.getWeight());
			
		}
		
		this.givenCheckupProperty.setValue(checkup);
		
	}
	
	private void addActionHandlers() {
		this.createEventProperty.addListener((evt)->{
			if(this.createEventProperty.getValue()) {
				this.addCheckup();
			}
		});
		
		this.updateEventProperty.addListener((evt)->{
			if(this.updateEventProperty.getValue()) {
				this.updateCheckup();
			}
		});
	}
	
	private void addCheckup() {
		
		AppointmentCheckup checkup = this.getCheckup();
		if(checkup == null) {
			FXMLAlert.statusAlert("Add Checkup Failed", "The checkup was malformed.", "Add Checkup failed", AlertType.ERROR);
			return;
		}
		
		if (!this.attemptAddCheckup()) {
			FXMLAlert.statusAlert("Add Checkup Failed", "The checkup did not add successfully.", "Add Checkup failed", AlertType.ERROR);
		} else {
			FXMLAlert.statusAlert("Add Checkup Success", "The checkup was added Successfully.", "Add Checkup Success", AlertType.INFORMATION);
			//TODO REFRESH APPOINTMENTS;
		}
	}
	
	private void updateCheckup() {
		if (!this.attemptUpdateCheckup(this.givenCheckupProperty.getValue(), this.getCheckup())) {
			FXMLAlert.statusAlert("Update Checkup Failed", "The checkup did not update successfully.", "Update Checkup failed", AlertType.ERROR);
		} else {
			FXMLAlert.statusAlert("Update Checkup Success", "The checkup updated successfully.", "Update Checkup Success", AlertType.INFORMATION);
			///TODO update Appointments
		}
	}
	
	private boolean attemptUpdateCheckup(AppointmentCheckup existingData, AppointmentCheckup newData) {
		//*
		QueryResult results = this.givenDB.attemptUpdateTuple(newData, existingData);
		if(results == null) {
			return false;
		}
		
		this.givenCheckupProperty.setValue(newData);
		return true; 
		//*/
	}
	
}
