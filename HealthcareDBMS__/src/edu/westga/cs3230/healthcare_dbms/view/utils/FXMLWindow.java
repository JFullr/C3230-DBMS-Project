package edu.westga.cs3230.healthcare_dbms.view.utils;

import java.net.URL;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class FXMLWindow {
	
	
	private FXMLLoader loader;
	private Stage stage;
	
	public FXMLWindow(URL fxmlWindowFile, boolean modal) throws Exception {
		this.loadFXMLWindow(fxmlWindowFile, "", modal);
	}
	
	public FXMLWindow(URL fxmlWindowFile, String title, boolean modal) throws Exception {
		this.loadFXMLWindow(fxmlWindowFile, title, modal);
	}
	
	public Object getController(){
		return this.loader.getController();
	}
	
	public void setTitle(String title){
		this.stage.setTitle(title);
	}
	
	public void setOnWindowClose(EventHandler<WindowEvent> event){
		this.stage.setOnHiding(event);
	}
	
	public void show(){
		this.stage.show();
	}
	
	private void loadFXMLWindow(URL fxmlWindowFile, String title, boolean modal) throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader(fxmlWindowFile);
		Parent root1 = (Parent) fxmlLoader.load();
		Stage stage = new Stage();
		if(modal){
			stage.initModality(Modality.APPLICATION_MODAL);
		}else{
			stage.initModality(Modality.NONE);
		}
		stage.setScene(new Scene(root1));
		stage.setTitle(title);
		
		this.stage = stage;
		this.loader = fxmlLoader;
		
	}
	
}
