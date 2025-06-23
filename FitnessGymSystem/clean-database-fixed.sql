-- 清理数据库脚本（修正版）
USE fitness;

-- 先删除有外键约束的表
DROP TABLE IF EXISTS equipment_rental;
DROP TABLE IF EXISTS course_enrollment;
DROP TABLE IF EXISTS equipment_rentals;
DROP TABLE IF EXISTS course_enrollments;

-- 删除其他重复的表
DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS member;
DROP TABLE IF EXISTS course;
DROP TABLE IF EXISTS staff;

-- 删除现有的表（如果存在）
DROP TABLE IF EXISTS courses;
DROP TABLE IF EXISTS equipment;
DROP TABLE IF EXISTS employees;
DROP TABLE IF EXISTS members;
DROP TABLE IF EXISTS membership_level;

-- 重新创建正确的表结构
-- 创建会员表
CREATE TABLE members (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    gender VARCHAR(10) NOT NULL,
    age INT NOT NULL,
    phone VARCHAR(20) NOT NULL,
    id_card VARCHAR(18) UNIQUE NOT NULL,
    level VARCHAR(20) NOT NULL,
    registration_date DATE DEFAULT (CURRENT_DATE),
    status VARCHAR(20) DEFAULT 'active'
);

-- 创建员工表
CREATE TABLE employees (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    age INT NOT NULL,
    gender VARCHAR(10) NOT NULL,
    position VARCHAR(50) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    email VARCHAR(100),
    hire_date DATE DEFAULT (CURRENT_DATE),
    salary DECIMAL(10,2),
    status VARCHAR(20) DEFAULT 'active'
);

-- 创建健身器材表
CREATE TABLE equipment (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    brand VARCHAR(100),
    model VARCHAR(100),
    purchase_date DATE NOT NULL,
    price DECIMAL(10,2),
    status VARCHAR(20) DEFAULT 'available',
    description TEXT,
    maintenance_record TEXT,
    last_maintenance_date DATE
);

-- 创建健身课程表
CREATE TABLE courses (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    instructor VARCHAR(100),
    instructor_id INT,
    instructor_name VARCHAR(100),
    duration INT NOT NULL,
    price DECIMAL(10,2),
    max_capacity INT DEFAULT 20,
    max_students INT DEFAULT 20,
    level VARCHAR(20),
    schedule VARCHAR(200),
    status VARCHAR(20) DEFAULT 'active',
    start_date DATE,
    FOREIGN KEY (instructor_id) REFERENCES employees(id)
);

-- 创建会员卡类型表
CREATE TABLE membership_level (
    id INT PRIMARY KEY AUTO_INCREMENT,
    level_name VARCHAR(50) NOT NULL,
    discount_rate DECIMAL(3,2) DEFAULT 1.00,
    monthly_fee DECIMAL(10,2) NOT NULL,
    description TEXT
);

-- 创建器材租借记录表
CREATE TABLE equipment_rentals (
    id INT PRIMARY KEY AUTO_INCREMENT,
    member_id INT NOT NULL,
    equipment_id INT NOT NULL,
    rental_date DATE DEFAULT (CURRENT_DATE),
    expected_return_date DATE NOT NULL,
    actual_return_date DATE,
    status VARCHAR(20) DEFAULT '租借中',
    notes TEXT,
    FOREIGN KEY (member_id) REFERENCES members(id),
    FOREIGN KEY (equipment_id) REFERENCES equipment(id)
);

-- 创建课程报名表
CREATE TABLE course_enrollments (
    id INT PRIMARY KEY AUTO_INCREMENT,
    member_id INT NOT NULL,
    course_id INT NOT NULL,
    enrollment_date DATE DEFAULT (CURRENT_DATE),
    status VARCHAR(20) DEFAULT '已报名',
    notes TEXT,
    FOREIGN KEY (member_id) REFERENCES members(id),
    FOREIGN KEY (course_id) REFERENCES courses(id)
);

-- 插入默认会员等级
INSERT INTO membership_level (level_name, discount_rate, monthly_fee, description) VALUES
('普通会员', 1.00, 99.00, '基础健身服务'),
('银卡会员', 0.95, 199.00, '享受5%折扣'),
('金卡会员', 0.90, 299.00, '享受10%折扣'),
('钻石会员', 0.85, 499.00, '享受15%折扣，专属服务');

-- 显示创建的表
SHOW TABLES; 