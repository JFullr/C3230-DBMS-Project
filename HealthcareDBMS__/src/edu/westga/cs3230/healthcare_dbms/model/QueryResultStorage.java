package edu.westga.cs3230.healthcare_dbms.model;

import java.util.ArrayList;

public class QueryResultStorage {
	
	private ArrayList<ArrayList<HealthcareQueryResult>> results;
	
	public QueryResultStorage() {
		this.results = new ArrayList<ArrayList<HealthcareQueryResult>>();
	}
	
	public ArrayList<HealthcareQueryResult> getLatestResults() {
		if(this.results.size() < 1) {
			return null;
		}
		return this.results.get(this.results.size()-1);
	}

}
