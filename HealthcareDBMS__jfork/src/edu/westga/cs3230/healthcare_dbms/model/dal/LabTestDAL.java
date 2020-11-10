package edu.westga.cs3230.healthcare_dbms.model.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import edu.westga.cs3230.healthcare_dbms.io.database.QueryResult;
import edu.westga.cs3230.healthcare_dbms.sql.SqlManager;

public class LabTestDAL {
	
	private String dbUrl;

	public LabTestDAL(String dbUrl) {
		this.dbUrl = dbUrl;
	}
	
	public QueryResult getLabTests() throws SQLException {
		String query = "select * "
						+ "from LabTest";
		
		SqlManager manager = new SqlManager();
		try (Connection con = DriverManager.getConnection(this.dbUrl);
				Statement stmt = con.createStatement()
				) {
			ResultSet rs = stmt.executeQuery(query);
			manager.readTuples(rs);
		}
		
		return new QueryResult(manager.getTuples());
	}

}
