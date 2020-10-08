package edu.westga.cs3230.healthcare_dbms.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import query.utils.SQLData;
import query.utils.SQLManager;

public class HealthcareQueryResult {
	
	private ArrayList<HashMap<String, SQLData>> tuples;
	
	public HealthcareQueryResult(String query) {
		this.tuples = null;
		this.callQuery(query);
	}
	
	private void  callQuery(String query) {
		
		SQLManager manager = new SQLManager();
		try {
			manager.readTuples(null, query);
			this.tuples = manager.getTuples();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<HashMap<String, SQLData>> getTuples() {
		return this.tuples;
	}

}
