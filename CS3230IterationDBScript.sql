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
	nurse_id INTEGER NOT NULL,
	PRIMARY KEY(appointment_id),
	FOREIGN KEY(appointment_id) REFERENCES Appointment(appointment_id) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY(nurse_id) REFERENCES Nurse(person_id) ON UPDATE CASCADE ON DELETE CASCADE
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
	UNIQUE(appointment_id,lab_test_id),
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


INSERT INTO Address VALUES
(1,'hamburger 42', null, 'Bunderful Yum', 'Washington', 66666),
(2,'road 42', null, 'City a', 'Alaska', 54654),
(3,'circle 42', null, 'City b', 'Ohio', 56765),
(4,'street 42', null, 'City c', 'Hawaii', 98778),
(5,'triangle 42', null, 'City d', 'Alabama', 12323),
(6,'alley 42', null, 'City e', 'Georgia', 12345),
(7,'boardwalk 42', null, 'City f', 'New York', 54321),
(8,'boardwalk 42', null, 'City f', 'New York', 56566),
(9,'dogwalk 345', null, 'City g', 'Oregon', 44444),
(10,'thrth 77', null, 'City h', 'Connecticut', 33333),
(11,'pot ato 420', null, 'City i', 'Mississippi', 222222),
(12,'grwgwgweg 37667', null, 'City j', 'Idaho', 11111),
(13,'wefwef 37667', null, 'City j', 'Idaho', 11111),
(14,'2352 35wfe 37667', null, 'City j', 'Idaho', 11111),
(15,'342 geg 37667', null, 'City j', 'Idaho', 11111),
(16,'jtjdjyjs 37667', null, 'City j', 'Idaho', 11111);


INSERT INTO Person VALUES(1,'frank', 'burg', 'f', 'Male', '2020-5-5', 123456789, "0123456789", "uieh@grjnrg.eee", 1);
INSERT INTO RegisteredUser VALUES(1, 'aa bb', 1);
INSERT INTO UserPasswordStore VALUES(1, "40bd001563085fc35165329ea1ff5c5ecbdbbeef", "hash");

INSERT INTO `Person` (`person_id`, `fname`, `lname`, `middle_initial`, `gender`, `DOB`, `SSN`, `contact_phone`, `contact_email`, `mailing_address_id`) VALUES
(2, 'Jane', 'Doe', 'M', 'Female', '1997-01-22', 111111112, '1111111111', 'jane@example.com', 2),
(3, 'John', 'Doe', 'M', 'Female', '1994-05-04', 111111111, '1111111112', 'anothercontactemail@example.com', 3),
(4, 'John', 'Doe', 'F', 'Male', '1999-03-04', 999999991, '4444444444', 'johnf@example.com', 4),
(5, 'Frank', 'Beans', 'q', 'Other', '2020-11-07', 344634463, '5465656451', 'beans@beans.beans', 5),
(6, 'test', 'add', 'a', 'Other', '2020-11-06', 545454634, '4865865865', 'a@a.com', 6),
(7, 'test', '1', 'b', 'Other', '2020-10-07', 111111131, '7865685678', 'b@a.com', 7),
(8, 'The', 'Potato', 'v', 'Other', '2020-10-07', 111311131, '9898989898', 'c@a.com', 8),
(9, 'Danger', 'Hotdog', 'c', 'Other', '2020-10-07', 111151131, '1212121212', 'd@a.com', 9),
(10, 'French', 'Caterpillar', 'd', 'Other', '2020-10-07', 111141531, '9999999998', 'e@a.com', 10),
(11, 'Orange', 'Green', 'h', 'Other', '2020-10-07', 113341531, '0000000008', 'f@a.com', 11),
(12, 'Yellow', 'Dagger', 'y', 'Other', '2020-10-07', 213341531, '1000000008', 'k@a.com', 12),
(13, 'Purple', 'McPurple', 't', 'Other', '2020-10-07', 313341531, '2000000008', 'j@a.com', 13),
(14, 'Brown', 'Whale', 'r', 'Other', '2020-10-07', 413341531, '3000000008', 'i@a.com', 14),
(15, 'Sludge', 'Farmer', 'e', 'Other', '2020-10-07', 513341531, '4000000008', 'h@a.com', 15);

INSERT INTO RegisteredUser VALUES(2, '999', 11);
INSERT INTO UserPasswordStore VALUES(2, "a9993e364706816aba3e25717850c26c9cd0d89d", "hash");

INSERT INTO Doctor VALUES
(1),
(8),
(9),
(10),
(11);


INSERT INTO Patient VALUES
(2),
(3),
(4),
(5),
(6),
(7);

INSERT INTO Nurse VALUES
(12),
(13),
(14),
(15);

INSERT INTO LabTest VALUES
(1, TRUE,  666.66, 'Blood Test', 'Lab Test 1 -- does blood test'),
(2, FALSE, 777.77, 'Appendix Test', 'Lab Test 2 -- does appedix test'),
(3, TRUE,  888.88, 'Kidney Test', 'Lab Test 3 -- does kidney test'),
(4, TRUE,  999.99, 'Brain Test', 'Lab Test 4 -- does brain test'),
(5, TRUE,  1111.11, 'Backwards Test', 'Lab Test 5 -- does backwards test'),
(6, TRUE,  2222.22, 'Allergy Test', 'Lab Test 6 -- does allergy test');

INSERT INTO `Appointment` (`appointment_id`, `person_id`, `date_time`, `doctor_id`, `appointment_reason`) VALUES
(6, 6, '2019-10-03 21:15:00', 1, 'Repeat Checkup'),
(7, 6, '2020-10-01 16:45:00', 8, 'Repeat Checkup'),
(8, 6, '2021-10-01 11:15:00', 9, 'Repeat Checkup'),
(9, 6, '2020-10-31 15:00:00', 8, 'Repeat Checkup'),
(10, 6, '2020-12-15 05:00:00', 1, 'Repeat Checkup'),
(11, 2, '2020-11-10 11:30:00', 9, 'Repeat Checkup'),
(12, 2, '2020-11-21 19:02:00', 8, 'Repeat Checkup'),
(13, 2, '2020-11-07 18:01:00', 10, 'Repeat Checkup'),
(14, 2, '2020-10-30 13:02:00', 11, 'Repeat Checkup'),
(15, 2, '2020-11-05 11:00:00', 11, 'Repeat Checkup');

INSERT INTO AppointmentCheckup VALUES
(6,60,60,60,60.0,60.0,12),
(7,70,70,70,70.0,70.0,13),
(8,80,80,80,80.0,80.0,14),
(9,90,90,90,90.0,90.0,15);

INSERT INTO `Admin` VALUES (1);

INSERT INTO `Diagnosis` (`appointment_id`, `diagnosis_description`) VALUES
(8, 'diagnosis test'),
(10, 'also diagnosis test');

INSERT INTO `LabTestOrder` (`lab_test_order_id`, `appointment_id`, `lab_test_id`, `date_to_perform`) VALUES
(1, 6, 4, '2020-10-12'),
(2, 6, 5, '2020-10-12'),
(3, 8, 5, '2020-12-23'),
(4, 8, 1, '2020-12-23'),
(5, 8, 3, '2020-12-23');

INSERT INTO `LabTestResult` (`lab_test_order_id`, `test_result`) VALUES
(1, 'result 0'),
(2, 'result 1'),
(3, 'forwards'),
(4, 'normal'),
(5, 'normal');

INSERT INTO `FinalDiagnosis` (`appointment_id`, `diagnosis_result`) VALUES
(6, 'normal');


drop procedure if exists `try_login`;
CREATE PROCEDURE `try_login`(IN `username` VARCHAR(255), IN `password` VARCHAR(255)) NOT DETERMINISTIC NO SQL SQL SECURITY DEFINER select distinct r.user_name, r.user_id, p.fname, p.lname from Person p, RegisteredUser r, UserPasswordStore ups where p.person_id = r.person_id and r.user_name = username and ups.password_salted_hashed = SHA1(password);

drop procedure if exists `get_lab_result`;
CREATE PROCEDURE `get_lab_result`(IN `lab_test_order_id__` INTEGER) SELECT * FROM LabTestResult WHERE lab_test_order_id = lab_test_order_id__;
									       
							
									       
drop procedure if exists `adminDateQuery`;
DELIMITER $$
CREATE DEFINER=`jfulle11`@`%` PROCEDURE `adminDateQuery`(IN `startDate` DATE, IN `endDate` DATE)
    READS SQL DATA
SELECT ap.date_time, p.person_id PatientID, CONCAT(p_p.lname, ' ', p_p.middle_initial, ' ', p_p.fname) PatientName, CONCAT(d_p.fname, ' ', d_p.middle_initial, ' ', d_p.lname) DoctorName, 
(SELECT ddd.diagnosis_description FROM Diagnosis ddd WHERE ddd.appointment_id = ap.appointment_id) AS DiagnosisDescription, 
(SELECT GROUP_CONCAT(lt.test_name)
    FROM LabTestOrder lo, LabTest lt 
    WHERE ap.appointment_id = lo.appointment_id AND lo.lab_test_id = lt.lab_test_id) AS TestsOrdered

FROM Appointment ap, Diagnosis dd, Doctor d, Patient p, Person p_p, Person d_p

WHERE ap.date_time >= startDate AND ap.date_time <= endDate AND ap.doctor_id = d.person_id AND d_p.person_id = d.person_id AND ap.person_id = p.person_id AND p.person_id = p_p.person_id

GROUP BY ap.date_time, d.person_id

ORDER BY ap.date_time ASC, PatientName DESC$$
DELIMITER ;
