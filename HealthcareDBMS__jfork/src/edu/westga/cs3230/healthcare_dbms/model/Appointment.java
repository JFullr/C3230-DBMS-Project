package edu.westga.cs3230.healthcare_dbms.model;

import java.sql.Timestamp;

import edu.westga.cs3230.healthcare_dbms.sql.SqlGenerated;

public class Appointment {
	
	@SqlGenerated
	private Integer appointment_id;
	private Integer person_id;
	private Timestamp date_time;
	
	private Integer doctor_id;
	private String appointment_reason;
	
	public Appointment(Integer person_id, Timestamp date_time, Integer doctor_id, String appointment_reason) {
		this.setPerson_id(person_id);
		this.setDate_time(date_time);
		this.setDoctor_id(doctor_id);
		this.setAppointment_reason(appointment_reason);
	}

	public Integer getAppointment_id() {
		return appointment_id;
	}

	public void setAppointment_id(Integer appointment_id) {
		this.appointment_id = appointment_id;
	}

	public Integer getPerson_id() {
		return person_id;
	}

	public void setPerson_id(Integer person_id) {
		this.person_id = person_id;
	}

	public Timestamp getDate_time() {
		return date_time;
	}

	public void setDate_time(Timestamp datetime) {
		this.date_time = datetime;
	}

	public Integer getDoctor_id() {
		return doctor_id;
	}

	public void setDoctor_id(Integer doctor_id) {
		this.doctor_id = doctor_id;
	}

	public String getAppointment_reason() {
		return appointment_reason;
	}

	public void setAppointment_reason(String appointment_reason) {
		this.appointment_reason = appointment_reason;
	}

}
