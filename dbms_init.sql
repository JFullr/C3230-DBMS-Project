drop table if exists UpdateQuery;
drop table if exists SearchQuery;
drop table if exists DeleteQuery;
drop table if exists SearchQuery;
drop table if exists ReadQuery;
drop table if exists AdminQuery;
drop table if exists Query;

drop table if exists UserPasswordStore;
drop table if exists RegisteredUser;

drop table if exists LabTest;
drop table if exists LabTestResult;
drop table if exists LabTestOrder;

drop table if exists AppointmentCheckup;
drop table if exists Diagnosis;
drop table if exists FinalDiagnosis;
drop table if exists Appointment;

drop table if exists Person;



CREATE TABLE Person( 
person_id INTEGER not null auto_increment, 
fname VARCHAR(30) not null,
lname VARCHAR(30) not null,
middle_initial CHAR(1) not null,
DOB DATE not null,
SSN INTEGER not null,
contact_phone VARCHAR(30) not null,



PRIMARY KEY(person_id)

);

