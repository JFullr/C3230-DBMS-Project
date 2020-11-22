package edu.westga.cs3230.healthcare_dbms.viewmodel;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

import edu.westga.cs3230.healthcare_dbms.io.database.HealthcareDatabase;
import edu.westga.cs3230.healthcare_dbms.io.database.QueryResult;
import edu.westga.cs3230.healthcare_dbms.model.Appointment;
import edu.westga.cs3230.healthcare_dbms.model.AppointmentData;
import edu.westga.cs3230.healthcare_dbms.model.Doctor;
import edu.westga.cs3230.healthcare_dbms.model.DoctorData;
import edu.westga.cs3230.healthcare_dbms.model.PatientData;
import edu.westga.cs3230.healthcare_dbms.model.Person;
import edu.westga.cs3230.healthcare_dbms.sql.SqlAttribute;
import edu.westga.cs3230.healthcare_dbms.sql.SqlSetter;
import edu.westga.cs3230.healthcare_dbms.sql.SqlTuple;
import edu.westga.cs3230.healthcare_dbms.view.embed.TupleEmbed;
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
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SingleSelectionModel;

/**
 * Viewmodel class for the Appointment pane.
 *
 * @author Joseph Fuller and Andrew Steinborn
 */
public class FullPatientViewModelSubAppt {
	
	/** The date property. */
	private final ObjectProperty<LocalDate> dateProperty;
	
	/** The hour property. */
	private final ObjectProperty<SingleSelectionModel<String>> hourProperty;
	
	/** The minute property. */
	private final ObjectProperty<SingleSelectionModel<String>> minuteProperty;
	
	/** The diurnal property. */
	private final ObjectProperty<SingleSelectionModel<String>> diurnalProperty;
	
	/** The doctor list. */
	private ObservableList<String> doctorList;
	
	/** The available doctors. */
	private ArrayList<DoctorData> availableDoctors;
	
	/** The doctor selection property. */
	private ObjectProperty<SingleSelectionModel<String>> doctorSelectionProperty;
	
	/** The available list. */
	private ObservableList<TupleEmbed> availableList;
	
	/** The available selection property. */
	private final ObjectProperty<MultipleSelectionModel<TupleEmbed>> availableSelectionProperty;
	
	/** The past list. */
	private ObservableList<TupleEmbed> pastList;
	
	/** The past selection property. */
	private final ObjectProperty<MultipleSelectionModel<TupleEmbed>> pastSelectionProperty;
	
	/** The reason property. */
	private final StringProperty reasonProperty;
	
	/** The create event property. */
	private final BooleanProperty createEventProperty;
	
	/** The update event property. */
	private final BooleanProperty updateEventProperty;
	
	/** The given patient property. */
	private final ObjectProperty<PatientData> givenPatientProperty;
	
	/** The given appointment property. */
	private final ObjectProperty<AppointmentData> givenAppointmentProperty;
	
	/** The given DB. */
	private HealthcareDatabase givenDB;

	/**
	 * Instantiates a new AppointmentViewModel.
	 *
	 * @param givenPatientProperty the given patient property
	 * @param givenAppointmentProperty the given appointment property
	 */
	public FullPatientViewModelSubAppt(ObjectProperty<PatientData> givenPatientProperty, ObjectProperty<AppointmentData> givenAppointmentProperty) {
		
		this.givenPatientProperty = givenPatientProperty;
		this.givenAppointmentProperty = givenAppointmentProperty;
		
		this.createEventProperty = new SimpleBooleanProperty(false);
		this.updateEventProperty = new SimpleBooleanProperty(false);
		
		this.dateProperty = new SimpleObjectProperty<LocalDate>(null);
		
		this.hourProperty = new SimpleObjectProperty<SingleSelectionModel<String>>(null);
		this.minuteProperty = new SimpleObjectProperty<SingleSelectionModel<String>>(null);
		this.diurnalProperty = new SimpleObjectProperty<SingleSelectionModel<String>>(null);
		
		this.doctorList = FXCollections.observableArrayList();
		this.availableDoctors = new ArrayList<DoctorData>();
		
		this.availableList = FXCollections.observableArrayList();
		this.availableSelectionProperty = new SimpleObjectProperty<MultipleSelectionModel<TupleEmbed>>();
		
		this.pastList = FXCollections.observableArrayList();
		this.pastSelectionProperty = new SimpleObjectProperty<MultipleSelectionModel<TupleEmbed>>();
		
		this.reasonProperty = new SimpleStringProperty();
		
		this.doctorSelectionProperty = new SimpleObjectProperty<SingleSelectionModel<String>>();
		
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
	 * Gets the available list.
	 *
	 * @return the available list
	 */
	public ObservableList<TupleEmbed> getAvailableList() {
		return this.availableList;
	}

	/**
	 * Gets the available selection property.
	 *
	 * @return the available selection property
	 */
	public ObjectProperty<MultipleSelectionModel<TupleEmbed>> getAvailableSelectionProperty() {
		return this.availableSelectionProperty;
	}

	/**
	 * Gets the past list.
	 *
	 * @return the past list
	 */
	public ObservableList<TupleEmbed> getPastList() {
		return this.pastList;
	}

	/**
	 * Gets the past selection property.
	 *
	 * @return the past selection property
	 */
	public ObjectProperty<MultipleSelectionModel<TupleEmbed>> getPastSelectionProperty() {
		return this.pastSelectionProperty;
	}

	/**
	 * Gets the hour property.
	 *
	 * @return the hour property
	 */
	public ObjectProperty<SingleSelectionModel<String>> getHourProperty() {
		return this.hourProperty;
	}


	/**
	 * Gets the minute property.
	 *
	 * @return the minute property
	 */
	public ObjectProperty<SingleSelectionModel<String>> getMinuteProperty() {
		return this.minuteProperty;
	}

	/**
	 * Gets the diurnal property.
	 *
	 * @return the diurnal property
	 */
	public ObjectProperty<SingleSelectionModel<String>> getDiurnalProperty() {
		return this.diurnalProperty;
	}

	/**
	 * Gets the date property.
	 *
	 * @return the date property
	 */
	public ObjectProperty<LocalDate> getDateProperty() {
		return this.dateProperty;
	}

	/**
	 * Gets the doctor list.
	 *
	 * @return the doctor list
	 */
	public ObservableList<String> getDoctorList() {
		return this.doctorList;
	}

	/**
	 * Gets the doctor selection property.
	 *
	 * @return the doctor selection property
	 */
	public ObjectProperty<SingleSelectionModel<String>> getDoctorSelectionProperty() {
		return this.doctorSelectionProperty;
	}
	
	/**
	 * Gets the reason property.
	 *
	 * @return the reason property
	 */
	public StringProperty getReasonProperty() {
		return this.reasonProperty;
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
	 * Update available appointments.
	 */
	public void updateAvailableAppointments() {
		
		if(this.givenPatientProperty.getValue() == null || this.givenDB == null) {
			return;
		}
		
		this.pastList.clear();
		this.availableList.clear();
		
		QueryResult valid = this.givenDB.getValidAppointmentsByPatient(this.givenPatientProperty.getValue());
		QueryResult invalid = this.givenDB.getInvalidAppointmentsByPatient(this.givenPatientProperty.getValue());
		this.addValidResults(valid);
		this.addInvalidResults(invalid);
	}

	/**
	 * Inits the from.
	 *
	 * @param appt the appt
	 */
	public void initFrom(Appointment appt) {
		
		if(appt == null) {
			this.minuteProperty.getValue().select("");
			this.hourProperty.getValue().select("");
			this.diurnalProperty.getValue().select("");
			this.dateProperty.setValue(null);
			this.reasonProperty.setValue("");
			this.doctorSelectionProperty.getValue().select("");
			return;
		}
		
		Timestamp time = appt.getDate_time();
		Date date = new Date(time.getTime());
		LocalDate ldate = date.toLocalDate();
		this.dateProperty.setValue(ldate);
		
		LocalTime ltime = time.toLocalDateTime().toLocalTime();
		
		
		int hour = ltime.getHour();
		boolean pm = false;
		if(hour > 11) {
			pm = true;
			if(hour != 12) {
				hour-=12;
			}
		}
		if(hour == 0) {
			hour = 12;
		}
		this.hourProperty.getValue().select(""+hour);
		
		if(ltime.getMinute() == 0) {
			this.minuteProperty.getValue().select("00");
		}else {
			this.minuteProperty.getValue().select(""+ltime.getMinute());
		}
		
		if(pm) {
			this.diurnalProperty.getValue().select("PM");
		} else {
			this.diurnalProperty.getValue().select("AM");
		}
		
		for(int i = 0; i < this.availableDoctors.size(); i++) {
			Doctor d = this.availableDoctors.get(i).getDoctor();
			if((int)d.getPerson_id() == (int)appt.getDoctor_id()) {
				DoctorData p = this.availableDoctors.get(i);
				this.doctorSelectionProperty.getValue().select(p.getPerson().getFname()+" "+p.getPerson().getLname());
				break;
			}
		}
		this.reasonProperty.setValue(appt.getAppointment_reason());
		
		this.updateUsingAppointment(appt);
		
	}
	
	/**
	 * Gets the appointment.
	 *
	 * @return the appointment
	 */
	public AppointmentData getAppointment() {

		Date date = null;
		LocalDate time = this.dateProperty.getValue();
		if(time != null) {
			date = Date.valueOf(time);
		}
		
		PatientData patient = this.givenPatientProperty.getValue();
		if(patient == null || patient.getPerson() == null || patient.getPerson().getPerson_id() == null) {
			System.out.println("FAILED PATIENT");
			return null;
		}
		
		Integer person_id = patient.getPerson().getPerson_id();
		
		Integer hour = this.nullInteger(this.hourProperty.getValue().getSelectedItem());
		Integer minutes =  this.nullInteger(this.minuteProperty.getValue().getSelectedItem());
		String diurnal =  this.nullString(this.diurnalProperty.getValue().getSelectedItem());
		
		int index = this.doctorSelectionProperty.getValue().getSelectedIndex();
		if(index < 0) {
			return null;
		}
		Integer doctorId = this.availableDoctors.get(index).getPerson().getPerson_id();
		
		if(hour == null || minutes == null || diurnal == null || doctorId == null) {
			return null;
		}
		
		boolean pm = "pm".equalsIgnoreCase(diurnal);
		if(hour == 12 && !pm) {
			hour = 0;
		}
		
		Timestamp stamp = this.makeTimestampFrom(date, hour, minutes, pm);
		
		Appointment appt = new Appointment(person_id, stamp, doctorId, this.reasonProperty.getValue());
		
		return new AppointmentData(appt, patient);
	}
	
	/**
	 * Load doctors.
	 */
	public void loadDoctors() {
		
		this.availableDoctors.clear();
		this.doctorList.clear();
		
		QueryResult doctors = this.givenDB.attemptGetDoctors();
		
		for(QueryResult result : doctors) {
			if(result.getTuple() != null) {
				Person person = new Person(null, null, null, null, null, null, null, null);
				SqlSetter.fillWith(person, result.getTuple());
				Doctor doctor = new Doctor(person.getPerson_id());
				
				DoctorData docData = new DoctorData(person, doctor);
				this.availableDoctors.add(docData);
				this.doctorList.add(person.getFname()+" "+person.getLname());
			}
		}
	}
	
	/**
	 * Null string.
	 *
	 * @param check the check
	 * @return the string
	 */
	private String nullString(String check) {
		if(check == null) {
			return null;
		}
		return check.isEmpty() ? null : check;
	}
	
	/**
	 * Null integer.
	 *
	 * @param check the check
	 * @return the integer
	 */
	private Integer nullInteger(String check) {
		try {
			if(check == null) {
				return null;
			}
			return check.isEmpty() ? null : Integer.parseInt(check);
		}catch(Exception e) {
			return null;
		}
	}
	
	/**
	 * Make timestamp from.
	 *
	 * @param base the base
	 * @param hour the hour
	 * @param minutes the minutes
	 * @param PM the pm
	 * @return the timestamp
	 */
	private Timestamp makeTimestampFrom(Date base, Integer hour, Integer minutes, boolean PM) {
		
		if(base == null || hour == null || minutes == null) {
			return null;
		}
		
		if(PM && hour != 12) {
			hour+=12;
		}
		
		StringBuilder build = new StringBuilder(base.toString());
		
		build.append(" ");
		build.append(String.format("%02d", hour));
		build.append(":");
		build.append(String.format("%02d", minutes));
		build.append(":00");
		
		try {
			return Timestamp.valueOf(build.toString());
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	/**
	 * Update using appointment.
	 *
	 * @param appt the appt
	 */
	private void updateUsingAppointment(Appointment appt) {
		if(this.givenAppointmentProperty.getValue() != null) {
			AppointmentData mod = this.givenAppointmentProperty.getValue();
			this.givenAppointmentProperty.setValue(new AppointmentData(appt, mod.getPatient()));
		}else {
			this.givenAppointmentProperty.setValue(new AppointmentData(appt, this.givenPatientProperty.getValue()));
		}
	}
	
	/**
	 * Adds the action handlers.
	 */
	private void addActionHandlers() {
		this.createEventProperty.addListener((evt)->{
			if(this.createEventProperty.getValue()) {
				this.addAppointment();
			}
		});
		
		this.updateEventProperty.addListener((evt)->{
			if(this.updateEventProperty.getValue()) {
				this.updateAppointment();
			}
		});
	}
	
	/**
	 * Adds the appointment.
	 */
	private void addAppointment() {
		
		AppointmentData appt = this.getAppointment();
		if(appt == null) {
			FXMLAlert.statusAlert("Add Appointment Failed", "The appointment was malformed. Please double check your input and try again.", "Add Appointment failed", AlertType.ERROR);
			return;
		}
		
		Timestamp time = appt.getAppointment().getDate_time();
		if(time.before(Timestamp.valueOf(LocalDateTime.now()))) {
			FXMLAlert.statusAlert("Add Appointment Failed", "The appointment specified is in the past. Please select a time in the future.", "Add Appointment failed due to being in the past", AlertType.ERROR);
			return;
		}
		
		if (!this.attemptAddAppointment(appt)) {
			FXMLAlert.statusAlert("Add Appointment Failed", "The appointment conflicts with an existing appointment.", "Add Appointment failed", AlertType.ERROR);
		} else {
			FXMLAlert.statusAlert("Add Appointment Success", "The appointment was added Successfully.", "Add Appointment Success", AlertType.INFORMATION);
			this.givenAppointmentProperty.setValue(appt);
			this.updateAvailableAppointments();
		}
	}
	
	/**
	 * Attempt add appointment.
	 *
	 * @param appointmentData the appointment data
	 * @return true, if successful
	 */
	private boolean attemptAddAppointment(AppointmentData appointmentData) {
		
		QueryResult results = this.givenDB.attemptAddAppointment(appointmentData);
		if (results == null || results.getTuple()== null) {
			return false;
		}
		
		results = this.givenDB.getAppointmentBy(appointmentData);
		this.updateAvailableAppointments();
		return true;
	}
	
	/**
	 * Adds the valid results.
	 *
	 * @param results the results
	 */
	private void addValidResults(QueryResult results) {
		this.addAppointmentResults(results, this.availableList);
	}
	
	/**
	 * Adds the invalid results.
	 *
	 * @param results the results
	 */
	private void addInvalidResults(QueryResult results) {
		this.addAppointmentResults(results, this.pastList);
	}

	/**
	 * Adds the appointment results.
	 *
	 * @param results the results
	 * @param destination the destination
	 */
	private void addAppointmentResults(QueryResult results, ObservableList<TupleEmbed> destination) {
		if(results == null) {
			return;
		}
		for(QueryResult result : results) {
			SqlTuple tup = result.getTuple().transform(tuples -> {
				int doctorId = (int) tuples.get("doctor_id").getValue();

				tuples.keySet().removeIf(key -> key.endsWith("_id"));

				Optional<String> doctorName = this.availableDoctors.stream()
						.filter(doctorData -> doctorData.getDoctor().getPerson_id() == doctorId)
						.map(doctorData -> doctorData.getPerson().fullName()).findFirst();
				if (doctorName.isPresent()) {
					tuples.put("doctor", new SqlAttribute("doctor", doctorName.get()));
				}
			});
			TupleEmbed embed = new TupleEmbed(result.getAssociated(), result.getAssociated(), tup);
			destination.add(embed);
		}
	}
	
	
	/**
	 * Update appointment.
	 */
	private void updateAppointment() {
		if (!this.attemptUpdateAppointment(this.givenAppointmentProperty.get().getAppointment(), this.getAppointment().getAppointment())) {
			FXMLAlert.statusAlert("Update Appointment Failed", "The appointment did not update successfully.", "Update Appointment failed", AlertType.ERROR);
		} else {
			FXMLAlert.statusAlert("Update Appointment Success", "The appointment updated successfully.", "Update Appointment Success", AlertType.INFORMATION);
			this.givenAppointmentProperty.setValue(this.getAppointment());
		}
	}
	
	/**
	 * Attempt update appointment.
	 *
	 * @param existingData the existing data
	 * @param newData the new data
	 * @return true, if successful
	 */
	private boolean attemptUpdateAppointment(Appointment existingData, Appointment newData) {
		
		QueryResult results;
		try {
			results = this.givenDB.attemptUpdateAppointment(existingData, newData);
		} catch (SQLException e) {
			return false;
		}
		
		if (results == null) {
			return false;
		}
		results = this.givenDB.getAppointmentBy(new AppointmentData(newData,null));

		this.updateAvailableAppointments();
		return true;
	}
	
}
