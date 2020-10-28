package edu.westga.cs3230.healthcare_dbms.viewmodel;

import java.time.LocalDate;

import edu.westga.cs3230.healthcare_dbms.model.Appointment;
import edu.westga.cs3230.healthcare_dbms.model.Login;
import edu.westga.cs3230.healthcare_dbms.view.embed.TupleEmbed;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
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
	}
	
	
	public Appointment getAppointment() {
		
		return new Appointment();
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
	

}
