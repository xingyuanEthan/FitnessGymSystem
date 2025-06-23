package ui;

import dao.EmployeeDAO;
import entity.Employee;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EmployeeManagementFrame extends JFrame {
    
    private EmployeeDAO employeeDAO;
    private JTable employeeTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JComboBox<String> positionComboBox;
    
    public EmployeeManagementFrame() {
        employeeDAO = new EmployeeDAO();
        initFrame();
        initComponents();
        loadEmployeeData();
    }
    
    private void initFrame() {
        setTitle("员工管理");
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
        
        JLabel titleLabel = new JLabel("员工信息管理", SwingConstants.CENTER);
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
        JLabel searchLabel = new JLabel("搜索员工:");
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
        searchButton.addActionListener(e -> searchEmployees());
        gbc.gridx = 2;
        gbc.gridy = 0;
        panel.add(searchButton, gbc);
        
        // 职位筛选
        JLabel positionLabel = new JLabel("职位筛选:");
        positionLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(positionLabel, gbc);
        
        positionComboBox = new JComboBox<>(new String[]{"全部", "教练", "前台", "管理员", "清洁工"});
        positionComboBox.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        positionComboBox.addActionListener(e -> filterByPosition());
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(positionComboBox, gbc);
        
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
        
        JButton addButton = new JButton("添加员工");
        addButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        addButton.setBackground(new Color(245, 245, 245));
        addButton.setForeground(Color.BLACK);
        addButton.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        addButton.addActionListener(e -> addEmployee());
        buttonPanel.add(addButton);
        
        JButton editButton = new JButton("编辑员工");
        editButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        editButton.setBackground(new Color(245, 245, 245));
        editButton.setForeground(Color.BLACK);
        editButton.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        editButton.addActionListener(e -> editEmployee());
        buttonPanel.add(editButton);
        
        JButton deleteButton = new JButton("删除员工");
        deleteButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        deleteButton.setBackground(new Color(245, 245, 245));
        deleteButton.setForeground(Color.BLACK);
        deleteButton.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        deleteButton.addActionListener(e -> deleteEmployee());
        buttonPanel.add(deleteButton);
        
        JButton refreshButton = new JButton("刷新");
        refreshButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        refreshButton.setBackground(new Color(245, 245, 245));
        refreshButton.setForeground(Color.BLACK);
        refreshButton.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        refreshButton.addActionListener(e -> loadEmployeeData());
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
        String[] columnNames = {"ID", "姓名", "性别", "职位", "电话", "邮箱", "入职日期", "薪资", "状态"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        employeeTable = new JTable(tableModel);
        employeeTable.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        employeeTable.getTableHeader().setFont(new Font("微软雅黑", Font.BOLD, 12));
        employeeTable.setRowHeight(25);
        employeeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(employeeTable);
        scrollPane.setPreferredSize(new Dimension(960, 300));
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void loadEmployeeData() {
        tableModel.setRowCount(0);
        List<Employee> employees = employeeDAO.getAll();
        for (Employee employee : employees) {
            Object[] row = {
                employee.getId(),
                employee.getName(),
                employee.getGender(),
                employee.getPosition(),
                employee.getPhone(),
                employee.getEmail(),
                employee.getHireDate(),
                employee.getSalary(),
                employee.getStatus()
            };
            tableModel.addRow(row);
        }
    }
    
    private void searchEmployees() {
        String searchText = searchField.getText().trim();
        if (searchText.isEmpty()) {
            loadEmployeeData();
            return;
        }
        
        tableModel.setRowCount(0);
        List<Employee> employees = employeeDAO.searchByName(searchText);
        for (Employee employee : employees) {
            Object[] row = {
                employee.getId(),
                employee.getName(),
                employee.getGender(),
                employee.getPosition(),
                employee.getPhone(),
                employee.getEmail(),
                employee.getHireDate(),
                employee.getSalary(),
                employee.getStatus()
            };
            tableModel.addRow(row);
        }
    }
    
    private void filterByPosition() {
        String selectedPosition = (String) positionComboBox.getSelectedItem();
        if ("全部".equals(selectedPosition)) {
            loadEmployeeData();
            return;
        }
        
        tableModel.setRowCount(0);
        List<Employee> employees = employeeDAO.searchByPosition(selectedPosition);
        for (Employee employee : employees) {
            Object[] row = {
                employee.getId(),
                employee.getName(),
                employee.getGender(),
                employee.getPosition(),
                employee.getPhone(),
                employee.getEmail(),
                employee.getHireDate(),
                employee.getSalary(),
                employee.getStatus()
            };
            tableModel.addRow(row);
        }
    }
    
    private void resetSearch() {
        searchField.setText("");
        positionComboBox.setSelectedIndex(0);
        loadEmployeeData();
    }
    
    private void addEmployee() {
        EmployeeDialog dialog = new EmployeeDialog(this, "添加员工", null);
        dialog.setVisible(true);
        
        if (dialog.isConfirmed()) {
            loadEmployeeData();
        }
    }
    
    private void editEmployee() {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请选择要编辑的员工", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int employeeId = (Integer) tableModel.getValueAt(selectedRow, 0);
        Employee employee = employeeDAO.getById(employeeId);
        
        if (employee != null) {
            EmployeeDialog dialog = new EmployeeDialog(this, "编辑员工", employee);
            dialog.setVisible(true);
            
            if (dialog.isConfirmed()) {
                loadEmployeeData();
            }
        } else {
            JOptionPane.showMessageDialog(this, "无法获取员工信息", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteEmployee() {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请选择要删除的员工", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int result = JOptionPane.showConfirmDialog(this, "确定要删除选中的员工吗？", "确认删除", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            int employeeId = (Integer) tableModel.getValueAt(selectedRow, 0);
            if (employeeDAO.deleteEmployee(employeeId)) {
                JOptionPane.showMessageDialog(this, "删除成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                loadEmployeeData();
            } else {
                JOptionPane.showMessageDialog(this, "删除失败", "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
} 