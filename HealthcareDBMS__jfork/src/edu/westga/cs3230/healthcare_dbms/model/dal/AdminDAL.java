package edu.westga.cs3230.healthcare_dbms.model.dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import edu.westga.cs3230.healthcare_dbms.io.database.DatabaseConnector;
import edu.westga.cs3230.healthcare_dbms.io.database.QueryResult;
import edu.westga.cs3230.healthcare_dbms.sql.SqlManager;
import edu.westga.cs3230.healthcare_dbms.sql.SqlTuple;

public class AdminDAL {

	private DatabaseConnector connector;

	public AdminDAL(DatabaseConnector connector) {
		this.connector = connector;
	}
	
	public QueryResult searchByDates(Date start, Date end) throws SQLException {
		
		SqlManager manager = new SqlManager();
		Connection con = this.connector.getCurrentConnection();
		try (PreparedStatement stmt = con.prepareCall("CALL adminDateQuery(?, ?)")) {
			stmt.setObject(1, start);
			stmt.setObject(2, end);
			ResultSet rs = stmt.executeQuery();
			manager.readTuples(rs);
		}

		return new QueryResult(manager.getTuples());
	}
	
	public QueryResult callQuery(String rawSql) throws SQLException {
		
		SqlManager manager = new SqlManager();
		Connection con = this.connector.getCurrentConnection();
		try (Statement stmt = con.createStatement()) {
			boolean isResultSet = stmt.execute(rawSql);
			
			if(isResultSet) {
				ResultSet rs = stmt.getResultSet();
				manager.readTuples(rs);
			}else {
				Integer updateCount = stmt.getUpdateCount();
				SqlTuple display = new SqlTuple();
				display.add("Tuples Affected", updateCount);
				manager.setRaw(display);
			}
			
		}

		return new QueryResult(manager.getTuples());
	}

}
