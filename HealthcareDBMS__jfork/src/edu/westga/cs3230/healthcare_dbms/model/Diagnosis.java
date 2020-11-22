package edu.westga.cs3230.healthcare_dbms.model;

// TODO: Auto-generated Javadoc
/**
 * The Class Diagnosis.
 */
public class Diagnosis {
	
	/** The appointment id. */
	private Integer appointment_id;
	
	/** The diagnosis description. */
	private String diagnosis_description;
	
	/**
	 * Instantiates a new diagnosis.
	 *
	 * @param appointment_id the appointment id
	 * @param diagnosis_description the diagnosis description
	 */
	public Diagnosis(Integer appointment_id, String diagnosis_description) {
		
		this.appointment_id = appointment_id;
		this.diagnosis_description = diagnosis_description;
		
	}

	/**
	 * Gets the appointment id.
	 *
	 * @return the appointment id
	 */
	public Integer getAppointment_id() {
		return appointment_id;
	}

	/**
	 * Sets the appointment id.
	 *
	 * @param appointment_id the new appointment id
	 */
	public void setAppointment_id(Integer appointment_id) {
		this.appointment_id = appointment_id;
	}

	/**
	 * Gets the diagnosis description.
	 *
	 * @return the diagnosis description
	 */
	public String getDiagnosis_description() {
		return diagnosis_description;
	}

	/**
	 * Sets the diagnosis description.
	 *
	 * @param diagnosis_description the new diagnosis description
	 */
	public void setDiagnosis_description(String diagnosis_description) {
		this.diagnosis_description = diagnosis_description;
	}

}
