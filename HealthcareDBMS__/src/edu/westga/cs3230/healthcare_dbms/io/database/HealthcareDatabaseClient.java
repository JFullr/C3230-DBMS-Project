package edu.westga.cs3230.healthcare_dbms.io.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.westga.cs3230.healthcare_dbms.model.HealthcareQueryResult;
import query.utils.SQLData;
import query.utils.SQLManager;

/**
 * Description
 * 
 */
public class HealthcareDatabaseClient {
	
	private HealthcareQueryResult lastResult;
	private String dbUrl;
	
	/**
	 * Instantiates a new healthcare database client.
	 *
	 * @param input/output the storage for queries
	 */
	public HealthcareDatabaseClient(String dbUrl, List<HealthcareQueryResult> storageForReadQueries) {
		this.lastResult = null;
		this.dbUrl = dbUrl;
	}
	
	public boolean callQuery(String query) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	public ArrayList<HashMap<String, SQLData>> getQueryTuples() {
		// TODO Auto-generated method stub
		return null;
	}

	public HealthcareQueryResult attemptLogin(String username, String password) throws SQLException {
		// TODO CORRECT PREPARED STATEMENT WITH QUERY
		String prepared = "from ? select pass = ?";
		SQLManager manager = new SQLManager();
		try (Connection con = DriverManager.getConnection(this.dbUrl);
				PreparedStatement stmt = con.prepareStatement(prepared);
				) {
			stmt.setString(1, username);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();
			manager.readTuples(rs);
		}
		
		return null;
	}
	
	public HealthcareQueryResult getLastQueryResult() {
		return this.lastResult;
	}


}
