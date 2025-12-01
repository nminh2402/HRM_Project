import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

public class CongViecFrame extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;

    // Các ô nhập liệu
    private JTextField txtTitle, txtStart, txtEnd, txtEmpId;

    public CongViecFrame() {
        setTitle("Quản lý Tiến độ Công việc");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        //  Tiêu đề
        JLabel titleLabel = new JLabel("DANH SÁCH CÔNG VIỆC", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(102, 0, 153)); // Màu tím đậm
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        //  Bảng dữ liệu

        String[] columnNames = {"Mã CV", "Tên Công việc", "Ngày Bắt đầu", "Ngày Kết thúc", "Mã NV Thực hiện"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        table.setRowHeight(25);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        //  Nhập liệu & Chức năng
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createTitledBorder("Giao việc mới"));

        //  Form nhập liệu
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        //  Tên việc + Mã NV
        gbc.gridx = 0; gbc.gridy = 0; inputPanel.add(new JLabel("Tên việc:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
        txtTitle = new JTextField(15); inputPanel.add(txtTitle, gbc);

        gbc.gridx = 2; gbc.gridy = 0; gbc.weightx = 0; inputPanel.add(new JLabel("Mã NV thực hiện:"), gbc);
        gbc.gridx = 3; gbc.gridy = 0; gbc.weightx = 1.0;
        txtEmpId = new JTextField(5); inputPanel.add(txtEmpId, gbc);

        //  Ngày bắt đầu + Ngày kết thúc
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0; inputPanel.add(new JLabel("Ngày BĐ (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0;
        txtStart = new JTextField(10); inputPanel.add(txtStart, gbc);

        gbc.gridx = 2; gbc.gridy = 1; gbc.weightx = 0; inputPanel.add(new JLabel("Ngày KT (YYYY-MM-DD):"), gbc);
        gbc.gridx = 3; gbc.gridy = 1; gbc.weightx = 1.0;
        txtEnd = new JTextField(10); inputPanel.add(txtEnd, gbc);

        //  Nút bấm
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        JButton btnAdd = new JButton("Thêm Công Việc");
        btnAdd.setBackground(new Color(0, 153, 76));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFont(new Font("Arial", Font.BOLD, 14));

        JButton btnDelete = new JButton("Xóa Công Việc");
        btnDelete.setBackground(new Color(204, 0, 0));
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setFont(new Font("Arial", Font.BOLD, 14));

        JButton btnClear = new JButton("Làm mới");

        btnPanel.add(btnAdd);
        btnPanel.add(btnDelete);
        btnPanel.add(btnClear);

        bottomPanel.add(inputPanel, BorderLayout.CENTER);
        bottomPanel.add(btnPanel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);


        loadData(); // Tải dữ liệu khi mở

        btnAdd.addActionListener(e -> addJob());
        btnDelete.addActionListener(e -> deleteJob());
        btnClear.addActionListener(e -> clearFields());

        // Click bảng -> Đổ dữ liệu xuống ô nhập
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int row = table.getSelectedRow();
                txtTitle.setText(table.getValueAt(row, 1).toString());
                txtStart.setText(table.getValueAt(row, 2).toString());
                txtEnd.setText(table.getValueAt(row, 3).toString());
                txtEmpId.setText(table.getValueAt(row, 4).toString());
            }
        });

        setVisible(true);
    }

    private void loadData() {
        try {
            Connection conn = DatabaseConnector.getConnection();
            String sql = "SELECT * FROM quanlynhansu.congviec";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            tableModel.setRowCount(0);
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("job_id"));
                row.add(rs.getString("title"));
                row.add(rs.getDate("start_time"));
                row.add(rs.getDate("end_time"));
                row.add(rs.getInt("employee_id"));
                tableModel.addRow(row);
            }
            conn.close();
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void addJob() {
        String title = txtTitle.getText();
        String start = txtStart.getText();
        String end = txtEnd.getText();
        String empId = txtEmpId.getText();

        if (title.isEmpty() || empId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên việc và Mã NV không được để trống!");
            return;
        }

        try {
            Connection conn = DatabaseConnector.getConnection();
            String sql = "INSERT INTO quanlynhansu.congviec (title, start_time, end_time, employee_id) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, title);
            stmt.setString(2, start);
            stmt.setString(3, end);
            stmt.setInt(4, Integer.parseInt(empId));

            if (stmt.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(this, "Giao việc thành công!");
                loadData();
                clearFields();
            }
            conn.close();
        } catch (Exception e) {

            if(e.getMessage().contains("foreign key")) {
                JOptionPane.showMessageDialog(this, "Lỗi: Mã nhân viên " + empId + " không tồn tại!");
            } else if (e.getMessage().contains("Data truncation") || e.getMessage().contains("Incorrect date")) {
                JOptionPane.showMessageDialog(this, "Lỗi: Ngày tháng phải đúng định dạng Năm-Tháng-Ngày (Ví dụ: 2025-01-01)");
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
            }
        }
    }

    private void deleteJob() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Chọn công việc cần xóa!");
            return;
        }

        int jobId = (int) table.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Xóa công việc này?", "Xác nhận", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Connection conn = DatabaseConnector.getConnection();
                String sql = "DELETE FROM quanlynhansu.congviec WHERE job_id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, jobId);

                if (stmt.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(this, "Đã xóa!");
                    loadData();
                    clearFields();
                }
                conn.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi xóa: " + e.getMessage());
            }
        }
    }

    private void clearFields() {
        txtTitle.setText("");
        txtStart.setText("");
        txtEnd.setText("");
        txtEmpId.setText("");
        table.clearSelection();
    }
}