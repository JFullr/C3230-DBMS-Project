package edu.westga.cs3230.healthcare_dbms.io.database;

import java.sql.SQLException;
import java.util.ArrayList;

import edu.westga.cs3230.healthcare_dbms.sql.SqlManager;
import edu.westga.cs3230.healthcare_dbms.sql.SqlTuple;

public class QueryResult {
	
	private String dbUrl;
	private ArrayList<SqlTuple> tuples;
	
	public QueryResult(String dbUrl, String query) {
		this.tuples = null;
		this.dbUrl = dbUrl;
		this.callQuery(query);
	}
	
	public QueryResult(ArrayList<SqlTuple> results) {
		this.dbUrl = null;
		this.tuples = results;
	}
	
	public ArrayList<SqlTuple> getTuples() {
		return this.tuples;
	}
	
	private void  callQuery(String query) {
		
		SqlManager manager = new SqlManager();
		try {
			manager.readTuples(this.dbUrl, query);
			this.tuples = manager.getTuples();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}