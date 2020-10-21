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
	
	public QueryResult updateTuple(Object values, SqlAttribute updateBy) throws SQLException {
		SqlTuple tuple = SqlGetter.getFrom(values);
		StringBuilder query = new StringBuilder("UPDATE "+values.getClass().getSimpleName()+" SET ");
		for (SqlAttribute attribute : tuple) {
			if (attribute.getValue() == null) {
				continue;
			}
			query.append(attribute.getAttribute()).append(" = ?, ");
		}
		
		if(!query.toString().contains("?")) {
			return null;
		}
		query.setLength(query.length() - 2);
		
		query.append(" WHERE ").append(updateBy.getAttribute()).append(" = ?");

		SqlManager manager = new SqlManager();
		try (Connection con = DriverManager.getConnection(this.dbUrl);
			 PreparedStatement stmt = con.prepareStatement(query.toString(), PreparedStatement.RETURN_GENERATED_KEYS);
		) {
			int j = 1;
			for(SqlAttribute attr : tuple) {
				if (attr.getValue() == null) {
					continue;
				}
				stmt.setObject(j, attr.getValue());
				j++;
			}
			stmt.setObject(j, updateBy.getValue());
			stmt.executeUpdate();
			manager.readTuples(stmt.getGeneratedKeys());
		}

		return new QueryResult(manager.getTuples());
	}

	
}
