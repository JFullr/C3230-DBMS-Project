package edu.westga.cs3230.healthcare_dbms.io.database;

import edu.westga.cs3230.healthcare_dbms.model.RegisteredUser;
import edu.westga.cs3230.healthcare_dbms.model.UserType;
import edu.westga.cs3230.healthcare_dbms.utils.reflect.ResultSetObjectReflector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The DAL for working with users, including authentication functionality.
 */
public class UserDataConnector {
    private final DatabaseConnector connector;
    private final PersonDataConnector personDataConnector;

    public UserDataConnector(DatabaseConnector connector) {
        this.connector = connector;
        this.personDataConnector = new PersonDataConnector(connector);
    }

    private RegisteredUser materialize(ResultSet set) throws SQLException {
        RegisteredUser user = new RegisteredUser();
        ResultSetObjectReflector.reflect(user, set);
        user.setUserType(UserType.valueOf(set.getString("user_type")));
        user.setAssociatedPerson(personDataConnector.fetchPersonById(set.getInt("person_id")));
        return user;
    }

    public RegisteredUser authenticate(String username, String password) throws SQLException {
        try (Connection connection = connector.getConnection();
             PreparedStatement login = connection.prepareStatement("select r.user_id AS user_id, r.user_name AS user_name, " +
                     "r.person_id AS person_id, r.user_type AS user_type from RegisteredUser r, UserPasswordStore ups " +
                     "where r.user_name = ? and p.user_id = r.user_id and ups.password = ?")) {
            login.setString(1, username);
            login.setString(2, password);

            try (ResultSet set = login.executeQuery()) {
                if (set.next()) {
                    return materialize(set);
                } else {
                    return null;
                }
            }
        }
    }
}
