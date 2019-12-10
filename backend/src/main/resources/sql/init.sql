DROP DATABASE if exists leave_approval_system;
CREATE DATABASE leave_approval_system default charset utf8 collate utf8_unicode_ci;
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
    id     int(20)                                                                                                      NOT NULL AUTO_INCREMENT,
    creator_id int(20),
    creator_name varchar(255),
    assign_to_id int(20),
    submit_date date,
    course_id int,
    course_name varchar(255),
    leave_since date,
    leave_until date,
    period varchar(255),
    leave_days int,
    type varchar(255),
    reason varchar(255),
    status enum ('ASSIGNED_TO_INSTRUCTOR','ASSIGNED_TO_ACADEMY','ASSIGNED_TO_TEACHER', 'APPROVED', 'REJECT') CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT 'ASSIGNED_TO_INSTRUCTOR',
    PRIMARY KEY (`id`) USING BTREE
);

CREATE TABLE courses
(
    id         int primary key auto_increment,
    name       varchar(64),
    teacher_id int
);

# 添加admin用户, 代表学院
INSERT INTO users (id, username, password, name, role, access_token)
VALUES (1, 'admin', 'admin', 'Administrator', 'Admin', 'dMuCjDJ2O4on0OsR');

# 添加老师
INSERT INTO users (id, username, password, name, role, access_token)
VALUES (2, 'Zhang', '123456', '张老师', 'Teacher', 'HMalJpcqQMIB3wP2');

INSERT INTO users (id, username, password, name, role, access_token)
VALUES (3, 'Tang', '123456', '唐老师', 'Teacher', 'UwYJ0Pigfv4x5B8M');

INSERT INTO users (id, username, password, name, role, access_token)
VALUES (4, 'Smith', '123456', 'Smith老师', 'Teacher', 'A9nJxs04tlRPKeQh');

INSERT INTO users (id, username, password, name, role, access_token)
VALUES (5, 'Tony', '123456', 'Tony老师', 'Teacher', 'NaFyR9b47mJs6VLu');

INSERT INTO users (id, username, password, name, role, access_token)
VALUES (6, 'Wangxiuhua', '123456', '王老师', 'Teacher', 'B5cEySKj6eL2lFgo');

# 添加辅导员
INSERT INTO users (id, username, password, name, role, access_token)
VALUES (7, 'Liyitong', '123456', '李依桐', 'Instructor', 'P1MaEKbG7AYN8zuL');

# 添加样例学生
INSERT INTO users (id, username, password, name, role, access_token)
VALUES (8, 'liyuxiao', '123456', '李同学', 'Student', 'pHIpbKbp15aH1gle');

INSERT INTO users (id, username, password, name, role, access_token)
VALUES (9, 'yeqianfeng', '123456', '叶同学', 'Student', 'A4w19abvQleU8R7O');

# 添加课程信息
INSERT INTO courses (id, name, teacher_id) VALUES (1, '高等数学', 2);
INSERT INTO courses (id, name, teacher_id) VALUES (2, '线性代数', 3);
INSERT INTO courses (id, name, teacher_id) VALUES (3, '计算机网络', 4);
INSERT INTO courses (id, name, teacher_id) VALUES (4, '操作系统', 5);
INSERT INTO courses (id, name, teacher_id) VALUES (5, '编译原理', 6);


