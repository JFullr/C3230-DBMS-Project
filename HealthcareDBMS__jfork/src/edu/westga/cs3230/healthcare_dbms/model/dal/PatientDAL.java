package edu.westga.cs3230.healthcare_dbms.model.dal;

import java.sql.SQLException;

import edu.westga.cs3230.healthcare_dbms.io.database.HealthcareDatabaseClient;
import edu.westga.cs3230.healthcare_dbms.io.database.QueryResult;
import edu.westga.cs3230.healthcare_dbms.model.PatientData;

public class PatientDAL {
	
	private PostDAL postDal;
	private PersonDAL personDal;
	private ConnectionDAL connectionDal;

	public PatientDAL(ConnectionDAL connectionDal) {
		this.connectionDal = connectionDal;
		this.postDal = new PostDAL(connectionDal);
		this.personDal = new PersonDAL(connectionDal);
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
