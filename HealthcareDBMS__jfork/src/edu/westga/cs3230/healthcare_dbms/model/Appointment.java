package edu.westga.cs3230.healthcare_dbms.model;

import java.sql.Timestamp;

import edu.westga.cs3230.healthcare_dbms.sql.SqlGenerated;
import edu.westga.cs3230.healthcare_dbms.sql.UiHide;

// TODO: Auto-generated Javadoc
/**
 * The Class Appointment.
 */
public class Appointment {
	
	/** The appointment id. */
	@SqlGenerated
	@UiHide
	private Integer appointment_id;
	
	/** The person id. */
	@UiHide
	private Integer person_id;
	
	/** The date time. */
	private Timestamp date_time;

	/** The doctor id. */
	@UiHide
	private Integer doctor_id;
	
	/** The appointment reason. */
	private String appointment_reason;
	
	/**
	 * Instantiates a new appointment.
	 *
	 * @param person_id the person id
	 * @param date_time the date time
	 * @param doctor_id the doctor id
	 * @param appointment_reason the appointment reason
	 */
	public Appointment(Integer person_id, Timestamp date_time, Integer doctor_id, String appointment_reason) {
		this.setPerson_id(person_id);
		this.setDate_time(date_time);
		this.setDoctor_id(doctor_id);
		this.setAppointment_reason(appointment_reason);
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
	 * Gets the person id.
	 *
	 * @return the person id
	 */
	public Integer getPerson_id() {
		return person_id;
	}

	/**
	 * Sets the person id.
	 *
	 * @param person_id the new person id
	 */
	public void setPerson_id(Integer person_id) {
		this.person_id = person_id;
	}

	/**
	 * Gets the date time.
	 *
	 * @return the date time
	 */
	public Timestamp getDate_time() {
		return date_time;
	}

	/**
	 * Sets the date time.
	 *
	 * @param datetime the new date time
	 */
	public void setDate_time(Timestamp datetime) {
		this.date_time = datetime;
	}

	/**
	 * Gets the doctor id.
	 *
	 * @return the doctor id
	 */
	public Integer getDoctor_id() {
		return doctor_id;
	}

	/**
	 * Sets the doctor id.
	 *
	 * @param doctor_id the new doctor id
	 */
	public void setDoctor_id(Integer doctor_id) {
		this.doctor_id = doctor_id;
	}

	/**
	 * Gets the appointment reason.
	 *
	 * @return the appointment reason
	 */
	public String getAppointment_reason() {
		return appointment_reason;
	}

	/**
	 * Sets the appointment reason.
	 *
	 * @param appointment_reason the new appointment reason
	 */
	public void setAppointment_reason(String appointment_reason) {
		this.appointment_reason = appointment_reason;
	}

}
