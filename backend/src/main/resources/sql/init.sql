DROP DATABASE if exists leave_approval_system;
CREATE DATABASE leave_approval_system;
use leave_approval_system;
DROP TABLE IF EXISTS `users`;
CREATE TABLE users
(
    id           int primary key auto_increment,
    username     varchar(255) unique,
    password     varchar(255),
    name         varchar(255),
    role         varchar(255),
    access_token varchar(32) unique
);

CREATE TABLE leave_requests
(
    id     int(20)                                                                                                       NOT NULL AUTO_INCREMENT,
    status enum ('INSTRUCTOR_APPROVAL','ACADEMY_APPROVAL','TEACHER_APPROVAL') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'INSTRUCTOR_APPROVAL',
    PRIMARY KEY (`id`) USING BTREE
);

CREATE TABLE courses
(
    id         int primary key auto_increment,
    name       varchar(64),
    teacher_id int
);

# 添加admin用户
INSERT INTO users (id, username, password, name, role, access_token)
VALUES (1, 'admin', 'admin', 'Administrator', 'Admin', 'dMuCjDJ2O4on0OsR');

# 添加一个样例老师
INSERT INTO users (id, username, password, name, role, access_token)
VALUES (2, 'tony', '123456', 'Tony Bryan', 'Teacher', 'HMalJpcqQMIB3wP6');

# 添加样例学生
INSERT INTO users (id, username, password, name, role, access_token)
VALUES (3, 'nicole', '123456', 'Nicole LiLi', 'Student', 'pHIpbKbp15aH1gle');

