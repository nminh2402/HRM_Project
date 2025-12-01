import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

public class NhanVienFrame extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;

    // Các ô nhập liệu
    private JTextField txtName, txtPhone, txtEmail, txtAddress;
    private JComboBox<String> cbGender;

    public NhanVienFrame() {
        setTitle("Quản lý Danh sách Nhân viên");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // 1. Tiêu đề
        JLabel titleLabel = new JLabel("DANH SÁCH NHÂN VIÊN", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLUE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        // 2. Bảng dữ liệu
        String[] columnNames = {"Mã NV", "Họ Tên", "Giới tính", "SĐT", "Email", "Địa chỉ"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        table.setRowHeight(25);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // 3. Khu vực Chức năng
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createTitledBorder("Thông tin nhân viên"));

        // nhap lieu
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Hàng 1
        gbc.gridx = 0; gbc.gridy = 0; inputPanel.add(new JLabel("Họ Tên:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0; txtName = new JTextField(15); inputPanel.add(txtName, gbc);

        gbc.gridx = 2; gbc.gridy = 0; gbc.weightx = 0; inputPanel.add(new JLabel("Giới tính:"), gbc);
        gbc.gridx = 3; gbc.gridy = 0; gbc.weightx = 1.0; cbGender = new JComboBox<>(new String[]{"Male", "Female", "Other"}); inputPanel.add(cbGender, gbc);

        gbc.gridx = 4; gbc.gridy = 0; gbc.weightx = 0; inputPanel.add(new JLabel("SĐT:"), gbc);
        gbc.gridx = 5; gbc.gridy = 0; gbc.weightx = 1.0; txtPhone = new JTextField(10); inputPanel.add(txtPhone, gbc);

        // Hàng 2
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0; inputPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0; txtEmail = new JTextField(15); inputPanel.add(txtEmail, gbc);

        gbc.gridx = 2; gbc.gridy = 1; gbc.weightx = 0; inputPanel.add(new JLabel("Địa chỉ:"), gbc);
        gbc.gridx = 3; gbc.gridy = 1; gbc.gridwidth = 3; gbc.weightx = 1.0; txtAddress = new JTextField(20); inputPanel.add(txtAddress, gbc);

        // --- Panel Nút Bấm ---
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        JButton btnAdd = new JButton("Thêm");
        btnAdd.setBackground(new Color(0, 153, 76));
        btnAdd.setForeground(Color.WHITE);

        JButton btnDelete = new JButton("Xóa");
        btnDelete.setBackground(new Color(204, 0, 0));
        btnDelete.setForeground(Color.WHITE);

        JButton btnClear = new JButton("Làm mới");

        btnPanel.add(btnAdd);
        btnPanel.add(btnDelete);
        btnPanel.add(btnClear);

        bottomPanel.add(inputPanel, BorderLayout.CENTER);
        bottomPanel.add(btnPanel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);


        loadDataFromDatabase();

        btnAdd.addActionListener(e -> addEmployee());
        btnDelete.addActionListener(e -> deleteEmployee());
        btnClear.addActionListener(e -> clearFields());

        // Sự kiện click bảng
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int row = table.getSelectedRow();
                // Lấy dữ liệu từ bảng đổ về ô nhập, tránh lỗi null
                Object nameVal = table.getValueAt(row, 1);
                Object genderVal = table.getValueAt(row, 2);
                Object phoneVal = table.getValueAt(row, 3);
                Object emailVal = table.getValueAt(row, 4);
                Object addrVal = table.getValueAt(row, 5);

                txtName.setText(nameVal != null ? nameVal.toString() : "");
                cbGender.setSelectedItem(genderVal != null ? genderVal.toString() : "Male");
                txtPhone.setText(phoneVal != null ? phoneVal.toString() : "");
                txtEmail.setText(emailVal != null ? emailVal.toString() : "");
                txtAddress.setText(addrVal != null ? addrVal.toString() : "");
            }
        });

        setVisible(true);
    }

    // Hàm Load
    private void loadDataFromDatabase() {
        try {
            Connection conn = DatabaseConnector.getConnection();
            String sql = "SELECT * FROM quanlynhansu.nhanvien";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            tableModel.setRowCount(0);
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("employee_id"));
                row.add(rs.getString("name"));
                row.add(rs.getString("gender"));
                row.add(rs.getString("phone"));
                row.add(rs.getString("email"));
                row.add(rs.getString("address"));
                tableModel.addRow(row);
            }
            conn.close();
        } catch (Exception e) { e.printStackTrace(); }
    }

    // Hàm Thêm
    private void addEmployee() {
        String name = txtName.getText();
        String gender = (String) cbGender.getSelectedItem();
        String phone = txtPhone.getText();
        String email = txtEmail.getText();
        String address = txtAddress.getText();

        if (name.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên và SĐT không được để trống!");
            return;
        }

        try {
            Connection conn = DatabaseConnector.getConnection();
            String sql = "INSERT INTO quanlynhansu.nhanvien (name, gender, phone, email, address, department_id, position_id, status) VALUES (?, ?, ?, ?, ?, 1, 1, 'Active')";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, gender);
            stmt.setString(3, phone);
            stmt.setString(4, email);
            stmt.setString(5, address);

            if (stmt.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(this, "Thêm thành công!");
                loadDataFromDatabase();
                clearFields();
            }
            conn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi thêm: " + e.getMessage());
        }
    }

    // Hàm Xóa
    private void deleteEmployee() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần xóa!");
            return;
        }

        int empId = (int) table.getValueAt(selectedRow, 0);
        String empName = (String) table.getValueAt(selectedRow, 1);

        int confirm = JOptionPane.showConfirmDialog(this, "Xóa nhân viên: " + empName + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Connection conn = DatabaseConnector.getConnection();
                String sql = "DELETE FROM quanlynhansu.nhanvien WHERE employee_id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, empId);

                if (stmt.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(this, "Đã xóa!");
                    loadDataFromDatabase();
                    clearFields();
                }
                conn.close();
            } catch (Exception e) {
                if(e.getMessage().contains("foreign key")) {
                    JOptionPane.showMessageDialog(this, "Không thể xóa nhân viên này vì có dữ liệu lương/tài khoản liên quan!");
                } else {
                    JOptionPane.showMessageDialog(this, "Lỗi xóa: " + e.getMessage());
                }
            }
        }
    }

    private void clearFields() {
        txtName.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        txtAddress.setText("");
        cbGender.setSelectedIndex(0);
        table.clearSelection();
    }
}