package edu.westga.cs3230.healthcare_dbms.model.dal;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.westga.cs3230.healthcare_dbms.io.database.HealthcareDatabase;
import edu.westga.cs3230.healthcare_dbms.io.database.HealthcareDatabaseClient;
import edu.westga.cs3230.healthcare_dbms.io.database.QueryResult;
import edu.westga.cs3230.healthcare_dbms.model.Patient;
import edu.westga.cs3230.healthcare_dbms.model.PatientData;
import edu.westga.cs3230.healthcare_dbms.model.Person;
import edu.westga.cs3230.healthcare_dbms.sql.SqlAttribute;
import edu.westga.cs3230.healthcare_dbms.sql.SqlGetter;
import edu.westga.cs3230.healthcare_dbms.sql.SqlManager;
import edu.westga.cs3230.healthcare_dbms.sql.SqlTuple;

public class PatientDAL {
	
	private PostDAL postDal;
	private PersonDAL personDal;
	private HealthcareDatabaseClient client;

	public PatientDAL(HealthcareDatabaseClient client) {
		this.client = client;
		this.postDal = new PostDAL(client);
		this.personDal = new PersonDAL(client);
	}
	
	public QueryResult attemptAddPatient(PatientData patient) throws SQLException {
		//
		return this.personDal.attemptAddPerson(patient.getPerson());
	}
	
	public QueryResult attemptUpdatePatient(PatientData updateData, PatientData existingData) throws SQLException {
		//
		return null;
	}

	public QueryResult getPersonMatching(PatientData patient) throws SQLException {
		//
		return this.personDal.getPersonMatching(patient.getPerson());
	}

	public QueryResult getPersonBySSN(PatientData patientData) throws SQLException {
		// 
		return this.personDal.getPersonBySSN(patientData.getPerson());
	}

}
