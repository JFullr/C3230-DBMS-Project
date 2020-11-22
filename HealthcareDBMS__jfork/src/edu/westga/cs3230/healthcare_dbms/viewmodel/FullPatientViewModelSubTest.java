package edu.westga.cs3230.healthcare_dbms.viewmodel;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import edu.westga.cs3230.healthcare_dbms.io.database.HealthcareDatabase;
import edu.westga.cs3230.healthcare_dbms.io.database.QueryResult;
import edu.westga.cs3230.healthcare_dbms.model.AppointmentData;
import edu.westga.cs3230.healthcare_dbms.model.LabTest;
import edu.westga.cs3230.healthcare_dbms.model.LabTestOrder;
import edu.westga.cs3230.healthcare_dbms.model.LabTestResult;
import edu.westga.cs3230.healthcare_dbms.sql.SqlGetter;
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
import javafx.scene.control.TextInputDialog;

/**
 * View model class for the lab test pane.
 *
 * @author Joseph Fuller and Andrew Steinborn
 */
public class FullPatientViewModelSubTest {

	/** The queue test event property. */
	private final BooleanProperty queueTestEventProperty;
	
	/** The order tests event property. */
	private final BooleanProperty orderTestsEventProperty;
	
	/** The remove test event property. */
	private final BooleanProperty removeTestEventProperty;
	
	/** The remove all tests event property. */
	private final BooleanProperty removeAllTestsEventProperty;

	/** The test cost property. */
	private final StringProperty testCostProperty;
	
	/** The test desc property. */
	private final StringProperty testDescProperty;
	
	/** The test date property. */
	private final ObjectProperty<LocalDate> testDateProperty;

	/** The available tests. */
	private ArrayList<LabTest> availableTests;
	
	/** The available tests lookup. */
	private HashMap<Integer,LabTest> availableTestsLookup;
	
	/** The available results lookup. */
	private HashMap<Integer,LabTestResult> availableResultsLookup;
	
	/** The tests list. */
	private ObservableList<String> testsList;
	
	/** The tests order list. */
	private ObservableList<TupleEmbed> testsOrderList;
	
	/** The test status list. */
	private ObservableList<TupleEmbed> testStatusList;

	/** The test list status selection property. */
	private final ObjectProperty<MultipleSelectionModel<TupleEmbed>> testListStatusSelectionProperty;
	
	/** The test list order selection property. */
	private final ObjectProperty<MultipleSelectionModel<TupleEmbed>> testListOrderSelectionProperty;
	
	/** The test drop selection property. */
	private final ObjectProperty<SingleSelectionModel<String>> testDropSelectionProperty;

	/** The given DB. */
	private HealthcareDatabase givenDB;
	
	/** The given appointment property. */
	private final ObjectProperty<AppointmentData> givenAppointmentProperty;

	/**
	 * Instantiates a new AppointmentViewModel.
	 *
	 * @param givenPatientProperty the given patient property
	 * @param givenAppointmentProperty the given appointment property
	 */
	public FullPatientViewModelSubTest(ObjectProperty<AppointmentData> givenAppointmentProperty) {

		this.givenAppointmentProperty = givenAppointmentProperty;
		
		this.availableTestsLookup = new HashMap<Integer,LabTest>();
		this.availableResultsLookup = new HashMap<Integer,LabTestResult>();
		
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
		
		this.addActionHandlers();

	}

	/**
	 * Gets the test list status selection property.
	 *
	 * @return the test list status selection property
	 */
	public ObjectProperty<MultipleSelectionModel<TupleEmbed>> getTestListStatusSelectionProperty() {
		return testListStatusSelectionProperty;
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
	 * Gets the tests list.
	 *
	 * @return the tests list
	 */
	public ObservableList<String> getTestsList() {
		return this.testsList;
	}

	/**
	 * Gets the test status list.
	 *
	 * @return the test status list
	 */
	public ObservableList<TupleEmbed> getTestStatusList() {
		return this.testStatusList;
	}
	
	/**
	 * Gets the tests order list.
	 *
	 * @return the tests order list
	 */
	public ObservableList<TupleEmbed> getTestsOrderList() {
		return this.testsOrderList;
	}

	/**
	 * Gets the test list order selection property.
	 *
	 * @return the test list order selection property
	 */
	public ObjectProperty<MultipleSelectionModel<TupleEmbed>> getTestListOrderSelectionProperty() {
		return this.testListOrderSelectionProperty;
	}

	/**
	 * Gets the test drop selection property.
	 *
	 * @return the test drop selection property
	 */
	public ObjectProperty<SingleSelectionModel<String>> getTestDropSelectionProperty() {
		return this.testDropSelectionProperty;
	}

	/**
	 * Gets the test cost property.
	 *
	 * @return the test cost property
	 */
	public StringProperty getTestCostProperty() {
		return this.testCostProperty;
	}

	/**
	 * Gets the test desc property.
	 *
	 * @return the test desc property
	 */
	public StringProperty getTestDescProperty() {
		return this.testDescProperty;
	}

	/**
	 * Gets the test date property.
	 *
	 * @return the test date property
	 */
	public ObjectProperty<LocalDate> getTestDateProperty() {
		return this.testDateProperty;
	}

	/**
	 * Gets the removes the all tests event property.
	 *
	 * @return the removes the all tests event property
	 */
	public BooleanProperty getRemoveAllTestsEventProperty() {
		return this.removeAllTestsEventProperty;
	}

	/**
	 * Gets the removes the test event property.
	 *
	 * @return the removes the test event property
	 */
	public BooleanProperty getRemoveTestEventProperty() {
		return this.removeTestEventProperty;
	}

	/**
	 * Gets the order tests event property.
	 *
	 * @return the order tests event property
	 */
	public BooleanProperty getOrderTestsEventProperty() {
		return this.orderTestsEventProperty;
	}

	/**
	 * Gets the queue test event property.
	 *
	 * @return the queue test event property
	 */
	public BooleanProperty getQueueTestEventProperty() {
		return this.queueTestEventProperty;
	}
	
	/**
	 * Gets the lab test order.
	 *
	 * @return the lab test order
	 */
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
	
	/**
	 * Load lab tests.
	 */
	public void loadLabTests() {
		
		this.availableTests.clear();
		this.testsList.clear();
		
		QueryResult tests = this.givenDB.attemptGetLabTests();
		
		for(QueryResult result : tests) {
			if(result.getTuple() != null) {
				
				LabTest test = new LabTest(null, null, null, null);
				SqlSetter.fillWith(test, result.getTuple());
				
				this.availableTestsLookup.put(test.getLab_test_id(), test);
				this.availableTests.add(test);
				this.testsList.add(test.getTest_name());
			}
		}
	}
	
	/**
	 * Load lab test orders.
	 */
	public void loadLabTestOrders() {
		
		this.testStatusList.clear();

		QueryResult orders = this.givenDB.attemptGetTestOrdersOf(this.givenAppointmentProperty.getValue().getAppointment());
		
		if(orders == null) {
			return;
		}
		
		this.getAllLabTestResults();
		
		for(QueryResult result : orders) {
			if(result.getTuple() != null) {
				
				LabTestOrder test = new LabTestOrder(null, null, null);
				SqlSetter.fillWith(test, result.getTuple());
				
				LabTest found = this.availableTestsLookup.get(test.getLab_test_id());
				SqlTuple display = new SqlTuple();
				display.add("TestName", found.getTest_name());
				display.add("DateToPerform", test.getDate_to_perform());
				
				LabTestResult labResult = this.availableResultsLookup.get(test.getLab_test_order_id());
				
				if(labResult != null) {
					display.add("Result", labResult.getTest_result());
				}else {
					display.add("Result", "");
				}
				
				TupleEmbed embed = new TupleEmbed(test, test, display);
				embed.getPressedPropertyAction().addListener((evt)->{
					if(embed.getPressedPropertyAction().getValue() != null) {
						this.showResultUpdatePanel();
					}
				});
				
				this.testStatusList.add(embed);
			}
		}
	}
	
	/**
	 * Clear order queue.
	 */
	public void clearOrderQueue() {
		this.testsOrderList.clear();
		this.testDateProperty.setValue(null);
	}
	
	/**
	 * Gets the lab test result.
	 *
	 * @param order the order
	 * @return the lab test result
	 */
	private LabTestResult getLabTestResult(LabTestOrder order){
		QueryResult queryResult = this.givenDB.attemptGetTestResultOf(order);
		
		if(queryResult == null || queryResult.getTuple() == null) {
			return null;
		}
		
		LabTestResult result = new LabTestResult(null, null);
		SqlSetter.fillWith(result, queryResult.getTuple());
		
		return result;
	}
	
	/**
	 * Gets the all lab test results.
	 *
	 * @return the all lab test results
	 */
	private void getAllLabTestResults(){
		QueryResult queryResult = this.givenDB.attemptGetTestResultsOf(this.givenAppointmentProperty.get().getAppointment());
		
		if(queryResult == null) {
			return;
		}
		
		this.availableResultsLookup.clear();
		for(QueryResult qResult: queryResult) {
			LabTestResult result = new LabTestResult(null, null);
			SqlSetter.fillWith(result, qResult.getTuple());
			
			this.availableResultsLookup.put(result.getLab_test_order_id(), result);
		}
		
	}
	
	/**
	 * Show result update panel.
	 */
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
						emb.updateAttribute("Result", val.get());
					}
				} else {
					if(this.givenDB.attemptPostTuple(newResult) == null) {
						FXMLAlert.statusAlert("Failed to create lab test result");
					}else {
						FXMLAlert.statusAlert("Successfully create lab test result");
						emb.updateAttribute("Result", val.get());
					}
				}
			}
		}
	}
	
	
	/**
	 * Adds the action handlers.
	 */
	private void addActionHandlers() {
		
		this.queueTestEventProperty.addListener((evt) -> {
			if (this.queueTestEventProperty.getValue()) {
				this.queueTestOrder();
			}
		});
		
		this.orderTestsEventProperty.addListener((evt) -> {
			if (this.orderTestsEventProperty.getValue()) {
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
		
	}
	
	/**
	 * Adds the all test orders.
	 */
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

	/**
	 * Populate test data.
	 */
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
	
	/**
	 * Queue test order.
	 */
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
		
		TupleEmbed embed = null;
		LabTest test = this.availableTestsLookup.get(order.getLab_test_id());
		if(test == null) {
			embed = new TupleEmbed(order, order, SqlGetter.getFrom(order).hideBasedOn(order));
		}else {
			SqlTuple view = new SqlTuple();
			view.add("Name", test.getTest_name());
			view.add("Description", test.getTest_description());
			view.add("Cost", test.getTest_cost());
			view.add("Date To Perform", order.getDate_to_perform());
			embed = new TupleEmbed(order,order,view);
		}
		
		this.testsOrderList.add(embed);
	}
	
	/**
	 * Removes the selected test.
	 */
	private void removeSelectedTest() {
		
		int index = this.testListOrderSelectionProperty.getValue().getSelectedIndex();
		if(index < 0) {
			return;
		}
		this.testsOrderList.remove(index);
		
	}

}
