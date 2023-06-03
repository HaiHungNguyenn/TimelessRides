USE master
GO

IF EXISTS(SELECT NAME FROM master.dbo.sysdatabases WHERE NAME = 'CarShowroom')
	ALTER DATABASE CarShowroom SET OFFLINE WITH ROLLBACK IMMEDIATE
	ALTER DATABASE CarShowroom SET ONLINE
	DROP DATABASE CarShowroom
GO

CREATE DATABASE CarShowroom
GO

USE CarShowroom
GO

CREATE TABLE [admin] (
	id						VARCHAR(100) PRIMARY KEY,
	[role]					VARCHAR(50),
	[name]					NVARCHAR(50),
	avatar					VARCHAR(100),
	email					VARCHAR(50),
	[password]				VARCHAR(100),
)

CREATE TABLE showroom (
	id						VARCHAR(100) PRIMARY KEY,
	[name]					NVARCHAR(50),
	[address]				NVARCHAR(100),
	city					NVARCHAR(50),
	phone					VARCHAR(10)
)

CREATE TABLE staff (
	id						VARCHAR(100) PRIMARY KEY,
	[role]					VARCHAR(50),
	showroom_id				VARCHAR(100) FOREIGN KEY REFERENCES showroom(id),
	[name]					NVARCHAR(50),
	avatar					VARCHAR(100),
	email					VARCHAR(50),
	phone					VARCHAR(10),
	gender					VARCHAR(50),
	[password]				VARCHAR(100),
	[address]				NVARCHAR(100),
	dob						DATE,
	joined_at				DATE
)

CREATE TABLE client (
	id						VARCHAR(100) PRIMARY KEY,
	[role]					VARCHAR(50),
	[name]					NVARCHAR(50),
	avatar					VARCHAR(100),
	phone					VARCHAR(10),
	email					VARCHAR(50),
	[password]				VARCHAR(100),
	[address]				NVARCHAR(100),
	gender					VARCHAR(50),
	dob						DATE,
	join_at					DATE
)

CREATE TABLE car_description (
	id						VARCHAR(100) PRIMARY KEY,
	make					VARCHAR(100),
	model					VARCHAR(100),
	body_color				VARCHAR(50),
	interior_color			VARCHAR(50),
	interior_material		VARCHAR(50),
	body					VARCHAR(50),
	license_plate			VARCHAR(50),
	fuel_type				VARCHAR(50),
	transmission			VARCHAR(50),
	first_registration		VARCHAR(50),
	seats					INT,
	[power]					INT,
	engine_capacity			INT,
	co2_emission			INT,
	kms_driven				INT,
	others					NVARCHAR(2000)
)

CREATE TABLE car (
	id						VARCHAR(100) PRIMARY KEY,
	car_description_id		VARCHAR(100) FOREIGN KEY REFERENCES car_description(id) UNIQUE NOT NULL,
	showroom_id				VARCHAR(100) FOREIGN KEY REFERENCES showroom(id),
	[name]					VARCHAR(100),
	price					BIGINT,
	[status]				VARCHAR(50)
)

CREATE TABLE car_image (
	id						VARCHAR(100) PRIMARY KEY,
	car_id					VARCHAR(100) FOREIGN KEY REFERENCES car(id),
	content					VARBINARY(MAX)
)

CREATE TABLE post (
	id						VARCHAR(100) PRIMARY KEY,
	car_id					VARCHAR(100) FOREIGN KEY REFERENCES car(id) UNIQUE,
	client_id				VARCHAR(100) FOREIGN KEY REFERENCES client(id),
	[description]			VARCHAR(2000),
	post_date				DATE,
	post_time				TIME,
	[status]				VARCHAR(50)
)

CREATE TABLE invoice (
	id						VARCHAR(100) PRIMARY KEY,
	staff_id				VARCHAR(100) FOREIGN KEY REFERENCES staff(id),
	client_id				VARCHAR(100) FOREIGN KEY REFERENCES client(id),
	car_id					VARCHAR(100) FOREIGN KEY REFERENCES car(id) UNIQUE,
	total					BIGINT,
	create_date				DATE,
	create_time				TIME,
	tax						VARCHAR(100),
	[status]				VARCHAR(50),
	others					NVARCHAR(2000)
)

CREATE TABLE off_meeting (
	id						VARCHAR(100) PRIMARY KEY,
	staff_id				VARCHAR(100) FOREIGN KEY REFERENCES staff(id),
	client_id				VARCHAR(100) FOREIGN KEY REFERENCES client(id),
	meeting_date			DATE,
	meeting_time			TIME,
	create_date				DATE,
	create_time				TIME,
	[description]			NVARCHAR(2000),
	[status]				VARCHAR(50)
)

CREATE TABLE feedback (
	id						VARCHAR(100) PRIMARY KEY,
	client_id				VARCHAR(100) FOREIGN KEY REFERENCES client(id),
	created_at				DATE,
	[description]			NVARCHAR(2000)
)

