package edu.westga.cs3230.healthcare_dbms.view.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class FXMLAlert {
	
	public static void statusAlert(String title, String headerMessage, String message, AlertType icon) {
		Alert alert = new Alert(icon);
		alert.setTitle(title);
		alert.setHeaderText(headerMessage);
		alert.setContentText(message);
		alert.showAndWait();
	}
	
	public static void statusAlert(String title, String message, AlertType icon) {
		Alert alert = new Alert(icon);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
	
	public static void statusAlert(String message, AlertType icon) {
		Alert alert = new Alert(icon);
		alert.setTitle("Status");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

}
