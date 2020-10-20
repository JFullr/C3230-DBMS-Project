package edu.westga.cs3230.healthcare_dbms.io.database;

import java.util.ArrayList;

public class QueryResultStorage {
	
	private ArrayList<ArrayList<QueryResult>> results;
	
	public QueryResultStorage() {
		this.results = new ArrayList<ArrayList<QueryResult>>();
	}
	
	public void add(QueryResult result) {
		ArrayList<QueryResult> list = new ArrayList<QueryResult>();
		list.add(result);
		this.results.add(list);
	}
	
	public void add(ArrayList<QueryResult> result) {
		this.results.add(result);
	}
	
	public ArrayList<QueryResult> getLatestResults() {
		if(this.results.size() < 1) {
			return null;
		}
		return this.results.get(this.results.size()-1);
	}

	public void clear() {
		this.results.clear();
	}

}
