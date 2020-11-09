package edu.westga.cs3230.healthcare_dbms.model;

public class FinalDiagnosis {

	private Integer appointment_id;
	private String diagnosis_result;
	
	public FinalDiagnosis() {
		this.appointment_id = null;
		this.diagnosis_result = null;
	}
	
	public FinalDiagnosis(String result) {
		this.appointment_id = null;
		this.diagnosis_result = result;
	}

	public Integer getAppointment_id() {
		return appointment_id;
	}

	public void setAppointment_id(Integer appointment_id) {
		this.appointment_id = appointment_id;
	}

	public String getDiagnosis_result() {
		return diagnosis_result;
	}

	public void setDiagnosis_result(String diagnosis_result) {
		this.diagnosis_result = diagnosis_result;
	}

}
