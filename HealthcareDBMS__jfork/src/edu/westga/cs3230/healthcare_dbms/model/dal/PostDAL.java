package edu.westga.cs3230.healthcare_dbms.model.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import edu.westga.cs3230.healthcare_dbms.sql.SqlGetter;
import edu.westga.cs3230.healthcare_dbms.sql.SqlTuple;

public class PostDAL {
	
	private String dbUrl;
	
	public PostDAL(String dbUrl) {
		this.dbUrl = dbUrl;
	}
	
	public void postTuple(Object obj) throws SQLException {
		
		SqlTuple tuple = SqlGetter.getFrom(obj);
		ArrayList<String> useAttributes = this.usingAttributes(obj);
		String query = this.buildQueryFrom(obj, tuple, useAttributes);
		
		//System.out.println(query.toString());
		
		try (Connection con = DriverManager.getConnection(this.dbUrl);
				PreparedStatement stmt = con.prepareStatement(query.toString());
				) {
			
			int j = 1;
			for(String attr : useAttributes) {
				//System.out.println(attr.toString());
				stmt.setObject(j, tuple.get(attr).getValue());
				j++;
			}
			//System.out.println(stmt);
			int resultOperated = stmt.executeUpdate();
			
		}
		
	}
	
	private String buildQueryFrom(Object obj, SqlTuple tuple, ArrayList<String> useAttributes) {
		
		int attributeCount = useAttributes.size();
		
		StringBuilder query = new StringBuilder("INSERT INTO ");
		query.append(obj.getClass().getSimpleName());
		query.append("( ");
		int i = 0;
		for(String attr : useAttributes) {
			//System.out.println(attr.toString());
			query.append(tuple.get(attr).getAttribute());
			if(i < attributeCount-1) {
				query.append(", ");
			}
			i++;
		}
		query.append(") VALUES (");
		
		for(int j = 0; j < attributeCount; j++) {
			query.append("? ");
			if(j < attributeCount-1) {
				query.append(", ");
			}
		}
		query.append(");");
		
		return query.toString();
	}
	
	private ArrayList<String> usingAttributes(Object data) throws SQLException{
		
		
		ArrayList<String> useAttributes = new ArrayList<String>();
		String table = data.getClass().getSimpleName();
		try (Connection con = DriverManager.getConnection(this.dbUrl);
				Statement stmt = con.createStatement()){
			ResultSet rs = stmt.executeQuery("SELECT * from "+table+" LIMIT 1");
			ResultSetMetaData tableMeta = rs.getMetaData();
			for(int i = 1; i <= tableMeta.getColumnCount(); i++) {
				String labelName = tableMeta.getColumnName(i);
				if(!tableMeta.isAutoIncrement(i)) {
					useAttributes.add(labelName.toLowerCase());
				}
				
			}
			
			rs.close();
		}
		
		return useAttributes;
	}

	
}
