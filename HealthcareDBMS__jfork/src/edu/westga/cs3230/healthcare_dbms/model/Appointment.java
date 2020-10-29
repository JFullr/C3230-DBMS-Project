package edu.westga.cs3230.healthcare_dbms.model;

import java.sql.Timestamp;

import edu.westga.cs3230.healthcare_dbms.sql.SqlGenerated;

public class Appointment {
	
	@SqlGenerated
	private Integer appointment_id;
	private Integer person_id;
	private Timestamp date_time;
	
	public Appointment(Integer person_id, Timestamp date_time) {
		this.setPerson_id(person_id);
		this.setDate_time(date_time);
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

}
