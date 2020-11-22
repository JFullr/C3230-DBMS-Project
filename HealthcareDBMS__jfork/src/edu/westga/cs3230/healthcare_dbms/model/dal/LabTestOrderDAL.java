package edu.westga.cs3230.healthcare_dbms.model.dal;

import edu.westga.cs3230.healthcare_dbms.io.database.DatabaseConnector;
import edu.westga.cs3230.healthcare_dbms.io.database.QueryResult;
import edu.westga.cs3230.healthcare_dbms.model.LabTestOrder;
import edu.westga.cs3230.healthcare_dbms.sql.SqlManager;

import java.sql.*;

/**
 * Provides a DAL for accessing and adding lab test orders for a given patient.
 *
 * @author Joseph Fuller and Andrew Steinborn
 */
public class LabTestOrderDAL {
    
    /** The connector. */
    private DatabaseConnector connector;
    
    /** The post DAL. */
    private PostDAL postDAL;

    /**
     * Instantiates a new lab test order DAL.
     *
     * @param connector the connector
     */
    public LabTestOrderDAL(DatabaseConnector connector) {
        this.connector = connector;
        this.postDAL =  new PostDAL(connector);
    }

    /**
     * Gets the lab test orders for appointment.
     *
     * @param appointmentId the appointment id
     * @return the lab test orders for appointment
     * @throws SQLException the SQL exception
     */
    public QueryResult getLabTestOrdersForAppointment(int appointmentId) throws SQLException {
        String query = "select * from LabTestOrder where appointment_id = ?";

        SqlManager manager = new SqlManager();
        Connection con = connector.getCurrentConnection();
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, appointmentId);
            ResultSet rs = stmt.executeQuery();
            manager.readTuples(rs);
        }

        return new QueryResult(manager.getTuples());
    }

    /**
     * Adds the lab test order.
     *
     * @param order the order
     * @return the query result
     * @throws SQLException the SQL exception
     */
    public QueryResult addLabTestOrder(LabTestOrder order) throws SQLException {
        return new QueryResult(this.postDAL.postTuple(order));
    }

}
