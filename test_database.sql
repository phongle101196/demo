drop database if exists test;
create database if not exists test
character set utf8mb4
collate utf8mb4_unicode_ci;
use test;

create table users(
	id 			varchar(36) primary key,
    username 	varchar(50) unique not null,
    email		varchar(100) unique not null,
    `password`	varchar(255) not null,
    phone		varchar(15) not null,
	first_name	varchar(50) not null,
    last_name	varchar(50) not null,
    create_date	datetime default current_timestamp,
    avatar_url	varchar(255) default 'avatar.png',
    `role`		enum('USER', 'POSTER', 'ADMIN') default 'USER'
);

-- create table categories(
-- 	id			int primary key auto_increment,
--     `name`		varchar(100) unique not null
-- );


-- create table posts(
-- 	id			int primary key auto_increment,
--     title		varchar(255) not null,
--     content		text not null,
--     price		decimal(12,2) not null,
--     area		decimal(6,2) not null,
--     address		varchar(100) not null,
--     create_date	datetime default current_timestamp,
--     user_id		char(36) not null,
--     category_id	int not null,
--     foreign key (user_id) 		references users(id),
--     foreign key (category_id)	references categories(id)
-- ); 

-- create table images(
-- 	id			int primary key auto_increment,
--     img_url		varchar(255) not null,
--     post_id		int not null,
--     foreign key (post_id) references posts(id)
-- );