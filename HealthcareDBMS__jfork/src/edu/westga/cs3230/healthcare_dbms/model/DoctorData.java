package edu.westga.cs3230.healthcare_dbms.model;

public class DoctorData {
	
	private Person person;
	private Doctor doctor;
	
	public DoctorData(Person person, Doctor first) {
		this.assign(person, first);
	}
	
	private void assign(Person person, Doctor doctor) {
		this.person = person;
		this.doctor = doctor;
	}

	public Person getPerson() {
		return person;
	}

	public Doctor getDocor() {
		return doctor;
	}
}
