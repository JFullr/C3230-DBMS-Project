package edu.westga.cs3230.healthcare_dbms.view.embed;

import java.util.ArrayList;
import java.util.List;

import edu.westga.cs3230.healthcare_dbms.io.database.QueryResult;
import edu.westga.cs3230.healthcare_dbms.io.database.QueryResultStorage;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Responsible for managing the embeds displayed in a displayed row
 * 
 */
public class EmbedHandler {

	private List<Embed> allQueryEmbeds;
	private ObservableList<Embed> displayedQueryEmbeds;
	private ListProperty<Embed> displayedQueryEmbedsProperty;
	
	private QueryResultStorage storage;

	/**
	 * Instantiates a new query embed manager.
	 * 
	 * @param query storage to pull from
	 */
	public EmbedHandler(QueryResultStorage storage) {
		this.storage = storage;

		this.allQueryEmbeds = new ArrayList<Embed>();
		this.displayedQueryEmbeds = FXCollections.observableArrayList();

		this.displayedQueryEmbedsProperty = new SimpleListProperty<Embed>();

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
	public ObservableList<Embed> getDisplayedQueryEmbeds() {
		return this.displayedQueryEmbeds;
	}
	
	/**
	 * Gets the displayed healthcare embeds list property for binding
	 * 
	 * @return the displayed healthcare embeds property
	 */
	public ListProperty<Embed> getDisplayedQueryEmbedsProperty() {
		return this.displayedQueryEmbedsProperty;
	}

	/**
	 * Adds a query result to the managed embeds
	 * 
	 * @param result the query result to add to the embeds
	 */
	public void addQueryResult(QueryResult result) {
		this.allQueryEmbeds.add(this.createHealthcareEmbed(result));
	}

	private Embed createHealthcareEmbed(QueryResult data) {
		Embed embed = null;
		///TODO create query embed from result
		//embed = new HealthcareEmbed()
	
		return embed;
	}

	private void addAllEmbedsToDisplay() {
		
		this.displayedQueryEmbeds.clear();
		for (QueryResult item : this.storage.getLatestResults()) {
			Embed embed = this.createHealthcareEmbed(item);
			this.displayedQueryEmbeds.add(embed);
		}
		this.displayedQueryEmbedsProperty.set(this.displayedQueryEmbeds);
	}

	private void updateDisplayedQueryEmbeds() {
		this.displayedQueryEmbeds.clear();
		
		for (Embed embed : this.allQueryEmbeds) {
			this.displayedQueryEmbeds.add(embed);
		}
	}

}
