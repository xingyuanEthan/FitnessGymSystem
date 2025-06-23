package db;

import java.sql.*;

public class DatabaseInitializer {
    
    public static void main(String[] args) {
        initializeDatabase();
    }
    
    public static void initializeDatabase() {
        try (Connection conn = DBUtil.getConnection()) {
            // 创建会员表
            createMemberTable(conn);
            // 创建员工表
            createEmployeeTable(conn);
            // 创建健身器材表
            createEquipmentTable(conn);
            // 创建健身课程表
            createCourseTable(conn);
            // 创建会员卡类型表
            createMembershipLevelTable(conn);
            // 创建器材租借记录表
            createEquipmentRentalTable(conn);
            // 创建课程报名表
            createCourseEnrollmentTable(conn);
            
            System.out.println("数据库表创建成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void createMemberTable(Connection conn) throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS members (
                id INT PRIMARY KEY AUTO_INCREMENT,
                name VARCHAR(50) NOT NULL,
                gender VARCHAR(10) NOT NULL,
                age INT NOT NULL,
                phone VARCHAR(20) NOT NULL,
                id_card VARCHAR(18) UNIQUE NOT NULL,
                level VARCHAR(20) NOT NULL,
                registration_date DATE DEFAULT (CURRENT_DATE),
                status VARCHAR(20) DEFAULT 'active'
            )
        """;
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }
    
    private static void createEmployeeTable(Connection conn) throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS employees (
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
            )
        """;
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }
    
    private static void createEquipmentTable(Connection conn) throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS equipment (
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
            )
        """;
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }
    
    private static void createCourseTable(Connection conn) throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS courses (
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
            )
        """;
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }
    
    private static void createMembershipLevelTable(Connection conn) throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS membership_level (
                id INT PRIMARY KEY AUTO_INCREMENT,
                level_name VARCHAR(50) NOT NULL,
                discount_rate DECIMAL(3,2) DEFAULT 1.00,
                monthly_fee DECIMAL(10,2) NOT NULL,
                description TEXT
            )
        """;
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
        
        // 插入默认会员等级
        insertDefaultMembershipLevels(conn);
    }
    
    private static void insertDefaultMembershipLevels(Connection conn) throws SQLException {
        String sql = """
            INSERT IGNORE INTO membership_level (level_name, discount_rate, monthly_fee, description) VALUES
            ('普通会员', 1.00, 99.00, '基础健身服务'),
            ('银卡会员', 0.95, 199.00, '享受5%折扣'),
            ('金卡会员', 0.90, 299.00, '享受10%折扣'),
            ('钻石会员', 0.85, 499.00, '享受15%折扣，专属服务')
        """;
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }
    
    private static void createEquipmentRentalTable(Connection conn) throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS equipment_rentals (
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
            )
        """;
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }
    
    private static void createCourseEnrollmentTable(Connection conn) throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS course_enrollments (
                id INT PRIMARY KEY AUTO_INCREMENT,
                member_id INT NOT NULL,
                course_id INT NOT NULL,
                enrollment_date DATE DEFAULT (CURRENT_DATE),
                status VARCHAR(20) DEFAULT '已报名',
                notes TEXT,
                FOREIGN KEY (member_id) REFERENCES members(id),
                FOREIGN KEY (course_id) REFERENCES courses(id)
            )
        """;
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }
} 