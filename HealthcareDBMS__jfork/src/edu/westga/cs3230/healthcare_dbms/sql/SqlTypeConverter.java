package edu.westga.cs3230.healthcare_dbms.sql;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class SqlTypeConverter {
	
	//https://dev.mysql.com/doc/connector-j/5.1/en/connector-j-reference-type-conversions.html
	/*
	private static HashMap<String, SQL_TYPE> externalConvert;
	static {
		externalConvert = new HashMap<String, SQL_TYPE>();
		externalConvert.put("CHAR", SQL_TYPE.STRING);
		externalConvert.put("VARCHAR", SQL_TYPE.STRING);
		externalConvert.put("BLOB", SQL_TYPE.STRING);
		externalConvert.put("TEXT", SQL_TYPE.STRING);
		externalConvert.put("ENUM", SQL_TYPE.STRING);
		externalConvert.put("SET", SQL_TYPE.STRING);
		
		externalConvert.put("FLOAT", SQL_TYPE.FLOAT);
		externalConvert.put("REAL", SQL_TYPE.DOUBLE);
		externalConvert.put("DOUBLE PRECISION", SQL_TYPE.DOUBLE);
		externalConvert.put("NUMERIC", SQL_TYPE.INT);
		externalConvert.put("DECIMAL", SQL_TYPE.DOUBLE);
		externalConvert.put("TINYINT", SQL_TYPE.INT);
		externalConvert.put("SMALLINT", SQL_TYPE.INT);
		externalConvert.put("MEDIUMINT", SQL_TYPE.INT);
		externalConvert.put("INT", SQL_TYPE.INT);
		externalConvert.put("INTEGER", SQL_TYPE.INT);
		externalConvert.put("BIGINT", SQL_TYPE.INT);
		
		externalConvert.put("DATE", SQL_TYPE.DATE);
		externalConvert.put("TIME", SQL_TYPE.DATE);
		externalConvert.put("DATETIME", SQL_TYPE.DATE);
		externalConvert.put("TIMESTAMP", SQL_TYPE.DATE);
		
	}
	//*/
	
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
	
	
	
	
	
	
	
	
	/*
	public static SQL_TYPE convertFrom(String typeName) throws SQLException {
		return externalConvert.get(typeName);
	}
	*/
	
	public static Object convertObject(ResultSet rs, String label, String typename) throws SQLException {
		/*
		switch (type) {
		case ARRAY:
			return (Object) rs.getArray(label);
		case BOOLEAN:
			return (Boolean) rs.getBoolean(label);
		case CHAR:
			return (Character) (rs.getString(label).charAt(0));
		case DATE:
			return (Date) rs.getDate(label);
		case DOUBLE:
			return (Double) rs.getDouble(label);
		case FLOAT:
			return (Float) rs.getFloat(label);
		case INT:
			return (Integer) rs.getInt(label);
		case STRING:
			return (String) rs.getString(label);
		default:
			return null;
		}
		*/
		try {
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
