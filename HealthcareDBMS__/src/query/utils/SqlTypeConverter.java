package query.utils;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class SqlTypeConverter {
	
	//https://dev.mysql.com/doc/connector-j/5.1/en/connector-j-reference-type-conversions.html
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
		externalConvert.put("INTEGER", SQL_TYPE.INT);
		externalConvert.put("BIGINT", SQL_TYPE.INT);
		
		externalConvert.put("DATE", SQL_TYPE.DATE);
		externalConvert.put("TIME", SQL_TYPE.DATE);
		externalConvert.put("DATETIME", SQL_TYPE.DATE);
		externalConvert.put("TIMESTAMP", SQL_TYPE.DATE);
		
	}
	
	public static SQL_TYPE convertFrom(String typeName) throws SQLException {
		return externalConvert.get(typeName);
	}
	
	public static Object convertObject(ResultSet rs, String label, SQL_TYPE type) throws SQLException {
		switch (type) {
		case ARRAY:
			return (Object) rs.getArray(label);
		case BOOLEAN:
			return (Boolean) rs.getBoolean(label);
		case CHAR:
			return (Byte) rs.getByte(label);
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
	}
	
}
