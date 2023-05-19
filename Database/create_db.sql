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
	id INT IDENTITY(1,1) PRIMARY KEY,
	[role] VARCHAR(20),
	[name] VARCHAR(50),
	avatar VARCHAR(100),
	email VARCHAR(45),
	[password] VARCHAR(45),
)

CREATE TABLE showroom (
	id INT IDENTITY(1,1) PRIMARY KEY,
	[name] VARCHAR(50),
	[address] VARCHAR(100),
	city VARCHAR(30),
	phone VARCHAR(10)
)

CREATE TABLE staff (
	id INT IDENTITY(1,1) PRIMARY KEY,
	[role] VARCHAR(20),
	showroom_id INT FOREIGN KEY REFERENCES showroom(id),
	[name] VARCHAR(50),
	avatar VARCHAR(100),
	email VARCHAR(45),
	phone VARCHAR(10),
	gender VARCHAR(20),
	[password] VARCHAR(45),
	[address] VARCHAR(100),
	dob Date
)

CREATE TABLE client (
	id INT IDENTITY(1,1) PRIMARY KEY,
	[role] VARCHAR(20),
	[name] VARCHAR(50),
	avatar VARCHAR(100),
	phone VARCHAR(10),
	email VARCHAR(45),
	[password] VARCHAR(45),
	[address] VARCHAR(100),
	gender VARCHAR(20),
	dob DATE,
	tax varchar(50)
)

CREATE TABLE car_description (
	id INT IDENTITY(1,1) PRIMARY KEY,
	color VARCHAR(20),
	fuel_type VARCHAR(50),
	HP SMALLINT,
	wheel_size SMALLINT,
	bought_year SMALLINT,
	width SMALLINT, 
	[length] SMALLINT,
	height SMALLINT,
	km_spend VARCHAR(50),
	manufactured_year SMALLINT,
	others VARCHAR(100)
)

CREATE TABLE car (
	id INT IDENTITY(1,1) PRIMARY KEY,
	car_description_id INT FOREIGN KEY REFERENCES car_description(id) UNIQUE,
	showroom_id INT FOREIGN KEY REFERENCES showroom(id),
	[name] VARCHAR(50),
	brand VARCHAR(50), 
	price BIGINT,
	[status] VARCHAR(20)
)

CREATE TABLE car_image (
	id INT IDENTITY(1,1) PRIMARY KEY,
	car_id INT FOREIGN KEY REFERENCES car(id),
	link VARCHAR(100)
)

CREATE TABLE post (
	id INT IDENTITY(1,1) PRIMARY KEY,
	car_id INT FOREIGN KEY REFERENCES car(id) UNIQUE,
	client_id INT FOREIGN KEY REFERENCES client(id),
	[description] VARCHAR(2000),
	created_at DATE
)

CREATE TABLE invoice (
	id INT IDENTITY(1,1) PRIMARY KEY,
	staff_id INT FOREIGN KEY REFERENCES staff(id),
	client_id INT FOREIGN KEY REFERENCES client(id),
	car_id INT FOREIGN KEY REFERENCES car(id) UNIQUE,
	total BIGINT,
	created_at DATE,
	[status] VARCHAR(20)
)

CREATE TABLE off_meeting (
	id INT IDENTITY(1,1) PRIMARY KEY,
	staff_id INT FOREIGN KEY REFERENCES staff(id),
	client_id INT FOREIGN KEY REFERENCES client(id),
	meeting_date DATE,
	meeting_type VARCHAR(30),
	[status] VARCHAR(20)
)

CREATE TABLE feedback (
	id INT IDENTITY(1,1) PRIMARY KEY,
	client_id INT FOREIGN KEY REFERENCES client(id),
	created_at DATE,
	[description] VARCHAR(2000)
)

