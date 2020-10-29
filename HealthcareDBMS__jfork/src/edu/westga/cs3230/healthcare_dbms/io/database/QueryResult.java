package edu.westga.cs3230.healthcare_dbms.io.database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import edu.westga.cs3230.healthcare_dbms.sql.SqlAttribute;
import edu.westga.cs3230.healthcare_dbms.sql.SqlManager;
import edu.westga.cs3230.healthcare_dbms.sql.SqlTuple;

public class QueryResult implements Iterable<QueryResult>{
	
	private String dbUrl;
	private SqlTuple tuple;
	private Object associated;
	
	private ArrayList<QueryResult> batch;
	
	public QueryResult(String dbUrl, String query) {
		this.tuple = null;
		this.setAssociated(null);
		this.dbUrl = dbUrl;
		this.batch = new ArrayList<QueryResult>();
		this.callQuery(query);
	}
	
	public QueryResult(ArrayList<SqlTuple> results) {
		this.dbUrl = null;
		this.setAssociated(null);
		this.batch = new ArrayList<QueryResult>();
		this.addTuples(results);
	}
	
	public QueryResult(SqlTuple result) {
		this.dbUrl = null;
		this.setAssociated(null);
		this.batch = new ArrayList<QueryResult>();
		this.tuple = result;
	}
	
	public SqlTuple getTuple() {
		return this.tuple;
	}
	
	public QueryResult combine(QueryResult other) {
		if(other == null ) {
			return this;
		}
		
		this.batch.addAll(other.getBatch());
		
		return this;
	}
	
	public QueryResult combineMerge(QueryResult other) {
		if(other == null ) {
			return this;
		}
		
		for(QueryResult result : other.getBatch()) {
			if(result.getTuple() != null) {
				for(SqlAttribute attr : result.getTuple()) {
					this.tuple.add(attr);
				}
			}
		}
		
		this.batch.clear();
		
		return this;
	}
	
	public ArrayList<QueryResult> getBatch() {
		
		ArrayList<QueryResult> combined = new ArrayList<QueryResult>();
		//if(this.batch.size() > 0 && this.batch.get(0) != this) {
			combined.add(this);
		//}
		combined.addAll(this.batch);
		return combined;
	}

	public Object getAssociated() {
		return associated;
	}
	
	public void setAssociated(Object associated) {
		this.associated = associated;
	}

	@Override
	public Iterator<QueryResult> iterator() {
		return this.getBatch().iterator();
	}
	
	private void callQuery(String query) {
		
		SqlManager manager = new SqlManager();
		try {
			manager.readTuples(this.dbUrl, query);
			this.addTuples(manager.getTuples());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void addTuples(ArrayList<SqlTuple> tuples) {
		
		if(tuples == null || tuples.size() == 0) {
			return;
		}
		this.tuple = tuples.get(0);
		
		for(int i = 1; i < tuples.size(); i++) {
			this.combine(new QueryResult(tuples.get(i)));
		}
	}

}
