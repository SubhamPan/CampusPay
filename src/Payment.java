import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        f.setSize(900, 600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setResizable(false);
        f.setLayout(null);
        f.getContentPane().setBackground(new Color(243, 238, 234)); // Set background color

        // create a label
        title = new JLabel("Make a Payment");
        title.setFont(new Font("MONOSPACED", Font.BOLD, 30));
        title.setSize(300, 30);
        title.setLocation(350, 130);
        f.add(title);

        // create a label for the amount
        amount = new JLabel("Amount");
        amount.setFont(new Font("Arial", Font.PLAIN, 20));
        amount.setSize(100, 20);
        amount.setLocation(300, 200);
        f.add(amount);

        // create a text field for the amount
        amountText = new JTextField();
        amountText.setFont(new Font("Arial", Font.PLAIN, 15));
        amountText.setSize(190, 20);
        amountText.setLocation(400, 200);
        amountText.setBorder(BorderFactory.createLineBorder(new Color(224, 227, 215), 2)); // Set border color
        f.add(amountText);

        // create a label for the vendor
        JLabel vendor = new JLabel("Vendor");
        vendor.setFont(new Font("Arial", Font.PLAIN, 20));
        vendor.setSize(100, 20);
        vendor.setLocation(300, 250);
        f.add(vendor);

        // create a drop down list for the vendors, this will be populated from the database
        JComboBox<String> vendorList = new JComboBox<String>();

        // connect to the database and get the list of vendors
        try {
            Conn c = new Conn();
            CallableStatement cs = c.con.prepareCall("{call get_all_vendors()}");
            ResultSet rs = cs.executeQuery();

            // display the list of vendors
            while (rs.next()) {
                // ID - v_name
                vendorList.addItem(rs.getString("ID") + " - " + rs.getString("v_name"));
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        vendorList.setFont(new Font("Arial", Font.PLAIN, 15));
        vendorList.setSize(190, 20);
        vendorList.setLocation(400, 250);
        vendorList.setBorder(BorderFactory.createLineBorder(new Color(224, 227, 215), 2)); // Set border color
        f.add(vendorList);

        // create a button to make the payment
        pay = new JButton("Pay");
        pay.setFont(new Font("Arial", Font.PLAIN, 15));
        pay.setSize(100, 20);
        pay.setLocation(400, 300);
        pay.setBackground(new Color(176, 166, 149)); // Set background color
        pay.setBorder(BorderFactory.createLineBorder(new Color(176, 166, 149), 2)); // Set border color
        pay.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // get the amount from the text field
                String amount = amountText.getText();

                // connect to the database and insert the payment in the transactions table
                try {
                    Conn c = new Conn();
                    // we need transaction_id, vendor_id, student_id, total amount, date_time
                    String vendor_id = vendorList.getSelectedItem().toString().split(" - ")[0];
                    // transaction_id is auto incremented
                    // student_id is current user ID
                    CallableStatement cs = c.con.prepareCall("{call make_transaction(?, ?, ?, ?)}");
                    cs.setString(1, vendor_id);
                    cs.setString(2, User.getInstance().getId());
                    cs.setString(3, amount);
                    cs.registerOutParameter(4, Types.INTEGER);
                    cs.executeQuery();
                    int status = cs.getInt(4);
                    if (status == 0) {
                        JOptionPane.showMessageDialog(f, "Budget exceeded, payment failed");
                        return;
                    }
                    JOptionPane.showMessageDialog(f, "Payment successful");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(f, "Error: " + ex);
                    System.out.println(ex);
                }
            }
        });
        f.add(pay);

        // create a button to go back to the home page
        back = new JButton("Back");
        back.setFont(new Font("Arial", Font.PLAIN, 15));
        back.setSize(100, 20);
        back.setLocation(400, 350);
        back.setBackground(new Color(176, 166, 149)); // Set background color
        back.setBorder(BorderFactory.createLineBorder(new Color(176, 166, 149), 2)); // Set border color
        back.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // go back to the home page
                StudentHome h = new StudentHome();
                h.show();
                f.dispose();
            }
        });
        f.add(back);

        // set the frame visibility
        f.setVisible(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (dim.width - f.getSize().width) / 2;
        int y = (dim.height - f.getSize().height) / 2;
        f.setLocation(x, y);
    }

//    public static void main(String[] args) {
//        Payment p = new Payment();
//        p.show();
//    }
}
