package edu.westga.cs3230.healthcare_dbms.model;

import edu.westga.cs3230.healthcare_dbms.sql.SqlGenerated;

public class AppointmentCheckup {
	
	@SqlGenerated
	private Integer appointment_id;
	private Integer systolic_pressure;
	private Integer diastolic_pressure;
	private Integer pulse;
	private Double weight;
	private Double temperature;

	public AppointmentCheckup() {
		this.appointment_id = null;
		this.systolic_pressure = null;
		this.diastolic_pressure = null;
		this.pulse = null;
		this.weight = null;
		this.setTemperature(null);
	}

	public AppointmentCheckup(Integer systolic_pressure, Integer diastolic_pressure, Integer pulse,
			Double weight, Double temperature) {
		this.systolic_pressure = systolic_pressure;
		this.diastolic_pressure = diastolic_pressure;
		this.pulse = pulse;
		this.weight = weight;
		this.setTemperature(temperature);
	}
	
	public AppointmentCheckup(String systolic_pressure, String diastolic_pressure, String pulse,
			String weight, String temperature) throws Exception {
		this.systolic_pressure = Integer.parseInt(systolic_pressure);
		this.diastolic_pressure = Integer.parseInt(diastolic_pressure);
		this.pulse = Integer.parseInt(pulse);
		this.weight = Double.parseDouble(weight);
		this.setTemperature(Double.parseDouble(temperature));
	}

	/**
	 * @return the appointment_id
	 */
	public Integer getAppointment_id() {
		return appointment_id;
	}

	/**
	 * @param appointment_id the appointment_id to set
	 */
	public void setAppointment_id(int appointment_id) {
		this.appointment_id = appointment_id;
	}

	/**
	 * @return the systolic_pressure
	 */
	public int getSystolic_pressure() {
		return systolic_pressure;
	}

	/**
	 * @param systolic_pressure the systolic_pressure to set
	 */
	public void setSystolic_pressure(int systolic_pressure) {
		this.systolic_pressure = systolic_pressure;
	}

	/**
	 * @return the diastolic_pressure
	 */
	public Integer getDiastolic_pressure() {
		return diastolic_pressure;
	}

	/**
	 * @param diastolic_pressure the diastolic_pressure to set
	 */
	public void setDiastolic_pressure(int diastolic_pressure) {
		this.diastolic_pressure = diastolic_pressure;
	}

	/**
	 * @return the pulse
	 */
	public Integer getPulse() {
		return pulse;
	}

	/**
	 * @param pulse the pulse to set
	 */
	public void setPulse(int pulse) {
		this.pulse = pulse;
	}

	/**
	 * @return the weight
	 */
	public Double getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}

	public Double getTemperature() {
		return temperature;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}
}
