package edu.westga.cs3230.healthcare_dbms.model.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.westga.cs3230.healthcare_dbms.io.database.DatabaseConnector;
import edu.westga.cs3230.healthcare_dbms.io.database.QueryResult;
import edu.westga.cs3230.healthcare_dbms.model.Login;
import edu.westga.cs3230.healthcare_dbms.sql.SqlManager;

public class LoginDAL {

	private DatabaseConnector connector;

	public LoginDAL(DatabaseConnector connector) {
		this.connector = connector;
	}

	public QueryResult attemptLogin(Login login) throws SQLException {
		SqlManager manager = new SqlManager();
		Connection con = this.connector.getCurrentConnection();
		try (PreparedStatement stmt = con.prepareCall("CALL try_login(?, ?)")) {
			stmt.setString(1, login.getUser_name());
			stmt.setString(2, login.getPassword());
			ResultSet rs = stmt.executeQuery();
			manager.readTuples(rs);
		}

		return new QueryResult(manager.getTuples());
	}
}
