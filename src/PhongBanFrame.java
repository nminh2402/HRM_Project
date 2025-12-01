import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

public class PhongBanFrame extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtSearchId; // Ô nhập mã nhân viên

    public PhongBanFrame() {
        setTitle("Tra cứu Phòng Ban");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // 1. Tiêu đề
        JLabel titleLabel = new JLabel("DANH SÁCH PHÒNG BAN", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(new Color(255, 102, 0)); // Màu cam
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        // 2. Bảng dữ liệu
        String[] columnNames = {"Mã PB", "Tên Phòng Ban", "Địa Điểm"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        table.setRowHeight(25);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // 3. Khu vực Tìm kiếm
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 20));
        bottomPanel.setBorder(BorderFactory.createTitledBorder("Tra cứu vị trí làm việc của Nhân viên"));
        bottomPanel.setBackground(new Color(245, 245, 245));

        JLabel lblSearch = new JLabel("Nhập Mã NV:");
        lblSearch.setFont(new Font("Arial", Font.BOLD, 14));

        txtSearchId = new JTextField(10);
        txtSearchId.setFont(new Font("Arial", Font.PLAIN, 14));

        JButton btnSearch = new JButton("Tìm phòng ban");
        btnSearch.setBackground(new Color(0, 102, 204));
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setFocusPainted(false);

        JButton btnReset = new JButton("Xem tất cả");

        bottomPanel.add(lblSearch);
        bottomPanel.add(txtSearchId);
        bottomPanel.add(btnSearch);
        bottomPanel.add(btnReset);

        add(bottomPanel, BorderLayout.SOUTH);



        // Mặc định load tất cả phòng ban
        loadAllDepartments();


        btnSearch.addActionListener(e -> searchDepartmentByEmployee());


        btnReset.addActionListener(e -> {
            txtSearchId.setText("");
            loadAllDepartments();
        });

        setVisible(true);
    }


    private void loadAllDepartments() {
        try {
            Connection conn = DatabaseConnector.getConnection();
            String sql = "SELECT * FROM quanlynhansu.phongban";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            updateTable(rs);
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void searchDepartmentByEmployee() {
        String empIdText = txtSearchId.getText().trim();

        if (empIdText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập Mã nhân viên cần tìm!");
            return;
        }

        try {
            int empId = Integer.parseInt(empIdText); // Chuyển sang số

            Connection conn = DatabaseConnector.getConnection();


            String sql = "SELECT p.* " +
                    "FROM quanlynhansu.phongban p " +
                    "JOIN quanlynhansu.nhanvien n ON p.department_id = n.department_id " +
                    "WHERE n.employee_id = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, empId);

            ResultSet rs = stmt.executeQuery();


            if (!rs.isBeforeFirst()) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy! \n(Nhân viên mã " + empId + " không tồn tại hoặc chưa được xếp phòng ban)");
                tableModel.setRowCount(0); // Xóa bảng
            } else {
                updateTable(rs); // Hiển thị kết quả
            }

            conn.close();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Mã nhân viên phải là số!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi tìm kiếm: " + e.getMessage());
        }
    }


    private void updateTable(ResultSet rs) throws Exception {
        tableModel.setRowCount(0); // Xóa dữ liệu cũ
        while (rs.next()) {
            Vector<Object> row = new Vector<>();
            row.add(rs.getInt("department_id"));
            row.add(rs.getString("name"));
            row.add(rs.getString("location"));
            tableModel.addRow(row);
        }
    }
}