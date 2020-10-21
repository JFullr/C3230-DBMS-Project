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

public class PatientDAL {
	
	private PostDAL postDal;
	private PersonDAL personDal;
	private String dbUrl;

	public PatientDAL(String dbUrl) {
		this.dbUrl = dbUrl;
		this.postDal = new PostDAL(dbUrl);
		this.personDal = new PersonDAL(dbUrl);
	}
	
	public QueryResult attemptUpdatePatient(Person existing, Person patient) throws SQLException {
		
		
		
		return null;
	}

}
