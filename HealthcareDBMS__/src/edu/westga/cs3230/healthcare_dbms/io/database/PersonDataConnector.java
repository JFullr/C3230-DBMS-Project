package edu.westga.cs3230.healthcare_dbms.io.database;

import edu.westga.cs3230.healthcare_dbms.model.Person;
import edu.westga.cs3230.healthcare_dbms.utils.reflect.ResultSetObjectReflector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonDataConnector {
    private final DatabaseConnector connector;

    public PersonDataConnector(DatabaseConnector connector) {
        this.connector = connector;
    }

    public Person fetchPersonById(int id) throws SQLException {
        try (Connection connection = connector.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Person WHERE person_id = ?")) {
            statement.setInt(1, id);
            try (ResultSet set = statement.executeQuery()) {
                Person person = new Person();
                if (set.next()) {
                    ResultSetObjectReflector.reflect(person, set);
                }
                return null;
            }
        }
    }
}
