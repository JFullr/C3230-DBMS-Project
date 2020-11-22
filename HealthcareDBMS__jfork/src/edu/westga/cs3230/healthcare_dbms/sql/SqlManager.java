package edu.westga.cs3230.healthcare_dbms.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class SqlManager {
	
	private ArrayList<SqlTuple> tuples;
	
	public SqlManager() {
		this.tuples = new ArrayList<SqlTuple>();
	}
	
	public ArrayList<SqlTuple> getTuples(){
		return this.tuples;
	}
	
	public void readTuples(String connectionString, String query) throws SQLException {
		
		try (Connection con = DriverManager.getConnection(connectionString);
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {
			this.readTuples(rs);
		}
		
	}
	
	public void readTuples(ResultSet rs) throws SQLException {
		
		while (rs.next()) {
			HashMap<String, SqlAttribute> attributes = new LinkedHashMap<String, SqlAttribute>();
			ResultSetMetaData meta = rs.getMetaData();
			
			for(int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
				String labelName = meta.getColumnName(i);
				String typeName = meta.getColumnTypeName(i);
				//SQL_TYPE type = SqlTypeConverter.convertFrom(typename);
				
				Object obj = SqlTypeConverter.convertObject(rs, i, typeName, meta.getPrecision(i));
				//if(type == SQL_TYPE.STRING && meta.getPrecision(i) == 1) {
				
				
				attributes.put(labelName, new SqlAttribute(labelName, obj));
			}
			
			this.tuples.add(new SqlTuple(attributes));
		}
		
	}
	
	public void setTuple(ResultSet rs) throws SQLException {
		
		while (rs.next()) {
			HashMap<String, SqlAttribute> attributes = new LinkedHashMap<String, SqlAttribute>();
			ResultSetMetaData meta = rs.getMetaData();
			
			for(int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
				String labelName = meta.getColumnName(i);
				String typeName = meta.getColumnTypeName(i);
				//SQL_TYPE type = SqlTypeConverter.convertFrom(typename);
				
				Object obj = SqlTypeConverter.convertObject(rs, i, typeName, meta.getPrecision(i));
				//if(type == SQL_TYPE.STRING && meta.getPrecision(i) == 1) {
				
				attributes.put(labelName, new SqlAttribute(labelName, obj));
			}
			
			this.tuples.add(new SqlTuple(attributes));
		}
		
	}
	
}
