package ui;

import dao.CourseDAO;
import entity.Course;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.time.LocalDate;

public class CourseDialog extends JDialog {
    private CourseDAO courseDAO;
    private Course course;
    private boolean isEditMode;

    private JTextField nameField;
    private JTextField instructorField;
    private JTextField durationField;
    private JTextField priceField;
    private JTextField maxStudentsField;
    private JComboBox<String> levelComboBox;
    private JComboBox<String> statusComboBox;
    private JTextArea descriptionArea;

    private boolean confirmed = false;

    public CourseDialog(JFrame parent, String title, Course course) {
        super(parent, title, true);
        this.courseDAO = new CourseDAO();
        this.course = course;
        this.isEditMode = (course != null);
        initComponents();
        if (isEditMode) {
            loadCourseData();
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
        setSize(450, 500);
        setResizable(false);
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel nameLabel = new JLabel("课程名称:");
        nameLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(nameLabel, gbc);
        nameField = new JTextField(20);
        nameField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 1; gbc.gridy = 0;
        panel.add(nameField, gbc);

        JLabel instructorLabel = new JLabel("教练:");
        instructorLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(instructorLabel, gbc);
        instructorField = new JTextField(20);
        instructorField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 1; gbc.gridy = 1;
        panel.add(instructorField, gbc);

        JLabel durationLabel = new JLabel("时长(分钟):");
        durationLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(durationLabel, gbc);
        durationField = new JTextField(20);
        durationField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 1; gbc.gridy = 2;
        panel.add(durationField, gbc);

        JLabel priceLabel = new JLabel("价格:");
        priceLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(priceLabel, gbc);
        priceField = new JTextField(20);
        priceField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 1; gbc.gridy = 3;
        panel.add(priceField, gbc);

        JLabel maxStudentsLabel = new JLabel("最大人数:");
        maxStudentsLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(maxStudentsLabel, gbc);
        maxStudentsField = new JTextField(20);
        maxStudentsField.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 1; gbc.gridy = 4;
        panel.add(maxStudentsField, gbc);

        JLabel levelLabel = new JLabel("难度等级:");
        levelLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 5;
        panel.add(levelLabel, gbc);
        levelComboBox = new JComboBox<>(new String[]{"初级", "中级", "高级"});
        levelComboBox.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 1; gbc.gridy = 5;
        panel.add(levelComboBox, gbc);

        JLabel statusLabel = new JLabel("状态:");
        statusLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 6;
        panel.add(statusLabel, gbc);
        statusComboBox = new JComboBox<>(new String[]{"开放", "暂停", "已满"});
        statusComboBox.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 1; gbc.gridy = 6;
        panel.add(statusComboBox, gbc);

        JLabel descLabel = new JLabel("课程描述:");
        descLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 7;
        panel.add(descLabel, gbc);
        descriptionArea = new JTextArea(4, 20);
        descriptionArea.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        scrollPane.setPreferredSize(new Dimension(200, 80));
        gbc.gridx = 1; gbc.gridy = 7;
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

    private void loadCourseData() {
        if (course != null) {
            nameField.setText(course.getName());
            instructorField.setText(course.getInstructor());
            durationField.setText(String.valueOf(course.getDuration()));
            priceField.setText(String.valueOf(course.getPrice()));
            maxStudentsField.setText(String.valueOf(course.getMaxStudents()));
            levelComboBox.setSelectedItem(course.getLevel());
            statusComboBox.setSelectedItem(course.getStatus());
            descriptionArea.setText(course.getDescription());
        }
    }

    private void confirm() {
        if (!validateInput()) return;
        if (!isEditMode) course = new Course();
        course.setName(nameField.getText().trim());
        course.setInstructor(instructorField.getText().trim());
        course.setDuration(Integer.parseInt(durationField.getText().trim()));
        course.setPrice(Double.parseDouble(priceField.getText().trim()));
        course.setMaxStudents(Integer.parseInt(maxStudentsField.getText().trim()));
        course.setLevel((String) levelComboBox.getSelectedItem());
        course.setStatus((String) statusComboBox.getSelectedItem());
        course.setDescription(descriptionArea.getText().trim());
        if (!isEditMode) {
            course.setStartDate(Date.valueOf(LocalDate.now()));
        }
        boolean success = isEditMode ? courseDAO.updateCourse(course) : courseDAO.addCourse(course);
        if (success) {
            confirmed = true;
            JOptionPane.showMessageDialog(this, isEditMode ? "课程信息更新成功！" : "课程添加成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, isEditMode ? "课程信息更新失败！" : "课程添加失败！", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancel() { dispose(); }

    private boolean validateInput() {
        if (nameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入课程名称", "验证错误", JOptionPane.WARNING_MESSAGE);
            nameField.requestFocus(); return false;
        }
        if (instructorField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入教练姓名", "验证错误", JOptionPane.WARNING_MESSAGE);
            instructorField.requestFocus(); return false;
        }
        try {
            int duration = Integer.parseInt(durationField.getText().trim());
            if (duration <= 0) {
                JOptionPane.showMessageDialog(this, "请输入有效的时长", "验证错误", JOptionPane.WARNING_MESSAGE);
                durationField.requestFocus(); return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "请输入有效的时长", "验证错误", JOptionPane.WARNING_MESSAGE);
            durationField.requestFocus(); return false;
        }
        try {
            double price = Double.parseDouble(priceField.getText().trim());
            if (price < 0) {
                JOptionPane.showMessageDialog(this, "请输入有效的价格", "验证错误", JOptionPane.WARNING_MESSAGE);
                priceField.requestFocus(); return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "请输入有效的价格", "验证错误", JOptionPane.WARNING_MESSAGE);
            priceField.requestFocus(); return false;
        }
        try {
            int maxStudents = Integer.parseInt(maxStudentsField.getText().trim());
            if (maxStudents <= 0) {
                JOptionPane.showMessageDialog(this, "请输入有效的最大人数", "验证错误", JOptionPane.WARNING_MESSAGE);
                maxStudentsField.requestFocus(); return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "请输入有效的最大人数", "验证错误", JOptionPane.WARNING_MESSAGE);
            maxStudentsField.requestFocus(); return false;
        }
        return true;
    }

    public boolean isConfirmed() { return confirmed; }
    public Course getCourse() { return course; }
} 