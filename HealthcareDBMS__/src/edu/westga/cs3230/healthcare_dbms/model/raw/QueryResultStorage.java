package edu.westga.cs3230.healthcare_dbms.model.raw;

import java.util.ArrayList;

public class QueryResultStorage {
	
	private ArrayList<ArrayList<RawQueryResult>> results;
	
	public QueryResultStorage() {
		this.results = new ArrayList<ArrayList<RawQueryResult>>();
	}
	
	public ArrayList<RawQueryResult> getLatestResults() {
		if(this.results.size() < 1) {
			return null;
		}
		return this.results.get(this.results.size()-1);
	}

}
