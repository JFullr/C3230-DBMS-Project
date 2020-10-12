package query.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class SQLManager {
	
	private ArrayList<HashMap<String, SQLData>> tuples;
	
	public SQLManager() {
		this.tuples = new ArrayList<HashMap<String, SQLData>>();
	}
	
	public ArrayList<HashMap<String, SQLData>> getTuples(){
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
			HashMap<String, SQLData> attributes = new HashMap<String, SQLData>();
			ResultSetMetaData meta = rs.getMetaData();
			
			for(int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
				String labelName = meta.getColumnName(i);
				String typename = meta.getColumnTypeName(i);
				SQL_TYPE type = SqlTypeConverter.convertFrom(typename);
				
				Object obj = SqlTypeConverter.convertObject(rs, labelName, type);
				if(type == SQL_TYPE.STRING && meta.getPrecision(i) == 1) {
					obj = ((String)obj).charAt(0);
					type = SQL_TYPE.CHAR;
				}
				
				attributes.put(labelName, new SQLData(labelName, obj, type));
			}
			this.tuples.add(attributes);
		}
		
	}
	
}
