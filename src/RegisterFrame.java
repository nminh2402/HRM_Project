import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {

    private JTextField userField;
    private JPasswordField passField;
    private JPasswordField confirmPassField;
    private JButton btnRegister;
    private JButton btnCancel;

    public RegisterFrame() {
        setTitle("Đăng Ký Tài Khoản");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // 1. Tiêu đề
        JLabel titleLabel = new JLabel("ĐĂNG KÝ TÀI KHOẢN", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.BLUE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        // 2. Form nhập liệu
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // --- Dòng 1: Tên đăng nhập ---
        inputPanel.add(new JLabel("Tên đăng nhập:"));
        userField = new JTextField();
        inputPanel.add(userField); // <--- LẦN TRƯỚC MÌNH THIẾU DÒNG NÀY

        // --- Dòng 2: Mật khẩu ---
        inputPanel.add(new JLabel("Mật khẩu:"));
        passField = new JPasswordField();
        inputPanel.add(passField); // <--- VÀ DÒNG NÀY

        // --- Dòng 3: Nhập lại mật khẩu ---
        inputPanel.add(new JLabel("Nhập lại mật khẩu:"));
        confirmPassField = new JPasswordField();
        inputPanel.add(confirmPassField); // <--- VÀ DÒNG NÀY NỮA

        add(inputPanel, BorderLayout.CENTER);

        // 3. Nút bấm
        JPanel buttonPanel = new JPanel();
        btnRegister = new JButton("Đăng Ký");
        btnCancel = new JButton("Hủy bỏ");

        buttonPanel.add(btnRegister);
        buttonPanel.add(btnCancel);
        add(buttonPanel, BorderLayout.SOUTH);



        // Nút Đăng ký
        btnRegister.addActionListener(e -> {
            String user = userField.getText();
            String pass = new String(passField.getPassword());
            String confirm = new String(confirmPassField.getPassword());

            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
                return;
            }

            if (!pass.equals(confirm)) {
                JOptionPane.showMessageDialog(this, "Mật khẩu nhập lại không khớp!");
                return;
            }

            UserDAO dao = new UserDAO();
            if (dao.register(user, pass)) {
                JOptionPane.showMessageDialog(this, "Đăng ký thành công! Hãy đăng nhập ngay.");
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Tên đăng nhập đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Nút Hủy
        btnCancel.addActionListener(e -> this.dispose());

        setVisible(true);
    }
}