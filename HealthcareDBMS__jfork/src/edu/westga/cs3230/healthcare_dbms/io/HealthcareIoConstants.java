package edu.westga.cs3230.healthcare_dbms.io;

import java.net.URL;

import edu.westga.cs3230.healthcare_dbms.view.FullPatientCodeBehind;
import edu.westga.cs3230.healthcare_dbms.view.LoginCodeBehind;
import edu.westga.cs3230.healthcare_dbms.view.PatientCodeBehind;

/**
 * Constants for database IO.
 */
public class HealthcareIoConstants {
	
	/** The Constant SERVER_ADDRESS. */
	public static final String SERVER_ADDRESS = "sql://127.0.0.1:6600";
	
	/** The Constant NULL. */
	public static final String NULL = "NULL";
	
	/** The Constant DEFAULT_SQL_DIR. */
	public static final String DEFAULT_SQL_DIR = "sql/";

	/** The Constant SERVER_REQUEST_FAILURE. */
	public static final String SERVER_REQUEST_FAILURE = "failure";
	
	/** The Constant SERVER_REQUEST_SUCCESS. */
	public static final String SERVER_REQUEST_SUCCESS = "success";
	
	/** The Constant PATIENT_GUI. */
	private static final String PATIENT_GUI = "PatientGui.fxml";
	
	/** The Constant LOGIN_GUI. */
	private static final String LOGIN_GUI = "LoginGui.fxml";
	
	/** The Constant PATIENT_GUI_URL. */
	public static final URL PATIENT_GUI_URL = PatientCodeBehind.class.getResource(PATIENT_GUI);
	
	/** The Constant LOGIN_GUI_URL. */
	public static final URL LOGIN_GUI_URL = LoginCodeBehind.class.getResource(LOGIN_GUI);
	
	/** The Constant FULL_PATIENT_GUI. */
	private static final String FULL_PATIENT_GUI = "FullPatientGui.fxml";
	
	/** The Constant FULL_PATIENT_URL. */
	public static final URL FULL_PATIENT_URL = FullPatientCodeBehind.class.getResource(FULL_PATIENT_GUI);
}
