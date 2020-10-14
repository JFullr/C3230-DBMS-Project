package edu.westga.cs3230.healthcare_dbms.model.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.westga.cs3230.healthcare_dbms.io.database.QueryResult;
import edu.westga.cs3230.healthcare_dbms.model.Person;
import edu.westga.cs3230.healthcare_dbms.sql.SqlManager;

public class PersonDAL {
	
	private PostDAL postDal;
	private String dbUrl;

	public PersonDAL(String dbUrl) {
		this.dbUrl = dbUrl;
		this.postDal = new PostDAL(dbUrl);
	}
	
	public QueryResult attemptAddPatient(Person patient) throws SQLException {
		String prepared = "select ssn "
						+ "from Person "
						+ "where ssn = ?";
		
		SqlManager manager = new SqlManager();
		try (Connection con = DriverManager.getConnection(this.dbUrl);
				PreparedStatement stmt = con.prepareStatement(prepared);
				) {
			stmt.setString(1, ""+patient.getSSN());
			ResultSet rs = stmt.executeQuery();
			manager.readTuples(rs);
		}
		
		QueryResult result = new QueryResult(manager.getTuples());
		
		if(result == null || result.getTuples().size() == 0) {
			this.postDal.postTuple(patient);
		}
		
		return result;
	}

}
