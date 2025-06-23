package ui;

import dao.MemberDAO;
import entity.Member;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MemberManagementFrame extends JFrame {
    
    private MemberDAO memberDAO;
    private JTable memberTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JComboBox<String> levelComboBox;
    
    public MemberManagementFrame() {
        memberDAO = new MemberDAO();
        initFrame();
        initComponents();
        loadMemberData();
    }
    
    private void initFrame() {
        setTitle("会员管理");
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
        
        JLabel titleLabel = new JLabel("会员信息管理", SwingConstants.CENTER);
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
        JLabel searchLabel = new JLabel("搜索会员:");
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
        searchButton.addActionListener(e -> searchMembers());
        gbc.gridx = 2;
        gbc.gridy = 0;
        panel.add(searchButton, gbc);
        
        // 会员等级筛选
        JLabel levelLabel = new JLabel("会员等级:");
        levelLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(levelLabel, gbc);
        
        levelComboBox = new JComboBox<>(new String[]{"全部", "普通会员", "银卡会员", "金卡会员", "钻石会员"});
        levelComboBox.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        levelComboBox.addActionListener(e -> filterByLevel());
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(levelComboBox, gbc);
        
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
        
        JButton addButton = new JButton("添加会员");
        addButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        addButton.setBackground(new Color(245, 245, 245));
        addButton.setForeground(Color.BLACK);
        addButton.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        addButton.addActionListener(e -> addMember());
        buttonPanel.add(addButton);
        
        JButton editButton = new JButton("编辑会员");
        editButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        editButton.setBackground(new Color(245, 245, 245));
        editButton.setForeground(Color.BLACK);
        editButton.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        editButton.addActionListener(e -> editMember());
        buttonPanel.add(editButton);
        
        JButton deleteButton = new JButton("删除会员");
        deleteButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        deleteButton.setBackground(new Color(245, 245, 245));
        deleteButton.setForeground(Color.BLACK);
        deleteButton.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        deleteButton.addActionListener(e -> deleteMember());
        buttonPanel.add(deleteButton);
        
        JButton refreshButton = new JButton("刷新");
        refreshButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        refreshButton.setBackground(new Color(245, 245, 245));
        refreshButton.setForeground(Color.BLACK);
        refreshButton.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        refreshButton.addActionListener(e -> loadMemberData());
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
        String[] columnNames = {"ID", "姓名", "性别", "年龄", "电话", "身份证号", "会员等级", "注册日期", "状态"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        memberTable = new JTable(tableModel);
        memberTable.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        memberTable.getTableHeader().setFont(new Font("微软雅黑", Font.BOLD, 12));
        memberTable.setRowHeight(25);
        memberTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(memberTable);
        scrollPane.setPreferredSize(new Dimension(960, 300));
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void loadMemberData() {
        tableModel.setRowCount(0);
        List<Member> members = memberDAO.getAll();
        for (Member member : members) {
            Object[] row = {
                member.getId(),
                member.getName(),
                member.getGender(),
                member.getAge(),
                member.getPhone(),
                member.getIdCard(),
                member.getLevel(),
                member.getRegistrationDate(),
                member.getStatus()
            };
            tableModel.addRow(row);
        }
    }
    
    private void searchMembers() {
        String searchText = searchField.getText().trim();
        if (searchText.isEmpty()) {
            loadMemberData();
            return;
        }
        
        tableModel.setRowCount(0);
        List<Member> members = memberDAO.searchByName(searchText);
        for (Member member : members) {
            Object[] row = {
                member.getId(),
                member.getName(),
                member.getGender(),
                member.getAge(),
                member.getPhone(),
                member.getIdCard(),
                member.getLevel(),
                member.getRegistrationDate(),
                member.getStatus()
            };
            tableModel.addRow(row);
        }
    }
    
    private void filterByLevel() {
        String selectedLevel = (String) levelComboBox.getSelectedItem();
        if ("全部".equals(selectedLevel)) {
            loadMemberData();
            return;
        }
        
        tableModel.setRowCount(0);
        List<Member> members = memberDAO.searchByLevel(selectedLevel);
        for (Member member : members) {
            Object[] row = {
                member.getId(),
                member.getName(),
                member.getGender(),
                member.getAge(),
                member.getPhone(),
                member.getIdCard(),
                member.getLevel(),
                member.getRegistrationDate(),
                member.getStatus()
            };
            tableModel.addRow(row);
        }
    }
    
    private void resetSearch() {
        searchField.setText("");
        levelComboBox.setSelectedIndex(0);
        loadMemberData();
    }
    
    private void addMember() {
        MemberDialog dialog = new MemberDialog(this, "添加会员", null);
        dialog.setVisible(true);
        
        if (dialog.isConfirmed()) {
            loadMemberData();
        }
    }
    
    private void editMember() {
        int selectedRow = memberTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请选择要编辑的会员", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int memberId = (Integer) tableModel.getValueAt(selectedRow, 0);
        Member member = memberDAO.getById(memberId);
        
        if (member != null) {
            MemberDialog dialog = new MemberDialog(this, "编辑会员", member);
            dialog.setVisible(true);
            
            if (dialog.isConfirmed()) {
                loadMemberData();
            }
        } else {
            JOptionPane.showMessageDialog(this, "无法获取会员信息", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteMember() {
        int selectedRow = memberTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请选择要删除的会员", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int result = JOptionPane.showConfirmDialog(this, "确定要删除这个会员吗？", "确认删除", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            int memberId = (Integer) tableModel.getValueAt(selectedRow, 0);
            if (memberDAO.deleteMember(memberId)) {
                JOptionPane.showMessageDialog(this, "删除成功", "成功", JOptionPane.INFORMATION_MESSAGE);
                loadMemberData();
            } else {
                JOptionPane.showMessageDialog(this, "删除失败", "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
} 