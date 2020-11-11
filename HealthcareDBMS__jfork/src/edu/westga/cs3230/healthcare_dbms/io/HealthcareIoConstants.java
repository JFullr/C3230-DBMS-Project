package edu.westga.cs3230.healthcare_dbms.io;

import java.net.URL;

import edu.westga.cs3230.healthcare_dbms.view.FullPatientCodeBehind;
import edu.westga.cs3230.healthcare_dbms.view.LoginCodeBehind;
import edu.westga.cs3230.healthcare_dbms.view.PatientCodeBehind;
import edu.westga.cs3230.healthcare_dbms.view.utils.FXMLWindow;
import edu.westga.cs3230.healthcare_dbms.viewmodel.PatientViewModel;

/**
 * Constants for database IO.
 */
public class HealthcareIoConstants {
	
	public static final String SERVER_ADDRESS = "sql://127.0.0.1:6600";
	
	public static final String NULL = "NULL";
	
	public static final String DEFAULT_SQL_DIR = "sql/";

	public static final String SERVER_REQUEST_FAILURE = "failure";
	
	public static final String SERVER_REQUEST_SUCCESS = "success";
	
	private static final String PATIENT_GUI = "PatientGui.fxml";
	private static final String LOGIN_GUI = "LoginGui.fxml";
	
	public static final URL PATIENT_GUI_URL = PatientCodeBehind.class.getResource(PATIENT_GUI);
	public static final URL LOGIN_GUI_URL = LoginCodeBehind.class.getResource(LOGIN_GUI);
	
	private static final String FULL_PATIENT_GUI = "FullPatientGui.fxml";
	public static final URL FULL_PATIENT_URL = FullPatientCodeBehind.class.getResource(FULL_PATIENT_GUI);
}
