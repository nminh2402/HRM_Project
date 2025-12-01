import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {


    public boolean checkLogin(String username, String password) {
        boolean isValid = false;

        System.out.println("[DEBUG] Check login cho User: " + username);

        String sql = "SELECT * FROM quanlynhansu.user WHERE username = ? AND password = ?";

        try {
            Connection conn = DatabaseConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                isValid = true;
                System.out.println("[DEBUG] -> Đăng nhập thành công!");
            } else {
                isValid = false;
                System.out.println("[DEBUG] -> Thất bại.");
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isValid;
    }


    public boolean register(String username, String password) {

        String sql = "INSERT INTO quanlynhansu.user (username, password, role) VALUES (?, ?, 'user')";

        try {
            Connection conn = DatabaseConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            int rowsInserted = stmt.executeUpdate();
            conn.close();

            return rowsInserted > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}