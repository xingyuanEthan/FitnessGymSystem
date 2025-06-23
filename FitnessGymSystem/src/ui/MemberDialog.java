package ui;

import dao.MemberDAO;
import entity.Member;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.time.LocalDate;

public class MemberDialog extends JDialog {
    
    private MemberDAO memberDAO;
    private Member member;
    private boolean isEditMode;
    
    // UI组件
    private JTextField nameField;
    private JComboBox<String> genderComboBox;
    private JTextField ageField;
    private JTextField phoneField;
    private JTextField idCardField;
    private JComboBox<String> levelComboBox;
    private JComboBox<String> statusComboBox;
    
    private boolean confirmed = false;
    
    public MemberDialog(JFrame parent, String title, Member member) {
        super(parent, title, true);
        this.memberDAO = new MemberDAO();
        this.member = member;
        this.isEditMode = (member != null);
        
        initComponents();
        if (isEditMode) {
            loadMemberData();
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
        
        setSize(400, 350);
        setResizable(false);
    }
    
    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // 姓名
        JLabel nameLabel = new JLabel("姓名:");
        nameLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(nameLabel, gbc);
        
        nameField = new JTextField(20);
        nameField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(nameField, gbc);
        
        // 性别
        JLabel genderLabel = new JLabel("性别:");
        genderLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(genderLabel, gbc);
        
        genderComboBox = new JComboBox<>(new String[]{"男", "女"});
        genderComboBox.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(genderComboBox, gbc);
        
        // 年龄
        JLabel ageLabel = new JLabel("年龄:");
        ageLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(ageLabel, gbc);
        
        ageField = new JTextField(20);
        ageField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(ageField, gbc);
        
        // 电话
        JLabel phoneLabel = new JLabel("电话:");
        phoneLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(phoneLabel, gbc);
        
        phoneField = new JTextField(20);
        phoneField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(phoneField, gbc);
        
        // 身份证号
        JLabel idCardLabel = new JLabel("身份证号:");
        idCardLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(idCardLabel, gbc);
        
        idCardField = new JTextField(20);
        idCardField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(idCardField, gbc);
        
        // 会员等级
        JLabel levelLabel = new JLabel("会员等级:");
        levelLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(levelLabel, gbc);
        
        levelComboBox = new JComboBox<>(new String[]{"普通会员", "银卡会员", "金卡会员", "钻石会员"});
        levelComboBox.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(levelComboBox, gbc);
        
        // 状态
        JLabel statusLabel = new JLabel("状态:");
        statusLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(statusLabel, gbc);
        
        statusComboBox = new JComboBox<>(new String[]{"正常", "暂停", "注销"});
        statusComboBox.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 6;
        panel.add(statusComboBox, gbc);
        
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
    
    private void loadMemberData() {
        if (member != null) {
            nameField.setText(member.getName());
            genderComboBox.setSelectedItem(member.getGender());
            ageField.setText(String.valueOf(member.getAge()));
            phoneField.setText(member.getPhone());
            idCardField.setText(member.getIdCard());
            levelComboBox.setSelectedItem(member.getLevel());
            statusComboBox.setSelectedItem(member.getStatus());
        }
    }
    
    private void confirm() {
        // 验证输入
        if (!validateInput()) {
            return;
        }
        
        // 创建或更新会员对象
        if (!isEditMode) {
            member = new Member();
        }
        
        member.setName(nameField.getText().trim());
        member.setGender((String) genderComboBox.getSelectedItem());
        member.setAge(Integer.parseInt(ageField.getText().trim()));
        member.setPhone(phoneField.getText().trim());
        member.setIdCard(idCardField.getText().trim());
        member.setLevel((String) levelComboBox.getSelectedItem());
        member.setStatus((String) statusComboBox.getSelectedItem());
        
        if (!isEditMode) {
            member.setRegistrationDate(Date.valueOf(LocalDate.now()));
        }
        
        // 保存到数据库
        boolean success;
        if (isEditMode) {
            success = memberDAO.updateMember(member);
        } else {
            success = memberDAO.addMember(member);
        }
        
        if (success) {
            confirmed = true;
            JOptionPane.showMessageDialog(this, 
                isEditMode ? "会员信息更新成功！" : "会员添加成功！", 
                "成功", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, 
                isEditMode ? "会员信息更新失败！" : "会员添加失败！", 
                "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void cancel() {
        dispose();
    }
    
    private boolean validateInput() {
        // 验证姓名
        if (nameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入姓名", "验证错误", JOptionPane.WARNING_MESSAGE);
            nameField.requestFocus();
            return false;
        }
        
        // 验证年龄
        try {
            int age = Integer.parseInt(ageField.getText().trim());
            if (age <= 0 || age > 120) {
                JOptionPane.showMessageDialog(this, "请输入有效的年龄（1-120）", "验证错误", JOptionPane.WARNING_MESSAGE);
                ageField.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "请输入有效的年龄", "验证错误", JOptionPane.WARNING_MESSAGE);
            ageField.requestFocus();
            return false;
        }
        
        // 验证电话
        if (phoneField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入电话号码", "验证错误", JOptionPane.WARNING_MESSAGE);
            phoneField.requestFocus();
            return false;
        }
        
        // 验证身份证号
        if (idCardField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入身份证号", "验证错误", JOptionPane.WARNING_MESSAGE);
            idCardField.requestFocus();
            return false;
        }
        
        return true;
    }
    
    public boolean isConfirmed() {
        return confirmed;
    }
    
    public Member getMember() {
        return member;
    }
} 