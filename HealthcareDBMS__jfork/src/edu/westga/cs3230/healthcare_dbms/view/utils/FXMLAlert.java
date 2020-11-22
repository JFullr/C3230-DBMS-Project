package edu.westga.cs3230.healthcare_dbms.view.utils;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

/**
 * Shows alerts to the user.
 *
 * @author Joseph Fuller
 */
public class FXMLAlert {
	
	/**
	 * Status alert.
	 *
	 * @param title the title
	 * @param headerMessage the header message
	 * @param message the message
	 * @param icon the icon
	 * @return the optional
	 */
	public static Optional<ButtonType> statusAlert(String title, String headerMessage, String message, AlertType icon) {
		Alert alert = new Alert(icon);
		alert.setTitle(title);
		alert.setHeaderText(headerMessage);
		alert.setContentText(message);
		return alert.showAndWait();
	}
	
	/**
	 * Status alert.
	 *
	 * @param title the title
	 * @param message the message
	 * @param icon the icon
	 * @return the optional
	 */
	public static Optional<ButtonType> statusAlert(String title, String message, AlertType icon) {
		Alert alert = new Alert(icon);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		return alert.showAndWait();
	}
	
	/**
	 * Status alert.
	 *
	 * @param message the message
	 * @param icon the icon
	 * @return the optional
	 */
	public static Optional<ButtonType> statusAlert(String message, AlertType icon) {
		Alert alert = new Alert(icon);
		alert.setTitle("Status");
		alert.setHeaderText(null);
		alert.setContentText(message);
		return alert.showAndWait();
	}
	
	/**
	 * Status alert.
	 *
	 * @param message the message
	 */
	public static void statusAlert(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Status");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

}
