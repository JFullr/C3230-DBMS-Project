package edu.westga.cs3230.healthcare_dbms.model;

// TODO: Auto-generated Javadoc
/**
 * The Class LabTestResult.
 */
public class LabTestResult {

	/** The lab test order id. */
	private Integer lab_test_order_id;
	
	/** The test result. */
	private String test_result;

	/**
	 * Instantiates a new lab test result.
	 *
	 * @param lab_test_order_id the lab test order id
	 * @param test_result the test result
	 */
	public LabTestResult(Integer lab_test_order_id, String test_result) {
		this.lab_test_order_id = lab_test_order_id;
		this.test_result = test_result;
	}

	/**
	 * Gets the lab test order id.
	 *
	 * @return the lab test order id
	 */
	public Integer getLab_test_order_id() {
		return lab_test_order_id;
	}

	/**
	 * Sets the lab test order id.
	 *
	 * @param lab_test_order_id the new lab test order id
	 */
	public void setLab_test_order_id(Integer lab_test_order_id) {
		this.lab_test_order_id = lab_test_order_id;
	}

	/**
	 * Gets the test result.
	 *
	 * @return the test result
	 */
	public String getTest_result() {
		return test_result;
	}

	/**
	 * Sets the test result.
	 *
	 * @param test_result the new test result
	 */
	public void setTest_result(String test_result) {
		this.test_result = test_result;
	}

}
