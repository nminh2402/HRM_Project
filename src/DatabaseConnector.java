import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

    private static final String URL = "jdbc:mysql://localhost:3306/quanlynhansu";
    private static final String USER = "root";
    private static final String PASS = "123456"; // <-- Nhớ đổi pass nếu của bạn khác

    public static Connection getConnection() {
        Connection conn = null;
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            System.out.println("Lỗi: Chưa thêm thư viện MySQL Driver vào Project Structure!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Lỗi: Sai tên DB, User, Pass hoặc chưa bật MySQL!");
            e.printStackTrace();
        }
        return conn;
    }

    public static void main(String[] args) {
        Connection c = getConnection();
        if (c != null) {
            System.out.println("thanh cong");
        } else {
            System.out.println("that bai");
        }
    }
}