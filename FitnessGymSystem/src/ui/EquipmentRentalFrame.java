package ui;

import dao.EquipmentDAO;
import dao.EquipmentRentalDAO;
import dao.MemberDAO;
import entity.Equipment;
import entity.EquipmentRental;
import entity.Member;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EquipmentRentalFrame extends JFrame {
    
    private EquipmentRentalDAO rentalDAO;
    private EquipmentDAO equipmentDAO;
    private MemberDAO memberDAO;
    private JTable rentalTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JComboBox<String> statusComboBox;
    
    public EquipmentRentalFrame() {
        rentalDAO = new EquipmentRentalDAO();
        equipmentDAO = new EquipmentDAO();
        memberDAO = new MemberDAO();
        initFrame();
        initComponents();
        loadRentalData();
    }
    
    private void initFrame() {
        setTitle("器材租借管理");
        setSize(1200, 700);
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
        panel.setPreferredSize(new Dimension(1200, 60));
        panel.setLayout(new BorderLayout());
        
        JLabel titleLabel = new JLabel("器材租借管理", SwingConstants.CENTER);
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
        JLabel searchLabel = new JLabel("搜索租借记录:");
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
        searchButton.addActionListener(e -> searchRentals());
        gbc.gridx = 2;
        gbc.gridy = 0;
        panel.add(searchButton, gbc);
        
        // 状态筛选
        JLabel statusLabel = new JLabel("状态筛选:");
        statusLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(statusLabel, gbc);
        
        statusComboBox = new JComboBox<>(new String[]{"全部", "租借中", "已归还", "逾期"});
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
        
        JButton rentButton = new JButton("租借器材");
        rentButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        rentButton.setBackground(new Color(245, 245, 245));
        rentButton.setForeground(Color.BLACK);
        rentButton.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        rentButton.addActionListener(e -> rentEquipment());
        buttonPanel.add(rentButton);
        
        JButton returnButton = new JButton("归还器材");
        returnButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        returnButton.setBackground(new Color(245, 245, 245));
        returnButton.setForeground(Color.BLACK);
        returnButton.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        returnButton.addActionListener(e -> returnEquipment());
        buttonPanel.add(returnButton);
        
        JButton deleteButton = new JButton("删除记录");
        deleteButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        deleteButton.setBackground(new Color(245, 245, 245));
        deleteButton.setForeground(Color.BLACK);
        deleteButton.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        deleteButton.addActionListener(e -> deleteRental());
        buttonPanel.add(deleteButton);
        
        JButton refreshButton = new JButton("刷新");
        refreshButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        refreshButton.setBackground(new Color(245, 245, 245));
        refreshButton.setForeground(Color.BLACK);
        refreshButton.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        refreshButton.addActionListener(e -> loadRentalData());
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
        String[] columnNames = {"ID", "会员ID", "会员姓名", "器材ID", "器材名称", "租借日期", "预计归还日期", "实际归还日期", "状态", "备注"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        rentalTable = new JTable(tableModel);
        rentalTable.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        rentalTable.getTableHeader().setFont(new Font("微软雅黑", Font.BOLD, 12));
        rentalTable.setRowHeight(25);
        rentalTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(rentalTable);
        scrollPane.setPreferredSize(new Dimension(1160, 350));
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void loadRentalData() {
        tableModel.setRowCount(0);
        List<EquipmentRental> rentals = rentalDAO.getAll();
        for (EquipmentRental rental : rentals) {
            Member member = memberDAO.getById(rental.getMemberId());
            Equipment equipment = equipmentDAO.getById(rental.getEquipmentId());
            
            Object[] row = {
                rental.getId(),
                rental.getMemberId(),
                member != null ? member.getName() : "未知",
                rental.getEquipmentId(),
                equipment != null ? equipment.getName() : "未知",
                rental.getRentalDate(),
                rental.getExpectedReturnDate(),
                rental.getActualReturnDate(),
                rental.getStatus(),
                rental.getNotes()
            };
            tableModel.addRow(row);
        }
    }
    
    private void searchRentals() {
        String searchText = searchField.getText().trim();
        if (searchText.isEmpty()) {
            loadRentalData();
            return;
        }
        
        tableModel.setRowCount(0);
        List<EquipmentRental> rentals = rentalDAO.searchByMemberName(searchText);
        for (EquipmentRental rental : rentals) {
            Member member = memberDAO.getById(rental.getMemberId());
            Equipment equipment = equipmentDAO.getById(rental.getEquipmentId());
            
            Object[] row = {
                rental.getId(),
                rental.getMemberId(),
                member != null ? member.getName() : "未知",
                rental.getEquipmentId(),
                equipment != null ? equipment.getName() : "未知",
                rental.getRentalDate(),
                rental.getExpectedReturnDate(),
                rental.getActualReturnDate(),
                rental.getStatus(),
                rental.getNotes()
            };
            tableModel.addRow(row);
        }
    }
    
    private void filterByStatus() {
        String selectedStatus = (String) statusComboBox.getSelectedItem();
        if ("全部".equals(selectedStatus)) {
            loadRentalData();
            return;
        }
        
        tableModel.setRowCount(0);
        List<EquipmentRental> rentals = rentalDAO.searchByStatus(selectedStatus);
        for (EquipmentRental rental : rentals) {
            Member member = memberDAO.getById(rental.getMemberId());
            Equipment equipment = equipmentDAO.getById(rental.getEquipmentId());
            
            Object[] row = {
                rental.getId(),
                rental.getMemberId(),
                member != null ? member.getName() : "未知",
                rental.getEquipmentId(),
                equipment != null ? equipment.getName() : "未知",
                rental.getRentalDate(),
                rental.getExpectedReturnDate(),
                rental.getActualReturnDate(),
                rental.getStatus(),
                rental.getNotes()
            };
            tableModel.addRow(row);
        }
    }
    
    private void resetSearch() {
        searchField.setText("");
        statusComboBox.setSelectedIndex(0);
        loadRentalData();
    }
    
    private void rentEquipment() {
        // 创建租借对话框
        JDialog dialog = new JDialog(this, "租借器材", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // 会员ID输入
        JLabel memberLabel = new JLabel("会员ID:");
        memberLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        dialog.add(memberLabel, gbc);
        
        JTextField memberField = new JTextField(15);
        memberField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 0;
        dialog.add(memberField, gbc);
        
        // 器材ID输入
        JLabel equipmentLabel = new JLabel("器材ID:");
        equipmentLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        dialog.add(equipmentLabel, gbc);
        
        JTextField equipmentField = new JTextField(15);
        equipmentField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 1;
        dialog.add(equipmentField, gbc);
        
        // 预计归还日期
        JLabel returnDateLabel = new JLabel("预计归还日期:");
        returnDateLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        dialog.add(returnDateLabel, gbc);
        
        JTextField returnDateField = new JTextField(15);
        returnDateField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        returnDateField.setText(LocalDate.now().plusDays(7).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        gbc.gridx = 1;
        gbc.gridy = 2;
        dialog.add(returnDateField, gbc);
        
        // 备注
        JLabel notesLabel = new JLabel("备注:");
        notesLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        dialog.add(notesLabel, gbc);
        
        JTextField notesField = new JTextField(15);
        notesField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 3;
        dialog.add(notesField, gbc);
        
        // 按钮
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JButton confirmButton = new JButton("确认租借");
        confirmButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        confirmButton.setBackground(new Color(245, 245, 245));
        confirmButton.setForeground(Color.BLACK);
        confirmButton.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        confirmButton.addActionListener(e -> {
            try {
                int memberId = Integer.parseInt(memberField.getText().trim());
                int equipmentId = Integer.parseInt(equipmentField.getText().trim());
                String expectedReturnDate = returnDateField.getText().trim();
                String notes = notesField.getText().trim();
                
                EquipmentRental rental = new EquipmentRental();
                rental.setMemberId(memberId);
                rental.setEquipmentId(equipmentId);
                rental.setRentalDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                rental.setExpectedReturnDate(expectedReturnDate);
                rental.setStatus("租借中");
                rental.setNotes(notes);
                
                if (rentalDAO.addRental(rental)) {
                    JOptionPane.showMessageDialog(dialog, "租借成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                    dialog.dispose();
                    loadRentalData();
                } else {
                    JOptionPane.showMessageDialog(dialog, "租借失败", "错误", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "请输入有效的ID", "错误", JOptionPane.ERROR_MESSAGE);
            }
        });
        buttonPanel.add(confirmButton);
        
        JButton cancelButton = new JButton("取消");
        cancelButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        cancelButton.setBackground(new Color(245, 245, 245));
        cancelButton.setForeground(Color.BLACK);
        cancelButton.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        cancelButton.addActionListener(e -> dialog.dispose());
        buttonPanel.add(cancelButton);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        dialog.add(buttonPanel, gbc);
        
        dialog.setVisible(true);
    }
    
    private void returnEquipment() {
        int selectedRow = rentalTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请选择要归还的租借记录", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int rentalId = (Integer) tableModel.getValueAt(selectedRow, 0);
        String status = (String) tableModel.getValueAt(selectedRow, 8);
        
        if (!"租借中".equals(status)) {
            JOptionPane.showMessageDialog(this, "该记录不是租借中状态", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int result = JOptionPane.showConfirmDialog(this, "确定要归还这个器材吗？", "确认归还", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            EquipmentRental rental = rentalDAO.getById(rentalId);
            if (rental != null) {
                rental.setActualReturnDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                rental.setStatus("已归还");
                
                if (rentalDAO.updateRental(rental)) {
                    JOptionPane.showMessageDialog(this, "归还成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                    loadRentalData();
                } else {
                    JOptionPane.showMessageDialog(this, "归还失败", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    private void deleteRental() {
        int selectedRow = rentalTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请选择要删除的租借记录", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int result = JOptionPane.showConfirmDialog(this, "确定要删除选中的租借记录吗？", "确认删除", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            int rentalId = (Integer) tableModel.getValueAt(selectedRow, 0);
            if (rentalDAO.deleteRental(rentalId)) {
                JOptionPane.showMessageDialog(this, "删除成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                loadRentalData();
            } else {
                JOptionPane.showMessageDialog(this, "删除失败", "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
} 