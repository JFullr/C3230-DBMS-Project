package edu.westga.cs3230.healthcare_dbms.model;

import java.sql.Date;

import edu.westga.cs3230.healthcare_dbms.sql.SqlGenerated;
import edu.westga.cs3230.healthcare_dbms.sql.UiHide;

// TODO: Auto-generated Javadoc
/**
 * The Class Person.
 */
public class Person {
	
	/** The person id. */
	@SqlGenerated
	@UiHide
	private Integer person_id;
	
	/** The fname. */
	private String fname;
	
	/** The lname. */
	private String lname;
	
	/** The middle initial. */
	private String middle_initial;
	
	/** The gender. */
	private String gender;
	
	/** The dob. */
	private Date DOB;
	
	/** The ssn. */
	private Integer SSN;
	
	/** The contact phone. */
	private String contact_phone;
	
	/** The contact email. */
	private String contact_email;
	
	/** The mailing address id. */
	@UiHide
	private Integer mailing_address_id;
	
	/**
	 * Instantiates a new person.
	 *
	 * @param email the email
	 * @param phone the phone
	 * @param dob the dob
	 * @param fname the fname
	 * @param lname the lname
	 * @param middleInitial the middle initial
	 * @param gender the gender
	 * @param ssn the ssn
	 */
	public Person(String email, String phone, Date dob, String fname, String lname, String middleInitial, String gender, String ssn) {
		this.setContact_email(email);
		this.setContact_phone(phone);
		this.setDOB(dob);
		this.setFname(fname);
		this.setLname(lname);
		this.setMiddle_initial(middleInitial);
		this.setSSN(ssn == null || ssn.isEmpty() ? null : Integer.parseInt(ssn));
		this.person_id = null;
		this.setMailing_address_id(null);
		this.setGender(gender);
	}

	/**
	 * Gets the person id.
	 *
	 * @return the person id
	 */
	public Integer getPerson_id() {
		return person_id;
	}

	/**
	 * Sets the person id.
	 *
	 * @param person_id the new person id
	 */
	public void setPerson_id(Integer person_id) {
		this.person_id = person_id;
	}

	/**
	 * Gets the fname.
	 *
	 * @return the fname
	 */
	public String getFname() {
		return fname;
	}

	/**
	 * Sets the fname.
	 *
	 * @param fname the new fname
	 */
	public void setFname(String fname) {
		this.fname = fname;
	}

	/**
	 * Gets the lname.
	 *
	 * @return the lname
	 */
	public String getLname() {
		return lname;
	}

	/**
	 * Sets the lname.
	 *
	 * @param lname the new lname
	 */
	public void setLname(String lname) {
		this.lname = lname;
	}

	/**
	 * Gets the middle initial.
	 *
	 * @return the middle initial
	 */
	public String getMiddle_initial() {
		return middle_initial;
	}

	/**
	 * Sets the middle initial.
	 *
	 * @param middle_initial the new middle initial
	 */
	public void setMiddle_initial(String middle_initial) {
		this.middle_initial = middle_initial;
	}

	/**
	 * Gets the dob.
	 *
	 * @return the dob
	 */
	public Date getDOB() {
		return DOB;
	}

	/**
	 * Sets the dob.
	 *
	 * @param dOB the new dob
	 */
	public void setDOB(Date dOB) {
		DOB = dOB;
	}

	/**
	 * Gets the ssn.
	 *
	 * @return the ssn
	 */
	public Integer getSSN() {
		return SSN;
	}

	/**
	 * Sets the ssn.
	 *
	 * @param sSN the new ssn
	 */
	public void setSSN(Integer sSN) {
		SSN = sSN;
	}

	/**
	 * Gets the contact phone.
	 *
	 * @return the contact phone
	 */
	public String getContact_phone() {
		return contact_phone;
	}

	/**
	 * Sets the contact phone.
	 *
	 * @param contact_phone the new contact phone
	 */
	public void setContact_phone(String contact_phone) {
		this.contact_phone = contact_phone;
	}

	/**
	 * Gets the contact email.
	 *
	 * @return the contact email
	 */
	public String getContact_email() {
		return contact_email;
	}

	/**
	 * Sets the contact email.
	 *
	 * @param contact_email the new contact email
	 */
	public void setContact_email(String contact_email) {
		this.contact_email = contact_email;
	}

	/**
	 * Gets the mailing address id.
	 *
	 * @return the mailing address id
	 */
	public Integer getMailing_address_id() {
		return mailing_address_id;
	}

	/**
	 * Sets the mailing address id.
	 *
	 * @param mailing_address_id the new mailing address id
	 */
	public void setMailing_address_id(Integer mailing_address_id) {
		this.mailing_address_id = mailing_address_id;
	}

	/**
	 * Gets the gender.
	 *
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * Sets the gender.
	 *
	 * @param gender the new gender
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	/**
	 * Full name.
	 *
	 * @return the string
	 */
	public String fullName() {
		return this.fname + " " + this.lname;
	}
}
