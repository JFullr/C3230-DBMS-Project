package edu.westga.cs3230.healthcare_dbms.viewmodel;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
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
 * Viewmodel class for the Appointment window.
 */
public class FullPatientViewModelSubAppt {
	
	private final ObjectProperty<LocalDate> dateProperty;
	
	private final ObjectProperty<SingleSelectionModel<String>> hourProperty;
	private final ObjectProperty<SingleSelectionModel<String>> minuteProperty;
	private final ObjectProperty<SingleSelectionModel<String>> diurnalProperty;
	
	private ObservableList<String> doctorList;
	private ArrayList<DoctorData> availableDoctors;
	
	private ObjectProperty<SingleSelectionModel<String>> doctorSelectionProperty;
	
	private ObservableList<TupleEmbed> availableList;
	private final ObjectProperty<MultipleSelectionModel<TupleEmbed>> availableSelectionProperty;
	
	private ObservableList<TupleEmbed> pastList;
	private final ObjectProperty<MultipleSelectionModel<TupleEmbed>> pastSelectionProperty;
	
	private final StringProperty reasonProperty;
	
	private final BooleanProperty createEventProperty;
	private final BooleanProperty updateEventProperty;
	
	private final ObjectProperty<PatientData> givenPatientProperty;
	private final ObjectProperty<AppointmentData> givenAppointmentProperty;
	
	private HealthcareDatabase givenDB;

	/**
	 * Instantiates a new AppointmentViewModel.
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
		
		/*
		//TEST
		Doctor test = new Doctor(42);
		this.availableDoctors.add(test);
		this.doctorList.add("Test Doctor Name");
		//*/
		
		this.reasonProperty = new SimpleStringProperty();
		
		this.doctorSelectionProperty = new SimpleObjectProperty<SingleSelectionModel<String>>();
		
		this.addActionHandlers();
		
		
		
		
	}
	
	public void setDatabase(HealthcareDatabase givenDB) {
		this.givenDB = givenDB;
	}

	public ObservableList<TupleEmbed> getAvailableList() {
		return availableList;
	}

	public ObjectProperty<MultipleSelectionModel<TupleEmbed>> getAvailableSelectionProperty() {
		return availableSelectionProperty;
	}

	public ObservableList<TupleEmbed> getPastList() {
		return pastList;
	}

	public ObjectProperty<MultipleSelectionModel<TupleEmbed>> getPastSelectionProperty() {
		return pastSelectionProperty;
	}

	public ObjectProperty<SingleSelectionModel<String>> getHourProperty() {
		return this.hourProperty;
	}


	public ObjectProperty<SingleSelectionModel<String>> getMinuteProperty() {
		return this.minuteProperty;
	}

	public ObjectProperty<SingleSelectionModel<String>> getDiurnalProperty() {
		return this.diurnalProperty;
	}

	public ObjectProperty<LocalDate> getDateProperty() {
		return this.dateProperty;
	}

	public ObservableList<String> getDoctorList() {
		return this.doctorList;
	}

	public ObjectProperty<SingleSelectionModel<String>> getDoctorSelectionProperty() {
		return this.doctorSelectionProperty;
	}
	
	public StringProperty getReasonProperty() {
		return this.reasonProperty;
	}

	public BooleanProperty getCreateEventProperty() {
		return createEventProperty;
	}

	public BooleanProperty getUpdateEventProperty() {
		return updateEventProperty;
	}
	
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
		
		//*
		int index = this.doctorSelectionProperty.getValue().getSelectedIndex();
		if(index < 0) {
			return null;
		}
		Integer doctorId = this.availableDoctors.get(index).getPerson().getPerson_id();
		/*/
		//TEST
		Integer doctorId = 42;
		//*/
		
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
	
	private String nullString(String check) {
		if(check == null) {
			return null;
		}
		return check.isEmpty() ? null : check;
	}
	
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
			System.out.println(build.toString());
			return Timestamp.valueOf(build.toString());
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	private void updateUsingAppointment(Appointment appt) {
		if(this.givenAppointmentProperty.getValue() != null) {
			AppointmentData mod = this.givenAppointmentProperty.getValue();
			this.givenAppointmentProperty.setValue(new AppointmentData(appt, mod.getPatient()));
		}else {
			this.givenAppointmentProperty.setValue(new AppointmentData(appt, this.givenPatientProperty.getValue()));
		}
	}
	
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
	
	private void addAppointment() {
		
		AppointmentData appt = this.getAppointment();
		if(appt == null) {
			FXMLAlert.statusAlert("Add Appointment Failed", "The appointment was malformed.", "Add Appointment failed", AlertType.ERROR);
			return;
		}
		
		if (!this.attemptAddAppointment(appt)) {
			FXMLAlert.statusAlert("Add Appointment Failed", "The appointment did not add successfully.", "Add Appointment failed", AlertType.ERROR);
		} else {
			FXMLAlert.statusAlert("Add Appointment Success", "The appointment was added Successfully.", "Add Appointment Success", AlertType.INFORMATION);
			this.givenAppointmentProperty.setValue(appt);
			this.updateAvailableAppointments();
		}
	}
	
	private boolean attemptAddAppointment(AppointmentData appointmentData) {
		
		QueryResult results = this.givenDB.attemptAddAppointment(appointmentData);
		if (results == null || results.getTuple()== null) {
			return false;
		}
		
		results = this.givenDB.getAppointmentBy(appointmentData);
		return true;
	}
	
	private void addValidResults(QueryResult results) {
		addAppointmentResults(results, this.availableList);
	}
	
	private void addInvalidResults(QueryResult results) {
		addAppointmentResults(results, this.pastList);
	}

	private void addAppointmentResults(QueryResult results, ObservableList<TupleEmbed> destination) {
		if(results == null) {
			return;
		}
		for(QueryResult result : results) {
			SqlTuple tup = result.getTuple().transform(tuples -> {
				// Grab the doctor ID
				int doctorId = (int) tuples.get("doctor_id").getValue();

				// Remove all the IDs
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
	
	
	private void updateAppointment() {
		if (!this.attemptUpdateAppointment(this.givenAppointmentProperty.get().getAppointment(), this.getAppointment().getAppointment())) {
			FXMLAlert.statusAlert("Update Appointment Failed", "The appointment did not update successfully.", "Update Appointment failed", AlertType.ERROR);
		} else {
			FXMLAlert.statusAlert("Update Appointment Success", "The appointment updated successfully.", "Update Appointment Success", AlertType.INFORMATION);
			///TODO update Appointments
			this.givenAppointmentProperty.setValue(this.getAppointment());
		}
	}
	
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
		///TODO update appointment list possibly
		return true;
	}
	
}
