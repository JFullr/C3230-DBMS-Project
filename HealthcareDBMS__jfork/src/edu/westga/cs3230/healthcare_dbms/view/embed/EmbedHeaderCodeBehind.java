package edu.westga.cs3230.healthcare_dbms.view.embed;

import edu.westga.cs3230.healthcare_dbms.viewmodel.HeaderViewModel;
import javafx.beans.property.ListProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

/**
 * Code-behind file for the embed header view. The embed header is displayed on the list view on the main page, and display
 * the embed data when chosen.
 * 
 * @author 
 */
public class EmbedHeaderCodeBehind {
	
	@FXML
	private ListView<Embed> attributeView;
	private HeaderViewModel viewModel;
	private ListProperty<Embed> displayAttributesList;

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
	public EmbedHeaderCodeBehind() {
		this.viewModel = new HeaderViewModel();
	}

	/**
	 * Gets the view model.
	 *
	 * @return the view model
	 */
	public HeaderViewModel getViewModel() {
		return viewModel;
	}

}