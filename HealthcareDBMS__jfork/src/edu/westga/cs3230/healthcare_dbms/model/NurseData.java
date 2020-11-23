package edu.westga.cs3230.healthcare_dbms.model;

/**
 * Couples a {@link Doctor} with {@link Person}. A doctor is just an alias for
 * a specific person.
 *
 * @author Joseph Fuller and Andrew Steinborn
 */
public class NurseData {
	
	/** The person. */
	private Person person;
	
	/** The nurse. */
	private Nurse nurse;
	
	/**
	 * Instantiates a new nurse data.
	 *
	 * @param person the person
	 * @param first the first
	 */
	public NurseData(Person person, Nurse first) {
		this.assign(person, first);
	}
	
	/**
	 * Assigns the given person and nurse.
	 *
	 * @param person the person
	 * @param nurse the nurse
	 */
	private void assign(Person person, Nurse nurse) {
		this.person = person;
		this.nurse = nurse;
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
	 * Gets the nurse.
	 *
	 * @return the nurse
	 */
	public Nurse getNurse() {
		return this.nurse;
	}
}
