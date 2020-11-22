package edu.westga.cs3230.healthcare_dbms.model;

/**
 * Represents a login attempt.
 *
 * @author Joseph Fuller and Andrew Steinborn
 */
public class Login {

	/** The user name. */
	private String user_name;
	
	/** The password. */
	private String password;

	/**
	 * Instantiates a new login.
	 *
	 * @param userName the user name
	 * @param password the password
	 */
	public Login(String userName, String password) {
		this.user_name = userName;
		this.password = password;
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
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

}
