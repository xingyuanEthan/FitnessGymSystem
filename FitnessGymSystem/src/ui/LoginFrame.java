package ui;

import dao.LoginDAO;
import entity.Employee;
import entity.Member;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginFrame() {
        setTitle("系统登录");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        // Title Panel
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("高校健身房管理系统登录");
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 20));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("用户名:"), gbc);

        gbc.gridx = 1;
        usernameField = new JTextField(15);
        formPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("密 码:"), gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        formPanel.add(passwordField, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        loginButton = new JButton("登录");
        buttonPanel.add(loginButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add ActionListener to the login button
        loginButton.addActionListener(e -> performLogin());
        
        // Allow login by pressing Enter key
        getRootPane().setDefaultButton(loginButton);
    }

    private void performLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.trim().isEmpty() || password.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "用户名和密码不能为空！", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        LoginDAO loginDAO = new LoginDAO();
        Object user = loginDAO.login(username, password);

        if (user != null) {
            // Login successful
            dispose(); // Close the login window
            // Open the main frame and pass the user object
            SwingUtilities.invokeLater(() -> {
                MainFrame mainFrame = new MainFrame(user);
                mainFrame.setVisible(true);
            });
        } else {
            // Login failed
            JOptionPane.showMessageDialog(this, "用户名或密码错误！", "登录失败", JOptionPane.ERROR_MESSAGE);
        }
    }
} 