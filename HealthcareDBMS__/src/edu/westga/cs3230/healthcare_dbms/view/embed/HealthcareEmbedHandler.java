package edu.westga.cs3230.healthcare_dbms.view.embed;

import java.util.ArrayList;
import java.util.List;

import edu.westga.cs3230.healthcare_dbms.model.HealthcareQueryResult;
import edu.westga.cs3230.healthcare_dbms.model.QueryResultStorage;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Responsible for managing the embeds displayed in a displayed row
 * 
 */
public class HealthcareEmbedHandler {

	private List<HealthcareEmbed> allQueryEmbeds;
	private ObservableList<HealthcareEmbed> displayedQueryEmbeds;
	private ListProperty<HealthcareEmbed> displayedQueryEmbedsProperty;
	
	private QueryResultStorage storage;

	/**
	 * Instantiates a new query embed manager.
	 * 
	 * @param query storage to pull from
	 */
	public HealthcareEmbedHandler(QueryResultStorage storage) {
		this.storage = storage;

		this.allQueryEmbeds = new ArrayList<HealthcareEmbed>();
		this.displayedQueryEmbeds = FXCollections.observableArrayList();

		this.displayedQueryEmbedsProperty = new SimpleListProperty<HealthcareEmbed>();

	}

	/**
	 * updates the embeds display.
	 */
	public void updateQueryEmbeds() {
		this.updateDisplayedQueryEmbeds();

		this.displayedQueryEmbedsProperty.set(this.displayedQueryEmbeds);
	}

	/**
	 * Gets the displayed healthcare embeds for property binding
	 * 
	 * @return the displayed healthcare embeds
	 */
	public ObservableList<HealthcareEmbed> getDisplayedQueryEmbeds() {
		return this.displayedQueryEmbeds;
	}
	
	/**
	 * Gets the displayed healthcare embeds list property for binding
	 * 
	 * @return the displayed healthcare embeds property
	 */
	public ListProperty<HealthcareEmbed> getDisplayedQueryEmbedsProperty() {
		return this.displayedQueryEmbedsProperty;
	}

	/**
	 * Adds a query result to the managed embeds
	 * 
	 * @param result the query result to add to the embeds
	 */
	public void addQueryResult(HealthcareQueryResult result) {
		this.allQueryEmbeds.add(this.createHealthcareEmbed(result));
	}

	private HealthcareEmbed createHealthcareEmbed(HealthcareQueryResult data) {
		HealthcareEmbed embed = null;
		///TODO create query embed from result
		//embed = new HealthcareEmbed()
	
		return embed;
	}

	private void addAllEmbedsToDisplay() {
		
		this.displayedQueryEmbeds.clear();
		for (HealthcareQueryResult item : this.storage.getLatestResults()) {
			HealthcareEmbed embed = this.createHealthcareEmbed(item);
			this.displayedQueryEmbeds.add(embed);
		}
		this.displayedQueryEmbedsProperty.set(this.displayedQueryEmbeds);
	}

	private void updateDisplayedQueryEmbeds() {
		this.displayedQueryEmbeds.clear();
		
		for (HealthcareEmbed embed : this.allQueryEmbeds) {
			this.displayedQueryEmbeds.add(embed);
		}
	}

}
