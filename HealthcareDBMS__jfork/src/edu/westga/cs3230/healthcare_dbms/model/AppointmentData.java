package edu.westga.cs3230.healthcare_dbms.model;

public class AppointmentData {
	
	private Appointment appointment;
	private PatientData patient;
	
	public AppointmentData() {
		this.setAppointment(null);
		this.setPatient(null);
	}
	
	public AppointmentData(Appointment appt) {
		this.setAppointment(appt);
		this.setPatient(null);
	}
	
	public AppointmentData(Appointment appointment, PatientData patient) {
		this.setAppointment(appointment);
		this.setPatient(patient);
	}

	public PatientData getPatient() {
		return patient;
	}

	public void setPatient(PatientData patient) {
		this.patient = patient;
	}

	public Appointment getAppointment() {
		return appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}

}
