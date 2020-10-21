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
import edu.westga.cs3230.healthcare_dbms.model.PatientData;
import edu.westga.cs3230.healthcare_dbms.model.Person;
import edu.westga.cs3230.healthcare_dbms.sql.SqlAttribute;
import edu.westga.cs3230.healthcare_dbms.sql.SqlGetter;
import edu.westga.cs3230.healthcare_dbms.sql.SqlManager;
import edu.westga.cs3230.healthcare_dbms.sql.SqlSetter;
import edu.westga.cs3230.healthcare_dbms.sql.SqlTuple;

public class PatientDAL {
	
	private String dbUrl;
	private PostDAL postDal;
	private PersonDAL personDal;
	private AddressDAL addressDal;

	public PatientDAL(String dbUrl) {
		this.dbUrl = dbUrl;
		this.postDal = new PostDAL(dbUrl);
		this.personDal = new PersonDAL(dbUrl);
		this.addressDal = new AddressDAL(dbUrl);
	}
	
	public QueryResult attemptAddPatient(PatientData patient) throws SQLException {
		
		QueryResult result = null;
		Integer addressId = null;
		try {
			ArrayList<BigDecimal> generated = this.postDal.getGeneratedIds(this.postDal.postTuple(patient.getAddress()));
			if(generated.size() == 0) {
				System.out.println("ADDRESS FAILED GENERATED CHECK");
				return null;
			}
			addressId = generated.get(0).intValue();
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
		result = this.addressDal.getAddressById(addressId);
		
		if(result == null) {
			System.out.println("ADDRESS FAILED TO BE FOUND");
			return null;
		}
		
		patient.getPerson().setMailing_address_id(addressId);
		
		QueryResult person = this.personDal.attemptAddPerson(patient.getPerson());
		
		if(person == null) {
			System.out.println("PERSON FAILED TO ADD");
			//TODO delete person
			return null;
		}
		
		return result.combine(person);
	}
	
	public QueryResult attemptUpdatePatient(PatientData updateData, PatientData existingData) throws SQLException {
		//
		return null;
	}

	public QueryResult getPersonMatching(PatientData patient) throws SQLException {
		QueryResult qPerson = this.personDal.getPersonMatching(patient.getPerson());
		if (qPerson.getTuple().getAttributes().isEmpty()) {
			return qPerson;
		}
		Person person = new Person(null, null, null, null, null, null, null);
		SqlSetter.fillWith(person, qPerson.getTuple());
		//qPerson.setAssociated(person);
		
		QueryResult qAddress = this.addressDal.getAddressById(person.getMailing_address_id());
		Address address = new Address(null, null, null, null);
		SqlSetter.fillWith(address, qAddress.getTuple());
		qAddress.setAssociated(address);
		
		QueryResult combined = qAddress.combine(qPerson);
		PatientData data = new PatientData(person,address);
		combined.setAssociated(data);
		
		return combined;
	}

	public QueryResult getPatientBySSN(PatientData patientData) throws SQLException {
		// 
		return this.personDal.getPersonBySSN(patientData.getPerson());
	}

}
