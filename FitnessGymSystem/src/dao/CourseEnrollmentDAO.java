package dao;

import db.DBUtil;
import entity.CourseEnrollment;

import java.sql.*;
import java.util.*;

public class CourseEnrollmentDAO {
    
    // 报名课程
    public boolean enrollCourse(int memberId, int courseId) {
        try (Connection conn = DBUtil.getConnection()) {
            // 开始事务
            conn.setAutoCommit(false);
            try {
                // 检查课程是否存在且活跃
                String checkCourseSql = "SELECT status, max_capacity FROM course WHERE id = ? AND status = 'active'";
                PreparedStatement checkCoursePs = conn.prepareStatement(checkCourseSql);
                checkCoursePs.setInt(1, courseId);
                ResultSet courseRs = checkCoursePs.executeQuery();
                
                if (!courseRs.next()) {
                    conn.rollback();
                    return false; // 课程不存在或不活跃
                }
                
                // 检查是否已经报名
                String checkEnrollmentSql = "SELECT id FROM course_enrollment WHERE member_id = ? AND course_id = ? AND status = 'enrolled'";
                PreparedStatement checkEnrollmentPs = conn.prepareStatement(checkEnrollmentSql);
                checkEnrollmentPs.setInt(1, memberId);
                checkEnrollmentPs.setInt(2, courseId);
                ResultSet enrollmentRs = checkEnrollmentPs.executeQuery();
                
                if (enrollmentRs.next()) {
                    conn.rollback();
                    return false; // 已经报名
                }
                
                // 检查课程容量
                String countSql = "SELECT COUNT(*) FROM course_enrollment WHERE course_id = ? AND status = 'enrolled'";
                PreparedStatement countPs = conn.prepareStatement(countSql);
                countPs.setInt(1, courseId);
                ResultSet countRs = countPs.executeQuery();
                countRs.next();
                int currentEnrollment = countRs.getInt(1);
                
                if (currentEnrollment >= courseRs.getInt("max_capacity")) {
                    conn.rollback();
                    return false; // 课程已满
                }
                
                // 插入报名记录
                String insertSql = "INSERT INTO course_enrollment (member_id, course_id) VALUES (?, ?)";
                PreparedStatement insertPs = conn.prepareStatement(insertSql);
                insertPs.setInt(1, memberId);
                insertPs.setInt(2, courseId);
                int result = insertPs.executeUpdate();
                
                if (result > 0) {
                    conn.commit();
                    return true;
                } else {
                    conn.rollback();
                    return false;
                }
            } catch (Exception e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // 取消报名
    public boolean cancelEnrollment(int memberId, int courseId) {
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "UPDATE course_enrollment SET status = 'cancelled' WHERE member_id = ? AND course_id = ? AND status = 'enrolled'";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, memberId);
            ps.setInt(2, courseId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // 获取会员的报名记录
    public List<Map<String, Object>> getMemberEnrollments(int memberId) {
        List<Map<String, Object>> list = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection()) {
            String sql = """
                SELECT ce.*, c.name as course_name, c.description, c.duration, c.price, c.schedule, e.name as instructor_name
                FROM course_enrollment ce
                JOIN course c ON ce.course_id = c.id
                LEFT JOIN employee e ON c.instructor_id = e.id
                JOIN member m ON ce.member_id = m.id
                WHERE ce.member_id = ?
                ORDER BY ce.enrollment_date DESC
            """;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, memberId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> enrollment = new HashMap<>();
                enrollment.put("id", rs.getInt("id"));
                enrollment.put("memberId", rs.getInt("member_id"));
                enrollment.put("courseId", rs.getInt("course_id"));
                enrollment.put("courseName", rs.getString("course_name"));
                enrollment.put("description", rs.getString("description"));
                enrollment.put("duration", rs.getInt("duration"));
                enrollment.put("price", rs.getDouble("price"));
                enrollment.put("schedule", rs.getString("schedule"));
                enrollment.put("instructorName", rs.getString("instructor_name"));
                enrollment.put("enrollmentDate", rs.getTimestamp("enrollment_date"));
                enrollment.put("status", rs.getString("status"));
                list.add(enrollment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // 获取所有报名记录
    public List<Map<String, Object>> getAllEnrollments() {
        List<Map<String, Object>> list = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection()) {
            String sql = """
                SELECT ce.*, c.name as course_name, c.description, c.duration, c.price, c.schedule, e.name as instructor_name, m.name as member_name
                FROM course_enrollment ce
                JOIN course c ON ce.course_id = c.id
                LEFT JOIN employee e ON c.instructor_id = e.id
                JOIN member m ON ce.member_id = m.id
                ORDER BY ce.enrollment_date DESC
            """;
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> enrollment = new HashMap<>();
                enrollment.put("id", rs.getInt("id"));
                enrollment.put("memberId", rs.getInt("member_id"));
                enrollment.put("courseId", rs.getInt("course_id"));
                enrollment.put("memberName", rs.getString("member_name"));
                enrollment.put("courseName", rs.getString("course_name"));
                enrollment.put("description", rs.getString("description"));
                enrollment.put("duration", rs.getInt("duration"));
                enrollment.put("price", rs.getDouble("price"));
                enrollment.put("schedule", rs.getString("schedule"));
                enrollment.put("instructorName", rs.getString("instructor_name"));
                enrollment.put("enrollmentDate", rs.getTimestamp("enrollment_date"));
                enrollment.put("status", rs.getString("status"));
                list.add(enrollment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // 获取课程的报名人数
    public int getCourseEnrollmentCount(int courseId) {
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT COUNT(*) FROM course_enrollment WHERE course_id = ? AND status = 'enrolled'";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, courseId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    // 获取课程的报名会员列表
    public List<Map<String, Object>> getCourseEnrollments(int courseId) {
        List<Map<String, Object>> list = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection()) {
            String sql = """
                SELECT ce.*, m.name as member_name, m.phone, m.level
                FROM course_enrollment ce
                JOIN member m ON ce.member_id = m.id
                WHERE ce.course_id = ? AND ce.status = 'enrolled'
                ORDER BY ce.enrollment_date
            """;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, courseId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> enrollment = new HashMap<>();
                enrollment.put("id", rs.getInt("id"));
                enrollment.put("memberId", rs.getInt("member_id"));
                enrollment.put("memberName", rs.getString("member_name"));
                enrollment.put("phone", rs.getString("phone"));
                enrollment.put("level", rs.getString("level"));
                enrollment.put("enrollmentDate", rs.getTimestamp("enrollment_date"));
                enrollment.put("status", rs.getString("status"));
                list.add(enrollment);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<CourseEnrollment> getAll() {
        List<CourseEnrollment> enrollments = new ArrayList<>();
        String sql = "SELECT * FROM course_enrollments ORDER BY id DESC";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                CourseEnrollment enrollment = new CourseEnrollment();
                enrollment.setId(rs.getInt("id"));
                enrollment.setMemberId(rs.getInt("member_id"));
                enrollment.setCourseId(rs.getInt("course_id"));
                enrollment.setEnrollmentDate(rs.getString("enrollment_date"));
                enrollment.setStatus(rs.getString("status"));
                enrollment.setNotes(rs.getString("notes"));
                enrollments.add(enrollment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return enrollments;
    }
    
    public CourseEnrollment getById(int id) {
        String sql = "SELECT * FROM course_enrollments WHERE id = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                CourseEnrollment enrollment = new CourseEnrollment();
                enrollment.setId(rs.getInt("id"));
                enrollment.setMemberId(rs.getInt("member_id"));
                enrollment.setCourseId(rs.getInt("course_id"));
                enrollment.setEnrollmentDate(rs.getString("enrollment_date"));
                enrollment.setStatus(rs.getString("status"));
                enrollment.setNotes(rs.getString("notes"));
                return enrollment;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean addEnrollment(CourseEnrollment enrollment) {
        String sql = "INSERT INTO course_enrollments (member_id, course_id, enrollment_date, status, notes) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, enrollment.getMemberId());
            stmt.setInt(2, enrollment.getCourseId());
            stmt.setString(3, enrollment.getEnrollmentDate());
            stmt.setString(4, enrollment.getStatus());
            stmt.setString(5, enrollment.getNotes());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updateEnrollment(CourseEnrollment enrollment) {
        String sql = "UPDATE course_enrollments SET member_id=?, course_id=?, enrollment_date=?, status=?, notes=? WHERE id=?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, enrollment.getMemberId());
            stmt.setInt(2, enrollment.getCourseId());
            stmt.setString(3, enrollment.getEnrollmentDate());
            stmt.setString(4, enrollment.getStatus());
            stmt.setString(5, enrollment.getNotes());
            stmt.setInt(6, enrollment.getId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteEnrollment(int id) {
        String sql = "DELETE FROM course_enrollments WHERE id = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<CourseEnrollment> searchByMemberName(String memberName) {
        List<CourseEnrollment> enrollments = new ArrayList<>();
        String sql = """
            SELECT ce.* FROM course_enrollments ce 
            JOIN members m ON ce.member_id = m.id 
            WHERE m.name LIKE ? 
            ORDER BY ce.id DESC
        """;
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + memberName + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                CourseEnrollment enrollment = new CourseEnrollment();
                enrollment.setId(rs.getInt("id"));
                enrollment.setMemberId(rs.getInt("member_id"));
                enrollment.setCourseId(rs.getInt("course_id"));
                enrollment.setEnrollmentDate(rs.getString("enrollment_date"));
                enrollment.setStatus(rs.getString("status"));
                enrollment.setNotes(rs.getString("notes"));
                enrollments.add(enrollment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return enrollments;
    }
    
    public List<CourseEnrollment> searchByStatus(String status) {
        List<CourseEnrollment> enrollments = new ArrayList<>();
        String sql = "SELECT * FROM course_enrollments WHERE status = ? ORDER BY id DESC";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                CourseEnrollment enrollment = new CourseEnrollment();
                enrollment.setId(rs.getInt("id"));
                enrollment.setMemberId(rs.getInt("member_id"));
                enrollment.setCourseId(rs.getInt("course_id"));
                enrollment.setEnrollmentDate(rs.getString("enrollment_date"));
                enrollment.setStatus(rs.getString("status"));
                enrollment.setNotes(rs.getString("notes"));
                enrollments.add(enrollment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return enrollments;
    }
    
    public List<CourseEnrollment> getByMemberId(int memberId) {
        List<CourseEnrollment> enrollments = new ArrayList<>();
        String sql = "SELECT * FROM course_enrollments WHERE member_id = ? ORDER BY id DESC";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, memberId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                CourseEnrollment enrollment = new CourseEnrollment();
                enrollment.setId(rs.getInt("id"));
                enrollment.setMemberId(rs.getInt("member_id"));
                enrollment.setCourseId(rs.getInt("course_id"));
                enrollment.setEnrollmentDate(rs.getString("enrollment_date"));
                enrollment.setStatus(rs.getString("status"));
                enrollment.setNotes(rs.getString("notes"));
                enrollments.add(enrollment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return enrollments;
    }
    
    public List<CourseEnrollment> getByCourseId(int courseId) {
        List<CourseEnrollment> enrollments = new ArrayList<>();
        String sql = "SELECT * FROM course_enrollments WHERE course_id = ? ORDER BY id DESC";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, courseId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                CourseEnrollment enrollment = new CourseEnrollment();
                enrollment.setId(rs.getInt("id"));
                enrollment.setMemberId(rs.getInt("member_id"));
                enrollment.setCourseId(rs.getInt("course_id"));
                enrollment.setEnrollmentDate(rs.getString("enrollment_date"));
                enrollment.setStatus(rs.getString("status"));
                enrollment.setNotes(rs.getString("notes"));
                enrollments.add(enrollment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return enrollments;
    }
} 