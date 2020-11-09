package edu.westga.cs3230.healthcare_dbms.model;

public class LabTestOrderResult {

	private Integer lab_test_order_id;
	private String test_result;

	public LabTestOrderResult(Integer lab_test_order_id, String test_result) {
		this.lab_test_order_id = lab_test_order_id;
		this.test_result = test_result;
	}

	public Integer getLab_test_order_id() {
		return lab_test_order_id;
	}

	public void setLab_test_order_id(Integer lab_test_order_id) {
		this.lab_test_order_id = lab_test_order_id;
	}

	public String getTest_result() {
		return test_result;
	}

	public void setTest_result(String test_result) {
		this.test_result = test_result;
	}

}
