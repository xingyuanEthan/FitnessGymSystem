package dao;

import db.DBUtil;
import entity.Equipment;

import java.sql.*;
import java.util.*;

public class EquipmentDAO {
    
    // 获取所有器材
    public List<Equipment> getAll() {
        List<Equipment> list = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT * FROM equipment ORDER BY id";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Equipment e = new Equipment();
                e.setId(rs.getInt("id"));
                e.setName(rs.getString("name"));
                e.setBrand(rs.getString("brand"));
                e.setModel(rs.getString("model"));
                e.setPurchaseDate(rs.getDate("purchase_date"));
                e.setPrice(rs.getDouble("price"));
                e.setStatus(rs.getString("status"));
                e.setDescription(rs.getString("description"));
                e.setMaintenanceRecord(rs.getString("maintenance_record"));
                e.setLastMaintenanceDate(rs.getDate("last_maintenance_date"));
                list.add(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // 根据ID获取器材
    public Equipment getById(int id) {
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT * FROM equipment WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Equipment e = new Equipment();
                e.setId(rs.getInt("id"));
                e.setName(rs.getString("name"));
                e.setBrand(rs.getString("brand"));
                e.setModel(rs.getString("model"));
                e.setPurchaseDate(rs.getDate("purchase_date"));
                e.setPrice(rs.getDouble("price"));
                e.setStatus(rs.getString("status"));
                e.setDescription(rs.getString("description"));
                e.setMaintenanceRecord(rs.getString("maintenance_record"));
                e.setLastMaintenanceDate(rs.getDate("last_maintenance_date"));
                return e;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // 添加器材
    public boolean addEquipment(Equipment equipment) {
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "INSERT INTO equipment (name, brand, model, purchase_date, price, status, description) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, equipment.getName());
            ps.setString(2, equipment.getBrand());
            ps.setString(3, equipment.getModel());
            ps.setDate(4, new java.sql.Date(equipment.getPurchaseDate().getTime()));
            ps.setDouble(5, equipment.getPrice());
            ps.setString(6, equipment.getStatus());
            ps.setString(7, equipment.getDescription());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // 更新器材信息
    public boolean updateEquipment(Equipment equipment) {
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "UPDATE equipment SET name=?, brand=?, model=?, purchase_date=?, price=?, status=?, description=?, maintenance_record=?, last_maintenance_date=? WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, equipment.getName());
            ps.setString(2, equipment.getBrand());
            ps.setString(3, equipment.getModel());
            ps.setDate(4, new java.sql.Date(equipment.getPurchaseDate().getTime()));
            ps.setDouble(5, equipment.getPrice());
            ps.setString(6, equipment.getStatus());
            ps.setString(7, equipment.getDescription());
            ps.setString(8, equipment.getMaintenanceRecord());
            if (equipment.getLastMaintenanceDate() != null) {
                ps.setDate(9, new java.sql.Date(equipment.getLastMaintenanceDate().getTime()));
            } else {
                ps.setNull(9, Types.DATE);
            }
            ps.setInt(10, equipment.getId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // 删除器材
    public boolean deleteEquipment(int id) {
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "DELETE FROM equipment WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // 根据名称搜索器材
    public List<Equipment> searchByName(String name) {
        List<Equipment> list = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT * FROM equipment WHERE name LIKE ? ORDER BY name";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Equipment e = new Equipment();
                e.setId(rs.getInt("id"));
                e.setName(rs.getString("name"));
                e.setBrand(rs.getString("brand"));
                e.setModel(rs.getString("model"));
                e.setPurchaseDate(rs.getDate("purchase_date"));
                e.setPrice(rs.getDouble("price"));
                e.setStatus(rs.getString("status"));
                e.setDescription(rs.getString("description"));
                e.setMaintenanceRecord(rs.getString("maintenance_record"));
                e.setLastMaintenanceDate(rs.getDate("last_maintenance_date"));
                list.add(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // 根据状态搜索器材
    public List<Equipment> searchByStatus(String status) {
        List<Equipment> list = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT * FROM equipment WHERE status = ? ORDER BY name";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Equipment e = new Equipment();
                e.setId(rs.getInt("id"));
                e.setName(rs.getString("name"));
                e.setBrand(rs.getString("brand"));
                e.setModel(rs.getString("model"));
                e.setPurchaseDate(rs.getDate("purchase_date"));
                e.setPrice(rs.getDouble("price"));
                e.setStatus(rs.getString("status"));
                e.setDescription(rs.getString("description"));
                e.setMaintenanceRecord(rs.getString("maintenance_record"));
                e.setLastMaintenanceDate(rs.getDate("last_maintenance_date"));
                list.add(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // 获取可用器材
    public List<Equipment> getAvailableEquipment() {
        List<Equipment> list = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT * FROM equipment WHERE status = 'available' ORDER BY name";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Equipment e = new Equipment();
                e.setId(rs.getInt("id"));
                e.setName(rs.getString("name"));
                e.setBrand(rs.getString("brand"));
                e.setModel(rs.getString("model"));
                e.setPurchaseDate(rs.getDate("purchase_date"));
                e.setPrice(rs.getDouble("price"));
                e.setStatus(rs.getString("status"));
                e.setDescription(rs.getString("description"));
                e.setMaintenanceRecord(rs.getString("maintenance_record"));
                e.setLastMaintenanceDate(rs.getDate("last_maintenance_date"));
                list.add(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // 更新器材状态
    public boolean updateEquipmentStatus(int id, String status) {
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "UPDATE equipment SET status = ? WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // 添加维修记录
    public boolean addMaintenanceRecord(int id, String record) {
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "UPDATE equipment SET maintenance_record = ?, last_maintenance_date = CURRENT_DATE WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, record);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
} 