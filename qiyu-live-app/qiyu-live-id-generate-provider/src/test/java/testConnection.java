import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class testConnection {
    public static void main(String[] args) {
        try {
            // 加载 JDBC 驱动程序
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 设置数据库连接 URL
            String url = "jdbc:mysql://localhost:3306/qiyu_live_common?useSSL=false&serverTimezone=UTC";
            String username = "root";
            String password = "root";

            // 建立连接
            Connection conn = DriverManager.getConnection(url, username, password);
            System.out.println("连接成功！");
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}