CREATE database swp391;


/*DROP database swp391;*/


Use swp391;

CREATE TABLE users(
id int auto_increment primary key,
user_name varchar(50),
full_name nvarchar(100),
email varchar(50),
password varchar(255),
phone varchar(11),
address varchar(255),
image varchar(255),
id_role int
);

CREATE TABLE role(
id int auto_increment primary key,
name varchar(50),
description varchar(255)
);

CREATE TABLE blog(

id int auto_increment primary key,
title varchar(100),
content text,
image varchar(255),
price double,
reason nvarchar(255),
status bit,
create_date datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
confirm bit,
pet_type bit,

id_user_created int,
id_blog_type int,
id_pet_category int 
	


);

CREATE TABLE pet_type(
	id int auto_increment,
	type varchar(10),
	
	primary key(id)

);

CREATE TABLE blog_type(
id int auto_increment primary key,
name varchar(50)
);

CREATE TABLE booking(
id int auto_increment primary key,
total_price double,
paying_method varchar(10),
status bit,
create_date Date,
id_user int,
id_blog int
);

CREATE TABLE booking_history(
id int auto_increment primary key,
description varchar(255),
status bit,
id_booking int
);

CREATE TABLE comment(
id int auto_increment primary key,
description text,
create_date datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
rating int,
id_blog int,
id_user int
);

CREATE TABLE service_category(
    id int auto_increment primary key,
    name nvarchar(255)
);

CREATE TABLE service(
    id int auto_increment primary key,
    schedule nvarchar(255), 
    id_blog int,
    id_service_cate int
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

CREATE TABLE request(
	id int auto_increment primary key,
	create_date datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	id_blog int,
	id_user int	
);



CREATE TABLE invoice(
    id int auto_increment primary key,
    invoice_date datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    total_amount double,
    id_user int,
    id_blog int,
    FOREIGN KEY (id_user) REFERENCES users(id),
    FOREIGN KEY (id_blog) REFERENCES blog(id)
);




ALTER TABLE users ADD CONSTRAINT FK_id_role_user FOREIGN KEY (id_role) REFERENCES role(id);
ALTER TABLE blog ADD CONSTRAINT FK_id_user_blog FOREIGN KEY (id_user_created) REFERENCES users(id);
ALTER TABLE blog ADD CONSTRAINT FK_id_pet_category_blog FOREIGN KEY (id_pet_category) REFERENCES pet_category(id);
ALTER TABLE booking ADD CONSTRAINT FK_id_blog_booking FOREIGN KEY (id_blog) REFERENCES blog(id);
ALTER TABLE booking ADD CONSTRAINT FK_id_user_booking FOREIGN KEY (id_user) REFERENCES users(id);
ALTER TABLE booking_history ADD CONSTRAINT FK__booking_booking_history FOREIGN KEY (id_booking) REFERENCES booking(id);
ALTER TABLE comment ADD CONSTRAINT FK_id_user_comment FOREIGN KEY (id_user) REFERENCES users(id);
ALTER TABLE comment ADD CONSTRAINT FK_id_blog_comment FOREIGN KEY (id_blog) REFERENCES blog(id);
ALTER TABLE blog ADD CONSTRAINT FK_blog_type_blog FOREIGN KEY (id_blog_type) REFERENCES blog_type(id);



ALTER TABLE service ADD CONSTRAINT FK_id_blog_service FOREIGN KEY (id_blog) REFERENCES blog(id);
ALTER TABLE service ADD CONSTRAINT FK_id_service_cate_service_category FOREIGN KEY (id_service_cate) REFERENCES service_category(id);

ALTER TABLE request ADD CONSTRAINT FK_id_blog_request FOREIGN KEY (id_blog) REFERENCES blog(id);
ALTER TABLE request ADD CONSTRAINT FK_id_user_request FOREIGN KEY (id_user) REFERENCES users(id);
ALTER TABLE pet_category ADD CONSTRAINT FK_id_blog_pet_category FOREIGN KEY (id_blog) REFERENCES blog(id);
ALTER TABLE blog ADD CONSTRAINT FK_pet_category_blog FOREIGN KEY (id_pet_category) REFERENCES pet_category(id);




/*SELECT i.* FROM invoice i INNER JOIN blog b on b.id  = i.id_blog where i.id_user =1*/

SELECT u.id, u.user_name, COUNT(b.id) AS total_blogs
FROM users u
LEFT JOIN blog b ON u.id = b.id_user_created
GROUP BY u.id, u.user_name
ORDER BY total_blogs DESC
LIMIT 3;


SELECT COUNT(b) FROM blog b WHERE b.id_user_created = :userId