package edu.westga.cs3230.healthcare_dbms.model.dal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import edu.westga.cs3230.healthcare_dbms.io.database.DatabaseConnector;
import edu.westga.cs3230.healthcare_dbms.io.database.QueryResult;
import edu.westga.cs3230.healthcare_dbms.sql.SqlManager;

public class DoctorDAL {
	
	private DatabaseConnector connector;

	public DoctorDAL(DatabaseConnector connector) {
		this.connector = connector;
	}
	
	public QueryResult getDoctors() throws SQLException {
		String query = "select p.*  "
						+ "from Person p, Doctor d "
						+ "where p.person_id = d.person_id";
		
		SqlManager manager = new SqlManager();
		Connection con = this.connector.getCurrentConnection();
		try (Statement stmt = con.createStatement()) {
			ResultSet rs = stmt.executeQuery(query);
			manager.readTuples(rs);
		}
		
		return new QueryResult(manager.getTuples());
	}
	
	/*
	public QueryResult getDoctorsMatching(Doctor doctor) throws SQLException {
		
		QueryResult people = this.personDal.getPersonMatching(patient.getPerson());
		
		QueryResult combined = null;
		for(QueryResult qPerson : people.getBatch()) {
			
			Person person = new Person(null, null, null, null, null, null, null, null);
			
			if(qPerson.getTuple() == null) {
				return null;
			}
			
			SqlSetter.fillWith(person, qPerson.getTuple());
			
			QueryResult qAddress = this.addressDal.getAddressById(person.getMailing_address_id());
			Address address = new Address(null, null, null, null);
			SqlSetter.fillWith(address, qAddress.getTuple());
			qAddress.setAssociated(address);
			
			QueryResult midCombine = qAddress.combineMerge(qPerson);
			PatientData data = new PatientData(person,address);
			midCombine.setAssociated(data);
			
			if(combined == null) {
				combined = midCombine;
			} else {
				combined.combine(midCombine);
			}
		}
		
		return combined;
	}
	//*/

}
