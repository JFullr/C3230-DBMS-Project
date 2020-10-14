package edu.westga.cs3230.healthcare_dbms.view.embed;

import edu.westga.cs3230.healthcare_dbms.io.database.QueryResult;
import edu.westga.cs3230.healthcare_dbms.view.utils.FXMLContainer;
import edu.westga.cs3230.healthcare_dbms.viewmodel.HeaderViewModel;
import javafx.scene.layout.Pane;

/**
 * A Healthcare Embed that contains data and is displayable in a component.
 * 
 * @author 
 */
public class Embed extends Pane {

	private static final String HEADER_FILE = "embed/EmbedHeaderGui_alt";
	private static final String DATA_BODY_FILE = "embed/EmbedDataGui_alt";

	private boolean showDataPane;

	private FXMLContainer<EmbedHeaderCodeBehind> header;
	private FXMLContainer<EmbedDataCodeBehind> dataBody;

	/**
	 * Instantiates a new healthcare embed.
	 */
	public Embed(QueryResult resultToDisplay) {
		this.createEmbedHeaderPane(resultToDisplay);
		this.createEmbedDataPane(resultToDisplay);

		this.getChildren().add(this.header.getPane());
		this.showDataPane = false;
	}

	/**
	 * Shows the data panel for this entry.
	 */
	public void showPane() {
		this.showDataPane = true;
		this.getChildren().clear();
		this.getChildren().add(this.dataBody.getPane());
	}

	/**
	 * Hides the data panel for this entry.
	 */
	public void hidePane() {
		this.showDataPane = false;
		this.getChildren().clear();
		this.getChildren().add(this.header.getPane());
	}

	/**
	 * Determine if the data panel for this entry is showing currently.
	 * 
	 * @return is showing boolean
	 */
	public boolean isShowingDataPane() {
		return this.showDataPane;
	}
	
	
	
	
	private void createEmbedBodyPane(QueryResult result) {
		this.dataBody = new FXMLContainer<EmbedDataCodeBehind>(DATA_BODY_FILE);
		this.assignBodyData(result);
	}
	
	private void createEmbedHeaderPane(QueryResult result) {
		this.header = new FXMLContainer<EmbedHeaderCodeBehind>(HEADER_FILE);
		this.assignHeaderData(result);
	}

	private void assignHeaderData(QueryResult result) {
		HeaderViewModel viewModel = this.header.getController().getViewModel();
		
	}

	private void createEmbedDataPane(QueryResult result) {
		this.dataBody = new FXMLContainer<EmbedDataCodeBehind>(DATA_BODY_FILE);
		this.assignBodyData(result);
	}

	private void assignBodyData(QueryResult result) {
		HeaderViewModel viewModel = this.dataBody.getController().getViewModel();

		//TODO inject tuples into list property or observable list
		//
		//TODO create close property for close button in tuple action container
		/*
		viewModel.getCloseActionProperty().setValue((event) -> {
			this.hidePane();
		});
		*/
	}

}
