package edu.westga.cs3230.healthcare_dbms.model.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.westga.cs3230.healthcare_dbms.io.database.DatabaseConnector;
import edu.westga.cs3230.healthcare_dbms.io.database.QueryResult;
import edu.westga.cs3230.healthcare_dbms.model.Login;
import edu.westga.cs3230.healthcare_dbms.sql.SqlManager;
import edu.westga.cs3230.healthcare_dbms.sql.SqlTuple;

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
	
	public QueryResult attemptAdminLogin(Login login) throws SQLException {
		SqlManager manager = new SqlManager();
		Connection con = this.connector.getCurrentConnection();
		try (PreparedStatement stmt = con.prepareCall("CALL try_login(?, ?)")) {
			stmt.setString(1, login.getUser_name());
			stmt.setString(2, login.getPassword());
			ResultSet rs = stmt.executeQuery();
			manager.readTuples(rs);
		}
		
		if(manager.getTuples() == null || manager.getTuples().size() == 0) {
			throw new SQLException("Failed Admin Login");
		}
		
		Integer i_uid = null;
		try {
			String user_id = manager.getTuples().get(0).get("user_id").getValue().toString();
			i_uid = Integer.parseInt(user_id);
		}	catch(Exception e) {
			throw new SQLException("Failed Admin Login");
		}
		
		SqlManager adminManager = new SqlManager();
		String query = "SELECT * from Admin WHERE person_id = ? LIMIT 1";
		try (PreparedStatement stmt = con.prepareStatement(query)) {
			stmt.setObject(1, i_uid);
			ResultSet rs = stmt.executeQuery();
			adminManager.readTuples(rs);
		}
		
		if(adminManager.getTuples().size() < 1) {
			throw new SQLException("Failed Admin Login");
		}

		return new QueryResult(manager.getTuples());
	}
	
}
