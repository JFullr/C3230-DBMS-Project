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

// TODO: Auto-generated Javadoc
/**
 * The Class MainPageViewModelSubAdmin.
 */
public class MainPageViewModelSubAdmin {
	
	/** The admin start date property. */
	private final ObjectProperty<LocalDate> adminStartDateProperty;
	
	/** The admin end date property. */
	private final ObjectProperty<LocalDate> adminEndDateProperty;
	
	/** The admin query property. */
	private final StringProperty adminQueryProperty;
	
	/** The admin result list. */
	private final ObservableList<TupleEmbed> adminResultList;
	
	/** The given DB. */
	private HealthcareDatabase givenDB;
	
	/**
	 * Instantiates a new main page view model sub admin.
	 */
	public MainPageViewModelSubAdmin() {
		this.adminStartDateProperty = new SimpleObjectProperty<LocalDate>();
		this.adminEndDateProperty = new SimpleObjectProperty<LocalDate>();
		this.adminQueryProperty = new SimpleStringProperty();
		this.adminResultList = FXCollections.observableArrayList();
	}
	
	/**
	 * Sets the database.
	 *
	 * @param givenDB the new database
	 */
	public void setDatabase(HealthcareDatabase givenDB) {
		this.givenDB = givenDB;
	}
	
	/**
	 * Performs a query on the operated database.
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
	
	/**
	 * Handle admin date search.
	 */
	public void handleAdminDateSearch() {
		LocalDate start = this.adminStartDateProperty.getValue();
		LocalDate end = this.adminEndDateProperty.getValue();
		Date dStart = Date.valueOf(start);
		Date dEnd = Date.valueOf(end);
		
		QueryResult results = this.givenDB.callAdminDateQuery(dStart, dEnd);
		this.setAdminResults(results);
	}

	/**
	 * Gets the admin result list.
	 *
	 * @return the admin result list
	 */
	public ObservableList<TupleEmbed> getAdminResultList() {
		return adminResultList;
	}

	/**
	 * Gets the admin query property.
	 *
	 * @return the admin query property
	 */
	public StringProperty getAdminQueryProperty() {
		return adminQueryProperty;
	}

	/**
	 * Gets the admin end date property.
	 *
	 * @return the admin end date property
	 */
	public ObjectProperty<LocalDate> getAdminEndDateProperty() {
		return adminEndDateProperty;
	}

	/**
	 * Gets the admin start date property.
	 *
	 * @return the admin start date property
	 */
	public ObjectProperty<LocalDate> getAdminStartDateProperty() {
		return adminStartDateProperty;
	}
	
	/**
	 * Sets the admin results.
	 *
	 * @param results the new admin results
	 */
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
