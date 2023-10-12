CREATE database swp391;
/*DROP database swp391;*/
Use swp391;

CREATE TABLE users(
	id int auto_increment,
	user_name varchar(50),
	full_name nvarchar(100),
	email varchar(50),
	password varchar(255),
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
	image_sidebar varchar(255), 
	price double,
	status bit,
	create_date datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	reason NVARCHAR(255),
	confirm bit,
	pet_type bit,
	
	id_user_created int,
	id_blog_type int,
	pet_category_id int,
	
	primary key(id)
);

CREATE TABLE blog_type(
	id int auto_increment,
	name varchar(50),
	
	primary key (id)
);


CREATE TABLE booking(
	id int auto_increment,
	total_price double,
	paying_method varchar(10),
	status bit,
	create_date Date,
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
	
	id_blog int,
	id_user int,
	
	primary key(id)
);



CREATE TABLE service_category(
    id int auto_increment,
    name nvarchar(255),
    
    primary key(id)
);

CREATE TABLE service(
    id int auto_increment,
    schedule nvarchar(255), 
    
    id_blog int,
    id_service_cate int,
    
    primary key(id)
);
CREATE TABLE pet_category (
    id int auto_increment,
    name varchar(50),
    breed varchar(100),
    age int,
    color varchar(50),
    weight double,
    
    id_blog int,
    
    primary key(id)
);


ALTER TABLE users  ADD CONSTRAINT FK_id_role_user FOREIGN KEY (id_role) REFERENCES role(id);
ALTER TABLE blog ADD CONSTRAINT FK_id_user_blog FOREIGN KEY (id_user_created) REFERENCES users(id);
ALTER TABLE booking ADD CONSTRAINT FK_id_blog_booking FOREIGN KEY (id_blog) REFERENCES blog(id);
ALTER TABLE booking ADD CONSTRAINT FK_id_user_booking FOREIGN KEY (id_user) REFERENCES users(id);
ALTER TABLE booking_history ADD CONSTRAINT FK__booking_booking_history FOREIGN KEY (id_booking) REFERENCES booking(id);
ALTER TABLE comment ADD CONSTRAINT FK_id_user_comment FOREIGN KEY (id_user) REFERENCES users(id);
ALTER TABLE comment ADD CONSTRAINT FK_id_blog_comment FOREIGN KEY (id_blog) REFERENCES blog(id);
ALTER TABLE blog ADD CONSTRAINT FK_blog_type_blog FOREIGN KEY (id_blog_type) REFERENCES blog_type(id);
ALTER TABLE service ADD CONSTRAINT FK_id_blog_service FOREIGN KEY (id_blog) REFERENCES blog(id);
ALTER TABLE service ADD CONSTRAINT FK_id_service_cate_service_category FOREIGN KEY (id_service_cate) REFERENCES service_category(id);
ALTER TABLE pet_category ADD CONSTRAINT FK_id_blog_pet_category FOREIGN KEY (id_blog) REFERENCES blog(id);
ALTER TABLE blog ADD CONSTRAINT FK_pet_category_blog FOREIGN KEY (pet_category_id) REFERENCES pet_category(id);








