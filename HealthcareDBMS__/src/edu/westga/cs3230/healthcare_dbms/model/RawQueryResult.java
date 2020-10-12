package edu.westga.cs3230.healthcare_dbms.model;

import java.util.List;
import java.util.Map;

public class RawQueryResult {
	
	private List<Map<String, Object>> tuples;
	
	public RawQueryResult(List<Map<String, Object>> tuples) {
		this.tuples = tuples;
	}

	public List<Map<String, Object>> getTuples() {
		return this.tuples;
	}

}
