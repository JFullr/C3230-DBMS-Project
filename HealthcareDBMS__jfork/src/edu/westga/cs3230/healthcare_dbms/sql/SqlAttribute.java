package edu.westga.cs3230.healthcare_dbms.sql;

// TODO: Auto-generated Javadoc
/**
 * The Class SqlAttribute.
 */
public class SqlAttribute {
	
	/** The attribute. */
	private String attribute;
	
	/** The value. */
	private Object value;
	
	/**
	 * Instantiates a new sql attribute.
	 *
	 * @param attribute the attribute
	 * @param value the value
	 */
	public SqlAttribute(String attribute, Object value) {
		this.attribute = attribute;
		this.value = value;
	}
	
	/**
	 * Gets the attribute.
	 *
	 * @return the attribute
	 */
	public String getAttribute() {
		return attribute;
	}
	
	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}
	
	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return this.attribute + "(" + this.value.getClass().getName() + "): " + this.value;
	}
}
