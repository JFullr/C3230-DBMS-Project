package edu.westga.cs3230.healthcare_dbms.io.database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import edu.westga.cs3230.healthcare_dbms.sql.SqlAttribute;
import edu.westga.cs3230.healthcare_dbms.sql.SqlManager;
import edu.westga.cs3230.healthcare_dbms.sql.SqlTuple;

// TODO: Auto-generated Javadoc
/**
 * The Class QueryResult.
 */
public class QueryResult implements Iterable<QueryResult>{
	
	/** The db url. */
	private String dbUrl;
	
	/** The tuple. */
	private SqlTuple tuple;
	
	/** The associated. */
	private Object associated;
	
	/** The batch. */
	private ArrayList<QueryResult> batch;
	
	/**
	 * Instantiates a new query result.
	 *
	 * @param dbUrl the db url
	 * @param query the query
	 */
	public QueryResult(String dbUrl, String query) {
		this.tuple = null;
		this.setAssociated(null);
		this.dbUrl = dbUrl;
		this.batch = new ArrayList<QueryResult>();
		this.callQuery(query);
	}
	
	/**
	 * Instantiates a new query result.
	 *
	 * @param results the results
	 */
	public QueryResult(ArrayList<SqlTuple> results) {
		this.dbUrl = null;
		this.setAssociated(null);
		this.batch = new ArrayList<QueryResult>();
		this.addTuples(results);
	}
	
	/**
	 * Instantiates a new query result.
	 *
	 * @param result the result
	 */
	public QueryResult(SqlTuple result) {
		this.dbUrl = null;
		this.setAssociated(null);
		this.batch = new ArrayList<QueryResult>();
		this.tuple = result;
	}
	
	/**
	 * Gets the tuple.
	 *
	 * @return the tuple
	 */
	public SqlTuple getTuple() {
		return this.tuple;
	}
	
	/**
	 * Combine.
	 *
	 * @param other the other
	 * @return the query result
	 */
	public QueryResult combine(QueryResult other) {
		if(other == null ) {
			return this;
		}
		
		this.batch.addAll(other.getBatch());
		
		return this;
	}
	
	/**
	 * Combine merge.
	 *
	 * @param other the other
	 * @return the query result
	 */
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
	
	/**
	 * Gets the batch.
	 *
	 * @return the batch
	 */
	public ArrayList<QueryResult> getBatch() {
		
		ArrayList<QueryResult> combined = new ArrayList<QueryResult>();
		combined.add(this);
		combined.addAll(this.batch);
		return combined;
	}

	/**
	 * Gets the associated.
	 *
	 * @return the associated
	 */
	public Object getAssociated() {
		return associated;
	}
	
	/**
	 * Sets the associated.
	 *
	 * @param associated the new associated
	 */
	public void setAssociated(Object associated) {
		this.associated = associated;
	}

	/**
	 * Iterator.
	 *
	 * @return the iterator
	 */
	@Override
	public Iterator<QueryResult> iterator() {
		return this.getBatch().iterator();
	}
	
	/**
	 * Call query.
	 *
	 * @param query the query
	 */
	private void callQuery(String query) {
		
		SqlManager manager = new SqlManager();
		try {
			manager.readTuples(this.dbUrl, query);
			this.addTuples(manager.getTuples());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds the tuples.
	 *
	 * @param tuples the tuples
	 */
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
