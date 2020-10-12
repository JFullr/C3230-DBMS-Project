package edu.westga.cs3230.healthcare_dbms.view.embed;

import java.util.ArrayList;
import java.util.List;

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

	/**
	 * Instantiates a new query embed manager.
	 */
	public HealthcareEmbedHandler() {
		this.allQueryEmbeds = new ArrayList<HealthcareEmbed>();
		this.displayedQueryEmbeds = FXCollections.observableArrayList();

		this.displayedQueryEmbedsProperty = new SimpleListProperty<HealthcareEmbed>();

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

}
