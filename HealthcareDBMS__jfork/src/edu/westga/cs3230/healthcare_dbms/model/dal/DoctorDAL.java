package edu.westga.cs3230.healthcare_dbms.model.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import edu.westga.cs3230.healthcare_dbms.io.database.QueryResult;
import edu.westga.cs3230.healthcare_dbms.model.Address;
import edu.westga.cs3230.healthcare_dbms.model.Doctor;
import edu.westga.cs3230.healthcare_dbms.model.PatientData;
import edu.westga.cs3230.healthcare_dbms.model.Person;
import edu.westga.cs3230.healthcare_dbms.sql.SqlManager;
import edu.westga.cs3230.healthcare_dbms.sql.SqlSetter;

public class DoctorDAL {
	
	private String dbUrl;

	public DoctorDAL(String dbUrl) {
		this.dbUrl = dbUrl;
	}
	
	public QueryResult getDoctors() throws SQLException {
		String query = "select p.*  "
						+ "from Person p, Doctor d "
						+ "where p.person_id = d.person_id";
		
		SqlManager manager = new SqlManager();
		try (Connection con = DriverManager.getConnection(this.dbUrl);
				Statement stmt = con.createStatement()
				) {
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
