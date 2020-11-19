package edu.westga.cs3230.healthcare_dbms.viewmodel;

import java.sql.Date;
import java.time.LocalDate;

import edu.westga.cs3230.healthcare_dbms.io.database.HealthcareDatabase;
import edu.westga.cs3230.healthcare_dbms.io.database.QueryResult;
import edu.westga.cs3230.healthcare_dbms.model.Address;
import edu.westga.cs3230.healthcare_dbms.model.Appointment;
import edu.westga.cs3230.healthcare_dbms.model.AppointmentCheckup;
import edu.westga.cs3230.healthcare_dbms.model.AppointmentData;
import edu.westga.cs3230.healthcare_dbms.model.Diagnosis;
import edu.westga.cs3230.healthcare_dbms.model.FinalDiagnosis;
import edu.westga.cs3230.healthcare_dbms.model.PatientData;
import edu.westga.cs3230.healthcare_dbms.model.Person;
import edu.westga.cs3230.healthcare_dbms.view.PatientCodeBehind;
import edu.westga.cs3230.healthcare_dbms.view.utils.FXMLAlert;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Alert.AlertType;

/**
 * Viewmodel class for the Login window.
 */
public class FullPatientViewModel {
	
	private FullPatientViewModelSubCheckup viewModelCheckup;
	private FullPatientViewModelSubAppt viewModelAppt;
	private FullPatientViewModelSubFinal viewModelFinal;
	private FullPatientViewModelSubTest viewModelTest;
	private FullPatientViewModelSubDiag viewModelDiag;
	
	private FullPatientViewModelSubPatient viewModelPatient;
	
	private final BooleanProperty editableAppointmentProperty;
	
	private final ObjectProperty<AppointmentData> selectedAppointmentProperty;
	private final ObjectProperty<AppointmentCheckup> selectedCheckupProperty;
	private final ObjectProperty<FinalDiagnosis> selectedFinalDiagnosisProperty;
	private final ObjectProperty<PatientData> selectedPatientProperty;
	private final ObjectProperty<Diagnosis> selectedDiagnosisProperty;
	
	private HealthcareDatabase givenDB;
	private ObjectProperty<Object> givenStore;
	
	private boolean initialDataLoad;

	public FullPatientViewModel() {
		
		this.editableAppointmentProperty = new SimpleBooleanProperty(true);
		
		this.selectedCheckupProperty = new SimpleObjectProperty<AppointmentCheckup>();
		this.selectedFinalDiagnosisProperty = new SimpleObjectProperty<FinalDiagnosis>();
		
		this.selectedAppointmentProperty = new SimpleObjectProperty<AppointmentData>();
		this.selectedDiagnosisProperty = new SimpleObjectProperty<Diagnosis>();
		this.selectedPatientProperty = new SimpleObjectProperty<PatientData>();
		
		this.initialDataLoad = false;
		
		this.viewModelCheckup = new FullPatientViewModelSubCheckup(this.selectedPatientProperty, this.selectedAppointmentProperty, this.selectedCheckupProperty);
		this.viewModelAppt = new FullPatientViewModelSubAppt(this.selectedPatientProperty, this.selectedAppointmentProperty);
		this.viewModelFinal = new FullPatientViewModelSubFinal(this.selectedAppointmentProperty, this.selectedFinalDiagnosisProperty);
		this.viewModelTest = new FullPatientViewModelSubTest(this.selectedPatientProperty, this.selectedAppointmentProperty);
		this.viewModelDiag = new FullPatientViewModelSubDiag(this.selectedAppointmentProperty, this.selectedDiagnosisProperty);
		
		this.viewModelPatient = new FullPatientViewModelSubPatient(this.selectedPatientProperty);
		
	}
	
	public FullPatientViewModelSubDiag getViewModelDiag() {
		return this.viewModelDiag;
	}
	
	public FullPatientViewModelSubCheckup getViewModelCheckup() {
		return this.viewModelCheckup;
	}
	
	public FullPatientViewModelSubAppt getViewModelAppt() {
		return this.viewModelAppt;
	}
	
	public FullPatientViewModelSubFinal getViewModelFinal() {
		return this.viewModelFinal;
	}
	
	public FullPatientViewModelSubTest getViewModelTest() {
		return this.viewModelTest;
	}
	
	public FullPatientViewModelSubPatient getViewModelPatient() {
		return this.viewModelPatient;
	}
	
	public BooleanProperty getEditableAppointmentProperty() {
		return editableAppointmentProperty;
	}
	
	public ObjectProperty<AppointmentData> getSelectedAppointmentProperty() {
		return selectedAppointmentProperty;
	}
	
	public ObjectProperty<PatientData> getSelectedPatientProperty() {
		return selectedPatientProperty;
	}

	public ObjectProperty<AppointmentCheckup> getSelectedCheckupProperty() {
		return selectedCheckupProperty;
	}

	public ObjectProperty<FinalDiagnosis> getSelectedFinalDiagnosisProperty() {
		return selectedFinalDiagnosisProperty;
	}
	
	public ObjectProperty<Diagnosis> getSelectedDiagnosisProperty() {
		return selectedDiagnosisProperty;
	}
	
	public void setDatabase(HealthcareDatabase givenDB) {
		this.givenDB = givenDB;
		this.viewModelAppt.setDatabase(givenDB);
		this.viewModelCheckup.setDatabase(givenDB);
		this.viewModelFinal.setDatabase(givenDB);
		this.viewModelTest.setDatabase(givenDB);
		this.viewModelDiag.setDatabase(givenDB);
	}
	
	public void setSelectedAppointment(Appointment appt, boolean canEdit) {
		this.editableAppointmentProperty.setValue(canEdit);
		this.selectedFinalDiagnosisProperty.setValue(null);
		this.setSelectedAppointment(appt);
	}
	
	public void setSelectedAppointment(Appointment appt) {
		if(this.selectedAppointmentProperty.getValue() == null) {
			this.selectedAppointmentProperty.setValue(new AppointmentData(appt,null));
		} else {
			this.selectedAppointmentProperty.setValue(new AppointmentData(appt,
					this.selectedAppointmentProperty.getValue().getPatient()));
		}
		this.viewModelAppt.initFrom(appt);
		this.viewModelCheckup.loadCheckupData();
		this.viewModelFinal.loadFinalDiagnosis();
		this.viewModelDiag.loadDiagnosis();
		this.viewModelTest.loadLabTestOrders();
		this.viewModelTest.clearOrderQueue();
	}
	
	public void loadData() {
		if(!this.initialDataLoad) {
			this.viewModelAppt.loadDoctors();
			this.viewModelAppt.updateAvailableAppointments();
			this.viewModelTest.loadLabTests();
			this.initialDataLoad = true;
		}
	}
	
}
