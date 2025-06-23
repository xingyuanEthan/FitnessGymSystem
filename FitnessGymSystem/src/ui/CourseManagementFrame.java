package ui;

import dao.CourseDAO;
import entity.Course;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CourseManagementFrame extends JFrame {
    
    private CourseDAO courseDAO;
    private JTable courseTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JComboBox<String> statusComboBox;
    
    public CourseManagementFrame() {
        courseDAO = new CourseDAO();
        initFrame();
        initComponents();
        loadCourseData();
    }
    
    private void initFrame() {
        setTitle("课程管理");
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
        
        JLabel titleLabel = new JLabel("健身课程管理", SwingConstants.CENTER);
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
        JLabel searchLabel = new JLabel("搜索课程:");
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
        searchButton.addActionListener(e -> searchCourses());
        gbc.gridx = 2;
        gbc.gridy = 0;
        panel.add(searchButton, gbc);
        
        // 状态筛选
        JLabel statusLabel = new JLabel("状态筛选:");
        statusLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(statusLabel, gbc);
        
        statusComboBox = new JComboBox<>(new String[]{"全部", "活跃", "暂停", "已结束"});
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
        
        JButton addButton = new JButton("添加课程");
        addButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        addButton.setBackground(new Color(245, 245, 245));
        addButton.setForeground(Color.BLACK);
        addButton.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        addButton.addActionListener(e -> addCourse());
        buttonPanel.add(addButton);
        
        JButton editButton = new JButton("编辑课程");
        editButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        editButton.setBackground(new Color(245, 245, 245));
        editButton.setForeground(Color.BLACK);
        editButton.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        editButton.addActionListener(e -> editCourse());
        buttonPanel.add(editButton);
        
        JButton deleteButton = new JButton("删除课程");
        deleteButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        deleteButton.setBackground(new Color(245, 245, 245));
        deleteButton.setForeground(Color.BLACK);
        deleteButton.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        deleteButton.addActionListener(e -> deleteCourse());
        buttonPanel.add(deleteButton);
        
        JButton refreshButton = new JButton("刷新");
        refreshButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        refreshButton.setBackground(new Color(245, 245, 245));
        refreshButton.setForeground(Color.BLACK);
        refreshButton.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        refreshButton.addActionListener(e -> loadCourseData());
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
        String[] columnNames = {"ID", "课程名称", "描述", "教练ID", "时长(分钟)", "价格", "最大容量", "课程安排", "状态"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        courseTable = new JTable(tableModel);
        courseTable.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        courseTable.getTableHeader().setFont(new Font("微软雅黑", Font.BOLD, 12));
        courseTable.setRowHeight(25);
        courseTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(courseTable);
        scrollPane.setPreferredSize(new Dimension(960, 300));
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void loadCourseData() {
        tableModel.setRowCount(0);
        List<Course> courses = courseDAO.getAll();
        for (Course course : courses) {
            Object[] row = {
                course.getId(),
                course.getName(),
                course.getDescription(),
                course.getInstructorId(),
                course.getDuration(),
                course.getPrice(),
                course.getMaxCapacity(),
                course.getSchedule(),
                course.getStatus()
            };
            tableModel.addRow(row);
        }
    }
    
    private void searchCourses() {
        String searchText = searchField.getText().trim();
        if (searchText.isEmpty()) {
            loadCourseData();
            return;
        }
        
        tableModel.setRowCount(0);
        List<Course> courses = courseDAO.searchByName(searchText);
        for (Course course : courses) {
            Object[] row = {
                course.getId(),
                course.getName(),
                course.getDescription(),
                course.getInstructorId(),
                course.getDuration(),
                course.getPrice(),
                course.getMaxCapacity(),
                course.getSchedule(),
                course.getStatus()
            };
            tableModel.addRow(row);
        }
    }
    
    private void filterByStatus() {
        String selectedStatus = (String) statusComboBox.getSelectedItem();
        if ("全部".equals(selectedStatus)) {
            loadCourseData();
            return;
        }
        
        tableModel.setRowCount(0);
        List<Course> courses = courseDAO.searchByStatus(selectedStatus);
        for (Course course : courses) {
            Object[] row = {
                course.getId(),
                course.getName(),
                course.getDescription(),
                course.getInstructorId(),
                course.getDuration(),
                course.getPrice(),
                course.getMaxCapacity(),
                course.getSchedule(),
                course.getStatus()
            };
            tableModel.addRow(row);
        }
    }
    
    private void resetSearch() {
        searchField.setText("");
        statusComboBox.setSelectedIndex(0);
        loadCourseData();
    }
    
    private void addCourse() {
        CourseDialog dialog = new CourseDialog(this, "添加课程", null);
        dialog.setVisible(true);
        
        if (dialog.isConfirmed()) {
            loadCourseData();
        }
    }
    
    private void editCourse() {
        int selectedRow = courseTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请选择要编辑的课程", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int courseId = (Integer) tableModel.getValueAt(selectedRow, 0);
        Course course = courseDAO.getById(courseId);
        
        if (course != null) {
            CourseDialog dialog = new CourseDialog(this, "编辑课程", course);
            dialog.setVisible(true);
            
            if (dialog.isConfirmed()) {
                loadCourseData();
            }
        } else {
            JOptionPane.showMessageDialog(this, "无法获取课程信息", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteCourse() {
        int selectedRow = courseTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请选择要删除的课程", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int result = JOptionPane.showConfirmDialog(this, "确定要删除选中的课程吗？", "确认删除", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            int courseId = (Integer) tableModel.getValueAt(selectedRow, 0);
            if (courseDAO.deleteCourse(courseId)) {
                JOptionPane.showMessageDialog(this, "删除成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                loadCourseData();
            } else {
                JOptionPane.showMessageDialog(this, "删除失败", "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
} 