import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;


public class EmployeeDAO {


    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT id, name, position FROM employees";

        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Lặp qua kết quả trả về
            while (rs.next()) {
                // Tạo một đối tượng Employee từ dữ liệu hàng
                Employee emp = new Employee(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("position")
                );
                // Thêm vào danh sách
                employees.add(emp);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return employees;
    }


    public boolean addEmployee(Employee employee) {
        String sql = "INSERT INTO employees(id, name, position) VALUES(?,?,?)";

        DatabaseMetaData DatabaseConnector = null;
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {


            pstmt.setString(1, employee.getId());
            pstmt.setString(2, employee.getName());
            pstmt.setString(3, employee.getPosition());

            // Thực thi
            pstmt.executeUpdate();
            return true; // Trả về true nếu không có lỗi

        } catch (SQLException e) {
            System.out.println(e.getMessage());

            JOptionPane.showMessageDialog(null,
                    "Lỗi khi thêm nhân viên: " + e.getMessage(),
                    "Lỗi CSDL",
                    JOptionPane.ERROR_MESSAGE);
            return false; // Trả về false nếu có lỗi
        }
    }


}
