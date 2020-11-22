package edu.westga.cs3230.healthcare_dbms.model.dal;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.westga.cs3230.healthcare_dbms.io.database.DatabaseConnector;
import edu.westga.cs3230.healthcare_dbms.io.database.QueryResult;
import edu.westga.cs3230.healthcare_dbms.model.Patient;
import edu.westga.cs3230.healthcare_dbms.model.Person;
import edu.westga.cs3230.healthcare_dbms.sql.SqlAttribute;
import edu.westga.cs3230.healthcare_dbms.sql.SqlGetter;
import edu.westga.cs3230.healthcare_dbms.sql.SqlManager;
import edu.westga.cs3230.healthcare_dbms.sql.SqlTuple;

/**
 * A DAL for fetching and manipulating the personal details of someone.
 *
 * @author Joseph Fuller and Andrew Steinborn
 */
public class PersonDAL {
	
	/** The post dal. */
	private PostDAL postDal;
	
	/** The update dal. */
	private UpdateDAL updateDal;
	
	/** The connector. */
	private DatabaseConnector connector;

	/**
	 * Instantiates a new person DAL.
	 *
	 * @param connector the connector
	 */
	public PersonDAL(DatabaseConnector connector) {
		this.connector = connector;
		this.postDal = new PostDAL(connector);
		this.updateDal = new UpdateDAL(connector);
	}
	
	/**
	 * Attempts to add a person.
	 *
	 * @param person the patient
	 * @return the query result
	 * @throws SQLException the SQL exception
	 */
	public QueryResult attemptAddPerson(Person person) throws SQLException {
		return this.connector.getInTransaction(() -> {
			QueryResult result = this.getPersonBySSN(person);

			if (result == null || result.getTuple() == null) {
				ArrayList<SqlTuple> current = this.postDal.postTuple(person);
				Integer id = null;
				if (current == null || current.size() == 0) {
					throw new SQLException("Failed, Delete Patient");
				}
				SqlAttribute attr = current.get(0).get("GENERATED_KEY");
				if (attr != null) {
					id = ((BigDecimal) attr.getValue()).intValue();
					this.postDal.postTuple(new Patient(id));
				} else {
					throw new SQLException("Failed, Delete Patient");
				}

			}

			return result;
		});
	}
	
	/**
	 * Gets the person by SSN.
	 *
	 * @param person the person
	 * @return the person by SSN
	 * @throws SQLException the SQL exception
	 */
	public QueryResult getPersonBySSN(Person person) throws SQLException {
		String prepared = "select * "
						+ "from Person "
						+ "where ssn = ? "
						+ "LIMIT 2";
		
		SqlManager manager = new SqlManager();
		Connection con = connector.getCurrentConnection();
		try (PreparedStatement stmt = con.prepareStatement(prepared)) {
			stmt.setObject(1, person.getSSN());
			ResultSet rs = stmt.executeQuery();
			manager.readTuples(rs);
		}
		
		return new QueryResult(manager.getTuples());
	}

	/**
	 * Gets the DB record matching the person object.
	 *
	 * @param person the person
	 * @return the person matching
	 * @throws SQLException the SQL exception
	 */
	public QueryResult getPersonMatching(Person person) throws SQLException {
		SqlTuple tuple = SqlGetter.getFrom(person);
		StringBuilder query = new StringBuilder("SELECT * FROM Person WHERE ");
		for (SqlAttribute attribute : tuple) {
			if (attribute.getValue() == null) {
				continue;
			}
			query.append(attribute.getAttribute()).append(" = ? AND ");
		}
		
		if(!query.toString().contains("?")) {
			return null;
		}
		query.setLength(query.lastIndexOf("?")+1);

		SqlManager manager = new SqlManager();
		Connection con = connector.getCurrentConnection();
		try (PreparedStatement stmt = con.prepareStatement(query.toString())) {
			int j = 1;
			for(SqlAttribute attr : tuple) {
				if (attr.getValue() == null) {
					continue;
				}
				stmt.setObject(j, attr.getValue());
				j++;
			}
			ResultSet rs = stmt.executeQuery();
			manager.readTuples(rs);
		}

		return new QueryResult(manager.getTuples());
	}

	/**
	 * Attempts to update the person in the DB.
	 *
	 * @param previous the previous
	 * @param newValues the new values
	 * @return the query result
	 * @throws SQLException the SQL exception
	 */
	public QueryResult attemptUpdatePerson(Person previous, Person newValues) throws SQLException {
		if (previous.getPerson_id() == null) {
			throw new SQLException("Need a person to update.");
		}

		return updateDal.updateTuple(newValues, new SqlAttribute("person_id", previous.getPerson_id()));
	}

}
