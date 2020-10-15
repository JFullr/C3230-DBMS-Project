package edu.westga.cs3230.healthcare_dbms.model.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.westga.cs3230.healthcare_dbms.io.database.QueryResult;
import edu.westga.cs3230.healthcare_dbms.model.Person;
import edu.westga.cs3230.healthcare_dbms.sql.SqlAttribute;
import edu.westga.cs3230.healthcare_dbms.sql.SqlManager;
import edu.westga.cs3230.healthcare_dbms.sql.SqlTuple;

public class UserTypeDAL {

	private String dbUrl;

	public UserTypeDAL(String dbUrl) {
		this.dbUrl = dbUrl;
	}

	public String getUserType(Person patient) throws SQLException {
		
		return this.determineUserType(patient.getPerson_id());
	}

	private String determineUserType(int person_id) throws SQLException {
		
		String[] tables = {"Doctor, Nurse, Admin, Patient"};
		for(String table : tables) {
			if(this.readType(table, person_id)) {
				return table;
			}
		}

		return null;
	}

	private boolean readType(String tableName, int person_id) throws SQLException {

		String prepared = "select ssn " + 
							"from ? " + 
							"where person_id = ?";

		SqlManager manager = new SqlManager();
		try (Connection con = DriverManager.getConnection(this.dbUrl);
				PreparedStatement stmt = con.prepareStatement(prepared);) {
			stmt.setString(1, "" + tableName);
			stmt.setObject(2, person_id);
			ResultSet rs = stmt.executeQuery();
			manager.readTuples(rs);
		}

		return manager.getTuples().size() == 1;

	}

}
