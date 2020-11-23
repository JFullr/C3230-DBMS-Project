package edu.westga.cs3230.healthcare_dbms.viewmodel;

import java.util.ArrayList;

import edu.westga.cs3230.healthcare_dbms.io.database.HealthcareDatabase;
import edu.westga.cs3230.healthcare_dbms.io.database.QueryResult;
import edu.westga.cs3230.healthcare_dbms.model.Appointment;
import edu.westga.cs3230.healthcare_dbms.model.AppointmentCheckup;
import edu.westga.cs3230.healthcare_dbms.model.AppointmentData;
import edu.westga.cs3230.healthcare_dbms.model.Nurse;
import edu.westga.cs3230.healthcare_dbms.model.NurseData;
import edu.westga.cs3230.healthcare_dbms.model.Person;
import edu.westga.cs3230.healthcare_dbms.sql.SqlSetter;
import edu.westga.cs3230.healthcare_dbms.view.utils.FXMLAlert;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.SingleSelectionModel;

/**
 * View model class for the checkup pane.
 *
 * @author Joseph Fuller and Andrew Steinborn
 */
public class FullPatientViewModelSubCheckup {
	
	/** The systolic pressure property. */
	private final StringProperty systolicPressureProperty;
	
	/** The diatolic pressure property. */
	private final StringProperty diatolicPressureProperty;
	
	/** The pulse property. */
	private final StringProperty pulseProperty;
	
	/** The weight property. */
	private final StringProperty weightProperty;
	
	/** The temperature property. */
	private final StringProperty temperatureProperty;
	
	/** The given checkup property. */
	private final ObjectProperty<AppointmentCheckup> givenCheckupProperty;
	
	/** The given appointment property. */
	private final ObjectProperty<AppointmentData> givenAppointmentProperty;
	
	/** The nurse list. */
	private ObservableList<String> nurseList;
	
	/** The available doctors. */
	private ArrayList<NurseData> availableNurses;
	
	/** The doctor selection property. */
	private final ObjectProperty<SingleSelectionModel<String>> nurseSelectionProperty;
	
	/** The create event property. */
	private final BooleanProperty createEventProperty;
	
	/** The update event property. */
	private final BooleanProperty updateEventProperty;
	
	/** The given DB. */
	private HealthcareDatabase givenDB;
	
	/**
	 * Instantiates a new full patient view model sub checkup.
	 *
	 * @param givenAppointmentProperty the given appointment property
	 * @param givenCheckupProperty the given checkup property
	 */
	public FullPatientViewModelSubCheckup(ObjectProperty<AppointmentData> givenAppointmentProperty, ObjectProperty<AppointmentCheckup> givenCheckupProperty) {
		
		this.givenCheckupProperty = givenCheckupProperty;
		this.givenAppointmentProperty = givenAppointmentProperty;
		
		this.createEventProperty = new SimpleBooleanProperty(false);
		this.updateEventProperty = new SimpleBooleanProperty(false);
		
		this.nurseSelectionProperty = new SimpleObjectProperty<SingleSelectionModel<String>>();
		
		this.systolicPressureProperty = new SimpleStringProperty();
		this.diatolicPressureProperty = new SimpleStringProperty();
		this.pulseProperty = new SimpleStringProperty();
		this.weightProperty = new SimpleStringProperty();
		this.temperatureProperty = new SimpleStringProperty();
		
		this.nurseList = FXCollections.observableArrayList();
		this.availableNurses = new ArrayList<NurseData>();
		
		this.addActionHandlers();
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
	 * Gets the nurse list.
	 *
	 * @return the nurse list.
	 */
	public ObservableList<String> getNurseList(){
		return this.nurseList;
	}
	
	/**
	 * Gets the nurse selection property.
	 *
	 * @return the nurse selection property
	 */
	public ObjectProperty<SingleSelectionModel<String>> getNurseSelectionProperty() {
		return nurseSelectionProperty;
	}
	
	/**
	 * Gets the systolic pressure property.
	 *
	 * @return the systolic pressure property
	 */
	public StringProperty getSystolicPressureProperty() {
		return this.systolicPressureProperty;
	}

	/**
	 * Gets the diatolic pressure property.
	 *
	 * @return the diatolic pressure property
	 */
	public StringProperty getDiatolicPressureProperty() {
		return this.diatolicPressureProperty;
	}

	/**
	 * Gets the pulse property.
	 *
	 * @return the pulse property
	 */
	public StringProperty getPulseProperty() {
		return this.pulseProperty;
	}

	/**
	 * Gets the weight property.
	 *
	 * @return the weight property
	 */
	public StringProperty getWeightProperty() {
		return this.weightProperty;
	}

	/**
	 * Gets the temperature property.
	 *
	 * @return the temperature property
	 */
	public StringProperty getTemperatureProperty() {
		return this.temperatureProperty;
	}
	
	/**
	 * Gets the creates the event property.
	 *
	 * @return the creates the event property
	 */
	public BooleanProperty getCreateEventProperty() {
		return this.createEventProperty;
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
	 * Load checkup data.
	 */
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
	
	/**
	 * Load nurses.
	 */
	public void loadNurses() {
		
		this.availableNurses.clear();
		this.nurseList.clear();
		
		QueryResult nurses = this.givenDB.attemptGetNurses();
		
		for(QueryResult result : nurses) {
			if(result.getTuple() != null) {
				Person person = new Person(null, null, null, null, null, null, null, null);
				SqlSetter.fillWith(person, result.getTuple());
				Nurse nurse = new Nurse(person.getPerson_id());
				
				NurseData nurseData = new NurseData(person, nurse);
				this.availableNurses.add(nurseData);
				this.nurseList.add(person.getFname()+" "+person.getLname());
			}
		}
	}
	
	/**
	 * Gets the checkup.
	 *
	 * @return the checkup
	 */
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
	
	/**
	 * Attempt add checkup.
	 *
	 * @return true, if successful
	 */
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
	
	/**
	 * Inits the from checkup.
	 *
	 * @param checkup the checkup
	 */
	private void initFromCheckup(AppointmentCheckup checkup) {
		
		if(checkup == null) {
			
			this.diatolicPressureProperty.setValue("");
			this.pulseProperty.setValue("");
			this.systolicPressureProperty.setValue("");
			this.temperatureProperty.setValue("");
			this.weightProperty.setValue("");
			this.nurseSelectionProperty.getValue().select("");
			
		} else {
			
			this.diatolicPressureProperty.setValue(""+checkup.getDiastolic_pressure());
			this.pulseProperty.setValue(""+checkup.getPulse());
			this.systolicPressureProperty.setValue(""+checkup.getSystolic_pressure());
			this.temperatureProperty.setValue(""+checkup.getTemperature());
			this.weightProperty.setValue(""+checkup.getWeight());
			
			for(int i = 0; i < this.availableNurses.size(); i++) {
				Nurse nurse = this.availableNurses.get(i).getNurse();
				//System.out.println(nurse.getPerson_id()+"  "+checkup.getNurse_id());
				if((int)nurse.getPerson_id() == (int)checkup.getNurse_id()) {
					NurseData n = this.availableNurses.get(i);
					this.nurseSelectionProperty.getValue().select(n.getPerson().getFname()+" "+n.getPerson().getLname());
					break;
				}
			}
			
		}
		
		this.givenCheckupProperty.setValue(checkup);
		
	}
	
	/**
	 * Adds the action handlers.
	 */
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
	
	/**
	 * Adds the checkup.
	 */
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
		}
	}
	
	/**
	 * Update checkup.
	 */
	private void updateCheckup() {
		if (!this.attemptUpdateCheckup(this.givenCheckupProperty.getValue(), this.getCheckup())) {
			FXMLAlert.statusAlert("Update Checkup Failed", "The checkup did not update successfully.", "Update Checkup failed", AlertType.ERROR);
		} else {
			FXMLAlert.statusAlert("Update Checkup Success", "The checkup updated successfully.", "Update Checkup Success", AlertType.INFORMATION);
		}
	}
	
	/**
	 * Attempt update checkup.
	 *
	 * @param existingData the existing data
	 * @param newData the new data
	 * @return true, if successful
	 */
	private boolean attemptUpdateCheckup(AppointmentCheckup existingData, AppointmentCheckup newData) {
		QueryResult results = this.givenDB.attemptUpdateTuple(newData, existingData);
		if(results == null) {
			return false;
		}
		
		this.givenCheckupProperty.setValue(newData);
		return true; 
	}
	
}
