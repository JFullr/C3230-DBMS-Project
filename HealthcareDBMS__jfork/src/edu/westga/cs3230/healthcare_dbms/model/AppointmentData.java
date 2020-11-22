package edu.westga.cs3230.healthcare_dbms.model;

// TODO: Auto-generated Javadoc
/**
 * The Class AppointmentData.
 */
public class AppointmentData {
	
	/** The appointment. */
	private Appointment appointment;
	
	/** The patient. */
	private PatientData patient;
	
	/**
	 * Instantiates a new appointment data.
	 */
	public AppointmentData() {
		this.setAppointment(null);
		this.setPatient(null);
	}
	
	/**
	 * Instantiates a new appointment data.
	 *
	 * @param appt the appt
	 */
	public AppointmentData(Appointment appt) {
		this.setAppointment(appt);
		this.setPatient(null);
	}
	
	/**
	 * Instantiates a new appointment data.
	 *
	 * @param appointment the appointment
	 * @param patient the patient
	 */
	public AppointmentData(Appointment appointment, PatientData patient) {
		this.setAppointment(appointment);
		this.setPatient(patient);
	}

	/**
	 * Gets the patient.
	 *
	 * @return the patient
	 */
	public PatientData getPatient() {
		return patient;
	}

	/**
	 * Sets the patient.
	 *
	 * @param patient the new patient
	 */
	public void setPatient(PatientData patient) {
		this.patient = patient;
	}

	/**
	 * Gets the appointment.
	 *
	 * @return the appointment
	 */
	public Appointment getAppointment() {
		return appointment;
	}

	/**
	 * Sets the appointment.
	 *
	 * @param appointment the new appointment
	 */
	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}

}
