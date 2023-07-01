DROP DATABASE IF EXISTS payvisor;

CREATE DATABASE payvisor;

USE payvisor;

DROP TABLE IF EXISTS PaymentsData;

CREATE TABLE PaymentsData (
	PaymentsDataID INT PRIMARY KEY AUTO_INCREMENT,
    TransactionID VARCHAR(50) UNIQUE,
    FirstName VARCHAR(50),
    LastName VARCHAR(50),
    EmailID VARCHAR(60),
    Address VARCHAR(200),
    Country VARCHAR(100),
    Amount DECIMAL(10, 2) default 0,
    Currency VARCHAR(10),
    PaymentMethod VARCHAR(50),
    PaymentLocation VARCHAR(50),
    TransactionStatus VARCHAR(20),
    FailureReason VARCHAR(100),
    ErrorCode VARCHAR(10),
    TransactionDateTime DATETIME
);


DROP TABLE IF EXISTS BucketsData;

CREATE TABLE BucketsData (
    BucketName VARCHAR(100) PRIMARY KEY,
    BucketDescription VARCHAR(1500),
    Tags VARCHAR(1000),
	Severity ENUM('High', 'Medium', 'Low') DEFAULT 'Low',
    CreatedDateTime DATETIME,
	UpdatedDateTime DATETIME
);


DROP TABLE IF EXISTS PaymentFailuresData;

CREATE TABLE PaymentFailuresData (
	PaymentFailuresDataID INT PRIMARY KEY AUTO_INCREMENT,
    TransactionID VARCHAR(50) UNIQUE,
    FirstName VARCHAR(50),
    LastName VARCHAR(50),
    EmailID VARCHAR(60),
    Country VARCHAR(100),
    Amount DECIMAL(10, 2) default 0,
    Currency VARCHAR(10),
    PaymentMethod VARCHAR(50),
    PaymentLocation VARCHAR(50),
    TransactionStatus VARCHAR(20),
    FailureReason VARCHAR(100),
    BucketName VARCHAR(100),
    TransactionDateTime DATETIME,
    Timestamp DATETIME default CURRENT_TIMESTAMP,
	FOREIGN KEY (BucketName) REFERENCES BucketsData(BucketName)
);


DROP TABLE IF EXISTS BucketEmailTemplates;

CREATE TABLE BucketEmailTemplates (
	BucketEmailTemplateID INT PRIMARY KEY AUTO_INCREMENT,
    EmailTemplateName VARCHAR(150),
    BucketName VARCHAR(100),
    EmailTemplateReasons VARCHAR(1000),
    SendEmail ENUM('Yes', 'No') DEFAULT 'Yes',
    UseTemplateReasons ENUM('Yes', 'No') DEFAULT 'Yes',
    CreatedDateTime DATETIME,
    UpdatedDateTime DATETIME,
	FOREIGN KEY (BucketName) REFERENCES BucketsData(BucketName)
);

DROP TABLE IF EXISTS TrainMLFailureBucketsData;

CREATE TABLE TrainMLFailureBucketsData (
	FailureReason VARCHAR(100) UNIQUE,
	BucketName VARCHAR(100),
	FOREIGN KEY (BucketName) REFERENCES BucketsData(BucketName)
);

DROP TABLE IF EXISTS Reports;

CREATE TABLE Reports (
	ReportID INT PRIMARY KEY AUTO_INCREMENT,
    ReportName VARCHAR(100),
    ReportQuery VARCHAR(1000),
    QueryJson VARCHAR(1000),
	ChartType VARCHAR(20),
	ReportType VARCHAR(20),
    CreatedDateTime DATETIME,
    UpdatedDateTime DATETIME
);

DROP TABLE IF EXISTS Dashboards;

CREATE TABLE Dashboards (
	DashboardID INT PRIMARY KEY AUTO_INCREMENT,
    DashboardName VARCHAR(100),
    ReportID INT,
	DashboardType VARCHAR(20),
    CreatedDateTime DATETIME,
    UpdatedDateTime DATETIME,
	FOREIGN KEY (ReportID) REFERENCES Reports(ReportID)
);