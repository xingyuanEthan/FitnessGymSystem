package dao;

import db.DBUtil;
import entity.Course;

import java.sql.*;
import java.util.*;

public class CourseDAO {
    
    // 获取所有课程
    public List<Course> getAll() {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM courses ORDER BY id";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Course course = new Course();
                course.setId(rs.getInt("id"));
                course.setName(rs.getString("name"));
                course.setDescription(rs.getString("description"));
                course.setInstructor(rs.getString("instructor"));
                course.setInstructorId(rs.getInt("instructor_id"));
                course.setInstructorName(rs.getString("instructor_name"));
                course.setDuration(rs.getInt("duration"));
                course.setPrice(rs.getDouble("price"));
                course.setMaxCapacity(rs.getInt("max_capacity"));
                course.setMaxStudents(rs.getInt("max_students"));
                course.setLevel(rs.getString("level"));
                course.setSchedule(rs.getString("schedule"));
                course.setStatus(rs.getString("status"));
                course.setStartDate(rs.getDate("start_date"));
                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }
    
    // 根据ID获取课程
    public Course getById(int id) {
        String sql = "SELECT * FROM courses WHERE id = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Course course = new Course();
                course.setId(rs.getInt("id"));
                course.setName(rs.getString("name"));
                course.setDescription(rs.getString("description"));
                course.setInstructor(rs.getString("instructor"));
                course.setInstructorId(rs.getInt("instructor_id"));
                course.setInstructorName(rs.getString("instructor_name"));
                course.setDuration(rs.getInt("duration"));
                course.setPrice(rs.getDouble("price"));
                course.setMaxCapacity(rs.getInt("max_capacity"));
                course.setMaxStudents(rs.getInt("max_students"));
                course.setLevel(rs.getString("level"));
                course.setSchedule(rs.getString("schedule"));
                course.setStatus(rs.getString("status"));
                course.setStartDate(rs.getDate("start_date"));
                return course;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // 添加课程
    public boolean addCourse(Course course) {
        String sql = "INSERT INTO courses (name, description, instructor, duration, price, max_students, level, status, start_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, course.getName());
            stmt.setString(2, course.getDescription());
            stmt.setString(3, course.getInstructor());
            stmt.setInt(4, course.getDuration());
            stmt.setDouble(5, course.getPrice());
            stmt.setInt(6, course.getMaxStudents());
            stmt.setString(7, course.getLevel());
            stmt.setString(8, course.getStatus());
            if (course.getStartDate() != null) {
                stmt.setDate(9, new java.sql.Date(course.getStartDate().getTime()));
            } else {
                stmt.setNull(9, Types.DATE);
            }
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // 更新课程信息
    public boolean updateCourse(Course course) {
        String sql = "UPDATE courses SET name=?, description=?, instructor=?, duration=?, price=?, max_students=?, level=?, status=?, start_date=? WHERE id=?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, course.getName());
            stmt.setString(2, course.getDescription());
            stmt.setString(3, course.getInstructor());
            stmt.setInt(4, course.getDuration());
            stmt.setDouble(5, course.getPrice());
            stmt.setInt(6, course.getMaxStudents());
            stmt.setString(7, course.getLevel());
            stmt.setString(8, course.getStatus());
            if (course.getStartDate() != null) {
                stmt.setDate(9, new java.sql.Date(course.getStartDate().getTime()));
            } else {
                stmt.setNull(9, Types.DATE);
            }
            stmt.setInt(10, course.getId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // 删除课程
    public boolean deleteCourse(int id) {
        String sql = "DELETE FROM courses WHERE id = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // 根据名称搜索课程
    public List<Course> searchByName(String name) {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM courses WHERE name LIKE ? ORDER BY id";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + name + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Course course = new Course();
                course.setId(rs.getInt("id"));
                course.setName(rs.getString("name"));
                course.setDescription(rs.getString("description"));
                course.setInstructorId(rs.getInt("instructor_id"));
                course.setDuration(rs.getInt("duration"));
                course.setPrice(rs.getDouble("price"));
                course.setMaxCapacity(rs.getInt("max_capacity"));
                course.setSchedule(rs.getString("schedule"));
                course.setStatus(rs.getString("status"));
                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }
    
    // 根据状态搜索课程
    public List<Course> searchByStatus(String status) {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM courses WHERE status = ? ORDER BY id";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Course course = new Course();
                course.setId(rs.getInt("id"));
                course.setName(rs.getString("name"));
                course.setDescription(rs.getString("description"));
                course.setInstructorId(rs.getInt("instructor_id"));
                course.setDuration(rs.getInt("duration"));
                course.setPrice(rs.getDouble("price"));
                course.setMaxCapacity(rs.getInt("max_capacity"));
                course.setSchedule(rs.getString("schedule"));
                course.setStatus(rs.getString("status"));
                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }
    
    // 根据教练搜索课程
    public List<Course> getByInstructorId(int instructorId) {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM courses WHERE instructor_id = ? ORDER BY id";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, instructorId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Course course = new Course();
                course.setId(rs.getInt("id"));
                course.setName(rs.getString("name"));
                course.setDescription(rs.getString("description"));
                course.setInstructorId(rs.getInt("instructor_id"));
                course.setDuration(rs.getInt("duration"));
                course.setPrice(rs.getDouble("price"));
                course.setMaxCapacity(rs.getInt("max_capacity"));
                course.setSchedule(rs.getString("schedule"));
                course.setStatus(rs.getString("status"));
                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }
    
    // 获取活跃课程
    public List<Course> getActiveCourses() {
        List<Course> list = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection()) {
            String sql = """
                SELECT c.*, e.name as instructor_name 
                FROM course c 
                LEFT JOIN employee e ON c.instructor_id = e.id 
                WHERE c.status = 'active' 
                ORDER BY c.name
            """;
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Course c = new Course();
                c.setId(rs.getInt("id"));
                c.setName(rs.getString("name"));
                c.setDescription(rs.getString("description"));
                c.setInstructorId(rs.getInt("instructor_id"));
                c.setInstructorName(rs.getString("instructor_name"));
                c.setDuration(rs.getInt("duration"));
                c.setPrice(rs.getDouble("price"));
                c.setMaxCapacity(rs.getInt("max_capacity"));
                c.setSchedule(rs.getString("schedule"));
                c.setStatus(rs.getString("status"));
                list.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
} 