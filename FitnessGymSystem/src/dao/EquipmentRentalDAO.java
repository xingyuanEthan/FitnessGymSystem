package dao;

import db.DBUtil;
import entity.EquipmentRental;

import java.sql.*;
import java.util.*;

public class EquipmentRentalDAO {
    
    // 租借器材
    public boolean rentEquipment(int memberId, int equipmentId) {
        try (Connection conn = DBUtil.getConnection()) {
            // 开始事务
            conn.setAutoCommit(false);
            try {
                // 检查器材是否可用
                String checkSql = "SELECT status FROM equipment WHERE id = ? AND status = 'available'";
                PreparedStatement checkPs = conn.prepareStatement(checkSql);
                checkPs.setInt(1, equipmentId);
                ResultSet rs = checkPs.executeQuery();
                
                if (!rs.next()) {
                    conn.rollback();
                    return false; // 器材不可用
                }
                
                // 插入租借记录
                String insertSql = "INSERT INTO equipment_rental (member_id, equipment_id) VALUES (?, ?)";
                PreparedStatement insertPs = conn.prepareStatement(insertSql);
                insertPs.setInt(1, memberId);
                insertPs.setInt(2, equipmentId);
                int result = insertPs.executeUpdate();
                
                if (result > 0) {
                    // 更新器材状态为已租借
                    String updateSql = "UPDATE equipment SET status = 'rented' WHERE id = ?";
                    PreparedStatement updatePs = conn.prepareStatement(updateSql);
                    updatePs.setInt(1, equipmentId);
                    updatePs.executeUpdate();
                    
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
    
    // 归还器材
    public boolean returnEquipment(int memberId, int equipmentId) {
        try (Connection conn = DBUtil.getConnection()) {
            // 开始事务
            conn.setAutoCommit(false);
            try {
                // 更新租借记录
                String updateRentalSql = "UPDATE equipment_rental SET return_date = CURRENT_TIMESTAMP, status = 'returned' WHERE member_id = ? AND equipment_id = ? AND status = 'rented'";
                PreparedStatement updateRentalPs = conn.prepareStatement(updateRentalSql);
                updateRentalPs.setInt(1, memberId);
                updateRentalPs.setInt(2, equipmentId);
                int result = updateRentalPs.executeUpdate();
                
                if (result > 0) {
                    // 更新器材状态为可用
                    String updateEquipmentSql = "UPDATE equipment SET status = 'available' WHERE id = ?";
                    PreparedStatement updateEquipmentPs = conn.prepareStatement(updateEquipmentSql);
                    updateEquipmentPs.setInt(1, equipmentId);
                    updateEquipmentPs.executeUpdate();
                    
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
    
    // 获取会员的租借记录
    public List<Map<String, Object>> getMemberRentals(int memberId) {
        List<Map<String, Object>> list = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection()) {
            String sql = """
                SELECT er.*, e.name as equipment_name, e.model, m.name as member_name
                FROM equipment_rental er
                JOIN equipment e ON er.equipment_id = e.id
                JOIN member m ON er.member_id = m.id
                WHERE er.member_id = ?
                ORDER BY er.rental_date DESC
            """;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, memberId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> rental = new HashMap<>();
                rental.put("id", rs.getInt("id"));
                rental.put("memberId", rs.getInt("member_id"));
                rental.put("equipmentId", rs.getInt("equipment_id"));
                rental.put("memberName", rs.getString("member_name"));
                rental.put("equipmentName", rs.getString("equipment_name"));
                rental.put("model", rs.getString("model"));
                rental.put("rentalDate", rs.getTimestamp("rental_date"));
                rental.put("returnDate", rs.getTimestamp("return_date"));
                rental.put("status", rs.getString("status"));
                list.add(rental);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // 获取所有租借记录
    public List<Map<String, Object>> getAllRentals() {
        List<Map<String, Object>> list = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection()) {
            String sql = """
                SELECT er.*, e.name as equipment_name, e.model, m.name as member_name
                FROM equipment_rental er
                JOIN equipment e ON er.equipment_id = e.id
                JOIN member m ON er.member_id = m.id
                ORDER BY er.rental_date DESC
            """;
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> rental = new HashMap<>();
                rental.put("id", rs.getInt("id"));
                rental.put("memberId", rs.getInt("member_id"));
                rental.put("equipmentId", rs.getInt("equipment_id"));
                rental.put("memberName", rs.getString("member_name"));
                rental.put("equipmentName", rs.getString("equipment_name"));
                rental.put("model", rs.getString("model"));
                rental.put("rentalDate", rs.getTimestamp("rental_date"));
                rental.put("returnDate", rs.getTimestamp("return_date"));
                rental.put("status", rs.getString("status"));
                list.add(rental);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // 获取当前租借中的器材
    public List<Map<String, Object>> getCurrentRentals() {
        List<Map<String, Object>> list = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection()) {
            String sql = """
                SELECT er.*, e.name as equipment_name, e.model, m.name as member_name
                FROM equipment_rental er
                JOIN equipment e ON er.equipment_id = e.id
                JOIN member m ON er.member_id = m.id
                WHERE er.status = 'rented'
                ORDER BY er.rental_date DESC
            """;
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> rental = new HashMap<>();
                rental.put("id", rs.getInt("id"));
                rental.put("memberId", rs.getInt("member_id"));
                rental.put("equipmentId", rs.getInt("equipment_id"));
                rental.put("memberName", rs.getString("member_name"));
                rental.put("equipmentName", rs.getString("equipment_name"));
                rental.put("model", rs.getString("model"));
                rental.put("rentalDate", rs.getTimestamp("rental_date"));
                rental.put("returnDate", rs.getTimestamp("return_date"));
                rental.put("status", rs.getString("status"));
                list.add(rental);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<EquipmentRental> getAll() {
        List<EquipmentRental> rentals = new ArrayList<>();
        String sql = "SELECT * FROM equipment_rentals ORDER BY id DESC";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                EquipmentRental rental = new EquipmentRental();
                rental.setId(rs.getInt("id"));
                rental.setMemberId(rs.getInt("member_id"));
                rental.setEquipmentId(rs.getInt("equipment_id"));
                rental.setRentalDate(rs.getString("rental_date"));
                rental.setExpectedReturnDate(rs.getString("expected_return_date"));
                rental.setActualReturnDate(rs.getString("actual_return_date"));
                rental.setStatus(rs.getString("status"));
                rental.setNotes(rs.getString("notes"));
                rentals.add(rental);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rentals;
    }
    
    public EquipmentRental getById(int id) {
        String sql = "SELECT * FROM equipment_rentals WHERE id = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                EquipmentRental rental = new EquipmentRental();
                rental.setId(rs.getInt("id"));
                rental.setMemberId(rs.getInt("member_id"));
                rental.setEquipmentId(rs.getInt("equipment_id"));
                rental.setRentalDate(rs.getString("rental_date"));
                rental.setExpectedReturnDate(rs.getString("expected_return_date"));
                rental.setActualReturnDate(rs.getString("actual_return_date"));
                rental.setStatus(rs.getString("status"));
                rental.setNotes(rs.getString("notes"));
                return rental;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean addRental(EquipmentRental rental) {
        String sql = "INSERT INTO equipment_rentals (member_id, equipment_id, rental_date, expected_return_date, status, notes) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, rental.getMemberId());
            stmt.setInt(2, rental.getEquipmentId());
            stmt.setString(3, rental.getRentalDate());
            stmt.setString(4, rental.getExpectedReturnDate());
            stmt.setString(5, rental.getStatus());
            stmt.setString(6, rental.getNotes());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updateRental(EquipmentRental rental) {
        String sql = "UPDATE equipment_rentals SET member_id=?, equipment_id=?, rental_date=?, expected_return_date=?, actual_return_date=?, status=?, notes=? WHERE id=?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, rental.getMemberId());
            stmt.setInt(2, rental.getEquipmentId());
            stmt.setString(3, rental.getRentalDate());
            stmt.setString(4, rental.getExpectedReturnDate());
            stmt.setString(5, rental.getActualReturnDate());
            stmt.setString(6, rental.getStatus());
            stmt.setString(7, rental.getNotes());
            stmt.setInt(8, rental.getId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteRental(int id) {
        String sql = "DELETE FROM equipment_rentals WHERE id = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<EquipmentRental> searchByMemberName(String memberName) {
        List<EquipmentRental> rentals = new ArrayList<>();
        String sql = """
            SELECT er.* FROM equipment_rentals er 
            JOIN members m ON er.member_id = m.id 
            WHERE m.name LIKE ? 
            ORDER BY er.id DESC
        """;
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + memberName + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                EquipmentRental rental = new EquipmentRental();
                rental.setId(rs.getInt("id"));
                rental.setMemberId(rs.getInt("member_id"));
                rental.setEquipmentId(rs.getInt("equipment_id"));
                rental.setRentalDate(rs.getString("rental_date"));
                rental.setExpectedReturnDate(rs.getString("expected_return_date"));
                rental.setActualReturnDate(rs.getString("actual_return_date"));
                rental.setStatus(rs.getString("status"));
                rental.setNotes(rs.getString("notes"));
                rentals.add(rental);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rentals;
    }
    
    public List<EquipmentRental> searchByStatus(String status) {
        List<EquipmentRental> rentals = new ArrayList<>();
        String sql = "SELECT * FROM equipment_rentals WHERE status = ? ORDER BY id DESC";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                EquipmentRental rental = new EquipmentRental();
                rental.setId(rs.getInt("id"));
                rental.setMemberId(rs.getInt("member_id"));
                rental.setEquipmentId(rs.getInt("equipment_id"));
                rental.setRentalDate(rs.getString("rental_date"));
                rental.setExpectedReturnDate(rs.getString("expected_return_date"));
                rental.setActualReturnDate(rs.getString("actual_return_date"));
                rental.setStatus(rs.getString("status"));
                rental.setNotes(rs.getString("notes"));
                rentals.add(rental);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rentals;
    }
    
    public List<EquipmentRental> getByMemberId(int memberId) {
        List<EquipmentRental> rentals = new ArrayList<>();
        String sql = "SELECT * FROM equipment_rentals WHERE member_id = ? ORDER BY id DESC";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, memberId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                EquipmentRental rental = new EquipmentRental();
                rental.setId(rs.getInt("id"));
                rental.setMemberId(rs.getInt("member_id"));
                rental.setEquipmentId(rs.getInt("equipment_id"));
                rental.setRentalDate(rs.getString("rental_date"));
                rental.setExpectedReturnDate(rs.getString("expected_return_date"));
                rental.setActualReturnDate(rs.getString("actual_return_date"));
                rental.setStatus(rs.getString("status"));
                rental.setNotes(rs.getString("notes"));
                rentals.add(rental);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rentals;
    }
    
    public List<EquipmentRental> getByEquipmentId(int equipmentId) {
        List<EquipmentRental> rentals = new ArrayList<>();
        String sql = "SELECT * FROM equipment_rentals WHERE equipment_id = ? ORDER BY id DESC";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, equipmentId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                EquipmentRental rental = new EquipmentRental();
                rental.setId(rs.getInt("id"));
                rental.setMemberId(rs.getInt("member_id"));
                rental.setEquipmentId(rs.getInt("equipment_id"));
                rental.setRentalDate(rs.getString("rental_date"));
                rental.setExpectedReturnDate(rs.getString("expected_return_date"));
                rental.setActualReturnDate(rs.getString("actual_return_date"));
                rental.setStatus(rs.getString("status"));
                rental.setNotes(rs.getString("notes"));
                rentals.add(rental);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rentals;
    }
} 