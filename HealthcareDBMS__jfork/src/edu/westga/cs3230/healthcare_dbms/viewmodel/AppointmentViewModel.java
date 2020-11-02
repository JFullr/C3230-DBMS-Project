package edu.westga.cs3230.healthcare_dbms.viewmodel;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

import edu.westga.cs3230.healthcare_dbms.model.Appointment;
import edu.westga.cs3230.healthcare_dbms.model.AppointmentData;
import edu.westga.cs3230.healthcare_dbms.model.PatientData;
import edu.westga.cs3230.healthcare_dbms.view.embed.TupleEmbed;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SingleSelectionModel;

/**
 * Viewmodel class for the Appointment window.
 */
public class AppointmentViewModel {
	
	private final ObjectProperty<LocalDate> dateProperty;
	
	private final ObjectProperty<SingleSelectionModel<String>> hourProperty;
	private final ObjectProperty<SingleSelectionModel<String>> minuteProperty;
	private final ObjectProperty<SingleSelectionModel<String>> diurnalProperty;
	private final ObjectProperty<Appointment> existingAppointmentProperty;
	
	private final BooleanProperty actionEventPressed;
	private final StringProperty actionTextProperty;
	
	private ObservableList<TupleEmbed> tupleList;
	private ObjectProperty<MultipleSelectionModel<TupleEmbed>> tupleSelectionProperty;

	/**
	 * Instantiates a new AppointmentViewModel.
	 */
	public AppointmentViewModel() {		
		
		this.dateProperty = new SimpleObjectProperty<LocalDate>(null);
		this.hourProperty = new SimpleObjectProperty<SingleSelectionModel<String>>(null);
		this.minuteProperty = new SimpleObjectProperty<SingleSelectionModel<String>>(null);
		this.diurnalProperty = new SimpleObjectProperty<SingleSelectionModel<String>>(null);
		this.existingAppointmentProperty = new SimpleObjectProperty<>();
		
		this.actionEventPressed = new SimpleBooleanProperty(false);
		this.actionTextProperty = new SimpleStringProperty(null);
		
		this.tupleList = FXCollections.observableArrayList();
		this.tupleSelectionProperty = new SimpleObjectProperty<MultipleSelectionModel<TupleEmbed>>();
	}
	
	
	public AppointmentData getAppointment() {


		Date date = null;
		LocalDate time = this.dateProperty.getValue();
		if(time != null) {
			date = Date.valueOf(time);
		}
		
		TupleEmbed embed = this.tupleSelectionProperty.getValue().getSelectedItem();
		if(embed == null) {
			return null;
		}
		
		if(embed.getOperatedObject() == null || embed.getOperatedObject().getClass() != PatientData.class) {
			return null;
		}
		PatientData patient = ((PatientData) embed.getOperatedObject());
		if(patient == null || patient.getPerson() == null || patient.getPerson().getPerson_id() == null) {
			return null;
		}
		
		Integer person_id = patient.getPerson().getPerson_id();
		
		Integer hour = this.nullInteger(this.hourProperty.getValue().getSelectedItem());
		Integer minutes =  this.nullInteger(this.minuteProperty.getValue().getSelectedItem());
		String diurnal =  this.nullString(this.diurnalProperty.getValue().getSelectedItem());
		
		if(hour == null || minutes == null || diurnal == null) {
			return null;
		}
		
		boolean pm = "pm".equalsIgnoreCase(diurnal);
		if(hour == 12 && !pm) {
			hour = 0;
		}
		
		Timestamp stamp = this.makeTimestampFrom(date, hour, minutes, pm);
		
		Appointment appt = new Appointment(person_id, stamp);
		
		return new AppointmentData(appt, patient);
	}

	public BooleanProperty getActionPressedProperty() {
		return this.actionEventPressed;
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


	public BooleanBinding getIsUpdateProperty() {
		return this.existingAppointmentProperty.isNotNull();
	}

	public ObjectProperty<Appointment> getExistingAppointmentProperty() {
		return this.existingAppointmentProperty;
	}

	public ObjectProperty<LocalDate> getDateProperty() {
		return this.dateProperty;
	}
	
	public StringProperty getActionTextProperty() {
		return actionTextProperty;
	}

	public void setActionButtonText(String string) {
		this.actionTextProperty.setValue(string);
	}


	public void populateFrom(ObservableList<TupleEmbed> tuplesByAssociated) {
		this.tupleList.clear();
		this.tupleList.addAll(tuplesByAssociated);
	}


	public ObservableList<TupleEmbed> getTupleList() {
		return this.tupleList;
	}


	public ObjectProperty<MultipleSelectionModel<TupleEmbed>>  getTupleSelectionProperty() {
		return this.tupleSelectionProperty;
	}


	public void initFrom(Appointment appt) {
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
		this.minuteProperty.getValue().select(""+ltime.getMinute());
		if(pm) {
			this.diurnalProperty.getValue().select("PM");
		} else {
			this.diurnalProperty.getValue().select("AM");
		}
		this.existingAppointmentProperty.set(appt);
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
}
