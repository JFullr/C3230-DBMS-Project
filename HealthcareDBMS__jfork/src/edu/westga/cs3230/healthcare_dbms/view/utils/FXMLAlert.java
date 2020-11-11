package edu.westga.cs3230.healthcare_dbms.view.utils;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class FXMLAlert {
	
	public static Optional<ButtonType> statusAlert(String title, String headerMessage, String message, AlertType icon) {
		Alert alert = new Alert(icon);
		alert.setTitle(title);
		alert.setHeaderText(headerMessage);
		alert.setContentText(message);
		return alert.showAndWait();
	}
	
	public static Optional<ButtonType> statusAlert(String title, String message, AlertType icon) {
		Alert alert = new Alert(icon);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		return alert.showAndWait();
	}
	
	public static Optional<ButtonType> statusAlert(String message, AlertType icon) {
		Alert alert = new Alert(icon);
		alert.setTitle("Status");
		alert.setHeaderText(null);
		alert.setContentText(message);
		return alert.showAndWait();
	}
	
	public static void statusAlert(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Status");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

}
