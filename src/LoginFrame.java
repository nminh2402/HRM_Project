import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {

    private JTextField userField;
    private JPasswordField passField;
    private JButton loginButton;
    private JButton registerButton;

    public LoginFrame() {
        setTitle("Hệ thống Quản lý Nhân sự - HRM");
        setSize(850, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);


        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setBounds(0, 0, 400, 500);
        leftPanel.setLayout(new BorderLayout());

        JLabel imageLabel = new JLabel();
        try {
            ImageIcon originalIcon = new ImageIcon("login_image.jpg");
            if (originalIcon.getIconWidth() > 0) {
                Image image = originalIcon.getImage();
                Image scaledImage = image.getScaledInstance(400, 500, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(scaledImage));
            } else {
                imageLabel.setText("CHƯA CÓ ẢNH");
                imageLabel.setHorizontalAlignment(JLabel.CENTER);
                leftPanel.setBackground(new Color(0, 102, 204));
            }
        } catch (Exception e) { e.printStackTrace(); }

        leftPanel.add(imageLabel, BorderLayout.CENTER);
        add(leftPanel);


        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(null);
        rightPanel.setBounds(400, 0, 450, 500);
        rightPanel.setBackground(Color.WHITE);

        // Tiêu đề
        JLabel titleLabel = new JLabel("ĐĂNG NHẬP");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(new Color(0, 102, 204));
        titleLabel.setBounds(100, 60, 250, 50);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        rightPanel.add(titleLabel);

        // Ô nhập User
        JLabel userLabel = new JLabel("Tên đăng nhập");
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        userLabel.setBounds(50, 140, 350, 30);
        rightPanel.add(userLabel);

        userField = new JTextField();
        userField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        userField.setBounds(50, 170, 330, 40);
        rightPanel.add(userField);


        JLabel passLabel = new JLabel("Mật khẩu");
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passLabel.setBounds(50, 220, 350, 30);
        rightPanel.add(passLabel);

        passField = new JPasswordField();
        passField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        passField.setBounds(50, 250, 330, 40);
        rightPanel.add(passField);


        loginButton = new JButton("ĐĂNG NHẬP");
        loginButton.setBackground(new Color(0, 102, 204));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginButton.setBounds(50, 340, 150, 45);
        loginButton.setFocusPainted(false);
        rightPanel.add(loginButton);


        registerButton = new JButton("Đăng ký");
        registerButton.setBackground(Color.WHITE);
        registerButton.setForeground(new Color(0, 102, 204));
        registerButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        registerButton.setBounds(230, 340, 150, 45);
        registerButton.setFocusPainted(false);
        rightPanel.add(registerButton);

        add(rightPanel);


        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = userField.getText();
                String pass = new String(passField.getPassword());
                UserDAO dao = new UserDAO();
                if (dao.checkLogin(user, pass)) {
                    dispose();
                    new MainFrame();
                } else {
                    JOptionPane.showMessageDialog(null, "Sai tài khoản hoặc mật khẩu!");
                }
            }
        });

        registerButton.addActionListener(e -> new RegisterFrame());

        setVisible(true);
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel"); } catch (Exception e) {}
        new LoginFrame();
    }
}