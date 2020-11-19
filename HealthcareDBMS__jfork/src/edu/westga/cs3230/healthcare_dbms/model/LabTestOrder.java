package edu.westga.cs3230.healthcare_dbms.model;

import java.sql.Date;

import edu.westga.cs3230.healthcare_dbms.sql.SqlGenerated;
import edu.westga.cs3230.healthcare_dbms.sql.UiHide;

public class LabTestOrder {

	@SqlGenerated
	@UiHide
	private Integer lab_test_order_id;

	@UiHide
	private Integer appointment_id;

	@UiHide
	private Integer lab_test_id;

	@UiHide
	private Date date_to_perform;
	
	public LabTestOrder(Integer appointment_id, Integer lab_test_id, Date date_to_perform) {
		this.setLab_test_order_id(null);
		this.appointment_id = appointment_id;
		this.lab_test_id = lab_test_id;
		this.date_to_perform = date_to_perform;
	}

	public Date getDate_to_perform() {
		return date_to_perform;
	}

	public void setDate_to_perform(Date date_to_perform) {
		this.date_to_perform = date_to_perform;
	}

	public Integer getLab_test_id() {
		return lab_test_id;
	}

	public void setLab_test_id(Integer lab_test_id) {
		this.lab_test_id = lab_test_id;
	}

	public Integer getAppointment_id() {
		return appointment_id;
	}

	public void setAppointment_id(Integer appointment_id) {
		this.appointment_id = appointment_id;
	}

	public Integer getLab_test_order_id() {
		return lab_test_order_id;
	}

	public void setLab_test_order_id(Integer lab_test_order_id) {
		this.lab_test_order_id = lab_test_order_id;
	}

}
