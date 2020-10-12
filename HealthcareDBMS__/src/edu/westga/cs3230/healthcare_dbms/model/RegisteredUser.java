package edu.westga.cs3230.healthcare_dbms.model;

public class RegisteredUser {
    private int userId;
    private String userName;
    private UserType userType;
    private Person associatedPerson;

    public RegisteredUser() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Person getAssociatedPerson() {
        return associatedPerson;
    }

    public void setAssociatedPerson(Person associatedPerson) {
        this.associatedPerson = associatedPerson;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RegisteredUser that = (RegisteredUser) o;

        if (userId != that.userId) return false;
        if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;
        if (userType != that.userType) return false;
        return associatedPerson != null ? associatedPerson.equals(that.associatedPerson) : that.associatedPerson == null;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (userType != null ? userType.hashCode() : 0);
        result = 31 * result + (associatedPerson != null ? associatedPerson.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RegisteredUser{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userType=" + userType +
                ", associatedPerson=" + associatedPerson +
                '}';
    }
}
