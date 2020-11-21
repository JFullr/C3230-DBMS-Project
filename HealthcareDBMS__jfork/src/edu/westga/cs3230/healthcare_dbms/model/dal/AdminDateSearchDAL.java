package edu.westga.cs3230.healthcare_dbms.model.dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import edu.westga.cs3230.healthcare_dbms.io.database.DatabaseConnector;
import edu.westga.cs3230.healthcare_dbms.io.database.QueryResult;
import edu.westga.cs3230.healthcare_dbms.sql.SqlManager;

public class AdminDateSearchDAL {

	private DatabaseConnector connector;

	public AdminDateSearchDAL(DatabaseConnector connector) {
		this.connector = connector;
	}
	
	public QueryResult searchByDates(Date start, Date end) throws SQLException {
		/*
		String query = "select * from LabTest "
						+ "WHERE is_available = TRUE";
		
		SqlManager manager = new SqlManager();
		Connection con = this.connector.getCurrentConnection();
		try (Statement stmt = con.createStatement()) {
			ResultSet rs = stmt.executeQuery(query);
			manager.readTuples(rs);
		}
		
		return new QueryResult(manager.getTuples());
		//*/
		return null;
	}

}
