import db.DatabaseInitializer;
import db.DBUtil;
import java.sql.Connection;

public class TestDatabase {
    public static void main(String[] args) {
        try {
            // 测试数据库连接
            System.out.println("正在测试数据库连接...");
            try (Connection conn = DBUtil.getConnection()) {
                System.out.println("数据库连接成功！");
            }
            
            // 初始化数据库表
            System.out.println("正在初始化数据库表...");
            DatabaseInitializer.initializeDatabase();
            
            System.out.println("数据库初始化完成！");
            
        } catch (Exception e) {
            System.err.println("数据库初始化失败：" + e.getMessage());
            e.printStackTrace();
        }
    }
} 