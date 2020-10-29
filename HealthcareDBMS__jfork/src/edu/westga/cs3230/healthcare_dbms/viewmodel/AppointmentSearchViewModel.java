package edu.westga.cs3230.healthcare_dbms.viewmodel;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;

import edu.westga.cs3230.healthcare_dbms.model.Appointment;
import edu.westga.cs3230.healthcare_dbms.model.AppointmentData;
import edu.westga.cs3230.healthcare_dbms.model.PatientData;
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
 * Viewmodel class for the Appointment window.
 */
public class AppointmentSearchViewModel {
	
	private final BooleanProperty actionEventPressed;
	private final StringProperty actionTextProperty;
	
	private ObservableList<TupleEmbed> patientList;
	private final ObjectProperty<MultipleSelectionModel<TupleEmbed>> patientSelectionProperty;
	
	private ObservableList<TupleEmbed> availableList;
	private final ObjectProperty<MultipleSelectionModel<TupleEmbed>> availableSelectionProperty;
	
	private ObservableList<TupleEmbed> pastList;
	private final ObjectProperty<MultipleSelectionModel<TupleEmbed>> pastSelectionProperty;

	/**
	 * Instantiates a new AppointmentViewModel.
	 */
	public AppointmentSearchViewModel() {		
		
		this.actionEventPressed = new SimpleBooleanProperty(false);
		this.actionTextProperty = new SimpleStringProperty(null);
		
		this.patientList = FXCollections.observableArrayList();
		this.patientSelectionProperty = new SimpleObjectProperty<MultipleSelectionModel<TupleEmbed>>();
		
		this.availableList = FXCollections.observableArrayList();
		this.availableSelectionProperty = new SimpleObjectProperty<MultipleSelectionModel<TupleEmbed>>();
		
		this.pastList = FXCollections.observableArrayList();
		this.pastSelectionProperty = new SimpleObjectProperty<MultipleSelectionModel<TupleEmbed>>();
	}
	
	/*
	public AppointmentData getAppointment() {

		
		Date date = null;
		LocalDate time = this.dateProperty.getValue();
		if(time != null) {
			date = Date.valueOf(time);
		}
		
		TupleEmbed embed = this.patientSelectionProperty.getValue().getSelectedItem();
		if(embed == null) {
			return null;
		}
		
		
		return new AppointmentData(appt, patient);
	}
	*/

	public BooleanProperty getActionPressedProperty() {
		return this.actionEventPressed;
	}
	
	public StringProperty getActionTextProperty() {
		return actionTextProperty;
	}

	public void setActionButtonText(String string) {
		this.actionTextProperty.setValue(string);
	}


	public void populatePatientsFrom(ObservableList<TupleEmbed> tuplesByAssociated) {
		this.patientList.clear();
		this.patientList.addAll(tuplesByAssociated);
		this.availableList.clear();
		this.pastList.clear();
	}


	public ObservableList<TupleEmbed> getPatientList() {
		return this.patientList;
	}
	
	public ObservableList<TupleEmbed> getAvailableList() {
		return this.availableList;
	}
	
	public ObservableList<TupleEmbed> getPastList() {
		return this.pastList;
	}

	public ObjectProperty<MultipleSelectionModel<TupleEmbed>>  getPatientSelectionProperty() {
		return this.patientSelectionProperty;
	}
	
	public ObjectProperty<MultipleSelectionModel<TupleEmbed>>  getAvailableSelectionProperty() {
		return this.availableSelectionProperty;
	}
	
	public ObjectProperty<MultipleSelectionModel<TupleEmbed>>  getPastSelectionProperty() {
		return this.pastSelectionProperty;
	}
}
