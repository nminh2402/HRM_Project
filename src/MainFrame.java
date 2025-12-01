import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        // 1. Cài đặt cửa sổ chính (Dashboard)
        setTitle("Trang chủ - Hệ thống Quản lý HRM");
        setSize(800, 600); // Tăng chiều cao lên xíu vì có nhiều nút hơn
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 2. Header (Tiêu đề trên cùng)
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 102, 204));
        headerPanel.setPreferredSize(new Dimension(800, 80));
        headerPanel.setLayout(new GridBagLayout());

        JLabel lblWelcome = new JLabel("HỆ THỐNG QUẢN LÝ NHÂN SỰ (HRM)");
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 26));
        lblWelcome.setForeground(Color.WHITE);
        headerPanel.add(lblWelcome);

        add(headerPanel, BorderLayout.NORTH);

        // 3. Panel chứa các nút chức năng
        JPanel menuPanel = new JPanel(new GridLayout(0, 2, 20, 20));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // --- Nút 1: Quản lý Nhân viên ---
        JButton btnNhanVien = createMenuButton("QUẢN LÝ NHÂN VIÊN", new Color(0, 153, 76));
        btnNhanVien.addActionListener(e -> new NhanVienFrame());

        // --- Nút 2: Quản lý Phòng ban ---
        JButton btnPhongBan = createMenuButton("QUẢN LÝ PHÒNG BAN", new Color(255, 128, 0));
        btnPhongBan.addActionListener(e -> new PhongBanFrame());

        // --- Nút 3: Quản lý Lương ---
        JButton btnLuong = createMenuButton("BẢNG LƯƠNG CHI TIẾT", new Color(153, 0, 153));
        btnLuong.addActionListener(e -> new BangLuongFrame());

        // --- Nút 4: Quản lý Công việc (MỚI) ---
        JButton btnCongViec = createMenuButton("QUẢN LÝ CÔNG VIỆC", new Color(0, 102, 204));
        btnCongViec.addActionListener(e -> new CongViecFrame());

        // --- Nút 5: Đăng xuất ---
        JButton btnLogout = createMenuButton("ĐĂNG XUẤT", new Color(204, 0, 0));
        btnLogout.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn đăng xuất?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if(choice == JOptionPane.YES_OPTION){
                this.dispose();
                new LoginFrame();
            }
        });

        // THÊM CÁC NÚT VÀO MENU
        menuPanel.add(btnNhanVien);
        menuPanel.add(btnPhongBan);
        menuPanel.add(btnLuong);
        menuPanel.add(btnCongViec);
        menuPanel.add(btnLogout);

        add(menuPanel, BorderLayout.CENTER);



        setVisible(true);
    }


    private JButton createMenuButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createRaisedBevelBorder());
        return btn;
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel"); } catch (Exception e) {}
        new MainFrame();
    }
}