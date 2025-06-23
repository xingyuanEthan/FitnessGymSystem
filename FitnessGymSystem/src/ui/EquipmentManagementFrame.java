package ui;

import dao.EquipmentDAO;
import entity.Equipment;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EquipmentManagementFrame extends JFrame {
    
    private EquipmentDAO equipmentDAO;
    private JTable equipmentTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JComboBox<String> statusComboBox;
    
    public EquipmentManagementFrame() {
        equipmentDAO = new EquipmentDAO();
        initFrame();
        initComponents();
        loadEquipmentData();
    }
    
    private void initFrame() {
        setTitle("器材管理");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        
        // 创建标题面板
        JPanel titlePanel = createTitlePanel();
        add(titlePanel, BorderLayout.NORTH);
        
        // 创建搜索面板
        JPanel searchPanel = createSearchPanel();
        add(searchPanel, BorderLayout.CENTER);
        
        // 创建表格面板
        JPanel tablePanel = createTablePanel();
        add(tablePanel, BorderLayout.SOUTH);
    }
    
    private JPanel createTitlePanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(240, 240, 240));
        panel.setPreferredSize(new Dimension(1000, 60));
        panel.setLayout(new BorderLayout());
        
        JLabel titleLabel = new JLabel("健身器材管理", SwingConstants.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLACK);
        panel.add(titleLabel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createSearchPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // 搜索区域
        JLabel searchLabel = new JLabel("搜索器材:");
        searchLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(searchLabel, gbc);
        
        searchField = new JTextField(20);
        searchField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(searchField, gbc);
        
        JButton searchButton = new JButton("搜索");
        searchButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        searchButton.setBackground(new Color(245, 245, 245));
        searchButton.setForeground(Color.BLACK);
        searchButton.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        searchButton.addActionListener(e -> searchEquipment());
        gbc.gridx = 2;
        gbc.gridy = 0;
        panel.add(searchButton, gbc);
        
        // 状态筛选
        JLabel statusLabel = new JLabel("状态筛选:");
        statusLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(statusLabel, gbc);
        
        statusComboBox = new JComboBox<>(new String[]{"全部", "可用", "维护中", "已租借"});
        statusComboBox.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        statusComboBox.addActionListener(e -> filterByStatus());
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(statusComboBox, gbc);
        
        JButton resetButton = new JButton("重置");
        resetButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        resetButton.setBackground(new Color(245, 245, 245));
        resetButton.setForeground(Color.BLACK);
        resetButton.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        resetButton.addActionListener(e -> resetSearch());
        gbc.gridx = 2;
        gbc.gridy = 1;
        panel.add(resetButton, gbc);
        
        // 操作按钮区域
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setOpaque(false);
        
        JButton addButton = new JButton("添加器材");
        addButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        addButton.setBackground(new Color(245, 245, 245));
        addButton.setForeground(Color.BLACK);
        addButton.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        addButton.addActionListener(e -> addEquipment());
        buttonPanel.add(addButton);
        
        JButton editButton = new JButton("编辑器材");
        editButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        editButton.setBackground(new Color(245, 245, 245));
        editButton.setForeground(Color.BLACK);
        editButton.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        editButton.addActionListener(e -> editEquipment());
        buttonPanel.add(editButton);
        
        JButton deleteButton = new JButton("删除器材");
        deleteButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        deleteButton.setBackground(new Color(245, 245, 245));
        deleteButton.setForeground(Color.BLACK);
        deleteButton.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        deleteButton.addActionListener(e -> deleteEquipment());
        buttonPanel.add(deleteButton);
        
        JButton refreshButton = new JButton("刷新");
        refreshButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        refreshButton.setBackground(new Color(245, 245, 245));
        refreshButton.setForeground(Color.BLACK);
        refreshButton.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        refreshButton.addActionListener(e -> loadEquipmentData());
        buttonPanel.add(refreshButton);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        panel.add(buttonPanel, gbc);
        
        return panel;
    }
    
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        
        // 创建表格模型
        String[] columnNames = {"ID", "器材名称", "型号", "购买日期", "价格", "状态", "维护记录", "最后维护日期"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        equipmentTable = new JTable(tableModel);
        equipmentTable.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        equipmentTable.getTableHeader().setFont(new Font("微软雅黑", Font.BOLD, 12));
        equipmentTable.setRowHeight(25);
        equipmentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(equipmentTable);
        scrollPane.setPreferredSize(new Dimension(960, 300));
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void loadEquipmentData() {
        tableModel.setRowCount(0);
        List<Equipment> equipments = equipmentDAO.getAll();
        for (Equipment equipment : equipments) {
            Object[] row = {
                equipment.getId(),
                equipment.getName(),
                equipment.getModel(),
                equipment.getPurchaseDate(),
                equipment.getPrice(),
                equipment.getStatus(),
                equipment.getMaintenanceRecord(),
                equipment.getLastMaintenanceDate()
            };
            tableModel.addRow(row);
        }
    }
    
    private void searchEquipment() {
        String searchText = searchField.getText().trim();
        if (searchText.isEmpty()) {
            loadEquipmentData();
            return;
        }
        
        tableModel.setRowCount(0);
        List<Equipment> equipments = equipmentDAO.searchByName(searchText);
        for (Equipment equipment : equipments) {
            Object[] row = {
                equipment.getId(),
                equipment.getName(),
                equipment.getModel(),
                equipment.getPurchaseDate(),
                equipment.getPrice(),
                equipment.getStatus(),
                equipment.getMaintenanceRecord(),
                equipment.getLastMaintenanceDate()
            };
            tableModel.addRow(row);
        }
    }
    
    private void filterByStatus() {
        String selectedStatus = (String) statusComboBox.getSelectedItem();
        if ("全部".equals(selectedStatus)) {
            loadEquipmentData();
            return;
        }
        
        tableModel.setRowCount(0);
        List<Equipment> equipments = equipmentDAO.searchByStatus(selectedStatus);
        for (Equipment equipment : equipments) {
            Object[] row = {
                equipment.getId(),
                equipment.getName(),
                equipment.getModel(),
                equipment.getPurchaseDate(),
                equipment.getPrice(),
                equipment.getStatus(),
                equipment.getMaintenanceRecord(),
                equipment.getLastMaintenanceDate()
            };
            tableModel.addRow(row);
        }
    }
    
    private void resetSearch() {
        searchField.setText("");
        statusComboBox.setSelectedIndex(0);
        loadEquipmentData();
    }
    
    private void addEquipment() {
        EquipmentDialog dialog = new EquipmentDialog(this, "添加器材", null);
        dialog.setVisible(true);
        
        if (dialog.isConfirmed()) {
            loadEquipmentData();
        }
    }
    
    private void editEquipment() {
        int selectedRow = equipmentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请选择要编辑的器材", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int equipmentId = (Integer) tableModel.getValueAt(selectedRow, 0);
        Equipment equipment = equipmentDAO.getById(equipmentId);
        
        if (equipment != null) {
            EquipmentDialog dialog = new EquipmentDialog(this, "编辑器材", equipment);
            dialog.setVisible(true);
            
            if (dialog.isConfirmed()) {
                loadEquipmentData();
            }
        } else {
            JOptionPane.showMessageDialog(this, "无法获取器材信息", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteEquipment() {
        int selectedRow = equipmentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请选择要删除的器材", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int result = JOptionPane.showConfirmDialog(this, "确定要删除选中的器材吗？", "确认删除", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            int equipmentId = (Integer) tableModel.getValueAt(selectedRow, 0);
            if (equipmentDAO.deleteEquipment(equipmentId)) {
                JOptionPane.showMessageDialog(this, "删除成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                loadEquipmentData();
            } else {
                JOptionPane.showMessageDialog(this, "删除失败", "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
} 