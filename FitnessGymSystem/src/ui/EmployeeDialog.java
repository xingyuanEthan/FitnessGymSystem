package ui;

import dao.EmployeeDAO;
import entity.Employee;

import javax.swing.*;
import java.awt.*;

public class EmployeeDialog extends JDialog {
    private EmployeeDAO employeeDAO;
    private Employee employee;
    private boolean isEditMode;

    private JTextField nameField;
    private JComboBox<String> genderComboBox;
    private JTextField ageField;
    private JTextField phoneField;
    private JTextField positionField;
    private JTextField salaryField;
    private JComboBox<String> statusComboBox;

    private boolean confirmed = false;

    public EmployeeDialog(JFrame parent, String title, Employee employee) {
        super(parent, title, true);
        this.employeeDAO = new EmployeeDAO();
        this.employee = employee;
        this.isEditMode = (employee != null);
        initComponents();
        if (isEditMode) {
            loadEmployeeData();
        }
        pack();
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        JPanel formPanel = createFormPanel();
        add(formPanel, BorderLayout.CENTER);
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

        JLabel nameLabel = new JLabel("姓名:");
        nameLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(nameLabel, gbc);
        nameField = new JTextField(20);
        nameField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 1; gbc.gridy = 0;
        panel.add(nameField, gbc);

        JLabel genderLabel = new JLabel("性别:");
        genderLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(genderLabel, gbc);
        genderComboBox = new JComboBox<>(new String[]{"男", "女"});
        genderComboBox.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 1; gbc.gridy = 1;
        panel.add(genderComboBox, gbc);

        JLabel ageLabel = new JLabel("年龄:");
        ageLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(ageLabel, gbc);
        ageField = new JTextField(20);
        ageField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 1; gbc.gridy = 2;
        panel.add(ageField, gbc);

        JLabel phoneLabel = new JLabel("电话:");
        phoneLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(phoneLabel, gbc);
        phoneField = new JTextField(20);
        phoneField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 1; gbc.gridy = 3;
        panel.add(phoneField, gbc);

        JLabel positionLabel = new JLabel("职位:");
        positionLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(positionLabel, gbc);
        positionField = new JTextField(20);
        positionField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 1; gbc.gridy = 4;
        panel.add(positionField, gbc);

        JLabel salaryLabel = new JLabel("工资:");
        salaryLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 5;
        panel.add(salaryLabel, gbc);
        salaryField = new JTextField(20);
        salaryField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 1; gbc.gridy = 5;
        panel.add(salaryField, gbc);

        JLabel statusLabel = new JLabel("状态:");
        statusLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 6;
        panel.add(statusLabel, gbc);
        statusComboBox = new JComboBox<>(new String[]{"在职", "离职", "休假"});
        statusComboBox.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 1; gbc.gridy = 6;
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

    private void loadEmployeeData() {
        if (employee != null) {
            nameField.setText(employee.getName());
            genderComboBox.setSelectedItem(employee.getGender());
            ageField.setText(String.valueOf(employee.getAge()));
            phoneField.setText(employee.getPhone());
            positionField.setText(employee.getPosition());
            salaryField.setText(String.valueOf(employee.getSalary()));
            statusComboBox.setSelectedItem(employee.getStatus());
        }
    }

    private void confirm() {
        if (!validateInput()) return;
        if (!isEditMode) employee = new Employee();
        employee.setName(nameField.getText().trim());
        employee.setGender((String) genderComboBox.getSelectedItem());
        employee.setAge(Integer.parseInt(ageField.getText().trim()));
        employee.setPhone(phoneField.getText().trim());
        employee.setPosition(positionField.getText().trim());
        employee.setSalary(Double.parseDouble(salaryField.getText().trim()));
        employee.setStatus((String) statusComboBox.getSelectedItem());
        boolean success = isEditMode ? employeeDAO.updateEmployee(employee) : employeeDAO.addEmployee(employee);
        if (success) {
            confirmed = true;
            JOptionPane.showMessageDialog(this, isEditMode ? "员工信息更新成功！" : "员工添加成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, isEditMode ? "员工信息更新失败！" : "员工添加失败！", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancel() { dispose(); }

    private boolean validateInput() {
        if (nameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入姓名", "验证错误", JOptionPane.WARNING_MESSAGE);
            nameField.requestFocus(); return false;
        }
        try {
            int age = Integer.parseInt(ageField.getText().trim());
            if (age <= 0 || age > 120) {
                JOptionPane.showMessageDialog(this, "请输入有效的年龄（1-120）", "验证错误", JOptionPane.WARNING_MESSAGE);
                ageField.requestFocus(); return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "请输入有效的年龄", "验证错误", JOptionPane.WARNING_MESSAGE);
            ageField.requestFocus(); return false;
        }
        if (phoneField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入电话号码", "验证错误", JOptionPane.WARNING_MESSAGE);
            phoneField.requestFocus(); return false;
        }
        if (positionField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入职位", "验证错误", JOptionPane.WARNING_MESSAGE);
            positionField.requestFocus(); return false;
        }
        try {
            double salary = Double.parseDouble(salaryField.getText().trim());
            if (salary < 0) {
                JOptionPane.showMessageDialog(this, "请输入有效的工资", "验证错误", JOptionPane.WARNING_MESSAGE);
                salaryField.requestFocus(); return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "请输入有效的工资", "验证错误", JOptionPane.WARNING_MESSAGE);
            salaryField.requestFocus(); return false;
        }
        return true;
    }

    public boolean isConfirmed() { return confirmed; }
    public Employee getEmployee() { return employee; }
} 