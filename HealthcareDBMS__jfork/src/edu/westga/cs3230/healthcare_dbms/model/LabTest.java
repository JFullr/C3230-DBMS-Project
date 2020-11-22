package edu.westga.cs3230.healthcare_dbms.model;

import edu.westga.cs3230.healthcare_dbms.sql.SqlGenerated;
import edu.westga.cs3230.healthcare_dbms.sql.UiHide;

/**
 * Represents a lab test.
 *
 * @author Joseph Fuller and Andrew Steinborn
 */
public class LabTest {

	/** The lab test id. */
	@SqlGenerated
	@UiHide
	private Integer lab_test_id;
	
	/** The is available. */
	private Boolean is_available;
	
	/** The test cost. */
	private Double test_cost;
	
	/** The test description. */
	private String test_description;
	
	/** The test name. */
	private String test_name;
	
	/**
	 * Instantiates a new lab test.
	 *
	 * @param is_available the is available
	 * @param test_cost the test cost
	 * @param test_name the test name
	 * @param test_description the test description
	 */
	public LabTest(Boolean is_available, Double test_cost, String test_name, String test_description) {
		this.lab_test_id = null;
		this.is_available = is_available;
		this.test_cost = test_cost;
		this.test_name = test_name;
		this.test_description = test_description;
	}
	
	/**
	 * Gets the lab test id.
	 *
	 * @return the lab test id
	 */
	public Integer getLab_test_id() {
		return lab_test_id;
	}

	/**
	 * Sets the lab test id.
	 *
	 * @param lab_test_id the new lab test id
	 */
	public void setLab_test_id(Integer lab_test_id) {
		this.lab_test_id = lab_test_id;
	}

	/**
	 * Gets the test description.
	 *
	 * @return the test description
	 */
	public String getTest_description() {
		return test_description;
	}

	/**
	 * Sets the test description.
	 *
	 * @param test_description the new test description
	 */
	public void setTest_description(String test_description) {
		this.test_description = test_description;
	}

	/**
	 * Gets the test cost.
	 *
	 * @return the test cost
	 */
	public Double getTest_cost() {
		return test_cost;
	}

	/**
	 * Sets the test cost.
	 *
	 * @param test_cost the new test cost
	 */
	public void setTest_cost(Double test_cost) {
		this.test_cost = test_cost;
	}

	/**
	 * Gets the checks if is available.
	 *
	 * @return the checks if is available
	 */
	public Boolean getIs_available() {
		return is_available;
	}

	/**
	 * Sets the checks if is available.
	 *
	 * @param is_available the new checks if is available
	 */
	public void setIs_available(Boolean is_available) {
		this.is_available = is_available;
	}

	/**
	 * Gets the test name.
	 *
	 * @return the test name
	 */
	public String getTest_name() {
		return test_name;
	}

	/**
	 * Sets the test name.
	 *
	 * @param test_name the new test name
	 */
	public void setTest_name(String test_name) {
		this.test_name = test_name;
	}

}
