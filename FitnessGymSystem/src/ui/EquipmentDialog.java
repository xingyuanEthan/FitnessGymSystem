package ui;

import dao.EquipmentDAO;
import entity.Equipment;

import javax.swing.*;
import java.awt.*;

public class EquipmentDialog extends JDialog {
    
    private EquipmentDAO equipmentDAO;
    private Equipment equipment;
    private boolean isEditMode;
    
    // UI组件
    private JTextField nameField;
    private JTextField brandField;
    private JTextField modelField;
    private JTextField priceField;
    private JComboBox<String> statusComboBox;
    private JTextArea descriptionArea;
    
    private boolean confirmed = false;
    
    public EquipmentDialog(JFrame parent, String title, Equipment equipment) {
        super(parent, title, true);
        this.equipmentDAO = new EquipmentDAO();
        this.equipment = equipment;
        this.isEditMode = (equipment != null);
        
        initComponents();
        if (isEditMode) {
            loadEquipmentData();
        }
        
        pack();
        setLocationRelativeTo(parent);
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        
        // 创建表单面板
        JPanel formPanel = createFormPanel();
        add(formPanel, BorderLayout.CENTER);
        
        // 创建按钮面板
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);
        
        setSize(450, 400);
        setResizable(false);
    }
    
    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // 器材名称
        JLabel nameLabel = new JLabel("器材名称:");
        nameLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(nameLabel, gbc);
        
        nameField = new JTextField(20);
        nameField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(nameField, gbc);
        
        // 品牌
        JLabel brandLabel = new JLabel("品牌:");
        brandLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(brandLabel, gbc);
        
        brandField = new JTextField(20);
        brandField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(brandField, gbc);
        
        // 型号
        JLabel modelLabel = new JLabel("型号:");
        modelLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(modelLabel, gbc);
        
        modelField = new JTextField(20);
        modelField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(modelField, gbc);
        
        // 价格
        JLabel priceLabel = new JLabel("价格:");
        priceLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(priceLabel, gbc);
        
        priceField = new JTextField(20);
        priceField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(priceField, gbc);
        
        // 状态
        JLabel statusLabel = new JLabel("状态:");
        statusLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(statusLabel, gbc);
        
        statusComboBox = new JComboBox<>(new String[]{"正常", "维修中", "报废"});
        statusComboBox.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(statusComboBox, gbc);
        
        // 描述
        JLabel descLabel = new JLabel("描述:");
        descLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(descLabel, gbc);
        
        descriptionArea = new JTextArea(4, 20);
        descriptionArea.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        scrollPane.setPreferredSize(new Dimension(200, 80));
        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(scrollPane, gbc);
        
        return panel;
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        JButton confirmButton = new JButton("确定");
        confirmButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        confirmButton.setBackground(new Color(52, 152, 219));
        confirmButton.setForeground(Color.BLACK);
        confirmButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        confirmButton.addActionListener(e -> confirm());
        panel.add(confirmButton);
        
        JButton cancelButton = new JButton("取消");
        cancelButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        cancelButton.setBackground(new Color(231, 76, 60));
        cancelButton.setForeground(Color.BLACK);
        cancelButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        cancelButton.addActionListener(e -> cancel());
        panel.add(cancelButton);
        
        return panel;
    }
    
    private void loadEquipmentData() {
        if (equipment != null) {
            nameField.setText(equipment.getName());
            brandField.setText(equipment.getBrand());
            modelField.setText(equipment.getModel());
            priceField.setText(String.valueOf(equipment.getPrice()));
            statusComboBox.setSelectedItem(equipment.getStatus());
            descriptionArea.setText(equipment.getDescription());
        }
    }
    
    private void confirm() {
        // 验证输入
        if (!validateInput()) {
            return;
        }
        
        // 创建或更新器材对象
        if (!isEditMode) {
            equipment = new Equipment();
            equipment.setPurchaseDate(new java.util.Date()); // 设置当前日期为购买日期
        }
        
        equipment.setName(nameField.getText().trim());
        equipment.setBrand(brandField.getText().trim());
        equipment.setModel(modelField.getText().trim());
        equipment.setPrice(Double.parseDouble(priceField.getText().trim()));
        equipment.setStatus((String) statusComboBox.getSelectedItem());
        equipment.setDescription(descriptionArea.getText().trim());
        
        // 保存到数据库
        boolean success;
        if (isEditMode) {
            success = equipmentDAO.updateEquipment(equipment);
        } else {
            success = equipmentDAO.addEquipment(equipment);
        }
        
        if (success) {
            confirmed = true;
            JOptionPane.showMessageDialog(this, 
                isEditMode ? "器材信息更新成功！" : "器材添加成功！", 
                "成功", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, 
                isEditMode ? "器材信息更新失败！" : "器材添加失败！", 
                "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void cancel() {
        dispose();
    }
    
    private boolean validateInput() {
        // 验证器材名称
        if (nameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入器材名称", "验证错误", JOptionPane.WARNING_MESSAGE);
            nameField.requestFocus();
            return false;
        }
        
        // 验证品牌
        if (brandField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入品牌", "验证错误", JOptionPane.WARNING_MESSAGE);
            brandField.requestFocus();
            return false;
        }
        
        // 验证型号
        if (modelField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入型号", "验证错误", JOptionPane.WARNING_MESSAGE);
            modelField.requestFocus();
            return false;
        }
        
        // 验证价格
        try {
            double price = Double.parseDouble(priceField.getText().trim());
            if (price < 0) {
                JOptionPane.showMessageDialog(this, "请输入有效的价格", "验证错误", JOptionPane.WARNING_MESSAGE);
                priceField.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "请输入有效的价格", "验证错误", JOptionPane.WARNING_MESSAGE);
            priceField.requestFocus();
            return false;
        }
        
        return true;
    }
    
    public boolean isConfirmed() {
        return confirmed;
    }
    
    public Equipment getEquipment() {
        return equipment;
    }
} 