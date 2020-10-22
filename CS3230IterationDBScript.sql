drop table if exists UpdateQuery;
drop table if exists SearchQuery;
drop table if exists DeleteQuery;
drop table if exists SearchQuery;
drop table if exists CreateQuery;
drop table if exists ReadQuery;
drop table if exists AdminQuery;
drop table if exists Query;

drop table if exists UserPasswordStore;
drop table if exists RegisteredUser;

drop table if exists LabTestResult;
drop table if exists LabTestOrder;
drop table if exists LabTest;

drop table if exists AppointmentCheckup;
drop table if exists Diagnosis;
drop table if exists FinalDiagnosis;
drop table if exists Appointment;

drop table if exists Admin;
drop table if exists Doctor;
drop table if exists Nurse;
drop table if exists Patient;

drop table if exists Person;
drop table if exists Address;

CREATE TABLE Address (
	address_id INTEGER not null AUTO_INCREMENT,
	street_address1 VARCHAR(50) not null,
	street_address2 VARCHAR(50),
	city VARCHAR(50) not null,
	state VARCHAR(25) not null,
	zip_code INTEGER not null,
	PRIMARY KEY(address_id)
);

CREATE TABLE Person( 
	person_id INTEGER not null AUTO_INCREMENT, 
	fname VARCHAR(30) not null,
	lname VARCHAR(30) not null,
	middle_initial CHAR(1) not null,
	DOB DATE not null,
	SSN INTEGER(9) not null,
	contact_phone VARCHAR(30) not null,
	contact_email VARCHAR(50) not null,
	mailing_address_id INTEGER not null,
	UNIQUE(SSN),
	PRIMARY KEY(person_id),
	FOREIGN KEY(mailing_address_id) REFERENCES Address(address_id) ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE RegisteredUser(
	user_id INTEGER NOT NULL AUTO_INCREMENT,
	user_name VARCHAR(30) NOT NULL,
	person_id INTEGER NOT NULL,
	PRIMARY KEY (user_id),
	FOREIGN KEY (person_id) REFERENCES Person(person_id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Patient(
	person_id INTEGER NOT NULL,
	PRIMARY KEY (person_id),
	FOREIGN KEY (person_id) REFERENCES Person(person_id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Doctor(
	person_id INTEGER NOT NULL,
	PRIMARY KEY (person_id),
	FOREIGN KEY (person_id) REFERENCES Person(person_id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Admin(
	person_id INTEGER NOT NULL,
	PRIMARY KEY (person_id),
	FOREIGN KEY (person_id) REFERENCES Person(person_id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Nurse(
	person_id INTEGER NOT NULL,
	PRIMARY KEY (person_id),
	FOREIGN KEY (person_id) REFERENCES Person(person_id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE UserPasswordStore(
	user_id INTEGER NOT NULL,
	password_salted_hashed VARCHAR(255) NOT NULL,
	password_hash VARCHAR(255) NOT NULL,
	PRIMARY KEY (user_id),
	FOREIGN KEY (user_id) REFERENCES RegisteredUser(user_id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Appointment(
	appointment_id INTEGER NOT NULL AUTO_INCREMENT,
	patient_id INTEGER NOT NULL,
	date_time TIMESTAMP NOT NULL,
	PRIMARY KEY(appointment_id),
	FOREIGN KEY(patient_id) REFERENCES Person(person_id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE FinalDiagnosis(
	appointment_id INTEGER NOT NULL,
	diagnosis_result TEXT NOT NULL,
	PRIMARY KEY(appointment_id),
	FOREIGN KEY(appointment_id) REFERENCES Appointment(appointment_id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Diagnosis(
	appointment_id INTEGER NOT NULL,
	diagnosis_description TEXT NOT NULL,
	PRIMARY KEY(appointment_id),
	FOREIGN KEY(appointment_id) REFERENCES Appointment(appointment_id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE AppointmentCheckup(
	appointment_id INTEGER NOT NULL,
	checkup_status VARCHAR(30) NOT NULL,
	checkup_details TEXT NOT NULL,
	PRIMARY KEY(appointment_id),
	FOREIGN KEY(appointment_id) REFERENCES Appointment(appointment_id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE LabTest(
	lab_test_id INTEGER NOT NULL AUTO_INCREMENT,
	is_available BOOL NOT NULL,
	test_cost DECIMAL(10, 2) NOT NULL,
	test_description VARCHAR(255) NOT NULL,
	PRIMARY KEY(lab_test_id)
);

CREATE TABLE LabTestOrder(
	lab_test_order_id INTEGER NOT NULL AUTO_INCREMENT,
	appointment_id INTEGER NOT NULL,
	lab_test_id INTEGER NOT NULL,
	date_to_perform DATE NOT NULL,
	test_description VARCHAR(255) NOT NULL,
	PRIMARY KEY(lab_test_order_id),
	FOREIGN KEY(lab_test_id) REFERENCES LabTest(lab_test_id) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY(appointment_id) REFERENCES Appointment(appointment_id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE LabTestResult(
	lab_test_order_id INTEGER NOT NULL,
	test_result TEXT NOT NULL,
	PRIMARY KEY(lab_test_order_id),
	FOREIGN KEY(lab_test_order_id) REFERENCES LabTestOrder(lab_test_order_id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Query(
	query_id INTEGER NOT NULL AUTO_INCREMENT,
	user_id INTEGER NOT NULL,
	query_type ENUM('admin', 'create', 'read', 'delete', 'search', 'update') NOT NULL,
	PRIMARY KEY(query_id),
	FOREIGN KEY(user_id) REFERENCES RegisteredUser(user_id)
);

CREATE TABLE AdminQuery(
	query_id INTEGER NOT NULL,
	raw_query TEXT NOT NULL,
	PRIMARY KEY(query_id),
	FOREIGN KEY(query_id) REFERENCES Query(query_id)
);

CREATE TABLE CreateQuery(
	query_id INTEGER NOT NULL,
	table_name VARCHAR(255) NOT NULL,
	csv_attribute_values TEXT NOT NULL,
	PRIMARY KEY(query_id),
	FOREIGN KEY(query_id) REFERENCES Query(query_id)
);

CREATE TABLE ReadQuery(
	query_id INTEGER NOT NULL,
	table_name VARCHAR(255) NOT NULL,
	csv_key_s TEXT NOT NULL,
	PRIMARY KEY(query_id),
	FOREIGN KEY(query_id) REFERENCES Query(query_id)
);

CREATE TABLE DeleteQuery(
	query_id INTEGER NOT NULL,
	table_name VARCHAR(255) NOT NULL,
	csv_key_s TEXT NOT NULL,
	PRIMARY KEY(query_id),
	FOREIGN KEY(query_id) REFERENCES Query(query_id)
);

CREATE TABLE SearchQuery(
	query_id INTEGER NOT NULL,
	table_name VARCHAR(255) NOT NULL,
	csv_attributes TEXT NOT NULL,
	csv_params TEXT NOT NULL,
	PRIMARY KEY(query_id),
	FOREIGN KEY(query_id) REFERENCES Query(query_id)
);

CREATE TABLE UpdateQuery(
	query_id INTEGER NOT NULL,
	table_name VARCHAR(255) NOT NULL,
	csv_key_s TEXT NOT NULL,
	csv_attributes TEXT NOT NULL,
	csv_values TEXT NOT NULL,
	PRIMARY KEY(query_id),
	FOREIGN KEY(query_id) REFERENCES Query(query_id)
);

INSERT INTO Address VALUES(1,'hamburger 42', null, 'Bunderful Yum', 'WA', 66666);
INSERT INTO Person VALUES(1,'frank', 'burg', 'f', '2020-5-5', 123456789, "0123456789", "uieh@grjnrg.eee", 1);
INSERT INTO RegisteredUser VALUES(1, 'aa bb', 1);
INSERT INTO UserPasswordStore VALUES(1, "123", "hash");
