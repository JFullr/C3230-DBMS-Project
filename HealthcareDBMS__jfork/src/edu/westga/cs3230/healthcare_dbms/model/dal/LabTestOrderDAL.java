package edu.westga.cs3230.healthcare_dbms.model.dal;

import edu.westga.cs3230.healthcare_dbms.io.database.QueryResult;
import edu.westga.cs3230.healthcare_dbms.model.LabTestOrder;
import edu.westga.cs3230.healthcare_dbms.sql.SqlManager;

import java.sql.*;

public class LabTestOrderDAL {
    private String dbUrl;
    private PostDAL postDAL;

    public LabTestOrderDAL(String dbUrl) {
        this.dbUrl = dbUrl;
        this.postDAL =  new PostDAL(dbUrl);
    }

    public QueryResult getLabTestOrdersForAppointment(int appointmentId) throws SQLException {
        String query = "select * from LabTestOrder where appointment_id = ?";

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

    public QueryResult addLabTestOrder(LabTestOrder order) throws SQLException {
        return new QueryResult(this.postDAL.postTuple(order));
    }

}
