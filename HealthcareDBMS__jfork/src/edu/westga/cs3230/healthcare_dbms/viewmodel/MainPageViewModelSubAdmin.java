package edu.westga.cs3230.healthcare_dbms.viewmodel;

import java.sql.Date;
import java.time.LocalDate;

import edu.westga.cs3230.healthcare_dbms.io.database.HealthcareDatabase;
import edu.westga.cs3230.healthcare_dbms.io.database.QueryResult;
import edu.westga.cs3230.healthcare_dbms.view.embed.TupleEmbed;
import edu.westga.cs3230.healthcare_dbms.view.utils.FXMLAlert;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MainPageViewModelSubAdmin {
	
	private final ObjectProperty<LocalDate> adminStartDateProperty;
	private final ObjectProperty<LocalDate> adminEndDateProperty;
	private final StringProperty adminQueryProperty;
	private final ObservableList<TupleEmbed> adminResultList;
	
	private HealthcareDatabase givenDB;
	
	public MainPageViewModelSubAdmin() {
		this.adminStartDateProperty = new SimpleObjectProperty<LocalDate>();
		this.adminEndDateProperty = new SimpleObjectProperty<LocalDate>();
		this.adminQueryProperty = new SimpleStringProperty();
		this.adminResultList = FXCollections.observableArrayList();
	}
	
	public void setDatabase(HealthcareDatabase givenDB) {
		this.givenDB = givenDB;
	}
	
	/**
	 * Performs a query on the operated database
	 *
	 * @param query the query
	 */
	public void handleAdminQuery() {
		String rawSql = this.adminQueryProperty.getValue();
		
		QueryResult results = null;
		try {
			results = this.givenDB.callAdminQuery(rawSql);
		} catch (Exception e) {
			FXMLAlert.statusAlert(e.getMessage());
			return;
		}
		this.setAdminResults(results);
		this.adminQueryProperty.setValue("");
	}
	
	public void handleAdminDateSearch() {
		LocalDate start = this.adminStartDateProperty.getValue();
		LocalDate end = this.adminEndDateProperty.getValue();
		Date dStart = Date.valueOf(start);
		Date dEnd = Date.valueOf(end);
		
		QueryResult results = this.givenDB.callAdminDateQuery(dStart, dEnd);
		this.setAdminResults(results);
	}

	public ObservableList<TupleEmbed> getAdminResultList() {
		return adminResultList;
	}

	public StringProperty getAdminQueryProperty() {
		return adminQueryProperty;
	}

	public ObjectProperty<LocalDate> getAdminEndDateProperty() {
		return adminEndDateProperty;
	}

	public ObjectProperty<LocalDate> getAdminStartDateProperty() {
		return adminStartDateProperty;
	}
	
	private void setAdminResults(QueryResult results) {
		this.adminResultList.clear();
		for(QueryResult result : results) {
			if(result.getTuple() != null) {
				TupleEmbed embed = new TupleEmbed(null, null, result.getTuple());
				this.adminResultList.add(embed);
			}
		}
	}

}
