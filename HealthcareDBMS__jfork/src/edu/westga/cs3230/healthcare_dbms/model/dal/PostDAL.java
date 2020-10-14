package edu.westga.cs3230.healthcare_dbms.model.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import edu.westga.cs3230.healthcare_dbms.sql.SqlAttribute;
import edu.westga.cs3230.healthcare_dbms.sql.SqlGetter;
import edu.westga.cs3230.healthcare_dbms.sql.SqlTuple;

public class PostDAL {
	
	private String dbUrl;
	
	public PostDAL(String dbUrl) {
		this.dbUrl = dbUrl;
	}
	
	public void postTuple(Object obj) throws SQLException {
		
		SqlTuple tuple = SqlGetter.getFrom(obj);
		
		/*
		String table = obj.getClass().getSimpleName();
		ArrayList<String> attrOrder = new ArrayList<String>();
		
		try (Connection con = DriverManager.getConnection(this.dbUrl)){
			DatabaseMetaData meta = con.getMetaData();
			ResultSet rs = meta.getTables(null, null, table, null);
			
			ResultSetMetaData tableMeta = rs.getMetaData();
			for(int i = 1; i <= tableMeta.getColumnCount(); i++) {
				String labelName = tableMeta.getColumnName(i);
				attrOrder.add(labelName);
			}
			
			rs.close();
		}
		
		StringBuilder query = new StringBuilder("INSERT INTO TABLE ");
		for(int i = 0; i < attrOrder.size(); i++) {
			query.append("? ");
		}
		query.append("?;");
		//*/
		
		int attributeCount = tuple.getAttributes().size();
		
		StringBuilder query = new StringBuilder("INSERT INTO TABLE ");
		query.append(obj.getClass().getSimpleName());
		query.append("( ");
		int i = 0;
		for(SqlAttribute attr : tuple) {
			query.append(attr.getAttribute());
			if(i < attributeCount-1) {
				query.append(", ");
			}
		}
		query.append(") VALUES (");
		
		for(int j = 0; j < attributeCount; j++) {
			query.append("? ");
			if(j < attributeCount-1) {
				query.append(", ");
			}
		}
		query.append(");");
		
		try (Connection con = DriverManager.getConnection(this.dbUrl);
				PreparedStatement stmt = con.prepareStatement(query.toString());
				) {
			
			int j = 0;
			for(SqlAttribute attr : tuple) {
				stmt.setObject(j, attr.getValue());
				j++;
			}
			int resultOperated = stmt.executeUpdate();
			
		}
		
	}

	
}
