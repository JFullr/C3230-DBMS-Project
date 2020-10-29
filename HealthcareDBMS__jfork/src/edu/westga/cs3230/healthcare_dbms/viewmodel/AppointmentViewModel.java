package edu.westga.cs3230.healthcare_dbms.viewmodel;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;

import edu.westga.cs3230.healthcare_dbms.model.Appointment;
import edu.westga.cs3230.healthcare_dbms.model.AppointmentData;
import edu.westga.cs3230.healthcare_dbms.model.PatientData;
import edu.westga.cs3230.healthcare_dbms.model.Person;
import edu.westga.cs3230.healthcare_dbms.view.embed.TupleEmbed;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SingleSelectionModel;

/**
 * Viewmodel class for the Login window.
 */
public class AppointmentViewModel {
	
	private final ObjectProperty<LocalDate> dateProperty;
	
	private final ObjectProperty<SingleSelectionModel<String>> hourProperty;
	private final ObjectProperty<SingleSelectionModel<String>> minuteProperty;
	private final ObjectProperty<SingleSelectionModel<String>> diurnalProperty;
	
	private final BooleanProperty actionEventPressed;
	private final StringProperty actionTextProperty;
	
	private ObservableList<TupleEmbed> tupleList;
	private ObjectProperty<MultipleSelectionModel<TupleEmbed>> tupleSelectionProperty;

	/**
	 * Instantiates a new LoginViewModel.
	 */
	public AppointmentViewModel() {		
		
		this.dateProperty = new SimpleObjectProperty<LocalDate>(null);
		this.hourProperty = new SimpleObjectProperty<SingleSelectionModel<String>>(null);
		this.minuteProperty = new SimpleObjectProperty<SingleSelectionModel<String>>(null);
		this.diurnalProperty = new SimpleObjectProperty<SingleSelectionModel<String>>(null);
		
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
		Person person = ((PatientData) embed.getOperatedObject()).getPerson();
		if(person == null || person.getPerson_id() == null) {
			return null;
		}
		
		Integer person_id = person.getPerson_id();
		
		Integer hour = this.nullInteger(this.getHourProperty().getValue().getSelectedItem());
		Integer minutes =  this.nullInteger(this.getHourProperty().getValue().getSelectedItem());
		String diurnal =  this.nullString(this.getMinuteProperty().getValue().getSelectedItem());
		
		if(hour == null || minutes == null || diurnal == null) {
			return null;
		}
		
		Timestamp stamp = this.makeTimestampFrom(date, hour, minutes, "pm".equalsIgnoreCase(diurnal));
		
		Appointment appt = new Appointment(person_id, stamp);
		
		return new AppointmentData();
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
		
		build.append(":");
		build.append(hour);
		build.append(":");
		build.append(minutes);
		
		try {
			return Timestamp.valueOf(build.toString());
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}


	public ObjectProperty<MultipleSelectionModel<TupleEmbed>>  getTupleSelectionProperty() {
		return this.tupleSelectionProperty;
	}
}
