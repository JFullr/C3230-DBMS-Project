package edu.westga.cs3230.healthcare_dbms.io.database;

import java.util.ArrayList;
import java.util.Iterator;

import edu.westga.cs3230.healthcare_dbms.sql.SqlAttribute;
import edu.westga.cs3230.healthcare_dbms.sql.SqlTuple;

/**
 * Represents the result of a query.
 *
 * @author Joseph Fuller and Andrew Steinborn
 */
public class QueryResult implements Iterable<QueryResult>{

	/** The tuple. */
	private SqlTuple tuple;
	
	/** The associated. */
	private Object associated;
	
	/** The batch. */
	private final ArrayList<QueryResult> batch;
	
	/**
	 * Instantiates a new query result.
	 *
	 * @param results the results
	 */
	public QueryResult(ArrayList<SqlTuple> results) {
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
	 * Combines and merges two query results.
	 *
	 * @param other the other query result.
	 * @return the combined and merged query result
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
	 * Gets the object associated with this result.
	 *
	 * @return the associated object
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
