package edu.westga.cs3230.healthcare_dbms.sql;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class SqlTypeConverter {
	
	//https://dev.mysql.com/doc/connector-j/5.1/en/connector-j-reference-type-conversions.html
	
	private static HashMap<String, Method> convertFromSqlMethod;
	static {
		try {
		convertFromSqlMethod = new HashMap<String, Method>();
		convertFromSqlMethod.put("CHAR", ResultSet.class.getMethod("getString", String.class));
		convertFromSqlMethod.put("VARCHAR", ResultSet.class.getMethod("getString", String.class));
		convertFromSqlMethod.put("BLOB", ResultSet.class.getMethod("getString", String.class));
		convertFromSqlMethod.put("TEXT", ResultSet.class.getMethod("getString", String.class));
		convertFromSqlMethod.put("ENUM", ResultSet.class.getMethod("getString", String.class));
		convertFromSqlMethod.put("SET", ResultSet.class.getMethod("getString", String.class));
		
		convertFromSqlMethod.put("FLOAT", ResultSet.class.getMethod("getFloat", String.class));
		convertFromSqlMethod.put("REAL", ResultSet.class.getMethod("getDouble", String.class));
		convertFromSqlMethod.put("DOUBLE PRECISION", ResultSet.class.getMethod("getDouble", String.class));
		convertFromSqlMethod.put("NUMERIC", ResultSet.class.getMethod("getInt", String.class));
		convertFromSqlMethod.put("DECIMAL", ResultSet.class.getMethod("getDouble", String.class));
		convertFromSqlMethod.put("TINYINT", ResultSet.class.getMethod("getInt", String.class));
		convertFromSqlMethod.put("SMALLINT", ResultSet.class.getMethod("getInt", String.class));
		convertFromSqlMethod.put("MEDIUMINT", ResultSet.class.getMethod("getInt", String.class));
		convertFromSqlMethod.put("INT", ResultSet.class.getMethod("getInt", String.class));
		convertFromSqlMethod.put("INTEGER", ResultSet.class.getMethod("getInt", String.class));
		convertFromSqlMethod.put("BIGINT", ResultSet.class.getMethod("getBigDecimal", String.class));
		
		convertFromSqlMethod.put("DATE", ResultSet.class.getMethod("getDate", String.class));
		convertFromSqlMethod.put("TIME", ResultSet.class.getMethod("getDate", String.class));
		convertFromSqlMethod.put("DATETIME", ResultSet.class.getMethod("getDate", String.class));
		convertFromSqlMethod.put("TIMESTAMP", ResultSet.class.getMethod("getDate", String.class));
		
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}
	
	public static Object convertObject(ResultSet rs, String label, String typename) throws SQLException {
		try {
			if(typename.toLowerCase().endsWith("unsigned")) {
				typename = typename.substring(0, typename.length()-"unsigned".length()).trim();
			}
			/*
			 * if(convertFromSqlMethod.get(typename) == null) {
				System.out.println(label+" :: "+typename);
				return null;
			}
			*/
			return convertFromSqlMethod.get(typename).invoke(rs, label);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
