package edu.westga.cs3230.healthcare_dbms.model.dal;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.westga.cs3230.healthcare_dbms.io.database.QueryResult;
import edu.westga.cs3230.healthcare_dbms.model.Address;
import edu.westga.cs3230.healthcare_dbms.model.Patient;
import edu.westga.cs3230.healthcare_dbms.model.Person;
import edu.westga.cs3230.healthcare_dbms.sql.SqlAttribute;
import edu.westga.cs3230.healthcare_dbms.sql.SqlGetter;
import edu.westga.cs3230.healthcare_dbms.sql.SqlManager;
import edu.westga.cs3230.healthcare_dbms.sql.SqlTuple;

public class PersonDAL {
	
	private PostDAL postDal;
	private UpdateDAL updateDal;
	private String dbUrl;

	public PersonDAL(String dbUrl) {
		this.dbUrl = dbUrl;
		this.postDal = new PostDAL(dbUrl);
		this.updateDal = new UpdateDAL(dbUrl);
	}
	
	public QueryResult attemptAddPerson(Person patient) throws SQLException {
		
		///TODO use transactions
		
		QueryResult result = this.getPersonBySSN(patient);
		
		if(result == null || result.getTuple() == null) {
			ArrayList<SqlTuple> current = this.postDal.postTuple(patient);
			Integer id = null;
			if(current == null || current.size() == 0) {
				//TODO DELETE
				//return result;
			}
			SqlAttribute attr = current.get(0).get("GENERATED_KEY");
			if(attr != null) {
				id = ((BigDecimal)attr.getValue()).intValue();
				this.postDal.postTuple(new Patient(id));
			} else {
				//TODO DELETE
			}
			
		}
		
		return result;
	}
	
	public QueryResult getPersonBySSN(Person person) throws SQLException {
		String prepared = "select * "
						+ "from Person "
						+ "where ssn = ? "
						+ "LIMIT 2";
		
		SqlManager manager = new SqlManager();
		try (Connection con = DriverManager.getConnection(this.dbUrl);
				PreparedStatement stmt = con.prepareStatement(prepared);
				) {
			stmt.setObject(1, person.getSSN());
			//System.out.println(stmt);
			ResultSet rs = stmt.executeQuery();
			manager.readTuples(rs);
		}
		
		return new QueryResult(manager.getTuples());
	}

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
		
		//System.out.println(query);

		SqlManager manager = new SqlManager();
		try (Connection con = DriverManager.getConnection(this.dbUrl);
			 PreparedStatement stmt = con.prepareStatement(query.toString());
		) {
			int j = 1;
			for(SqlAttribute attr : tuple) {
				if (attr.getValue() == null) {
					continue;
				}
				stmt.setObject(j, attr.getValue());
				j++;
			}
			//System.out.println(stmt);
			ResultSet rs = stmt.executeQuery();
			manager.readTuples(rs);
		}

		return new QueryResult(manager.getTuples());
	}

	public QueryResult attemptUpdatePerson(Person previous, Person newValues) throws SQLException {
		if (previous.getPerson_id() == null) {
			throw new SQLException("Need a person to update.");
		}

		return updateDal.updateTuple(newValues, new SqlAttribute("person_id", previous.getPerson_id()));
	}

}
