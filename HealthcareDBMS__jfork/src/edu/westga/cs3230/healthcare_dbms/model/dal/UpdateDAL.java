package edu.westga.cs3230.healthcare_dbms.model.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import edu.westga.cs3230.healthcare_dbms.io.database.QueryResult;
import edu.westga.cs3230.healthcare_dbms.model.Person;
import edu.westga.cs3230.healthcare_dbms.sql.SqlAttribute;
import edu.westga.cs3230.healthcare_dbms.sql.SqlGetter;
import edu.westga.cs3230.healthcare_dbms.sql.SqlManager;
import edu.westga.cs3230.healthcare_dbms.sql.SqlTuple;

public class UpdateDAL {
	
	private String dbUrl;
	
	public UpdateDAL(String dbUrl) {
		this.dbUrl = dbUrl;
	}
	
	public QueryResult updateTuple(Object newValues, Object oldValues) throws SQLException {
		return updateTuple(newValues, oldValues, null);
	}
	
	public QueryResult updateTuple(Object newValues, Object oldValues, SqlTuple selection) throws SQLException {
		SqlTuple oldTuple = SqlGetter.getFrom(oldValues);
		SqlTuple newTuple = SqlGetter.getFrom(newValues);
		StringBuilder query = new StringBuilder("UPDATE "+newValues.getClass().getSimpleName()+" SET ");
		for (SqlAttribute attribute : newTuple) {
			if (attribute.getValue() == null) {
				continue;
			}
			query.append(attribute.getAttribute()).append(" = ? , ");
		}
		
		if(!query.toString().contains(",")) {
			return null;
		}
		query.setLength(query.lastIndexOf(","));
		
		///TODO create WHERE discriminator from selection values
		query.append(" WHERE ");
		for (SqlAttribute attribute : oldTuple) {
			if (attribute.getValue() == null) {
				continue;
			}
			query.append(attribute.getAttribute()).append(" = ? AND ");
		}
		
		if(!query.toString().contains("AND")) {
			return null;
		}
		query.setLength(query.lastIndexOf("AND"));

		SqlManager manager = new SqlManager();
		try (Connection con = DriverManager.getConnection(this.dbUrl);
			 PreparedStatement stmt = con.prepareStatement(query.toString());
		) {
			int j = 1;
			for(SqlAttribute attr : newTuple) {
				if (attr.getValue() == null) {
					continue;
				}
				stmt.setObject(j, attr.getValue());
				j++;
			}
			
			for(SqlAttribute attr : oldTuple) {
				if (attr.getValue() == null) {
					continue;
				}
				stmt.setObject(j, attr.getValue());
				j++;
			}
			System.out.println(stmt);
			stmt.executeUpdate();
			//ResultSet rs = stmt.executeQuery();
			//manager.readTuples(rs);
		}

		return new QueryResult(manager.getTuples());
	}

	
}
