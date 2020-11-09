package edu.westga.cs3230.healthcare_dbms.viewmodel;

import edu.westga.cs3230.healthcare_dbms.io.database.HealthcareDatabase;
import edu.westga.cs3230.healthcare_dbms.io.database.QueryResult;
import edu.westga.cs3230.healthcare_dbms.model.AppointmentCheckup;
import edu.westga.cs3230.healthcare_dbms.model.FinalDiagnosis;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Viewmodel class for the Login window.
 */
public class FullPatientViewModelSubFinal {
	
	private final StringProperty finalDiagnosisProperty;
	
	private HealthcareDatabase givenDB;
	
	public FullPatientViewModelSubFinal() {
		this.finalDiagnosisProperty = new SimpleStringProperty();
	}
	
	public StringProperty getFinalDiagnosisProperty() {
		return finalDiagnosisProperty;
	}
	
	public void initFrom(FinalDiagnosis diagnosis) {
		this.finalDiagnosisProperty.setValue(diagnosis.getDiagnosis_result());
	}

	public FinalDiagnosis getDiagnosis() {
		
		FinalDiagnosis checkupData = new FinalDiagnosis(this.finalDiagnosisProperty.getValue());
		
		return checkupData;
	}
	
	public boolean attemptPushFinalDiagnosis() {
		
		//TODO check if exists, if not add checkup, else update
		
		FinalDiagnosis finalDiagnosis = this.getDiagnosis();
		if(finalDiagnosis == null) {
			return false;
		}
		
		/*
		TODO create FinalDiagnosisDAL
		QueryResult results = this.givenDB.attemptAddAppointmentCheckup(checkupData);
		
		if (results == null || results.getTuple()== null) {
			return false;
		}
		
		//TODO pull back appointment data mabye
		//results = this.givenDB.getAppointmentBy(appointmentData);
		
		return true;
		//*/
		return false;
	}
	
}
