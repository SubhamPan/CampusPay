import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Home {
    // show the home page
    public void show() {
        // create a new frame to store text field and button
        JFrame f = new JFrame("Home");
        f.setSize(800, 600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(null);

        // create a label
        JLabel title = new JLabel("Home Page");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(300, 30);
        title.setLocation(250, 30);
        f.add(title);

        // show the total amount spent by querying the database
        JLabel total = new JLabel("Total Amount Spent: ");
        total.setFont(new Font("Arial", Font.PLAIN, 20));
        total.setSize(150, 20);
        total.setLocation(100, 100);
        f.add(total);

        // connect to the database and get the total amount spent
        // use the procedure get_total_amount_spent_by_student(IN student_id varchar(50), OUT total_amount_spent int)
        try {
            Conn c = new Conn();
            CallableStatement cs = c.con.prepareCall("{call get_total_amount_spent_by_student(?, ?)}");
            cs.setString(1, User.getInstance().getId());
            cs.registerOutParameter(2, Types.INTEGER);
            cs.execute();
            int total_amount_spent = cs.getInt(2);
            JLabel totalAmount = new JLabel(Integer.toString(total_amount_spent));
            totalAmount.setFont(new Font("Arial", Font.PLAIN, 20));
            totalAmount.setSize(150, 20);
            totalAmount.setLocation(250, 100);
            f.add(totalAmount);
        } catch (Exception e) {
            System.out.println(e);
        }

        // vendors page button
        JButton vendors = new JButton("Vendors");
        vendors.setFont(new Font("Arial", Font.PLAIN, 15));
        vendors.setSize(100, 20);
        vendors.setLocation(250, 150);
        vendors.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // show the list of vendors
                VendorsMenu vendors = new VendorsMenu();
                vendors.show();
                f.dispose();
            }
        });
        f.add(vendors);

        // payment page button
        JButton payment = new JButton("Payment");
        payment.setFont(new Font("Arial", Font.PLAIN, 15));
        payment.setSize(100, 20);
        payment.setLocation(250, 200);
        payment.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // show the payment form
                Payment payment = new Payment();
                payment.show();
                f.dispose();
            }
        });
        f.add(payment);

        // payment history page button
        JButton paymentHistory = new JButton("Payment History");
        paymentHistory.setFont(new Font("Arial", Font.PLAIN, 15));
        paymentHistory.setSize(150, 20);
        paymentHistory.setLocation(250, 250);
        paymentHistory.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // show the payment history
                PaymentHistory paymentHistory = new PaymentHistory();
                paymentHistory.show();
                f.dispose();
            }
        });
        f.add(paymentHistory);

        // create a button
        JButton logout = new JButton("Logout");
        logout.setFont(new Font("Arial", Font.PLAIN, 15));
        logout.setSize(100, 20);
        logout.setLocation(250, 300);
        logout.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // go back to the login page
                LoginSystem login = new LoginSystem();
                login.show();
                f.dispose();
            }
        });
        f.add(logout);

        // set the frame visibility
        f.setVisible(true);
    }
}
