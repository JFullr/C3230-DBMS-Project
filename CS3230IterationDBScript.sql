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
	gender ENUM('Male', 'Female', 'Other') not null,
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
	person_id INTEGER NOT NULL,
	date_time TIMESTAMP NOT NULL,
	doctor_id INTEGER NOT NULL,
	appointment_reason VARCHAR(255) NOT NULL,
	UNIQUE(date_time, doctor_id),
	PRIMARY KEY(appointment_id),
	FOREIGN KEY(person_id) REFERENCES Person(person_id) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY(doctor_id) REFERENCES Doctor(person_id) ON UPDATE CASCADE ON DELETE CASCADE
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
	systolic_pressure INTEGER NOT NULL,
	diastolic_pressure INTEGER NOT NULL,
	pulse INTEGER NOT NULL,
	weight DECIMAL(4, 2) NOT NULL,
	temperature DECIMAL(4, 2) NOT NULL,
	PRIMARY KEY(appointment_id),
	FOREIGN KEY(appointment_id) REFERENCES Appointment(appointment_id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE LabTest(
	lab_test_id INTEGER NOT NULL AUTO_INCREMENT,
	is_available BOOL NOT NULL,
	test_cost DECIMAL(10, 2) NOT NULL,
	test_name VARCHAR(255) NOT NULL,
	test_description TEXT NOT NULL,
	PRIMARY KEY(lab_test_id)
);

CREATE TABLE LabTestOrder(
	lab_test_order_id INTEGER NOT NULL AUTO_INCREMENT,
	appointment_id INTEGER NOT NULL,
	lab_test_id INTEGER NOT NULL,
	date_to_perform DATE NOT NULL,
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

INSERT INTO Address VALUES
(1,'hamburger 42', null, 'Bunderful Yum', 'Washington', 66666),
(2,'road 42', null, 'City a', 'Alaska', 54654),
(3,'circle 42', null, 'City b', 'Ohio', 56765),
(4,'street 42', null, 'City c', 'Hawaii', 98778),
(5,'triangle 42', null, 'City d', 'Alabama', 12323),
(6,'alley 42', null, 'City e', 'Georgia', 12345),
(7,'boardwalk 42', null, 'City f', 'New York', 54321);

INSERT INTO Person VALUES(1,'frank', 'burg', 'f', 'Male', '2020-5-5', 123456789, "0123456789", "uieh@grjnrg.eee", 1);
INSERT INTO RegisteredUser VALUES(1, 'aa bb', 1);
INSERT INTO UserPasswordStore VALUES(1, "123", "hash");

INSERT INTO Doctor VALUES(1);

INSERT INTO `Person` (`person_id`, `fname`, `lname`, `middle_initial`, `gender`, `DOB`, `SSN`, `contact_phone`, `contact_email`, `mailing_address_id`) VALUES
(2, 'Jane', 'Doe', 'M', 'Female', '1997-01-22', 11111111, '1111111111', 'jane@example.com', 2),
(3, 'John', 'Doe', 'M', 'Female', '1994-05-04', 111111111, '1111111112', 'anothercontactemail@example.com', 3),
(4, 'John', 'Doe', 'F', 'Male', '1999-03-04', 999999991, '4444444444', 'johnf@example.com', 4),
(5, 'Frank', 'Beans', 'q', 'Other', '2020-11-07', 344634463, '5465656451', 'beans@beans.beans', 5),
(6, 'test', 'add', 'a', 'Other', '2020-11-06', 545454634, '4865865865', 'a@a.com', 6),
(7, 'test', '1', 'a', 'Other', '2020-10-07', 786786787, '7865685678', 'a@a.com', 7);

INSERT INTO Patient VALUES
(2),
(3),
(4),
(5),
(6),
(7);

INSERT INTO LabTest VALUES
(1, TRUE,  666.66, 'Blood Test', 'Lab Test 1 -- does blood test'),
(2, FALSE, 777.77, 'Appendix Test', 'Lab Test 2 -- does appedix test'),
(3, TRUE,  888.88, 'Kidney Test', 'Lab Test 3 -- does kidney test');

INSERT INTO `Appointment` (`appointment_id`, `person_id`, `date_time`, `doctor_id`, `appointment_reason`) VALUES
(6, 6, '2019-10-03 21:15:00', 1, 'Repeat Checkup'),
(7, 6, '2020-10-01 16:45:00', 1, 'Repeat Checkup'),
(8, 6, '2021-10-01 11:15:00', 1, 'Repeat Checkup'),
(9, 6, '2020-10-31 15:00:00', 1, 'Repeat Checkup'),
(10, 6, '2020-12-15 05:00:00', 1, 'Repeat Checkup'),
(11, 2, '2020-11-10 11:30:00', 1, 'Repeat Checkup'),
(12, 2, '2020-11-21 19:02:00', 1, 'Repeat Checkup'),
(13, 2, '2020-11-07 18:01:00', 1, 'Repeat Checkup'),
(14, 2, '2020-10-30 13:02:00', 1, 'Repeat Checkup'),
(15, 2, '2020-11-05 11:00:00', 1, 'Repeat Checkup');