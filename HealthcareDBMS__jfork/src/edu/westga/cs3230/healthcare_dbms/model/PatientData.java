package edu.westga.cs3230.healthcare_dbms.model;

import edu.westga.cs3230.healthcare_dbms.sql.AssociatedHider;

import java.util.function.Predicate;

/**
 * Couples a {@link Person} with {@link Address}.
 *
 * @author Joseph Fuller and Andrew Steinborn
 */
public class PatientData implements AssociatedHider {
	
	/** The person. */
	private Person person;
	
	/** The address. */
	private Address address;
	
	/**
	 * Instantiates a new patient data.
	 *
	 * @param person the person
	 * @param first the first
	 */
	public PatientData(Person person, Address first) {
		this.assign(person, first);
	}
	
	/**
	 * Assign.
	 *
	 * @param person the person
	 * @param address the address
	 */
	private void assign(Person person, Address address) {
		this.person = person;
		this.address = address;
	}

	/**
	 * Gets the person.
	 *
	 * @return the person
	 */
	public Person getPerson() {
		return person;
	}

	/**
	 * Gets the address.
	 *
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * Hide function.
	 *
	 * @return the predicate
	 */
	@Override
	public Predicate<String> hideFunction() {
		return key -> key.endsWith("_id");
	}
}
