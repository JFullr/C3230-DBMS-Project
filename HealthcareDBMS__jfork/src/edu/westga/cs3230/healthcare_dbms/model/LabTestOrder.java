package edu.westga.cs3230.healthcare_dbms.model;

import java.sql.Date;

import edu.westga.cs3230.healthcare_dbms.sql.SqlGenerated;
import edu.westga.cs3230.healthcare_dbms.sql.UiHide;

// TODO: Auto-generated Javadoc
/**
 * The Class LabTestOrder.
 */
public class LabTestOrder {

	/** The lab test order id. */
	@SqlGenerated
	@UiHide
	private Integer lab_test_order_id;

	/** The appointment id. */
	@UiHide
	private Integer appointment_id;

	/** The lab test id. */
	@UiHide
	private Integer lab_test_id;

	/** The date to perform. */
	@UiHide
	private Date date_to_perform;
	
	/**
	 * Instantiates a new lab test order.
	 *
	 * @param appointment_id the appointment id
	 * @param lab_test_id the lab test id
	 * @param date_to_perform the date to perform
	 */
	public LabTestOrder(Integer appointment_id, Integer lab_test_id, Date date_to_perform) {
		this.setLab_test_order_id(null);
		this.appointment_id = appointment_id;
		this.lab_test_id = lab_test_id;
		this.date_to_perform = date_to_perform;
	}

	/**
	 * Gets the date to perform.
	 *
	 * @return the date to perform
	 */
	public Date getDate_to_perform() {
		return date_to_perform;
	}

	/**
	 * Sets the date to perform.
	 *
	 * @param date_to_perform the new date to perform
	 */
	public void setDate_to_perform(Date date_to_perform) {
		this.date_to_perform = date_to_perform;
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
	 * Gets the appointment id.
	 *
	 * @return the appointment id
	 */
	public Integer getAppointment_id() {
		return appointment_id;
	}

	/**
	 * Sets the appointment id.
	 *
	 * @param appointment_id the new appointment id
	 */
	public void setAppointment_id(Integer appointment_id) {
		this.appointment_id = appointment_id;
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

}
