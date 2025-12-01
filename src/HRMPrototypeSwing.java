import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class HRMPrototypeSwing {


    private JFrame frame;
    private JTable employeeTable;
    private DefaultTableModel tableModel;
    private JTextField idField;
    private JTextField nameField;
    private JTextField positionField;




    public void createAndShowGUI() {

        frame = new JFrame("Hệ thống Quản lý HRM (Swing Prototype)");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400); // Kích thước lớn hơn
        frame.setLocationRelativeTo(null); // Giữa màn hình
        frame.setLayout(new BorderLayout(10, 10)); // Layout chính

        // nhap lieu
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        JLabel idLabel = new JLabel("Mã Nhân viên:");
        idField = new JTextField(15);

        JLabel nameLabel = new JLabel("Tên Nhân viên:");
        nameField = new JTextField(15);

        JLabel positionLabel = new JLabel("Chức vụ:");
        positionField = new JTextField(15);

        inputPanel.add(idLabel);
        inputPanel.add(idField);
        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(positionLabel);
        inputPanel.add(positionField);

        frame.add(inputPanel, BorderLayout.NORTH);


        String[] columnNames = {"Mã NV", "Tên Nhân viên", "Chức vụ"};
        tableModel = new DefaultTableModel(columnNames, 0);
        employeeTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(employeeTable);
        frame.add(scrollPane, BorderLayout.CENTER);


        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton addButton = new JButton("Thêm Nhân viên");
        buttonPanel.add(addButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);


        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                String name = nameField.getText();
                String position = positionField.getText();

                if (!id.isEmpty() && !name.isEmpty() && !position.isEmpty()) {
                    Object[] rowData = {id, name, position};
                    tableModel.addRow(rowData);

                    idField.setText("");
                    nameField.setText("");
                    positionField.setText("");
                } else {
                    JOptionPane.showMessageDialog(frame,
                            "Vui lòng nhập đầy đủ thông tin!",
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE); // Lỗi gõ sai là ở đây
                }
            }
        });


        frame.setVisible(true);
    }
}
