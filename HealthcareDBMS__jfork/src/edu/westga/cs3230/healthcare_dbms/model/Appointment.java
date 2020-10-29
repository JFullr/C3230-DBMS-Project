package edu.westga.cs3230.healthcare_dbms.model;

import java.sql.Date;

import edu.westga.cs3230.healthcare_dbms.sql.SqlGenerated;

public class Appointment {
	
	@SqlGenerated
	private Integer appointment_id;
	private Integer person_id;
	private Date datetime;

}
