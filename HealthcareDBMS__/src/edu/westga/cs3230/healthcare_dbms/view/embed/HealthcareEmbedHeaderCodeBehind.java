package edu.westga.cs3230.healthcare_dbms.view.embed;

import edu.westga.cs3230.healthcare_dbms.viewmodel.HealthcareEmbedHeaderViewModel;
import javafx.beans.property.ListProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

/**
 * Code-behind file for the embed header view. The embed header is displayed on the list view on the main page, and display
 * the embed data when chosen.
 * 
 * @author 
 */
public class HealthcareEmbedHeaderCodeBehind {
	
	@FXML
	private ListView<HealthcareEmbed> attributeView;
	private HealthcareEmbedHeaderViewModel viewModel;
	private ListProperty<HealthcareEmbed> displayAttributesList;

	/**
	 * JavaFx ComponentInitializer.
	 */
	@FXML
	public void initialize() {
		
		this.viewModel.getDisplayAttributeListProperty().bindBidirectional(this.displayAttributesList);

	}

	/**
	 * Instantiates a new healthcare embed header code behind.
	 */
	public HealthcareEmbedHeaderCodeBehind() {
		this.viewModel = new HealthcareEmbedHeaderViewModel();
	}

	/**
	 * Gets the view model.
	 *
	 * @return the view model
	 */
	public HealthcareEmbedHeaderViewModel getViewModel() {
		return viewModel;
	}

}