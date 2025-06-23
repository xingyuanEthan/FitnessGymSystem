// src/dao/MemberDAO.java
package dao;

import db.DBUtil;
import entity.Member;

import java.sql.*;
import java.util.*;

public class MemberDAO {
    
    // 获取所有会员
    public List<Member> getAll() {
        List<Member> members = new ArrayList<>();
        String sql = "SELECT * FROM members ORDER BY id";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Member member = new Member();
                member.setId(rs.getInt("id"));
                member.setName(rs.getString("name"));
                member.setGender(rs.getString("gender"));
                member.setAge(rs.getInt("age"));
                member.setPhone(rs.getString("phone"));
                member.setIdCard(rs.getString("id_card"));
                member.setLevel(rs.getString("level"));
                member.setRegistrationDate(rs.getDate("registration_date"));
                member.setStatus(rs.getString("status"));
                members.add(member);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return members;
    }
    
    // 根据ID获取会员
    public Member getById(int id) {
        String sql = "SELECT * FROM members WHERE id = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Member member = new Member();
                member.setId(rs.getInt("id"));
                member.setName(rs.getString("name"));
                member.setGender(rs.getString("gender"));
                member.setAge(rs.getInt("age"));
                member.setPhone(rs.getString("phone"));
                member.setIdCard(rs.getString("id_card"));
                member.setLevel(rs.getString("level"));
                member.setRegistrationDate(rs.getDate("registration_date"));
                member.setStatus(rs.getString("status"));
                return member;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // 添加会员
    public boolean addMember(Member member) {
        String sql = "INSERT INTO members (name, gender, age, phone, id_card, level, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, member.getName());
            stmt.setString(2, member.getGender());
            stmt.setInt(3, member.getAge());
            stmt.setString(4, member.getPhone());
            stmt.setString(5, member.getIdCard());
            stmt.setString(6, member.getLevel());
            stmt.setString(7, member.getStatus());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // 更新会员信息
    public boolean updateMember(Member member) {
        String sql = "UPDATE members SET name=?, gender=?, age=?, phone=?, id_card=?, level=?, status=? WHERE id=?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, member.getName());
            stmt.setString(2, member.getGender());
            stmt.setInt(3, member.getAge());
            stmt.setString(4, member.getPhone());
            stmt.setString(5, member.getIdCard());
            stmt.setString(6, member.getLevel());
            stmt.setString(7, member.getStatus());
            stmt.setInt(8, member.getId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // 删除会员
    public boolean deleteMember(int id) {
        String sql = "DELETE FROM members WHERE id = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // 根据姓名搜索会员
    public List<Member> searchByName(String name) {
        List<Member> members = new ArrayList<>();
        String sql = "SELECT * FROM members WHERE name LIKE ? ORDER BY id";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + name + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Member member = new Member();
                member.setId(rs.getInt("id"));
                member.setName(rs.getString("name"));
                member.setGender(rs.getString("gender"));
                member.setAge(rs.getInt("age"));
                member.setPhone(rs.getString("phone"));
                member.setIdCard(rs.getString("id_card"));
                member.setLevel(rs.getString("level"));
                member.setRegistrationDate(rs.getDate("registration_date"));
                member.setStatus(rs.getString("status"));
                members.add(member);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return members;
    }
    
    // 根据会员等级搜索
    public List<Member> searchByLevel(String level) {
        List<Member> list = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT * FROM members WHERE level = ? ORDER BY name";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, level);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Member m = new Member();
                m.setId(rs.getInt("id"));
                m.setName(rs.getString("name"));
                m.setGender(rs.getString("gender"));
                m.setAge(rs.getInt("age"));
                m.setPhone(rs.getString("phone"));
                m.setIdCard(rs.getString("id_card"));
                m.setLevel(rs.getString("level"));
                m.setRegistrationDate(rs.getDate("registration_date"));
                m.setStatus(rs.getString("status"));
                list.add(m);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // 根据姓名和等级排序
    public List<Member> getAllOrdered(String orderBy) {
        List<Member> list = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT * FROM members ORDER BY " + orderBy;
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Member m = new Member();
                m.setId(rs.getInt("id"));
                m.setName(rs.getString("name"));
                m.setGender(rs.getString("gender"));
                m.setAge(rs.getInt("age"));
                m.setPhone(rs.getString("phone"));
                m.setIdCard(rs.getString("id_card"));
                m.setLevel(rs.getString("level"));
                m.setRegistrationDate(rs.getDate("registration_date"));
                m.setStatus(rs.getString("status"));
                list.add(m);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
