package edu.westga.cs3230.healthcare_dbms.sql;

public class SqlAttribute {
	
	private String attribute;
	private Object value;
	
	public SqlAttribute(String attribute, Object value) {
		this.attribute = attribute;
		this.value = value;
	}
	
	public String getAttribute() {
		return attribute;
	}
	public Object getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return this.attribute + "(" + this.value.getClass().getName() + "): " + this.value;
	}
}
