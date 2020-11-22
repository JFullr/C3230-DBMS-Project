package edu.westga.cs3230.healthcare_dbms.model.dal;

import edu.westga.cs3230.healthcare_dbms.io.database.DatabaseConnector;
import edu.westga.cs3230.healthcare_dbms.io.database.QueryResult;
import edu.westga.cs3230.healthcare_dbms.sql.SqlManager;

import java.sql.*;

// TODO: Auto-generated Javadoc
/**
 * The Class LabTestResultDAL.
 */
public class LabTestResultDAL {
    
    /** The connector. */
    private DatabaseConnector connector;

    /**
     * Instantiates a new lab test result DAL.
     *
     * @param connector the connector
     */
    public LabTestResultDAL(DatabaseConnector connector) {
        this.connector = connector;
    }
    
    /**
     * Gets the lab test order result for.
     *
     * @param labTestOrderId the lab test order id
     * @return the lab test order result for
     * @throws SQLException the SQL exception
     */
    public QueryResult getLabTestOrderResultFor(int labTestOrderId) throws SQLException {
    	
    	String query = "CALL get_lab_result(?)";
        SqlManager manager = new SqlManager();
        Connection con = this.connector.getCurrentConnection();
        try (PreparedStatement stmt = con.prepareCall(query)) {
            stmt.setInt(1, labTestOrderId);
            ResultSet rs = stmt.executeQuery();
            manager.readTuples(rs);
        }

        return new QueryResult(manager.getTuples());
    }

    /**
     * Gets the lab test order results for appointment.
     *
     * @param appointmentId the appointment id
     * @return the lab test order results for appointment
     * @throws SQLException the SQL exception
     */
    public QueryResult getLabTestOrderResultsForAppointment(int appointmentId) throws SQLException {
        String query = "SELECT r.* from LabTestResult r, LabTestOrder o "
        				+ "WHERE o.appointment_id = ? AND r.lab_test_order_id = o.lab_test_order_id";

        SqlManager manager = new SqlManager();
        Connection con = this.connector.getCurrentConnection();
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, appointmentId);
            ResultSet rs = stmt.executeQuery();
            manager.readTuples(rs);
        }

        return new QueryResult(manager.getTuples());
    }

}
