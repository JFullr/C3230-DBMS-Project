package edu.westga.cs3230.healthcare_dbms.model;

public class PatientData {
	
	private Person person;
	private Address first;
	private Address second;
	
	public PatientData(Person person, Address first) {
		this.assign(person, first, null);
	}
	
	public PatientData(Person person, Address first, Address second) {
		this.assign(person, first, second);
	}
	
	private void assign(Person person, Address first, Address second) {
		this.person = person;
		this.first = first;
		this.second = second;
	}

	public Person getPerson() {
		return person;
	}

	public Address getFirstAddress() {
		return first;
	}

	public Address getSecondAddress() {
		return second;
	}

}
