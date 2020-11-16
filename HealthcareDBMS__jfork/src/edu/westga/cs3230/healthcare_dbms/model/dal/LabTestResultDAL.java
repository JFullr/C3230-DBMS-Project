package edu.westga.cs3230.healthcare_dbms.model.dal;

import edu.westga.cs3230.healthcare_dbms.io.database.QueryResult;
import edu.westga.cs3230.healthcare_dbms.model.LabTestOrder;
import edu.westga.cs3230.healthcare_dbms.sql.SqlManager;

import java.sql.*;

public class LabTestResultDAL {
    private String dbUrl;

    public LabTestResultDAL(String dbUrl) {
        this.dbUrl = dbUrl;
    }
    
    public QueryResult getLabTestOrderResultFor(int labTestOrderId) throws SQLException {
        //String query = "SELECT * FROM LabTestResult "
        //				+ "WHERE lab_test_order_id = ?";

    	String query = "CALL get_lab_result(?)";
        SqlManager manager = new SqlManager();
        try (Connection con = DriverManager.getConnection(this.dbUrl);
             PreparedStatement stmt =  con.prepareCall(query)
        ) {
            stmt.setInt(1, labTestOrderId);
            ResultSet rs = stmt.executeQuery();
            manager.readTuples(rs);
        }

        return new QueryResult(manager.getTuples());
    }

    public QueryResult getLabTestOrderResultsForAppointment(int appointmentId) throws SQLException {
        String query = "SELECT r.* from LabTestResult r, LabTestOrder o "
        				+ "WHERE o.appointment_id = ? AND r.lab_test_order_id = o.lab_test_order_id";

        SqlManager manager = new SqlManager();
        try (Connection con = DriverManager.getConnection(this.dbUrl);
             PreparedStatement stmt = con.prepareStatement(query)
        ) {
            stmt.setInt(1, appointmentId);
            ResultSet rs = stmt.executeQuery();
            manager.readTuples(rs);
        }

        return new QueryResult(manager.getTuples());
    }

}
