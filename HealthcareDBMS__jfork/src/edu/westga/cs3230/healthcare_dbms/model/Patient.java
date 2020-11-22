package edu.westga.cs3230.healthcare_dbms.model;

// TODO: Auto-generated Javadoc
/**
 * The Class Patient.
 */
public class Patient {
	
	/** The person id. */
	private Integer person_id;

	/**
	 * Instantiates a new patient.
	 *
	 * @param person_id the person id
	 */
	public Patient(Integer person_id) {
		this.setPerson_id(person_id);
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

}
