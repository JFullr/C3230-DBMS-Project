package edu.westga.cs3230.healthcare_dbms.model;

/**
 * Represents a checkup for a patient done when they have an appointment.
 *
 * @author Joseph Fuller and Andrew Steinborn
 */
public class AppointmentCheckup {
	
	/** The appointment id. */
	private Integer appointment_id;
	
	/** The patient's systolic pressure. */
	private Integer systolic_pressure;
	
	/** The patient's diastolic pressure. */
	private Integer diastolic_pressure;
	
	/** The patient's pulse. */
	private Integer pulse;
	
	/** The patient's weight. */
	private Double weight;
	
	/** The patient's temperature. */
	private Double temperature;
	
	/** The nurse id. */
	private Integer nurse_id;

	/**
	 * Instantiates a new appointment checkup.
	 */
	public AppointmentCheckup() {
		this.appointment_id = null;
		this.systolic_pressure = null;
		this.diastolic_pressure = null;
		this.pulse = null;
		this.weight = null;
		this.setTemperature(null);
	}

	/**
	 * Instantiates a new appointment checkup.
	 *
	 * @param appointment_id the appointment id
	 * @param systolic_pressure the patient's systolic pressure
	 * @param diastolic_pressure the patient's diastolic pressure
	 * @param pulse the patient's pulse
	 * @param weight the patient's weight
	 * @param temperature the patient's temperature
	 * @param nurse_id the nurse id
	 */
	public AppointmentCheckup(Integer appointment_id, Integer systolic_pressure, Integer diastolic_pressure, Integer pulse,
			Double weight, Double temperature, Integer nurse_id) {
		this.appointment_id = appointment_id;
		this.systolic_pressure = systolic_pressure;
		this.diastolic_pressure = diastolic_pressure;
		this.pulse = pulse;
		this.weight = weight;
		this.nurse_id = nurse_id;
		this.setTemperature(temperature);
	}
	
	/**
	 * Instantiates a new appointment checkup.
	 *
	 * @param appointment_id the appointment id
	 * @param systolic_pressure the patient's systolic pressure
	 * @param diastolic_pressure the patient's diastolic pressure
	 * @param pulse the patient's pulse
	 * @param weight the patient's weight
	 * @param temperature the patient's temperature
	 */
	public AppointmentCheckup(String appointment_id, String systolic_pressure, String diastolic_pressure, String pulse,
			String weight, String temperature) {
		this.appointment_id = Integer.parseInt(appointment_id);
		this.systolic_pressure = Integer.parseInt(systolic_pressure);
		this.diastolic_pressure = Integer.parseInt(diastolic_pressure);
		this.pulse = Integer.parseInt(pulse);
		this.weight = Double.parseDouble(weight);
		this.setTemperature(Double.parseDouble(temperature));
	}

	/**
	 * Gets the appointment id.
	 *
	 * @return the appointment_id
	 */
	public Integer getAppointment_id() {
		return appointment_id;
	}

	/**
	 * Sets the appointment id.
	 *
	 * @param appointment_id the appointment_id to set
	 */
	public void setAppointment_id(int appointment_id) {
		this.appointment_id = appointment_id;
	}

	/**
	 * Gets the patient's systolic pressure.
	 *
	 * @return the patient's systolic pressure
	 */
	public int getSystolic_pressure() {
		return systolic_pressure;
	}

	/**
	 * Sets the patient's systolic pressure.
	 *
	 * @param systolic_pressure the patient's systolic pressure
	 */
	public void setSystolic_pressure(int systolic_pressure) {
		this.systolic_pressure = systolic_pressure;
	}

	/**
	 * Gets the patient's diastolic pressure.
	 *
	 * @return the patient's diastolic pressure
	 */
	public Integer getDiastolic_pressure() {
		return diastolic_pressure;
	}

	/**
	 * Sets the patient's diastolic pressure.
	 *
	 * @param diastolic_pressure the diastolic pressure to set
	 */
	public void setDiastolic_pressure(int diastolic_pressure) {
		this.diastolic_pressure = diastolic_pressure;
	}

	/**
	 * Gets the patient's pulse.
	 *
	 * @return the patient's pulse
	 */
	public Integer getPulse() {
		return pulse;
	}

	/**
	 * Sets the patient's pulse.
	 *
	 * @param pulse the pulse to set
	 */
	public void setPulse(int pulse) {
		this.pulse = pulse;
	}

	/**
	 * Gets the patient's weight.
	 *
	 * @return the patient's weight
	 */
	public Double getWeight() {
		return weight;
	}

	/**
	 * Sets the patient's weight.
	 *
	 * @param weight the weight to set
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}

	/**
	 * Gets the patient's temperature.
	 *
	 * @return the temperature
	 */
	public Double getTemperature() {
		return temperature;
	}

	/**
	 * Sets the patient's temperature.
	 *
	 * @param temperature the new temperature
	 */
	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	/**
	 * Gets the nurse id.
	 *
	 * @return the nurse id
	 */
	public Integer getNurse_id() {
		return nurse_id;
	}

	/**
	 * Sets the nurse id.
	 *
	 * @param nurse_id the new nurse id
	 */
	public void setNurse_id(Integer nurse_id) {
		this.nurse_id = nurse_id;
	}
}
