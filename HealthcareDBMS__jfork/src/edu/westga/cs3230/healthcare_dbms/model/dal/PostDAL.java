package edu.westga.cs3230.healthcare_dbms.model.dal;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.westga.cs3230.healthcare_dbms.io.database.DatabaseConnector;
import edu.westga.cs3230.healthcare_dbms.sql.SqlAttribute;
import edu.westga.cs3230.healthcare_dbms.sql.SqlGenerated;
import edu.westga.cs3230.healthcare_dbms.sql.SqlGetter;
import edu.westga.cs3230.healthcare_dbms.sql.SqlManager;
import edu.westga.cs3230.healthcare_dbms.sql.SqlTuple;

/**
 * A helper class to add entries into the DB.
 *
 * @author Joseph Fuller and Andrew Steinborn
 */
public class PostDAL {

	/** The connector. */
	private DatabaseConnector connector;
	
	/**
	 * Instantiates a new post DAL.
	 *
	 * @param connector the connector
	 */
	public PostDAL(DatabaseConnector connector) {
		this.connector = connector;
	}
	
	/**
	 * Post tuple.
	 *
	 * @param obj the obj
	 * @return the array list
	 * @throws SQLException the SQL exception
	 */
	public ArrayList<SqlTuple> postTuple(Object obj) throws SQLException {
		
		SqlTuple tuple = SqlGetter.getFrom(obj);
		ArrayList<String> useAttributes = this.usingAttributes(obj);
		String query = this.buildQueryFrom(obj, tuple, useAttributes);
		
		SqlManager manager = new SqlManager();
		Connection con = this.connector.getCurrentConnection();
		try (PreparedStatement stmt = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
			
			int j = 1;
			for(String attr : useAttributes) {
				stmt.setObject(j, tuple.get(attr).getValue());
				j++;
			}
			
			stmt.executeUpdate();
			manager.readTuples(stmt.getGeneratedKeys());
		}
		return manager.getTuples();
	}
	
	/**
	 * Gets the generated ids.
	 *
	 * @param postResult the post result
	 * @return the generated ids
	 */
	public ArrayList<BigDecimal> getGeneratedIds(ArrayList<SqlTuple> postResult){
		
		ArrayList<BigDecimal> values = new ArrayList<BigDecimal>();
		for(SqlTuple tup : postResult) {
			SqlAttribute attr = tup.get("GENERATED_KEY");
			if(attr != null) {
				values.add((BigDecimal)attr.getValue());
			}
		}
	
		return values;
	}
	
	/**
	 * Builds the query from.
	 *
	 * @param obj the obj
	 * @param tuple the tuple
	 * @param useAttributes the use attributes
	 * @return the string
	 */
	private String buildQueryFrom(Object obj, SqlTuple tuple, ArrayList<String> useAttributes) {
		
		int attributeCount = useAttributes.size();
		
		StringBuilder query = new StringBuilder("INSERT INTO ");
		query.append(obj.getClass().getSimpleName());
		query.append("( ");
		int i = 0;
		for(String attr : useAttributes) {
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
	
	/**
	 * Using attributes.
	 *
	 * @param data the data
	 * @return the array list
	 * @throws SQLException the SQL exception
	 */
	private ArrayList<String> usingAttributes(Object data) throws SQLException{
		
		ArrayList<String> useAttributes = new ArrayList<String>();
		
		SqlTuple tup = SqlGetter.getFrom(data);
		
		for(SqlAttribute attr : tup) {
			try {
				if(data.getClass().getField(attr.getAttribute())
						.getDeclaredAnnotation(SqlGenerated.class) == null) {
					useAttributes.add(attr.getAttribute());
				}else {
					System.out.println("GENERATED VALUE HIT");
				}
			}catch(Exception e) {
				useAttributes.add(attr.getAttribute());
			}
		}
		
		return useAttributes;
	}

	
}
