package edu.westga.cs3230.healthcare_dbms.model.dal;

import edu.westga.cs3230.healthcare_dbms.io.database.DatabaseConnector;
import edu.westga.cs3230.healthcare_dbms.io.database.QueryResult;
import edu.westga.cs3230.healthcare_dbms.model.LabTestOrder;
import edu.westga.cs3230.healthcare_dbms.sql.SqlManager;

import java.sql.*;

public class LabTestOrderDAL {
    private DatabaseConnector connector;
    private PostDAL postDAL;

    public LabTestOrderDAL(DatabaseConnector connector) {
        this.connector = connector;
        this.postDAL =  new PostDAL(connector);
    }

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

    public QueryResult addLabTestOrder(LabTestOrder order) throws SQLException {
        return new QueryResult(this.postDAL.postTuple(order));
    }

}
