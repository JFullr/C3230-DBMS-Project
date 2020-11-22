package edu.westga.cs3230.healthcare_dbms.model;

/**
 * Couples a {@link Doctor} with {@link Person}. A doctor is just an alias for
 * a specific person.
 *
 * @author Joseph Fuller and Andrew Steinborn
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
	 * Assigns the given person and doctor.
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
