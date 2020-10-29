package edu.westga.cs3230.healthcare_dbms.model;

public class AppointmentData {
	
	private Appointment appt;
	private PatientData patient;
	
	public AppointmentData() {
		this.setAppt(null);
		this.setPatient(null);
	}
	
	public AppointmentData(Appointment appt) {
		this.setAppt(appt);
		this.setPatient(null);
	}
	
	public AppointmentData(Appointment appt, PatientData patient) {
		this.setAppt(appt);
		this.setPatient(patient);
	}

	public PatientData getPatient() {
		return patient;
	}

	public void setPatient(PatientData patient) {
		this.patient = patient;
	}

	public Appointment getAppt() {
		return appt;
	}

	public void setAppt(Appointment appt) {
		this.appt = appt;
	}

}
