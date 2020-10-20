package edu.westga.cs3230.healthcare_dbms.model.dal;

import edu.westga.cs3230.healthcare_dbms.io.database.QueryResult;
import edu.westga.cs3230.healthcare_dbms.model.Address;
import edu.westga.cs3230.healthcare_dbms.sql.SqlAttribute;
import edu.westga.cs3230.healthcare_dbms.sql.SqlGetter;
import edu.westga.cs3230.healthcare_dbms.sql.SqlManager;
import edu.westga.cs3230.healthcare_dbms.sql.SqlTuple;

import java.sql.*;
import java.util.ArrayList;

public class AddressDAL {
    private String dbUrl;
    private PostDAL postDal;

    public AddressDAL(String dbUrl) {
        this.dbUrl = dbUrl;
        this.postDal = new PostDAL(dbUrl);
    }

    public QueryResult getAddressById(int addressId) throws SQLException {
        String prepared = "select * from Address where address_id = ?";

        SqlManager manager = new SqlManager();
        try (Connection con = DriverManager.getConnection(this.dbUrl);
             PreparedStatement stmt = con.prepareStatement(prepared);
        ) {
            stmt.setInt(1, addressId);
            ResultSet rs = stmt.executeQuery();
            manager.readTuples(rs);
        }

        return new QueryResult(manager.getTuples());
    }

    public QueryResult matchAddress(Address address) throws SQLException {
        SqlTuple tuple = SqlGetter.getFrom(address);
        StringBuilder query = new StringBuilder("SELECT * FROM Address WHERE ");
        for (SqlAttribute attribute : tuple) {
            if (attribute.getValue() == null) {
                continue;
            }
            query.append(attribute.getAttribute()).append(" = ?, ");
        }
        // remove the trailing comma at the end
        query.setLength(query.length() - 2);

        SqlManager manager = new SqlManager();
        try (Connection con = DriverManager.getConnection(this.dbUrl);
             PreparedStatement stmt = con.prepareStatement(query.toString());
        ) {
            int j = 1;
            for(SqlAttribute attr : tuple) {
                if( attr.getValue() == null) {
                    continue;
                }
                stmt.setObject(j, attr.getValue());
                j++;
            }
            ResultSet rs = stmt.executeQuery();
            manager.readTuples(rs);
        }

        return new QueryResult(manager.getTuples());
    }

    public QueryResult attemptAddAddress(Address address) throws SQLException {
        QueryResult attemptedMatch = this.matchAddress(address);
        if (attemptedMatch.getTuples().size() > 0) {
            return attemptedMatch;
        }

        // TODO: Transaction support, uniqueness of address
        ArrayList<SqlTuple> result = this.postDal.postTuple(SqlGetter.getFrom(address));
        return new QueryResult(result);
    }
}
