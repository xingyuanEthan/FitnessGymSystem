-- Clean database script (English version)
USE fitness;

-- Drop tables with foreign key constraints first
DROP TABLE IF EXISTS equipment_rental;
DROP TABLE IF EXISTS course_enrollment;
DROP TABLE IF EXISTS equipment_rentals;
DROP TABLE IF EXISTS course_enrollments;

-- Drop other duplicate tables
DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS member;
DROP TABLE IF EXISTS course;
DROP TABLE IF EXISTS staff;

-- Drop existing tables (if exist)
DROP TABLE IF EXISTS courses;
DROP TABLE IF EXISTS equipment;
DROP TABLE IF EXISTS employees;
DROP TABLE IF EXISTS members;
DROP TABLE IF EXISTS membership_level;

-- Recreate correct table structure
-- Create members table
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

-- Create employees table
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

-- Create equipment table
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

-- Create courses table
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

-- Create membership level table
CREATE TABLE membership_level (
    id INT PRIMARY KEY AUTO_INCREMENT,
    level_name VARCHAR(50) NOT NULL,
    discount_rate DECIMAL(3,2) DEFAULT 1.00,
    monthly_fee DECIMAL(10,2) NOT NULL,
    description TEXT
);

-- Create equipment rentals table
CREATE TABLE equipment_rentals (
    id INT PRIMARY KEY AUTO_INCREMENT,
    member_id INT NOT NULL,
    equipment_id INT NOT NULL,
    rental_date DATE DEFAULT (CURRENT_DATE),
    expected_return_date DATE NOT NULL,
    actual_return_date DATE,
    status VARCHAR(20) DEFAULT 'rented',
    notes TEXT,
    FOREIGN KEY (member_id) REFERENCES members(id),
    FOREIGN KEY (equipment_id) REFERENCES equipment(id)
);

-- Create course enrollments table
CREATE TABLE course_enrollments (
    id INT PRIMARY KEY AUTO_INCREMENT,
    member_id INT NOT NULL,
    course_id INT NOT NULL,
    enrollment_date DATE DEFAULT (CURRENT_DATE),
    status VARCHAR(20) DEFAULT 'enrolled',
    notes TEXT,
    FOREIGN KEY (member_id) REFERENCES members(id),
    FOREIGN KEY (course_id) REFERENCES courses(id)
);

-- Insert default membership levels
INSERT INTO membership_level (level_name, discount_rate, monthly_fee, description) VALUES
('Regular Member', 1.00, 99.00, 'Basic fitness service'),
('Silver Member', 0.95, 199.00, '5% discount'),
('Gold Member', 0.90, 299.00, '10% discount'),
('Diamond Member', 0.85, 499.00, '15% discount, exclusive service');

-- Show created tables
SHOW TABLES; 