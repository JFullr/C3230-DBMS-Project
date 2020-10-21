package edu.westga.cs3230.healthcare_dbms.io.database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import edu.westga.cs3230.healthcare_dbms.sql.SqlManager;
import edu.westga.cs3230.healthcare_dbms.sql.SqlTuple;

public class QueryResult implements Iterable<QueryResult>{
	
	private String dbUrl;
	private SqlTuple tuples;
	private Object associated;
	
	private ArrayList<QueryResult> batch;
	
	public QueryResult(String dbUrl, String query) {
		this.tuples = null;
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
		this.tuples = result;
	}
	
	public SqlTuple getTuple() {
		return this.tuples;
	}
	
	public QueryResult combine(QueryResult other) {
		if(other == null || other.getTuple() == null) {
			return this;
		}
		
		this.batch.addAll(other.getBatch());
		
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
		
		if(tuples.size() == 0) {
			return;
		}
		this.tuples = tuples.get(0);
		
		for(int i = 1; i < tuples.size(); i++) {
			this.combine(new QueryResult(tuples.get(i)));
		}
	}

}
