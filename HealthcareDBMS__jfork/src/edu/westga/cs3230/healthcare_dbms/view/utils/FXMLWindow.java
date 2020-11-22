package edu.westga.cs3230.healthcare_dbms.view.utils;

import java.net.URL;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Manages a window loaded from an FXML file.
 *
 * @author Joseph Fuller
 */
public class FXMLWindow {
	
	/** The FXML loader. */
	private FXMLLoader loader;
	
	/** The stage. */
	private Stage stage;
	
	/** The root component. */
	private Parent root;
	
	/**
	 * Instantiates a new FXML window.
	 *
	 * @param fxmlWindowFile the fxml window file
	 * @param modal the modal
	 * @throws Exception the exception
	 */
	public FXMLWindow(URL fxmlWindowFile, boolean modal) throws Exception {
		this.loadFXMLWindow(fxmlWindowFile, "", modal);
	}
	
	/**
	 * Instantiates a new FXML window.
	 *
	 * @param fxmlWindowFile the fxml window file
	 * @param title the title
	 * @param modal the modal
	 * @throws Exception the exception
	 */
	public FXMLWindow(URL fxmlWindowFile, String title, boolean modal) throws Exception {
		this.loadFXMLWindow(fxmlWindowFile, title, modal);
	}
	
	/**
	 * Gets the controller.
	 *
	 * @return the controller
	 */
	public Object getController(){
		return this.loader.getController();
	}
	
	/**
	 * Gets the node.
	 *
	 * @return the node
	 */
	public Node getNode() {
		return this.root;
	}
	
	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void setTitle(String title){
		this.stage.setTitle(title);
	}
	
	/**
	 * Sets the on window close.
	 *
	 * @param event the new on window close
	 */
	public void setOnWindowClose(EventHandler<WindowEvent> event){
		this.stage.setOnHiding(event);
	}
	
	/**
	 * Pack.
	 */
	public void pack() {
		this.stage.getScene().getWindow().sizeToScene();
	}
	
	/**
	 * Show.
	 */
	public void show(){
		this.stage.show();
	}
	
	/**
	 * Load FXML window.
	 *
	 * @param fxmlWindowFile the fxml window file
	 * @param title the title
	 * @param modal the modal
	 * @throws Exception the exception
	 */
	private void loadFXMLWindow(URL fxmlWindowFile, String title, boolean modal) throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader(fxmlWindowFile);
		this.root = (Parent) fxmlLoader.load();
		
		Stage stage = new Stage();
		if(modal){
			stage.initModality(Modality.APPLICATION_MODAL);
		}else{
			stage.initModality(Modality.NONE);
		}
		
		Scene scene = new Scene(this.root);
		stage.setScene(scene);
		stage.setTitle(title);
		
		this.stage = stage;
		this.loader = fxmlLoader;
		
	}
	
}
