package edu.westga.cs3230.healthcare_dbms.model.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import edu.westga.cs3230.healthcare_dbms.io.database.DatabaseConnector;
import edu.westga.cs3230.healthcare_dbms.io.database.QueryResult;
import edu.westga.cs3230.healthcare_dbms.sql.SqlAttribute;
import edu.westga.cs3230.healthcare_dbms.sql.SqlGetter;
import edu.westga.cs3230.healthcare_dbms.sql.SqlManager;
import edu.westga.cs3230.healthcare_dbms.sql.SqlTuple;

/**
 * A helper class to allow updating records in a DB given part of a person. You can
 * think of this and {@link PostDAL} as a pseudo-ORM.
 *
 * @author Joseph Fuller and Andrew Steinborn
 */
public class UpdateDAL {
	
	/** The connector. */
	private DatabaseConnector connector;
	
	/**
	 * Instantiates a new update DAL.
	 *
	 * @param connector the connector
	 */
	public UpdateDAL(DatabaseConnector connector) {
		this.connector = connector;
	}
	
	/**
	 * Updates the tuple in the DB matching the {@code oldValues} with the {@code newValues}.
	 *
	 * @param newValues the new values
	 * @param oldValues the old values
	 * @return the query result
	 * @throws SQLException the SQL exception
	 */
	public QueryResult updateTuple(Object newValues, Object oldValues) throws SQLException {
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
		Connection con = this.connector.getCurrentConnection();
		try (PreparedStatement stmt = con.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS)) {
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

			stmt.executeUpdate();
			manager.readTuples(stmt.getGeneratedKeys());
		}

		return new QueryResult(manager.getTuples());
	}

	
}
