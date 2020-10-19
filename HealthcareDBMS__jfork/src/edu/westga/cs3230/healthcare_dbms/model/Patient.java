package edu.westga.cs3230.healthcare_dbms.model;

public class Patient {
	
	private Integer person_id;

	public Patient(Integer person_id) {
		this.setPerson_id(person_id);
	}

	public Integer getPerson_id() {
		return person_id;
	}

	public void setPerson_id(Integer person_id) {
		this.person_id = person_id;
	}

}
