package edu.westga.cs3230.healthcare_dbms.io;

import java.net.URL;

import edu.westga.cs3230.healthcare_dbms.view.AppointmentCodeBehind;
import edu.westga.cs3230.healthcare_dbms.view.AppointmentSearchCodeBehind;
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
	private static final String APPOINTMENT_GUI = "AppointmentGui.fxml";
	private static final String APPOINTMENT_SEARCH_GUI = "AppointmentSearchGui.fxml";
	private static final String APPOINTMENT_CHECKUP_GUI = "AppointmentCheckupGui.fxml";
	
	public static final URL PATIENT_GUI_URL = PatientCodeBehind.class.getResource(PATIENT_GUI);
	public static final URL LOGIN_GUI_URL = LoginCodeBehind.class.getResource(LOGIN_GUI);
	public static final URL APPOINTMENT_GUI_URL = AppointmentCodeBehind.class.getResource(APPOINTMENT_GUI);
	public static final URL APPOINTMENT_SEARCH_GUI_URL = AppointmentSearchCodeBehind.class.getResource(APPOINTMENT_SEARCH_GUI);
	public static final URL APPOINTMENT_CHECKUP_URL = AppointmentCodeBehind.class.getResource(APPOINTMENT_CHECKUP_GUI);
}
