package ui;

import entity.Employee;
import entity.Member;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    
    private Object currentUser;

    public MainFrame(Object user) {
        this.currentUser = user;
        setTitle("高校健身房管理系统");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // 设置图标
        try {
            setIconImage(new ImageIcon(getClass().getResource("/images/gym_icon.png")).getImage());
        } catch (Exception e) {
            // 如果没有图标文件，忽略
        }
        
        initComponents();
    }
    
    private void initComponents() {
        // 创建主面板
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(248, 249, 250));
        
        // 创建标题面板
        JPanel titlePanel = createTitlePanel();
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        
        // 创建功能按钮面板
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        
        // 创建状态栏
        JPanel statusPanel = createStatusPanel();
        mainPanel.add(statusPanel, BorderLayout.SOUTH);
        
        setContentPane(mainPanel);
    }
    
    private JPanel createTitlePanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(52, 152, 219));
        panel.setPreferredSize(new Dimension(800, 100));
        panel.setLayout(new BorderLayout());
        
        JLabel titleLabel = new JLabel("高校健身房管理系统", SwingConstants.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 32));
        titleLabel.setForeground(Color.BLACK);
        panel.add(titleLabel, BorderLayout.CENTER);
        
        JLabel subtitleLabel = new JLabel("Fitness Gym Management System", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        subtitleLabel.setForeground(Color.BLACK);
        panel.add(subtitleLabel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 20, 20));
        panel.setBackground(new Color(248, 249, 250));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));
        
        // 根据用户角色决定显示哪些按钮
        if (currentUser instanceof Employee) {
            Employee employee = (Employee) currentUser;
            String position = employee.getPosition();

            if ("管理员".equals(position)) {
                // 管理员: 显示所有按钮
                addAdminButtons(panel);
            } else if ("教练".equals(position)) {
                // 教练: 显示部分按钮
                addTeacherButtons(panel);
            }
        } else if (currentUser instanceof Member) {
            // 会员: 显示特定按钮
            addMemberButtons(panel);
        }
        
        return panel;
    }
    
    private void addAdminButtons(JPanel panel) {
        // 会员管理按钮
        JButton memberButton = createFunctionButton("会员管理", "管理会员信息", new Color(52, 152, 219));
        memberButton.addActionListener(e -> openMemberManagement());
        panel.add(memberButton);
        
        // 员工管理按钮
        JButton employeeButton = createFunctionButton("员工管理", "管理员工信息", new Color(46, 204, 113));
        employeeButton.addActionListener(e -> openEmployeeManagement());
        panel.add(employeeButton);
        
        // 器材管理按钮
        JButton equipmentButton = createFunctionButton("器材管理", "管理健身器材", new Color(155, 89, 182));
        equipmentButton.addActionListener(e -> openEquipmentManagement());
        panel.add(equipmentButton);
        
        // 课程管理按钮
        JButton courseButton = createFunctionButton("课程管理", "管理健身课程", new Color(230, 126, 34));
        courseButton.addActionListener(e -> openCourseManagement());
        panel.add(courseButton);
        
        // 器材租借按钮
        JButton rentalButton = createFunctionButton("器材租借", "器材租借管理", new Color(231, 76, 60));
        rentalButton.addActionListener(e -> openEquipmentRental());
        panel.add(rentalButton);
        
        // 课程报名按钮
        JButton enrollmentButton = createFunctionButton("课程报名", "课程报名管理", new Color(26, 188, 156));
        enrollmentButton.addActionListener(e -> openCourseEnrollment());
        panel.add(enrollmentButton);
    }
    
    private void addTeacherButtons(JPanel panel) {
        // 课程管理按钮
        JButton courseButton = createFunctionButton("课程管理", "管理健身课程", new Color(230, 126, 34));
        courseButton.addActionListener(e -> openCourseManagement());
        panel.add(courseButton);
        
        // 会员管理按钮
        JButton memberButton = createFunctionButton("会员查看", "查看会员信息", new Color(52, 152, 219));
        memberButton.addActionListener(e -> openMemberManagement());
        panel.add(memberButton);
        
        // 课程报名按钮
        JButton enrollmentButton = createFunctionButton("课程报名", "课程报名管理", new Color(26, 188, 156));
        enrollmentButton.addActionListener(e -> openCourseEnrollment());
        panel.add(enrollmentButton);
    }
    
    private void addMemberButtons(JPanel panel) {
        // 器材租借按钮
        JButton rentalButton = createFunctionButton("器材租借", "器材租借管理", new Color(231, 76, 60));
        rentalButton.addActionListener(e -> openEquipmentRental());
        panel.add(rentalButton);
        
        // 课程报名按钮
        JButton enrollmentButton = createFunctionButton("课程报名", "课程报名管理", new Color(26, 188, 156));
        enrollmentButton.addActionListener(e -> openCourseEnrollment());
        panel.add(enrollmentButton);
    }
    
    private JButton createFunctionButton(String title, String description, Color color) {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(200, 120));
        button.setBackground(color);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("微软雅黑", Font.BOLD, 16));
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // 创建自定义面板
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 18));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel descLabel = new JLabel(description);
        descLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        descLabel.setForeground(Color.BLACK);
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panel.add(Box.createVerticalGlue());
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(descLabel);
        panel.add(Box.createVerticalGlue());
        
        button.setLayout(new BorderLayout());
        button.add(panel, BorderLayout.CENTER);
        
        // 添加鼠标悬停效果
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.brighter());
                button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
                button.setBorder(BorderFactory.createEmptyBorder());
            }
        });
        
        return button;
    }
    
    private JPanel createStatusPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(52, 73, 94));
        panel.setPreferredSize(new Dimension(800, 35));
        panel.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        
        String welcomeText = "系统就绪";
        if (currentUser instanceof Employee) {
            welcomeText = "欢迎您, " + ((Employee) currentUser).getName() + " (" + ((Employee) currentUser).getPosition() + ")";
        } else if (currentUser instanceof Member) {
            welcomeText = "欢迎您, " + ((Member) currentUser).getName() + " (会员)";
        }
        JLabel statusLabel = new JLabel(welcomeText);
        statusLabel.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        statusLabel.setForeground(Color.WHITE);
        panel.add(statusLabel, BorderLayout.WEST);
        
        JLabel timeLabel = new JLabel(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));
        timeLabel.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        timeLabel.setForeground(Color.WHITE);
        panel.add(timeLabel, BorderLayout.EAST);
        
        // 更新时间
        Timer timer = new Timer(1000, e -> {
            timeLabel.setText(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));
        });
        timer.start();
        
        return panel;
    }
    
    private void openMemberManagement() {
        SwingUtilities.invokeLater(() -> {
            MemberManagementFrame frame = new MemberManagementFrame();
            frame.setVisible(true);
        });
    }
    
    private void openEmployeeManagement() {
        SwingUtilities.invokeLater(() -> {
            EmployeeManagementFrame frame = new EmployeeManagementFrame();
            frame.setVisible(true);
        });
    }
    
    private void openEquipmentManagement() {
        SwingUtilities.invokeLater(() -> {
            EquipmentManagementFrame frame = new EquipmentManagementFrame();
            frame.setVisible(true);
        });
    }
    
    private void openCourseManagement() {
        SwingUtilities.invokeLater(() -> {
            CourseManagementFrame frame = new CourseManagementFrame();
            frame.setVisible(true);
        });
    }
    
    private void openEquipmentRental() {
        SwingUtilities.invokeLater(() -> {
            EquipmentRentalFrame frame = new EquipmentRentalFrame();
            frame.setVisible(true);
        });
    }
    
    private void openCourseEnrollment() {
        SwingUtilities.invokeLater(() -> {
            CourseEnrollmentFrame frame = new CourseEnrollmentFrame();
            frame.setVisible(true);
        });
    }
    
    public static void main(String[] args) {
        // 设置界面外观
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            // Launch the login frame instead of the main frame
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });
    }
} 