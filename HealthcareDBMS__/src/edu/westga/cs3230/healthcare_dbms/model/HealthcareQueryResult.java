package edu.westga.cs3230.healthcare_dbms.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import query.utils.SQLData;
import query.utils.SQLManager;

public class HealthcareQueryResult {
	
	private String dbUrl;
	private ArrayList<HashMap<String, SQLData>> tuples;
	
	public HealthcareQueryResult(String dbUrl, String query) {
		this.tuples = null;
		this.dbUrl = dbUrl;
		this.callQuery(query);
	}
	
	public HealthcareQueryResult(ArrayList<HashMap<String, SQLData>> results) {
		this.dbUrl = null;
		this.tuples = results;
	}
	
	public ArrayList<HashMap<String, SQLData>> getTuples() {
		return this.tuples;
	}
	
	private void  callQuery(String query) {
		
		SQLManager manager = new SQLManager();
		try {
			manager.readTuples(this.dbUrl, query);
			this.tuples = manager.getTuples();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
