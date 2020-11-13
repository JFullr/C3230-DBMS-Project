package edu.westga.cs3230.healthcare_dbms.model.dal;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.westga.cs3230.healthcare_dbms.io.database.QueryResult;
import edu.westga.cs3230.healthcare_dbms.model.Login;
import edu.westga.cs3230.healthcare_dbms.sql.SqlManager;

public class LoginDAL {

	private String dbUrl;

	public LoginDAL(String dbUrl) {
		this.dbUrl = dbUrl;
	}

	public QueryResult attemptLogin(Login login) throws SQLException {
		String prepared = "select distinct r.user_name, r.user_id, p.fname, p.lname "
				+ "from Person p, RegisteredUser r, UserPasswordStore ups "
				+ "where p.person_id = r.person_id and r.user_name = ? and ups.password_salted_hashed = ?";

		SqlManager manager = new SqlManager();
		try (Connection con = DriverManager.getConnection(this.dbUrl);
				PreparedStatement stmt = con.prepareStatement(prepared);) {
			stmt.setString(1, login.getUser_name());
			stmt.setString(2, getHashedPassword(login.getPassword()));
			ResultSet rs = stmt.executeQuery();
			manager.readTuples(rs);
		}

		return new QueryResult(manager.getTuples());
	}


	private String getHashedPassword(String password) {
		// TODO: this is not how you'd store a password properly
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			throw new AssertionError(e);
		}
		digest.update(password.getBytes(StandardCharsets.UTF_8));
		byte[] hashed = digest.digest();

		StringBuilder builder = new StringBuilder();
		for (byte b : hashed) {
			builder.append(String.format("%02x", b));
		}
		return builder.toString();
	}

}
