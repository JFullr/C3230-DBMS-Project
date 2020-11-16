package edu.westga.cs3230.healthcare_dbms.viewmodel;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import edu.westga.cs3230.healthcare_dbms.io.database.HealthcareDatabase;
import edu.westga.cs3230.healthcare_dbms.io.database.QueryResult;
import edu.westga.cs3230.healthcare_dbms.model.AppointmentData;
import edu.westga.cs3230.healthcare_dbms.model.LabTest;
import edu.westga.cs3230.healthcare_dbms.model.LabTestOrder;
import edu.westga.cs3230.healthcare_dbms.model.LabTestResult;
import edu.westga.cs3230.healthcare_dbms.model.PatientData;
import edu.westga.cs3230.healthcare_dbms.sql.SqlGetter;
import edu.westga.cs3230.healthcare_dbms.sql.SqlSetter;
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
import javafx.scene.control.TextInputDialog;

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

	private final ObjectProperty<MultipleSelectionModel<TupleEmbed>> testListStatusSelectionProperty;
	private final ObjectProperty<MultipleSelectionModel<TupleEmbed>> testListOrderSelectionProperty;
	private final ObjectProperty<SingleSelectionModel<String>> testDropSelectionProperty;

	private HealthcareDatabase givenDB;
	
	private final ObjectProperty<PatientData> givenPatientProperty;
	private final ObjectProperty<AppointmentData> givenAppointmentProperty;

	/**
	 * Instantiates a new AppointmentViewModel.
	 */
	public FullPatientViewModelSubTest(ObjectProperty<PatientData> givenPatientProperty, ObjectProperty<AppointmentData> givenAppointmentProperty) {

		this.givenAppointmentProperty = givenAppointmentProperty;
		this.givenPatientProperty = givenPatientProperty;
		
		this.queueTestEventProperty = new SimpleBooleanProperty(false);
		this.orderTestsEventProperty = new SimpleBooleanProperty(false);
		this.removeTestEventProperty = new SimpleBooleanProperty(false);
		this.removeAllTestsEventProperty = new SimpleBooleanProperty(false);

		this.testListOrderSelectionProperty = new SimpleObjectProperty<MultipleSelectionModel<TupleEmbed>>();
		this.testListStatusSelectionProperty = new SimpleObjectProperty<MultipleSelectionModel<TupleEmbed>>();
		this.testDropSelectionProperty = new SimpleObjectProperty<SingleSelectionModel<String>>();

		this.testsList = FXCollections.observableArrayList();
		this.testsOrderList = FXCollections.observableArrayList();
		this.testStatusList = FXCollections.observableArrayList();

		this.testCostProperty = new SimpleStringProperty();
		this.testDescProperty = new SimpleStringProperty();
		this.testDateProperty = new SimpleObjectProperty<LocalDate>();
		
		this.availableTests = new ArrayList<LabTest>();
		
		/*
		//TESTS
		LabTest test = new LabTest(true, 666.0, "Test Lab Test");test.setLab_test_id(666);
		this.availableTests.add(test);
		this.testsList.add(test.getTest_description());
		test = new LabTest(true, 235.0, "Test sg Test");test.setLab_test_id(662356);
		this.availableTests.add(test);
		this.testsList.add(test.getTest_description());
		//**/
		
		this.addActionHandlers();

	}

	public ObjectProperty<MultipleSelectionModel<TupleEmbed>> getTestListStatusSelectionProperty() {
		return testListStatusSelectionProperty;
	}

	public void setDatabase(HealthcareDatabase givenDB) {
		this.givenDB = givenDB;
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
		
		int index = this.testDropSelectionProperty.getValue().getSelectedIndex();
		if(index < 0) {
			return null;
		}
		Integer testId = this.availableTests.get(index).getLab_test_id();
		if(testId == null) {
			return null;
		}
		
		if(this.givenAppointmentProperty.getValue() == null) {
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
		
		LabTestOrder order = new LabTestOrder(apptId, testId, dob);
		
		return order;
	}
	
	public void loadLabTests() {
		
		this.availableTests.clear();
		this.testsList.clear();
		
		QueryResult tests = this.givenDB.attemptGetLabTests();
		
		for(QueryResult result : tests) {
			if(result.getTuple() != null) {
				
				LabTest test = new LabTest(null, null, null, null);
				SqlSetter.fillWith(test, result.getTuple());
				
				this.availableTests.add(test);
				this.testsList.add(test.getTest_name());
			}
		}
	}
	
	public void loadLabTestOrders() {
		
		this.testStatusList.clear();
		//TODO
		QueryResult orders = this.givenDB.attemptGetTestOrdersOf(this.givenAppointmentProperty.getValue().getAppointment());
		
		if(orders == null) {
			return;
		}
		
		for(QueryResult result : orders) {
			if(result.getTuple() != null) {
				
				LabTestOrder test = new LabTestOrder(null, null, null);
				SqlSetter.fillWith(test, result.getTuple());
				
				TupleEmbed embed = new TupleEmbed(test,test,result.getTuple());
				embed.getPressedPropertyAction().addListener((evt)->{
					if(embed.getPressedPropertyAction().getValue() != null) {
						this.showResultUpdatePanel();
					}
				});
				
				this.testStatusList.add(embed);
			}
		}
	}
	
	private LabTestResult getLabTestResult(LabTestOrder order){
		QueryResult queryResult = this.givenDB.attemptGetTestResultOf(order);
		
		if(queryResult == null) {
			return null;
		}
		
		LabTestResult result = new LabTestResult(null, null);
		SqlSetter.fillWith(result, queryResult.getTuple());
		
		return result;
	}
	
	private void showResultUpdatePanel() {
		TupleEmbed emb = this.testListStatusSelectionProperty.getValue().getSelectedItem();
		if(emb != null) {
			
			LabTestResult result = this.getLabTestResult((LabTestOrder) emb.getOperatedObject());
			
			String prompt = "";
			if(result != null) {
				prompt = result.getTest_result();
			}
			
			TextInputDialog edit = new TextInputDialog(prompt);
			edit.setTitle("Lab Result");
			edit.setHeaderText("Enter Lab Result");
			edit.setContentText("Result: ");
			
			Optional<String> val = edit.showAndWait();
			if(!val.equals(Optional.empty())) {
				LabTestResult newResult = new LabTestResult(
						((LabTestOrder) emb.getOperatedObject()).getLab_test_order_id(), 
						val.get());
				if(result != null) {
					if(this.givenDB.attemptUpdateTuple(newResult, result) == null) {
						FXMLAlert.statusAlert("Failed to update lab test result");
					}else {
						FXMLAlert.statusAlert("Successfully updated lab test result");
					}
				} else {
					if(this.givenDB.attemptPostTuple(newResult) == null) {
						FXMLAlert.statusAlert("Failed to create lab test result");
					}else {
						FXMLAlert.statusAlert("Successfully create lab test result");
					}
				}
			}
		}
	}
	
	
	private void addActionHandlers() {
		
		this.queueTestEventProperty.addListener((evt) -> {
			if (this.queueTestEventProperty.getValue()) {
				this.queueTestOrder();
			}
		});
		
		this.orderTestsEventProperty.addListener((evt) -> {
			if (this.orderTestsEventProperty.getValue()) {
				//TODO DAL push multiple orders
				this.addAllTestOrders();
			}
		});
		
		this.removeTestEventProperty.addListener((evt) -> {
			if (this.removeTestEventProperty.getValue()) {
				this.removeSelectedTest();
			}
		});
		
		this.removeAllTestsEventProperty.addListener((evt) -> {
			if (this.removeAllTestsEventProperty.getValue()) {
				this.testsOrderList.clear();
			}
		});
		
		this.testDropSelectionProperty.addListener((evt)->{
			if(this.testDropSelectionProperty.getValue()!=null) {
				this.testDropSelectionProperty.getValue().selectedItemProperty().addListener((eevt)->{
					this.populateTestData();
				});
			}
		});
		
		/*
		this.testListStatusSelectionProperty.addListener((evt)->{
			if(this.testListStatusSelectionProperty.getValue()!=null) {
				this.testListStatusSelectionProperty.getValue().selectedItemProperty().addListener((eevt)->{
					this.showResultUpdatePanel();
				});
			}
		});
		*/
	}
	
	private void addAllTestOrders() {
		ArrayList<TupleEmbed> failedOrders = new ArrayList<TupleEmbed>();
		for(TupleEmbed emb : this.testsOrderList) {
			QueryResult result = this.givenDB.attemptAddTestOrder((LabTestOrder) emb.getOperatedObject());
			if(result == null) {
				failedOrders.add(emb);
				FXMLAlert.statusAlert("Test Order Failed", "The test order could not be added.", "Add Test Order failed", AlertType.ERROR);
			}
		}
		this.testsOrderList.clear();
		this.testsOrderList.addAll(failedOrders);
		this.loadLabTestOrders();
	}

	private void populateTestData() {
		SingleSelectionModel<String> sel = this.testDropSelectionProperty.getValue();
		Integer index = sel.getSelectedIndex();
		if(index >= 0) {
			this.testCostProperty.setValue(""+this.availableTests.get(index).getTest_cost());
			this.testDescProperty.setValue(""+this.availableTests.get(index).getTest_description());
		} else {
			this.testCostProperty.setValue("");
			this.testDescProperty.setValue("");
		}
	}
	
	private void queueTestOrder() {
		LabTestOrder order = this.getLabTestOrder();
		if(order == null) {
			return;
		}
		
		for(int i = 0; i < this.testsOrderList.size(); i++) {
			if(((LabTestOrder)this.testsOrderList.get(i).getOperatedObject()).getLab_test_id() == order.getLab_test_id()) {
				FXMLAlert.statusAlert("Cannot Order Duplicate Lab Test");
				return;
			}
		}
		
		for(int i = 0; i < this.testStatusList.size(); i++) {
			if(((LabTestOrder)this.testStatusList.get(i).getOperatedObject()).getLab_test_id() == order.getLab_test_id()) {
				FXMLAlert.statusAlert("The specified lab test has already been ordered, cannot order again.");
				return;
			}
		}
		
		TupleEmbed embed = new TupleEmbed(order, order, SqlGetter.getFrom(order));
		this.testsOrderList.add(embed);
	}
	
	private void removeSelectedTest() {
		
		int index = this.testListOrderSelectionProperty.getValue().getSelectedIndex();
		if(index < 0) {
			return;
		}
		this.testsOrderList.remove(index);
		
	}

	

}
