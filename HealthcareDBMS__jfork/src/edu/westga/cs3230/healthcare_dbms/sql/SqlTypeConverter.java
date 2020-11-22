package edu.westga.cs3230.healthcare_dbms.sql;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class SqlTypeConverter {
	
	//
/*	
	private static HashMap<String, Class<?>> nullMappings;
	static {
		try {
		nullMappings = new HashMap<String, Class<?>>();
		nullMappings.put("CHAR", String.class);
		nullMappings.put("VARCHAR", String.class);
		nullMappings.put("BLOB",  String.class);
		nullMappings.put("TEXT", String.class);
		nullMappings.put("ENUM",  String.class);
		nullMappings.put("SET", String.class);
		
	}
*/
	
	private static HashMap<String, Method> convertFromSqlMethod;
	static {
		try {
		convertFromSqlMethod = new HashMap<String, Method>();
		convertFromSqlMethod.put("CHAR", ResultSet.class.getMethod("getString", int.class));
		convertFromSqlMethod.put("VARCHAR", ResultSet.class.getMethod("getString", int.class));
		convertFromSqlMethod.put("BLOB", ResultSet.class.getMethod("getString", int.class));
		convertFromSqlMethod.put("TEXT", ResultSet.class.getMethod("getString", int.class));
		convertFromSqlMethod.put("ENUM", ResultSet.class.getMethod("getString", int.class));
		convertFromSqlMethod.put("SET", ResultSet.class.getMethod("getString", int.class));
		
		convertFromSqlMethod.put("FLOAT", ResultSet.class.getMethod("getFloat", int.class));
		convertFromSqlMethod.put("REAL", ResultSet.class.getMethod("getDouble", int.class));
		convertFromSqlMethod.put("DOUBLE PRECISION", ResultSet.class.getMethod("getDouble", int.class));
		convertFromSqlMethod.put("NUMERIC", ResultSet.class.getMethod("getInt", int.class));
		convertFromSqlMethod.put("DECIMAL", ResultSet.class.getMethod("getDouble", int.class));
		convertFromSqlMethod.put("TINYINT", ResultSet.class.getMethod("getInt", int.class));
		convertFromSqlMethod.put("SMALLINT", ResultSet.class.getMethod("getInt", int.class));
		convertFromSqlMethod.put("MEDIUMINT", ResultSet.class.getMethod("getInt", int.class));
		convertFromSqlMethod.put("INT", ResultSet.class.getMethod("getInt", int.class));
		convertFromSqlMethod.put("INTEGER", ResultSet.class.getMethod("getInt", int.class));
		convertFromSqlMethod.put("BIGINT", ResultSet.class.getMethod("getBigDecimal", int.class));
		
		convertFromSqlMethod.put("DATE", ResultSet.class.getMethod("getDate", int.class));
		convertFromSqlMethod.put("TIME", ResultSet.class.getMethod("getDate", int.class));
		convertFromSqlMethod.put("DATETIME", ResultSet.class.getMethod("getTimestamp", int.class));
		convertFromSqlMethod.put("TIMESTAMP", ResultSet.class.getMethod("getTimestamp", int.class));
		
		convertFromSqlMethod.put("BIT", ResultSet.class.getMethod("getBoolean", int.class));
		
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}
	
	public static Object convertObject(ResultSet rs, int i, String typename, int precision) throws SQLException {
		try {
			if(typename.toLowerCase().endsWith("unsigned")) {
				typename = typename.substring(0, typename.length()-"unsigned".length()).trim();
			}
			
			Object obj =  convertFromSqlMethod.get(typename).invoke(rs, i);

			return obj;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Object convertStringTo(String value, Class<?> type) {
		
		Object ret = null;
		try {
			if(type == String.class) {
				ret = value;
			} else if(type == Double.class) {
				ret = Double.parseDouble(value);
			} else if(type == Integer.class) {
				ret = Integer.parseInt(value);
			} else if(type == Float.class) {
				ret = Float.parseFloat(value);
			} else if(type == Date.class) {
				ret = Date.valueOf(value);
			} else if(type == BigDecimal.class) {
				ret = new BigDecimal(value);
			}
		} catch (Exception e) {
			return null;
		}
		
		return ret;
	}
	
}
