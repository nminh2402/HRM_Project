import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Vector;

public class BangLuongFrame extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;

    public BangLuongFrame() {
        setTitle("Bảng Lương Chi Tiết");
        setSize(900, 500);
        setLocationRelativeTo(null);


        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());

        // Tiêu đề
        JLabel titleLabel = new JLabel("CHI TIẾT LƯƠNG NHÂN VIÊN", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.RED);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Tạo bảng
        String[] columnNames = {"Mã NV", "Họ Tên", "Chức Vụ", "Lương CB", "Hệ Số", "Thực Nhận"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        table.setRowHeight(25);

        add(new JScrollPane(table), BorderLayout.CENTER);

        // Load dữ liệu
        loadData();

        setVisible(true);
    }

    private void loadData() {
        try {
            Connection conn = DatabaseConnector.getConnection();

            String sql = "SELECT * FROM view_bang_luong_chi_tiet";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            tableModel.setRowCount(0);
            NumberFormat currency = NumberFormat.getInstance(new Locale("vi", "VN"));

            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                // Lấy theo thứ tự cột trong ảnh Workbench bạn gửi
                row.add(rs.getString(1)); // Mã
                row.add(rs.getString(2)); // Tên
                row.add(rs.getString(3)); // Chức vụ
                row.add(currency.format(rs.getDouble(4))); // Lương
                row.add(rs.getDouble(5)); // Hệ số
                row.add(currency.format(rs.getDouble(6))); // Thực nhận
                tableModel.addRow(row);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}