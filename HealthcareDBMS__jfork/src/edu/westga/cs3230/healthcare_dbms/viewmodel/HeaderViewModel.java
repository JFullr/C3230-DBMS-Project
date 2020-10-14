package edu.westga.cs3230.healthcare_dbms.viewmodel;

import edu.westga.cs3230.healthcare_dbms.view.embed.Embed;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;

/**
 * The Class HealthcareEmbedHeaderViewModel.
 * 
 * @author 
 */
public class HeaderViewModel {
	
	private ListProperty<Embed> displayAttributesList;
	

	/**
	 * Instantiates a new healthcare embed header view model.
	 */
	public HeaderViewModel() {
		
		this.displayAttributesList = new SimpleListProperty<Embed>();
			
	}
	
	/**
	 * Gets the display attributes list property.
	 *
	 * @return the display attributes list property
	 */
	public ListProperty<Embed> getDisplayAttributeListProperty() {
		return this.displayAttributesList;
	}
	

}
