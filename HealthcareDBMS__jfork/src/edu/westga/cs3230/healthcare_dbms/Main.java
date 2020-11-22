package edu.westga.cs3230.healthcare_dbms;

import edu.westga.cs3230.healthcare_dbms.view.MainPageCodeBehind;
import edu.westga.cs3230.healthcare_dbms.view.utils.FXMLContainer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * The entry point to launch the JavaFX program.
 * 
 * @author ...
 */
public class Main extends Application {

	/**
	 * Start.
	 *
	 * @param primaryStage the primary stage
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			Pane root = new FXMLContainer<MainPageCodeBehind>("MainPageGui").getPane();
			Scene scene = new Scene(root, root.getPrefWidth(), root.getPrefHeight());
			this.setupStage(primaryStage, scene);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Setup stage.
	 *
	 * @param primaryStage the primary stage
	 * @param scene the scene
	 */
	private void setupStage(Stage primaryStage, Scene scene) {
		primaryStage.setScene(scene);
		primaryStage.setTitle("Psuedo Healthcare DBMS");
		primaryStage.show();
		primaryStage.setOnCloseRequest((winEvent)->{
			System.exit(0);
		});
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws Exception the exception
	 */
	public static void main(String[] args) throws Exception {
		launch(args);
	}
}
