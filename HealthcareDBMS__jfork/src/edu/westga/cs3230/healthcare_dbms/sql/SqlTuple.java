package edu.westga.cs3230.healthcare_dbms.sql;

import java.util.HashMap;
import java.util.Iterator;

public class SqlTuple implements Iterable<SqlAttribute> {
	
	private HashMap<String, SqlAttribute> attrs;
	
	public SqlTuple(SqlAttribute attr) {
		this.attrs = new HashMap<String, SqlAttribute>();
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

	@Override
	public Iterator<SqlAttribute> iterator() {
		return this.attrs.values().iterator();
	}
	
	

}
