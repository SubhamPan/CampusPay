import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class AdminEditTransaction {
    private JFrame f;
    private JLabel vendor, student, total, date;
    private JTextField vendorText, studentText, totalText, dateText;
    private JButton edit;

    public AdminEditTransaction(String transaction_id, String vendor_id, String student_id, String total_amount, String date_time) {
        f = new JFrame("Edit Transaction");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(900, 600);
        f.setResizable(false);

        Container container = f.getContentPane();
        container.setLayout(null);
        container.setBackground(new Color(243, 238, 234)); // Set background color

        JLabel title = new JLabel("Edit Transaction");
        title.setFont(new Font("MONOSPACED", Font.BOLD, 30)); // Use same font and size as login window
        title.setSize(300, 30);
        title.setLocation(350, 100);
        container.add(title);

        vendor = new JLabel("Vendor ID");
        vendor.setFont(new Font("Arial", Font.PLAIN, 20)); // Use same font and size as login window
        vendor.setSize(100, 20);
        vendor.setLocation(300, 150);
        container.add(vendor);

        vendorText = new JTextField();
        vendorText.setFont(new Font("Arial", Font.PLAIN, 15)); // Use same font and size as login window
        vendorText.setSize(190, 20);
        vendorText.setLocation(450, 150);
        vendorText.setText(vendor_id);
        vendorText.setBorder(BorderFactory.createLineBorder(new Color(224, 227, 215), 2)); // Set border color
        container.add(vendorText);

        student = new JLabel("Student ID");
        student.setFont(new Font("Arial", Font.PLAIN, 20));
        student.setSize(100, 20);
        student.setLocation(300, 200);
        container.add(student);

        studentText = new JTextField();
        studentText.setFont(new Font("Arial", Font.PLAIN, 15));
        studentText.setSize(190, 20);
        studentText.setLocation(450, 200);
        studentText.setText(student_id);
        studentText.setBorder(BorderFactory.createLineBorder(new Color(224, 227, 215), 2)); // Set border color
        container.add(studentText);

        total = new JLabel("Total Amount");
        total.setFont(new Font("Arial", Font.PLAIN, 20));
        total.setSize(150, 20);
        total.setLocation(300, 250);
        container.add(total);

        totalText = new JTextField();
        totalText.setFont(new Font("Arial", Font.PLAIN, 15));
        totalText.setSize(190, 20);
        totalText.setLocation(450, 250);
        totalText.setText(total_amount);
        totalText.setBorder(BorderFactory.createLineBorder(new Color(224, 227, 215), 2)); // Set border color
        container.add(totalText);

        date = new JLabel("Date Time");
        date.setFont(new Font("Arial", Font.PLAIN, 20));
        date.setSize(100, 20);
        date.setLocation(300, 300);
        container.add(date);

        dateText = new JTextField ();
        dateText.setFont(new Font("Arial", Font.PLAIN, 15));
        dateText.setSize(190, 20);
        dateText.setLocation(450, 300);
        dateText.setText(date_time);
        dateText.setBorder(BorderFactory.createLineBorder(new Color(224, 227, 215), 2)); // Set border color
        container.add(dateText);

        edit = new JButton("Edit");
        edit.setFont(new Font("Arial", Font.PLAIN, 15));
        edit.setSize(100, 30); // Increase height for better visibility
        edit.setLocation(400, 350);
        edit.setBackground(new Color(176, 166, 149));
        edit.setBorder(BorderFactory.createLineBorder(new Color(176, 166, 149), 2)); // Set border color
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Conn c = new Conn();
                    String query = "CALL update_transaction(?, ?, ?, ?, ?)";
                    PreparedStatement stmt = c.con.prepareStatement(query);
                    stmt.setString(1, vendorText.getText());
                    stmt.setString(2, studentText.getText());
                    stmt.setString(3, totalText.getText());
                    stmt.setString(4, dateText.getText());
                    stmt.setString(5, transaction_id);
                    stmt.executeUpdate();
                    JOptionPane.showMessageDialog(f, "Transaction Updated");
                    f.dispose();
                    ShowTransactions showTransactions = new ShowTransactions();
                    showTransactions.show();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        container.add(edit);

        JButton back = new JButton("Back");
        back.setFont(new Font("Arial", Font.PLAIN, 15)); // Use same font and size as login window
        back.setSize(100, 30); // Increase height for better visibility
        back.setLocation(400, 400);
        back.setBackground(new Color(176, 166, 149));
        back.setBorder(BorderFactory.createLineBorder(new Color(176, 166, 149), 2)); // Set border color
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShowTransactions showTransactions = new ShowTransactions();
                showTransactions.show();
                f.dispose();
            }
        });
        container.add(back);

//        f.setVisible(true);

        // Centering the frame on the screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (dim.width - f.getSize().width) / 2;
        int y = (dim.height - f.getSize().height) / 2;
        f.setLocation(x, y);
    }

    public void show() {
        f.setVisible(true);
    }

    public static void main(String[] args) {
        AdminEditTransaction adminEditTransaction = new AdminEditTransaction("1", "1", "1", "100", "2021-05-01 00:00:00");
        adminEditTransaction.show();
    }
}
