package edu.westga.cs3230.healthcare_dbms.viewmodel;

import edu.westga.cs3230.healthcare_dbms.view.embed.HealthcareEmbed;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;

/**
 * The Class HealthcareEmbedHeaderViewModel.
 * 
 * @author 
 */
public class HealthcareEmbedHeaderViewModel {
	
	private ListProperty<HealthcareEmbed> displayAttributesList;
	

	/**
	 * Instantiates a new healthcare embed header view model.
	 */
	public HealthcareEmbedHeaderViewModel() {
		
		this.displayAttributesList = new SimpleListProperty<HealthcareEmbed>();
			
	}
	
	/**
	 * Gets the display attributes list property.
	 *
	 * @return the display attributes list property
	 */
	public ListProperty<HealthcareEmbed> getDisplayAttributeListProperty() {
		return this.displayAttributesList;
	}
	

}
