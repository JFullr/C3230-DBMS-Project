package edu.westga.cs3230.healthcare_dbms.model;

// TODO: Auto-generated Javadoc
/**
 * The Class FinalDiagnosis.
 */
public class FinalDiagnosis {

	/** The appointment id. */
	private Integer appointment_id;
	
	/** The diagnosis result. */
	private String diagnosis_result;
	
	/**
	 * Instantiates a new final diagnosis.
	 */
	public FinalDiagnosis() {
		this.appointment_id = null;
		this.diagnosis_result = null;
	}
	
	/**
	 * Instantiates a new final diagnosis.
	 *
	 * @param appointment_id the appointment id
	 * @param result the result
	 */
	public FinalDiagnosis(Integer appointment_id, String result) {
		this.appointment_id = appointment_id;
		this.diagnosis_result = result;
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
	 * Gets the diagnosis result.
	 *
	 * @return the diagnosis result
	 */
	public String getDiagnosis_result() {
		return diagnosis_result;
	}

	/**
	 * Sets the diagnosis result.
	 *
	 * @param diagnosis_result the new diagnosis result
	 */
	public void setDiagnosis_result(String diagnosis_result) {
		this.diagnosis_result = diagnosis_result;
	}

}
