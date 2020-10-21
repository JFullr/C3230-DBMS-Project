package edu.westga.cs3230.healthcare_dbms;

import edu.westga.cs3230.healthcare_dbms.model.Patient;
import edu.westga.cs3230.healthcare_dbms.model.Person;
import edu.westga.cs3230.healthcare_dbms.sql.SqlGenerated;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.application.Application#start(javafx.stage.Stage)
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

	private void setupStage(Stage primaryStage, Scene scene) {
		primaryStage.setScene(scene);
		primaryStage.setTitle("Psuedo Healthcare DBMS");
		//primaryStage.setMaxWidth(650);
		//primaryStage.setWidth(650);
		primaryStage.show();
		primaryStage.setOnCloseRequest((winEvent)->{
			System.exit(0);
		});
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) throws Exception {
		//System.out.println(Person.class.getDeclaredField("person_id").getDeclaredAnnotation(SqlGenerated.class)!=null);
		launch(args);
	}
}
