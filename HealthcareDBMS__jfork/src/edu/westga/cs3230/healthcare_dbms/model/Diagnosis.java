package edu.westga.cs3230.healthcare_dbms.model;

public class Diagnosis {
	
	private Integer appointment_id;
	private String diagnosis_description;
	
	public Diagnosis(Integer appointment_id, String diagnosis_description) {
		
		this.appointment_id = appointment_id;
		this.diagnosis_description = diagnosis_description;
		
	}

	public Integer getAppointment_id() {
		return appointment_id;
	}

	public void setAppointment_id(Integer appointment_id) {
		this.appointment_id = appointment_id;
	}

	public String getDiagnosis_description() {
		return diagnosis_description;
	}

	public void setDiagnosis_description(String diagnosis_description) {
		this.diagnosis_description = diagnosis_description;
	}

}
