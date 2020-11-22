package edu.westga.cs3230.healthcare_dbms.model;

// TODO: Auto-generated Javadoc
/**
 * The Class DoctorData.
 */
public class DoctorData {
	
	/** The person. */
	private Person person;
	
	/** The doctor. */
	private Doctor doctor;
	
	/**
	 * Instantiates a new doctor data.
	 *
	 * @param person the person
	 * @param first the first
	 */
	public DoctorData(Person person, Doctor first) {
		this.assign(person, first);
	}
	
	/**
	 * Assign.
	 *
	 * @param person the person
	 * @param doctor the doctor
	 */
	private void assign(Person person, Doctor doctor) {
		this.person = person;
		this.doctor = doctor;
	}

	/**
	 * Gets the person.
	 *
	 * @return the person
	 */
	public Person getPerson() {
		return person;
	}

	/**
	 * Gets the doctor.
	 *
	 * @return the doctor
	 */
	public Doctor getDoctor() {
		return doctor;
	}
}
