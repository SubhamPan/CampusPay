import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class AdminEditTransaction {
    private JFrame f;
    private JLabel vendor, student, total, date;
    private JTextField vendorText, studentText, totalText, dateText;
    private JButton edit;
    private String transaction_id;
    private String vendor_id;
    private String student_id;
    private String total_amount;
    private String date_time;

    public AdminEditTransaction(String transaction_id, String vendor_id, String student_id, String total_amount, String date_time) {
        this.transaction_id = transaction_id;
        this.vendor_id = vendor_id;
        this.student_id = student_id;
        this.total_amount = total_amount;
        this.date_time = date_time;
        f = new JFrame("Edit Transaction");
        vendor = new JLabel("Vendor ID");
        vendor.setFont(new Font("Arial", Font.PLAIN, 20));
        vendor.setSize(100, 20);
        vendor.setLocation(100, 100);
        f.add(vendor);

        vendorText = new JTextField();
        vendorText.setFont(new Font("Arial", Font.PLAIN, 15));
        vendorText.setSize(190, 20);
        vendorText.setLocation(200, 100);
        vendorText.setText(vendor_id);
        f.add(vendorText);

        student = new JLabel("Student ID");
        student.setFont(new Font("Arial", Font.PLAIN, 20));
        student.setSize(100, 20);
        student.setLocation(100, 150);
        f.add(student);

        studentText = new JTextField();
        studentText.setFont(new Font("Arial", Font.PLAIN, 15));
        studentText.setSize(190, 20);
        studentText.setLocation(200, 150);
        studentText.setText(student_id);
        f.add(studentText);

        total = new JLabel("Total Amount");
        total.setFont(new Font("Arial", Font.PLAIN, 20));
        total.setSize(100, 20);
        total.setLocation(100, 200);
        f.add(total);

        totalText = new JTextField();
        totalText.setFont(new Font("Arial", Font.PLAIN, 15));
        totalText.setSize(190, 20);
        totalText.setLocation(200, 200);
        totalText.setText(total_amount);
        f.add(totalText);

        date = new JLabel("Date Time");
        date.setFont(new Font("Arial", Font.PLAIN, 20));
        date.setSize(100, 20);
        date.setLocation(100, 250);
        f.add(date);

        dateText = new JTextField ();
        dateText.setFont(new Font("Arial", Font.PLAIN, 15));
        dateText.setSize(190, 20);
        dateText.setLocation(200, 250);
        dateText.setText(date_time);
        f.add(dateText);

        edit = new JButton("Edit");
        edit.setFont(new Font("Arial", Font.PLAIN, 15));
        edit.setSize(100, 20);
        edit.setLocation(200, 300);
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Conn c = new Conn();
                    String query = "UPDATE transactions SET vendor_id = ?, student_id = ?, total_amount = ?, date_time = ? WHERE ID = ?";
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
        f.add(edit);

//        back
        JButton back = new JButton("Back");
        back.setFont(new Font("Arial", Font.PLAIN, 15));
        back.setSize(100, 20);
        back.setLocation(200, 350);
        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ShowTransactions showTransactions = new ShowTransactions();
                showTransactions.show();
                f.dispose();
            }
        });

        f.setSize(500, 500);
        f.setLayout(null);
        f.setVisible(true);
    }

    public void show() {
        f.setVisible(true);
    }

//    public static void main(String[] args) {
//        AdminEditTransaction adminEditTransaction = new AdminEditTransaction("1", "1", "1", "100", "2021-05-01 00:00:00");
//        adminEditTransaction.show();
//    }

}
