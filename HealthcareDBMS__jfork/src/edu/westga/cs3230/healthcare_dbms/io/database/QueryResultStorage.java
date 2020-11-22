package edu.westga.cs3230.healthcare_dbms.io.database;

import java.util.ArrayList;

/**
 * The Class QueryResultStorage.
 */
public class QueryResultStorage {
	
	/** The results. */
	private ArrayList<ArrayList<QueryResult>> results;
	
	/**
	 * Instantiates a new query result storage.
	 */
	public QueryResultStorage() {
		this.results = new ArrayList<ArrayList<QueryResult>>();
	}
	
	/**
	 * Adds the.
	 *
	 * @param result the result
	 */
	public void add(QueryResult result) {
		ArrayList<QueryResult> list = new ArrayList<QueryResult>();
		list.add(result);
		this.results.add(list);
	}
	
	/**
	 * Adds the.
	 *
	 * @param result the result
	 */
	public void add(ArrayList<QueryResult> result) {
		this.results.add(result);
	}
	
	/**
	 * Gets the latest results.
	 *
	 * @return the latest results
	 */
	public ArrayList<QueryResult> getLatestResults() {
		if(this.results.size() < 1) {
			return null;
		}
		return this.results.get(this.results.size()-1);
	}

	/**
	 * Clear.
	 */
	public void clear() {
		this.results.clear();
	}

}
