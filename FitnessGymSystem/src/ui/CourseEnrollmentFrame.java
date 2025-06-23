package ui;

import dao.CourseDAO;
import dao.CourseEnrollmentDAO;
import dao.MemberDAO;
import entity.Course;
import entity.CourseEnrollment;
import entity.Member;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CourseEnrollmentFrame extends JFrame {
    
    private CourseEnrollmentDAO enrollmentDAO;
    private CourseDAO courseDAO;
    private MemberDAO memberDAO;
    private JTable enrollmentTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JComboBox<String> statusComboBox;
    
    public CourseEnrollmentFrame() {
        enrollmentDAO = new CourseEnrollmentDAO();
        courseDAO = new CourseDAO();
        memberDAO = new MemberDAO();
        initFrame();
        initComponents();
        loadEnrollmentData();
    }
    
    private void initFrame() {
        setTitle("课程报名管理");
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
        
        JLabel titleLabel = new JLabel("课程报名管理", SwingConstants.CENTER);
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
        JLabel searchLabel = new JLabel("搜索报名记录:");
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
        searchButton.addActionListener(e -> searchEnrollments());
        gbc.gridx = 2;
        gbc.gridy = 0;
        panel.add(searchButton, gbc);
        
        // 状态筛选
        JLabel statusLabel = new JLabel("状态筛选:");
        statusLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(statusLabel, gbc);
        
        statusComboBox = new JComboBox<>(new String[]{"全部", "已报名", "已取消", "已完成"});
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
        
        JButton enrollButton = new JButton("报名课程");
        enrollButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        enrollButton.setBackground(new Color(245, 245, 245));
        enrollButton.setForeground(Color.BLACK);
        enrollButton.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        enrollButton.addActionListener(e -> enrollCourse());
        buttonPanel.add(enrollButton);
        
        JButton cancelButton = new JButton("取消报名");
        cancelButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        cancelButton.setBackground(new Color(245, 245, 245));
        cancelButton.setForeground(Color.BLACK);
        cancelButton.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        cancelButton.addActionListener(e -> cancelEnrollment());
        buttonPanel.add(cancelButton);
        
        JButton deleteButton = new JButton("删除记录");
        deleteButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        deleteButton.setBackground(new Color(245, 245, 245));
        deleteButton.setForeground(Color.BLACK);
        deleteButton.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        deleteButton.addActionListener(e -> deleteEnrollment());
        buttonPanel.add(deleteButton);
        
        JButton refreshButton = new JButton("刷新");
        refreshButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        refreshButton.setBackground(new Color(245, 245, 245));
        refreshButton.setForeground(Color.BLACK);
        refreshButton.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        refreshButton.addActionListener(e -> loadEnrollmentData());
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
        String[] columnNames = {"ID", "会员ID", "会员姓名", "课程ID", "课程名称", "报名日期", "状态", "备注"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        enrollmentTable = new JTable(tableModel);
        enrollmentTable.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        enrollmentTable.getTableHeader().setFont(new Font("微软雅黑", Font.BOLD, 12));
        enrollmentTable.setRowHeight(25);
        enrollmentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(enrollmentTable);
        scrollPane.setPreferredSize(new Dimension(1160, 350));
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void loadEnrollmentData() {
        tableModel.setRowCount(0);
        List<CourseEnrollment> enrollments = enrollmentDAO.getAll();
        for (CourseEnrollment enrollment : enrollments) {
            Member member = memberDAO.getById(enrollment.getMemberId());
            Course course = courseDAO.getById(enrollment.getCourseId());
            
            Object[] row = {
                enrollment.getId(),
                enrollment.getMemberId(),
                member != null ? member.getName() : "未知",
                enrollment.getCourseId(),
                course != null ? course.getName() : "未知",
                enrollment.getEnrollmentDate(),
                enrollment.getStatus(),
                enrollment.getNotes()
            };
            tableModel.addRow(row);
        }
    }
    
    private void searchEnrollments() {
        String searchText = searchField.getText().trim();
        if (searchText.isEmpty()) {
            loadEnrollmentData();
            return;
        }
        
        tableModel.setRowCount(0);
        List<CourseEnrollment> enrollments = enrollmentDAO.searchByMemberName(searchText);
        for (CourseEnrollment enrollment : enrollments) {
            Member member = memberDAO.getById(enrollment.getMemberId());
            Course course = courseDAO.getById(enrollment.getCourseId());
            
            Object[] row = {
                enrollment.getId(),
                enrollment.getMemberId(),
                member != null ? member.getName() : "未知",
                enrollment.getCourseId(),
                course != null ? course.getName() : "未知",
                enrollment.getEnrollmentDate(),
                enrollment.getStatus(),
                enrollment.getNotes()
            };
            tableModel.addRow(row);
        }
    }
    
    private void filterByStatus() {
        String selectedStatus = (String) statusComboBox.getSelectedItem();
        if ("全部".equals(selectedStatus)) {
            loadEnrollmentData();
            return;
        }
        
        tableModel.setRowCount(0);
        List<CourseEnrollment> enrollments = enrollmentDAO.searchByStatus(selectedStatus);
        for (CourseEnrollment enrollment : enrollments) {
            Member member = memberDAO.getById(enrollment.getMemberId());
            Course course = courseDAO.getById(enrollment.getCourseId());
            
            Object[] row = {
                enrollment.getId(),
                enrollment.getMemberId(),
                member != null ? member.getName() : "未知",
                enrollment.getCourseId(),
                course != null ? course.getName() : "未知",
                enrollment.getEnrollmentDate(),
                enrollment.getStatus(),
                enrollment.getNotes()
            };
            tableModel.addRow(row);
        }
    }
    
    private void resetSearch() {
        searchField.setText("");
        statusComboBox.setSelectedIndex(0);
        loadEnrollmentData();
    }
    
    private void enrollCourse() {
        // 创建报名对话框
        JDialog dialog = new JDialog(this, "报名课程", true);
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
        
        // 课程ID输入
        JLabel courseLabel = new JLabel("课程ID:");
        courseLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        dialog.add(courseLabel, gbc);
        
        JTextField courseField = new JTextField(15);
        courseField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 1;
        dialog.add(courseField, gbc);
        
        // 备注
        JLabel notesLabel = new JLabel("备注:");
        notesLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        dialog.add(notesLabel, gbc);
        
        JTextField notesField = new JTextField(15);
        notesField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 2;
        dialog.add(notesField, gbc);
        
        // 按钮
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JButton confirmButton = new JButton("确认报名");
        confirmButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        confirmButton.setBackground(new Color(245, 245, 245));
        confirmButton.setForeground(Color.BLACK);
        confirmButton.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        confirmButton.addActionListener(e -> {
            try {
                int memberId = Integer.parseInt(memberField.getText().trim());
                int courseId = Integer.parseInt(courseField.getText().trim());
                String notes = notesField.getText().trim();
                
                CourseEnrollment enrollment = new CourseEnrollment();
                enrollment.setMemberId(memberId);
                enrollment.setCourseId(courseId);
                enrollment.setEnrollmentDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                enrollment.setStatus("已报名");
                enrollment.setNotes(notes);
                
                if (enrollmentDAO.addEnrollment(enrollment)) {
                    JOptionPane.showMessageDialog(dialog, "报名成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                    dialog.dispose();
                    loadEnrollmentData();
                } else {
                    JOptionPane.showMessageDialog(dialog, "报名失败", "错误", JOptionPane.ERROR_MESSAGE);
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
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        dialog.add(buttonPanel, gbc);
        
        dialog.setVisible(true);
    }
    
    private void cancelEnrollment() {
        int selectedRow = enrollmentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请选择要取消的报名记录", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int enrollmentId = (Integer) tableModel.getValueAt(selectedRow, 0);
        String status = (String) tableModel.getValueAt(selectedRow, 6);
        
        if (!"已报名".equals(status)) {
            JOptionPane.showMessageDialog(this, "该记录不是已报名状态", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int result = JOptionPane.showConfirmDialog(this, "确定要取消这个报名吗？", "确认取消", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            CourseEnrollment enrollment = enrollmentDAO.getById(enrollmentId);
            if (enrollment != null) {
                enrollment.setStatus("已取消");
                
                if (enrollmentDAO.updateEnrollment(enrollment)) {
                    JOptionPane.showMessageDialog(this, "取消成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                    loadEnrollmentData();
                } else {
                    JOptionPane.showMessageDialog(this, "取消失败", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    private void deleteEnrollment() {
        int selectedRow = enrollmentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请选择要删除的报名记录", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int result = JOptionPane.showConfirmDialog(this, "确定要删除选中的报名记录吗？", "确认删除", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            int enrollmentId = (Integer) tableModel.getValueAt(selectedRow, 0);
            if (enrollmentDAO.deleteEnrollment(enrollmentId)) {
                JOptionPane.showMessageDialog(this, "删除成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                loadEnrollmentData();
            } else {
                JOptionPane.showMessageDialog(this, "删除失败", "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
} 