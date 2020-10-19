package edu.westga.cs3230.healthcare_dbms.model;

public class Address {
    private Integer address_id;
    private String street_address_1;
    private String street_address_2;
    private String city;
    private String state;
    private Integer zip_code;

    public Address(String street_address_1, String street_address_2, String city, String state, String zip_code) {
        this.address_id = -1;
        this.street_address_1 = street_address_1;
        this.street_address_2 = street_address_2;
        this.city = city;
        this.state = state;
        this.zip_code = zip_code != null && zip_code.isEmpty() ? Integer.parseInt(zip_code) : null;
    }

    public Address(Integer address_id, String street_address_1, String street_address_2, String city, String state, Integer zip_code) {
        this.address_id = address_id;
        this.street_address_1 = street_address_1;
        this.street_address_2 = street_address_2;
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

    public String getStreet_address_1() {
        return street_address_1;
    }

    public void setStreet_address_1(String street_address_1) {
        this.street_address_1 = street_address_1;
    }

    public String getStreet_address_2() {
        return street_address_2;
    }

    public void setStreet_address_2(String street_address_2) {
        this.street_address_2 = street_address_2;
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
