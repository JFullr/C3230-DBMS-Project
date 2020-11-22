package edu.westga.cs3230.healthcare_dbms.model;

// TODO: Auto-generated Javadoc
/**
 * The Class RegisteredUser.
 */
public class RegisteredUser {

	/** The user id. */
	private Integer user_id;
	
	/** The user name. */
	private String user_name;
	
	/** The person id. */
	//private String user_type;
	private Integer person_id;

	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	public Integer getUser_id() {
		return user_id;
	}

	/**
	 * Sets the user id.
	 *
	 * @param user_id the new user id
	 */
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	/**
	 * Gets the user name.
	 *
	 * @return the user name
	 */
	public String getUser_name() {
		return user_name;
	}

	/**
	 * Sets the user name.
	 *
	 * @param user_name the new user name
	 */
	public void setUser_name(String user_name) {
		this.user_name = user_name;
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

}
