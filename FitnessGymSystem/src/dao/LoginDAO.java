package dao;

import db.DBUtil;
import entity.Employee;
import entity.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginDAO {

    /**
     * Authenticates a user based on username and password.
     * It checks both the employees and members tables.
     *
     * @param username The username.
     * @param password The password.
     * @return An Employee or Member object if authentication is successful, otherwise null.
     */
    public Object login(String username, String password) {
        // First, try to log in as an employee
        String employeeSql = "SELECT * FROM employees WHERE username = ? AND password = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(employeeSql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Employee employee = new Employee();
                employee.setId(rs.getInt("id"));
                employee.setName(rs.getString("name"));
                employee.setUsername(rs.getString("username"));
                employee.setAge(rs.getInt("age"));
                employee.setGender(rs.getString("gender"));
                employee.setPosition(rs.getString("position"));
                employee.setPhone(rs.getString("phone"));
                employee.setEmail(rs.getString("email"));
                employee.setHireDate(rs.getDate("hire_date"));
                employee.setSalary(rs.getDouble("salary"));
                employee.setStatus(rs.getString("status"));
                return employee;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // If not an employee, try to log in as a member
        String memberSql = "SELECT * FROM members WHERE username = ? AND password = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(memberSql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Member member = new Member();
                member.setId(rs.getInt("id"));
                member.setName(rs.getString("name"));
                member.setUsername(rs.getString("username"));
                member.setGender(rs.getString("gender"));
                member.setAge(rs.getInt("age"));
                member.setPhone(rs.getString("phone"));
                member.setIdCard(rs.getString("id_card"));
                member.setLevel(rs.getString("level"));
                member.setRegistrationDate(rs.getDate("registration_date"));
                member.setStatus(rs.getString("status"));
                return member;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // Return null if no user is found
    }
} 