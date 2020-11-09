package edu.westga.cs3230.healthcare_dbms.viewmodel;

import edu.westga.cs3230.healthcare_dbms.io.HealthcareIoConstants;
import edu.westga.cs3230.healthcare_dbms.io.database.HealthcareDatabase;
import edu.westga.cs3230.healthcare_dbms.io.database.QueryResult;
import edu.westga.cs3230.healthcare_dbms.model.AppointmentData;
import edu.westga.cs3230.healthcare_dbms.model.PatientData;
import edu.westga.cs3230.healthcare_dbms.sql.SqlTuple;
import edu.westga.cs3230.healthcare_dbms.view.AppointmentCodeBehind;
import edu.westga.cs3230.healthcare_dbms.view.embed.TupleEmbed;
import edu.westga.cs3230.healthcare_dbms.view.utils.FXMLWindow;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.MultipleSelectionModel;

/**
 * Viewmodel class for the Login window.
 */
public class FullPatientViewModelSubControl {
	
	private ObservableList<TupleEmbed> availableList;
	private final ObjectProperty<MultipleSelectionModel<TupleEmbed>> availableSelectionProperty;
	
	private ObservableList<TupleEmbed> pastList;
	private final ObjectProperty<MultipleSelectionModel<TupleEmbed>> pastSelectionProperty;
	
	private HealthcareDatabase givenDB;
	
	public FullPatientViewModelSubControl() {
		
		this.availableList = FXCollections.observableArrayList();
		this.availableSelectionProperty = new SimpleObjectProperty<MultipleSelectionModel<TupleEmbed>>();
		
		this.pastList = FXCollections.observableArrayList();
		this.pastSelectionProperty = new SimpleObjectProperty<MultipleSelectionModel<TupleEmbed>>();
		
	}
	
	public ObservableList<TupleEmbed> getAvailableList() {
		return this.availableList;
	}
	
	public ObservableList<TupleEmbed> getPastList() {
		return this.pastList;
	}
	
	public void searchUpdate(PatientData patient) {
		
		if(this.givenDB == null || patient == null || patient.getPerson() == null) {
			return;
		}
		
		this.pastList.clear();
		this.availableList.clear();
		
		QueryResult valid = this.givenDB.getValidAppointmentsByPatient(patient);
		QueryResult invalid = this.givenDB.getInvalidAppointmentsByPatient(patient);
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
			
			/*
			final TupleEmbed xbed = embed;
			xbed.getPressedPropertyAction().addListener((evt)->{
				this.givenStore.setValue(xbed.getPressedPropertyAction().getValue());
			});
			*/
			
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
	
	public boolean attemptAddAppointment(AppointmentData appointmentData) {
		
		QueryResult results = this.givenDB.attemptAddAppointment(appointmentData);
		if (results == null || results.getTuple()== null) {
			return false;
		}
		
		results = this.givenDB.getAppointmentBy(appointmentData);
		
		//this.addResults(patientData, patientData.getPerson(), results);
		return true;
	}
	
	private TupleEmbed createEmbed(Object operatesOn, Object display, SqlTuple attributes) {
		TupleEmbed embed = new TupleEmbed(operatesOn, display, attributes);
		return embed;
	}

	public ObjectProperty<MultipleSelectionModel<TupleEmbed>> getAvailableSelectionProperty() {
		return availableSelectionProperty;
	}

	public ObjectProperty<MultipleSelectionModel<TupleEmbed>> getPastSelectionProperty() {
		return pastSelectionProperty;
	}
	
}
