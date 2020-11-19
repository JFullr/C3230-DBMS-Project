package edu.westga.cs3230.healthcare_dbms.sql;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

public class SqlTuple implements Iterable<SqlAttribute> {
	
	public static final String SQL_GENERATED_ID = "GEN_ID";
	
	private HashMap<String, SqlAttribute> attrs;
	
	public SqlTuple() {
		this.attrs = new LinkedHashMap<>();
	}
	
	public SqlTuple(SqlAttribute attr) {
		this.attrs = new LinkedHashMap<>();
		if(attr != null) {
			this.attrs.put(attr.getAttribute(), attr);
		}
	}
	
	public SqlTuple(HashMap<String, SqlAttribute> attrs) {
		if(attrs == null) {
			this.attrs = new LinkedHashMap<String, SqlAttribute>();
		} else {
			this.attrs = attrs;
		}
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
	
	public boolean add(String attributeName, Object value) {
		SqlAttribute attr = new SqlAttribute(attributeName, value);
		return this.add(attr);
	}
	
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
	

	@Override
	public Iterator<SqlAttribute> iterator() {
		return this.attrs.values().iterator();
	}
	
	public SqlTuple filter(BiPredicate<String, SqlAttribute> keep) {
		HashMap<String, SqlAttribute> filterCopy = new LinkedHashMap<>(this.attrs);
		filterCopy.entrySet().removeIf(entry -> !keep.test(entry.getKey(), entry.getValue()));
		return new SqlTuple(filterCopy);
	}

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

	public SqlTuple transform(Consumer<Map<String, SqlAttribute>> transformer) {
		HashMap<String, SqlAttribute> transformed = new LinkedHashMap<>(this.attrs);
		transformer.accept(transformed);
		return new SqlTuple(transformed);
	}

}
