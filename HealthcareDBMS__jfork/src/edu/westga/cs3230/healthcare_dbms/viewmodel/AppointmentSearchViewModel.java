package edu.westga.cs3230.healthcare_dbms.viewmodel;

import edu.westga.cs3230.healthcare_dbms.io.database.HealthcareDatabase;
import edu.westga.cs3230.healthcare_dbms.io.database.QueryResult;
import edu.westga.cs3230.healthcare_dbms.model.PatientData;
import edu.westga.cs3230.healthcare_dbms.sql.SqlTuple;
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
	
	private HealthcareDatabase givenDB;
	private ObjectProperty<Object> givenStore;

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
	
	public void setDatabaseAccess(HealthcareDatabase givenDB, ObjectProperty<Object> selectedTupleObject) {
		this.givenDB = givenDB;
		this.givenStore = selectedTupleObject;
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
	
	public void searchUpdate() {
		
		if(this.patientSelectionProperty.getValue() == null || this.givenDB == null) {
			return;
		}
		
		TupleEmbed embed = this.patientSelectionProperty.getValue().getSelectedItem();
		if(embed == null || embed.getOperatedObject() == null) {
			return;
		}
		
		Object operated = embed.getOperatedObject();
		if(operated.getClass() != PatientData.class) {
			return;
		}
		
		this.pastList.clear();
		this.availableList.clear();
		
		QueryResult valid = this.givenDB.getValidAppointmentsByPatient((PatientData)operated);
		QueryResult invalid = this.givenDB.getInvalidAppointmentsByPatient((PatientData)operated);
		this.addValidResults(null, null, valid);
		this.addInvalidResults(null, null, invalid);
	}
	
	private void addValidResults(Object operatedOn, Object display, QueryResult results) {
		
		if(results == null) {
			return;
		}
		
		TupleEmbed embed = null;
		for(QueryResult result : results) {
			SqlTuple tup = result.getTuple();
			if(result.getAssociated() == null) {
				embed =  this.createEmbed(operatedOn, display, tup);
			} else {
				embed = this.createEmbed(result.getAssociated(), result.getAssociated(), tup);
			}
			
			final TupleEmbed xbed = embed;
			xbed.getPressedPropertyAction().addListener((evt)->{
				this.givenStore.setValue(xbed.getPressedPropertyAction().getValue());
			});
			
			this.availableList.add(embed);
		}
		
	}
	
	private void addInvalidResults(Object operatedOn, Object display, QueryResult results) {
		
		if(results == null) {
			return;
		}
		
		TupleEmbed embed = null;
		for(QueryResult result : results) {
			SqlTuple tup = result.getTuple();
			if(result.getAssociated() == null) {
				embed =  this.createEmbed(operatedOn, display, tup);
			} else {
				embed = this.createEmbed(null, result.getAssociated(), tup);
			}
			
			this.pastList.add(embed);
		}
		
	}
	
	private TupleEmbed createEmbed(Object operatesOn, Object display, SqlTuple attributes) {
		TupleEmbed embed = new TupleEmbed(operatesOn, display, attributes);
		return embed;
	}
	
	
}
