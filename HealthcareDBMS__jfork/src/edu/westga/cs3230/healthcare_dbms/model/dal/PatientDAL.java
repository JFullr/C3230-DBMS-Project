package edu.westga.cs3230.healthcare_dbms.model.dal;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.westga.cs3230.healthcare_dbms.io.database.DatabaseConnector;
import edu.westga.cs3230.healthcare_dbms.io.database.QueryResult;
import edu.westga.cs3230.healthcare_dbms.model.Address;
import edu.westga.cs3230.healthcare_dbms.model.AppointmentData;
import edu.westga.cs3230.healthcare_dbms.model.Patient;
import edu.westga.cs3230.healthcare_dbms.model.PatientData;
import edu.westga.cs3230.healthcare_dbms.model.Person;
import edu.westga.cs3230.healthcare_dbms.sql.SqlAttribute;
import edu.westga.cs3230.healthcare_dbms.sql.SqlGetter;
import edu.westga.cs3230.healthcare_dbms.sql.SqlManager;
import edu.westga.cs3230.healthcare_dbms.sql.SqlSetter;
import edu.westga.cs3230.healthcare_dbms.sql.SqlTuple;

public class PatientDAL {

	private PostDAL postDal;
	private PersonDAL personDal;
	private AddressDAL addressDal;
	private UpdateDAL updateDal;
	private DatabaseConnector connector;

	public PatientDAL(DatabaseConnector connector) {
		this.connector = connector;
		this.postDal = new PostDAL(connector);
		this.personDal = new PersonDAL(connector);
		this.addressDal = new AddressDAL(connector);
		this.updateDal = new UpdateDAL(connector);
	}
	
	public QueryResult attemptAddPatient(PatientData patient) throws SQLException {
		return connector.getInTransaction(() -> {

			QueryResult result = null;
			Integer addressId = null;
			try {
				ArrayList<BigDecimal> generated = this.postDal.getGeneratedIds(this.postDal.postTuple(patient.getAddress()));
				if (generated.size() == 0) {
					System.out.println("ADDRESS FAILED GENERATED CHECK");
					return null;
				}
				addressId = generated.get(0).intValue();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}

			result = this.addressDal.getAddressById(addressId);

			if (result == null) {
				System.out.println("ADDRESS FAILED TO BE FOUND");
				return null;
			}

			patient.getPerson().setMailing_address_id(addressId);

			QueryResult person = this.personDal.attemptAddPerson(patient.getPerson());

			if (person == null) {
				System.out.println("PERSON FAILED TO ADD");
				//TODO delete person
				return null;
			}

			return result.combine(person);
		});
	}
	
	public QueryResult attemptUpdatePatient(PatientData updateData, PatientData existingData) throws SQLException {
		QueryResult pers = this.updateDal.updateTuple(updateData.getPerson(), existingData.getPerson());
		QueryResult addr = this.updateDal.updateTuple(updateData.getAddress(), existingData.getAddress());
		return pers.combineMerge(addr);
	}

	public QueryResult getPersonMatching(PatientData patient) throws SQLException {
		
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

	public QueryResult getPatientBySSN(PatientData patientData) throws SQLException {
		// 
		//return this.personDal.getPersonBySSN(patientData.getPerson());
		return this.getPersonMatching(patientData);
	}

}
