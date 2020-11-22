package edu.westga.cs3230.healthcare_dbms.model;

// TODO: Auto-generated Javadoc
/**
 * The Class AppointmentCheckup.
 */
public class AppointmentCheckup {
	
	/** The appointment id. */
	private Integer appointment_id;
	
	/** The systolic pressure. */
	private Integer systolic_pressure;
	
	/** The diastolic pressure. */
	private Integer diastolic_pressure;
	
	/** The pulse. */
	private Integer pulse;
	
	/** The weight. */
	private Double weight;
	
	/** The temperature. */
	private Double temperature;

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
	 * @param systolic_pressure the systolic pressure
	 * @param diastolic_pressure the diastolic pressure
	 * @param pulse the pulse
	 * @param weight the weight
	 * @param temperature the temperature
	 */
	public AppointmentCheckup(Integer appointment_id, Integer systolic_pressure, Integer diastolic_pressure, Integer pulse,
			Double weight, Double temperature) {
		this.appointment_id = appointment_id;
		this.systolic_pressure = systolic_pressure;
		this.diastolic_pressure = diastolic_pressure;
		this.pulse = pulse;
		this.weight = weight;
		this.setTemperature(temperature);
	}
	
	/**
	 * Instantiates a new appointment checkup.
	 *
	 * @param appointment_id the appointment id
	 * @param systolic_pressure the systolic pressure
	 * @param diastolic_pressure the diastolic pressure
	 * @param pulse the pulse
	 * @param weight the weight
	 * @param temperature the temperature
	 * @throws Exception the exception
	 */
	public AppointmentCheckup(String appointment_id, String systolic_pressure, String diastolic_pressure, String pulse,
			String weight, String temperature) throws Exception {
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
	 * Gets the systolic pressure.
	 *
	 * @return the systolic_pressure
	 */
	public int getSystolic_pressure() {
		return systolic_pressure;
	}

	/**
	 * Sets the systolic pressure.
	 *
	 * @param systolic_pressure the systolic_pressure to set
	 */
	public void setSystolic_pressure(int systolic_pressure) {
		this.systolic_pressure = systolic_pressure;
	}

	/**
	 * Gets the diastolic pressure.
	 *
	 * @return the diastolic_pressure
	 */
	public Integer getDiastolic_pressure() {
		return diastolic_pressure;
	}

	/**
	 * Sets the diastolic pressure.
	 *
	 * @param diastolic_pressure the diastolic_pressure to set
	 */
	public void setDiastolic_pressure(int diastolic_pressure) {
		this.diastolic_pressure = diastolic_pressure;
	}

	/**
	 * Gets the pulse.
	 *
	 * @return the pulse
	 */
	public Integer getPulse() {
		return pulse;
	}

	/**
	 * Sets the pulse.
	 *
	 * @param pulse the pulse to set
	 */
	public void setPulse(int pulse) {
		this.pulse = pulse;
	}

	/**
	 * Gets the weight.
	 *
	 * @return the weight
	 */
	public Double getWeight() {
		return weight;
	}

	/**
	 * Sets the weight.
	 *
	 * @param weight the weight to set
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}

	/**
	 * Gets the temperature.
	 *
	 * @return the temperature
	 */
	public Double getTemperature() {
		return temperature;
	}

	/**
	 * Sets the temperature.
	 *
	 * @param temperature the new temperature
	 */
	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}
}
