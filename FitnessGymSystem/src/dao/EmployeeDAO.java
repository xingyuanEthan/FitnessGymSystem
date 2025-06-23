package dao;

import db.DBUtil;
import entity.Employee;

import java.sql.*;
import java.util.*;

public class EmployeeDAO {
    
    // 获取所有员工
    public List<Employee> getAll() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees ORDER BY id";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setId(rs.getInt("id"));
                employee.setName(rs.getString("name"));
                employee.setAge(rs.getInt("age"));
                employee.setGender(rs.getString("gender"));
                employee.setPosition(rs.getString("position"));
                employee.setPhone(rs.getString("phone"));
                employee.setEmail(rs.getString("email"));
                employee.setHireDate(rs.getDate("hire_date"));
                employee.setSalary(rs.getDouble("salary"));
                employee.setStatus(rs.getString("status"));
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }
    
    // 根据ID获取员工
    public Employee getById(int id) {
        String sql = "SELECT * FROM employees WHERE id = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Employee employee = new Employee();
                employee.setId(rs.getInt("id"));
                employee.setName(rs.getString("name"));
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // 添加员工
    public boolean addEmployee(Employee employee) {
        String sql = "INSERT INTO employees (name, age, gender, position, phone, email, salary, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, employee.getName());
            stmt.setInt(2, employee.getAge());
            stmt.setString(3, employee.getGender());
            stmt.setString(4, employee.getPosition());
            stmt.setString(5, employee.getPhone());
            stmt.setString(6, employee.getEmail());
            stmt.setDouble(7, employee.getSalary());
            stmt.setString(8, employee.getStatus());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // 更新员工信息
    public boolean updateEmployee(Employee employee) {
        String sql = "UPDATE employees SET name=?, age=?, gender=?, position=?, phone=?, email=?, salary=?, status=? WHERE id=?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, employee.getName());
            stmt.setInt(2, employee.getAge());
            stmt.setString(3, employee.getGender());
            stmt.setString(4, employee.getPosition());
            stmt.setString(5, employee.getPhone());
            stmt.setString(6, employee.getEmail());
            stmt.setDouble(7, employee.getSalary());
            stmt.setString(8, employee.getStatus());
            stmt.setInt(9, employee.getId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // 删除员工
    public boolean deleteEmployee(int id) {
        String sql = "DELETE FROM employees WHERE id = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // 根据姓名搜索员工
    public List<Employee> searchByName(String name) {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees WHERE name LIKE ? ORDER BY id";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + name + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setId(rs.getInt("id"));
                employee.setName(rs.getString("name"));
                employee.setGender(rs.getString("gender"));
                employee.setPosition(rs.getString("position"));
                employee.setPhone(rs.getString("phone"));
                employee.setEmail(rs.getString("email"));
                employee.setHireDate(rs.getDate("hire_date"));
                employee.setSalary(rs.getDouble("salary"));
                employee.setStatus(rs.getString("status"));
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }
    
    // 根据职位搜索员工
    public List<Employee> searchByPosition(String position) {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees WHERE position LIKE ? ORDER BY id";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + position + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setId(rs.getInt("id"));
                employee.setName(rs.getString("name"));
                employee.setGender(rs.getString("gender"));
                employee.setPosition(rs.getString("position"));
                employee.setPhone(rs.getString("phone"));
                employee.setEmail(rs.getString("email"));
                employee.setHireDate(rs.getDate("hire_date"));
                employee.setSalary(rs.getDouble("salary"));
                employee.setStatus(rs.getString("status"));
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }
    
    // 获取所有教练
    public List<Employee> getAllInstructors() {
        List<Employee> list = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT * FROM employee WHERE position LIKE '%教练%' AND status = 'active' ORDER BY name";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Employee e = new Employee();
                e.setId(rs.getInt("id"));
                e.setName(rs.getString("name"));
                e.setGender(rs.getString("gender"));
                e.setPosition(rs.getString("position"));
                e.setPhone(rs.getString("phone"));
                e.setEmail(rs.getString("email"));
                e.setHireDate(rs.getDate("hire_date"));
                e.setSalary(rs.getDouble("salary"));
                e.setStatus(rs.getString("status"));
                list.add(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
} 