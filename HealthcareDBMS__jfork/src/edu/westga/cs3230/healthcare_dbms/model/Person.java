package edu.westga.cs3230.healthcare_dbms.model;

import java.sql.Date;

import edu.westga.cs3230.healthcare_dbms.sql.SqlGenerated;

public class Person {
	
	@SqlGenerated
	private Integer person_id;
	private String fname;
	private String lname;
	private String middle_initial;
	private Date DOB;
	private Integer SSN;
	private String contact_phone;
	private String contact_email;
	private Integer mailing_address_id;
	
	public Person(String email, String phone, Date dob, String fname, String lname, String middleInitial, String ssn) {
		this.setContact_email(email);
		this.setContact_phone(phone);
		this.setDOB(dob);
		this.setFname(fname);
		this.setLname(lname);
		this.setMiddle_initial(middleInitial);
		this.setSSN(ssn == null || ssn.isEmpty() ? null : Integer.parseInt(ssn));
		this.person_id = null;
		this.setMailing_address_id(null);
	}

	public Integer getPerson_id() {
		return person_id;
	}

	public void setPerson_id(Integer person_id) {
		this.person_id = person_id;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getMiddle_initial() {
		return middle_initial;
	}

	public void setMiddle_initial(String middle_initial) {
		this.middle_initial = middle_initial;
	}

	public Date getDOB() {
		return DOB;
	}

	public void setDOB(Date dOB) {
		DOB = dOB;
	}

	public Integer getSSN() {
		return SSN;
	}

	public void setSSN(Integer sSN) {
		SSN = sSN;
	}

	public String getContact_phone() {
		return contact_phone;
	}

	public void setContact_phone(String contact_phone) {
		this.contact_phone = contact_phone;
	}

	public String getContact_email() {
		return contact_email;
	}

	public void setContact_email(String contact_email) {
		this.contact_email = contact_email;
	}

	public Integer getMailing_address_id() {
		return mailing_address_id;
	}

	public void setMailing_address_id(Integer mailing_address_id) {
		this.mailing_address_id = mailing_address_id;
	}

}
