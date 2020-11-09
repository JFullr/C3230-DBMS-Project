package edu.westga.cs3230.healthcare_dbms.model;

import edu.westga.cs3230.healthcare_dbms.sql.SqlGenerated;

public class LabTest {

	@SqlGenerated
	private Integer lab_test_id;
	private Boolean is_available;
	private Double test_cost;
	private String test_description;
	
	public LabTest(Boolean is_available, Double test_cost, String test_description) {
		this.lab_test_id = null;
		this.is_available = is_available;
		this.test_cost = test_cost;
		this.test_description = test_description;
	}

	public String getTest_description() {
		return test_description;
	}

	public void setTest_description(String test_description) {
		this.test_description = test_description;
	}

	public Double getTest_cost() {
		return test_cost;
	}

	public void setTest_cost(Double test_cost) {
		this.test_cost = test_cost;
	}

	public Boolean getIs_available() {
		return is_available;
	}

	public void setIs_available(Boolean is_available) {
		this.is_available = is_available;
	}

}
