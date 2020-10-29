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
import edu.westga.cs3230.healthcare_dbms.model.AppointmentData;
import edu.westga.cs3230.healthcare_dbms.model.Login;
import edu.westga.cs3230.healthcare_dbms.model.PatientData;
import edu.westga.cs3230.healthcare_dbms.model.Person;
import edu.westga.cs3230.healthcare_dbms.sql.SqlManager;
import edu.westga.cs3230.healthcare_dbms.sql.SqlSetter;

public class AppointmentDAL {

	private String dbUrl;

	public AppointmentDAL(String dbUrl) {
		this.dbUrl = dbUrl;
	}

	public QueryResult attemptAddAppointment(AppointmentData patient) throws SQLException {
		/*
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
		//*/
		return null;
	}

	public QueryResult getAppointmentMatching(AppointmentData appointmentData) throws SQLException{
		/*
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
		*/
		return null;
	}
	
}
