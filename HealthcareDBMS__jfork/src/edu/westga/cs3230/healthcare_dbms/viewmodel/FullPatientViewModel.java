package edu.westga.cs3230.healthcare_dbms.viewmodel;

import edu.westga.cs3230.healthcare_dbms.io.database.HealthcareDatabase;
import edu.westga.cs3230.healthcare_dbms.model.Appointment;
import edu.westga.cs3230.healthcare_dbms.model.AppointmentCheckup;
import edu.westga.cs3230.healthcare_dbms.model.AppointmentData;
import edu.westga.cs3230.healthcare_dbms.model.Diagnosis;
import edu.westga.cs3230.healthcare_dbms.model.FinalDiagnosis;
import edu.westga.cs3230.healthcare_dbms.model.PatientData;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

// TODO: Auto-generated Javadoc
/**
 * Viewmodel class for the Login window.
 */
public class FullPatientViewModel {
	
	/** The view model checkup. */
	private FullPatientViewModelSubCheckup viewModelCheckup;
	
	/** The view model appt. */
	private FullPatientViewModelSubAppt viewModelAppt;
	
	/** The view model final. */
	private FullPatientViewModelSubFinal viewModelFinal;
	
	/** The view model test. */
	private FullPatientViewModelSubTest viewModelTest;
	
	/** The view model diag. */
	private FullPatientViewModelSubDiag viewModelDiag;
	
	/** The view model patient. */
	private FullPatientViewModelSubPatient viewModelPatient;
	
	/** The editable appointment property. */
	private final BooleanProperty editableAppointmentProperty;
	
	/** The selected appointment property. */
	private final ObjectProperty<AppointmentData> selectedAppointmentProperty;
	
	/** The selected checkup property. */
	private final ObjectProperty<AppointmentCheckup> selectedCheckupProperty;
	
	/** The selected final diagnosis property. */
	private final ObjectProperty<FinalDiagnosis> selectedFinalDiagnosisProperty;
	
	/** The selected patient property. */
	private final ObjectProperty<PatientData> selectedPatientProperty;
	
	/** The selected diagnosis property. */
	private final ObjectProperty<Diagnosis> selectedDiagnosisProperty;
	
	/** The initial data load. */
	private boolean initialDataLoad;

	/**
	 * Instantiates a new full patient view model.
	 */
	public FullPatientViewModel() {
		
		this.editableAppointmentProperty = new SimpleBooleanProperty(true);
		
		this.selectedCheckupProperty = new SimpleObjectProperty<AppointmentCheckup>();
		this.selectedFinalDiagnosisProperty = new SimpleObjectProperty<FinalDiagnosis>();
		
		this.selectedAppointmentProperty = new SimpleObjectProperty<AppointmentData>();
		this.selectedDiagnosisProperty = new SimpleObjectProperty<Diagnosis>();
		this.selectedPatientProperty = new SimpleObjectProperty<PatientData>();
		
		this.initialDataLoad = false;
		
		this.viewModelCheckup = new FullPatientViewModelSubCheckup(this.selectedAppointmentProperty, this.selectedCheckupProperty);
		this.viewModelAppt = new FullPatientViewModelSubAppt(this.selectedPatientProperty, this.selectedAppointmentProperty);
		this.viewModelFinal = new FullPatientViewModelSubFinal(this.selectedAppointmentProperty, this.selectedFinalDiagnosisProperty);
		this.viewModelTest = new FullPatientViewModelSubTest(this.selectedAppointmentProperty);
		this.viewModelDiag = new FullPatientViewModelSubDiag(this.selectedAppointmentProperty, this.selectedDiagnosisProperty);
		
		this.viewModelPatient = new FullPatientViewModelSubPatient(this.selectedPatientProperty);
		
	}
	
	/**
	 * Gets the view model diag.
	 *
	 * @return the view model diag
	 */
	public FullPatientViewModelSubDiag getViewModelDiag() {
		return this.viewModelDiag;
	}
	
	/**
	 * Gets the view model checkup.
	 *
	 * @return the view model checkup
	 */
	public FullPatientViewModelSubCheckup getViewModelCheckup() {
		return this.viewModelCheckup;
	}
	
	/**
	 * Gets the view model appt.
	 *
	 * @return the view model appt
	 */
	public FullPatientViewModelSubAppt getViewModelAppt() {
		return this.viewModelAppt;
	}
	
	/**
	 * Gets the view model final.
	 *
	 * @return the view model final
	 */
	public FullPatientViewModelSubFinal getViewModelFinal() {
		return this.viewModelFinal;
	}
	
	/**
	 * Gets the view model test.
	 *
	 * @return the view model test
	 */
	public FullPatientViewModelSubTest getViewModelTest() {
		return this.viewModelTest;
	}
	
	/**
	 * Gets the view model patient.
	 *
	 * @return the view model patient
	 */
	public FullPatientViewModelSubPatient getViewModelPatient() {
		return this.viewModelPatient;
	}
	
	/**
	 * Gets the editable appointment property.
	 *
	 * @return the editable appointment property
	 */
	public BooleanProperty getEditableAppointmentProperty() {
		return editableAppointmentProperty;
	}
	
	/**
	 * Gets the selected appointment property.
	 *
	 * @return the selected appointment property
	 */
	public ObjectProperty<AppointmentData> getSelectedAppointmentProperty() {
		return selectedAppointmentProperty;
	}
	
	/**
	 * Gets the selected patient property.
	 *
	 * @return the selected patient property
	 */
	public ObjectProperty<PatientData> getSelectedPatientProperty() {
		return selectedPatientProperty;
	}

	/**
	 * Gets the selected checkup property.
	 *
	 * @return the selected checkup property
	 */
	public ObjectProperty<AppointmentCheckup> getSelectedCheckupProperty() {
		return selectedCheckupProperty;
	}

	/**
	 * Gets the selected final diagnosis property.
	 *
	 * @return the selected final diagnosis property
	 */
	public ObjectProperty<FinalDiagnosis> getSelectedFinalDiagnosisProperty() {
		return selectedFinalDiagnosisProperty;
	}
	
	/**
	 * Gets the selected diagnosis property.
	 *
	 * @return the selected diagnosis property
	 */
	public ObjectProperty<Diagnosis> getSelectedDiagnosisProperty() {
		return selectedDiagnosisProperty;
	}
	
	/**
	 * Sets the database.
	 *
	 * @param givenDB the new database
	 */
	public void setDatabase(HealthcareDatabase givenDB) {
		this.viewModelAppt.setDatabase(givenDB);
		this.viewModelCheckup.setDatabase(givenDB);
		this.viewModelFinal.setDatabase(givenDB);
		this.viewModelTest.setDatabase(givenDB);
		this.viewModelDiag.setDatabase(givenDB);
	}
	
	/**
	 * Sets the selected appointment.
	 *
	 * @param appt the appt
	 * @param canEdit the can edit
	 */
	public void setSelectedAppointment(Appointment appt, boolean canEdit) {
		this.editableAppointmentProperty.setValue(canEdit);
		this.selectedFinalDiagnosisProperty.setValue(null);
		this.setSelectedAppointment(appt);
	}
	
	/**
	 * Sets the selected appointment.
	 *
	 * @param appt the new selected appointment
	 */
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
	
	/**
	 * Load data.
	 */
	public void loadData() {
		if(!this.initialDataLoad) {
			this.viewModelAppt.loadDoctors();
			this.viewModelAppt.updateAvailableAppointments();
			this.viewModelTest.loadLabTests();
			this.initialDataLoad = true;
		}
	}
	
}
