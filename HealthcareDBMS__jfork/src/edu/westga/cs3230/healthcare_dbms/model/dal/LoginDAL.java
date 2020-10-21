package edu.westga.cs3230.healthcare_dbms.model.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.westga.cs3230.healthcare_dbms.io.database.HealthcareDatabaseClient;
import edu.westga.cs3230.healthcare_dbms.io.database.QueryResult;
import edu.westga.cs3230.healthcare_dbms.model.Login;
import edu.westga.cs3230.healthcare_dbms.sql.SqlManager;

public class LoginDAL {

	private ConnectionDAL connectionDal;

	public LoginDAL(ConnectionDAL connectionDal) {
		this.connectionDal = connectionDal;
	}

	public QueryResult attemptLogin(Login login) throws SQLException {
		String prepared = "select distinct r.user_name, r.user_id, p.fname, p.lname "
				+ "from Person p, RegisteredUser r, UserPasswordStore ups "
				+ "where p.person_id = r.person_id and r.user_name = ? and ups.password_salted_hashed = ?";

		SqlManager manager = new SqlManager();
		Connection con = connectionDal.getConnection();
		try (PreparedStatement stmt = con.prepareStatement(prepared)) {
			stmt.setString(1, login.getUser_name());
			stmt.setString(2, login.getPassword());
			ResultSet rs = stmt.executeQuery();
			manager.readTuples(rs);
		}

		return new QueryResult(manager.getTuples());
	}
	
}
