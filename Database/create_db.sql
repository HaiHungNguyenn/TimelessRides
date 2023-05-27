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
	id VARCHAR(100) PRIMARY KEY,
	[role] NVARCHAR(20),
	[name] NVARCHAR(50),
	avatar VARCHAR(100),
	email VARCHAR(45),
	[password] VARCHAR(100),
)

CREATE TABLE showroom (
	id VARCHAR(100) PRIMARY KEY,
	[name] NVARCHAR(50),
	[address] NVARCHAR(100),
	city NVARCHAR(30),
	phone VARCHAR(10)
)

CREATE TABLE staff (
	id VARCHAR(100) PRIMARY KEY,
	[role] VARCHAR(20),
	showroom_id VARCHAR(100) FOREIGN KEY REFERENCES showroom(id),
	[name] NVARCHAR(50),
	avatar VARCHAR(100),
	email VARCHAR(45),
	phone VARCHAR(10),
	gender VARCHAR(20),
	[password] VARCHAR(100),
	[address] NVARCHAR(100),
	dob DATE,
	join_at DATE
)

CREATE TABLE client (
	id VARCHAR(100) PRIMARY KEY,
	[role] VARCHAR(20),
	[name] NVARCHAR(50),
	avatar VARCHAR(100),
	phone VARCHAR(10),
	email VARCHAR(45),
	[password] VARCHAR(100),
	[address] NVARCHAR(100),
	gender VARCHAR(20),
	dob DATE,
	join_at DATE,
	tax varchar(50)
)

CREATE TABLE car_description (
	id VARCHAR(100) PRIMARY KEY,
	color VARCHAR(20),
	fuel_type VARCHAR(50),
	no_of_seat SMALLINT,
	HP SMALLINT,
	wheel_size SMALLINT,
	bought_year SMALLINT,
	width SMALLINT, 
	[length] SMALLINT,
	height SMALLINT,
	km_spend VARCHAR(50),
	manufactured_year SMALLINT,
	others NVARCHAR(100)
)

CREATE TABLE car (
	id VARCHAR(100) PRIMARY KEY,
	car_description_id VARCHAR(100) FOREIGN KEY REFERENCES car_description(id) UNIQUE,
	showroom_id VARCHAR(100) FOREIGN KEY REFERENCES showroom(id),
	[name] VARCHAR(50),
	brand VARCHAR(50), 
	price BIGINT,
	[status] VARCHAR(20)
)

CREATE TABLE car_image (
	id VARCHAR(100) PRIMARY KEY,
	car_id VARCHAR(100) FOREIGN KEY REFERENCES car(id),
	link VARCHAR(300)
)

CREATE TABLE post (
	id VARCHAR(100) PRIMARY KEY,
	car_id VARCHAR(100) FOREIGN KEY REFERENCES car(id) UNIQUE,
	client_id VARCHAR(100) FOREIGN KEY REFERENCES client(id),
	[description] VARCHAR(2000),
	created_at DATE,
	[status] VARCHAR(20)
)

CREATE TABLE invoice (
	id VARCHAR(100) PRIMARY KEY,
	staff_id VARCHAR(100) FOREIGN KEY REFERENCES staff(id),
	client_id VARCHAR(100) FOREIGN KEY REFERENCES client(id),
	car_id VARCHAR(100) FOREIGN KEY REFERENCES car(id) UNIQUE,
	total BIGINT,
	created_at DATE,
	[status] VARCHAR(20)
)

CREATE TABLE off_meeting (
	id VARCHAR(100) PRIMARY KEY,
	staff_id VARCHAR(100) FOREIGN KEY REFERENCES staff(id),
	client_id VARCHAR(100) FOREIGN KEY REFERENCES client(id),
	meeting_date DATE,
	created_at DATE,
	[descrtiption] NVARCHAR(2000),
	[status] VARCHAR(20)
)

CREATE TABLE feedback (
	id VARCHAR(100) PRIMARY KEY,
	client_id VARCHAR(100) FOREIGN KEY REFERENCES client(id),
	created_at DATE,
	[description] NVARCHAR(2000)
)

