CREATE DATABASE swp391;
USE swp391;

CREATE TABLE [user](
	id int IDENTITY(1,1),
	username varchar(50),
	fullname nvarchar(100),
	email varchar(50),
	password varchar(50),
	phone varchar(11),
	address varchar(255),
	image varchar(255),
	id_role int,
	primary key(id)
);

CREATE TABLE role(
	id int IDENTITY(1,1),
	name varchar(50),
	description varchar(255),
	
	primary key(id)
);


CREATE TABLE blog(
	id int IDENTITY(1,1) primary key,
	title varchar(100),
	content text,
	image varchar(255),
	blog_type varchar(50),
	fee float,
	status bit,
	create_date Date,
	id_user_create int,
	
	
);

CREATE TABLE exchange(
	id int IDENTITY(1,1) primary key,
	price float,
	confirm bit,
	id_blog int,
 
	
	
);

CREATE TABLE service(
	id int IDENTITY(1,1) primary key,
	min_price float,
	max_price float,
	start_time date,
	end_time date,
	id_blog int,
	 
);

CREATE TABLE booking(
	id_user int,
	id_blog int,
	id int IDENTITY(1,1),
	create_date date,
	total_price float,
	paying_method varchar(10),
	status bit,
	
	primary key(id,id_user,id_blog)


);

CREATE TABLE booking_history(
	id int IDENTITY(1,1),
	description varchar(255),
	status bit,
	id_booking int,
	
	primary key(id)
);


CREATE TABLE comment(
	id_user int,
	id_blog int,
	description varchar(255),
	date Date,
	
	primary key(id_user,id_blog)
);

ALTER TABLE [user]  ADD CONSTRAINT FK_id_role_user FOREIGN KEY (id_role) REFERENCES role(id);

ALTER TABLE blog ADD CONSTRAINT FK_id_user_blog FOREIGN KEY (id_user_create) REFERENCES [user](id);
ALTER TABLE exchange ADD CONSTRAINT FK_id_blog_exchange FOREIGN KEY (id_blog) REFERENCES blog(id);
ALTER TABLE service ADD CONSTRAINT FK_id_blog_service FOREIGN KEY (id_blog) REFERENCES blog(id);
ALTER TABLE booking ADD CONSTRAINT FK_id_blog_booking FOREIGN KEY (id_blog) REFERENCES blog(id);
ALTER TABLE booking ADD CONSTRAINT FK_id_user_booking FOREIGN KEY (id_user) REFERENCES [user](id);
ALTER TABLE booking_history ADD CONSTRAINT FK__bookingid_booking_history FOREIGN KEY (id_booking) REFERENCES booking(id);
ALTER TABLE comment ADD CONSTRAINT FK_id_blog_comment FOREIGN KEY (id_blog) REFERENCES blog(id);
ALTER TABLE comment ADD CONSTRAINT FK_id_user_comment FOREIGN KEY (id_user) REFERENCES [user](id);