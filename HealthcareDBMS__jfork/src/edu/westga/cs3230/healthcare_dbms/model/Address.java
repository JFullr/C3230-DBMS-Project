package edu.westga.cs3230.healthcare_dbms.model;

import edu.westga.cs3230.healthcare_dbms.sql.SqlGenerated;

public class Address {
	
	@SqlGenerated
    private Integer address_id;
    private String street_address1;
    private String street_address2;
    private String city;
    private String state;
    private Integer zip_code;

    public Address(String street_address1, String city, String state, Integer zip_code) {
        this.address_id = null;
        this.street_address1 = street_address1;
        this.street_address2 = null;
        this.city = city;
        this.state = state;
        this.zip_code = zip_code;
    }

    public Address(String street_address_1, String street_address_2, String city, String state, Integer zip_code) {
        this.address_id = null;
    	this.street_address1 = street_address_1;
        this.street_address2 = street_address_2;
        this.city = city;
        this.state = state;
        this.zip_code = zip_code;
    }

    public Integer getAddress_id() {
        return address_id;
    }

    public void setAddress_id(Integer address_id) {
        this.address_id = address_id;
    }

    public String getStreet_address1() {
        return street_address1;
    }

    public void setStreet_address1(String street_address_1) {
        this.street_address1 = street_address_1;
    }

    public String getStreet_address2() {
        return street_address2;
    }

    public void setStreet_address2(String street_address_2) {
        this.street_address2 = street_address_2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getZip_code() {
        return zip_code;
    }

    public void setZip_code(Integer zip_code) {
        this.zip_code = zip_code;
    }
    
}
