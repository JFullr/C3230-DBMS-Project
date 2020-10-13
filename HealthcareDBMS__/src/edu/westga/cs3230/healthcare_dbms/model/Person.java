package edu.westga.cs3230.healthcare_dbms.model;

import edu.westga.cs3230.healthcare_dbms.utils.reflect.DbName;

import java.util.Date;

public class Person {
    private int personId;
    private String firstName;
    private String lastName;
    private char middleInitial;
    private Date dateOfBirth;
    private String ssn;
    private String contactPhone;
    private String contactEmail;
    private String mailingAddress;

    public Person() {
    }

    public int getPersonId() {
        return personId;
    }

    @DbName("person_id")
    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getFirstName() {
        return firstName;
    }

    @DbName("fname")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @DbName("lname")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public char getMiddleInitial() {
        return middleInitial;
    }

    @DbName("middle_initial")
    public void setMiddleInitial(char middleInitial) {
        this.middleInitial = middleInitial;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    @DbName("DOB")
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getSsn() {
        return ssn;
    }

    @DbName("SSN")
    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    @DbName("contact_phone")
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    @DbName("contact_email")
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getMailingAddress() {
        return mailingAddress;
    }

    @DbName("mailing_address")
    public void setMailingAddress(String mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (personId != person.personId) return false;
        if (middleInitial != person.middleInitial) return false;
        if (firstName != null ? !firstName.equals(person.firstName) : person.firstName != null) return false;
        if (lastName != null ? !lastName.equals(person.lastName) : person.lastName != null) return false;
        if (dateOfBirth != null ? !dateOfBirth.equals(person.dateOfBirth) : person.dateOfBirth != null) return false;
        if (ssn != null ? !ssn.equals(person.ssn) : person.ssn != null) return false;
        if (contactPhone != null ? !contactPhone.equals(person.contactPhone) : person.contactPhone != null)
            return false;
        if (contactEmail != null ? !contactEmail.equals(person.contactEmail) : person.contactEmail != null)
            return false;
        return mailingAddress != null ? mailingAddress.equals(person.mailingAddress) : person.mailingAddress == null;
    }

    @Override
    public int hashCode() {
        int result = personId;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (int) middleInitial;
        result = 31 * result + (dateOfBirth != null ? dateOfBirth.hashCode() : 0);
        result = 31 * result + (ssn != null ? ssn.hashCode() : 0);
        result = 31 * result + (contactPhone != null ? contactPhone.hashCode() : 0);
        result = 31 * result + (contactEmail != null ? contactEmail.hashCode() : 0);
        result = 31 * result + (mailingAddress != null ? mailingAddress.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Person{" +
                "personId=" + personId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleInitial=" + middleInitial +
                ", dateOfBirth=" + dateOfBirth +
                ", ssn='" + ssn + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                ", contactEmail='" + contactEmail + '\'' +
                ", mailingAddress='" + mailingAddress + '\'' +
                '}';
    }
}
