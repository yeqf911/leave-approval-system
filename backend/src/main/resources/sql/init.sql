CREATE DATABASE leave_approval_system;
CREATE TABLE users
(
    id       int primary key auto_increment,
    username varchar(255) unique,
    password varchar(255),
    name     varchar(255),
    role     tinyint,
    access_token varchar(32) unique
);
