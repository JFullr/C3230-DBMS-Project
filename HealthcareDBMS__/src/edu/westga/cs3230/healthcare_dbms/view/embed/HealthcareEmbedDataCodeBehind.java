package edu.westga.cs3230.healthcare_dbms.view.embed;

import edu.westga.cs3230.healthcare_dbms.viewmodel.HealthcareEmbedHeaderViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Code-behind file for the embed data view. The embed data is the view displayed when a Helathcare Query header (tuple) is selected
 * from the list view on the main page.
 * 
 * @author 
 */
public class HealthcareEmbedDataCodeBehind {
    
    @FXML
    private Button closeButton;
    
    private HealthcareEmbedHeaderViewModel viewModel;
    
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
    public HealthcareEmbedDataCodeBehind() {
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
	
	private void bindProperties() {
		///TODO add action pane property
    	//this.viewModel.getCloseActionProperty().bindBidirectional(this.closeButton.onActionProperty());
	}
	
}
