package edu.westga.cs3230.healthcare_dbms.sql;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

// TODO: Auto-generated Javadoc
/**
 * The Class SqlManager.
 */
public class SqlManager {
	
	/** The tuples returned from a DB call. */
	private final ArrayList<SqlTuple> tuples;
	
	/**
	 * Instantiates a new sql manager.
	 */
	public SqlManager() {
		this.tuples = new ArrayList<SqlTuple>();
	}
	
	/**
	 * Gets the tuples.
	 *
	 * @return the tuples
	 */
	public ArrayList<SqlTuple> getTuples(){
		return this.tuples;
	}
	
	/**
	 * Adds the raw.
	 *
	 * @param tuple the tuple
	 */
	public void addRaw(SqlTuple tuple) {
		this.tuples.add(tuple);
	}
	
	/**
	 * Sets the raw.
	 *
	 * @param tuple the new raw
	 */
	public void setRaw(SqlTuple tuple) {
		this.tuples.clear();
		this.tuples.add(tuple);
	}
	
	/**
	 * Read tuples.
	 *
	 * @param rs the rs
	 * @throws SQLException the SQL exception
	 */
	public void readTuples(ResultSet rs) throws SQLException {
		
		while (rs.next()) {
			HashMap<String, SqlAttribute> attributes = new LinkedHashMap<String, SqlAttribute>();
			ResultSetMetaData meta = rs.getMetaData();
			
			for(int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
				String labelName = meta.getColumnName(i);
				String typeName = meta.getColumnTypeName(i);
				
				Object obj = SqlTypeConverter.convertObject(rs, i, typeName, meta.getPrecision(i));
				
				
				attributes.put(labelName, new SqlAttribute(labelName, obj));
			}
			
			this.tuples.add(new SqlTuple(attributes));
		}
		
	}
	
	/**
	 * Sets the tuple.
	 *
	 * @param rs the new tuple
	 * @throws SQLException the SQL exception
	 */
	public void setTuple(ResultSet rs) throws SQLException {
		
		while (rs.next()) {
			HashMap<String, SqlAttribute> attributes = new LinkedHashMap<String, SqlAttribute>();
			ResultSetMetaData meta = rs.getMetaData();
			
			for(int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
				String labelName = meta.getColumnName(i);
				String typeName = meta.getColumnTypeName(i);
				
				Object obj = SqlTypeConverter.convertObject(rs, i, typeName, meta.getPrecision(i));
				
				attributes.put(labelName, new SqlAttribute(labelName, obj));
			}
			
			this.tuples.add(new SqlTuple(attributes));
		}
		
	}
	
}
