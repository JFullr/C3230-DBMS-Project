package edu.westga.cs3230.healthcare_dbms.view.embed;

import edu.westga.cs3230.healthcare_dbms.viewmodel.HeaderViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Code-behind file for the embed data view. The embed data is the view displayed when a Helathcare Query header (tuple) is selected
 * from the list view on the main page.
 * 
 * @author 
 */
public class EmbedDataCodeBehind {
    
    @FXML
    private Button closeButton;
    
    private HeaderViewModel viewModel;
    
    /**
     * JavaFx ComponentInitializer.
     */
    @FXML
	public void initialize() {
    	this.bindProperties();
	}
    
    /**
     * Instantiates a new Healthcare embed data code behind.
     */
    public EmbedDataCodeBehind() {
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
	
	private void bindProperties() {
		///TODO add action pane property
    	//this.viewModel.getCloseActionProperty().bindBidirectional(this.closeButton.onActionProperty());
	}
	
}
