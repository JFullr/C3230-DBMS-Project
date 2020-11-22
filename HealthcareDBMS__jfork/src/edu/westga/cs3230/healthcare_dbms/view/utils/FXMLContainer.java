package edu.westga.cs3230.healthcare_dbms.view.utils;

import java.net.URL;

import edu.westga.cs3230.healthcare_dbms.utils.ExceptionText;
import edu.westga.cs3230.healthcare_dbms.view.MainPageCodeBehind;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

/**
 * Class for containing FXML document and data while enforcing MVVM location.
 *
 * @author Joseph Fuller
 * @param <T> the generic type
 */
public class FXMLContainer<T> {
	
	/** The backed pane. */
	private Pane backedPane;
	
	/** The backed loader. */
	private FXMLLoader backedLoader;
	
	/**
	 * Loads a JavaFX pane by name.
	 * Enforces documents to be in the view package, or sub-packages.
	 *
	 * @param resourceName the name used to identify the document
	 * @return the pane loaded
	 */
	public FXMLContainer(String resourceName) {
		this.backedPane = null;
		this.backedLoader = new FXMLLoader();
		URL loc = MainPageCodeBehind.class.getResource(resourceName + ".fxml");
		this.backedLoader.setLocation(loc);
		try {
			this.backedPane = this.backedLoader.load();
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException(ExceptionText.FXML_RESOURCE_NOT_LOADED);
		}
	}
	
	/**
	 * Gets the stored pane.
	 *
	 * @return the stored pane
	 */
	public Pane getPane() {
		return this.backedPane;
	}
	
	/**
	 * Gets the controller.
	 *
	 * @return the controller
	 */
	public T getController() {
		return this.backedLoader.getController();
	}

}
