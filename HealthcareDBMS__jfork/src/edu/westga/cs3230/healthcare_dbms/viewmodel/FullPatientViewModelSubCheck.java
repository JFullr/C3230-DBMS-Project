package edu.westga.cs3230.healthcare_dbms.viewmodel;

import edu.westga.cs3230.healthcare_dbms.io.database.HealthcareDatabase;
import edu.westga.cs3230.healthcare_dbms.io.database.QueryResult;
import edu.westga.cs3230.healthcare_dbms.model.AppointmentCheckup;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Viewmodel class for the Login window.
 */
public class FullPatientViewModelSubCheck {
	
	private final StringProperty systolicPressureProperty;
	private final StringProperty diatolicPressureProperty;
	private final StringProperty pulseProperty;
	private final StringProperty weightProperty;
	private final StringProperty temperatureProperty;
	
	private HealthcareDatabase givenDB;
	
	public FullPatientViewModelSubCheck() {
		
		this.systolicPressureProperty = new SimpleStringProperty();
		this.diatolicPressureProperty = new SimpleStringProperty();
		this.pulseProperty = new SimpleStringProperty();
		this.weightProperty = new SimpleStringProperty();
		this.temperatureProperty = new SimpleStringProperty();
		
	}
	
	public void initFrom(AppointmentCheckup checkupData) {
		this.systolicPressureProperty.setValue(""+checkupData.getSystolic_pressure());
		this.diatolicPressureProperty.setValue(""+checkupData.getDiastolic_pressure());
		this.pulseProperty.setValue(""+checkupData.getPulse());
		this.weightProperty.setValue(""+checkupData.getWeight());
		this.temperatureProperty.setValue(""+checkupData.getTemperature());
	}
	
	public AppointmentCheckup getCheckup() {
		
		AppointmentCheckup checkupData = null;
		try {
			checkupData = new AppointmentCheckup(
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
	
	public boolean attemptPushCheckup() {
		
		//TODO check if exists, if not add checkup, else update
		
		AppointmentCheckup checkupData = this.getCheckup();
		if(checkupData == null) {
			return false;
		}
		
		QueryResult results = this.givenDB.attemptAddAppointmentCheckup(checkupData);
		
		if (results == null || results.getTuple()== null) {
			return false;
		}
		
		//TODO pull back appointment data mabye
		//results = this.givenDB.getAppointmentBy(appointmentData);
		
		return true;
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
	
}
