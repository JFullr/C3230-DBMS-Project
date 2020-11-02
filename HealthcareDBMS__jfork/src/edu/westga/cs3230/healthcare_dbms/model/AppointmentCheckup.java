package edu.westga.cs3230.healthcare_dbms.model;

public class AppointmentCheckup {
	private int appointment_id;
	private int systolic_pressure;
	private int diastolic_pressure;
	private int pulse;
	private double weight;

	public AppointmentCheckup() {
	}

	public AppointmentCheckup(int appointment_id, int systolic_pressure, int diastolic_pressure, int pulse,
							  double weight) {
		this.appointment_id = appointment_id;
		this.systolic_pressure = systolic_pressure;
		this.diastolic_pressure = diastolic_pressure;
		this.pulse = pulse;
		this.weight = weight;
	}

	/**
	 * @return the appointment_id
	 */
	public int getAppointment_id() {
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
	public int getDiastolic_pressure() {
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
	public int getPulse() {
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
	public double getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}
}
