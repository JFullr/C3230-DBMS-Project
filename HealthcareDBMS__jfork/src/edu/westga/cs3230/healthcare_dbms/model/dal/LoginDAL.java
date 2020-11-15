package edu.westga.cs3230.healthcare_dbms.model.dal;

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
		SqlManager manager = new SqlManager();
		try (Connection con = DriverManager.getConnection(this.dbUrl);
				PreparedStatement stmt = con.prepareCall("CALL try_login(?, ?)")) {
			stmt.setString(1, login.getUser_name());
			stmt.setString(2, login.getPassword());
			ResultSet rs = stmt.executeQuery();
			manager.readTuples(rs);
		}

		return new QueryResult(manager.getTuples());
	}
}
