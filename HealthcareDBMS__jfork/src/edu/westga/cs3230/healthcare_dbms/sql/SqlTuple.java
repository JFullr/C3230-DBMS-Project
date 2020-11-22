package edu.westga.cs3230.healthcare_dbms.sql;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

// TODO: Auto-generated Javadoc
/**
 * The Class SqlTuple.
 */
public class SqlTuple implements Iterable<SqlAttribute> {
	
	/** The Constant SQL_GENERATED_ID. */
	public static final String SQL_GENERATED_ID = "GEN_ID";
	
	/** The attrs. */
	private HashMap<String, SqlAttribute> attrs;
	
	/**
	 * Instantiates a new sql tuple.
	 */
	public SqlTuple() {
		this.attrs = new LinkedHashMap<>();
	}
	
	/**
	 * Instantiates a new sql tuple.
	 *
	 * @param attr the attr
	 */
	public SqlTuple(SqlAttribute attr) {
		this.attrs = new LinkedHashMap<>();
		if(attr != null) {
			this.attrs.put(attr.getAttribute(), attr);
		}
	}
	
	/**
	 * Instantiates a new sql tuple.
	 *
	 * @param attrs the attrs
	 */
	public SqlTuple(HashMap<String, SqlAttribute> attrs) {
		if(attrs == null) {
			this.attrs = new LinkedHashMap<String, SqlAttribute>();
		} else {
			this.attrs = attrs;
		}
	}
	
	/**
	 * Gets the attributes.
	 *
	 * @return the attributes
	 */
	public HashMap<String, SqlAttribute> getAttributes() {
		return this.attrs;
	}
	
	/**
	 * Gets the.
	 *
	 * @param attribute the attribute
	 * @return the sql attribute
	 */
	public SqlAttribute get(String attribute) {
		return this.attrs.get(attribute);
	}
	
	/**
	 * Sets the.
	 *
	 * @param attribute the attribute
	 * @param value the value
	 * @return the sql attribute
	 */
	public SqlAttribute set(String attribute, SqlAttribute value) {
		return this.attrs.put(attribute, value);
	}
	
	/**
	 * Adds the.
	 *
	 * @param attributeName the attribute name
	 * @param value the value
	 * @return true, if successful
	 */
	public boolean add(String attributeName, Object value) {
		SqlAttribute attr = new SqlAttribute(attributeName, value);
		return this.add(attr);
	}
	
	/**
	 * Adds the.
	 *
	 * @param value the value
	 * @return true, if successful
	 */
	public boolean add(SqlAttribute value) {
		
		if(this.attrs.get(value.getAttribute()) == null) {
			this.attrs.put(value.getAttribute(), value);
			return false;
		}
		
		StringBuilder builder = new StringBuilder(value.getAttribute());
		builder.append("_");
		int defaultLength = builder.length();
		for(int i = 0; i < Integer.MAX_VALUE; i++) {
			builder.append(i);
			
			if(this.attrs.get(builder.toString()) == null) {
				this.attrs.put(builder.toString(), value);
				return true;
			}
			
			builder.setLength(defaultLength);
		}
		
		return false;
	}
	
	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		StringBuilder build = new StringBuilder("");
		for(SqlAttribute attr : this) {
			build.append(attr.getValue());
			build.append(" ; ");
		}
		return build.toString();
	}

	/**
	 * Iterator.
	 *
	 * @return the iterator
	 */
	@Override
	public Iterator<SqlAttribute> iterator() {
		return this.attrs.values().iterator();
	}
	
	/**
	 * Filter.
	 *
	 * @param keep the keep
	 * @return the sql tuple
	 */
	public SqlTuple filter(BiPredicate<String, SqlAttribute> keep) {
		HashMap<String, SqlAttribute> filterCopy = new LinkedHashMap<>(this.attrs);
		filterCopy.entrySet().removeIf(entry -> !keep.test(entry.getKey(), entry.getValue()));
		return new SqlTuple(filterCopy);
	}

	/**
	 * Hide based on.
	 *
	 * @param object the object
	 * @return the sql tuple
	 */
	public SqlTuple hideBasedOn(Object object) {
		if (object instanceof AssociatedHider) {
			return filter((key, value) -> !((AssociatedHider) object).hideFunction().test(key));
		}
		HashMap<String, SqlAttribute> filterCopy = new LinkedHashMap<>(this.attrs);
		for (Field field : object.getClass().getFields()) {
			if (field.isAnnotationPresent(UiHide.class)) {
				filterCopy.remove(field.getName());
			}
		}
		return new SqlTuple(filterCopy);
	}

	/**
	 * Transform.
	 *
	 * @param transformer the transformer
	 * @return the sql tuple
	 */
	public SqlTuple transform(Consumer<Map<String, SqlAttribute>> transformer) {
		HashMap<String, SqlAttribute> transformed = new LinkedHashMap<>(this.attrs);
		transformer.accept(transformed);
		return new SqlTuple(transformed);
	}

}
