package edu.westga.cs3230.healthcare_dbms.model;

public class Login {

	private String user_name;
	private String password;

	public Login(String userName, String password) {
		this.user_name = userName;
		this.password = password;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
