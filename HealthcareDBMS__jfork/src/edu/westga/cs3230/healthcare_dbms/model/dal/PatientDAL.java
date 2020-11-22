package edu.westga.cs3230.healthcare_dbms.model.dal;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.westga.cs3230.healthcare_dbms.io.database.DatabaseConnector;
import edu.westga.cs3230.healthcare_dbms.io.database.QueryResult;
import edu.westga.cs3230.healthcare_dbms.model.Address;
import edu.westga.cs3230.healthcare_dbms.model.PatientData;
import edu.westga.cs3230.healthcare_dbms.model.Person;
import edu.westga.cs3230.healthcare_dbms.sql.SqlSetter;

/**
 * A DAL for fetching and manipulating patient data.
 *
 * @author Joseph Fuller and Andrew Steinborn
 */
public class PatientDAL {

	/** The post dal. */
	private PostDAL postDal;
	
	/** The person dal. */
	private PersonDAL personDal;
	
	/** The address dal. */
	private AddressDAL addressDal;
	
	/** The update dal. */
	private UpdateDAL updateDal;
	
	/** The connector. */
	private DatabaseConnector connector;

	/**
	 * Instantiates a new patient DAL.
	 *
	 * @param connector the connector
	 */
	public PatientDAL(DatabaseConnector connector) {
		this.connector = connector;
		this.postDal = new PostDAL(connector);
		this.personDal = new PersonDAL(connector);
		this.addressDal = new AddressDAL(connector);
		this.updateDal = new UpdateDAL(connector);
	}
	
	/**
	 * Attempts to add a patient to the database.
	 *
	 * @param patient the patient
	 * @return the query result
	 * @throws SQLException the SQL exception
	 */
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
				return null;
			}

			return result.combine(person);
		});
	}
	
	/**
	 * Attempts to update a patient in the DB.
	 *
	 * @param updateData the update data
	 * @param existingData the existing data
	 * @return the query result
	 * @throws SQLException the SQL exception
	 */
	public QueryResult attemptUpdatePatient(PatientData updateData, PatientData existingData) throws SQLException {
		QueryResult pers = this.updateDal.updateTuple(updateData.getPerson(), existingData.getPerson());
		QueryResult addr = this.updateDal.updateTuple(updateData.getAddress(), existingData.getAddress());
		return pers.combineMerge(addr);
	}

	/**
	 * Gets the DB record matching the given patient data.
	 *
	 * @param patient the patient data to match
	 * @return the person matching
	 * @throws SQLException the SQL exception
	 */
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

	/**
	 * Gets the patient by SSN.
	 *
	 * @param patientData the patient data
	 * @return the patient by SSN
	 * @throws SQLException the SQL exception
	 */
	public QueryResult getPatientBySSN(PatientData patientData) throws SQLException {
		return this.getPersonMatching(patientData);
	}

}
