package edu.westga.cs3230.healthcare_dbms.model;

/**
 * Represents a doctor. A patient is merely a specialization of
 * {@link Person}.
 *
 * @author Joseph Fuller and Andrew Steinborn
 */
public class Nurse {
	
	/** The person id. */
	private Integer person_id;

	/**
	 * Instantiates a new nurse.
	 *
	 * @param person_id the person id
	 */
	public Nurse(Integer person_id) {
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
