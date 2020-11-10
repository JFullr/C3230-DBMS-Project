package edu.westga.cs3230.healthcare_dbms.sql;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class SqlTuple implements Iterable<SqlAttribute> {
	
	public static final String SQL_GENERATED_ID = "GEN_ID";
	
	private HashMap<String, SqlAttribute> attrs;
	
	public SqlTuple(SqlAttribute attr) {
		this.attrs = new LinkedHashMap<>();
		this.attrs.put(attr.getAttribute(), attr);
	}
	
	public SqlTuple(HashMap<String, SqlAttribute> attrs) {
		this.attrs = attrs;
	}
	
	public HashMap<String, SqlAttribute> getAttributes() {
		return this.attrs;
	}
	
	public SqlAttribute get(String attribute) {
		return this.attrs.get(attribute);
	}
	
	public SqlAttribute set(String attribute, SqlAttribute value) {
		return this.attrs.put(attribute, value);
	}
	
	public boolean add(SqlAttribute value) {
		
		if(this.attrs.get(value.getAttribute()) == null) {
			this.attrs.put(value.getAttribute(), value);
			return false;
		}
		
		StringBuilder builder = new StringBuilder(value.getAttribute());
		builder.append("_");
		for(int i = 0; i < Integer.MAX_VALUE; i++) {
			builder.append(i);
			
			if(this.attrs.get(builder.toString()) == null) {
				this.attrs.put(builder.toString(), value);
				return true;
			}
			
			builder.setLength(builder.length()-1);
		}
		
		
		
		return false;
	}
	

	@Override
	public Iterator<SqlAttribute> iterator() {
		return this.attrs.values().iterator();
	}
	
	

}
