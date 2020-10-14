package edu.westga.cs3230.healthcare_dbms.io.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.westga.cs3230.healthcare_dbms.model.Person;
import edu.westga.cs3230.healthcare_dbms.model.dal.PostDAL;
import edu.westga.cs3230.healthcare_dbms.sql.SqlAttribute;
import edu.westga.cs3230.healthcare_dbms.sql.SqlGetter;
import edu.westga.cs3230.healthcare_dbms.sql.SqlManager;

/**
 * Description
 * 
 */
public class HealthcareDatabaseClient {
	
	private PostDAL postDal;
	
	private QueryResult lastResult;
	private String dbUrl;
	
	/**
	 * Instantiates a new healthcare database client.
	 *
	 * @param input/output the storage for queries
	 */
	public HealthcareDatabaseClient(String dbUrl, List<QueryResult> storageForReadQueries) {
		this.lastResult = null;
		this.dbUrl = dbUrl;
		this.postDal = new PostDAL(dbUrl);
	}
	
	public boolean callQuery(String query) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	public ArrayList<HashMap<String, SqlAttribute>> getQueryTuples() {
		// TODO Auto-generated method stub
		return null;
	}

	public QueryResult attemptLogin(String username, String password) throws SQLException {
		// TODO check for correctness
		String prepared = "select distinct r.user_name, r.user_id, p.fname, p.lname "
						+ "from Person p, RegisteredUser r, UserPasswordStore ups "
						+ "where p.person_id = r.person_id and r.user_name = ? and ups.password = ?";
		
		SqlManager manager = new SqlManager();
		try (Connection con = DriverManager.getConnection(this.dbUrl);
				PreparedStatement stmt = con.prepareStatement(prepared);
				) {
			stmt.setString(1, username);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();
			manager.readTuples(rs);
		}
		
		this.lastResult = new QueryResult(manager.getTuples());
		
		return this.lastResult;
	}
	
	public QueryResult getLastQueryResult() {
		return this.lastResult;
	}

	public QueryResult attemptAddPatient(Person patient) throws SQLException {
		// TODO check for correctness
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
		
		this.lastResult = new QueryResult(manager.getTuples());
		
		if(this.lastResult == null || this.lastResult.getTuples().size() == 0) {
			this.postDal.postTuple(patient);
		}
		
		return this.lastResult;
	}


}
