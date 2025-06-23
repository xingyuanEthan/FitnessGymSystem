package ui;

import javax.swing.*;
import java.awt.*;

public class UIUtils {
    
    // 定义系统配色方案
    public static final Color PRIMARY_COLOR = new Color(52, 152, 219);
    public static final Color SUCCESS_COLOR = new Color(46, 204, 113);
    public static final Color WARNING_COLOR = new Color(230, 126, 34);
    public static final Color DANGER_COLOR = new Color(231, 76, 60);
    public static final Color INFO_COLOR = new Color(26, 188, 156);
    public static final Color SECONDARY_COLOR = new Color(155, 89, 182);
    
    public static final Color BACKGROUND_COLOR = new Color(248, 249, 250);
    public static final Color DARK_BACKGROUND = new Color(52, 73, 94);
    
    // 创建主要按钮样式
    public static JButton createPrimaryButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(Color.BLACK);
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(PRIMARY_COLOR.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(PRIMARY_COLOR);
            }
        });
        
        return button;
    }
    
    // 创建成功按钮样式
    public static JButton createSuccessButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        button.setBackground(SUCCESS_COLOR);
        button.setForeground(Color.BLACK);
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(SUCCESS_COLOR.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(SUCCESS_COLOR);
            }
        });
        
        return button;
    }
    
    // 创建危险按钮样式
    public static JButton createDangerButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        button.setBackground(DANGER_COLOR);
        button.setForeground(Color.BLACK);
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(DANGER_COLOR.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(DANGER_COLOR);
            }
        });
        
        return button;
    }
    
    // 创建次要按钮样式
    public static JButton createSecondaryButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        button.setBackground(new Color(108, 117, 125));
        button.setForeground(Color.BLACK);
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(108, 117, 125).brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(108, 117, 125));
            }
        });
        
        return button;
    }
    
    // 创建标题面板
    public static JPanel createTitlePanel(String title, String subtitle) {
        JPanel panel = new JPanel();
        panel.setBackground(PRIMARY_COLOR);
        panel.setPreferredSize(new Dimension(800, 80));
        panel.setLayout(new BorderLayout());
        
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLACK);
        panel.add(titleLabel, BorderLayout.CENTER);
        
        if (subtitle != null) {
            JLabel subtitleLabel = new JLabel(subtitle, SwingConstants.CENTER);
            subtitleLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
            subtitleLabel.setForeground(Color.BLACK);
            panel.add(subtitleLabel, BorderLayout.SOUTH);
        }
        
        return panel;
    }
    
    // 创建搜索面板
    public static JPanel createSearchPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        return panel;
    }
    
    // 设置表格样式
    public static void styleTable(JTable table) {
        table.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("微软雅黑", Font.BOLD, 12));
        table.getTableHeader().setBackground(PRIMARY_COLOR);
        table.getTableHeader().setForeground(Color.BLACK);
        table.setRowHeight(25);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setSelectionBackground(PRIMARY_COLOR.brighter());
        table.setGridColor(new Color(200, 200, 200));
    }
} 