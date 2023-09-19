CREATE DATABASE swp391;
USE swp391;

CREATE TABLE users(
	id int auto_increment,
	user_name varchar(50),
	full_name nvarchar(100),
	email varchar(50),
	password varchar(50),
	phone varchar(11),
	address varchar(255),
	image varchar(255),
	id_role int,
	primary key(id)
);

CREATE TABLE role(
	id int auto_increment,
	name varchar(50),
	description varchar(255),
	
	primary key(id)
);


CREATE TABLE blog(
	id int auto_increment,
	title varchar(100),
	content text,
	image varchar(255),
	min_price double,
	max_price double ,
	status bit,
	create_date date,
	confirm bit,
	
	id_user_create int,
	id_blog_type int,
	
	primary key(id)
);

CREATE TABLE blog_type(
	id int auto_increment,
	name varchar(50),
	primary key (id)
);


CREATE TABLE booking(
	id int auto_increment,
	create_date date,
	total_price double,
	paying_method varchar(10),
	status bit,
	id_user int,
	id_blog int,
	primary key(id)


);

CREATE TABLE booking_history(
	id int auto_increment,
	description varchar(255),
	status bit,
	id_booking int,
	
	primary key(id)
);


CREATE TABLE comment(
	id int auto_increment,
	description text,
	create_date Date,
	rating int,
	id_user int,
	primary key(id)
);

CREATE TABLE user_blog_comment(
	id_blog int,
	id_user int,
	id_comment int,
	
	primary key(id_user,id_blog,id_comment)
);

ALTER TABLE users  ADD CONSTRAINT FK_id_role_user FOREIGN KEY (id_role) REFERENCES role(id);
ALTER TABLE blog ADD CONSTRAINT FK_id_user_blog FOREIGN KEY (id_user_create) REFERENCES users(id);
ALTER TABLE booking ADD CONSTRAINT FK_id_blog_booking FOREIGN KEY (id_blog) REFERENCES blog(id);
ALTER TABLE booking ADD CONSTRAINT FK_id_user_booking FOREIGN KEY (id_user) REFERENCES users(id);
ALTER TABLE booking_history ADD CONSTRAINT FK__booking_booking_history FOREIGN KEY (id_booking) REFERENCES booking(id);
ALTER TABLE comment ADD CONSTRAINT FK_id_user_comment FOREIGN KEY (id_user) REFERENCES users(id);
ALTER TABLE user_blog_comment ADD CONSTRAINT FK_id_user_user_blog_comment FOREIGN KEY (id_user) REFERENCES users(id);
ALTER TABLE user_blog_comment ADD CONSTRAINT FK_id_blog_user_blog_comment FOREIGN KEY (id_blog) REFERENCES blog(id);
ALTER TABLE user_blog_comment ADD CONSTRAINT FK_id_comment_user_blog_comment FOREIGN KEY (id_comment) REFERENCES comment(id);
ALTER TABLE blog ADD CONSTRAINT FK_blog_type_blog FOREIGN KEY (id_blog_type) REFERENCES blog_type(id);



