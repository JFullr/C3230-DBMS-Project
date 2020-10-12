package edu.westga.cs3230.healthcare_dbms.model.raw;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RawQueryResult {

	private List<String> columnNames;
	private List<List<Object>> tuples;
	
	public RawQueryResult(ResultSet set) throws SQLException {
		this.columnNames = new ArrayList<>();

		ResultSetMetaData metaData = set.getMetaData();
		for (int i = 1; i <= metaData.getColumnCount(); i++) {
			String columnName = metaData.getColumnName(i);
			this.columnNames.add(columnName);
		}

		this.tuples = new ArrayList<>();
		while (set.next()) {
			List<Object> tuple = new ArrayList<>(this.columnNames.size());
			for (int i = 1; i <= this.columnNames.size(); i++) {
				tuple.add(set.getObject(i));
			}
			this.tuples.add(tuple);
		}
	}

	public List<String> getColumnNames() {
		return columnNames;
	}

	public List<List<Object>> getTuples() {
		return tuples;
	}
}
