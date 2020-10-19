package edu.westga.cs3230.healthcare_dbms.model.dal;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.westga.cs3230.healthcare_dbms.io.database.QueryResult;
import edu.westga.cs3230.healthcare_dbms.model.Patient;
import edu.westga.cs3230.healthcare_dbms.model.Person;
import edu.westga.cs3230.healthcare_dbms.sql.SqlAttribute;
import edu.westga.cs3230.healthcare_dbms.sql.SqlGetter;
import edu.westga.cs3230.healthcare_dbms.sql.SqlManager;
import edu.westga.cs3230.healthcare_dbms.sql.SqlTuple;
import edu.westga.cs3230.healthcare_dbms.utils.EmptyUtil;

public class PersonDAL {
	
	private PostDAL postDal;
	private String dbUrl;

	public PersonDAL(String dbUrl) {
		this.dbUrl = dbUrl;
		this.postDal = new PostDAL(dbUrl);
	}
	
	public QueryResult attemptAddPatient(Person patient) throws SQLException {
		
		///TODO use transactions
		
		QueryResult result = this.getPersonBySSN(patient);
		
		if(result == null || result.getTuples().size() == 0) {
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
	
	private QueryResult getPersonBySSN(Person person) throws SQLException {
		String prepared = "select * "
						+ "from Person "
						+ "where ssn = ? "
						+ "LIMIT 2";
		
		SqlManager manager = new SqlManager();
		try (Connection con = DriverManager.getConnection(this.dbUrl);
				PreparedStatement stmt = con.prepareStatement(prepared);
				) {
			stmt.setString(1, ""+person.getSSN());
			ResultSet rs = stmt.executeQuery();
			manager.readTuples(rs);
		}
		
		return new QueryResult(manager.getTuples());
	}

	public QueryResult getPersonMatching(Person person) throws SQLException {
		SqlTuple tuple = SqlGetter.getFrom(person);
		StringBuilder query = new StringBuilder("SELECT * FROM Person WHERE ");
		for (SqlAttribute attribute : tuple) {
			if (EmptyUtil.isEmpty(attribute.getValue())) {
				continue;
			}
			query.append(attribute.getAttribute()).append(" = ?, ");
		}
		// remove the trailing comma at the end
		query.setLength(query.length() - 2);

		SqlManager manager = new SqlManager();
		try (Connection con = DriverManager.getConnection(this.dbUrl);
			 PreparedStatement stmt = con.prepareStatement(query.toString());
		) {
			int j = 1;
			for(SqlAttribute attr : tuple) {
				if (EmptyUtil.isEmpty(attr.getValue())) {
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

}
