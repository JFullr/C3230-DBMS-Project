package edu.westga.cs3230.healthcare_dbms.model;

import edu.westga.cs3230.healthcare_dbms.sql.SqlGenerated;

/**
 * Represents a street address.
 *
 * @author Joseph Fuller and Andrew Steinborn
 */
public class Address {
	
	/** The address id. */
	@SqlGenerated
    private Integer address_id;
    
    /** The street address 1. */
    private String street_address1;
    
    /** The street address 2. */
    private String street_address2;
    
    /** The city. */
    private String city;
    
    /** The state. */
    private String state;
    
    /** The zip code. */
    private Integer zip_code;

    /**
     * Instantiates a new address.
     *
     * @param street_address1 the street address 1
     * @param city the city
     * @param state the state
     * @param zip_code the zip code
     */
    public Address(String street_address1, String city, String state, Integer zip_code) {
        this.address_id = null;
        this.street_address1 = street_address1;
        this.street_address2 = null;
        this.city = city;
        this.state = state;
        this.zip_code = zip_code;
    }

    /**
     * Instantiates a new address.
     *
     * @param street_address_1 the street address 1
     * @param street_address_2 the street address 2
     * @param city the city
     * @param state the state
     * @param zip_code the zip code
     */
    public Address(String street_address_1, String street_address_2, String city, String state, Integer zip_code) {
        this.address_id = null;
    	this.street_address1 = street_address_1;
        this.street_address2 = street_address_2;
        this.city = city;
        this.state = state;
        this.zip_code = zip_code;
    }

    /**
     * Gets the address id.
     *
     * @return the address id
     */
    public Integer getAddress_id() {
        return address_id;
    }

    /**
     * Sets the address id.
     *
     * @param address_id the new address id
     */
    public void setAddress_id(Integer address_id) {
        this.address_id = address_id;
    }

    /**
     * Gets the street address 1.
     *
     * @return the street address 1
     */
    public String getStreet_address1() {
        return street_address1;
    }

    /**
     * Sets the street address 1.
     *
     * @param street_address_1 the new street address 1
     */
    public void setStreet_address1(String street_address_1) {
        this.street_address1 = street_address_1;
    }

    /**
     * Gets the street address 2.
     *
     * @return the street address 2
     */
    public String getStreet_address2() {
        return street_address2;
    }

    /**
     * Sets the street address 2.
     *
     * @param street_address_2 the new street address 2
     */
    public void setStreet_address2(String street_address_2) {
        this.street_address2 = street_address_2;
    }

    /**
     * Gets the city.
     *
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city.
     *
     * @param city the new city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets the state.
     *
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the state.
     *
     * @param state the new state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Gets the zip code.
     *
     * @return the zip code
     */
    public Integer getZip_code() {
        return zip_code;
    }

    /**
     * Sets the zip code.
     *
     * @param zip_code the new zip code
     */
    public void setZip_code(Integer zip_code) {
        this.zip_code = zip_code;
    }
    
}
