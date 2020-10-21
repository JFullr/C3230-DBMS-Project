package edu.westga.cs3230.healthcare_dbms.model;

public class PatientData {
	
	private Person person;
	private Address address;
	
	public PatientData(Person person, Address first) {
		this.assign(person, first);
	}
	
	private void assign(Person person, Address address) {
		this.person = person;
		this.address = address;
	}

	public Person getPerson() {
		return person;
	}

	public Address getAddress() {
		return address;
	}
}
