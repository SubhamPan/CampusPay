import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
public class Payment {
    // this class is used to make a payment
    private Container c;
    private JLabel title;
    private JLabel amount;
    private JTextField amountText;
    private JButton pay;
    private JButton back;
    private JTextArea tout;

    public void show() {
        // create a new frame to store the payment form
        JFrame f = new JFrame("Payment");
        f.setSize(600, 400);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(null);

        // create a label
        title = new JLabel("Make a Payment");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(300, 30);
        title.setLocation(250, 30);
        f.add(title);

        // create a label for the amount
        amount = new JLabel("Amount");
        amount.setFont(new Font("Arial", Font.PLAIN, 20));
        amount.setSize(100, 20);
        amount.setLocation(100, 100);
        f.add(amount);

        // create a text field for the amount
        amountText = new JTextField();
        amountText.setFont(new Font("Arial", Font.PLAIN, 15));
        amountText.setSize(190, 20);
        amountText.setLocation(200, 100);
        f.add(amountText);

        // create a label for the vendor
        JLabel vendor = new JLabel("Vendor");
        vendor.setFont(new Font("Arial", Font.PLAIN, 20));
        vendor.setSize(100, 20);
        vendor.setLocation(100, 150);
        f.add(vendor);

        // create a drop down list for the vendors, this will be populated from the database
        JComboBox<String> vendorList = new JComboBox<String>();

        // connect to the database and get the list of vendors
        try {
            Conn c = new Conn();
            ResultSet rs = c.stmt.executeQuery("SELECT * FROM vendors");

            // display the list of vendors
            while (rs.next()) {
                vendorList.addItem(rs.getString("v_name"));
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        vendorList.setFont(new Font("Arial", Font.PLAIN, 15));
        vendorList.setSize(190, 20);
        vendorList.setLocation(200, 150);
        f.add(vendorList);

        // create a button to make the payment
        pay = new JButton("Pay");
        pay.setFont(new Font("Arial", Font.PLAIN, 15));
        pay.setSize(100, 20);
        pay.setLocation(250, 200);
        pay.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // get the amount from the text field
                String amount = amountText.getText();

                // connect to the database and insert the payment in the transactions table
                try {
                    Conn c = new Conn();
                    // we need transaction_id, vendor_id, student_id, total amount, date_time
                    String vendor_id = "";
                    ResultSet rs = c.stmt.executeQuery("SELECT * FROM vendors WHERE v_name = '" + vendorList.getSelectedItem() + "'");
                    if (rs.next()) {
                        vendor_id = rs.getString("ID");
                    }
                    // transaction_id is auto incremented
                    // student_id is current user ID
                    // date_time is current date and time
                    c.stmt.executeUpdate("INSERT INTO transactions (vendor_id, total_amount, date_time) VALUES ('" + vendor_id + "', '" + amount + "', NOW())");

                    JOptionPane.showMessageDialog(f, "Payment successful");
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        });
        f.add(pay);

        // create a button to go back to the home page
        back = new JButton("Back");
        back.setFont(new Font("Arial", Font.PLAIN, 15));
        back.setSize(100, 20);
        back.setLocation(250, 250);
        back.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // go back to the home page
                f.dispose();
            }
        });
        f.add(back);

        // set the frame visibility
        f.setVisible(true);
    }

    public static void main(String[] args) {
        Payment p = new Payment();
        p.show();
    }
}
