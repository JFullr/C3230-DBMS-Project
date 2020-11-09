package edu.westga.cs3230.healthcare_dbms.viewmodel;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import edu.westga.cs3230.healthcare_dbms.io.database.HealthcareDatabase;
import edu.westga.cs3230.healthcare_dbms.io.database.QueryResult;
import edu.westga.cs3230.healthcare_dbms.model.AppointmentData;
import edu.westga.cs3230.healthcare_dbms.model.LabTest;
import edu.westga.cs3230.healthcare_dbms.model.LabTestOrder;
import edu.westga.cs3230.healthcare_dbms.model.PatientData;
import edu.westga.cs3230.healthcare_dbms.sql.SqlAttribute;
import edu.westga.cs3230.healthcare_dbms.sql.SqlGetter;
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
import javafx.scene.control.SingleSelectionModel;

/**
 * Viewmodel class for the Appointment window.
 */
public class FullPatientViewModelSubTest {

	private final BooleanProperty queueTestEventProperty;
	private final BooleanProperty orderTestsEventProperty;
	private final BooleanProperty removeTestEventProperty;
	private final BooleanProperty removeAllTestsEventProperty;

	private final StringProperty testCostProperty;
	private final StringProperty testDescProperty;
	private final ObjectProperty<LocalDate> testDateProperty;

	private ArrayList<LabTest> availableTests;
	private ObservableList<String> testsList;
	private ObservableList<TupleEmbed> testsOrderList;
	private ObservableList<TupleEmbed> testStatusList;

	private final ObjectProperty<MultipleSelectionModel<TupleEmbed>> testListOrderSelectionProperty;
	private final ObjectProperty<SingleSelectionModel<String>> testDropSelectionProperty;

	private HealthcareDatabase givenDB;
	private ObjectProperty<Object> givenStore;
	
	private final ObjectProperty<PatientData> givenPatientProperty;
	private final ObjectProperty<AppointmentData> givenAppointmentProperty;

	/**
	 * Instantiates a new AppointmentViewModel.
	 */
	public FullPatientViewModelSubTest(ObjectProperty<PatientData> patientProperty, ObjectProperty<AppointmentData> appointmentProperty) {

		this.givenAppointmentProperty = appointmentProperty;
		this.givenPatientProperty = patientProperty;
		
		this.queueTestEventProperty = new SimpleBooleanProperty(false);
		this.orderTestsEventProperty = new SimpleBooleanProperty(false);
		this.removeTestEventProperty = new SimpleBooleanProperty(false);
		this.removeAllTestsEventProperty = new SimpleBooleanProperty(false);

		this.testListOrderSelectionProperty = new SimpleObjectProperty<MultipleSelectionModel<TupleEmbed>>();
		this.testDropSelectionProperty = new SimpleObjectProperty<SingleSelectionModel<String>>();

		this.testsList = FXCollections.observableArrayList();
		this.testsOrderList = FXCollections.observableArrayList();
		this.testStatusList = FXCollections.observableArrayList();

		this.testCostProperty = new SimpleStringProperty();
		this.testDescProperty = new SimpleStringProperty();
		this.testDateProperty = new SimpleObjectProperty<LocalDate>();
		
		this.availableTests = new ArrayList<LabTest>();

		this.addActionHandlers();

		/*
		 * this.testPicker.setItems(this.viewModel.getViewModelTest().getTestsList());
		 * this.testPicker.selectionModelProperty().bindBidirectional(this.viewModel.
		 * getViewModelTest().getTestDropSelectionProperty());
		 * 
		 * this.testOrderViewList.selectionModelProperty().bindBidirectional(this.
		 * viewModel.getViewModelTest().getTestOrderSelectionProperty());
		 * this.testCostField.textProperty().bindBidirectional(this.viewModel.
		 * getViewModelTest().getActionTextProperty() this.testDescField
		 * this.testDatePicker this.testPicker this.testOrderList
		 * this.testRemoveSelButton this.testRemoveAllButton this.testOrderButton
		 * this.addTestButton
		 */

	}

	// *
	public ArrayList<LabTestOrder> getLabTestOrders() {
		ArrayList<LabTestOrder> orderedTests = new ArrayList<LabTestOrder>();
		// TODO reconstruct from tuple list of orders
		return orderedTests;
	}
	// */

	public void setDatabaseAccess(HealthcareDatabase givenDB, ObjectProperty<Object> selectedTupleObject) {
		this.givenDB = givenDB;
		this.givenStore = selectedTupleObject;
	}

	public ObservableList<String> getTestsList() {
		return this.testsList;
	}

	public ObservableList<TupleEmbed> getTestStatusList() {
		return this.testStatusList;
	}
	
	public ObservableList<TupleEmbed> getTestsOrderList() {
		return this.testsOrderList;
	}

	public ObjectProperty<MultipleSelectionModel<TupleEmbed>> getTestListOrderSelectionProperty() {
		return this.testListOrderSelectionProperty;
	}

	public ObjectProperty<SingleSelectionModel<String>> getTestDropSelectionProperty() {
		return this.testDropSelectionProperty;
	}

	public StringProperty getTestCostProperty() {
		return testCostProperty;
	}

	public StringProperty getTestDescProperty() {
		return testDescProperty;
	}

	public ObjectProperty<LocalDate> getTestDateProperty() {
		return testDateProperty;
	}

	public BooleanProperty getRemoveAllTestsEventProperty() {
		return removeAllTestsEventProperty;
	}

	public BooleanProperty getRemoveTestEventProperty() {
		return removeTestEventProperty;
	}

	public BooleanProperty getOrderTestsEventProperty() {
		return orderTestsEventProperty;
	}

	public BooleanProperty getQueueTestEventProperty() {
		return queueTestEventProperty;
	}
	
	public LabTestOrder getLabTestOrder(){
		
		int index = this.testListOrderSelectionProperty.getValue().getSelectedIndex();
		if(index < 0) {
			return null;
		}
		Integer testId = this.availableTests.get(index).getLab_test_id();
		if(testId == null) {
			return null;
		}
		
		Integer apptId = this.givenAppointmentProperty.getValue().getAppointment().getAppointment_id();
		if(apptId == null) {
			return null;
		}
		
		Date dob = null;
		LocalDate time = this.testDateProperty.getValue();
		if(time != null) {
			dob = Date.valueOf(time);
		}
		
		LabTestOrder order = new LabTestOrder(testId, apptId, dob);
		
		return order;
	}

	private void addActionHandlers() {
		
		this.queueTestEventProperty.addListener((evt) -> {
			if (this.queueTestEventProperty.getValue()) {
				addTestOrder();
			}
		});
		
		this.orderTestsEventProperty.addListener((evt) -> {
			if (this.orderTestsEventProperty.getValue()) {
				
			}
		});
		
		this.removeTestEventProperty.addListener((evt) -> {
			if (this.removeTestEventProperty.getValue()) {
				
			}
		});
		
		this.removeAllTestsEventProperty.addListener((evt) -> {
			if (this.removeAllTestsEventProperty.getValue()) {
				this.testsOrderList.clear();
			}
		});
		
	}
	
	private void addTestOrder() {
		LabTestOrder order = this.getLabTestOrder();
		if(order == null) {
			return;
		}
		
		int index = this.testListOrderSelectionProperty.getValue().getSelectedIndex();
		SqlTuple desc = SqlGetter.getFrom(order);
		desc.add(new SqlAttribute("description", this.availableTests.get(index).getTest_description()));
		
		TupleEmbed embed = this.createEmbed(order, order, desc);
		this.testsOrderList.add(embed);
	}

	private void addValidResults(Object operatedOn, Object display, QueryResult results) {

		if (results == null) {
			return;
		}

		TupleEmbed embed = null;
		for (QueryResult result : results) {
			SqlTuple tup = result.getTuple();
			if (result.getAssociated() == null) {
				embed = this.createEmbed(operatedOn, display, tup);
			} else {
				embed = this.createEmbed(result.getAssociated(), result.getAssociated(), tup);
			}

			final TupleEmbed xbed = embed;
			xbed.getPressedPropertyAction().addListener((evt) -> {
				this.givenStore.setValue(xbed.getPressedPropertyAction().getValue());
			});

			// this.availableList.add(embed);
		}

	}

	//TODO use to generate test order embeds
	private TupleEmbed createEmbed(Object operatesOn, Object display, SqlTuple attributes) {
		TupleEmbed embed = new TupleEmbed(operatesOn, display, attributes);
		return embed;
	}

}
